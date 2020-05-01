public class Nuage {
  int x;
  int y;
  int vitesse;
  double rng;
  boolean petit;

  public Nuage() {
    x = 700;
    // initialisation d'une poisition y et d'une vitesse aléatoires
    y = (int) (Math.random() * 400);
    vitesse = (int) (Math.random() * 5);
    // attribution de la taille du nuage (choix de l'image utilisée)
    rng = Math.random();
    if (rng > 0.2)
      petit = true;
    else
      petit = false;
  }

  public void bouge() {
    x -= vitesse;
  }
}