public class Correcteur {

    public static void main(String[] args) {
        if (args.length != 2) {
            System.out.println("Attention: 2 arguments sont attendus");
            System.exit(-1);
        }

        Dictionnaire dictionnaire = new Dictionnaire(args[1]);
        Texte texte = new Texte(args[0]);

        texte.correction(dictionnaire);
        texte.afficher();
    }
}
