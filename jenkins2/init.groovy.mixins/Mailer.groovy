// Configure global mail settings
def mailer = { instance, smtp_host, smtp_port, replyto_addr, email_suffix ->
    def mailer = instance.getDescriptor('hudson.tasks.Mailer')
    mailer.setSmtpHost(smtp_host)
    mailer.setSmtpPort(smtp_port)
    mailer.setReplyToAddress(replyto_addr)
    mailer.setCharset('UTF-8')
    mailer.setDefaultSuffix(email_suffix)
    mailer.save()
    println "Setting smtp and replyto information"
}
