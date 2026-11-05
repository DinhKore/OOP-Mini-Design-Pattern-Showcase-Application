public class NotificationFactory {
    public static Notification createNotification(String type, String recipient, String message, String extraData) {
        if (type == null || type.trim().isEmpty()) {
            throw new IllegalArgumentException("Notification type cannot be empty.");
        }
        
        switch (type.toLowerCase()) {
            case "email":
                if (extraData == null || extraData.trim().isEmpty()) {
                    throw new IllegalArgumentException("Email subject is required.");
                }
                return new EmailNotification(recipient, extraData, message);
                
            case "sms":
                return new SMSNotification(recipient, message);
                
            default:
                throw new IllegalArgumentException("Unsupported notification type: " + type);
        }
    }
}