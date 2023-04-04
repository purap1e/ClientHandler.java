import java.io.IOException;
import java.net.Socket;

public class Client {
    public static void main(String[] args) throws IOException {
        Socket socket = new Socket("localhost", 1234);
        MainFrame mainFrame = new MainFrame(socket);
        mainFrame.setVisible(true);
    }
}
