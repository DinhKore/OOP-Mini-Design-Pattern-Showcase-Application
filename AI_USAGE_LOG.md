# NHẬT KÝ SỬ DỤNG TRÍ TUỆ NHÂN TẠO (AI USAGE LOG)

* **Môn học:** Lập trình Hướng đối tượng (OOP) — HK1 2026-2027
* **Giảng viên hướng dẫn (GVHD):** Thầy Huỳnh Xuân Phụng
* **Nhóm sinh viên thực hiện:**
  1. **Hàn Minh Đính** — MSSV: `22110021` (Thành viên A)
  2. **Trần Đức Trung** — MSSV: `22110084` (Thành viên B)
* **Kho lưu trữ GitHub (Repository):** [https://github.com/DinhKore/OOP-Mini-Design-Pattern-Showcase-Application](https://github.com/DinhKore/OOP-Mini-Design-Pattern-Showcase-Application)

---

## 1. NGUYÊN TẮC SỬ DỤNG AI CỦA NHÓM (AI USAGE POLICY)
Tuân thủ nghiêm ngặt quy định tại [`CONTRIBUTING.md`](file:///d:/University/OOP_Again/OOP-Mini-Design-Pattern-Showcase-Application/CONTRIBUTING.md) và hướng dẫn của GVHD Huỳnh Xuân Phụng:
1. **Tính minh bạch tuyệt đối (Transparency):** Toàn bộ các công cụ AI, câu lệnh điều khiển (Prompts), đoạn mã boilerplate do AI sinh ra và các can thiệp/phản biện của sinh viên đều được ghi chép trung thực.
2. **Thẩm định & Trách nhiệm cá nhân (Accountability):** Sinh viên chịu trách nhiệm 100% về kiến trúc và chất lượng mã nguồn. Sinh viên không copy-paste mù quáng mà luôn phản biện, phát hiện code smell của AI (ví dụ: AI ban đầu sinh Simple Factory dùng switch-case vi phạm OCP) và chủ động tái cấu trúc theo chuẩn OOP.
3. **Sẵn sàng vấn đáp (Oral Defense Readiness):** Cả hai thành viên đều nắm vững call stack, vai trò của từng class/method và sẵn sàng giải trình trực tiếp trước GVHD mà không phụ thuộc vào AI.

---

## 2. NHẬT KÝ CHI TIẾT THEO TỪNG GIAI ĐOẠN PHÁT TRIỂN

### Giai đoạn 1: Khởi tạo Nền tảng Cơ sở (Core Foundation)
* **Người thực hiện:** Hàn Minh Đính & Trần Đức Trung (Pair-programming)
* **Công cụ AI:** Google Gemini (GPT-3.5 / Gemini Flash)
* **Prompt chính:**
  > *"Tôi cần làm đồ án Mini Design-Pattern Showcase môn OOP (đề tài Notification Dispatcher). Hãy phân tích và gợi ý một cấu trúc tiêu chuẩn bằng C# hoặc Java thể hiện rõ các tính chất OOP (Encapsulation, Abstraction, Polymorphism)."*
* **AI tạo ra:**
  * Khung mã nguồn ban đầu của lớp cơ sở `Notification` và các lớp con `EmailNotification`, `SMSNotification` bằng C#.
  * Gợi ý kiểm tra hợp lệ dữ liệu trong constructor (Fail-fast validation).
* **Can thiệp & Sửa đổi của sinh viên:**
  * **Quyết định chuyển đổi công nghệ:** Nhóm quyết định không dùng C# mà chuyển toàn bộ sang **Java (JDK 21 LTS)** để phù hợp với chuyên môn và định hướng của hai thành viên.
  * Tự tay loại bỏ toàn bộ file cấu hình C#, việt hóa và xây dựng `PROJECT_TRACKING.md`, `CONTRIBUTING.md` và `AI_CONTEXT.md` để ràng buộc kỷ luật làm việc nhóm.
  * Tự tay viết constructor bảo vệ tính đóng gói với thuộc tính `private final`.

---

### Giai đoạn 2: Phân chia Nhánh Triển khai Design Patterns

#### 2.1. Nhánh Thành viên A: Hàn Minh Đính (`feature/han-minh-dinh-factory-decorator`)
* **Công cụ AI:** Google Gemini 1.5 Pro / GitHub Copilot
* **Prompt chính:**
  > *"Tôi đang làm đồ án Notification Dispatcher bằng Java. Hãy gợi ý mã nguồn cho:  
  > 1. Factory Pattern để khởi tạo Email và SMS thay vì dùng từ khóa new trực tiếp.  
  > 2. Decorator Pattern gồm một abstract NotificationDecorator kế thừa Notification và một EncryptedDecorator mã hóa Base64."*
* **AI tạo ra:**
  * Một lớp `NotificationFactory` với hàm static dùng `switch (type)`.
  * Lớp `NotificationDecorator` kế thừa `Notification` bằng cách gọi `super(notification.getRecipient(), notification.getMessage())`.
* **Can thiệp & Phản biện của Hàn Minh Đính (Phát hiện lỗi kiến trúc):**
  * **Phát hiện Code Smell 1 (Ghost State):** Đính nhận thấy khi Decorator gọi `super(...)`, constructor của `Notification` sẽ tự động cấp phát một `UUID` và `LocalDateTime` hoàn toàn mới $\rightarrow$ Vô ích và lãng phí tài nguyên. Đính đã can thiệp tạo thêm constructor rỗng `#Notification()` dành riêng cho Decorator.
  * **Phát hiện Code Smell 2 (Vi phạm OCP ở Factory):** Đính nhận thấy đoạn code `switch-case` mà AI sinh ra là **Simple Factory** chứ chưa phải **GoF Factory Method**, nếu sau này thêm kênh mới sẽ phải sửa file cũ. Đính đã đánh dấu task này để tái cấu trúc ở Giai đoạn 4.
  * Tự tay sửa lỗi logic trong hàm `Main`: Ban đầu truyền nhầm object email gốc, đã sửa lại thành truyền đúng đối tượng đã bọc `encryptedEmail`.

#### 2.2. Nhánh Thành viên B: Trần Đức Trung (`feature/tran-duc-trung-strategy-observer`)
* **Công cụ AI:** Cursor (Model: Grok 4.7 / Claude 3.5 Sonnet)
* **Prompt chính:**
  > *"Xây dựng Strategy và Observer cho Notification Dispatcher bằng Java:  
  > 1. Giao diện IDispatchStrategy và 2 chiến lược: Sync (gửi ngay) và Async (hàng đợi không dùng thread).  
  > 2. Giao diện NotificationObserver và SystemLogger để nhận tín hiệu onDispatchSuccess và onDispatchFailure.  
  > 3. NotificationDispatcher kết nối hai bên và tự validate dữ liệu trước khi gửi."*
* **AI tạo ra:**
  * Bộ khung interface `IDispatchStrategy` và `NotificationObserver`.
  * Bộ điều phối `NotificationDispatcher` và các assertions kiểm thử.
* **Can thiệp & Quyết định kỹ thuật của Trần Đức Trung:**
  * **Quyết định tính tất định (Determinism):** AI ban đầu đề xuất dùng `Thread` hoặc `ExecutorService` cho `AsyncDispatchStrategy`. Trung đã từ chối và yêu cầu chuyển sang mô hình hàng đợi nội bộ (`List<Notification> queue`) để đảm bảo tính tất định khi assert kiểm thử và dễ giải thích call stack khi vấn đáp với Thầy Phụng.
  * **Bảo vệ tính Đóng gói (Encapsulation):** Trung bổ sung `Collections.unmodifiableList()` cho cả `getQueue()` và `getLogs()` để tránh bị sửa đổi từ bên ngoài.
  * Thêm phương thức đa hình `validateDelivery()` vào từng kênh để Dispatcher không phải kiểm tra `instanceof`.

---

### Giai đoạn 3: Tích hợp (Merge) & Kiểm thử Liên thông
* **Người thực hiện:** Hàn Minh Đính & Trần Đức Trung
* **Công việc thực tế:**
  * Tiến hành gộp nhánh `feature/han-minh-dinh-factory-decorator` và `feature/tran-duc-trung-strategy-observer` vào `main` qua commit `e3439b1`.
  * Xử lý xung đột logic: Bổ sung ủy quyền phương thức `validateDelivery()` bên trong `NotificationDecorator` để đảm bảo decorator tương thích với lớp cơ sở `Notification`.
  * Thiết lập kịch bản kiểm thử tích hợp liên thông 4 pattern trong `Main.java`.

---

### Giai đoạn 4: Tái cấu trúc Toàn diện & Minh chứng OCP (Chất Lượng Cao)
* **Người thực hiện:** Hàn Minh Đính & Trần Đức Trung
* **Mục tiêu:** Nâng tầm đồ án, khắc phục toàn bộ các code smell do AI sinh ra ban đầu để đạt chuẩn chấm điểm khắt khe nhất của Thầy Phụng.
* **Các cải tiến kiến trúc cốt lõi do sinh viên chỉ đạo:**
  1. **Triệt tiêu Simple Factory $\rightarrow$ GoF Factory Method + Registry:**
     * Tạo lớp trừu tượng `NotificationCreator` với phương thức `createNotification(...)`.
     * Tách thành các lớp Creator chuyên trách: `EmailNotificationCreator`, `SMSNotificationCreator`, `PushNotificationCreator`.
     * Tự tay viết thêm thuật toán chuẩn hóa số điện thoại di động Việt Nam (chuyển đổi `+84` thành đầu số `0`) trong `SMSNotificationCreator`.
     * Xây dựng `NotificationFactoryRegistry` hỗ trợ đăng ký động tại runtime $\rightarrow$ **Giải quyết triệt để OCP**.
  2. **Giải quyết triệt để Subclass Explosion $\rightarrow$ Decorator Chaining:**
     * Tạo thêm `PriorityDecorator` để minh chứng khả năng lồng ghép chuỗi: `EncryptedDecorator(PriorityDecorator(baseNotification))`.
  3. **Mô hình Đa Subscribers:**
     * Bổ sung thêm `MetricsTracker` (Observer 2) để chứng minh rõ nét tính phân định trách nhiệm (**Separation of Concerns**).
  4. **Minh chứng OCP cụ thể (OCP Proof):**
     * Tạo mới `PushNotification.java` và `PushNotificationCreator.java`. Hệ thống tiếp nhận và vận hành trơn tru mà **không cần sửa đổi 1 dòng code nào của các class đã có**.
  5. **Chuẩn hóa thư mục Java:**
     * Gom toàn bộ 18 class/interface vào cấu trúc package chuẩn trong `src/` (`model`, `factory`, `decorator`, `strategy`, `observer`, `dispatcher`).
     * Viết tài liệu hướng dẫn Build & Run đa nền tảng (CMD, PowerShell, VS Code, IntelliJ) trong `README.md`.

---

## 3. BẢNG TỔNG KẾT ĐÓNG GÓP & TRÁCH NHIỆM

| Tiêu chí | Công cụ AI đóng góp | Sinh viên thẩm định & quyết định |
| :--- | :--- | :--- |
| **Mã nguồn cơ sở (Boilerplate)** | Gợi ý khung cú pháp ban đầu cho các interface và class. | Tự tay gõ lại, tinh chỉnh tên biến, đóng gói `private final`, thêm constructor rỗng loại bỏ Ghost state. |
| **Kiến trúc Design Pattern** | Ban đầu đề xuất Simple Factory (switch-case) và Thread đa luồng phức tạp. | **Bác bỏ giải pháp của AI**: Chuyển sang **GoF Factory Method chuẩn kết hợp Registry (OCP)** và hàng đợi Queue tất định phục vụ vấn đáp. |
| **Kiểm thử & Khắc phục lỗi** | Gợi ý các lệnh assert cơ bản. | Thiết lập 4 Scenarios kiểm thử toàn diện, kiểm chứng bắt lỗi cú pháp Regex và hoán đổi Strategy lúc chạy. |
| **Tài liệu & Giải trình** | Gợi ý cấu trúc báo cáo và sơ đồ Mermaid. | Tự tay phân tích chi tiết từng Class/Method cho mục *Self-Explanation*, xây dựng ma trận test evidence và chuẩn bị kịch bản vấn đáp trực tiếp. |

---

## 4. CAM KẾT CỦA SINH VIÊN (STUDENT ATTESTATION)
Nhóm sinh viên chúng em xin cam đoan:
* Mọi thông tin ghi trong nhật ký này là trung thực 100%.
* Chúng em hiểu rõ tường tận từng dòng mã nguồn, bản chất của từng Design Pattern và sẵn sàng giải trình trực tiếp trước GVHD Huỳnh Xuân Phụng tại buổi bảo vệ đồ án (Live-Defense).

**Sinh viên thực hiện:**
* **Hàn Minh Đính** (MSSV: `22110021`)
* **Trần Đức Trung** (MSSV: `22110084`)
