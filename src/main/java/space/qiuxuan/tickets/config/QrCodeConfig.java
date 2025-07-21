package space.qiuxuan.tickets.config;

import com.google.zxing.qrcode.QRCodeWriter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import space.qiuxuan.tickets.services.QrCodeService;

@Configuration
public class QrCodeConfig {

    @Bean
    public QRCodeWriter qrCodeWriter() {
        return new QRCodeWriter();
    }

}
