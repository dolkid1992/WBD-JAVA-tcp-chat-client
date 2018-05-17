public interface MessageEvent {
    void onConnected(String message);

    void onMessageReceived(String message);

    void onError(String message);
}
