# Quy Tắc Mã Nguồn & Sử Dụng AI

Mọi đóng góp vào dự án cần tuân thủ nghiêm ngặt các quy tắc sau để đảm bảo tính minh bạch và chất lượng kiến trúc.

## 1. Quy tắc Commit Git (Bắt buộc)
Mọi lần thay đổi mã nguồn phải sử dụng mẫu Commit đa dòng dưới đây (Dùng tính năng Commit trong VS Code GUI thay vì lệnh terminal để tránh lỗi format):

```text
[Loại]: [Tóm tắt công việc]

Chi tiết:
- (Các file đã sửa, logic đã thêm...)
- (Chỉ rõ nguyên lý OOP hoặc Pattern đang được áp dụng...)

AI thực hiện:
- (AI đã gợi ý giải pháp nào hoặc sinh ra đoạn code boilerplate nào?)
- (Ghi "Không sử dụng" nếu tự code 100%)

Người can thiệp:
- (Chỉ định rõ quyết định thiết kế của con người: ví dụ refactor lại tên biến, cấu trúc lại logic, ghép nối các class...)
```
*Các loại (Type):* `feat` (Tính năng), `fix` (Sửa lỗi), `docs` (Tài liệu), `refactor` (Tối ưu code), `test` (Kiểm thử).

## 2. Quy tắc sử dụng AI (AI Usage Policy)
- **Minh bạch:** Bất kỳ đoạn code cốt lõi nào do AI gợi ý phải được giải thích lại bằng comment của con người trong source code.
- **Thẩm định:** Người dùng chịu trách nhiệm 100% về code được push. Cấm copy-paste mã nguồn nếu không thể giải thích được luồng chạy (Call stack) và nguyên lý OOP áp dụng bên trong.
- Toàn bộ các câu lệnh điều khiển AI (Prompts) quan trọng liên quan đến kiến trúc phải được lưu trữ để kết xuất vào `AI_USAGE_LOG`.

## 3. Quy tắc Kiến trúc
- Không tạo "God class" (Class ôm đồm mọi chức năng).
- Phải áp dụng tính Đóng gói (Encapsulation) triệt để: Sử dụng `private` fields và `public properties` có logic kiểm duyệt (Validation).
- Các class cốt lõi phải dựa vào Abstraction (Interface/Abstract class) thay vì implementation cụ thể.