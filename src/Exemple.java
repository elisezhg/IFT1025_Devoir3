import java.io.*;
import java.util.*;
import java.util.regex.Pattern;

public class Exemple {

    public static void main(String[] args) {

        // Exemple d'utilisation de la ligne de commande

        if (args.length != 2) {
            /*
               **************************************************
                Vous pouvez changer les arguments de la ligne de
                commande de votre programme dans IntelliJ avec le menu
                Run > Edit Configurations, puis en ajoutant des
                arguments dans "Program arguments"
               **************************************************
            */
            System.out.println("Attention: 2 arguments sont attendus");
            System.exit(-1);
        }

        System.out.println("Argument #1 = " + args[0]);
        System.out.println("Argument #2 = " + args[1]);

        // --------------------------------------------------

        // Exemple de lecture d'un fichier ligne par ligne
        try {
            FileReader fileReader = new FileReader("texte-a-corriger.txt");
            BufferedReader br = new BufferedReader(fileReader);

            String ligne;

            while((ligne = br.readLine()) != null) {
                System.out.println("Ligne = " + ligne);
            }

            br.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        // --------------------------------------------------

        /* Exemple de lecture d'un texte mot à mot
           Le code suivant demande au Scanner de lire :

           - Soit un mot, qui est une séquence de lettre (avec accents
             ou non)
           - Soit un séparateur (tout le reste)
        */

        Pattern patternMot = Pattern.compile("[a-zA-Z\\u00C0-\\u017F]+");
        Pattern patternSeparateur = Pattern.compile("[^a-zA-Z\\u00C0-\\u017F]+");

        try {
            FileReader fileReader = new FileReader("texte-a-corriger.txt");
            Scanner s = new Scanner(fileReader);
            s.useDelimiter("\\b");

            while (s.hasNext(patternMot)) {
                // Lire un mot
                String mot = s.next(patternMot);
                System.out.println("Mot: " + mot);

                // Lire un séparateur
                String separateur = s.next(patternSeparateur);
                System.out.println("Séparateur: " + separateur);
            }

            s.close();

        } catch (IOException e) {
            e.printStackTrace();
        }

        // --------------------------------------------------
        // Exemple d'utilisation des ensembles (Set)

        Set<String> ensemble = new HashSet<>();

        ensemble.add("Test");
        ensemble.add("Bonjour");
        ensemble.add("Blub");

        System.out.println("Il y a " + ensemble.size() + " mots dans l'ensemble");
        
        if(ensemble.contains("Test")) {
            System.out.println("Il y a le mot 'Test' dans l'ensemble");
        }

        if(ensemble.contains("twado")) {
            System.out.println("Il y a le mot 'twado' dans l'ensemble");
        }


        // --------------------------------------------------
        // Exemple d'utilisation des listes associatives (Map)

        Map<String, Integer> listeAssoc1 = new HashMap<>();

        listeAssoc1.put("Test", 10);
        listeAssoc1.put("abc", 20);

        System.out.println("Valeur associée au mot 'abc' dans listeAssoc1:");
        System.out.println(listeAssoc1.get("abc"));


        System.out.println("Valeur associée au mot 'blub' dans listeAssoc1:");
        // La valeur devrait être null puisque le mot n'est pas dans listeAssoc1
        System.out.println(listeAssoc1.get("blub"));

        // Notez qu'on peut faire une liste associative qui associe
        // des types plus complexes ensemble :
        Map<Integer, Set<Double>> listeAssoc2 = new HashMap<>();

        Set<Double> ensemble2 = new HashSet<>();

        ensemble2.add(1.5);
        ensemble2.add(2.5);
        ensemble2.add(3.5);

        listeAssoc2.put(10, ensemble2);

        System.out.println("Il y a " + ensemble2.size() + " éléments dans ensemble2");

        // Les ensembles peuvent être combinés simplement (ajouter un
        // élément en double ne l'ajoute qu'une seule fois)
        Set<Double> ensemble3 = new HashSet<>();

        ensemble3.add(5.5);
        ensemble3.add(2.5);
        ensemble3.add(1.5);

        System.out.println("Union de ensemble2 et ensemble3:");

        Set<Double> unionEnsembles = new HashSet<>();
        unionEnsembles.addAll(ensemble2);
        unionEnsembles.addAll(ensemble3);

        for(Double val : unionEnsembles) {
            System.out.println(val);
        }
    }
}
