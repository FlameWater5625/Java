import java.io.FileWriter;
import java.io.IOException;

public class Main {
    public static void main(String[] args) {
        try {
            FileWriter myWriter = new FileWriter("hello.txt");
            myWriter.write("help!");
            myWriter.close();
            System.out.println("succesfull!!!!!!!!!!!!!!!!!!");
        } catch (IOException e) {
            // exception cqtching 
            System.out.println("erreur");
            e.printStackTrace();
        }
    }
}