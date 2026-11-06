import decorator.EncryptedDecorator;
import decorator.NotificationDecorator;
import decorator.PriorityDecorator;
import dispatcher.NotificationDispatcher;
import factory.EmailNotificationCreator;
import factory.NotificationCreator;
import factory.NotificationFactoryRegistry;
import factory.PushNotificationCreator;
import factory.SMSNotificationCreator;
import model.EmailNotification;
import model.Notification;
import model.PushNotification;
import model.SMSNotification;
import observer.MetricsTracker;
import observer.SystemLogger;
import strategy.AsyncDispatchStrategy;
import strategy.IDispatchStrategy;
import strategy.SyncDispatchStrategy;

public class Main {
    public static void main(String[] args) {
        System.out.println("======================================================================");
        System.out.println("     MINI DESIGN-PATTERN SHOWCASE: NOTIFICATION DISPATCHER SYSTEM     ");
        System.out.println("     Members: Han Minh Dinh (22110021) & Tran Duc Trung (22110084)    ");
        System.out.println("======================================================================\n");

        runScenario1HappyPath();
        runScenario2ValidationAndErrors();
        runScenario3PolymorphismAndStrategySwap();
        runScenario4OpenClosedPrincipleProof();

        System.out.println("\n>>> ALL DEMO SCENARIOS & ASSERTIONS PASSED SUCCESSFULLY (100%) <<<");
    }

    /**
     * KỊCH BẢN 1: Happy Path End-to-End (E2E)
     * Thể hiện luồng tích hợp hoàn chỉnh của cả 4 Design Patterns:
     * Factory Method -> Decorator Chaining -> Dispatcher (Strategy) -> Multi-Observers.
     */
    private static void runScenario1HappyPath() {
        printHeader("SCENARIO 1: Happy Path End-to-End Pipeline");

        // 1. Creational: Dùng Factory Method Creator để khởi tạo đối tượng (không gọi new trực tiếp)
        NotificationCreator emailCreator = new EmailNotificationCreator("Project Review Meeting");
        Notification baseEmail = emailCreator.createNotification(
                "dinhkore@gmail.com",
                "OOP Final Project",
                "Please review the Design Patterns implementation before oral defense.");

        // 2. Structural: Decorator Chaining (Bọc lồng nhau)
        // Bọc mức độ ưu tiên URGENT -> Tiếp tục bọc mã hóa Base64
        Notification urgentEmail = new PriorityDecorator(baseEmail, PriorityDecorator.PriorityLevel.URGENT);
        Notification secureUrgentEmail = new EncryptedDecorator(urgentEmail);

        System.out.println("[Preview Decorated Notification Details]");
        System.out.println(secureUrgentEmail.getDetails());
        System.out.println("----------------------------------------------------------------------");

        // 3. Behavioral: Dispatcher + Strategy + Multi-Observers
        NotificationDispatcher dispatcher = new NotificationDispatcher();
        SystemLogger logger = new SystemLogger();
        MetricsTracker metrics = new MetricsTracker();

        dispatcher.subscribe(logger);
        dispatcher.subscribe(metrics);
        dispatcher.setStrategy(new SyncDispatchStrategy());

        // Thực hiện điều phối gửi tin
        dispatcher.dispatch(secureUrgentEmail);

        // Assertions xác thực kết quả
        assertEquals(1, metrics.getTotalDispatched(), "Metrics total dispatched should be 1");
        assertEquals(1, metrics.getTotalSuccess(), "Metrics total success should be 1");
        assertEquals(0, metrics.getTotalFailed(), "Metrics total failed should be 0");
        assertContains(logger.getLogs().get(0), "SUCCESS", "Log should contain SUCCESS");
        assertContains(logger.getLogs().get(0), "strategy=Sync", "Log should indicate Sync strategy");

        System.out.println("[OK] Scenario 1 (Happy Path E2E) completed with all assertions passed.\n");
    }

    /**
     * KỊCH BẢN 2: Validation & Error Handling Scenarios
     * Kiểm chứng hệ thống từ chối các thao tác không hợp lệ (Fail-fast & Robustness).
     */
    private static void runScenario2ValidationAndErrors() {
        printHeader("SCENARIO 2: Validation & Error Handling (Rejecting Invalid Input)");

        NotificationDispatcher dispatcher = new NotificationDispatcher();
        SystemLogger logger = new SystemLogger();
        MetricsTracker metrics = new MetricsTracker();

        dispatcher.subscribe(logger);
        dispatcher.subscribe(metrics);
        dispatcher.setStrategy(new SyncDispatchStrategy());

        // Ca 2.1: Email sai định dạng (thiếu @ và domain)
        NotificationCreator emailCreator = new EmailNotificationCreator();
        Notification invalidEmail = emailCreator.createNotification(
                "invalid-email-address",
                "Important",
                "This email address has no @ symbol.");
        dispatcher.dispatch(invalidEmail);

        // Ca 2.2: Số điện thoại SMS sai định dạng (không đủ 10 số hoặc sai đầu số)
        NotificationCreator smsCreator = new SMSNotificationCreator();
        Notification invalidSMS = smsCreator.createNotification(
                "12345",
                null,
                "This phone number is too short.");
        dispatcher.dispatch(invalidSMS);

        // Ca 2.3: Dispatch khi chưa gán Strategy
        NotificationDispatcher unconfiguredDispatcher = new NotificationDispatcher();
        unconfiguredDispatcher.subscribe(logger);
        unconfiguredDispatcher.dispatch(emailCreator.createNotification("valid@gmail.com", "Test", "Msg"));

        // Assertions kiểm tra
        assertEquals(2, metrics.getTotalFailed(), "Metrics should record 2 failed deliveries");
        assertContains(logger.getLogs().get(0), "FAILED", "Log 1 should be FAILED");
        assertContains(logger.getLogs().get(0), "Invalid email address", "Log 1 reason must specify invalid email");
        assertContains(logger.getLogs().get(1), "FAILED", "Log 2 should be FAILED");
        assertContains(logger.getLogs().get(1), "Invalid phone number", "Log 2 reason must specify invalid phone");
        assertContains(logger.getLogs().get(2), "No dispatch strategy is set", "Log 3 must notify missing strategy");

        System.out.println("[OK] Scenario 2 (Validation & Error Handling) completed with all assertions passed.\n");
    }

    /**
     * KỊCH BẢN 3: Polymorphism & Strategy Swap at Runtime
     * Hoán đổi giải thuật gửi tin tại runtime và chứng minh hành vi hệ thống thay đổi tương ứng.
     */
    private static void runScenario3PolymorphismAndStrategySwap() {
        printHeader("SCENARIO 3: Polymorphism & Runtime Strategy Swap");

        NotificationDispatcher dispatcher = new NotificationDispatcher();
        SystemLogger logger = new SystemLogger();
        dispatcher.subscribe(logger);

        NotificationCreator smsCreator = new SMSNotificationCreator();
        Notification sms1 = smsCreator.createNotification("0912345678", null, "Your verification code is 889900.");
        Notification sms2 = smsCreator.createNotification("+84 987 654 321", null, "Your package is arriving today.");

        // B1: Gửi qua Sync Strategy
        System.out.println("-> Step 3.1: Using SyncDispatchStrategy (Immediate transmission):");
        dispatcher.setStrategy(new SyncDispatchStrategy());
        dispatcher.dispatch(sms1);

        // B2: Đổi sang Async Strategy tại runtime
        System.out.println("\n-> Step 3.2: Swapping to AsyncDispatchStrategy (Queue-based transmission):");
        AsyncDispatchStrategy asyncStrategy = new AsyncDispatchStrategy();
        dispatcher.setStrategy(asyncStrategy);
        dispatcher.dispatch(sms2);

        // Assertions kiểm tra
        assertEquals(1, asyncStrategy.getQueueSize(), "Async queue should contain exactly 1 queued notification");
        assertEquals("0987654321", asyncStrategy.getQueue().get(0).getRecipient(),
                "SMS phone number should be normalized from +84 to 0 prefix");
        assertContains(logger.getLogs().get(0), "strategy=Sync", "Log 0 should be Sync");
        assertContains(logger.getLogs().get(1), "strategy=Async", "Log 1 should be Async");

        System.out.println("[OK] Scenario 3 (Polymorphism & Strategy Swap) completed with all assertions passed.\n");
    }

    /**
     * KỊCH BẢN 4: Minh chứng nguyên lý Open/Closed (OCP Proof)
     * Thêm loại thông báo mới PushNotification và PushNotificationCreator
     * mà KHÔNG CẦN CHỈNH SỬA bất kỳ class cũ nào (Notification, Dispatcher, Decorator...).
     */
    private static void runScenario4OpenClosedPrincipleProof() {
        printHeader("SCENARIO 4: Open/Closed Principle (OCP Proof - Adding PushNotification)");

        // Sử dụng Registry để quản lý các Creator mở rộng động
        NotificationFactoryRegistry registry = new NotificationFactoryRegistry();
        registry.register("email", new EmailNotificationCreator());
        registry.register("sms", new SMSNotificationCreator());

        // THÊM LOẠI MỚI MÀ KHÔNG SỬA CODE CŨ:
        registry.register("push", new PushNotificationCreator("Breaking News"));

        Notification pushNote = registry.create(
                "push",
                "device_token_abc12345",
                "Server Maintenance",
                "System will undergo scheduled maintenance at midnight.");

        // Tiếp tục tận dụng toàn bộ pipeline Decorator, Strategy, Observer đã có:
        Notification decoratedPush = new PriorityDecorator(pushNote, PriorityDecorator.PriorityLevel.HIGH);

        NotificationDispatcher dispatcher = new NotificationDispatcher();
        SystemLogger logger = new SystemLogger();
        MetricsTracker metrics = new MetricsTracker();

        dispatcher.subscribe(logger);
        dispatcher.subscribe(metrics);
        dispatcher.setStrategy(new SyncDispatchStrategy());

        dispatcher.dispatch(decoratedPush);

        // Assertions kiểm tra
        assertEquals(1, metrics.getTotalSuccess(), "Push notification dispatched successfully");
        assertContains(logger.getLogs().get(0), "to=device_token_abc12345", "Log should show device token recipient");

        System.out.println("[OCP VERIFIED] Added PushNotification & PushNotificationCreator with ZERO edits to existing classes.");
        System.out.println("[OK] Scenario 4 completed with all assertions passed.\n");
    }

    // ==========================================
    // UTILITY ASSERTION METHODS
    // ==========================================
    private static void printHeader(String title) {
        System.out.println("----------------------------------------------------------------------");
        System.out.println(">>> " + title);
        System.out.println("----------------------------------------------------------------------");
    }

    private static void assertEquals(Object expected, Object actual, String message) {
        if (expected == null && actual == null) {
            return;
        }
        if (expected != null && expected.equals(actual)) {
            return;
        }
        throw new AssertionError("Assertion Failed: " + message + " | Expected: " + expected + ", but got: " + actual);
    }

    private static void assertContains(String text, String expectedSubstring, String message) {
        if (text == null || !text.contains(expectedSubstring)) {
            throw new AssertionError("Assertion Failed: " + message + " | Text was: \"" + text + "\", missing substring: \"" + expectedSubstring + "\"");
        }
    }
}
