package model;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.UUID;

/**
 * Abstract Base Class đại diện cho một thông báo trong hệ thống.
 * Áp dụng tính Đóng gói (Encapsulation) qua các thuộc tính private/immutable
 * và định nghĩa tính Trừu tượng (Abstraction) qua các phương thức trừu tượng.
 */
public abstract class Notification {
    private final String id;
    private final String recipient;
    private final String message;
    private final LocalDateTime createdAt;

    /**
     * Constructor chính cho các loại thông báo cụ thể (Email, SMS, Push).
     * Kiểm tra tính hợp lệ dữ liệu ngay từ lúc khởi tạo (Fail-fast validation).
     */
    protected Notification(String recipient, String message) {
        if (recipient == null || recipient.trim().isEmpty()) {
            throw new IllegalArgumentException("Recipient cannot be empty.");
        }
        if (message == null || message.trim().isEmpty()) {
            throw new IllegalArgumentException("Message content cannot be empty.");
        }

        this.id = UUID.randomUUID().toString();
        this.recipient = recipient.trim();
        this.message = message;
        this.createdAt = LocalDateTime.now();
    }

    /**
     * Constructor dành riêng cho Decorator.
     * Tránh việc cấp phát thừa UUID và Timestamp (Ghost state) khi bọc đối tượng.
     */
    protected Notification() {
        this.id = null;
        this.recipient = null;
        this.message = null;
        this.createdAt = null;
    }

    public String getId() {
        return id;
    }

    public String getRecipient() {
        return recipient;
    }

    public String getMessage() {
        return message;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    /**
     * Chuẩn bị nội dung hiển thị theo định dạng riêng của từng kênh/decorator.
     */
    public abstract String prepareContent();

    /**
     * Mỗi kênh tự chịu trách nhiệm kiểm tra định dạng dữ liệu (Email format, phone regex...).
     * Thể hiện Đa hình (Polymorphism) để Dispatcher không cần dùng if/else kiểm tra kiểu.
     */
    public abstract void validateDelivery();

    /**
     * Lấy thông tin chi tiết đầy đủ của thông báo.
     * Gọi qua getter và prepareContent() để Decorator có thể ghi đè linh hoạt.
     */
    public String getDetails() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        String timeStr = (getCreatedAt() != null) ? getCreatedAt().format(formatter) : "N/A";
        return String.format("[ID: %s] %s | To: %s\nContent: %s",
                getId(), timeStr, getRecipient(), prepareContent());
    }
}
