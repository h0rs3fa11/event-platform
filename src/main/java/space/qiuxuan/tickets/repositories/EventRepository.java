package space.qiuxuan.tickets.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import space.qiuxuan.tickets.domain.entities.Event;

import java.util.UUID;

public interface EventRepository extends JpaRepository<Event, UUID> {
}
