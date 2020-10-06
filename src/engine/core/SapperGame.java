package engine.core;

public class SapperGame {
    private int clicks;
    private Minefield minefield;

    public SapperGame(int size,int numberOfMines) {
        minefield = new Minefield(size,numberOfMines);
        clicks =0;
    }

    public void newGame(){
        minefield = new Minefield(minefield.getSize(),minefield.getNumberOfMines());
        clicks =0;
    }

    public int getClicks(){
        return clicks;
    }

    public Boolean makeTurn(int i,int j){
        clicks++;
        return minefield.openCell(i,j);
    }
    public Boolean isWin(){
        return minefield.isWin();
    }

    public Cell getCell(int i, int j){
        return minefield.getCell(i,j);
    }
    public void revealField(){
        for (int i = 0; i < minefield.getSize(); i++) {
            for (int j = 0; j < minefield.getSize(); j++) {
                minefield.openCell(i,j);
            }
        }
    }

}
