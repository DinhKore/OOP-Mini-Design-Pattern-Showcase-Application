package model;

/**
 * Concrete Subclass đại diện cho thông báo dạng SMS.
 * Cài đặt xác thực số điện thoại chuẩn Việt Nam (10 chữ số bắt đầu bằng số 0).
 */
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
