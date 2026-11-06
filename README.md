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

### 5.1. Yêu cầu môi trường
* Đã cài đặt **Java Development Kit (JDK 17 LTS hoặc JDK 21 LTS trở lên)**.
* Kiểm tra phiên bản bằng lệnh:
  ```bash
  java -version
  javac -version
  ```

---

### 5.2. Cách 1: Chạy nhanh qua Dòng lệnh (Terminal / CLI)
Lệnh dưới đây hoạt động chuẩn xác trên **mọi hệ điều hành và mọi trình dòng lệnh** (Windows Command Prompt `cmd`, PowerShell, macOS Terminal, Linux Bash, Git Bash, VS Code Terminal):

#### Bước 1: Biên dịch mã nguồn (Compile)
Đứng tại thư mục gốc của dự án (`OOP-Mini-Design-Pattern-Showcase-Application/`), chạy:
```bash
javac -d bin -sourcepath src src/Main.java
```
> **Giải thích:** Tham số `-sourcepath src` hướng dẫn trình biên dịch tự động tìm nạp và biên dịch toàn bộ các lớp phụ thuộc trong các package (`model`, `factory`, `decorator`, `strategy`, `observer`, `dispatcher`) vào thư mục `bin/`.

#### Bước 2: Chạy ứng dụng (Run)
```bash
java -cp bin Main
```

---

#### 💡 Mẹo chạy gộp trong 1 dòng lệnh duy nhất (One-liner):
* **Trên Windows Command Prompt (CMD):**
  ```cmd
  javac -d bin -sourcepath src src/Main.java && java -cp bin Main
  ```
* **Trên PowerShell / VS Code Terminal / Git Bash / macOS / Linux:**
  ```powershell
  javac -d bin -sourcepath src src/Main.java; java -cp bin Main
  ```

---

### 5.3. Cách 2: Chạy bằng Visual Studio Code (VS Code)
1. Mở thư mục dự án `OOP-Mini-Design-Pattern-Showcase-Application` bằng VS Code (`File > Open Folder...`).
2. Cài đặt tiện ích mở rộng **Extension Pack for Java** (của Microsoft) nếu chưa có.
3. Điều hướng mở file: `src/Main.java`.
4. Nhấn nút **Run** (biểu tượng mũi tên tam giác màu xanh ở góc trên bên phải) hoặc phím tắt `F5` / `Ctrl + F5`.

---

### 5.4. Cách 3: Chạy bằng IntelliJ IDEA / Eclipse
1. Mở IDE $\rightarrow$ chọn **Open** $\rightarrow$ chọn thư mục dự án.
2. Đảm bảo thư mục `src` được đánh dấu là **Sources Root** (Nếu chưa: Chuột phải vào thư mục `src` $\rightarrow$ `Mark Directory as` $\rightarrow$ `Sources Root`).
3. Chuột phải vào file `src/Main.java` $\rightarrow$ chọn **Run 'Main.main()'**.

---

### 5.5. Xử lý sự cố thường gặp (Troubleshooting)
* **Lỗi `'javac' is not recognized as an internal or external command`:**  
  $\rightarrow$ Bạn chưa cài JDK hoặc chưa cấu hình biến môi trường `PATH`. Hãy cài đặt JDK (khuyến nghị OpenJDK 21) và thêm đường dẫn `bin` của JDK vào biến `PATH`.
* **Lỗi `'ForEach-Object' is not recognized...`:**  
  $\rightarrow$ Lỗi xảy ra do bạn gõ nhầm lệnh của PowerShell vào cửa sổ Windows CMD. Hãy dùng lệnh biên dịch đa năng: `javac -d bin -sourcepath src src/Main.java`.

---

## 6. Kết Quả Chạy Mẫu (Expected Output Preview)
Khi chạy thành công, console sẽ hiển thị toàn bộ 4 kịch bản kiểm thử pass 100%:
```text
======================================================================
     MINI DESIGN-PATTERN SHOWCASE: NOTIFICATION DISPATCHER SYSTEM     
     Members: Han Minh Dinh (22110021) & Tran Duc Trung (22110084)    
======================================================================

----------------------------------------------------------------------
>>> SCENARIO 1: Happy Path End-to-End Pipeline
----------------------------------------------------------------------
[Preview Decorated Notification Details]
[ID: ...] ... | To: dinhkore@gmail.com
Content: [ENCRYPTED: W1BSSU9SSVRZOiBVUkdFTlRdCltFbWFpbCAtIFN1YmplY3Q6IE9PUCBGaW5hbCBQcm9qZWN0XQ...]
----------------------------------------------------------------------
[Sync Dispatch] Sent immediately to dinhkore@gmail.com -> [ENCRYPTED: ...]
[SystemLogger] SUCCESS | strategy=Sync | id=... | to=dinhkore@gmail.com
[MetricsTracker] Updated -> Dispatched: 1 | Success: 1 | Failed: 0
[OK] Scenario 1 (Happy Path E2E) completed with all assertions passed.

----------------------------------------------------------------------
>>> SCENARIO 2: Validation & Error Handling (Rejecting Invalid Input)
----------------------------------------------------------------------
[SystemLogger] FAILED | id=... | to=invalid-email-address | reason=Invalid email address: invalid-email-address
[MetricsTracker] Updated -> Dispatched: 1 | Success: 0 | Failed: 1
[SystemLogger] FAILED | id=... | to=12345 | reason=Invalid phone number: 12345
[MetricsTracker] Updated -> Dispatched: 2 | Success: 0 | Failed: 2
[SystemLogger] FAILED | id=... | to=valid@gmail.com | reason=No dispatch strategy is set.
[OK] Scenario 2 (Validation & Error Handling) completed with all assertions passed.

----------------------------------------------------------------------
>>> SCENARIO 3: Polymorphism & Runtime Strategy Swap
----------------------------------------------------------------------
-> Step 3.1: Using SyncDispatchStrategy (Immediate transmission):
[Sync Dispatch] Sent immediately to 0912345678 -> [SMS] (Length: 33 chars) Message: Your verification code is 889900.
[SystemLogger] SUCCESS | strategy=Sync | id=... | to=0912345678

-> Step 3.2: Swapping to AsyncDispatchStrategy (Queue-based transmission):
[Async Dispatch] Queued notification [ID: ...] for 0987654321 (Queue depth: 1)
[SystemLogger] SUCCESS | strategy=Async | id=... | to=0987654321
[OK] Scenario 3 (Polymorphism & Strategy Swap) completed with all assertions passed.

----------------------------------------------------------------------
>>> SCENARIO 4: Open/Closed Principle (OCP Proof - Adding PushNotification)
----------------------------------------------------------------------
[Sync Dispatch] Sent immediately to device_token_abc12345 -> [PRIORITY: HIGH] | [Push - Title: Server Maintenance] | Device: device_token_abc12345 | Payload: System will undergo scheduled maintenance at midnight.
[SystemLogger] SUCCESS | strategy=Sync | id=... | to=device_token_abc12345
[MetricsTracker] Updated -> Dispatched: 1 | Success: 1 | Failed: 0
[OCP VERIFIED] Added PushNotification & PushNotificationCreator with ZERO edits to existing classes.
[OK] Scenario 4 completed with all assertions passed.

>>> ALL DEMO SCENARIOS & ASSERTIONS PASSED SUCCESSFULLY (100%) <<<
```

---

## 7. Minh Chứng Kiểm Thử Tích Hợp (Test Scenarios)
Lớp `Main.java` tự động chạy và kiểm chứng qua assertions (100% pass) cho 4 kịch bản:
- **Scenario 1 (Happy Path E2E):** Khởi tạo bằng Factory Method $\rightarrow$ Lồng ghép Decorators (Priority + Encrypted) $\rightarrow$ Điều phối gửi qua Sync Strategy $\rightarrow$ Đa Observers (SystemLogger + MetricsTracker) ghi nhận thành công.
- **Scenario 2 (Validation & Error Handling):** Chặn đứng và ghi log trạng thái thất bại khi email sai cú pháp regex, SMS sai độ dài, hoặc khi Dispatcher thiếu Strategy.
- **Scenario 3 (Polymorphism & Strategy Swap):** Hoán đổi chiến lược gửi tức thời (Sync) sang hàng đợi (Async) tại runtime mà không sửa đổi Dispatcher.
- **Scenario 4 (OCP Proof):** Đăng ký và gửi `PushNotification` qua `PushNotificationCreator` hoàn toàn mới mà không cần chỉnh sửa bất kỳ class nào trước đó.
