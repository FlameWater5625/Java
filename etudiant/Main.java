package etudiant;
java.util.*;

public class Main {
    System.out.println("DEmarrage ecole v2");

    int menu = 0;
    Scanner scan = new Scanner(System.in);

    do {
        System.out.println("1. Ajouter un etudiant");
        System.out.println("2. Supprimer un etudiant");
        System.out.println("3. Afficher la liste des etudiants");
        System.out.println("4. Quitter");
        System.out.print("Entrez votre choix : ");
        menu = scan.nextInt();

        switch (menu) {
            case 1:
                // Ajouter un etudiant
                System.out.println("Nom de l'etudiant : ");
                String nom = scan.next();
                System.out.println("Prenom de l'etudiant : ");
                String prenom = scan.next();
                System.out.println("Classe de l'etudiant : ");
                String classe = scan.next();
                break;
            case 2:
                System.out.println("Nom de l'etudiant : ");
                String nom2 = scan.next();
                
                // Supprimer un etudiant
                break;
            case 3:
                // Afficher la liste des etudiants
                break;
            case 4:
                // Quitter
                break;
            default:
                System.out.println("Choix invalide");
        }
    } while (menu != 4);

}
        


