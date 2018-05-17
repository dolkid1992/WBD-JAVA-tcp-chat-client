import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.Socket;

public class ChatClient {
    private MessageEvent messageEvent;
    private PrintStream printStream;

    public ChatClient(MessageEvent messageEvent) {
        if (messageEvent == null) {
            throw new RuntimeException("MessageEvent cannot be null");
        }
        this.messageEvent = messageEvent;
    }

    public void connect(String serverHost, int serverPort) throws IOException {
        Socket socket = new Socket(serverHost, serverPort);
        messageEvent.onConnected("Connected to server");
        printStream = new PrintStream(socket.getOutputStream());
        BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));

        new Thread(() -> {
            try{
                String line;
                while ((line = reader.readLine()) != null){
                    messageEvent.onMessageReceived(line);
                }
                reader.close();
            }catch (Exception ex){
                messageEvent.onError("Error reading message: " + ex.getMessage());
            }
        }).start();
    }

    public void sendMessage(String message){
        printStream.println(message);
    }
}
