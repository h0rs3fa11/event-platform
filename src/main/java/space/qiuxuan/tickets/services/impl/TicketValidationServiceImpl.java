package space.qiuxuan.tickets.services.impl;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import space.qiuxuan.tickets.domain.entities.*;
import space.qiuxuan.tickets.exceptions.QrCodeNotFoundException;
import space.qiuxuan.tickets.exceptions.TicketNotFoundException;
import space.qiuxuan.tickets.repositories.QrCodeRepository;
import space.qiuxuan.tickets.repositories.TicketRepository;
import space.qiuxuan.tickets.repositories.TicketValidationRepository;
import space.qiuxuan.tickets.services.TicketValidationService;

import java.util.UUID;

@Service
@RequiredArgsConstructor
@Transactional
public class TicketValidationServiceImpl implements TicketValidationService {

    private final QrCodeRepository qrCodeRepository;
    private final TicketValidationRepository ticketValidationRepository;
    private final TicketRepository ticketRepository;

    @Override
    public TicketValidation validateTicketByQrCode(UUID qrCodeId) {
        QrCode qrCode = qrCodeRepository.findByIdAndStatus(qrCodeId, QrCodeStatusEnum.ACTIVE)
                .orElseThrow(() -> new QrCodeNotFoundException(String.format("Qr code %s not found", qrCodeId)));

        Ticket ticket = qrCode.getTicket();

        return validateTicket(ticket, TicketValidationMethod.QR_SCAN);
    }

    @Override
    public TicketValidation validateTicketManually(UUID ticketId) {
        Ticket ticket = ticketRepository.findById(ticketId)
                .orElseThrow(() -> new TicketNotFoundException(String.format("Ticket %s not found", ticketId)));

        return validateTicket(ticket, TicketValidationMethod.MANUAL);
    }

    private TicketValidation validateTicket(Ticket ticket, TicketValidationMethod ticketValidationMethod) {
        TicketValidation ticketValidation = new TicketValidation();
        ticketValidation.setTicket(ticket);
        ticketValidation.setValidationMethod(ticketValidationMethod);

        TicketValidationStatusEnum ticketValidationStatus = ticket.getValidations().stream()
                .filter(validation -> TicketValidationStatusEnum.VALID.equals(validation.getStatus()))
                .findFirst()
                .map(validation -> TicketValidationStatusEnum.INVALID)
                .orElse(TicketValidationStatusEnum.VALID);

        ticketValidation.setStatus(ticketValidationStatus);
        return ticketValidationRepository.save(ticketValidation);
    }
}
