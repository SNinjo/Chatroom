package Client;

public class Main {

    public static void main(String[] args) {
        excute();
        excute();
    }

    public static void excute() {
        new Controller(new Model(), new View());
    }

    public static void excute(String name, int[] position) {
        new Controller(new Model(name), new View(position));
    }

}
