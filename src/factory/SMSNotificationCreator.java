package factory;

import model.Notification;
import model.SMSNotification;

/**
 * Concrete Creator cho SMSNotification.
 * Đóng gói logic khởi tạo thực tế: tự động chuẩn hóa số điện thoại
 * (loại bỏ dấu gạch ngang, khoảng trắng, chuyển mã quốc gia +84 về đầu số 0).
 */
public class SMSNotificationCreator extends NotificationCreator {

    @Override
    public Notification createNotification(String recipient, String titleOrSubject, String message) {
        String normalizedPhone = normalizePhoneNumber(recipient);
        return new SMSNotification(normalizedPhone, message);
    }

    private String normalizePhoneNumber(String phone) {
        if (phone == null) {
            return null;
        }
        // Loại bỏ khoảng trắng và dấu gạch nối
        String cleaned = phone.replaceAll("[\\s-]", "");
        // Chuyển +84 thành đầu số 0
        if (cleaned.startsWith("+84")) {
            cleaned = "0" + cleaned.substring(3);
        }
        return cleaned;
    }
}
