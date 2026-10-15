import java.util.UUID;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * Lớp trừu tượng cơ sở thể hiện tính Abstraction và Encapsulation.
 * Mọi loại thông báo trong hệ thống đều phải kế thừa từ lớp này.
 */
public abstract class Notification {
    // Encapsulation: Fields được giấu kín (private), chỉ truy cập qua Getters
    private String id;
    private String recipient;
    private String message;
    private LocalDateTime createdAt;

    // Constructor bắt buộc phải có dữ liệu hợp lệ (Validation)
    protected Notification(String recipient, String message) {
        if (recipient == null || recipient.trim().isEmpty()) {
            throw new IllegalArgumentException("Người nhận không được để trống.");
        }
        if (message == null || message.trim().isEmpty()) {
            throw new IllegalArgumentException("Nội dung tin nhắn không được để trống.");
        }

        this.id = UUID.randomUUID().toString(); // Tự động sinh ID
        this.recipient = recipient;
        this.message = message;
        this.createdAt = LocalDateTime.now();
    }

    // --- Getters ---
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
     * Phương thức abstract buộc các class con phải tự định nghĩa cách thức chuẩn bị
     * nội dung
     * (Polymorphism - Đa hình)
     */
    public abstract String prepareContent();

    /**
     * Lớp con có thể ghi đè (override) nếu cần bổ sung thông tin riêng.
     */
    public String getDetails() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        return String.format("[ID: %s] %s | Tới: %s\nNội dung: %s",
                id, createdAt.format(formatter), recipient, prepareContent());
    }
}