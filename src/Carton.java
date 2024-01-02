import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class Carton
{
    public static final int NB_LIGNES = 3;
    public static final int NB_COLONNES = 9;
    public static final int NB_LIGNES_A_REMPLIR = 5;


    LotooButton[][] listeCases = new LotooButton[NB_LIGNES][NB_COLONNES];

    public Carton() {
    }

    public Carton(File fichier) {
        try {
            Scanner sc = new Scanner(fichier);
            int ligne = 0;
            while (sc.hasNextLine()){
                int colonne = 0;
                Scanner scLigne = new Scanner(sc.nextLine());
                while (scLigne.hasNextInt()) {
                    int num = scLigne.nextInt();
                    if (num == 0){
                        LotooButton btn = new LotooButton("0");
                        this.listeCases[ligne][colonne] = btn;
                    } else {
                        LotooButton btn = new LotooButton(String.valueOf(num));
                        this.listeCases[ligne][colonne] = btn;
                    }
                    colonne++;
                }
                ligne++;
            }
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    public LotooButton[][] getListeCases() {
        return listeCases;
    }

    //Méthodes probablement pas utiles
    public static int intToRow(int i)
    {
        return i / NB_COLONNES;
    }

    public static int intToColumn(int i)
    {
        return i % NB_COLONNES;
    }

    public static int coordToInt(int row, int column)
    {
        return row * NB_COLONNES + column;
    }
}
