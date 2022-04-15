package Server;

import javax.swing.*;
import java.awt.*;

public class View {

    private JFrame frame;
    private Container container;
    JTextField tfUserInput;
    private JTextField tfInputPort;
    private JLabel lbPort;
    private JLabel lbCopyright;
    JButton btnState;
    JButton btnSend;
    private JScrollPane scrollPane;
    JScrollBar scrollBar;
    JTextArea taConsole;

    private final static int[] position = {100, 100};

    View(){
        initialize(this.position);
    }
    View(int[] position){
        initialize(position);
    }

    private void initialize(int[] position) {
        frame = new JFrame();
        frame.setTitle("Chatroom 伺服器");
        container = frame.getContentPane();
        container.setLayout(null);
        container.setBackground(Color.LIGHT_GRAY);
        frame.setBounds(position[0], position[1], 500, 400);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        btnState = new JButton("Open");
        btnState.setFont(new Font("Arial", Font.BOLD, 12));
        btnState.setBounds(343, 27, 100, 23);
        container.add(btnState);

        btnSend = new JButton("Send");
        btnSend.setFont(new Font("Arial", Font.BOLD, 12));
        btnSend.setBounds(360, 308, 87, 23);
        container.add(btnSend);

        // Console 加上卷軸
        taConsole = new JTextArea();
        taConsole.setBounds(0, 0, 401, 197);
        taConsole.setEditable(false);
        scrollPane = new JScrollPane(taConsole);
        scrollPane.setBounds(46, 91, 401, 197);
        container.add(scrollPane);
        scrollBar = scrollPane.getVerticalScrollBar();

        tfUserInput = new JTextField();
        tfUserInput.setBounds(46, 308, 310, 23);
        container.add(tfUserInput);
        tfUserInput.setColumns(10);

        lbCopyright = new JLabel("Made by 廖儒均");
        lbCopyright.setForeground(Color.WHITE);
        lbCopyright.setFont(new Font("微软雅黑", Font.PLAIN, 14));
        lbCopyright.setBounds(372, 341, 112, 18);
        container.add(lbCopyright);

        tfInputPort = new JTextField("3000");
        tfInputPort.setBounds(81, 29, 120, 21);
        container.add(tfInputPort);
        tfInputPort.setColumns(10);

        lbPort = new JLabel("IP");
        lbPort.setFont(new Font("Arial", Font.BOLD, 14));
        lbPort.setBounds(58, 30, 46, 20);
        container.add(lbPort);

        frame.setVisible(true);
        frame.setResizable(false);
    }

    void printToConsole(String message) {
        taConsole.append(message + "\n");
        scrollBar.setValue(scrollBar.getMaximum());
    }

    int getPort() {
        if (!Other.Method.IsNum(tfInputPort.getText())) return -1;
        return Integer.parseInt(tfInputPort.getText());
    }

    void changeState(String state) {
        btnState.setText(state);
    }

    void showErrorDialog(String message) {
        showDialog(message, 0);
    }
    void showPlainDialog(String message) {
        showDialog(message, -1);
    }
    private void showDialog(String message, int type) {
        JOptionPane.showMessageDialog(frame, message, "GradeSystem", type);
    }

}
