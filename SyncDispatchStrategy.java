// Gửi đồng bộ: xong trong lời gọi dispatch, người gọi phải chờ.
public class SyncDispatchStrategy implements IDispatchStrategy {
    @Override
    public String getName() {
        return "Sync";
    }

    @Override
    public void dispatch(Notification notification) {
        System.out.println("[Sync] Sent now -> " + notification.prepareContent().replace("\n", " | "));
    }
}
