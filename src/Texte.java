import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Scanner;
import java.util.Set;
import java.util.regex.Pattern;

public class Texte {
    private ArrayList<String> mots = new ArrayList<>();
    private ArrayList<String> separateurs = new ArrayList<>();
    private ArrayList<Set<String>> motsCorriges = new ArrayList<>();

    /**
     * Constructeur du texte
     * @param fileName
     */
    public Texte(String fileName) {

        Pattern patternMot = Pattern.compile("[a-zA-Z\\u00C0-\\u017F]+");
        Pattern patternSeparateur = Pattern.compile("[^a-zA-Z\\u00C0-\\u017F]+");

        try {
            FileReader fileReader = new FileReader(fileName);
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


    public void afficher() {
        //ArrayUtils.addAll(mots, separateurs);

        String txt = "";

        for (int i = 0; i < mots.size(); i++) {
            String mot = mots.get(i);

            if (motsCorriges.get(i) != null) {
                txt += "[" + mot + " => " + String.join(",", motsCorriges.get(i)) + "]";
            } else {
                txt += mot;
            }

            if (i < separateurs.size()) {
                txt += separateurs.get(i);
            }
        }

       System.out.println(txt);
    }

    public void correction(Dictionnaire dictionnaire) {

        Set<String> inconnu = new HashSet<>();
        inconnu.add("(?)");

        for (String mot : mots) {

            // Correction à faire
            if (!dictionnaire.ensembleMots.contains(mot) && !dictionnaire.ensembleMots.contains(mot.toLowerCase())) {

                // Liste de toutes les suggestions possibles du mot
                // A
                Set<String> motSuggestions = new HashSet<>();

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
                    Set<String> B2 = dictionnaire.getListeAssoc().get(motCoupe) == null ? new HashSet<>() : dictionnaire.getListeAssoc().get(motCoupe);
                    if (B2 != null) {
                        motSuggestions.addAll(B2);
                    }
                }

                // Ajout de toutes les suggestions du mot à la liste des suggestions pour tous les mots
                motsCorriges.add(motSuggestions.size() == 0 ? inconnu : motSuggestions);

            } else {
                motsCorriges.add(null);
            }
        }
    }

}
