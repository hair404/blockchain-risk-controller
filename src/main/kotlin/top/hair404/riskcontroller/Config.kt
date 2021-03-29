package top.hair404.riskcontroller

object Config {

    val SMTP_SERVER = System.getenv("SMTP_SERVER")
    val SMTP_PORT = System.getenv("SMTP_PORT").toInt()
    val SMTP_PROTOCOL = System.getenv("SMTP_PROTOCOL")
    val EMAIL_ACCOUNT = System.getenv("EMAIL_ACCOUNT")
    val EMAIL_PASSWORD = System.getenv("EMAIL_PASSWORD")
    val NOTICE_EMAIL = System.getenv("NOTICE_EMAIL")
    val PUBLIC_SERVER = System.getenv("PUBLIC_SERVER")
    val CHECK_INTERVAL = System.getenv("CHECK_INTERVAL").toLong()

}