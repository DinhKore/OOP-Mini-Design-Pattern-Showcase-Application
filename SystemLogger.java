import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class SystemLogger implements NotificationObserver {
    private final List<String> logs = new ArrayList<>();

    @Override
    public void onDispatchSuccess(Notification notification, String strategyName) {
        String line = String.format("SUCCESS | strategy=%s | id=%s | to=%s",
                strategyName, notification.getId(), notification.getRecipient());
        logs.add(line);
        System.out.println("[Logger] " + line);
    }

    @Override
    public void onDispatchFailure(Notification notification, String reason) {
        String line = String.format("FAILED | id=%s | to=%s | reason=%s",
                notification.getId(), notification.getRecipient(), reason);
        logs.add(line);
        System.out.println("[Logger] " + line);
    }

    public List<String> getLogs() {
        return Collections.unmodifiableList(logs);
    }
}
