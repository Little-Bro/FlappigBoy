import java.awt.*;

public class Sol {
  int hauteur;

  public Sol() {
    hauteur = 20;
  }

  public void dessine(Graphics g) {
    g.setColor(Color.green);
    g.fillRect(0, 800 - hauteur, 700, hauteur);
  }
}
