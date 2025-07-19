package space.qiuxuan.tickets.services.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import space.qiuxuan.tickets.domain.CreateEventRequest;
import space.qiuxuan.tickets.domain.entities.Event;
import space.qiuxuan.tickets.domain.entities.Ticket;
import space.qiuxuan.tickets.domain.entities.TicketType;
import space.qiuxuan.tickets.domain.entities.User;
import space.qiuxuan.tickets.exceptions.UserNotFoundException;
import space.qiuxuan.tickets.repositories.EventRepository;
import space.qiuxuan.tickets.repositories.UserRepository;
import space.qiuxuan.tickets.services.EventService;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class EventServiceImpl implements EventService {

    private final UserRepository userRepository;
    private final EventRepository eventRepository;

    @Override
    public Event createEvent(UUID organizerId, CreateEventRequest event) {
        User organizer = userRepository.findById(organizerId)
                .orElseThrow(() -> new UserNotFoundException(String.format("User with id %s not found", organizerId)));

        Event eventToCreate = new Event();

        List<TicketType> ticketTypesToCreate = event.getTicketTypes().stream().map(ticketType -> {
            TicketType ticketTypeToCreate = new TicketType();
            ticketTypeToCreate.setName(ticketType.getName());
            ticketTypeToCreate.setPrice(ticketType.getPrice());
            ticketTypeToCreate.setDescription(ticketType.getDescription());
            ticketTypeToCreate.setTotalAvailable(ticketType.getTotalAvailable());
            ticketTypeToCreate.setEvent(eventToCreate);
            return ticketTypeToCreate;
        }).toList();

        eventToCreate.setName(event.getName());
        eventToCreate.setStart(event.getStart());
        eventToCreate.setEnd(event.getEnd());
        eventToCreate.setVenue(event.getVenue());
        eventToCreate.setSalesStart(event.getSalesStart());
        eventToCreate.setSalesEnd(event.getSalesEnd());
        eventToCreate.setStatus(event.getStatus());
        eventToCreate.setOrganizer(organizer);
        eventToCreate.setTicketTypes(ticketTypesToCreate);

        return eventRepository.save(eventToCreate);

    }

    @Override
    public Page<Event> listEventsForOrganizer(UUID organizerId, Pageable pageable) {
        return eventRepository.findByOrganizerId(organizerId, pageable);
    }

    @Override
    public Optional<Event> getEventForOrganizer(UUID organizerId, UUID id) {
        return eventRepository.findByIdAndOrganizerId(id, organizerId);
    }

}
