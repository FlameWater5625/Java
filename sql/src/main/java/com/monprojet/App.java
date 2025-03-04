package com.monprojet;


import java.util.Scanner;

public class App {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int choix;

        do {
            System.out.println("\nMenu:");
            System.out.println("1: Insérer un utilisateur");
            System.out.println("2: Supprimer un utilisateur");
            System.out.println("3: Modifier un utilisateur");
            System.out.println("4: Afficher tous les utilisateurs");
            System.out.println("5: Quitter");
            System.out.print("Choisissez une option : ");
            choix = scanner.nextInt();
            scanner.nextLine(); // Pour consommer la nouvelle ligne

            switch (choix) {
                case 1:
                    System.out.print("Entrez l'email : ");
                    String email = scanner.nextLine();
                    System.out.print("Entrez le nom : ");
                    String nom = scanner.nextLine();
                    GestionUtilisateurs.insererUtilisateur(email, nom);
                    break;
                case 2:
                    System.out.print("Entrez l'ID de l'utilisateur à supprimer : ");
                    int idSupprimer = scanner.nextInt();
                    GestionUtilisateurs.supprimerUtilisateur(idSupprimer);
                    break;
                case 3:
                    System.out.print("Entrez l'ID de l'utilisateur à modifier : ");
                    int idModifier = scanner.nextInt();
                    scanner.nextLine(); // Pour consommer la nouvelle ligne
                    System.out.print("Entrez le nouvel email : ");
                    String nouvelEmail = scanner.nextLine();
                    System.out.print("Entrez le nouveau nom : ");
                    String nouveauNom = scanner.nextLine();
                    GestionUtilisateurs.modifierUtilisateur(idModifier, nouvelEmail, nouveauNom);
                    break;
                case 4:
                    GestionUtilisateurs.afficherUtilisateurs();
                    break;
                case 5:
                    System.out.println("Au revoir !");
                    break;
                default:
                    System.out.println("Option invalide. Veuillez réessayer.");
            }
        } while (choix != 5);

        scanner.close();
    }
}