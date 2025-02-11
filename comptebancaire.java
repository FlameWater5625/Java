public class CompteBancaire {
    private double solde;
    private String titulaire;

    public CompteBancaire(String titulaire, double soldeInitial) {
        this.titulaire = titulaire;
        this.solde = soldeInitial;
    }

    public void deposer(double montant) {
        if (montant > 0) {
            solde += montant;
            System.out.println(montant + " € déposés. Nouveau solde : " + solde + " €");
        } else {
            System.out.println("Le montant à déposer doit être positif.");
        }
    }

    public void retirer(double montant) {
        if (montant > 0 && montant <= solde) {
            solde -= montant;
            System.out.println(montant + " € retirés. Nouveau solde : " + solde + " €");
        } else if (montant > solde) {
            System.out.println("Fonds insuffisants. Solde actuel : " + solde + " €");
        } else {
            System.out.println("Le montant à retirer doit être positif.");
        }
    }

    public static void main(String[] args) {
        CompteBancaire compte = new CompteBancaire("Jean Dupont", 1000.0);
        compte.deposer(500.0);
        compte.retirer(200.0);
        compte.retirer(1500.0);
    }
}
