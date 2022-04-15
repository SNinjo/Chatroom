package Client;

import javax.swing.JTextArea;
import javax.swing.JScrollBar;

public class Model {

    threadClient threadClient = null;
    boolean IsConnect = false;
    String strUserName = "";

    Model() {}
    Model(String name) {
        strUserName = name;
    }

    boolean connect(String ip, int port, JTextArea taConsole, JScrollBar scrollBar) {
        threadClient = new threadClient(ip, port, taConsole, scrollBar);
        if (!threadClient.IsConnect) return false;
        threadClient.excuteInput();

        // 自動更新名子
        if (!strUserName.equals("")) threadClient.excuteOutput("/rename " + strUserName);
        return true;
    }

    void output(String message) {
        threadClient.excuteOutput(message);
    }

}
