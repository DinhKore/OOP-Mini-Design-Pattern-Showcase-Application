package decorator;

import model.Notification;

import java.util.Base64;

/**
 * Concrete Decorator bổ sung tính năng mã hóa Base64 cho nội dung thông báo.
 * Giữ nguyên cấu trúc đối tượng ban đầu, chỉ biến đổi kết quả trả về của prepareContent().
 */
public class EncryptedDecorator extends NotificationDecorator {

    public EncryptedDecorator(Notification notification) {
        super(notification);
    }

    @Override
    public String prepareContent() {
        String originalContent = wrappedNotification.prepareContent();
        String encoded = Base64.getEncoder().encodeToString(originalContent.getBytes());
        return String.format("[ENCRYPTED: %s]", encoded);
    }
}
