# Mini Design-Pattern Showcase: Notification Dispatcher System

## 1. Tổng Quan (Overview)
Đây là dự án ứng dụng thực tiễn các nguyên lý Lập trình Hướng đối tượng (OOP) và Design Patterns. 
Hệ thống mô phỏng một **Bộ điều phối thông báo (Notification Dispatcher)**, chịu trách nhiệm xử lý, định dạng và phân phối các loại thông báo khác nhau (Email, SMS, Push) một cách linh hoạt, dễ mở rộng và bảo trì.

## 2. Mục Tiêu Kỹ Thuật (Technical Goals)
Dự án được thiết kế để giải quyết các "code smells" (thiết kế kém) thông qua việc áp dụng:
*   **Nguyên lý OOP cốt lõi:** Encapsulation (Đóng gói), Polymorphism (Đa hình), Abstraction (Trừu tượng).
*   **Nguyên lý thiết kế:** Open/Closed Principle (OCP) và Separation of Concerns (SoC).

## 3. Kiến Trúc & Design Patterns
Hệ thống triển khai 4 Design Patterns cổ điển để xử lý các bài toán cụ thể:
1.  **Creational - Factory Method:** 
    *   *Vấn đề:* Tránh việc khởi tạo object bằng từ khóa `new` tràn lan gây dính kết (tight-coupling).
    *   *Giải pháp:* Quản lý vòng đời và logic khởi tạo phức tạp của các loại thông báo (`Email`, `SMS`).
2.  **Structural - Decorator:** 
    *   *Vấn đề:* Hạn chế sự bùng nổ class con (subclass explosion) khi thêm tính năng.
    *   *Giải pháp:* Cho phép bọc thêm tính năng (Mã hóa, Gắn chữ ký) vào thông báo ngay tại runtime.
3.  **Behavioral - Strategy:** 
    *   *Vấn đề:* Loại bỏ các khối lệnh `if/else` hoặc `switch/case` khổng lồ khi chọn cách gửi.
    *   *Giải pháp:* Đóng gói các thuật toán gửi (Gửi ngay, Gửi chậm) thành các Strategy có thể hoán đổi.
4.  **Behavioral - Observer:** 
    *   *Vấn đề:* Các hệ thống cần biết trạng thái gửi tin mà không làm ảnh hưởng đến class chính.
    *   *Giải pháp:* Thiết lập cơ chế Publisher-Subscriber để ghi log và theo dõi sự kiện gửi thành công/thất bại.

## 4. Công Nghệ Sử Dụng (Tech Stack)
*   **Ngôn ngữ:** C# (.NET 8.0+)
*   **IDE:** Visual Studio Code
*   **Kiểm thử:** Unit Tests nội bộ (Console assertions).

## 5. Sản Phẩm Chuyển Giao (Deliverables)
1. Source code hoàn chỉnh kèm hướng dẫn Build/Run.
2. Sơ đồ lớp (UML Class Diagram) của toàn bộ hệ thống.
3. Tài liệu Tự giải trình (Self-Explanation) cho từng Pattern và nguyên lý OOP.
4. Minh chứng kiểm thử (Test cases).
5. Nhật ký sử dụng AI (AI Usage Log).