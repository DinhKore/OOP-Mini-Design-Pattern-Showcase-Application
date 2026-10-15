# NGỮ CẢNH DỰ ÁN DÀNH CHO AI (AI CONTEXT)

Bất cứ khi nào tôi mở phiên làm việc mới, hãy đọc các quy tắc sau trước khi sinh mã nguồn (code):

## 1. Ràng Buộc Kỹ Thuật
- **Ngôn ngữ:** C# (.NET 8.0).
- **Domain:** Notification Dispatcher System (Hệ thống điều phối thông báo).
- **Trọng tâm thiết kế:** Ưu tiên chất lượng cấu trúc OOP hơn là số lượng chức năng.

## 2. Yêu Cầu Mã Nguồn (Strict Rules)
1. **Encapsulation:** KHÔNG BAO GIỜ để `public` cho các fields. Luôn sử dụng `private` và quản lý bằng Properties (với `{ get; private set; }` nếu cần). Thêm validation vào constructor hoặc setter.
2. **Abstraction & Polymorphism:** Sử dụng `abstract class` hoặc `interface` làm nền tảng. Khi gọi phương thức, hãy dùng lời gọi đa hình.
3. **OCP & SoC:** Code phải tuân thủ Open/Closed Principle (Thêm tính năng bằng cách thêm class, không sửa class cũ) và Separation of Concerns (Phân tách mối quan tâm rõ ràng).

## 3. Yêu Cầu Giải Trình (Self-Explanation)
Khi tôi yêu cầu bạn code một Design Pattern bất kỳ, BẮT BUỘC bạn phải trả lời theo cấu trúc sau trước khi đưa code:
- **Vấn đề trước khi dùng Pattern (Code Smell):** Giải thích ngắn gọn nếu code theo kiểu thông thường thì sẽ tệ như thế nào.
- **Giải pháp áp dụng Pattern:** Pattern này dọn dẹp code smell đó ra sao và nó bám sát nguyên lý OOP nào.

*Nếu bạn đã nắm rõ bối cảnh này, hãy trả lời ngắn gọn "Đã hiểu ngữ cảnh C# Notification System" và chờ lệnh tiếp theo.*