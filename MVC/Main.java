package MVC;

public class Main {

    public static void main(String[] args) {
        Main.run();
    }

    public static void run() {
        new Controller(new Model(), new View());
    }

}
