package Main;

import Server.threadServer;

public class Main {

    public static void main(String[] args) {
        // GUI 多人連線
        Server.Main.excute(new int[]{120, 500});
        Client.Main.excute("User01", new int[]{10, 100});
        Client.Main.excute("User02", new int[]{535, 100});
        Client.Main.excute("User03", new int[]{1060, 100});

        // Client - Server
//        Server.Main.excute(new int[]{535, 100});
//        Client.Main.excute("使用者", new int[]{10, 100});
    }

}
