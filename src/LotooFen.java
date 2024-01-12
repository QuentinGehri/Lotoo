import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;


public class LotooFen extends JFrame implements ActionListener {
    private static final Font POLICE_JTA = new Font("Lucida Sans", Font.PLAIN, 14);
    private static final String REP_DEPART = ".";
    private JPanel jPanChoixCarton, jPanCarton, jPanCentre, jPanTirage, jPanAfficherExec, jPanTroisBtn, jPanNord;
    private JButton jBtnChoixCarton, jBtnChargerTirage, jBtnAfficher, jBtnExecuterTirage, jBtnStat, jBtnVider, jBtnQuitter;
    private JLabel jLblChoixCarton, jLblTirage;
    private String cartonCharge, tirageCharge;
    private JCheckBox jCbMode;
    private JTextArea jTa;
    private JFileChooser jfc;
    private ArrayList<Integer> listeTirage = null;
    private Carton cartonActif;
    private boolean modeManuel = false;

    public LotooFen() {
        setTitle("Application réalisée par Quentin");
        //setLayout(new FlowLayout());
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        jfc = new JFileChooser(REP_DEPART);
        jPanChoixCarton = new JPanel();
        jBtnChoixCarton = new JButton("Charger un carton");
        cartonCharge = "<?>";
        jLblChoixCarton = new JLabel("Carton: " + cartonCharge);
        jPanChoixCarton.add(jBtnChoixCarton);
        jPanChoixCarton.add(jLblChoixCarton);
        //add(jPanChoixCarton, "North");
        jPanCentre = new JPanel();
        jPanCentre.setLayout(new BorderLayout());
        jPanCarton = new JPanel();
        jPanCarton.setLayout(new GridLayout(Carton.NB_LIGNES, 0));
        //int nbTour = Carton.NB_LIGNES * Carton.NB_COLONNES;

        Carton c = new Carton();
        for (int i = 0; i < Carton.NB_LIGNES; i++) {
            for (int j = 0; j < Carton.NB_COLONNES; j++) {
                LotooButton bouton = new LotooButton("<?>");
                c.listeCases[i][j] = bouton;
            }
        }
        afficherUnCarton(c);

        jPanCentre.add(jPanCarton);
        tirageCharge = "<?>";

        jLblTirage = new JLabel("Tirage: " + tirageCharge);

        jPanTirage = new JPanel();
        jBtnChargerTirage = new JButton("Charger un tirage");
        jBtnChargerTirage.setEnabled(false);
        jPanTirage.add(jBtnChargerTirage);
        jPanTirage.add(jLblTirage);
        jPanNord = new JPanel(new BorderLayout());
        jPanNord.add(jPanChoixCarton, "North");
        jPanNord.add(jPanCarton, "Center");
        jPanNord.add(jPanTirage, "South");
        add(jPanNord, "North");
        jPanAfficherExec = new JPanel();
        jBtnAfficher = new JButton("Afficher");
        jBtnAfficher.setEnabled(false);
        jBtnExecuterTirage = new JButton("Exécuter tirage");
        jBtnExecuterTirage.setEnabled(false);
        jCbMode = new JCheckBox("Mode manuel");
        jPanAfficherExec.add(jBtnAfficher);
        jPanAfficherExec.add(jBtnExecuterTirage);
        jPanAfficherExec.add(jCbMode);
        jPanCentre.add(jPanAfficherExec, "North");
        jTa = new JTextArea();
        jPanCentre.add(jTa, "Center");
        jPanTroisBtn = new JPanel();
        //jPanTroisBtn.setLayout(new GridLayout(1, 0));
        jBtnStat = new JButton("Statistiques");
        jBtnVider = new JButton("Vider");
        jBtnQuitter = new JButton("Quitter");
        jPanTroisBtn.add(jBtnStat);
        jPanTroisBtn.add(jBtnVider);
        jPanTroisBtn.add(jBtnQuitter);
        //add(jPanSud, "South");
        jPanCentre.add(jPanTroisBtn, "South");
        add(jPanCentre, "Center");
        jBtnChoixCarton.addActionListener(this);
        jBtnQuitter.addActionListener(this);
        jBtnChargerTirage.addActionListener(this);
        jBtnExecuterTirage.addActionListener(this);
        jBtnAfficher.addActionListener(this);
        jBtnVider.addActionListener(this);
        jBtnStat.addActionListener(this);
        jCbMode.addActionListener(this);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == jBtnChoixCarton) {
            int actionUtilisateur = jfc.showDialog(null, "Choisir un fichier");
            File fichier = recupFichierChoisi(actionUtilisateur, jfc);
            if (fichier != null) {
                cartonActif = new Carton(fichier);
                afficherUnCarton(cartonActif);
                jLblChoixCarton.setText("Carton: " + fichier.getPath());
                this.jBtnChargerTirage.setEnabled(true);
            }
        } else if (e.getSource() == jBtnQuitter) {
            System.exit(0);
        } else if (e.getSource() == jBtnChargerTirage) {
            int actionUtilisateur = jfc.showDialog(null, "Choisir un tirage");
            File fichier = recupFichierChoisi(actionUtilisateur, jfc);
            if (fichier != null) {
                this.listeTirage = scannerTirage(fichier);
                jLblTirage.setText("Tirage: " + fichier.getPath());
                jBtnExecuterTirage.setEnabled(true);
                jBtnAfficher.setEnabled(true);
            }
        } else if (e.getSource() == jBtnAfficher) {
            String tirageStr = String.valueOf(this.listeTirage.get(0));
            for (int i = 1; i < this.listeTirage.size(); i++) {
                tirageStr += ", " + this.listeTirage.get(i);
            }
            jTa.setText("Voici le tirage en cours\n\n"+ tirageStr);
            System.out.println("hello");
        } else if (e.getSource() == jBtnExecuterTirage) {
            int nbLigneRemplie = verifLesLignes();
            afficherMessage(nbLigneRemplie);
        } else if (e.getSource() == jBtnVider) {
            jTa.removeAll();
            jTa.setText("");
            repaint();
            revalidate();
        } else if (e.getSource() == jBtnStat) {
            jTa.removeAll();
            File fichier = new File("Data");
            Map<String, Integer> resultat = new HashMap<>();
            resultat.put("carton", 0);
            resultat.put("quine", 0);
            resultat.put("tirage", 0);
            compterQuines(fichier, resultat);
            compterNbCartonEtTirage(fichier, resultat);
            jTa.setText("Statistiques sur le répertoire " + fichier.getPath() + "\n"
                    + "\nNombre de carton: " + resultat.get("carton")
                    + "\nNombre de tirages: " + resultat.get("tirage")
                    + "\nNombre de quines: " + resultat.get("quine"));
            repaint();
            revalidate();
        } else if (e.getSource() == jCbMode) {
            if (modeManuel) {
                modeManuel = false;
            } else {
                modeManuel = true;
            }
        } else {
            if (modeManuel) {
                if (e.getActionCommand() != "") {
                    LotooButton boutonClique = (LotooButton) e.getSource();
                    if (boutonClique.isValSortie()) {
                        boutonClique.setValSortie(false);
                        messageModeManuel();
                    } else {
                        boutonClique.setValSortie(true);
                        messageModeManuel();
                    }
                }
            }
        }
    }

    private void compterQuines(File fichier, Map<String, Integer> resultat) {
        for (File f : fichier.listFiles()) {
            if (f.isDirectory()) {
                compterQuines(f, resultat);
            } else {
                String nomF = f.getName();
                int index = nomF.lastIndexOf('.');
                String nomFichier = nomF.substring(0, index - 1);
                if (nomFichier.toLowerCase().contains("quine")) {
                    resultat.put("quine", resultat.get("quine") + 1);
                }
            }
        }
    }

    private void compterNbCartonEtTirage(File fichier, Map<String, Integer> resultat) {
        for (File f : fichier.listFiles()) {
            if (f.isDirectory()) {
                compterNbCartonEtTirage(f, resultat);
            } else {
                String nomF = f.getName();
                int index = nomF.lastIndexOf('.');
                String extFichier = nomF.substring(index + 1);
                if (resultat.containsKey(extFichier)) {
                    resultat.put(extFichier, resultat.get(extFichier) + 1);
                }
            }
        }
    }

    private void messageModeManuel() {
        int nbLigneRemplies = 0;
        for (LotooButton[] lBtn : cartonActif.getListeCases()) {
            int nbCaseRemplie = 0;
            for (LotooButton btn : lBtn) {
                if (btn.isValSortie()) {
                    nbCaseRemplie++;
                }
            }
            System.out.println(nbCaseRemplie);
            if (isRempli(nbCaseRemplie)) {
                nbLigneRemplies++;
            }
        }
        System.out.println(nbLigneRemplies);
        afficherMessage(nbLigneRemplies);
    }

    private boolean isRempli(int nbCaseRemplie) {
        if (nbCaseRemplie == Carton.NB_LIGNES_A_REMPLIR){
            return true;
        }
        return false;
    }

    private void afficherMessage(int nbLigneRemplie) {
        jTa.removeAll();
        if (nbLigneRemplie > 0) {
            if (nbLigneRemplie == 1){
                jTa.setText("QUINE !");
            } else if(nbLigneRemplie == 2) {
                jTa.setText("DOUBLE QUINE !");
            } else {
                jTa.setText("CARTON PLEIN !!");
            }
        }
        repaint();
        revalidate();
    }

    private int verifLesLignes() {
        int nbLigneRemplie = 0;
        LotooButton[][] listeBouton = cartonActif.getListeCases();
        for (LotooButton[] ligneB : listeBouton) {
            int nbCaseRemplie = 0;
            for (LotooButton b : ligneB) {
                if(b.getText() != "") {
                    if (listeTirage.contains(Integer.parseInt(b.getText()))) {
                        b.setValSortie(true);
                        nbCaseRemplie++;
                    }
                }
            }
            if (isRempli(nbCaseRemplie)){
                nbLigneRemplie++;
            }
        }
        return nbLigneRemplie;
    }

    private static ArrayList scannerTirage(File fichier) {
        ArrayList<Integer> listeTirage = new ArrayList<>();
        try {
            Scanner sc = new Scanner(fichier);
            /*while (sc.hasNextLine()){
                Scanner scLigne = new Scanner(sc.nextLine());*/
                while (sc.hasNextInt()) {
                    listeTirage.add(sc.nextInt());
                }
            /*}*/
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
        return listeTirage;
    }

    private void afficherUnCarton(Carton c) {
        LotooButton[][] listeBtn = c.getListeCases();
        jPanCarton.removeAll();
        for (int i = 0; i < Carton.NB_LIGNES; i++) {
            for (int j = 0; j < Carton.NB_COLONNES; j++) {
                LotooButton btn = listeBtn[i][j];
                jPanCarton.add(btn);
                btn.addActionListener(this);
            }
        }
        revalidate();
        repaint();
    }

    private static File recupFichierChoisi(int actionUtilisateur, JFileChooser jfc) {
        File fichier;
        if (actionUtilisateur == JFileChooser.APPROVE_OPTION) {
            fichier = jfc.getSelectedFile();
        } else {
            fichier = null;
        }
        return fichier;
    }

    // NE PAS MODIFIER !
    private void afficher(String s, JTextArea jta)
    {
      jta.append(s + System.lineSeparator());
    }

    static String findExtension(File f)
    {
        if ( f != null )
        {
            String s = f.getName();
            int posDernierPoint = s.lastIndexOf('.');
            if ( posDernierPoint == -1 ) // le fichier n'a pas d'extension
                return "";
            else
                return s.substring(posDernierPoint+1).toLowerCase();
        }
        return "";
    }
}
