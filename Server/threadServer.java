package Server;

import javax.swing.JTextArea;
import javax.swing.JScrollBar;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import java.net.SocketTimeoutException;
import java.util.ArrayList;

public class threadServer implements Runnable {

    ServerSocket serverSocket;
    public ArrayList<String> listUserName = new ArrayList<String>();
    public ArrayList<PrintWriter> listPrintWriter = new ArrayList<PrintWriter>();
    private int port = 8080;
    final static String tokenDisconnect = "/end";
    boolean IsOpen = true;

    private JTextArea taConsole = null;
    private JScrollBar scrollBar = null;

    // 一般開啟方式
    threadServer() {
        initialize(port);
    }
    // GUI 開始方式
    threadServer(int port, JTextArea taConsole, JScrollBar scrollBar) {
        this.taConsole = taConsole;
        this.scrollBar = scrollBar;
        initialize(port);
    }
    private void initialize(int port) {
        try {
            serverSocket = new ServerSocket(port);
            print("Waiting for client on port " + serverSocket.getLocalPort() + "...");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void run() {
        while (IsOpen) {
            try {
                Socket socket = serverSocket.accept();
                new Thread(new chatProcess(socket)).start();
            } catch (SocketTimeoutException s) {
                print("Socket timed out!");
                break;
            } catch (SocketException e) {
                // Socket closed
                //e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
                break;
            }
        }
    }

    void print(String message) {
        if (taConsole == null) System.out.println(message);
        else {
            taConsole.append(message + "\n");
            scrollBar.setValue(scrollBar.getMaximum());
        }
    }

    // 系統廣播
    void broadcast(String message) {
        String strOutput  = "伺服器 " + " (" + Other.Method.getTime() + ")   <<   " + message;

        // 廣播中斷指令
        if (message.equals(tokenDisconnect)) strOutput = tokenDisconnect;

        String finalOutput = strOutput;
        listPrintWriter.forEach(printWriter -> {
            printWriter.println(finalOutput);
        });
    }


    // 開啟新執行序與每個 client 連接 socket，處理 client 之間的通訊
    class chatProcess implements Runnable {

        private Socket socket;
        private String hostInfo;
        private String message;
        private PrintWriter printWriter;

        chatProcess(Socket skt) {
            this.socket = skt;
            this.hostInfo = skt.getInetAddress().getHostName() + " [" + skt.getInetAddress().getHostAddress() + ":" + skt.getPort() + "]";
        }

        public void run() {
            try {
                InputStream is = socket.getInputStream();
                InputStreamReader isr = new InputStreamReader(is, "utf-8");
                BufferedReader br = new BufferedReader(isr);

                OutputStream os = socket.getOutputStream();
                OutputStreamWriter osw = new OutputStreamWriter(os, "utf-8");
                BufferedWriter bw = new BufferedWriter(osw);
                printWriter = new PrintWriter(bw, true);

                synchronized (listPrintWriter) {
                    listPrintWriter.add(printWriter);
                }
                print(hostInfo + " is online.  total number of people: " + listPrintWriter.size());

                while (IsOpen && !(message = br.readLine()).equals(tokenDisconnect)) {
                    // 命名指令
                    if (message.indexOf("/rename ") != -1) {
                        String oldInfo = hostInfo;
                        hostInfo = message.substring(message.indexOf(" ") + 1);
                        print(oldInfo + " 改名為 " + hostInfo);
                        continue;
                    }

                    message = hostInfo + " (" + Other.Method.getTime() + ")   <<   " + message;
                    synchronized (listPrintWriter) {
                        for (int index = 0; index < listPrintWriter.size(); index++) {
                            if (listPrintWriter.get(index) == printWriter) continue;
                            listPrintWriter.get(index).println(message);
                        }
                    }

                    print("\t" + message);
                }

                synchronized (listPrintWriter) {
                    listPrintWriter.remove(printWriter);
                }
                print(hostInfo + " is offline. total number of people: " + listPrintWriter.size());
                socket.close();
            } catch (SocketException e) {
                // 不正常斷線 (直接關閉視窗)
                synchronized (listPrintWriter) {
                    listPrintWriter.remove(printWriter);
                }
                print(hostInfo + " is offline. total number of people: " + listPrintWriter.size());
                try {
                    socket.close();
                } catch (IOException ioException) {
                    ioException.printStackTrace();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }

}
