package engine;

import engine.core.Cell;
import engine.core.State;

import java.util.Random;

public class Minefield {
    private Cell[][] field;
    private final int numberOfMines;
    private Boolean isFirstTurn;
    /**
     * Sets field of size and fills with mines
     * @param size Rows of minefield
     * @param numberOfMines Number of minefields
     */
    public Minefield(int size, int numberOfMines) {
        if(size<=1)
            size = 11;
        this.field = new Cell[size][size];
        this.numberOfMines = numberOfMines;
        isFirstTurn = true;
        initializeField();
    }

    /**
     * Sets mines to minefield
     */
    private void initializeField() {
        var rnd = new Random();
        int remainingMines= numberOfMines;
        double mineRate = ((double) numberOfMines / (getSize() * getSize())) * 100;
        for (int i = 0; i < field.length; i++) {
            for (int j = 0; j < field.length; j++)
                field[i][j] = new Cell(((rnd.nextInt(100) < mineRate)
                        && remainingMines-- != 0));
        }
        countBombs();
    }

    /**
     * Counts bombs around specific cell
     */
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
     * Opens all cells which are allocated to current
     * @param i row of cell
     * @param j column of cell
     * @return True if opening cell is not a bomb, false if it is bomb.
     */
    public boolean openCell(int i, int j) {
        if(isFirstTurn){
            isFirstTurn = false;
            field[i][j] = new Cell(false);
        }
        if (field[i][j].isMine())
        {
            field[i][j].setState(State.opened);
            return false;
        }
        recursiveOpen(i, j);
        return true;
    }

    /**
     * Operation method for opening
     */
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

    /**
     * Operational method which checks existing of cell with specific coordinates
     * @param i row number
     * @param j column number
     * @return returns true if cell doesn't exist in minefield
     */
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

    /**
     * Checks if all empty cells were opened
     * @return Returns game result
     */
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
