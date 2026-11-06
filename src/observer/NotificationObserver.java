package observer;

import model.Notification;

/**
 * Observer Interface: Định nghĩa các sự kiện phản hồi khi việc gửi thông báo
 * diễn ra thành công hoặc thất bại.
 * Bất kỳ module nào muốn nhận sự kiện (Logger, Analytics, Alerting) chỉ cần cài đặt interface này.
 */
public interface NotificationObserver {
    /**
     * Kích hoạt khi thông báo được gửi thành công qua một Strategy.
     */
    void onDispatchSuccess(Notification notification, String strategyName);

    /**
     * Kích hoạt khi việc gửi thông báo gặp lỗi (Sai định dạng, thiếu cấu hình, ngoại lệ...).
     */
    void onDispatchFailure(Notification notification, String reason);
}
