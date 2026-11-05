// Observer: bên nghe không nằm trong class gửi. Muốn thêm logger mới thì viết class mới rồi subscribe.
public interface NotificationObserver {
    void onDispatchSuccess(Notification notification, String strategyName);

    void onDispatchFailure(Notification notification, String reason);
}
