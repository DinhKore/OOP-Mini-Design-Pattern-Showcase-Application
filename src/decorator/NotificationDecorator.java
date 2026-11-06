package decorator;

import model.Notification;

import java.time.LocalDateTime;

/**
 * Abstract Decorator kế thừa Notification và chứa tham chiếu wrappedNotification.
 * Áp dụng nguyên lý ủy quyền (Delegation): Toàn bộ các phương thức getter,
 * prepareContent() và validateDelivery() mặc định đều chuyển tiếp lời gọi
 * sang đối tượng được bọc bên trong.
 */
public abstract class NotificationDecorator extends Notification {
    protected final Notification wrappedNotification;

    public NotificationDecorator(Notification notification) {
        super(); // Gọi constructor rỗng để tránh sinh UUID/Timestamp rác (Ghost State)

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
    public String getRecipient() {
        return wrappedNotification.getRecipient();
    }

    @Override
    public String getMessage() {
        return wrappedNotification.getMessage();
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
