public class SMSNotification extends Notification {

    public SMSNotification(String recipient, String message) {
        super(recipient, message);
    }

    @Override
    public String prepareContent() {
        return String.format("[SMS] (Length: %d chars) Message: %s", getMessage().length(), getMessage());
    }

    @Override
    public void validateDelivery() {
        if (!getRecipient().matches("^0\\d{9}$")) {
            throw new IllegalArgumentException("Invalid phone number: " + getRecipient());
        }
    }
}