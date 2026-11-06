package observer;

import model.Notification;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Concrete Observer: Ghi nhận vết sự kiện (Audit Log) ra console và lưu trữ trong bộ nhớ.
 */
public class SystemLogger implements NotificationObserver {
    private final List<String> logs = new ArrayList<>();

    @Override
    public void onDispatchSuccess(Notification notification, String strategyName) {
        String line = String.format("SUCCESS | strategy=%s | id=%s | to=%s",
                strategyName, notification.getId(), notification.getRecipient());
        logs.add(line);
        System.out.println("[SystemLogger] " + line);
    }

    @Override
    public void onDispatchFailure(Notification notification, String reason) {
        String line = String.format("FAILED | id=%s | to=%s | reason=%s",
                notification.getId(), notification.getRecipient(), reason);
        logs.add(line);
        System.out.println("[SystemLogger] " + line);
    }

    /**
     * Trả về danh sách log chỉ đọc để bảo toàn tính Đóng gói (Encapsulation).
     */
    public List<String> getLogs() {
        return Collections.unmodifiableList(logs);
    }

    public void clearLogs() {
        logs.clear();
    }
}
