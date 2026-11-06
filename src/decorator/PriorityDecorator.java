package decorator;

import model.Notification;

/**
 * Concrete Decorator bổ sung mức độ ưu tiên (Priority) cho thông báo.
 * Minh chứng tính linh hoạt của Decorator: Có thể lồng ghép (Chaining) nhiều
 * tính năng (vừa Đặt mức ưu tiên, vừa Mã hóa) mà không cần tạo vô số class con
 * như UrgentEmail, UrgentEncryptedEmail, UrgentSMS, v.v. (Tránh Subclass Explosion).
 */
public class PriorityDecorator extends NotificationDecorator {
    public enum PriorityLevel {
        LOW,
        NORMAL,
        HIGH,
        URGENT
    }

    private final PriorityLevel priority;

    public PriorityDecorator(Notification notification, PriorityLevel priority) {
        super(notification);
        if (priority == null) {
            throw new IllegalArgumentException("Priority level cannot be null.");
        }
        this.priority = priority;
    }

    public PriorityLevel getPriority() {
        return priority;
    }

    @Override
    public String prepareContent() {
        return String.format("[PRIORITY: %s]\n%s", priority.name(), wrappedNotification.prepareContent());
    }
}
