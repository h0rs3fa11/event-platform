package space.qiuxuan.tickets.domain.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ListTicketResponseTicketTypeDto {
    private UUID id;
    private String name;
    private Double price;
}
