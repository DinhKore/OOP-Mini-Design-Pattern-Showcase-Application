import java.util.ArrayList;
import java.util.List;

// Điều phối: chọn strategy để gửi, rồi báo cho observer. Không tự format Email/SMS và không tự ghi log.
public class NotificationDispatcher {
    private IDispatchStrategy strategy;
    private final List<NotificationObserver> observers = new ArrayList<>();

    public void setStrategy(IDispatchStrategy strategy) {
        if (strategy == null) {
            throw new IllegalArgumentException("Dispatch strategy cannot be null.");
        }
        this.strategy = strategy;
    }

    public void subscribe(NotificationObserver observer) {
        if (observer == null) {
            throw new IllegalArgumentException("Observer cannot be null.");
        }
        if (!observers.contains(observer)) {
            observers.add(observer);
        }
    }

    public void dispatch(Notification notification) {
        if (notification == null) {
            throw new IllegalArgumentException("Notification cannot be null.");
        }
        if (strategy == null) {
            notifyFailure(notification, "No dispatch strategy is set.");
            return;
        }

        try {
            notification.validateDelivery();
            strategy.dispatch(notification);
            notifySuccess(notification);
        } catch (IllegalArgumentException ex) {
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
