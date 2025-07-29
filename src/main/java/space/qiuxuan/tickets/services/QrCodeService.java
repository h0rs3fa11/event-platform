package space.qiuxuan.tickets.services;

import com.google.zxing.WriterException;
import com.google.zxing.qrcode.QRCodeWriter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import space.qiuxuan.tickets.domain.entities.QrCode;
import space.qiuxuan.tickets.domain.entities.Ticket;
import space.qiuxuan.tickets.repositories.QrCodeRepository;

import java.util.UUID;

public interface QrCodeService {

    QrCode generateQrCode(Ticket ticket);

    byte[] getQrCodeImageForUserAndTicket(UUID userId, UUID ticketId);
}
