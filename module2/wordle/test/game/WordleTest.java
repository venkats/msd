package game;

import org.junit.jupiter.api.Test;

import java.util.List;
import static game.Wordle.*;
import static game.Match.*;
import static game.Status.*;
import static org.junit.jupiter.api.Assertions.*;

public class WordleTest {
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
}
