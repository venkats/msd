package game;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URI;
import java.util.Scanner;

public class AgileDeveloperSpellChecker implements SpellChecker {
  public String getResponse(String word) throws IOException {
    var url = "http://agiledeveloper.com/spellcheck?check=" + word;

    try(var scanner = new Scanner(URI.create(url).toURL().openStream())) {
      return scanner.nextLine();
    }
  }

  public boolean isSpellingCorrect(String word) {
    try {
      return getResponse(word).equals("true");
    } catch (IOException ex) {
      throw new RuntimeException(ex.getMessage(), ex);
    }
  }
}
