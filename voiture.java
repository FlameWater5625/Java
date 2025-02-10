// creer une classe voiture
public class voiture {
    String marque
    String couleur
    String modele

    public voiture(String marque, String couleur, String modele) {
        this.marque = marque
        this.couleur = couleur
        this.modele = modele
    }

    void demarrer() {
        System.out.println("la voiture demarre")
    }

    void accelerer() {
        System.out.println("la voiture accellere")
    }

    void ralentir() {
        System.out.println("la voiture ralentit")
    }

    public class main(String[] args) { 
}