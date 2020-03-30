import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Scanner;
import java.util.Set;
import java.util.regex.Pattern;

public class Texte {
    private ArrayList<String> mots = new ArrayList<>();
    private ArrayList<String> separateurs = new ArrayList<>();
    private ArrayList<Set<String>> motsSuggestions = new ArrayList<>();


    /**
     * Constructeur du texte
     * @param fileName texte à corriger
     */
    public Texte(String fileName) {

        Pattern patternMot = Pattern.compile("(?U)\\w+");
        Pattern patternSeparateur = Pattern.compile("(?U)\\W+");

        try {
            InputStreamReader fileReader = new InputStreamReader(
                    new FileInputStream(fileName),
                    "UTF-8");
            Scanner s = new Scanner(fileReader);
            s.useDelimiter("\\b");

            while (s.hasNext(patternMot)) {
                // Lire un mot
                String mot = s.next(patternMot);
                mots.add(mot);

                // Lire un séparateur
                if (s.hasNext(patternSeparateur)) {
                    String separateur = s.next(patternSeparateur);
                    separateurs.add(separateur);
                }
            }

            s.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    /**
     * Retourne les mots corrigés
     * @param dictionnaire contient l'ensemble des mots et la liste associative
     */
    public void correction(Dictionnaire dictionnaire) {
        Set<String> inconnu = new HashSet<>();
        inconnu.add("(?)");

        for (String mot : mots) {
            mot = mot.toLowerCase();

            // Si le mot est incorrecte
            if (!dictionnaire.getEnsembleMots().contains(mot)) {

                // liste des suggestions de chaque mot
                Set<String> motSuggestions = new HashSet<>();

                // A
                if (dictionnaire.getListeAssoc().get(mot) != null) {
                    motSuggestions = dictionnaire.getListeAssoc().get(mot);
                }

                for (int i = 0; i < mot.length(); i++) {
                    String motCoupe = mot.substring(0, i) + mot.substring(i + 1);

                    // B1
                    if (dictionnaire.getEnsembleMots().contains(motCoupe)) {
                        motSuggestions.add(motCoupe);
                    }

                    // B2
                    Set<String> B2 = new HashSet<>();
                    if (dictionnaire.getListeAssoc().get(motCoupe) != null) {
                        B2 = dictionnaire.getListeAssoc().get(motCoupe);
                    }
                    motSuggestions.addAll(B2);

                }

                // Ajout de toutes les suggestions du mot s'il y en a
                int nbSugg = motSuggestions.size();
                motsSuggestions.add(nbSugg == 0 ? inconnu : motSuggestions);

            } else {
                motsSuggestions.add(null);
            }
        }
    }


    /**
     * Affiche le texte avec les suggestions pour chaque mots
     */
    public void afficher() {

        String txt = "";

        for (int i = 0; i < mots.size(); i++) {
            String mot = mots.get(i);

            if (motsSuggestions.get(i) != null) {
                txt += "[" + mot + " => " +
                        String.join(",", motsSuggestions.get(i)) + "]";
            } else {
                txt += mot;
            }

            if (i < separateurs.size()) {
                txt += separateurs.get(i);
            }
        }
        System.out.println(txt);
    }
}
