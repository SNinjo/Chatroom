package Client;

import javax.swing.*;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.ConnectException;
import java.net.Socket;

public class threadClient {

    private Socket socket;
    private String Ip = "127.0.0.1";
    private int port = 8080;
    private PrintWriter printWriter;
    final static String tokenDisconnect = "/end";
    boolean IsConnect = false;

    private JTextArea taConsole = null;
    private JScrollBar scrollBar = null;

    // 一般開啟方式
    threadClient(String Ip, int port) {
        initialize(Ip, port);
    }
    // GUI 開啟方式
    threadClient(String Ip, int port, JTextArea taConsole, JScrollBar scrollBar) {
        this.taConsole = taConsole;
        this.scrollBar = scrollBar;
        initialize(Ip, port);
    }
    private void initialize(String Ip, int port) {
        this.Ip = Ip;
        this.port = port;
        try {
            socket = new Socket(Ip, port);
            this.IsConnect = true;
            //System.out.println("Connecting to " + Ip + ":" + port + "...");

            OutputStream os = socket.getOutputStream();
            OutputStreamWriter osw = new OutputStreamWriter(os, "utf-8");
            BufferedWriter bw = new BufferedWriter(osw);
            this.printWriter = new PrintWriter(bw, true);
        } catch (ConnectException e) {
            // 無法連線至 Server
            print("系統   <<   Error-   無法連線至 Server(請檢查網路或是伺服器運作狀態)");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void excuteOutput(String message) {
        printWriter.println(message);
    }

    public void excuteInput() {
        chatProcess process = new chatProcess();
        new Thread(process).start();
    }

    void print(String message) {
        if (taConsole == null) System.out.println(message);
        else {
            taConsole.append(message + "\n");
            scrollBar.setValue(scrollBar.getMaximum());
        }
    }


    class chatProcess implements Runnable {

        chatProcess(){}

        public void run() {
            try {
                InputStream is = socket.getInputStream();
                InputStreamReader isr = new InputStreamReader(is,"utf-8");
                BufferedReader br = new BufferedReader(isr);
                String strInput = "";
                while((strInput = br.readLine()) != null) {
                    if (strInput.equals(tokenDisconnect)) {
                        print("系統   <<   Error- 伺服器已關閉(請重新連接)");
                        return;
                    }
                    print(strInput);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }

}
