package br.edu.pucminas.sistema_moeda_estudantil.model.domain.service;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class EmailService {

    @Autowired(required = false)
    private JavaMailSender mailSender;

    @Value("${spring.mail.username:}")
    private String sender;

    @Transactional
    public void sendDonationReceivedNotification(String receptor) {;

        try {

            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");

            helper.setFrom(sender);
            helper.setTo(receptor);
            helper.setSubject("Novas moedas recebidas!");

            String html = """
                            <html>
                              <body style="font-family: Arial, sans-serif; background-color: #f7f7f7; padding: 20px;">
                                <div style="max-width: 600px; margin: auto; background-color: #ffffff; padding: 20px; border-radius: 8px; box-shadow: 0 0 10px rgba(0,0,0,0.1);">
                                  <h2 style="color: #2c3e50;">Olá!</h2>
                                  <p style="color: #34495e;">Você recebeu uma doação de moeda! <site>
                                  </p>             
                                  <p style="color: #34495e; margin-top: 20px;">Obrigado por usar nossa plataforma!</p>
                                  <p style="font-size: 12px; color: #95a5a6;">Este é um e-mail automático, não responda.</p>
                                </div>
                              </body>
                            </html>
                    """;

            helper.setText(html, true);
            mailSender.send(message);

        } catch (MessagingException e) {
            throw new RuntimeException("Erro ao enviar e-mail. Nenhum dado será salvo.", e);
        }
    }


}
