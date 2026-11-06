# Mini Design-Pattern Showcase: Notification Dispatcher System

**Môn học:** Lập trình Hướng đối tượng (OOP) — HK1 2026-2027  
**Giảng viên hướng dẫn (GVHD):** Thầy Huỳnh Xuân Phụng  
**Nhóm sinh viên thực hiện:**
- **Hàn Minh Đính** — MSSV: `22110021`
- **Trần Đức Trung** — MSSV: `22110084`

---

## 1. Tổng Quan Ứng Dụng (Domain Overview)
Hệ thống **Notification Dispatcher** là một ứng dụng nhỏ gọn, mạch lạc được xây dựng nhằm mô phỏng và thị phạm chuẩn mực việc áp dụng **4 Design Patterns kinh điển** (Creational, Structural, Behavioral) cùng các nguyên lý thiết kế hướng đối tượng cốt lõi (**SOLID**, **GRASP**).

Hệ thống hỗ trợ quản lý, định dạng, bọc tính năng động và phân phối thông báo đa kênh (`Email`, `SMS`, `Push Notification`) theo các chiến lược gửi đồng bộ/bất đồng bộ và cơ chế lắng nghe sự kiện đa kênh.

---

## 2. Kiến Trúc Design Patterns & Giải Pháp Thiết Kế

| Nhóm Pattern | Pattern Áp Dụng | Vấn đề Thiết Kế (Code Smell Trước Khi Dùng) | Giải Pháp Kiến Trúc Sau Khi Áp Dụng |
| :--- | :--- | :--- | :--- |
| **Creational** | **Factory Method** (`NotificationCreator`) & **Registry** (`NotificationFactoryRegistry`) | Lạm dụng từ khóa `new` trực tiếp ở Client gây dính kết cao (tight-coupling). Dùng `switch-case` gây vi phạm nguyên lý **Open/Closed (OCP)** mỗi khi bổ sung kênh thông báo mới. | Đóng gói logic khởi tạo đối tượng vào các Creator con (`EmailNotificationCreator`, `SMSNotificationCreator`, `PushNotificationCreator`). Sử dụng Registry cho phép đăng ký Creator mới tại runtime mà không sửa đổi bất kỳ code cũ nào. |
| **Structural** | **Decorator** (`NotificationDecorator`, `EncryptedDecorator`, `PriorityDecorator`) | Hiện tượng bùng nổ lớp con (**Subclass Explosion**) khi cần kết hợp nhiều tính năng động (Mã hóa, Gán mức độ ưu tiên) với nhiều kênh thông báo khác nhau. | Cho phép bọc (wrap/chain) các tính năng bổ sung lồng nhau ngay tại runtime mà không can thiệp vào mã nguồn của lớp cơ sở. |
| **Behavioral** | **Strategy** (`IDispatchStrategy`, `SyncDispatchStrategy`, `AsyncDispatchStrategy`) | Các khối lệnh `if/else` khổng lồ trong Dispatcher khi quyết định phương thức gửi; không thể thay đổi giải thuật gửi tin tại runtime. | Đóng gói giải thuật gửi thành các chiến lược có thể hoán đổi linh hoạt (`setStrategy`) ngay trong lúc ứng dụng đang chạy. |
| **Behavioral** | **Observer** (`NotificationObserver`, `SystemLogger`, `MetricsTracker`) | Vi phạm **Separation of Concerns**: Lớp gửi tin phải ôm đồm việc ghi log, tính toán metrics thống kê, phát cảnh báo lỗi. | Thiết lập kiến trúc Publisher-Subscriber: Dispatcher chỉ phát sự kiện (`onDispatchSuccess`, `onDispatchFailure`), các subscriber (`SystemLogger`, `MetricsTracker`) độc lập tự xử lý nhiệm vụ riêng. |

---

## 3. Các Nguyên Lý OOP & Thiết Kế Thể Hiện Rõ Nét

1. **Encapsulation (Đóng gói):** 
   - Toàn bộ thuộc tính nghiệp vụ trong `Notification`, `NotificationDispatcher`, `AsyncDispatchStrategy` đều là `private final`.
   - Các danh sách hàng đợi (Queue), danh sách logs, bộ nhớ đệm đều chỉ được truy xuất ra bên ngoài dưới dạng **bản sao chỉ đọc** (`Collections.unmodifiableList`, `unmodifiableMap`).
2. **Abstraction (Trừu tượng hóa):**
   - Định nghĩa rõ nét các hợp đồng (contracts) qua Abstract Class `Notification`, `NotificationCreator`, `NotificationDecorator` và Interfaces `IDispatchStrategy`, `NotificationObserver`.
3. **Polymorphism (Đa hình):**
   - Phương thức `validateDelivery()` tự động xác thực theo quy tắc riêng của từng kênh (Email Regex, Phone length, Token validation).
   - Cơ chế ủy quyền trong `NotificationDecorator` và việc hoán đổi thuật toán linh hoạt của `IDispatchStrategy`.
4. **Open/Closed Principle (OCP):**
   - Thêm kênh `PushNotification` và `PushNotificationCreator` chỉ bằng cách bổ sung class mới và đăng ký vào `NotificationFactoryRegistry`, **tuyệt đối không sửa đổi 1 dòng code nào của các class đã có**.
5. **Separation of Concerns (Phân định trách nhiệm):**
   - Không có "God Class": Model chỉ chứa dữ liệu và tự validate định dạng; Factory lo khởi tạo; Decorator lo mở rộng tính năng; Strategy lo thuật toán gửi; Observer lo ghi log/thống kê; Dispatcher chỉ đóng vai trò nhạc trưởng điều phối.

---

## 4. Cấu Trúc Thư Mục Dự Án (Project Structure)

```text
OOP-Mini-Design-Pattern-Showcase-Application/
├── src/
│   ├── model/                      # Các đối tượng nghiệp vụ (Entities)
│   │   ├── Notification.java       # Abstract Base Class
│   │   ├── EmailNotification.java  # Kênh Email
│   │   ├── SMSNotification.java    # Kênh SMS
│   │   └── PushNotification.java   # Kênh Push (Minh chứng OCP)
│   ├── factory/                    # GoF Factory Method & OCP Registry
│   │   ├── NotificationCreator.java
│   │   ├── EmailNotificationCreator.java
│   │   ├── SMSNotificationCreator.java
│   │   ├── PushNotificationCreator.java
│   │   └── NotificationFactoryRegistry.java
│   ├── decorator/                  # Structural Decorator Pattern
│   │   ├── NotificationDecorator.java
│   │   ├── EncryptedDecorator.java
│   │   └── PriorityDecorator.java
│   ├── strategy/                   # Behavioral Strategy Pattern
│   │   ├── IDispatchStrategy.java
│   │   ├── SyncDispatchStrategy.java
│   │   └── AsyncDispatchStrategy.java
│   ├── observer/                   # Behavioral Observer Pattern
│   │   ├── NotificationObserver.java
│   │   ├── SystemLogger.java
│   │   └── MetricsTracker.java
│   ├── dispatcher/                 # Bộ điều phối trung tâm (Context/Subject)
│   │   └── NotificationDispatcher.java
│   └── Main.java                   # Điểm khởi chạy & 4 Kịch bản kiểm thử E2E
├── .gitignore
├── AI_CONTEXT.md
├── CONTRIBUTING.md
├── Dinh_Tracking
├── PROJECT_TRACKING.md
└── README.md
```

---

## 5. Hướng Dẫn Biên Dịch & Chạy Ứng Dụng (Build & Run)

### Yêu cầu môi trường:
- **Java Development Kit (JDK):** Java 17 LTS hoặc Java 21 LTS trở lên.

### Lệnh biên dịch (Chạy được trên mọi Terminal: CMD, PowerShell, Bash, Git Bash):
```bash
javac -d bin -sourcepath src src/Main.java
```

*(Hoặc dùng lệnh quét đệ quy nếu muốn)*:
- **PowerShell:** `javac -d bin (Get-ChildItem -Recurse src/*.java | ForEach-Object { $_.FullName })`
- **Linux / macOS / Bash:** `javac -d bin $(find src -name "*.java")`

### Lệnh chạy demo:
```bash
java -cp bin Main
```

---

## 6. Minh Chứng Kiểm Thử Tích Hợp (Test Scenarios)
Lớp `Main.java` tự động chạy và kiểm chứng qua assertions (100% pass) cho 4 kịch bản:
- **Scenario 1 (Happy Path E2E):** Khởi tạo bằng Factory Method $\rightarrow$ Lồng ghép Decorators (Priority + Encrypted) $\rightarrow$ Điều phối gửi qua Sync Strategy $\rightarrow$ Đa Observers (SystemLogger + MetricsTracker) ghi nhận thành công.
- **Scenario 2 (Validation & Error Handling):** Chặn đứng và ghi log trạng thái thất bại khi email sai cú pháp regex, SMS sai độ dài, hoặc khi Dispatcher thiếu Strategy.
- **Scenario 3 (Polymorphism & Strategy Swap):** Hoán đổi chiến lược gửi tức thời (Sync) sang hàng đợi (Async) tại runtime mà không sửa đổi Dispatcher.
- **Scenario 4 (OCP Proof):** Đăng ký và gửi `PushNotification` qua `PushNotificationCreator` hoàn toàn mới mà không cần chỉnh sửa bất kỳ class nào trước đó.
