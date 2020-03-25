import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class Dictionnaire {

    Set<String> ensembleMots = new HashSet<>();
    Map<String, Set<String>> listeAssoc = new HashMap<>();

    /**
     * Construction du dictionnaire
     * @param fileName
     */
    public Dictionnaire(String fileName) {
        try {
            FileReader fileReader = new FileReader(fileName);
            BufferedReader br = new BufferedReader(fileReader);

            String ligne;

            while((ligne = br.readLine()) != null) {
                ensembleMots.add(ligne); // ajout mots au dictionnaire

                for (int i = 0; i < ligne.length(); i++) {
                    String mot = ligne.substring(0, i) + ligne.substring(i + 1);

                    // addAll a la place?
                    Set<String> correctionsPossibles = new HashSet<>();
                    Set<String> sugg = listeAssoc.get(mot);
                    if (sugg != null)  correctionsPossibles = sugg;
                    correctionsPossibles.add(ligne);

                    listeAssoc.put(mot, correctionsPossibles); // associe le mot sans 1 lettre à la ligne
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

    public void setEnsembleMots(Set<String> ensembleMots) {
        this.ensembleMots = ensembleMots;
    }

    public Map<String, Set<String>> getListeAssoc() {
        return listeAssoc;
    }

    public void setListeAssoc(Map<String, Set<String>> listeAssoc) {
        this.listeAssoc = listeAssoc;
    }

}
