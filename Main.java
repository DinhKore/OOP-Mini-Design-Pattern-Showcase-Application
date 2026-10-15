import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        System.out.println("=== NOTIFICATION DISPATCHER SYSTEM ===");

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
}