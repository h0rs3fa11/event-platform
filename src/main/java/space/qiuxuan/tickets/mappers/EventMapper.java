package space.qiuxuan.tickets.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import space.qiuxuan.tickets.domain.CreateEventRequest;
import space.qiuxuan.tickets.domain.CreateTicketTypeRequest;
import space.qiuxuan.tickets.domain.dtos.CreateEventRequestDto;
import space.qiuxuan.tickets.domain.dtos.CreateEventResponseDto;
import space.qiuxuan.tickets.domain.dtos.CreateTicketTypeRequestDto;
import space.qiuxuan.tickets.domain.entities.Event;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface EventMapper {

    CreateTicketTypeRequest fromDto(CreateTicketTypeRequestDto dto);

    CreateEventRequest fromDto(CreateEventRequestDto dto);

    CreateEventResponseDto toDto(Event event);

}
