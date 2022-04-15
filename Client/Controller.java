package Client;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class Controller {

    private Model model;
    private View view;

    Controller(Model model, View view) {
        this.model = model;
        this.view = view;

        if (!model.strUserName.equals("")) setFrameTitle(model.strUserName);

        // Enter 事件
        view.tfUserInput.addKeyListener(new KeyAdapter() {
            public void keyPressed(KeyEvent e){
                if (e.getKeyCode() == 10) sendMessage();
            }
        });

        // 按鈕事件
        view.btnState.addActionListener(e -> {
            if (model.IsConnect) disconnect();
            else connect();
        });
        view.btnSend.addActionListener(e -> sendMessage());
    }

    void connect() {
        String ip = view.getIp();
        int port = view.getPort();
        if (port == -1) {
            view.showErrorDialog("port 必須為數字");
            return;
        }
        boolean bState = model.connect(ip, port, view.taConsole, view.scrollBar);
        if (!bState) return; // 連線失敗

        model.IsConnect = true;
        view.changeState("Disconnect");
        view.printToConsole(" ------------------ 成功連線 ------------------\n");
    }

    void disconnect() {
        model.output(threadClient.tokenDisconnect);
        model.IsConnect = false;
        view.changeState("Connect");
        view.printToConsole("");
        view.printToConsole(" ------------------ 關閉連線 ------------------");
    }

    void sendMessage() {
        String message = view.tfUserInput.getText();
        view.tfUserInput.setText("");

        if (model.IsConnect == true) {
            model.output(message);
        }
        else if (message.indexOf("/") != 0) {
            view.printToConsole("系統   <<   尚未與聊天室連線喔!");
        }

        if (message.equals(threadClient.tokenDisconnect)) disconnect();
        else if (message.indexOf("/connect") == 0) connect();
        else if (message.indexOf("/rename ") == 0) {
            String newName = message.substring(message.indexOf(" ") + 1);
            model.strUserName = newName;
            view.printToConsole("系統   <<   成功改名成:  " + newName);
            setFrameTitle(newName);
        }
        else if (model.IsConnect) view.printToConsole("你 (" + Other.Method.getTime() + ")   <<   " + message);
    }

    void setFrameTitle(String name) {
        view.setFrameTitle(name);
    }

}
