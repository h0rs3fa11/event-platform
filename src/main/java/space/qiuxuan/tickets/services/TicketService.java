package space.qiuxuan.tickets.services;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import space.qiuxuan.tickets.domain.entities.Ticket;

import java.util.Optional;
import java.util.UUID;

public interface TicketService {

    Page<Ticket> listTicketsForUser(UUID userId, Pageable pageable);

    Optional<Ticket> getTicketForUser(UUID userId, UUID ticketId);
}
