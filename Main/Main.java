package Main;

import Server.threadServer;

public class Main {

    public static void main(String[] args) {
        // GUI 多人連線
        /*Server.Main.excute(new int[]{120, 480});
        Client.Main.excute("鍾承翰", new int[]{10, 100});
        Client.Main.excute("王俊傑", new int[]{535, 100});
        Client.Main.excute("廖儒均", new int[]{1060, 100});*/

        // Client - Server
        Server.Main.excute(new int[]{535, 100});
        Client.Main.excute("使用者", new int[]{10, 100});
    }

}
