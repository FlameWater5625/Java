import java.util.Scanner; // Import du Scanner

// Classe parent Produit
class Produit {
    protected String nomProduit;
    protected double prixProduit;

    // Constructeur
    public Produit(String nom, double prix) {
        this.nomProduit = nom;
        this.prixProduit = prix;
    }

    // Méthode pour calculer le prix après TVA
    public double calculerPrixTTC(int tauxTVA) {
        return prixProduit * (1 + tauxTVA / 100.0);
    }
}

// Classe Main
public class prix {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in); 

        // Demande du prix et du nom du produit
        System.out.print("Entrez le prix du produit : ");
        double prixProduit = scanner.nextDouble();
        scanner.nextLine(); // Consommer la ligne restante

        System.out.print("Entrez le nom du produit : ");
        String nomProduit = scanner.nextLine().toLowerCase(); // Conversion en minuscule pour éviter les erreurs de saisie

        // Fermeture du scanner
        scanner.close(); 

        // Définition des taux de TVA
        int tvaDvd = 20;
        int tvaLivre = 5;

        // Création de l'objet Produit
        Produit produit = new Produit(nomProduit, prixProduit);

        // Vérification du type de produit et affichage du prix après TVA
        if (nomProduit.equals("dvd")) {
            System.out.println("Le prix du DVD après TVA est : " + produit.calculerPrixTTC(tvaDvd) + " euros");
        } else if (nomProduit.equals("livre")) {
            System.out.println("Le prix du livre après TVA est : " + produit.calculerPrixTTC(tvaLivre) + " euros");
        } else {
            System.out.println("Produit inconnu. Veuillez réessayer.");
        }
    }
}
