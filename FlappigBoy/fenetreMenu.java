import javax.swing.*;
import java.awt.event.*;
import java.awt.*;

public class fenetreMenu extends JFrame implements ActionListener {
  private JButton bout4;
  private JTextField text1;
  private JButton bout5;
  private JButton bout3;
  private JButton bout2;
  private JButton bout1;
  private int niveau;
  private String name;
  private Color madagascar = new Color(255, 192, 203);
  private Color selection = new Color(144, 238, 144);
  Score historique;
  Image pig = Toolkit.getDefaultToolkit().getImage("pigFinal2.png");

  public fenetreMenu(Score historique) {
    this.historique = historique;
    setTitle("Menu");
    setSize(700, 800);
    setLocation(300, 50);
    setIconImage(pig);

    // création des JLabels
    JLabel monEtiquette = new JLabel();
    monEtiquette.setIcon(new ImageIcon("imageCiel.jpg"));
    monEtiquette.setBounds(0, 0, 700, 800);

    JLabel regles = new JLabel(
        "<html>Regles du jeu:<br> <br> - Entrer le pseudo et valider<br>- Choisir le niveau et appuyer sur le bouton Jouer ! <br> - Faire bouger le cochon a partir de la touche Espace afin de passer entre les tuyaux et eviter le sol</html> ");
    regles.setBounds(10, 70, 500, 200);
    add(regles);

    JLabel label1 = new JLabel("Choix du niveau");
    label1.setBounds(10, 270, 150, 50);
    add(label1);

    text1 = new JTextField("Pseudo");
    text1.setBounds(10, 10, 150, 50);
    add(text1);

    // creation des boutons
    bout1 = new JButton("Facile");
    bout1.setBounds(40, 330, 150, 50);
    bout1.setBackground(madagascar);
    bout1.setForeground(Color.white);
    add(bout1);
    bout1.addActionListener(this);

    bout2 = new JButton("Moyen");
    bout2.setBounds(270, 330, 150, 50);
    bout2.setBackground(madagascar);
    bout2.setForeground(Color.white);
    add(bout2);
    bout2.addActionListener(this);

    bout3 = new JButton("Difficile");
    bout3.setBounds(500, 330, 150, 50);
    bout3.setBackground(madagascar);
    bout3.setForeground(Color.white);
    add(bout3);
    bout3.addActionListener(this);

    bout4 = new JButton("Jouer !");
    bout4.setBounds(230, 500, 250, 100);
    bout4.setBackground(madagascar);
    bout4.setForeground(Color.white);
    Font police1 = new Font(" Arial ", Font.BOLD, 45);
    bout4.setFont(police1);
    add(bout4);
    bout4.addActionListener(this);

    bout5 = new JButton("Valider");
    bout5.setBounds(170, 10, 100, 50);
    add(bout5);
    bout5.addActionListener(this);
    bout5.setBackground(madagascar);
    bout5.setForeground(Color.white);

    JPanel panel = new JPanel();
    panel.setLayout(null);
    panel.setBounds(0, 0, 700, 800);
    panel.add(text1);
    panel.add(regles);
    panel.add(label1);
    panel.add(bout1);
    panel.add(bout2);
    panel.add(bout3);
    panel.add(bout4);
    panel.add(monEtiquette);
    add(panel);

    setVisible(true);
    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
  }

  // gestion des boutons
  public void actionPerformed(ActionEvent e) {
    if (e.getSource() == bout2) {
      niveau = 2;
      bout2.setBackground(selection);
      bout1.setBackground(madagascar);
      bout3.setBackground(madagascar);
    }
    if (e.getSource() == bout3) {
      niveau = 3;
      bout3.setBackground(selection);
      bout2.setBackground(madagascar);
      bout1.setBackground(madagascar);
    }
    if (e.getSource() == bout1) {
      niveau = 1;
      bout2.setBackground(madagascar);
      bout1.setBackground(selection);
      bout3.setBackground(madagascar);
    }
    if (e.getSource() == bout5) {
      name = text1.getText();

    }
    if (e.getSource() == bout4) {
      if (niveau != 0 && name != null) {
        setVisible(false);
        fenetreJeu jeu = new fenetreJeu(niveau, name, historique);
      } else
        JOptionPane.showMessageDialog(this,
            "Il faut choisir un niveau de difficulté et valider son pseudo avant de commencer !");
    }
  }
}