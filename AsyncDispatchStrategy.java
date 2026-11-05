import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

// Gửi bất đồng bộ (mô phỏng): đưa vào hàng đợi rồi trả về ngay, không tạo thread.
public class AsyncDispatchStrategy implements IDispatchStrategy {
    private final List<Notification> queue = new ArrayList<>();

    @Override
    public String getName() {
        return "Async";
    }

    @Override
    public void dispatch(Notification notification) {
        queue.add(notification);
        System.out.println("[Async] Queued " + notification.getId() + ". Queue size: " + queue.size());
    }

    public List<Notification> getQueue() {
        return Collections.unmodifiableList(queue);
    }
}
