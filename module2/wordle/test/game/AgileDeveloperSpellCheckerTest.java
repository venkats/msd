package game;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class AgileDeveloperSpellCheckerTest {
  private AgileDeveloperSpellChecker agileDeveloperSpellChecker;

  @BeforeEach
  void init() {
    agileDeveloperSpellChecker = spy(new AgileDeveloperSpellChecker());
  }

  @Test
  void getResponseFromServiceForAWord() throws IOException {
    assertTrue(agileDeveloperSpellChecker.getResponse("RIVER").length() > 0);
  }

  @Test
  void isSpellingCorrectForCorrectSpelling() throws IOException {
    assertTrue(agileDeveloperSpellChecker.isSpellingCorrect("good"));

    verify(agileDeveloperSpellChecker, times(1)).getResponse("good");
  }

  @Test
  void isSpellingCorrectForIncorrectSpelling() throws IOException {
    assertFalse(agileDeveloperSpellChecker.isSpellingCorrect("gddo"));

    verify(agileDeveloperSpellChecker, times(1)).getResponse("gddo");
  }

  @Test
  void isSpellingCorrectWithException() throws IOException {
    when(agileDeveloperSpellChecker.getResponse("gddo")).thenThrow(new IOException("Network failure"));

    var ex = assertThrows(RuntimeException.class, () -> agileDeveloperSpellChecker.isSpellingCorrect("gddo"));

    assertEquals("Network failure", ex.getMessage());
    verify(agileDeveloperSpellChecker, times(1)).getResponse("gddo");
  }
}
