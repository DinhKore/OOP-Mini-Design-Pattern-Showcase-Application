import java.util.Base64;

public class EncryptedDecorator extends NotificationDecorator {

    public EncryptedDecorator(Notification notification) {
        super(notification);
    }

    @Override
    public String prepareContent() {
        String originalContent = wrappedNotification.prepareContent();
        String encoded = Base64.getEncoder().encodeToString(originalContent.getBytes());
        return String.format("[ENCRYPTED] %s", encoded);
    }
}