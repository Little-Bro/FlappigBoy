public class Objet {
  int x, y;
  int c;
  boolean malus;
  double rng;

  public Objet(double f) {
    x = 700;
    y = 400;
    c = 30;
    rng = Math.random();

    // Attribution du rôle malus/bonus
    if (rng < f)
      malus = true;
    else
      malus = false;
  }

  // déplacement sinusoïdal
  public void bouge(int tps) {
    x -= 10;
    y += 10 * Math.sin(tps * 0.1);
  }

  // Collision avec le joueur
  public boolean collision(Boule b) {
    if (b.x + b.cote > x && b.x < x + c) {
      if (b.y + b.cote > y && b.y < y + c) {
        return true;
      } else
        return false;
    } else
      return false;
  }
}