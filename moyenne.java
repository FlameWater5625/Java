// calcul d une moyenne en java
publc class moyenne{
    public static void main(String[] args) {
        double somme = 0;
        int tableau[] = {3, 5, 1, 2, 8, 10, 11, 4, 5};
        // triage du tableau
        for (int i = 0; i < tableau.length; i++) {
            for (int j = i + 1; j < tableau.length; j++) {
                if (tableau[i] > tableau[j]) {
                    int temp = tableau[i];
                    tableau[i] = tableau[j];
                    tableau[j] = temp;
                }
            }
        }
        // calcul de la moyenne du tableau
        for (int i = 0; i < tableau.length; i++) {
            somme += tableau[i];
        }
        System.out.println("La moyenne du tableau est : " + somme / tableau.length);

        // calcul de la mediane du tableau
        if (tableau.length % 2 == 0) {
            System.out.println("La mediane du tableau est : " + (tableau[tableau.length / 2] + tableau[tableau.length / 2 - 1]) / 2);
        } else {
            System.out.println("La mediane du tableau est : " + tableau[tableau.length / 2]);
        }
        
        
    }
}