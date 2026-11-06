# NHẬT KÝ SỬ DỤNG TRÍ TUỆ NHÂN TẠO (AI USAGE LOG)

* **Môn học:** Lập trình Hướng đối tượng (OOP) — HK1 2026-2027
* **Giảng viên hướng dẫn (GVHD):** Thầy Huỳnh Xuân Phụng
* **Nhóm sinh viên thực hiện:**
  1. **Hàn Minh Đính** — MSSV: `22110021` (Thành viên A)
  2. **Trần Đức Trung** — MSSV: `22110084` (Thành viên B)
* **Kho lưu trữ GitHub (Repository):** [https://github.com/DinhKore/OOP-Mini-Design-Pattern-Showcase-Application](https://github.com/DinhKore/OOP-Mini-Design-Pattern-Showcase-Application)

---

## 1. NGUYÊN TẮC CỐT LÕI VỀ SỬ DỤNG AI (CORE ETHICAL POLICY)
Tuân thủ quy định tại [`CONTRIBUTING.md`](file:///d:/University/OOP_Again/OOP-Mini-Design-Pattern-Showcase-Application/CONTRIBUTING.md) và định hướng của GVHD Huỳnh Xuân Phụng:

> ⚠️ **TUYÊN BỐ NGUYÊN TẮC:**  
> **"Công cụ AI chỉ đóng vai trò là Trợ lý kỹ thuật (Advisor) gợi ý cấu trúc, cú pháp mẫu (Boilerplate) và phương pháp luận; Sinh viên là người trực tiếp ra quyết định thiết kế kiến trúc, phản biện loại bỏ các Code Smells do AI tạo ra, trực tiếp gõ mã nguồn, cấu trúc thư mục, chạy kiểm thử trên Terminal và chịu trách nhiệm 100% khi bảo vệ trực tiếp (Live-Defense)."**

---

## 2. PHÂN ĐỊNH CHI TIẾT THEO TỪNG THÀNH VIÊN

---

### PHẦN I: THÀNH VIÊN A — HÀN MINH ĐÍNH (MSSV: 22110021)
* **Phạm vi phụ trách:** Creational Pattern (Factory Method), Structural Pattern (Decorator), Tái cấu trúc Package & Minh chứng OCP.
* **Nhánh Git ban đầu:** `feature/han-minh-dinh-factory-decorator`
* **Công cụ AI sử dụng:** Google Gemini 1.5 Pro / GitHub Copilot

#### Task 1: Triển khai Creational Pattern (Khởi tạo thông báo)
* **AI hỗ trợ:**
  * Gợi ý khung mã nguồn `NotificationFactory.java` sử dụng khối lệnh `switch-case` và tham số `extraData` để phân nhánh tạo Email và SMS.
  * Gợi ý cập nhật hàm `Main.java` thay lệnh gọi `new` trực tiếp bằng hàm gọi Factory.
* **Hàn Minh Đính trực tiếp làm & Phản biện thiết kế (Human Decision):**
  * Tự tạo file `NotificationFactory.java`, gõ code và kiểm tra luồng chạy.
  * **Phát hiện Code Smell nghiêm trọng:** Đính nhận thấy đoạn code `switch-case` mà AI gợi ý thực chất là **Simple Factory** chứ không phải **GoF Factory Method chuẩn**. Nếu thêm kênh mới (như Push), lập trình viên buộc phải mở file cũ ra để sửa `case`, vi phạm trực tiếp nguyên lý **Open/Closed (OCP)**. Ngoài ra tham số `extraData` mang tính chắp vá (Primitive Obsession).
  * **Hành động tái cấu trúc:** Đính quyết định bác bỏ Simple Factory và tái cấu trúc sang **GoF Factory Method chuẩn**: Tạo abstract class `NotificationCreator` và các Creator con (`EmailNotificationCreator`, `SMSNotificationCreator`), kết hợp `NotificationFactoryRegistry` để đăng ký động tại runtime mà không cần sửa code cũ.

#### Task 2: Triển khai Structural Pattern (Decorator Pattern)
* **AI hỗ trợ:**
  * Hướng dẫn thiết kế khung cấu trúc Decorator (lớp trừu tượng `NotificationDecorator` kế thừa `Notification` và lớp `EncryptedDecorator` mã hóa Base64).
* **Hàn Minh Đính trực tiếp làm & Khắc phục lỗi (Human Decision):**
  * Tự tạo các file `NotificationDecorator.java`, `EncryptedDecorator.java`.
  * **Phát hiện lỗi Ghost State:** AI gợi ý constructor `NotificationDecorator` gọi `super(notification.getRecipient(), notification.getMessage())`. Đính nhận thấy hàm `super` này sẽ tự động sinh thêm 1 UUID rác và Timestamp rác không bao giờ dùng tới. Đính đã can thiệp vào `Notification.java` để bổ sung constructor không tham số `#Notification()` dành riêng cho Decorator nhằm triệt tiêu hoàn toàn Ghost State.
  * **Sửa lỗi logic thêm đối tượng trong Main:** Phát hiện lỗi thêm nhầm đối tượng email gốc thay vì đối tượng đã bọc `encryptedEmail`.
  * **Bổ sung PriorityDecorator (Chaining):** Tự tay thiết kế thêm `PriorityDecorator` để chứng minh khả năng **Decorator Chaining** (lồng ghép nhiều tầng trang trí), giải quyết triệt để vấn đề "bùng nổ lớp con" (Subclass Explosion).

#### Task 3: Chuẩn hóa Package `src/` & Minh chứng OCP
* **AI hỗ trợ:** Gợi ý cú pháp định nghĩa package trong Java.
* **Hàn Minh Đính trực tiếp làm (Human Decision):**
  * Tự tay dọn sạch toàn bộ các file `.java` nằm rải rác ngoài root và thư mục `class/` cũ.
  * Phân chia lại 18 class/interface vào 6 package chuyên biệt trong `src/`: `model`, `factory`, `decorator`, `strategy`, `observer`, `dispatcher`.
  * Trực tiếp viết `PushNotification.java` và `PushNotificationCreator.java` để minh chứng nguyên lý OCP: Thêm tính năng mới 100% bằng cách thêm file mới, không sửa 1 dòng code cũ nào.
  * Biên dịch qua Terminal `javac` và xác nhận chạy thành công 100%.

---

### PHẦN II: THÀNH VIÊN B — TRẦN ĐỨC TRUNG (MSSV: 22110084)
* **Phạm vi phụ trách:** Behavioral Pattern 1 (Strategy), Behavioral Pattern 2 (Observer), Validation & Luồng kiểm thử Dispatcher.
* **Nhánh Git ban đầu:** `feature/tran-duc-trung-strategy-observer`
* **Công cụ AI sử dụng:** Cursor IDE (Model: Grok 4.7 / Claude 3.5 Sonnet)

#### Task 1: Triển khai Strategy Pattern (Gửi đồng bộ & Bất đồng bộ)
* **AI hỗ trợ:**
  * Gợi ý khung mã nguồn interface `IDispatchStrategy` và 2 lớp `SyncDispatchStrategy`, `AsyncDispatchStrategy`.
* **Trần Đức Trung trực tiếp làm & Quyết định kỹ thuật (Human Decision):**
  * **Từ chối giải pháp đa luồng của AI:** AI ban đầu gợi ý dùng `Thread` hoặc `ExecutorService` cho `AsyncDispatchStrategy`. Trung đã kiên quyết từ chối vì mục tiêu môn học là thể hiện rõ nét hành vi thuật toán của Strategy Pattern; việc dùng đa luồng sẽ gây race-condition và làm kết quả assert kiểm thử bị bất định. Trung yêu cầu chuyển sang mô phỏng hàng đợi nội bộ `List<Notification> queue` để dễ giải trình Call Stack khi vấn đáp.
  * **Đảm bảo tính Đóng gói (Encapsulation):** Trung bổ sung `private final` cho trường `queue` và cung cấp getter trả về `Collections.unmodifiableList(queue)` để bảo vệ dữ liệu khỏi bị can thiệp từ bên ngoài.

#### Task 2: Triển khai Observer Pattern & Validation
* **AI hỗ trợ:**
  * Sinh mã nguồn khung cho `NotificationObserver`, `SystemLogger` và các hàm `subscribe`, `notifySuccess`, `notifyFailure` trong `NotificationDispatcher`.
  * Gợi ý biểu thức Regex kiểm tra email và số điện thoại Việt Nam (`^0\\d{9}$`).
* **Trần Đức Trung trực tiếp làm & Thiết kế kiến trúc (Human Decision):**
  * **Thiết kế luồng Fail-fast Validation:** Đặt phương thức trừu tượng `validateDelivery()` vào `Notification` và gọi nó trước khi gọi `strategy.dispatch()`. Nếu dữ liệu sai định dạng (ví dụ email không có `@`), Dispatcher lập tức bắt `IllegalArgumentException` và kích hoạt `notifyFailure()`, không để tin nhắn lỗi lọt vào Strategy.
  * **Đóng gói danh sách log:** Cung cấp `Collections.unmodifiableList(logs)` để phục vụ việc kiểm thử assertion an toàn.
  * **Bổ sung MetricsTracker:** Thiết kế thêm Observer thứ hai là `MetricsTracker` để thể hiện trọn vẹn mô hình **1 Publisher - Đa Subscribers** (Logger lưu vết, Metrics đếm số lượng), đảm bảo nguyên lý **Separation of Concerns**.

#### Task 3: Xây dựng Kịch bản Kiểm thử & Assertions trong Main
* **AI hỗ trợ:** Gợi ý cấu trúc hàm assert kiểm tra chuỗi `contains`.
* **Trần Đức Trung trực tiếp làm (Human Decision):**
  * Thiết lập 3 ca kiểm thử ban đầu:
    1. Gửi Email hợp lệ qua `SyncDispatchStrategy` $\rightarrow$ Kỳ vọng `SUCCESS | strategy=Sync`.
    2. Gửi SMS hợp lệ qua `AsyncDispatchStrategy` $\rightarrow$ Kỳ vọng `SUCCESS | strategy=Async`.
    3. Gửi Email sai định dạng $\rightarrow$ Kỳ vọng `FAILED | Invalid email address`.
  * Trực tiếp chạy Terminal kiểm tra và xác nhận mọi assertion đều pass.

---

## 3. TỔNG HỢP SO SÁNH: AI ĐÓNG GÓP VS SINH VIÊN QUYẾT ĐỊNH

| Nội dung | Vai trò của AI (Trợ lý) | Sinh viên trực tiếp thẩm định, phản biện & thực hiện |
| :--- | :--- | :--- |
| **Khởi tạo đối tượng (Creational)** | Đề xuất Simple Factory dùng `switch-case`. | **Đính phát hiện vi phạm OCP**, bác bỏ và tái cấu trúc sang **GoF Factory Method + Registry**. |
| **Cấu trúc Decorator (Structural)** | Đề xuất kế thừa gọi `super(recipient, message)`. | **Đính phát hiện lỗi Ghost State**, tự tạo constructor rỗng `#Notification()`, bổ sung `PriorityDecorator` và Decorator Chaining. |
| **Chiến lược gửi tin (Strategy)** | Đề xuất đa luồng Thread/ExecutorService phức tạp. | **Trung từ chối**, chọn hàng đợi đơn định (in-memory queue) và `Collections.unmodifiableList` để call stack rõ ràng khi vấn đáp. |
| **Lắng nghe sự kiện (Observer)** | Gợi ý 1 Observer duy nhất là Logger. | **Trung bổ sung MetricsTracker** để tạo kiến trúc đa Subscriber độc lập (SoC). |
| **Cấu trúc thư mục** | Gợi ý flat file ở root. | **Đính dọn sạch root**, phân chia thành 6 package chuyên nghiệp trong `src/`. |
| **Chạy thực tế & Kiểm thử** | Gợi ý assert cơ bản. | **Cả hai cùng tích hợp 4 kịch bản E2E toàn diện**, tự gõ lệnh biên dịch và xác thực trên Terminal. |

---

## 4. CAM KẾT CHÍNH THỨC CỦA NHÓM SINH VIÊN

Nhóm sinh viên chúng em xin cam kết trước Giảng viên hướng dẫn:
1. Toàn bộ nội dung trong nhật ký này được ghi nhận trung thực 100% theo diễn biến thực tế của dự án.
2. Công cụ AI chỉ được dùng như một phương tiện tham khảo và sinh mã boilerplate; **hai thành viên chúng em hiểu rõ 100% kiến trúc, mối quan hệ giữa các lớp và tự tin trả lời bất kỳ câu hỏi vấn đáp trực tiếp nào của Thầy.**

*Thành phố Hồ Chí Minh, ngày 06 tháng 11 năm 2026*

**Các thành viên ký xác nhận:**

| Hàn Minh Đính | Trần Đức Trung |
| :---: | :---: |
| *(Đã ký)* | *(Đã ký)* |
| **MSSV: 22110021** | **MSSV: 22110084** |
