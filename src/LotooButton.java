import java.awt.*;
import javax.swing.Action;
import javax.swing.Icon;
import javax.swing.JButton;

public class LotooButton extends JButton
{
    private static final Font lotooFont = new Font("Segoe Script", 1, 24);
    private static final Color MY_GREEN = new Color(0,140, 0);

    private int valeur;
    private boolean valSortie;
    private int numLigne;

    public LotooButton(String text, Icon icon) {
        super(text, icon);
        setFont(lotooFont);
        valSortie = false;
        valeur  = -1;
        numLigne = -1;
        setForeground(MY_GREEN);
    }

    //NE PAS MODIFIER CE QUI SUIT !
    public LotooButton() {
        this((String) null, (Icon) null);
    }

    public LotooButton(Action a) {
        this();
        setAction(a);
    }

    public LotooButton(Icon icon) {
        this((String) null, icon);
    }

    public LotooButton(String text) {
        this(text, (Icon) null);
    }

    public void setText(String s)
    {
        if ( s.equals("0") || s.equals("") )
        {
            super.setText("");
            setBackground(MY_GREEN);
        }
        else
            super.setText(s);
    }

    public void setValeur(int v)
    {
        valeur = v;
        setText(valeur+"");
    }

    public int getValeur()
    {
        return valeur;
    }

    public boolean isValSortie()
    {
        return valSortie;
    }

    public void setValSortie(boolean valSortie)
    {
        this.valSortie = valSortie;
        if ( valSortie )
            setForeground(Color.RED);
        else
            setForeground(MY_GREEN);
    }

    public void setNumLigne(int numLigne)
    {
        this.numLigne = numLigne;
    }
    public int getNumLigne()
    {
        return numLigne;
    }
}











