import java.util.UUID;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public abstract class Notification {
    private String id;
    private String recipient;
    private String message;
    private LocalDateTime createdAt;

    protected Notification(String recipient, String message) {
        if (recipient == null || recipient.trim().isEmpty()) {
            throw new IllegalArgumentException("Recipient cannot be empty.");
        }
        if (message == null || message.trim().isEmpty()) {
            throw new IllegalArgumentException("Message content cannot be empty.");
        }

        this.id = UUID.randomUUID().toString();
        this.recipient = recipient;
        this.message = message;
        this.createdAt = LocalDateTime.now();
    }

    public String getId() {
        return id;
    }

    public String getRecipient() {
        return recipient;
    }

    public String getMessage() {
        return message;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public abstract String prepareContent();

    public String getDetails() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        return String.format("[ID: %s] %s | To: %s\nContent: %s",
                id, createdAt.format(formatter), recipient, prepareContent());
    }
}