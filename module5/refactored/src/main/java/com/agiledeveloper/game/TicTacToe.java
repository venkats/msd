package com.agiledeveloper.game;

public class TicTacToe {
  public enum Peg { EMPTY, X, O }

  private final int SIZE = 3;

  private Peg[][] pegs = new Peg[SIZE][SIZE];

  public TicTacToe() {
    for (int i = 0; i < SIZE; i++) {
      for (int j = 0; j < SIZE; j++) {
         pegs[i][j] = Peg.EMPTY;
      }
    }
  }

  TicTacToe(Peg[][] givePegs) {
    pegs = givePegs;
  }

  public boolean isGameWon() {
    return checkRowMatch() || checkColumnMatch() || checkDiagonalsMatch();
  }

  private boolean checkDiagonalsMatch() {
    return checkTopLeftToBottomRightDiagonalMatch() || checkBottomLeftToTopRightDiagonalMatch();
  }

  private boolean checkBottomLeftToTopRightDiagonalMatch() {
    return pegs[2][0] == pegs[1][1] && pegs[2][0] == pegs[0][2] && pegs[2][0] != Peg.EMPTY;
  }

  private boolean checkTopLeftToBottomRightDiagonalMatch() {
    return pegs[0][0] == pegs[1][1] && pegs[0][0] == pegs[2][2] && pegs[0][0] != Peg.EMPTY;
  }

  private boolean checkColumnMatch() {
    for (int j = 0; j < SIZE; j++) {
      if(pegs[0][j] == pegs[1][j] && pegs[0][j] == pegs[2][j] && pegs[0][j] != Peg.EMPTY) {
        return true;
      }
    }

    return false;
  }

  private boolean checkRowMatch() {
    for (int i = 0; i < SIZE; i++) {
      if(pegs[i][0] == pegs[i][1] && pegs[i][0] == pegs[i][2] && pegs[i][0] != Peg.EMPTY) {
        return true;
      }
    }

    return false;
  }
}
