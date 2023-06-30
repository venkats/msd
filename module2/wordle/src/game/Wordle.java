package game;

import java.util.List;
import java.util.stream.IntStream;

import static game.Match.*;
import static game.Status.*;

enum Match { EXACT, NO, EXISTS }
enum Status { WON, INPROGRESS };

public class Wordle {
  private static final int WORD_SIZE = 5;

  public static List<Match> tally(String target, String guess) {
    if(target.length() != WORD_SIZE) {
      throw new RuntimeException("Target length should be " + WORD_SIZE);
    }

    if(guess.length() != WORD_SIZE) {
      throw new RuntimeException("Guess length should be " + WORD_SIZE);
    }

    return IntStream.range(0, WORD_SIZE)
      .mapToObj(index -> tallyAtPosition(target, guess, index))
      .toList();
  }

  private static Match tallyAtPosition(String target, String guess, int index) {
    if(target.charAt(index) == guess.charAt(index)) {
      return EXACT;
    }

    var letterAtIndex = guess.charAt(index);

    var numberOfOccurrencesInGuessUntilIndex = IntStream.rangeClosed(0, index)
      .filter(i -> guess.charAt(i) == letterAtIndex)
      .count();

    var numberOfOccurrencesInTarget = IntStream.range(0, WORD_SIZE)
      .filter(i -> target.charAt(i) == letterAtIndex)
      .count();

    var numberOfPositionalMatches = IntStream.range(0, WORD_SIZE)
      .filter(i -> target.charAt(i) == letterAtIndex)
      .filter(i -> guess.charAt(i) == letterAtIndex)
      .count();

    if(numberOfOccurrencesInGuessUntilIndex <= numberOfOccurrencesInTarget - numberOfPositionalMatches) {
      return EXISTS;
    }

    return NO;
  }

  public static Response play(String target, String guess, int numberOfAttempts) {
    var tallyResult = tally(target, guess);

    var won = tallyResult.stream().allMatch(match -> match.equals(EXACT));

    var message = won ? List.of("Amazing", "Splendid", "Awesome", "Yay", "Yay", "Yay").get(numberOfAttempts) : "";

    var status = won ? WON : INPROGRESS;

    return new Response(numberOfAttempts + 1, status, tallyResult, message);
  }
}
