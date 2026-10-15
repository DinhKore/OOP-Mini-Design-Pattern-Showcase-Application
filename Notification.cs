using System;

namespace NotificationSystem
{
    /// <summary>
    /// Lớp trừu tượng cơ sở thể hiện tính Abstraction và Encapsulation.
    /// Mọi loại thông báo trong hệ thống đều phải kế thừa từ lớp này.
    /// </summary>
    public abstract class Notification
    {
        // Encapsulation: Fields được giấu kín (private), chỉ truy cập qua Properties (getters/setters)
        public string Id { get; private set; }
        public string Recipient { get; private set; }
        public string Message { get; private set; }
        public DateTime CreatedAt { get; private set; }

        // Constructor bắt buộc phải có dữ liệu hợp lệ (Validation)
        protected Notification(string recipient, string message)
        {
            if (string.IsNullOrWhiteSpace(recipient))
                throw new ArgumentException("Người nhận không được để trống.", nameof(recipient));

            if (string.IsNullOrWhiteSpace(message))
                throw new ArgumentException("Nội dung tin nhắn không được để trống.", nameof(message));

            Id = Guid.NewGuid().ToString(); // Tự động sinh ID duy nhất
            Recipient = recipient;
            Message = message;
            CreatedAt = DateTime.Now;
        }

        /// <summary>
        /// Phương thức abstract buộc các class con phải tự định nghĩa cách thức chuẩn bị nội dung
        /// (Polymorphism - Đa hình)
        /// </summary>
        public abstract string PrepareContent();

        /// <summary>
        /// Phương thức virtual cho phép lấy thông tin chi tiết. 
        /// Lớp con có thể ghi đè (override) nếu cần bổ sung thông tin riêng.
        /// </summary>
        public virtual string GetDetails()
        {
            return $"[ID: {Id}] {CreatedAt:yyyy-MM-dd HH:mm:ss} | Tới: {Recipient}\nNội dung: {PrepareContent()}";
        }
    }
}