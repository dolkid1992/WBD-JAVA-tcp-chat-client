import java.io.IOException;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        ChatClient chatClient = new ChatClient(new MessageEventImpl());

        try {
            Scanner scanner = new Scanner(System.in);
            System.out.println("Enter server host: ");
            String serverHost = scanner.nextLine();
            System.out.println("Enter server port: ");
            int serverPort = scanner.nextInt();
            scanner.nextLine();

            chatClient.connect(serverHost, serverPort);
            String message;
            do{
                System.out.println("Message: ");
                message = scanner.nextLine();
                chatClient.sendMessage(message);
            }while (!message.equals("bye"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    static class MessageEventImpl implements MessageEvent{
        @Override
        public void onConnected(String message) {
            System.out.println(message);
        }

        @Override
        public void onMessageReceived(String message) {
            System.out.println(message);
        }

        @Override
        public void onError(String message) {
            System.out.println(message);
        }
    }
}