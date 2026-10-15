public class EmailNotification extends Notification {
    private String subject;

    public EmailNotification(String recipient, String subject, String message) {
        super(recipient, message);

        if (subject == null || subject.trim().isEmpty()) {
            throw new IllegalArgumentException("Email subject cannot be empty.");
        }
        this.subject = subject;
    }

    public String getSubject() {
        return subject;
    }

    @Override
    public String prepareContent() {
        return String.format("[Email - Subject: %s]\n%s", subject, getMessage());
    }
}