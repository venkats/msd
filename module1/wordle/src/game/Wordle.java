package game;

import java.util.List;

import static game.Match.*;

enum Match { EXACT, NO }

public class Wordle {
  public static List<Match> tally(String target, String guess) {
    if(guess.equals(target)) {
      return List.of(EXACT, EXACT, EXACT, EXACT, EXACT);
    }

    return List.of(NO, NO, NO, NO, NO);
  }
}
