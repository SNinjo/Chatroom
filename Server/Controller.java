package Server;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class Controller {

    private Model model;
    private View view;

    Controller(Model model, View view) {
        this.model = model;
        this.view = view;

        // Enter 事件
        view.tfUserInput.addKeyListener(new KeyAdapter() {
            public void keyPressed(KeyEvent e){
                if (e.getKeyCode() == 10) sendMessage();
            }
        });

        // 按鈕事件
        view.btnState.addActionListener(e -> {
            if (model.IsOpen) closeServer();
            else openServer();
        });
        view.btnSend.addActionListener(e -> sendMessage());
    }

    void openServer() {
        model.openServer(view.getPort(), view.taConsole, view.scrollBar);
        model.IsOpen = true;
        view.changeState("Close");
        view.printToConsole(" ------------------ 伺服器開啟 ------------------\n");
    }

    void closeServer() {
        model.closeServer();
        model.IsOpen = false;
        view.changeState("Open");
        view.printToConsole("");
        view.printToConsole(" ------------------ 尚未連線 ------------------");
    }

    void sendMessage() {
        String message = view.tfUserInput.getText();
        view.tfUserInput.setText("");
        if (model.IsOpen) {
            view.printToConsole("伺服器   <<   " + message);
            model.broadcast(message);
        }
        else {
            view.printToConsole("無法廣播(伺服器尚未開啟)");
        }
    }

}
