package game;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;
import static game.Wordle.*;
import static game.Match.*;
import static game.Status.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

public class WordleTest {
  private SpellChecker spellChecker;

  @BeforeEach
  void init() {
    spellChecker = mock(SpellChecker.class);

    when(spellChecker.isSpellingCorrect(anyString())).thenReturn(true);

    Wordle.setSpellCheckerService(spellChecker);
  }

  @Test
  void canary(){
    assertTrue(true);
  }

  @Test
  void tallyForTargetFAVORAndGuessFAVOR(){
    var response = tally("FAVOR", "FAVOR");

    assertEquals(List.of(EXACT, EXACT, EXACT, EXACT, EXACT), response);
  }

  @Test
  void tallyForTargetFAVORAndGuessTESTS(){
    var response = tally("FAVOR", "TESTS");

    assertEquals(List.of(NO, NO, NO, NO, NO), response);
  }

  @Test
  void tallyForTargetFORAndGuessFAVOR(){
    var ex = assertThrows(RuntimeException.class, () -> tally("FOR", "FAVOR"));

    assertEquals("Target length should be 5", ex.getMessage());
  }

  @Test
  void tallyForTargetFAVORAndGuessFOR(){
    var ex = assertThrows(RuntimeException.class, () -> tally("FAVOR", "FOR"));

    assertEquals("Guess length should be 5", ex.getMessage());
  }

  @Test
  void testsForTally(){
    assertAll(
      () -> assertEquals(List.of(EXISTS, EXACT, NO, NO, NO), tally("FAVOR", "RAPID")),

      () -> assertEquals(List.of(NO, EXACT, NO, EXACT, EXACT), tally("FAVOR", "MAYOR")),
      () -> assertEquals(List.of(NO, NO, EXACT, NO, EXACT), tally("FAVOR", "RIVER")),
      () -> assertEquals(List.of(EXISTS, NO, NO, NO, NO), tally("FAVOR", "AMAST")),
      () -> assertEquals(List.of(EXACT, EXACT, EXACT, EXACT, EXACT), tally("SKILL", "SKILL")),
      () -> assertEquals(List.of(EXACT, NO, EXACT, NO, EXACT), tally("SKILL", "SWIRL")),
      () -> assertEquals(List.of(NO, EXISTS, NO, NO, EXACT), tally("SKILL", "CIVIL")),
      () -> assertEquals(List.of(EXACT, NO, EXACT, NO, NO), tally("SKILL", "SHIMS")),
      () -> assertEquals(List.of(EXACT, EXISTS, EXISTS, EXACT, NO), tally("SKILL", "SILLY")),
      () -> assertEquals(List.of(EXACT, EXISTS, EXACT, NO, NO), tally("SKILL", "SLICE")),
      () -> assertEquals(List.of(EXISTS, NO, EXISTS, EXISTS, NO), tally("SAGAS", "ABASE"))
    );
  }

  @Test
  void playFirstAttemptWithWinningGuess(){
    var response = play("FAVOR", "FAVOR", 0);

    assertEquals(
      new Response(1, WON, List.of(EXACT, EXACT, EXACT, EXACT, EXACT), "Amazing"),
      response);
  }

  @Test
  void playFirstAttemptInvalidGuess(){
    var ex = assertThrows(RuntimeException.class, () -> play("FAVOR", "FOR", 1));

    assertEquals("Guess length should be 5", ex.getMessage());
  }

  @Test
  void playFirstAttemptWithNonWinningGuess(){
    var response = play("FAVOR", "RIVER", 0);

    assertEquals(
      new Response(1, INPROGRESS, List.of(NO, NO, EXACT, NO, EXACT), ""),
      response);
  }

  @Test
  void playSecondAttemptWithWinningGuess(){
    var response = play("FAVOR", "FAVOR", 1);

    assertEquals(
      new Response(2, WON, List.of(EXACT, EXACT, EXACT, EXACT, EXACT), "Splendid"),
      response);
  }

  @Test
  void playSecondAttemptWithNonWinningGuess(){
    var response = play("FAVOR", "RIVER", 1);

    assertEquals(
      new Response(2, INPROGRESS, List.of(NO, NO, EXACT, NO, EXACT), ""),
      response);
  }

  @Test
  void playThirdAttemptWithWinningGuess(){
    var response = play("FAVOR", "FAVOR", 2);

    assertEquals(
      new Response(3, WON, List.of(EXACT, EXACT, EXACT, EXACT, EXACT), "Awesome"),
      response);
  }

  @Test
  void playThirdAttemptWithNonWinningGuess(){
    var response = play("FAVOR", "RIVER", 2);

    assertEquals(
      new Response(3, INPROGRESS, List.of(NO, NO, EXACT, NO, EXACT), ""),
      response);
  }

  @Test
  void playFourthAttemptWithWinningGuess(){
    var response = play("FAVOR", "FAVOR", 3);

    assertEquals(
      new Response(4, WON, List.of(EXACT, EXACT, EXACT, EXACT, EXACT), "Yay"),
      response);
  }

  @Test
  void playFourthAttemptWithNonWinningGuess(){
    var response = play("FAVOR", "RIVER", 3);

    assertEquals(
      new Response(4, INPROGRESS, List.of(NO, NO, EXACT, NO, EXACT), ""),
      response);
  }

  @Test
  void playFiftyAttemptWithWinningGuess(){
    var response = play("FAVOR", "FAVOR", 4);

    assertEquals(
      new Response(5, WON, List.of(EXACT, EXACT, EXACT, EXACT, EXACT), "Yay"),
      response);
  }

  @Test
  void playFifthAttemptWithNonWinningGuess(){
    var response = play("FAVOR", "RIVER", 4);

    assertEquals(
      new Response(5, INPROGRESS, List.of(NO, NO, EXACT, NO, EXACT), ""),
      response);
  }

  @Test
  void playSixthAttemptWithWinningGuess(){
    var response = play("FAVOR", "FAVOR", 5);

    assertEquals(
      new Response(6, WON, List.of(EXACT, EXACT, EXACT, EXACT, EXACT), "Yay"),
      response);
  }

  @Test
  void playSixthAttemptWithNonWinningGuess(){
    var response = play("FAVOR", "RIVER", 5);

    assertEquals(
      new Response(6, LOST, List.of(NO, NO, EXACT, NO, EXACT), ""),
      response);
  }

  @Test
  void playSeventhAttemptWithWinningGuess(){
    var ex = assertThrows(RuntimeException.class,
      () -> play("FAVOR", "FAVOR", 6));

    assertEquals("Game Over", ex.getMessage());
  }

  @Test
  void playEightAttemptWithNonWinningGuess(){
    var ex = assertThrows(RuntimeException.class,
      () -> play("FAVOR", "RAPID", 7));

    assertEquals("Game Over", ex.getMessage());
  }

  @Test
  void playFirstAttemptWithCorrectSpellingForGuess(){
    var response = play("FAVOR", "RIVER", 0);

    assertEquals(
      new Response(1, INPROGRESS, List.of(NO, NO, EXACT, NO, EXACT), ""),
      response);

    verify(spellChecker, times(1)).isSpellingCorrect("RIVER");
  }

  @Test
  void playFirstAttemptWithIncorrectSpellingForGuess(){
    when(spellChecker.isSpellingCorrect("RIVER")).thenReturn(false);

    var response = play("FAVOR", "RIVER", 0);

    assertEquals(
      new Response(0, WRONGSPELLING, List.of(NO, NO, NO, NO, NO), "Incorrect spelling"),
      response);

    verify(spellChecker, times(1)).isSpellingCorrect("RIVER");
  }

  @Test
  void playSecondAttemptWithIncorrectSpellingForGuess(){
    when(spellChecker.isSpellingCorrect("RIVER")).thenReturn(false);

    var response = play("FAVOR", "RIVER", 1);

    assertEquals(
      new Response(1, WRONGSPELLING, List.of(NO, NO, NO, NO, NO), "Incorrect spelling"),
      response);

    verify(spellChecker, times(1)).isSpellingCorrect("RIVER");
  }

  @Test
  void playSecondAttemptWithFailureToCheckSpelling(){
    when(spellChecker.isSpellingCorrect("RIVER")).thenThrow(new RuntimeException("Network failure"));

    var ex =
      assertThrows(RuntimeException.class, () -> play("FAVOR", "RIVER", 1));

    assertEquals("Network failure", ex.getMessage());

    verify(spellChecker, times(1)).isSpellingCorrect("RIVER");
  }
}
