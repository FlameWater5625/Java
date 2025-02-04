import java.util.Scanner; // Import du Scanner

public class HelloWorld {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in); // Création de l'objet Scanner
        int age = 0; // Initialisation de l'âge
        
        System.out.print("Enter your age: "); // Demande de l'âge
        
        if (scanner.hasNextInt()) {
            age = scanner.nextInt(); // Lecture de l'âge
        } else {
            System.out.println("Invalid input"); 
            scanner.close(); // Fermeture du scanner avant de quitter
            System.exit(0);
        }
        
        if (age >= 18) {
            System.out.println("You are an adult"); // Affichage du message si l'utilisateur est adulte
        } else {
            System.out.println("You are a minor"); // Affichage du message si l'utilisateur est mineur
        }
        
        scanner.close(); // Fermeture du scanner
    }
}
