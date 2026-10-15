# Quy tắc Đóng góp và Sử dụng AI (Team Rules)

Dự án này là Public theo yêu cầu của Giảng viên. Mọi dấu vết làm việc phải minh bạch.

## 1. Quy tắc Commit Message (BẮT BUỘC)
Để theo dõi chính xác đóng góp của Người và AI, mọi commit message phải tuân thủ format sau:

`[Loại]: [Mô tả ngắn gọn] | Human: [Chi tiết con người làm] | AI: [Chi tiết AI hỗ trợ]`

**Các Loại (Loại):**
- `feat`: Thêm tính năng / Pattern mới
- `fix`: Sửa lỗi
- `docs`: Cập nhật tài liệu
- `test`: Thêm test case

**Ví dụ Commit Hợp lệ:**
- `feat: implement Factory Pattern cho Notification | Human: Thiết kế interface và luồng logic | AI: Generate boiler-plate code cho các class con.`
- `test: thêm test case check null email | Human: Xác định edge cases cần test | AI: Viết code xUnit/JUnit dựa trên mô tả.`
- `docs: cập nhật UML diagram | Human: Vẽ tay sơ đồ | AI: Chuyển đổi ý tưởng thành cú pháp Mermaid/PlantUML.`

**Lưu ý:** Không dùng lệnh `git commit -m "update code"`.

## 2. Quy tắc sử dụng AI (AI Usage Policy)
- **ĐƯỢC PHÉP:** Dùng AI để debug, tạo boilerplate code, giải thích lỗi, gợi ý tên biến, viết comment, viết test case dựa trên logic đã có.
- **KHÔNG ĐƯỢC PHÉP:** Yêu cầu AI "Viết toàn bộ file báo cáo" hoặc "Viết toàn bộ hệ thống". 
- Bạn **PHẢI** hiểu 100% từng dòng code được merge vào nhánh chính. Thầy sẽ hỏi ngẫu nhiên trong buổi Live Defense.
- Mọi prompt quan trọng dùng với AI phải được copy và dán vào file `/docs/AI_USAGE_LOG.md`.

## 3. Quy tắc Branching (Nhánh)
- Nhánh `main`: Chỉ chứa code đã hoàn chỉnh, chạy được và đã review.
- Tạo nhánh riêng cho mỗi pattern: `feature/factory-pattern`, `feature/decorator-pattern`.
- Tạo Pull Request (PR) và người còn lại phải Approve mới được merge vào `main`.