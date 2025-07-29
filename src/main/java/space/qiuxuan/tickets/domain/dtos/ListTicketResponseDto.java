package space.qiuxuan.tickets.domain.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import space.qiuxuan.tickets.domain.entities.TicketStatusEnum;
import space.qiuxuan.tickets.domain.entities.TicketType;

import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ListTicketResponseDto {
    private UUID id;
    private TicketStatusEnum status;
    private ListTicketResponseTicketTypeDto ticketType;
}
