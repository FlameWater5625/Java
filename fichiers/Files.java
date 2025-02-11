// aplication pour lire, écrire, effacer et modifier des fichiers
import java.io.FileWriter;
import java.io.IOException;


public class Files {
    public static void main(String[] args) {
        // ouvrir fichier
        try {
            FileWriter myWriter = new FileWriter("fichiers.txt");
            myWriter.write("Hello world!");
            myWriter.close();
            System.out.println("succesfull");
        } catch (IOException e) {
            System.out.println("erreur");
            e.printStackTrace();
        }

        
    }
} 