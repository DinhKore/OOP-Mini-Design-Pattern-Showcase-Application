import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        System.out.println("=== NOTIFICATION DISPATCHER SYSTEM ===");

        List<Notification> notifications = new ArrayList<>();

        notifications.add(NotificationFactory.createNotification("email",
                "dinhkore@gmail.com",
                "Let's meet at 8 PM to finalize our progress.",
                "OOP Project Meeting"));

        notifications.add(NotificationFactory.createNotification("sms",
                "0901234567",
                "Your OTP code is: 123456",
                null));

        for (Notification note : notifications) {
            System.out.println(note.getDetails());
            System.out.println("--------------------------------------------------");
        }
    }
}