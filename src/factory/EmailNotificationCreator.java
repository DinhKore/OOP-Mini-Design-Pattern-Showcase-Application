package factory;

import model.EmailNotification;
import model.Notification;

/**
 * Concrete Creator cho EmailNotification.
 * Đóng gói logic khởi tạo thực tế (chuẩn hóa email về chữ thường, kiểm tra tiêu đề mặc định).
 */
public class EmailNotificationCreator extends NotificationCreator {
    private final String defaultSubject;

    public EmailNotificationCreator() {
        this("No Subject");
    }

    public EmailNotificationCreator(String defaultSubject) {
        this.defaultSubject = defaultSubject;
    }

    @Override
    public Notification createNotification(String recipient, String titleOrSubject, String message) {
        String finalSubject = (titleOrSubject != null && !titleOrSubject.trim().isEmpty())
                ? titleOrSubject.trim()
                : this.defaultSubject;

        String normalizedRecipient = (recipient != null) ? recipient.trim().toLowerCase() : null;

        return new EmailNotification(normalizedRecipient, finalSubject, message);
    }
}
