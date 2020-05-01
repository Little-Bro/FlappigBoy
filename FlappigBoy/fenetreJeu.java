import javax.swing.*;
import java.awt.event.*;
import java.awt.*;
import javax.swing.Timer;
import java.util.*;
import java.io.*;
import sun.audio.*;

public class fenetreJeu extends JFrame implements KeyListener, ActionListener {
  private static final long serialVersionUID = 1L;
  // objets
  Timer chrono;
  Boule boule = new Boule(350, 350, 2, 700, 500, 40); // (x,y,m,g,vLim,cote)
  Sol sol = new Sol();
  Score historique;

  // variables
  int temps = 0; // variable temporelle
  int instant = 0;
  int score = 0; // score
  int tempsBonus = 0; // gère la durée de l'effet d'in objet
  boolean peutFaireObjet = true; // permet d'éviter de créer de nouveux objets lorsque le joueur est soumis aux
                                 // effets d'un objet
  boolean peutAppuyer = true; // permet de gérer le temps entre chaque saut

  // images
  Image pig = Toolkit.getDefaultToolkit().getImage("pigFinal2.png");
  Image deadPig = Toolkit.getDefaultToolkit().getImage("pigFinalMort.png");
  Image imgSol = Toolkit.getDefaultToolkit().getImage("sol.png");
  Image imgObj = Toolkit.getDefaultToolkit().getImage("objet.png");
  Image imgNuage = Toolkit.getDefaultToolkit().getImage("nuage.png");
  Image imgNuageGrand = Toolkit.getDefaultToolkit().getImage("nuageGrand.png");

  // listes d'objets
  public LinkedList<Obstacle> listeObstacle;
  public LinkedList<Objet> listeObjets;
  public LinkedList<Nuage> listeNuages;

  // Paramètres d'une partie
  String name;
  int niveau;
  int d; // écart vertical entre les tuyaux
  int l; // largeur d'un tuyau
  int vt; // vitesse du tuyau
  int g; // gravité
  int f; // force
  int m;// masse
  int vl; // vitesse limite de la boule
  int t; // intervalle entre chaque tuyau
  double fm;// fréquence de malus
  int fopp; // force gravité inversée
  int gopp; // g gravité inversée

  // constructeur
  public fenetreJeu(int niveau, String name, Score historique) {
    this.historique = historique;
    this.name = name;
    this.niveau = niveau;

    // création de la fenêtre
    setTitle("JEU");
    setSize(700, 800);
    setLocation(300, 50);
    setIconImage(pig);

    // création du chrono
    chrono = new Timer(30, this);
    chrono.start();

    // gestion du clavier
    this.addKeyListener(this);

    // création de la liste d'obstacles
    listeObstacle = new LinkedList<Obstacle>();

    // création de la liste d'objets
    listeObjets = new LinkedList<Objet>();

    // création d'une liste de nuages
    listeNuages = new LinkedList<Nuage>();
    listeNuages.add(new Nuage());

    // paramètres de la fenêtres
    setVisible(true);
    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

    // Variation des paramètres selon le niveau de difficulté
    switch (niveau) {
      case 1:
        d = 250;
        l = 100;
        vt = 233;
        g = 900;
        f = 55000;
        m = 2;
        vl = 450;
        t = 70;
        fm = 0.5;
        break;

      // tuyaux plus rapides, moins de force de "rappel", vitesse limite supérieur,
      // malus plus fréquent
      case 2:
        d = 250;
        l = 100;
        vt = 267;
        g = 900;
        f = 50000;
        m = 2;
        vl = 500;
        t = 60;
        fm = 0.7;
        break;

      // tuyaux plus rapprochés, plus rapide, masse plus élevée, gravité bien plus
      // forte, malus plus fréquent
      case 3:
        d = 200;
        l = 100;
        vt = 400;
        g = 1200;
        f = 50000;
        m = 3;
        vl = 500;
        t = 50;
        fm = 0.8;
        break;
    }
    boule.g = g;
    boule.vLim = vl;
    fopp = -f;
    gopp = -g;
  }

  // chrono
  public void actionPerformed(ActionEvent e) {
    setTitle("Flappig boy !");
    temps++;

    // test GameOver
    if (boule.estMort) {
      jouerSon("Mort.wav");
      chrono.stop();
      setVisible(false);
      fenetreGameOver fen = new fenetreGameOver(score, name, niveau, historique);
    }

    // création des obstacles
    if (temps % t == 0) {
      listeObstacle.add(new Obstacle(800, d, l, vt));
    }

    // gestion du joueur
    boule.bouge(0);

    // test collision avec le sol
    if (boule.y + boule.cote >= 800 - sol.hauteur)
      boule.estMort = true;

    // test pour éviter de créer de nouveaux objets si le joueur est déjà sous
    // l'effet d'un objet
    if (boule.malusActif || boule.invincible)
      peutFaireObjet = false;
    else
      peutFaireObjet = true;

    // création des objets
    if (peutFaireObjet) {
      if (temps % 200 == 0) {
        int rng = (int) (Math.random() * 100);
        if (rng > 1) {
          listeObjets.add(new Objet(fm));
        }
      }
    }

    // création des nuages
    if (temps % 50 == 0) {
      int rng = (int) (Math.random() * 100);
      if (rng > 25)
        listeNuages.add(new Nuage());
    }

    // chrono malus-fin du malus
    if (temps - tempsBonus > 300 && boule.malusActif) {
      jouerSon("finBonus.wav");
      boule.malusActif = false;
      boule.g = g;
      f = -f;
      boule.v = 0;
    }
    // chrono bonus-fin du bonus
    if (temps - tempsBonus > 300 && boule.invincible) {
      jouerSon("finBonus.wav");
      boule.invincible = false;
    }

    // Effet du malus
    if (boule.malusActif) {
      f = fopp;
      boule.g = gopp;
    }

    // Colision avec un objet bonus/malus
    for (Objet obj : listeObjets) {
      obj.bouge(temps);

      if (obj.collision(boule)) {
        listeObjets.remove();
        if (obj.malus) {
          boule.malusActif = true;
          jouerSon("bonusGravite.wav");
        }

        else {
          boule.invincible = true;
          jouerSon("bonusInvincible.wav");
        }
        tempsBonus = temps;
      }
    }

    // Gestion des nuages
    for (int i = 0; i < listeNuages.size(); i++) {
      listeNuages.get(i).bouge();
      if (listeNuages.get(i).x + 300 < 0)
        listeNuages.remove(listeNuages.get(i));
    }

    // gestion des obstacles
    for (int i = 0; i < listeObstacle.size(); i++) {
      listeObstacle.get(i).bouge();

      // score
      if (listeObstacle.get(0).x + listeObstacle.get(0).largeur < boule.x
          && listeObstacle.get(0).x + listeObstacle.get(0).largeur + ((int) (vt * 30 * 0.001) + 1) > boule.x) {
        score++;
        jouerSon("Score.wav");
      }

      // collision
      if (listeObstacle.get(0).collision(boule) && !boule.invincible)
        boule.estMort = true;

      // libération d'espace dans la liste
      if (listeObstacle.get(i).x + listeObstacle.get(i).largeur < 0)
        listeObstacle.remove(listeObstacle.get(i));
    }
    repaint();
  }

  // dessin
  public void paint(Graphics g) {
    // dessin du ciel bleu
    g.setColor(new Color(0, 166, 255));
    g.fillRect(0, 0, this.getWidth(), this.getHeight());

    // dessin des nuages
    for (Nuage n : listeNuages) {
      if (n.petit)
        g.drawImage(imgNuage, n.x, n.y, this);
      else
        g.drawImage(imgNuageGrand, n.x, n.y, this);
    }

    // dessin des obstacles
    for (Obstacle obs : listeObstacle) {
      obs.dessine(g);
    }

    // dessin des objets
    for (int i = 0; i < listeObjets.size(); i++) {
      g.drawImage(imgObj, listeObjets.get(i).x, listeObjets.get(i).y, this);
    }

    // dessin du sol
    g.drawImage(imgSol, 0, 800 - sol.hauteur, this);

    // affichage du score
    g.setColor(Color.white);
    g.setFont(new Font("Impact", Font.PLAIN, 36));
    g.drawString(Integer.toString(score), 340, 100);

    // affichage du texte malus/bonus
    if (boule.malusActif) {
      g.setColor(Color.white);
      g.setFont(new Font("Impact", Font.PLAIN, 36));
      g.drawString(("INVERSION DE LA GRAVITE !"), 100, 150);
    }

    if (boule.invincible) {
      g.setColor(Color.white);
      g.setFont(new Font("Impact", Font.PLAIN, 36));
      g.drawString(("INVINCIBILITE!"), 230, 150);
    }

    // Décompte
    if (temps - tempsBonus > 180 && temps - tempsBonus < 220 && (boule.malusActif || boule.invincible)) {
      g.setColor(Color.red);
      g.setFont(new Font("Impact", Font.PLAIN, 30));
      g.drawString(("3"), 340, 200);
    } else if (temps - tempsBonus > 220 && temps - tempsBonus < 260 && (boule.malusActif || boule.invincible)) {
      g.setColor(Color.red);
      g.setFont(new Font("Impact", Font.PLAIN, 30));
      g.drawString(("2"), 340, 200);
    } else if (temps - tempsBonus > 260 && (boule.malusActif || boule.invincible)) {
      g.setColor(Color.red);
      g.setFont(new Font("Impact", Font.PLAIN, 30));
      g.drawString(("1"), 340, 200);
    }
    // dessin du joueur
    g.drawImage(pig, boule.x, boule.y, this);
    if (boule.estMort)
      g.drawImage(deadPig, boule.x, boule.y, this);
  }

  // gestion de l'appui sur la touche espace
  public void keyPressed(KeyEvent e) {
    if (e.getKeyCode() == KeyEvent.VK_SPACE) {
      // gestion de l'intervalle entre chaque saut
      if (temps - instant > 5) {
        jouerSon("Jump.wav");
        boule.bouge(f);
        instant = temps;
      }
    }
  }

  // gestion du son
  public void jouerSon(String path) {
    InputStream son;
    try {
      son = new FileInputStream(new File(path));
      AudioStream audio = new AudioStream(son);
      AudioPlayer.player.start(audio);
    } catch (Exception e) {
      JOptionPane.showMessageDialog(null, "Erreur son");
    }
  }

  // surcharge des méthodes du clavier
  public void keyReleased(KeyEvent e) {
  }

  public void keyTyped(KeyEvent e) {
  }
}