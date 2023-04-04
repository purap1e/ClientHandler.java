import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.net.Socket;

public class ChatPage extends JPanel {
    private MainFrame application;

    private JTextArea area;
    private JButton button;
    private JTextField textField;

    private Socket socket;
    private BufferedWriter bufferedWriter;
    private BufferedReader bufferedReader;

    public ChatPage(MainFrame application, Socket socket) throws IOException {
        this.application = application;

        this.socket = socket;
        this.bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        this.bufferedWriter= new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));

        setSize(500,500);
        setLayout(null);

        textField = new JTextField();
        textField.setSize(400,30);
        textField.setLocation(10,425);
        add(textField);


        area = new JTextArea();
        area.setSize(500,420);
        area.setLocation(0,0);
        area.setEditable(false);
        add(area);

        button = new JButton("send");
        button.setSize(100,30);
        button.setLocation(400,425);

        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (!textField.equals("")){
                    try {
                        String messageToSend = textField.getText();
                        bufferedWriter.write(messageToSend);
                        bufferedWriter.newLine();
                        bufferedWriter.flush();
                    } catch (IOException y) {
                        closeEverything(socket, bufferedReader, bufferedWriter);
                    }
                    textField.setText("");
                }
            }
        });
        add(button);

        listenForIncomingMessages();
    }

    private void listenForIncomingMessages() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                String msgFromGroupChat;

                while (socket.isConnected()) {
                    try {
                        msgFromGroupChat = bufferedReader.readLine();
                        area.append(msgFromGroupChat + "\n");
                    } catch (IOException e){
                        closeEverything(socket, bufferedReader, bufferedWriter);
                    }
                }
            }
        }).start();
    }

    public void closeEverything(Socket socket, BufferedReader bufferedReader, BufferedWriter bufferedWriter){
        try {
            if (bufferedReader != null) {
                bufferedReader.close();
            }
            if (bufferedWriter != null) {
                bufferedWriter.close();
            }
            if (socket != null) {
                socket.close();
            }
        } catch (IOException e){
            e.printStackTrace();
        }
    }
}
