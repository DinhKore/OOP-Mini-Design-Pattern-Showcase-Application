import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        System.out.println("=== NOTIFICATION DISPATCHER SYSTEM ===");

        List<Notification> notifications = new ArrayList<>();

        Notification email = NotificationFactory.createNotification("email",
                "dinhkore@gmail.com",
                "Let's meet at 8 PM to finalize our progress.",
                "OOP Project Meeting");

        Notification sms = NotificationFactory.createNotification("sms",
                "0987654321",
                "Your OTP is 123456. Please do not share it with anyone.",
                null);

        Notification encryptedEmail = new EncryptedDecorator(email);
        notifications.add(encryptedEmail);
        notifications.add(sms);

        for (Notification note : notifications) {
            System.out.println(note.getDetails());
            System.out.println("--------------------------------------------------");
        }
    }
}