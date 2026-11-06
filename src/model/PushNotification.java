package model;

/**
 * Concrete Subclass đại diện cho thông báo ứng dụng (Push Notification).
 * Đây là biến thể mới được bổ sung nhằm minh chứng cho nguyên lý Open/Closed (OCP):
 * Thêm loại thông báo mới mà hoàn toàn không sửa đổi mã nguồn các lớp hiện hữu.
 */
public class PushNotification extends Notification {
    private final String deviceToken;
    private final String title;

    public PushNotification(String deviceToken, String title, String message) {
        super(deviceToken, message);

        if (title == null || title.trim().isEmpty()) {
            throw new IllegalArgumentException("Push notification title cannot be empty.");
        }
        this.deviceToken = deviceToken.trim();
        this.title = title.trim();
    }

    public String getDeviceToken() {
        return deviceToken;
    }

    public String getTitle() {
        return title;
    }

    @Override
    public String prepareContent() {
        return String.format("[Push - Title: %s]\nDevice: %s | Payload: %s",
                title, deviceToken, getMessage());
    }

    @Override
    public void validateDelivery() {
        // Device token phải có độ dài tối thiểu 8 ký tự alphanumeric
        if (!deviceToken.matches("^[A-Za-z0-9_-]{8,}$")) {
            throw new IllegalArgumentException("Invalid device token: " + deviceToken);
        }
    }
}
