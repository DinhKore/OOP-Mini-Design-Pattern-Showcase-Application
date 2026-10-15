# Workflow & Task Assignments

Dự án được chia thành các Giai đoạn (Phases) nối tiếp nhau. Hoàn thành `[x]` từng task để sang giai đoạn tiếp theo.

## Giai đoạn 1: Nền Tảng (Core Foundation)
*Mục tiêu: Xây dựng bộ khung cơ sở vững chắc. Cả hai thành viên cùng làm (Pair-programming) hoặc review chéo trực tiếp.*

- [ ] Khởi tạo dự án C# Console (`dotnet new console`).
- [ ] Thiết kế Abstract Base Class `Notification` (Áp dụng Encapsulation, định nghĩa Abstraction).
- [ ] Thiết kế các Subclasses cơ bản: `EmailNotification`, `SMSNotification` (Áp dụng Polymorphism).
- [ ] Viết test case (Happy path) để khởi tạo và in ra thông tin Base classes.
- [ ] **Review:** Đảm bảo không có trường dữ liệu nào là `public`, mọi truy cập qua Properties/Methods.

## Giai đoạn 2: Phân Chia Triển Khai Design Patterns
*Mục tiêu: Chia đôi luồng công việc để tối ưu thời gian. Mỗi người chịu trách nhiệm code và tự giải trình cho Pattern của mình.*

**Thành viên A ([Tên Thành viên A]):**
- [ ] **Factory Method Pattern:** Cài đặt `NotificationFactory` để khởi tạo Email/SMS.
- [ ] **Decorator Pattern:** Cài đặt abstract `NotificationDecorator` và `EncryptedDecorator`.
- [ ] Viết Tự giải trình (Self-Explanation): Vấn đề trước khi dùng (Code smell) & Giải pháp áp dụng OOP.

**Thành viên B ([Tên Thành viên B]):**
- [ ] **Strategy Pattern:** Cài đặt giao diện `IDispatchStrategy` và các concrete strategy (VD: Gửi đồng bộ, Gửi bất đồng bộ).
- [ ] **Observer Pattern:** Cài đặt cơ chế Event/Observer để lắng nghe trạng thái gửi tin (VD: `SystemLogger`).
- [ ] Viết Tự giải trình (Self-Explanation): Vấn đề trước khi dùng (Code smell) & Giải pháp áp dụng OOP.

## Giai đoạn 3: Tích Hợp & Minh Chứng OCP
*Mục tiêu: Gộp code, xử lý tương tác giữa các patterns và chứng minh hệ thống dễ mở rộng.*

- [ ] Gộp toàn bộ mã nguồn (Merge code). Cả hai cùng kiểm tra xung đột (Conflicts).
- [ ] Cài đặt kịch bản luồng chính (End-to-End Test): Tạo thông báo bằng Factory -> Bọc bằng Decorator -> Gửi bằng Strategy -> Ghi log bằng Observer.
- [ ] Cài đặt kịch bản xử lý lỗi (Error/Validation Test): Bắt lỗi khi người dùng nhập sai định dạng hoặc thiếu dữ liệu.
- [ ] **Chứng minh Open/Closed Principle:** Cùng nhau thêm 1 loại thông báo mới (VD: `PushNotification`) chỉ bằng cách thêm class, tuyệt đối KHÔNG sửa code cũ.

## Giai đoạn 4: Hoàn Thiện & Báo Cáo
*Mục tiêu: Dọn dẹp mã nguồn, xuất tài liệu và chuẩn bị bảo vệ.*

- [ ] Refactor mã nguồn (Đảm bảo Separation of Concerns - không có "God class").
- [ ] Vẽ UML Class Diagram hoàn chỉnh cho toàn hệ thống.
- [ ] Tổng hợp file Tự giải trình các nguyên lý (Encapsulation, Polymorphism, Abstraction, OCP, SoC).
- [ ] Tổng hợp file AI Usage Log từ lịch sử Commit Git.
- [ ] Review chéo mã nguồn để chuẩn bị Live-defense.