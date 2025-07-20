package space.qiuxuan.tickets.repositories;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import space.qiuxuan.tickets.domain.entities.Event;
import space.qiuxuan.tickets.domain.entities.EventStatusEnum;

import java.util.Optional;
import java.util.UUID;

public interface EventRepository extends JpaRepository<Event, UUID> {
    Page<Event> findByOrganizerId(UUID organizerId, Pageable pageable);
    Optional<Event> findByIdAndOrganizerId(UUID id, UUID organizerId);
    Page<Event> findByStatus(EventStatusEnum status, Pageable pageable);
}
