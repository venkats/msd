package com.agiledeveloper.game;

public class TicTacToe {
  private final int SIZE = 3;

  private int[][] pegs = new int[SIZE][SIZE];
  //0 - empty, 1 - X, 2 - O

  public TicTacToe() {
    for (int i = 0; i < SIZE; i++) {
      for (int j = 0; j < SIZE; j++) {
         pegs[i][j] = 0;
      }
    }
  }

  public boolean isGameWon() {
    for (int i = 0; i < SIZE; i++) {
      if(pegs[i][0] == pegs[i][1] && pegs[i][0] == pegs[i][2] && pegs[i][0] != 0) {
        return true;
      }
    }

    for (int j = 0; j < SIZE; j++) {
      if(pegs[0][j] == pegs[1][j] && pegs[0][j] == pegs[2][j] && pegs[0][j] != 0) {
        return true;
      }
    }

    if(pegs[0][0] == pegs[1][1] && pegs[0][0] == pegs[2][2] && pegs[0][0] != 0) {
      return true;
    }

    if(pegs[2][0] == pegs[1][1] && pegs[2][0] == pegs[0][2] && pegs[2][0] != 0) {
      return true;
    }

    return false;
  }
}
