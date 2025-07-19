package space.qiuxuan.tickets.domain.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import space.qiuxuan.tickets.domain.entities.Event;

import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ListEventTicketTypeResponseDto {

    private UUID id;
    private String name;
    private Double price;
    private String description;
    private Integer totalAvailable;

}
