import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        System.out.println("=== NOTIFICATION DISPATCHER SYSTEM ===");
        printBaseNotifications();
        runDispatchDemo();
    }

    private static void printBaseNotifications() {
        List<Notification> notifications = new ArrayList<>();

        notifications.add(new EmailNotification(
                "dinhkore@gmail.com",
                "OOP Project Meeting",
                "Let's meet at 8 PM to finalize our progress."));

        notifications.add(new SMSNotification(
                "0901234567",
                "Your OTP code is: 123456"));

        for (Notification note : notifications) {
            System.out.println(note.getDetails());
            System.out.println("--------------------------------------------------");
        }
    }

    // Luồng Thành viên B: đổi strategy lúc chạy, logger chỉ nghe sự kiện thành công/thất bại.
    private static void runDispatchDemo() {
        System.out.println("=== DISPATCH DEMO (Strategy + Observer) ===");

        NotificationDispatcher dispatcher = new NotificationDispatcher();
        SystemLogger logger = new SystemLogger();
        dispatcher.subscribe(logger);

        dispatcher.setStrategy(new SyncDispatchStrategy());
        dispatcher.dispatch(new EmailNotification(
                "tranductrung@gmail.com",
                "Week 2 update",
                "Strategy and Observer are ready for review."));

        dispatcher.setStrategy(new AsyncDispatchStrategy());
        dispatcher.dispatch(new SMSNotification(
                "0901234567",
                "Queued SMS from the async strategy."));

        dispatcher.dispatch(new EmailNotification(
                "not-an-email",
                "This should fail",
                "Recipient format is checked at dispatch time."));

        assertLog(logger, 0, "SUCCESS");
        assertLog(logger, 0, "strategy=Sync");
        assertLog(logger, 1, "SUCCESS");
        assertLog(logger, 1, "strategy=Async");
        assertLog(logger, 2, "FAILED");
        assertLog(logger, 2, "Invalid email address");

        System.out.println("Dispatch demo assertions passed.");
    }

    private static void assertLog(SystemLogger logger, int index, String expectedPart) {
        String line = logger.getLogs().get(index);
        if (!line.contains(expectedPart)) {
            throw new IllegalStateException(
                    "Expected log[" + index + "] to contain \"" + expectedPart + "\" but was: " + line);
        }
    }
}
