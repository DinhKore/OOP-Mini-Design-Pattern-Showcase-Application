package factory;

import model.Notification;

/**
 * Abstract Creator theo mẫu thiết kế GoF Factory Method.
 * Định nghĩa phương thức trừu tượng createNotification() để các lớp con
 * quyết định chính xác loại đối tượng Notification nào cần được khởi tạo.
 */
public abstract class NotificationCreator {

    /**
     * Factory Method kinh điển (GoF).
     * @param recipient Người nhận (Email / SĐT / Device Token)
     * @param titleOrSubject Tiêu đề / Chủ đề (hoặc tham số bổ trợ tương ứng)
     * @param message Nội dung thông báo
     * @return Một đối tượng Notification cụ thể
     */
    public abstract Notification createNotification(String recipient, String titleOrSubject, String message);

    /**
     * Template method / non-trivial workflow:
     * Khởi tạo thông báo và kích hoạt quy trình tiền kiểm tra (Pre-dispatch validation).
     */
    public Notification createAndValidate(String recipient, String titleOrSubject, String message) {
        Notification notification = createNotification(recipient, titleOrSubject, message);
        notification.validateDelivery();
        return notification;
    }
}
