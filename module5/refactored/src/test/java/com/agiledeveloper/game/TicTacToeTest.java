package com.agiledeveloper.game;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import com.agiledeveloper.game.TicTacToe.Peg;

public class TicTacToeTest {
  private final int SIZE = 3;
  private TicTacToe ticTacToe;
  private final Peg EMPTY = Peg.EMPTY;
  private final Peg X = Peg.X;
  private final Peg O = Peg.O;

  private Peg[][] createEmptyPegs() {
    var pegs = new Peg[SIZE][SIZE];

    for (int i = 0; i < SIZE; i++) {
      for (int j = 0; j < SIZE; j++) {
        pegs[i][j] = EMPTY;
      }
    }

    return pegs;
  }

  @Test
  void gameWonByRowMatch(){
    var pegs = createEmptyPegs();

    pegs[1][0] = pegs[1][1] = pegs[1][2] = X;

    ticTacToe = new TicTacToe(pegs);

    assertTrue(ticTacToe.isGameWon());
  }

  @Test
  void gameWonByColumnMatch(){
    var pegs = createEmptyPegs();

    pegs[0][0] = pegs[1][0] = pegs[2][0] = O;

    ticTacToe = new TicTacToe(pegs);

    assertTrue(ticTacToe.isGameWon());
  }

  @Test
  void gameWonByDiagonalMatch(){
    var pegs = createEmptyPegs();

    pegs[0][0] = pegs[1][1] = pegs[2][2] = O;

    ticTacToe = new TicTacToe(pegs);

    assertTrue(ticTacToe.isGameWon());
  }

  @Test
  void gameWonByTheOtherDiagonalMatch(){
    var pegs = createEmptyPegs();

    pegs[2][0] = pegs[1][1] = pegs[0][2] = X;

    ticTacToe = new TicTacToe(pegs);

    assertTrue(ticTacToe.isGameWon());
  }

  @Test
  void gameNotWonWhenAllPegsEmpty(){
    var pegs = createEmptyPegs();

    ticTacToe = new TicTacToe(pegs);

    assertFalse(ticTacToe.isGameWon());
  }

  @Test
  void gameNotWonWhenNoRowOrColumnFilledWithSamePeg(){
    var pegs = createEmptyPegs();

    pegs[0][0] = X; pegs[0][1] = O; pegs[0][2] = X;
    pegs[1][0] = X; pegs[1][1] = O; pegs[1][2] = X;
    pegs[2][0] = O; pegs[2][1] = X; pegs[2][2] = O;

    ticTacToe = new TicTacToe(pegs);

    assertFalse(ticTacToe.isGameWon());
  }
}
