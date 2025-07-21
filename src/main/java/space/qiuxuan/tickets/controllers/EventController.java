package space.qiuxuan.tickets.controllers;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.*;
import space.qiuxuan.tickets.domain.CreateEventRequest;
import space.qiuxuan.tickets.domain.UpdateEventRequest;
import space.qiuxuan.tickets.domain.dtos.*;
import space.qiuxuan.tickets.domain.entities.Event;
import space.qiuxuan.tickets.mappers.EventMapper;
import space.qiuxuan.tickets.services.EventService;
import space.qiuxuan.tickets.util.JwtUtil;

import java.util.UUID;

import static space.qiuxuan.tickets.util.JwtUtil.parseUserId;

@RestController
@RequestMapping(path = "/api/v1/events")
@RequiredArgsConstructor
public class EventController {

    private final EventMapper eventMapper;
    private final EventService eventService;

    @PostMapping
    public ResponseEntity<CreateEventResponseDto> createEvent(
            @AuthenticationPrincipal Jwt jwt,
            @Valid @RequestBody CreateEventRequestDto createEventRequestDto
            ) {
        CreateEventRequest createEventRequest = eventMapper.fromDto(createEventRequestDto);
        UUID userId = parseUserId(jwt);
        Event createdEvent = eventService.createEvent(userId, createEventRequest);
        CreateEventResponseDto createEventResponseDto = eventMapper.toDto(createdEvent);
        return new ResponseEntity<>(createEventResponseDto,HttpStatus.CREATED);
    }

    @PutMapping(path = "/{eventId}")
    public ResponseEntity<UpdateEventResponseDto> createEvent(
            @AuthenticationPrincipal Jwt jwt,
            @PathVariable UUID eventId,
            @Valid @RequestBody UpdateEventRequestDto updateEventRequestDto
    ) {
        UpdateEventRequest updateEventRequest = eventMapper.fromDto(updateEventRequestDto);
        UUID userId = parseUserId(jwt);

        Event updateEvent = eventService.updateEventForOrganizer(userId, eventId, updateEventRequest);

        UpdateEventResponseDto updateEventResponseDto = eventMapper.toUpdateEventResponseDtoDto(updateEvent);
        return ResponseEntity.ok(updateEventResponseDto);
    }

    @GetMapping
    public ResponseEntity<Page<ListEventResponseDto>> listEvents(
            @AuthenticationPrincipal Jwt jwt,
            Pageable pageable
    ) {
        Page<Event> events = eventService.listEventsForOrganizer(parseUserId(jwt), pageable);
        return ResponseEntity.ok(
                events.map(eventMapper::toListEventResponseDto)
        );
    }

    @GetMapping(path = "/{eventId}")
    public ResponseEntity<GetEventDetailsResponseDto> getEvent(
            @AuthenticationPrincipal Jwt jwt,
            @PathVariable UUID eventId
    ) {
        return eventService.getEventForOrganizer(parseUserId(jwt), eventId)
                .map(eventMapper::toGetEventDetailsResponseDto)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping(path = "/{eventId}")
    public ResponseEntity<Void> deleteEvent(
            @AuthenticationPrincipal Jwt jwt,
            @PathVariable UUID eventId
    ) {
        eventService.deleteEventForOrganizer(parseUserId(jwt), eventId);
        return ResponseEntity.noContent().build();
    }
}
