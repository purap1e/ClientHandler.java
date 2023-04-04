import javax.swing.*;
import java.io.IOException;
import java.net.Socket;

public class MainFrame extends JFrame {
    private final MainMenu mainMenuPage;
    private final ChatPage chatPage;

    public MainFrame(Socket socket) throws IOException {

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setTitle("Chat");
        setSize(500, 500);
        setLayout(null);


        mainMenuPage = new MainMenu(this, socket);
        mainMenuPage.setVisible(true);
        add(mainMenuPage);

        chatPage = new ChatPage(this, socket);
        chatPage.setVisible(false);
        add(chatPage);

    }

    public MainMenu getMainMenuPage() {
        return mainMenuPage;
    }

    public ChatPage getChatPage() {
        return chatPage;
    }
}
