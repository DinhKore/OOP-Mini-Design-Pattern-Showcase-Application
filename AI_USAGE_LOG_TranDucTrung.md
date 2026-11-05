# Nhật ký AI — Trần Đức Trung

File này để Hàn Minh Đính và Trần Đức Trung đọc chung trước khi vấn đáp.
Nhánh: `feature/tran-duc-trung-strategy-observer`
Phần việc: Thành viên B — Strategy và Observer.
MSSV: điền trước khi nộp báo cáo tuần (thầy yêu cầu có họ tên và MSSV).

## Việc Trần Đức Trung tự làm

- Xác định hai thành viên: Hàn Minh Đính và Trần Đức Trung.
- Yêu cầu tách nhánh riêng, không sửa trực tiếp trên `main` của Đính.
- Yêu cầu có một file riêng ghi AI làm gì và bản thân làm gì, để gửi cho nhau đọc.
- Phần Factory Method và Decorator vẫn để Hàn Minh Đính làm trên nhánh của Đính.

## Việc AI làm

Công cụ: Cursor, model Grok 4.7. Người ra lệnh là Trần Đức Trung.

AI đã viết và chỉnh các file sau:

- `IDispatchStrategy.java`, `SyncDispatchStrategy.java`, `AsyncDispatchStrategy.java`
- `NotificationObserver.java`, `SystemLogger.java`, `NotificationDispatcher.java`
- Thêm `validateDelivery()` vào `Notification`, `EmailNotification`, `SMSNotification`
- `Main.java`: giữ demo giai đoạn 1, thêm demo gửi tin và assertion
- `PROJECT_TRACKING.md`: điền tên hai thành viên, tick phần của Trung
- File này

AI không push lên GitHub. AI không làm Factory và Decorator.

## Tự giải trình để vấn đáp

### Strategy

Vấn đề trước khi dùng: nếu `NotificationDispatcher` tự quyết định cách gửi bằng `if/else` (gửi ngay hay đưa vào hàng đợi), mỗi cách gửi mới đều phải sửa đúng class đó. Class vừa điều phối vừa chứa thuật toán.

Giải pháp: thuật toán gửi nằm trong `IDispatchStrategy`. `SyncDispatchStrategy` gửi ngay trong lời gọi. `AsyncDispatchStrategy` chỉ xếp hàng rồi trả về, không tạo thread, để demo chạy ổn định và dễ giải thích. Đổi cách gửi bằng `setStrategy(...)`, không sửa dispatcher.

### Observer

Vấn đề trước khi dùng: lệnh ghi log nằm trong hàm gửi. Muốn thêm người nghe (file log, màn hình, thống kê) thì phải sửa class gửi.

Giải pháp: `NotificationDispatcher` là publisher. `SystemLogger` là subscriber. Gửi xong hoặc gửi lỗi thì dispatcher gọi `onDispatchSuccess` / `onDispatchFailure`. Thêm người nghe mới chỉ cần class mới và `subscribe`, không sửa strategy.

### Các nguyên lý đang dùng trong phần này

- Đóng gói: hàng đợi, danh sách observer và strategy đều là field private. Hàng đợi và log trả ra bản chỉ đọc.
- Đa hình: dispatcher giữ `IDispatchStrategy` và `NotificationObserver`, không phụ thuộc class cụ thể. `validateDelivery()` mỗi kênh tự kiểm tra.
- Open/Closed: thêm cách gửi hoặc thêm logger bằng class mới.
- Separation of Concerns: strategy chỉ gửi, logger chỉ ghi nhận, dispatcher chỉ nối hai bên. Format nội dung vẫn ở `prepareContent()`.

## Luồng chạy khi thầy hỏi call stack

1. `Main.runDispatchDemo()` tạo `NotificationDispatcher` và `SystemLogger`, rồi `subscribe`.
2. `setStrategy(new SyncDispatchStrategy())`.
3. `dispatch(email)` gọi `validateDelivery()`. Email đúng định dạng thì `strategy.dispatch(...)` in dòng `[Sync]`.
4. Dispatcher gọi `logger.onDispatchSuccess(...)`.
5. `setStrategy(new AsyncDispatchStrategy())` rồi gửi SMS. SMS vào `queue`, logger ghi `strategy=Async`.
6. Email recipient `not-an-email` vẫn tạo được object vì constructor chỉ cấm chuỗi rỗng. `validateDelivery()` ném `IllegalArgumentException`. Dispatcher bắt lỗi, không gọi strategy, logger ghi `FAILED`.
7. `assertLog(...)` kiểm tra 3 dòng log. Sai thì ném `IllegalStateException`.

## Câu nên trả lời được

- Vì sao Async không dùng thread? Vì bài cần thấy hành vi xếp hàng, và assertion phải chắc chắn. Success ở nhánh này nghĩa là đã nhận vào hàng đợi, chưa phải đã gửi tới máy người nhận.
- Vì sao không viết `if (notification instanceof EmailNotification)` trong dispatcher? Vì đó là chỗ Strategy và đa hình phải thay thế.
- File nào Đính chưa làm? `NotificationFactory`, `NotificationDecorator`, `EncryptedDecorator`.
