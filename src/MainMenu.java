import javax.swing.*;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.net.Socket;

public class MainMenu extends JPanel {
    private MainFrame application;

    private JButton button;
    private JTextField nameTextField;
    private JLabel label;

    private Socket socket;

    private BufferedWriter bufferedWriter;

    public MainMenu(MainFrame application, Socket socket) throws IOException {
        this.application = application;
        this.socket = socket;
        this.bufferedWriter = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));

        setSize(500,500);
        setLayout(null);

        label = new JLabel("Nick Name:");
        label.setSize(300,30);
        label.setLocation(50,100);
        add(label);

        nameTextField = new JTextField("");
        nameTextField.setSize(300,30);
        nameTextField.setLocation(150,100);
        add(nameTextField);

        button = new JButton("confirm");
        button.setSize(300,30);
        button.setLocation(100,200);

        button.addActionListener(s -> {
            application.getMainMenuPage().setVisible(false);
            application.getChatPage().setVisible(true);

            try {
                application.setTitle(nameTextField.getText());
                System.out.println("User set name: " + nameTextField.getText());
                bufferedWriter.write(nameTextField.getText());
                bufferedWriter.newLine();
                bufferedWriter.flush();
            } catch (IOException e) {
                e.printStackTrace();
            }

            nameTextField.setText("");

        });
        add(button);
    }
}
