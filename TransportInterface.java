// creation interface
interface Transport {
    void deplacer();
}

class Voiture implements Transport {
    @Override
    public void deplacer() {
        System.out.println("La voiture se deplace sur la route");
    }
}

class Avion implements Transport {
    @Override
    public void deplacer() {
        System.out.println("L'avion se deplace dans les airs");
    }
}

public class TransportInterface {
    public static void main(String[] args) {
        Transport voiture = new Voiture();
        voiture.deplacer(); // Affiche : La voiture se déplace sur la route

        Transport avion = new Avion();
        avion.deplacer(); // Affiche : L'avion se déplace dans les airs
    }
}
