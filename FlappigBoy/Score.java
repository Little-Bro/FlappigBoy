import java.io.*;

public class Score {
  BufferedWriter highscore;
  int meilleurscore = 0;
  File file = new File("historiques_scores.txt");

  public Score() {
    try {
      highscore = new BufferedWriter(new FileWriter(file));
      highscore.write("HIGHSCORE");
      highscore.newLine();
      highscore.newLine();
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  public void liste(int score, String username, int niveau) {
    if (score > meilleurscore)
      meilleurscore = score;
    try {
      highscore.write("Joueur : " + username + " : ");
      highscore.write(Integer.toString(score));
      switch (niveau) {
        case 1:
          highscore.write("   (Niveau facile)");
          break;
        case 2:
          highscore.write("   (Niveau moyen !)");
          break;
        case 3:
          highscore.write("   (Niveau difficile !!)");
          break;

      }
      highscore.newLine();
      highscore.flush();
    } catch (Exception excep) {
      excep.printStackTrace();
    }
  }
}

/*
 * try ( BufferedWriter bw = new BufferedWriter(new FileWriter(file)) ) { for
 * (int prime = 2; prime < 10000; ++prime) { boolean isPrime = true; final int
 * limit = (int) sqrt(prime); // for (int i = 2; i <= limit && isPrime; i++) {
 * isPrime = prime % i != 0; } if (isPrime){ bw.write(String.valueOf(prime));
 * bw.newLine(); } } } catch (IOException ex) { e.printStackTrace(); }
 */