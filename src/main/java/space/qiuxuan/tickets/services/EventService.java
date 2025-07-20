package space.qiuxuan.tickets.services;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import space.qiuxuan.tickets.domain.CreateEventRequest;
import space.qiuxuan.tickets.domain.UpdateEventRequest;
import space.qiuxuan.tickets.domain.entities.Event;

import java.util.Optional;
import java.util.UUID;

public interface EventService {

    Event createEvent(UUID organizerId, CreateEventRequest event);

    Page<Event> listEventsForOrganizer(UUID organizerId, Pageable pageable);

    Optional<Event> getEventForOrganizer(UUID organizerId, UUID id);

    Event updateEventForOrganizer(UUID organizerId, UUID id, UpdateEventRequest event);

    void deleteEventForOrganizer(UUID organizerId, UUID id);

}
