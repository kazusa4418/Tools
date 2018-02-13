import java.io.File;

public class readOnlySeter {
    public static void main(String[] args) {
        File file = new File("InputScanner.java");
        boolean b = file.setReadOnly();
        if (b) {
            System.out.println("success");
        }
        else {
            System.out.println("failed");
        }
    }
}