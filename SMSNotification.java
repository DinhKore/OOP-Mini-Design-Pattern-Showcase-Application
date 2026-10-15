public class SMSNotification extends Notification {

    public SMSNotification(String recipient, String message) {
        super(recipient, message);
    }

    @Override
    public String prepareContent() {
        return String.format("[SMS] (Length: %d chars) Message: %s", getMessage().length(), getMessage());
    }
}