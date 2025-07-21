package space.qiuxuan.tickets.services.impl;

import com.google.zxing.WriterException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import space.qiuxuan.tickets.domain.entities.Ticket;
import space.qiuxuan.tickets.domain.entities.TicketStatusEnum;
import space.qiuxuan.tickets.domain.entities.TicketType;
import space.qiuxuan.tickets.domain.entities.User;
import space.qiuxuan.tickets.exceptions.TicketSoldOutException;
import space.qiuxuan.tickets.exceptions.TicketTypeNotFoundException;
import space.qiuxuan.tickets.exceptions.UserNotFoundException;
import space.qiuxuan.tickets.repositories.TicketRepository;
import space.qiuxuan.tickets.repositories.TicketTypeRepository;
import space.qiuxuan.tickets.repositories.UserRepository;
import space.qiuxuan.tickets.services.QrCodeService;
import space.qiuxuan.tickets.services.TicketTypeService;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class TicketTypeServiceImpl implements TicketTypeService {

    private final UserRepository userRepository;
    private final TicketRepository ticketRepository;
    private final TicketTypeRepository ticketTypeRepository;
    private final QrCodeService qrCodeService;

    @Override
    @Transactional
    public Ticket purchaseTicket(UUID userId, UUID ticketTypeId) {
        User user = userRepository.findById(userId).orElseThrow(() -> new UserNotFoundException(String.format("User %s not found", userId)));

        TicketType ticketType = ticketTypeRepository.findByIdWIthLock(ticketTypeId).orElseThrow(() -> new TicketTypeNotFoundException(
                String.format("Ticket type %s not found", ticketTypeId)
        ));

        int purchasedTickets = ticketRepository.countByTicketTypeId(ticketTypeId);
        Integer totalAvailable = ticketType.getTotalAvailable();

        if(purchasedTickets + 1 > totalAvailable) {
            throw new TicketSoldOutException();
        }

        Ticket ticket = new Ticket();
        ticket.setStatus(TicketStatusEnum.PURCHASED);
        ticket.setTicketType(ticketType);
        ticket.setPurchaser(user);

        Ticket savedTicket = ticketRepository.save(ticket);
        qrCodeService.generateQrCode(savedTicket);

        return ticketRepository.save(savedTicket);

    }
}
