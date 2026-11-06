package factory;

import model.Notification;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * Registry quản lý động các Factory Method Creator theo nguyên lý Open/Closed (OCP).
 * Thay vì sử dụng khối switch-case cứng nhắc (gây vi phạm OCP mỗi khi thêm loại mới),
 * Registry cho phép đăng ký (register) thêm Creator mới tại runtime mà không cần
 * chỉnh sửa bất kỳ dòng mã nguồn nào của các class đã có.
 */
public class NotificationFactoryRegistry {
    private final Map<String, NotificationCreator> registry = new HashMap<>();

    public void register(String type, NotificationCreator creator) {
        if (type == null || type.trim().isEmpty()) {
            throw new IllegalArgumentException("Notification type cannot be empty.");
        }
        if (creator == null) {
            throw new IllegalArgumentException("Creator cannot be null.");
        }
        registry.put(type.trim().toLowerCase(), creator);
    }

    public Notification create(String type, String recipient, String titleOrSubject, String message) {
        if (type == null) {
            throw new IllegalArgumentException("Notification type cannot be null.");
        }
        NotificationCreator creator = registry.get(type.trim().toLowerCase());
        if (creator == null) {
            throw new IllegalArgumentException("Unsupported or unregistered notification type: " + type);
        }
        return creator.createNotification(recipient, titleOrSubject, message);
    }

    public boolean hasType(String type) {
        return type != null && registry.containsKey(type.trim().toLowerCase());
    }

    public Set<String> getRegisteredTypes() {
        return Collections.unmodifiableSet(registry.keySet());
    }
}
