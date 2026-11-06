package model;

/**
 * Concrete Subclass đại diện cho thông báo dạng Email.
 * Mở rộng thuộc tính subject và cài đặt xác thực địa chỉ email bằng Regex.
 */
public class EmailNotification extends Notification {
    private final String subject;

    public EmailNotification(String recipient, String subject, String message) {
        super(recipient, message);

        if (subject == null || subject.trim().isEmpty()) {
            throw new IllegalArgumentException("Email subject cannot be empty.");
        }
        this.subject = subject.trim();
    }

    public String getSubject() {
        return subject;
    }

    @Override
    public String prepareContent() {
        return String.format("[Email - Subject: %s]\n%s", subject, getMessage());
    }

    @Override
    public void validateDelivery() {
        if (!getRecipient().matches("^[^@\\s]+@[^@\\s]+\\.[^@\\s]+$")) {
            throw new IllegalArgumentException("Invalid email address: " + getRecipient());
        }
    }
}
