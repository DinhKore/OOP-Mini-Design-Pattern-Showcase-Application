// Strategy: đóng gói thuật toán gửi. Dispatcher chỉ gọi interface này.
public interface IDispatchStrategy {
    String getName();

    void dispatch(Notification notification);
}
