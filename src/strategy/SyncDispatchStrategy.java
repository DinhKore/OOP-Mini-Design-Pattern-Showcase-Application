package strategy;

import model.Notification;

/**
 * Concrete Strategy: Gửi đồng bộ (Synchronous).
 * Hoàn thành việc truyền tải ngay trong luồng thực thi hiện tại; người gọi chờ kết quả.
 */
public class SyncDispatchStrategy implements IDispatchStrategy {

    @Override
    public String getName() {
        return "Sync";
    }

    @Override
    public void dispatch(Notification notification) {
        System.out.println("[Sync Dispatch] Sent immediately to " + notification.getRecipient()
                + " -> " + notification.prepareContent().replace("\n", " | "));
    }
}
