public class comptebancaire {
    // Attributs privés
    private double solde;
    private String titulaire;

    
    public comptebancaire(String titulaire, double soldeInitial) {
        this.titulaire = titulaire;
        this.solde = soldeInitial;
    }

    /**
     * Depose un montant sur le compte.
     * @param montant montant à déposer
     */
    public void deposer(double montant) {
        if (montant > 0) {
            solde += montant;
            System.out.println(montant + " € deposes. Nouveau solde : " + solde + " €");
        } else {
            System.out.println("Le montant a deposer doit etre pos");
        }
    }

   // retirer argent
    public void retirer(double montant) {
        if (montant > 0 && montant <= solde) {
            solde -= montant;
            System.out.println(montant + " € retires. Nouveau solde : " + solde + " €");
        } else if (montant > solde) {
            System.out.println("Fonds insuffisants. Solde actuel : " + solde + " €");
        } else {
            System.out.println("Le montant a retirer doit etre positif.");
        }
    }
}
