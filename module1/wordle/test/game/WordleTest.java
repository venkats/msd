package game;

import org.junit.jupiter.api.Test;

import java.util.List;
import static game.Wordle.tally;
import static game.Match.*;
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
}
