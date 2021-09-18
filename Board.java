package com.company.alekseyvalouev;

abstract class Rules {
    static void checkNeighbors(Cell[][] boardState, int i, int j) {
        int x = i+1;
        int y = j+1;
        int total = 0;
        if (boardState[x-1][y].checkState() == true) {
            total++;
        }
        if (boardState[x+1][y].checkState() == true) {
            total++;
        }
        if (boardState[x][y-1].checkState() == true) {
            total++;
        }
        if (boardState[x][y+1].checkState() == true) {
            total++;
        }
        if (boardState[x-1][y-1].checkState() == true) {
            total++;
        }
        if (boardState[x-1][y+1].checkState() == true) {
            total++;
        }
        if (boardState[x+1][y-1].checkState() == true) {
            total++;
        }
        if (boardState[x+1][y+1].checkState() == true) {
            total++;
        }
        boardState[x][y].setNeighbors(total);
    }
}

public class Board extends Rules {
    private static Reference ref = new Reference();
    private static Cell[][] boardState = new Cell[ref.getBoardSize()+2][ref.getBoardSize()+2];
    public static boolean setBoardState(String newBoardState) {
        /*if (newBoardState.length() != 100) {
            return false;
        }*/
        for (int i = 0; i < ref.getBoardSize()+2; i++) {
            for (int j = 0; j < ref.getBoardSize()+2; j++) {
                boardState[i][j] = new Cell();
                boardState[i][j].setState(false);
            }
        }
        for (int i = 0; i < ref.getBoardSize(); i++) {
            for (int j = 0; j < ref.getBoardSize(); j++) {
                int index = ref.getBoardSize()*i + j;
                if (newBoardState.charAt(index) == '1') {
                    boardState[i+1][j+1].setState(true);
                } else if (newBoardState.charAt(index) == '0') {
                    boardState[i+1][j+1].setState(false);
                } else {
                    return false;

                }
            }
        }
        return true;
    }

    public static void updateBoard() {
        for (int i = 0; i < ref.getBoardSize(); i++) {
            for (int j = 0; j < ref.getBoardSize(); j++) {
                checkNeighbors(boardState, i, j);
            }
        }
        for (int i = 0; i < ref.getBoardSize(); i++) {
            for (int j = 0; j < ref.getBoardSize(); j++) {
                boardState[i+1][j+1].updateCell();
            }
        }
    }

    public static void printBoard() {
        String[] rows = new String[10];
        for (int k = 0; k < ref.getBoardSize(); k++) {
            rows[k] = "";
        }
        for (int j = 0; j < ref.getBoardSize(); j++) {
            for (int i = 0; i < ref.getBoardSize(); i++) {
                if (boardState[j+1][i+1].checkState()) {
                    rows[j] += "*  ";
                } else {
                    rows[j] += ".  ";
                }
            }
        }
        System.out.println("____________________");
        for (int row = 0; row < rows.length; row++) {
            System.out.println(rows[row]);
        }
    }

    public static Cell getCell(int x, int y) {
        return boardState[x+1][y+1];
    }

    public static void changeCell(int x, int y) {
        boardState[x+1][y+1].switchState();
    }

}
