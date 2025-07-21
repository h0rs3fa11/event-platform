package space.qiuxuan.tickets.services;

import com.google.zxing.WriterException;
import space.qiuxuan.tickets.domain.entities.Ticket;

import java.util.UUID;

public interface TicketTypeService {
    Ticket purchaseTicket(UUID userId, UUID ticketTypeId) throws WriterException;
}
