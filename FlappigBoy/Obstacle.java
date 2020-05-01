
import java.awt.*;

public class Obstacle {
  // Attributs d'un obstacle
  public int x;
  public int vitesse;
  public int largeur;
  public int hauteur;
  public int L1; // longueur du tuyau du haut
  public int L2; // longueur du tuyau du bas
  public int distance; // la distance entre les deux tuyaux

  public Obstacle(int hauteur, int distance, int largeur, int vitesse) {
    this.largeur = largeur;
    this.distance = distance;
    this.vitesse = vitesse;
    this.hauteur = hauteur;
    x = 700;

    // relation entre le tuyau du bas et du haut
    while (L1 < 50) {
      L1 = (int) (Math.random() * (hauteur - distance - 40));
    }
    L2 = hauteur - (L1 + distance);
  }

  // affichage des obstacles
  public void dessine(Graphics g) {
    g.setColor(new Color(60, 190, 70));
    g.fillRect(x, 0, largeur, L1);
    g.fillRect(x, hauteur - L2, largeur, L2);

    // contours noirs
    g.setColor(Color.black);
    g.drawLine(x, 0, x, L1);
    g.drawLine(x, L1, x + largeur, L1);
    g.drawLine(x + largeur, L1, x + largeur, 0);
    g.drawLine(x, 800, x, hauteur - L2);
    g.drawLine(x, hauteur - L2, x + largeur, hauteur - L2);
    g.drawLine(x + largeur, hauteur - L2, x + largeur, 800);
    g.drawLine(x, L1 - 40, x + largeur, L1 - 40);
    g.drawLine(x, hauteur - L2 + 40, x + largeur, hauteur - L2 + 40);
  }

  // relation entre la vitesse du tuyau et sa position
  public void bouge() {
    x += -vitesse * 30 * 0.001;
  }

  // condition de collision avec le joueur
  public boolean collision(Boule b) {
    if (!b.invincible) {
      if (b.x + b.cote > x && b.x < x + largeur) {
        if (b.y < L1 || b.y + b.cote > L1 + distance)
          return true;
        else
          return false;
      } else
        return false;
    } else
      return false;
  }
}