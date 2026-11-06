package dispatcher;

import model.Notification;
import observer.NotificationObserver;
import strategy.IDispatchStrategy;

import java.util.ArrayList;
import java.util.List;

/**
 * Lớp điều phối trung tâm (Dispatcher / Context).
 * Đóng vai trò Subject trong Observer Pattern và Context trong Strategy Pattern.
 * Tuân thủ Separation of Concerns (SoC):
 * - Không tự format chuỗi hiển thị của Email/SMS/Push (Ủy thác cho Model/Decorator).
 * - Không tự gán cứng thuật toán gửi tin (Ủy thác cho IDispatchStrategy).
 * - Không tự ghi log hay đo lường số liệu (Ủy thác cho NotificationObserver).
 */
public class NotificationDispatcher {
    private IDispatchStrategy strategy;
    private final List<NotificationObserver> observers = new ArrayList<>();

    public void setStrategy(IDispatchStrategy strategy) {
        if (strategy == null) {
            throw new IllegalArgumentException("Dispatch strategy cannot be null.");
        }
        this.strategy = strategy;
    }

    public IDispatchStrategy getStrategy() {
        return strategy;
    }

    public void subscribe(NotificationObserver observer) {
        if (observer == null) {
            throw new IllegalArgumentException("Observer cannot be null.");
        }
        if (!observers.contains(observer)) {
            observers.add(observer);
        }
    }

    public void unsubscribe(NotificationObserver observer) {
        observers.remove(observer);
    }

    public int getObserverCount() {
        return observers.size();
    }

    /**
     * Tiến trình điều phối gửi thông báo:
     * 1. Kiểm tra cấu hình và hợp lệ cơ bản.
     * 2. Gọi validateDelivery() đa hình của chính loại Notification đó.
     * 3. Thực thi gửi qua Strategy đã được tiêm (injected).
     * 4. Bắn sự kiện thành công hoặc thất bại cho toàn bộ Observers đã đăng ký.
     */
    public void dispatch(Notification notification) {
        if (notification == null) {
            throw new IllegalArgumentException("Notification cannot be null.");
        }
        if (strategy == null) {
            notifyFailure(notification, "No dispatch strategy is set.");
            return;
        }

        try {
            // Đa hình: Mỗi kênh tự kiểm tra logic của mình (Email Regex, Phone length...)
            notification.validateDelivery();

            // Thực thi thuật toán gửi theo Strategy
            strategy.dispatch(notification);

            // Bắn tín hiệu thành công tới tất cả Subscribers
            notifySuccess(notification);
        } catch (IllegalArgumentException ex) {
            // Bắt lỗi kiểm tra dữ liệu và phát tín hiệu thất bại tới Subscribers
            notifyFailure(notification, ex.getMessage());
        }
    }

    private void notifySuccess(Notification notification) {
        for (NotificationObserver observer : observers) {
            observer.onDispatchSuccess(notification, strategy.getName());
        }
    }

    private void notifyFailure(Notification notification, String reason) {
        for (NotificationObserver observer : observers) {
            observer.onDispatchFailure(notification, reason);
        }
    }
}
