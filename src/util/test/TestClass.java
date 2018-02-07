package util.test;

@SuppressWarnings("all")
public class TestClass {

    public static void main(String[] args) {
        show("integer");
    }

    private static void show(String... args) {
        for (String arg : args) {
            System.out.println(arg);
        }
    }


}
