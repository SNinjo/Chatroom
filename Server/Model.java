package Server;

import javax.swing.JTextArea;
import javax.swing.JScrollBar;
import java.io.IOException;

public class Model {

    threadServer threadServer = null;
    boolean IsOpen = false;
    String strUserName = "";

    Model() {}
    Model(String name) {
        strUserName = name;
    }

    void openServer(int port, JTextArea taConsole, JScrollBar scrollBar) {
        threadServer = new threadServer(port, taConsole, scrollBar);
        new Thread(threadServer).start();
    }

    void closeServer() {
        threadServer.IsOpen = false;
        broadcast(threadServer.tokenDisconnect);
        try {
            threadServer.serverSocket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    void broadcast(String message) {
        threadServer.broadcast(message);
    }

}
