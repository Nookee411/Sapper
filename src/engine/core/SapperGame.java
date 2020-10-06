package engine.core;

import engine.Minefield;

public class SapperGame {
    private int clicks;
    private Minefield minefield;

    /**
     * Constructor of game sapper which sets size of minefield fills it with mines
     * @param size Number of rows and columns in minefield
     * @param numberOfMines Number of mines in field
     */
    public SapperGame(int size,int numberOfMines) {
        minefield = new Minefield(size,numberOfMines);
        clicks =0;
    }

    /**
     * resets game field and counters
     */
    public void newGame(){
        minefield = new Minefield(minefield.getSize(),minefield.getNumberOfMines());
        clicks =0;
    }

    public int getClicks(){
        return clicks;
    }

    /**
     * Opens cell with specific coordinates
     * @param i row coordinate
     * @param j column coordinate
     * @return Returns if mine was opened
     */
    public Boolean makeTurn(int i,int j){
        clicks++;
        return minefield.openCell(i,j);
    }

    /**
     *
     * @return is game was won
     */
    public Boolean isWin(){
        return minefield.isWin();
    }

    /**
     * Returns specific cell with coordinates
     * @param i row of cell
     * @param j column of cell
     * @return cell at coordinates i, j in minefield
     */
    public Cell getCell(int i, int j){
        return minefield.getCell(i,j);
    }

    /**
     * Opens all cells on field
     */
    public void revealField(){
        for (int i = 0; i < minefield.getSize(); i++) {
            for (int j = 0; j < minefield.getSize(); j++) {
                minefield.openCell(i,j);
            }
        }
    }

}
