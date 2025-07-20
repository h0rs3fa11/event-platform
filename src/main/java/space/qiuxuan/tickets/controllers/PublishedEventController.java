package space.qiuxuan.tickets.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import space.qiuxuan.tickets.domain.dtos.GetPublishedEventDetailsResponseDto;
import space.qiuxuan.tickets.domain.dtos.ListPublishedEventResponseDto;
import space.qiuxuan.tickets.domain.entities.Event;
import space.qiuxuan.tickets.mappers.EventMapper;
import space.qiuxuan.tickets.services.EventService;

import java.util.UUID;

@RestController
@RequestMapping(path = "/api/v1/published-events")
@RequiredArgsConstructor
public class PublishedEventController {

    private final EventService eventService;
    private final EventMapper eventMapper;

    @GetMapping
    public ResponseEntity<Page<ListPublishedEventResponseDto>> listPublishedEvents(
            @RequestParam(required = false) String q,
            Pageable pageable) {
        Page<Event> events;
        if (q != null && !q.trim().isEmpty()) {
            events = eventService.searchPublishedEvents(q, pageable);
        } else {
            events = eventService.listPublishedEvents(pageable);
        }
        return ResponseEntity.ok(events.map(eventMapper::toListPublishedEventResponseDto));
    }

    @GetMapping(path = "/{eventId}")
    public ResponseEntity<GetPublishedEventDetailsResponseDto> getPublishedEvent(
            @PathVariable UUID eventId
    ) {
        return eventService.getPublishedEvent(eventId)
                .map(eventMapper::toGetPublishedEventDetailsResponseDto)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
}
