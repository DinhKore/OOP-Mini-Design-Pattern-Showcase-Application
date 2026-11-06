package factory;

import model.Notification;
import model.PushNotification;

/**
 * Concrete Creator cho PushNotification.
 * Minh chứng OCP: Thêm một Creator mới mà không làm thay đổi
 * NotificationCreator hay các Creator khác đã có.
 */
public class PushNotificationCreator extends NotificationCreator {
    private final String defaultTitle;

    public PushNotificationCreator() {
        this("System Alert");
    }

    public PushNotificationCreator(String defaultTitle) {
        this.defaultTitle = defaultTitle;
    }

    @Override
    public Notification createNotification(String recipient, String titleOrSubject, String message) {
        String finalTitle = (titleOrSubject != null && !titleOrSubject.trim().isEmpty())
                ? titleOrSubject.trim()
                : this.defaultTitle;

        return new PushNotification(recipient, finalTitle, message);
    }
}
