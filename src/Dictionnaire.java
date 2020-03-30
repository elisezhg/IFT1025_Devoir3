import java.io.*;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class Dictionnaire {

    private Set<String> ensembleMots = new HashSet<>();
    private Map<String, Set<String>> listeAssoc = new HashMap<>();

    /**
     * Construction du dictionnaire
     * @param fileName dictionnaire
     */
    public Dictionnaire(String fileName) {
        try {
            InputStreamReader fileReader = new InputStreamReader(
                    new FileInputStream(fileName),
                    "UTF-8");

            BufferedReader br = new BufferedReader(fileReader);

            String mot;

            while((mot = br.readLine()) != null) {
                mot = mot.toLowerCase();
                ensembleMots.add(mot); // ajoute mot au dictionnaire

                for (int i = 0; i < mot.length(); i++) {
                    String motCoupe = mot.substring(0, i) + mot.substring(i + 1);

                    Set<String> correctionsPossibles = new HashSet<>();

                    if (listeAssoc.get(motCoupe) != null) {
                        // va chercher les autres corrections possibles
                        correctionsPossibles = listeAssoc.get(motCoupe);
                    }

                    correctionsPossibles.add(mot); // ajoute mot original

                    listeAssoc.put(motCoupe, correctionsPossibles);
                }
            }

            br.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public Set<String> getEnsembleMots() {
        return ensembleMots;
    }

    public Map<String, Set<String>> getListeAssoc() {
        return listeAssoc;
    }
}
