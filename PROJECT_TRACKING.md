# Project Tracking & Task Assignments (2-Week Sprint)

Tiến độ được rút ngắn từ 4 tuần xuống 2 tuần. Yêu cầu hai thành viên cập nhật file này (`[x]`) mỗi khi hoàn thành task.

## Week 1: Core Architecture & Setup (Ngày 1 - Ngày 7)
**Mục tiêu:** Xây dựng khung OOP cơ bản và 2 patterns đầu tiên.

- [ ] **Ngày 1-2: Khởi tạo & Base Classes**
  - [ ] Thiết lập GitHub Repo, add collaborator.
  - [ ] Cài đặt môi trường (C# / Java). Tạo khung project.
  - [ ] Tạo Abstract Base Class `Notification` và các lớp con `EmailNotification`, `SMSNotification` (Thể hiện tính Abstraction & Inheritance).
  - *Assignee:* Cả hai cùng setup, Member 1 code base classes.

- [ ] **Ngày 3-4: Factory Method Pattern (Creational)**
  - [ ] Implement `NotificationFactory` để tạo thông báo.
  - [ ] Viết Tự giải trình (Self-Explanation): Vấn đề trước khi dùng Factory vs Sau khi dùng.
  - *Assignee:* Member 2.

- [ ] **Ngày 5-6: Decorator Pattern (Structural)**
  - [ ] Implement `NotificationDecorator` (Ví dụ: `EncryptedDecorator`).
  - [ ] Viết Test Case cơ bản (Happy path) cho việc khởi tạo và trang trí thông báo.
  - *Assignee:* Member 1.

- [ ] **Ngày 7: Review Week 1 & Merge**
  - [ ] Code review chéo. Đảm bảo tuân thủ Encapsulation (các fields phải private, dùng properties/getters).
  - *Assignee:* Cả hai.

## Week 2: Behavioral Patterns, Testing & Documentation (Ngày 8 - Ngày 14)
**Mục tiêu:** Hoàn thiện 2 patterns còn lại, viết Tests và Báo cáo.

- [ ] **Ngày 8-9: Strategy Pattern (Behavioral 1)**
  - [ ] Implement `IDispatchStrategy` (InstantDispatch, ScheduledDispatch).
  - [ ] Tích hợp vào hệ thống, chứng minh tính Đa hình (Polymorphism).
  - *Assignee:* Member 2.

- [ ] **Ngày 10-11: Observer Pattern (Behavioral 2) & Edge Cases**
  - [ ] Implement Publisher/Subscriber để theo dõi trạng thái gửi (Success/Fail).
  - [ ] Viết Test Case bắt lỗi (Ví dụ: Nội dung rỗng, thiếu địa chỉ nhận).
  - *Assignee:* Member 1.

- [ ] **Ngày 12: Chứng minh Open/Closed Principle (Quan trọng!)**
  - [ ] Thêm một loại thông báo mới (vd: `PushNotification`) hoặc Strategy mới mà **không sửa code cũ**.
  - [ ] Viết Test Case cho tính đa hình này.
  - *Assignee:* Cả hai cùng làm.

- [ ] **Ngày 13: Documentation & UML**
  - [ ] Vẽ UML Class Diagram hoàn chỉnh.
  - [ ] Hoàn thiện thư mục `/docs`: Chốt file Tự giải trình (Self-Explanation) cho 4 patterns.
  - [ ] Tổng hợp file `AI_USAGE_LOG.md`.

- [ ] **Ngày 14: Rehearsal (Chuẩn bị bảo vệ)**
  - [ ] Push code Final.
  - [ ] Hai người tự đặt câu hỏi và trả lời chéo về code để chuẩn bị cho buổi Live Defense.