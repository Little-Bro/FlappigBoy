import javax.swing.*;
import java.awt.event.*;
import java.awt.*;
import javax.swing.JLabel;

public class fenetreGameOver extends JFrame implements ActionListener {
  private JButton Bouton1;
  private JButton Bouton2;
  int score = 0;
  int niveau;
  String name;
  Score historique;
  Image pig = Toolkit.getDefaultToolkit().getImage("pigFinal2.png");

  public fenetreGameOver(int score, String name, int niveau, Score historique) {
    this.score = score;
    this.niveau = niveau;
    this.name = name;
    this.historique = historique;
    historique.liste(score, name, niveau);

    // Création de la fenêtre
    setTitle("Game Over!");
    setSize(700, 800);
    setLocation(300, 50);
    setIconImage(pig);

    // Création des boutons
    Bouton1 = new JButton("Nouvelle partie");
    Bouton1.setBounds(210, 500, 300, 70);
    Font police = new Font(" Calibri ", Font.BOLD, 20);
    Bouton1.setBackground(new Color(255, 192, 203));
    Bouton1.setForeground(Color.white);
    Bouton1.setFont(police);
    Bouton1.addActionListener(this);
    add(Bouton1);

    Bouton2 = new JButton("Menu");
    Bouton2.setBounds(210, 600, 300, 70);
    Bouton2.setBackground(new Color(255, 192, 203));
    Bouton2.setForeground(Color.white);
    Bouton2.setFont(police);
    Bouton2.addActionListener(this);
    add(Bouton2);

    // Création du texte affiché
    JLabel Texte = new JLabel("SCORE: " + score);
    Texte.setBounds(300, 140, 250, 150);
    Font police1 = new Font("Impact", Font.BOLD, 22);
    Texte.setForeground(Color.black);
    Texte.setFont(police1);
    add(Texte);
    JLabel Texte2 = new JLabel("GAME OVER ! ");
    Texte2.setBounds(250, 100, 250, 150);
    Font police2 = new Font("Impact", Font.BOLD, 30);
    Texte2.setForeground(Color.black);
    Texte2.setFont(police2);
    add(Texte2);

    JLabel Highscore = new JLabel("HIGHSCORE : " + historique.meilleurscore);
    Highscore.setBounds(270, 180, 250, 150);
    Highscore.setForeground(Color.black);
    Highscore.setFont(police1);
    add(Highscore);

    JLabel monEtiquette = new JLabel();
    monEtiquette.setIcon(new ImageIcon("gameOverImage.jpg"));
    monEtiquette.setBounds(0, 0, 700, 800);
    add(monEtiquette);

    // Création du conteneur associé
    JPanel ConteneurMain = new JPanel();
    ConteneurMain.setBounds(0, 0, 400, 400);
    ConteneurMain.setLayout(null);
    ConteneurMain.add(Bouton1);
    ConteneurMain.add(Bouton2);
    ConteneurMain.add(Texte);
    ConteneurMain.add(Texte2);
    ConteneurMain.add(monEtiquette);
    ConteneurMain.setBackground(new Color(109, 234, 255));
    add(ConteneurMain);

    setVisible(true);
    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
  }

  // Intéraction avec les boutons
  public void actionPerformed(ActionEvent e) {
    if (e.getSource() == Bouton1) {
      setVisible(false);
      fenetreJeu jeu = new fenetreJeu(niveau, name, historique);
    }

    else if (e.getSource() == Bouton2) {
      setVisible(false);
      fenetreMenu m = new fenetreMenu(historique);
    }
  }
}