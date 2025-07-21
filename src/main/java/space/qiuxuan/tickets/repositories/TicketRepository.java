package space.qiuxuan.tickets.repositories;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import space.qiuxuan.tickets.domain.entities.Ticket;

import java.util.UUID;

@Repository
public interface TicketRepository extends JpaRepository<Ticket, UUID> {
    int countByTicketTypeId(UUID ticketId);
}
