package space.qiuxuan.tickets.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import space.qiuxuan.tickets.domain.CreateEventRequest;
import space.qiuxuan.tickets.domain.CreateTicketTypeRequest;
import space.qiuxuan.tickets.domain.UpdateEventRequest;
import space.qiuxuan.tickets.domain.UpdateTicketTypeRequest;
import space.qiuxuan.tickets.domain.dtos.*;
import space.qiuxuan.tickets.domain.entities.Event;
import space.qiuxuan.tickets.domain.entities.Ticket;
import space.qiuxuan.tickets.domain.entities.TicketType;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface EventMapper {

    CreateTicketTypeRequest fromDto(CreateTicketTypeRequestDto dto);

    CreateEventRequest fromDto(CreateEventRequestDto dto);

    CreateEventResponseDto toDto(Event event);

    ListEventTicketTypeResponseDto toDto(TicketType ticketType);

    ListEventResponseDto toListEventResponseDto(Event event);

    GetEventTicketTypesResponseDto toGetEventTicketTypesResponseDtoDto(TicketType ticketType);

    GetEventDetailsResponseDto toGetEventDetailsResponseDto(Event event);

    UpdateTicketTypeRequest fromDto(UpdateTicketTypeRequestDto dto);

    UpdateEventRequest fromDto(UpdateEventRequestDto dto);

    UpdateTicketTypeResponseDto toUpdateTicketTypeResponseDtoDto(TicketType ticketType);

    UpdateEventResponseDto toUpdateEventResponseDtoDto(Event event);
}
