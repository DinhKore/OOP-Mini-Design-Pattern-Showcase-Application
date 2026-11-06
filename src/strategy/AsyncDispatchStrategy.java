package strategy;

import model.Notification;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Concrete Strategy: Gửi bất đồng bộ mô phỏng (Asynchronous Queued).
 * Thay vì xử lý ngay, thông báo được đẩy vào hàng đợi (Queue) chờ batch worker xử lý.
 * Thiết kế dùng danh sách nội bộ thay vì đa luồng phức tạp để đảm bảo tính tất định
 * và dễ dàng giải trình call stack khi bảo vệ trực tiếp (Live-defense).
 */
public class AsyncDispatchStrategy implements IDispatchStrategy {
    private final List<Notification> queue = new ArrayList<>();

    @Override
    public String getName() {
        return "Async";
    }

    @Override
    public void dispatch(Notification notification) {
        queue.add(notification);
        System.out.println("[Async Dispatch] Queued notification [ID: " + notification.getId()
                + "] for " + notification.getRecipient() + " (Queue depth: " + queue.size() + ")");
    }

    /**
     * Trả về danh sách chỉ đọc (Read-only view) nhằm đảm bảo tính Đóng gói (Encapsulation).
     */
    public List<Notification> getQueue() {
        return Collections.unmodifiableList(queue);
    }

    public int getQueueSize() {
        return queue.size();
    }

    public void clearQueue() {
        queue.clear();
    }
}
