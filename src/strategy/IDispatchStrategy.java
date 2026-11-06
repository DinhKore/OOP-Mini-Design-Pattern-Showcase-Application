package strategy;

import model.Notification;

/**
 * Strategy Interface định nghĩa hợp đồng chung cho các thuật toán phân phối/gửi thông báo.
 * NotificationDispatcher chỉ phụ thuộc vào interface này thay vì các lớp cụ thể.
 */
public interface IDispatchStrategy {
    /**
     * Tên nhận diện của chiến lược gửi.
     */
    String getName();

    /**
     * Thực thi thuật toán gửi thông báo tương ứng.
     */
    void dispatch(Notification notification);
}
