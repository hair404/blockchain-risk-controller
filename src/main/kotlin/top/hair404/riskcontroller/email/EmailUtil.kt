package top.hair404.riskcontroller.email

import org.simplejavamail.api.mailer.config.TransportStrategy
import org.simplejavamail.email.EmailBuilder
import org.simplejavamail.mailer.MailerBuilder
import top.hair404.riskcontroller.Config

object EmailUtil {

    private val mailer = MailerBuilder
        .withSMTPServer(Config.SMTP_SERVER, Config.SMTP_PORT, Config.EMAIL_ACCOUNT, Config.EMAIL_PASSWORD)
        .withTransportStrategy(TransportStrategy.valueOf(Config.SMTP_PROTOCOL))
        .buildMailer()

    fun sendEmail(to: String, title: String, text: String) {
        val mail = EmailBuilder.startingBlank()
            .from(Config.EMAIL_ACCOUNT)
            .to(to)
            .withSubject(title)
            .withPlainText(text)
            .buildEmail()

        mailer.sendMail(mail)
    }
}