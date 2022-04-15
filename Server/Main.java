package Server;

public class Main {

    public static void main(String[] args) {
        excute();
    }

    public static void excute() {
        new Thread(new threadServer()).start();
    }

    public static void excute(boolean IsGUI) {
        if (IsGUI) new Controller(new Model(), new View());
        else new Thread(new threadServer()).start();
    }

    public static void excute(int[] position) {
        new Controller(new Model(), new View(position));
    }

}
