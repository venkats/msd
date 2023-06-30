package game;

import java.util.List;
import java.util.stream.IntStream;

import static game.Match.*;
import static game.Status.*;

enum Match { EXACT, NO, EXISTS }
enum Status { WON, INPROGRESS, LOST, WRONGSPELLING };

public class Wordle {
  private static final int WORD_SIZE = 5;
  private static final int MAX_ATTEMPTS = 6;
  private static SpellChecker spellChecker;

  public static void setSpellCheckerService(SpellChecker aSpellChecker) {
    spellChecker = aSpellChecker;
  }

  public static List<Match> tally(String target, String guess) {
    checkTargetWordLength(target);

    checkGuessWordLength(guess);

    return composeTallyAtEachPosition(target, guess);
  }

  private static List<Match> composeTallyAtEachPosition(String target, String guess) {
    return IntStream.range(0, WORD_SIZE)
      .mapToObj(index -> tallyAtPosition(target, guess, index))
      .toList();
  }

  private static void checkGuessWordLength(String guess) {
    if(guess.length() != WORD_SIZE) {
      throw new RuntimeException("Guess length should be " + WORD_SIZE);
    }
  }

  private static void checkTargetWordLength(String target) {
    if(target.length() != WORD_SIZE) {
      throw new RuntimeException("Target length should be " + WORD_SIZE);
    }
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
    if(!spellChecker.isSpellingCorrect(guess)) {
      return new Response(numberOfAttempts, WRONGSPELLING, List.of(NO, NO, NO, NO, NO), "Incorrect spelling");
    }

    if(numberOfAttempts >= MAX_ATTEMPTS) {
      throw new RuntimeException("Game Over");  
    }
    
    var tallyResult = tally(target, guess);

    var won = tallyResult.stream().allMatch(match -> match.equals(EXACT));

    var message = won ? List.of("Amazing", "Splendid", "Awesome", "Yay", "Yay", "Yay").get(numberOfAttempts) : "";

    var status = won ? WON : numberOfAttempts == 5 ? LOST : INPROGRESS;

    return new Response(numberOfAttempts + 1, status, tallyResult, message);
  }
}
