# Mini Design-Pattern Showcase: Notification Dispatcher System

## 1. Giới thiệu (Overview)
Đây là dự án cuối kì môn Lập trình Hướng đối tượng (OOP). Ứng dụng mô phỏng một **Hệ thống điều phối thông báo (Notification Dispatcher)**, cho phép gửi thông báo qua nhiều kênh (Email, SMS) với các tính năng mã hóa, lên lịch và ghi log.
Mục đích chính của dự án là chứng minh việc áp dụng chính xác các nguyên lý OOP (Encapsulation, Polymorphism, Abstraction) và 4 Design Patterns cổ điển để giải quyết các vấn đề thiết kế thực tế (Code Smells).

## 2. Team Members
- Thành viên 1: [Tên SV 1] - [MSSV 1] - GitHub: [@DinhKore]
- Thành viên 2: [Tên SV 2] - [MSSV 2] - GitHub: [@User2]

## 3. Design Patterns Áp dụng (Tối thiểu 4)
1. **Creational - Factory Method:** `NotificationFactory` để đóng gói logic khởi tạo các loại thông báo phức tạp.
2. **Structural - Decorator:** `EncryptedNotification`, `UrgentNotification` để thêm tính năng động cho thông báo mà không cần tạo quá nhiều subclass.
3. **Behavioral 1 - Strategy:** `IDispatchStrategy` (Gửi ngay, Gửi vào hàng đợi) để thay đổi thuật toán gửi tại runtime.
4. **Behavioral 2 - Observer:** `EventLogger`, `AnalyticsTracker` đăng ký lắng nghe các sự kiện gửi thông báo (Thành công/Thất bại).

## 4. Hướng dẫn cài đặt và chạy (Build/Run Instructions)
- Ngôn ngữ: C# (.NET 8.0) hoặc Java (JDK 21) *(Team tự điền)*
- IDE: Visual Studio / IntelliJ
- Cú pháp chạy: `dotnet run` hoặc `java Main`
- Hướng dẫn chạy Test: *(Điền lệnh chạy Unit test ở đây)*

## 5. Tài liệu báo cáo (Deliverables)
Các tài liệu giải trình (Self-Explanation), UML Diagram và Nhật ký sử dụng AI (AI Usage Log) được lưu trữ tại thư mục `/docs`.