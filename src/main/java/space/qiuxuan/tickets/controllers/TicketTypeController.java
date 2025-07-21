package space.qiuxuan.tickets.controllers;

import com.google.zxing.WriterException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.*;
import space.qiuxuan.tickets.domain.entities.Ticket;
import space.qiuxuan.tickets.services.TicketTypeService;

import java.util.UUID;

import static space.qiuxuan.tickets.util.JwtUtil.parseUserId;

@RestController
@RequiredArgsConstructor
@RequestMapping(path = "/api/v1/events/{eventId}/ticket-types")
public class TicketTypeController {

    private final TicketTypeService ticketTypeService;

    @PostMapping(path = "/{ticketTypeId}/tickets")
    public ResponseEntity<Void> purchaseTicket(
            @AuthenticationPrincipal Jwt jwt,
            @PathVariable UUID ticketTypeId) throws WriterException {
        ticketTypeService.purchaseTicket(parseUserId(jwt), ticketTypeId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
