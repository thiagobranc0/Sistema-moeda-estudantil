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
    public void sendDonationReceivedNotification(String receptor, String doador, String mensagemDoador, Double valorDoacao) {;

        try {
            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");

            helper.setFrom(sender);
            helper.setTo(receptor);
            helper.setSubject("Novas moedas recebidas!");

            String html = """
            <html>
                <body style="font-family: Arial, sans-serif; background-color: #f7f7f7; padding: 20px;">
                    <div style="max-width: 600px; margin: auto; background-color: #ffffff; padding: 20px; border-radius: 8px; 
                                box-shadow: 0 0 10px rgba(0,0,0,0.1);">

                        <h2 style="color: #2c3e50;">Olá!</h2>

                        <p style="color: #34495e;">
                            Você recebeu uma nova doação de moedas!
                        </p>

                        <p style="color: #34495e; margin-top: 10px;">
                            <strong>Doador:</strong> %s
                        </p>

                        <p style="color: #34495e; margin-top: 5px;">
                            <strong>Valor recebido:</strong> %.2f moedas
                        </p>

                        <p style="color: #34495e; margin-top: 15px;">
                            <strong>Mensagem do doador:</strong>
                        </p>

                        <div style="background-color: #f0f0f0; padding: 12px; border-left: 4px solid #2c3e50; 
                                    border-radius: 4px; color: #555;">
                            %s
                        </div>

                        <p style="color: #34495e; margin-top: 20px;">
                            Obrigado por usar nossa plataforma!
                        </p>

                        <p style="font-size: 12px; color: #95a5a6;">
                            Este é um e-mail automático, não responda.
                        </p>

                    </div>
                </body>
            </html>
            """.formatted(doador, valorDoacao, mensagemDoador);

            helper.setText(html, true);
            mailSender.send(message);

        } catch (MessagingException e) {
            throw new RuntimeException("Erro ao enviar e-mail. Nenhum dado será salvo.", e);
        }
    }

    @Transactional
    public void sendStudentRedeemNotification(String receptor, String nomeVantagem) {;

        try {
            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");

            helper.setFrom(sender);
            helper.setTo(receptor);
            helper.setSubject("Seu código de vantagem chegou!");

            final String CODIGO_FIXO = "1mpst03r0b!";

            String html = """
            <html>
                <body style="font-family: Arial, sans-serif; background-color: #f7f7f7; padding: 20px;">
                    <div style="max-width: 600px; margin: auto; background-color: #ffffff; padding: 20px; border-radius: 8px; box-shadow: 0 0 10px rgba(0,0,0,0.1);">
                        
                        <h2 style="color: #2c3e50;">Olá! Seu código de vantagem está disponível. Aproveite!</h2>

                        <p style="color: #34495e;">
                            <strong>Vantagem resgatada:</strong> %s
                        </p>

                        <p style="color: #34495e;">
                            <strong>Código do seu resgate:</strong> %s
                        </p>

                        <p style="color: #34495e; margin-top: 20px;">
                            Obrigado por usar nossa plataforma!
                        </p>

                        <p style="font-size: 12px; color: #95a5a6;">
                            Este é um e-mail automático, não responda.
                        </p>
                    </div>
                </body>
            </html>
            """.formatted(nomeVantagem, CODIGO_FIXO);

            helper.setText(html, true);
            mailSender.send(message);

        } catch (MessagingException e) {
            throw new RuntimeException("Erro ao enviar e-mail. Nenhum dado será salvo.", e);
        }
    }

    @Transactional
    public void sendBusinessRedeemNotification(String receptor, String nomeVantagem) {;

        try {
            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");

            helper.setFrom(sender);
            helper.setTo(receptor);
            helper.setSubject("Um aluno resgatou uma de suas vantagens!");

            final String CODIGO_FIXO = "1mpst03r0b!";

            String html = """
            <html>
                <body style="font-family: Arial, sans-serif; background-color: #f7f7f7; padding: 20px;">
                    <div style="max-width: 600px; margin: auto; background-color: #ffffff; padding: 20px; border-radius: 8px; box-shadow: 0 0 10px rgba(0,0,0,0.1);">
                        
                        <h2 style="color: #2c3e50;">Olá! Um aluno resgatou uma de suas vantagens!</h2>

                        <p style="color: #34495e;">
                            <strong>Vantagem resgatada:</strong> %s
                        </p>

                        <p style="color: #34495e;">
                            <strong>Código do resgate:</strong> %s
                        </p>

                        <p style="color: #34495e; margin-top: 20px;">
                            Obrigado por usar nossa plataforma!
                        </p>

                        <p style="font-size: 12px; color: #95a5a6;">
                            Este é um e-mail automático, não responda.
                        </p>
                    </div>
                </body>
            </html>
            """.formatted(nomeVantagem, CODIGO_FIXO);

            helper.setText(html, true);
            mailSender.send(message);

        } catch (MessagingException e) {
            throw new RuntimeException("Erro ao enviar e-mail. Nenhum dado será salvo.", e);
        }
    }


}
