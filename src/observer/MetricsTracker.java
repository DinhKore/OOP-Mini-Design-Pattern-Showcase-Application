package observer;

import model.Notification;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * Concrete Observer thứ hai: Thống kê và đo lường chỉ số vận hành (Metrics Analytics).
 * Minh chứng tính hiệu quả của Observer Pattern: Một sự kiện phát ra từ Dispatcher
 * được nhiều bên độc lập cùng lắng nghe (Logger để lưu vết, MetricsTracker để đếm chỉ số)
 * mà không hề can thiệp hay phụ thuộc lẫn nhau (Separation of Concerns).
 */
public class MetricsTracker implements NotificationObserver {
    private int totalDispatched = 0;
    private int totalSuccess = 0;
    private int totalFailed = 0;
    private final Map<String, Integer> strategyUsage = new HashMap<>();

    @Override
    public void onDispatchSuccess(Notification notification, String strategyName) {
        totalDispatched++;
        totalSuccess++;
        strategyUsage.put(strategyName, strategyUsage.getOrDefault(strategyName, 0) + 1);
        System.out.printf("[MetricsTracker] Updated -> Dispatched: %d | Success: %d | Failed: %d\n",
                totalDispatched, totalSuccess, totalFailed);
    }

    @Override
    public void onDispatchFailure(Notification notification, String reason) {
        totalDispatched++;
        totalFailed++;
        System.out.printf("[MetricsTracker] Updated -> Dispatched: %d | Success: %d | Failed: %d\n",
                totalDispatched, totalSuccess, totalFailed);
    }

    public int getTotalDispatched() {
        return totalDispatched;
    }

    public int getTotalSuccess() {
        return totalSuccess;
    }

    public int getTotalFailed() {
        return totalFailed;
    }

    public Map<String, Integer> getStrategyUsage() {
        return Collections.unmodifiableMap(strategyUsage);
    }

    public void reset() {
        totalDispatched = 0;
        totalSuccess = 0;
        totalFailed = 0;
        strategyUsage.clear();
    }
}
