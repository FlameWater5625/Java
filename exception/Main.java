public class Main {
    static String[] cours = {"Java", "Dev", "Cyber", "Mngmnt"};
    
    public static String getElement(int index) throws ArrayIndexOutOfBoundsException{
        if (cours.length <index) {
        throw new ArrayIndexOutOfBoundsException("Elemnt hords du tab");
        }
        return cours[index];
    }

    public static void main(String[] args) {
        try {
            String cours = getElement(5);
            System.out.println(cours);
        } catch (ArrayIndexOutOfBoundsException e) {
            System.out.println("Erreur capturee :" + e.getMessage());
        }
    }




}