import java.time.LocalDateTime;

public abstract class NotificationDecorator extends Notification {
    protected Notification wrappedNotification;

    public NotificationDecorator(Notification notification) {
        super(
                notification != null ? notification.getRecipient() : "TEMP",
                notification != null ? notification.getMessage() : "TEMP");

        if (notification == null) {
            throw new IllegalArgumentException("Notification to decorate cannot be null.");
        }
        this.wrappedNotification = notification;
    }

    @Override
    public String getId() {
        return wrappedNotification.getId();
    }

    @Override
    public LocalDateTime getCreatedAt() {
        return wrappedNotification.getCreatedAt();
    }

    @Override
    public String prepareContent() {
        return wrappedNotification.prepareContent();
    }

    @Override
    public void validateDelivery() {
        wrappedNotification.validateDelivery();
    }
}