package engine.core;

import java.util.Random;

public class Minefield {
    private Cell[][] field;
    private final int numberOfMines;

    //TODO Add Constraint to size
    public Minefield(int size, int numberOfMines) {
        this.field = new Cell[size][size];
        this.numberOfMines = numberOfMines;
        initializeField();
    }

    private void initializeField() {
        var rnd = new Random();
        double mineRate = ((double) numberOfMines / (getSize() * getSize())) * 100;
        for (int i = 0; i < field.length; i++) {
            for (int j = 0; j < field.length; j++) {
                field[i][j] = new Cell(rnd.nextInt(100) < mineRate, 0);
            }
        }
        countBombs();
    }

    private void countBombs() {
        //going trough all cells of field
        for (int i = 0; i < field.length; i++) {
            for (int j = 0; j < field.length; j++) {

                for (int k = -1; k <= 1; k++) {
                    for (int l = -1; l <= 1; l++) {
                        if (!(k == 0 && l == 0) && !isOutOfBounds(i + k, j + l) &&
                                field[i + k][j + l].isMine())
                            field[i][j].minesAround++;
                    }
                }
            }
        }
    }

    /**
     * @param i row of cell
     * @param j column of cell
     * @return True if opening cell is not a bomb, false if it is bomb.
     */
    public boolean openCell(int i, int j) {
        if (field[i][j].isMine())
        {
            field[i][j].setState(State.opened);
            return false;
        }
        recursiveOpen(i, j);
        return true;
    }

    private void recursiveOpen(int i, int j) {
        for (int k = -1; k <= 1; k++) {
            for (int l = -1; l <= 1; l++) {
                if ((l==0||k==0) &&
                        !isOutOfBounds(i+k,j+l)&&
                        (field[i + k][j + l].getState() == State.closed ||
                                field[i + k][j + l].getState() == State.marked) &&
                        !field[i + k][j + l].isMine()) {
                    field[i + k][j + l].setState(State.opened);
                    recursiveOpen(i + k, j + l);
                }
            }
        }
    }

    private Boolean isOutOfBounds(int i, int j) {
        try {
            field[i][j] = field[i][j];
            return false;
        } catch (Exception ex) {
            return true;
        }
    }

    public Cell getCell(int i, int j){
        return field[i][j];
    }

    public int getSize() {
        return field.length;
    }

    public int getNumberOfMines() {
        return numberOfMines;
    }

    public Boolean isWin() {
        for (int i = 0; i < getSize(); i++) {
            for (int j = 0; j < getSize(); j++) {
                if (field[i][j].getState() == State.closed &&
                        !field[i][j].isMine())
                    return false;
            }
        }
        return true;
    }
}
