package UI;

import engine.core.Cell;

import javax.swing.*;
import java.awt.*;

public class CellButton extends JButton {
    private final int i;
    private final int j;

    /**
     * Standard JButton with coordinates in grid
     * Cell parameter defines appearance of button
     * @param cell Defines button appearance
     * @param i Button row in grid
     * @param j Button column in grid
     */
    public CellButton(Cell cell, int i, int j){
        super("");
        this.i = i;
        this.j = j;
        super.setBorder(BorderFactory.createCompoundBorder());
        switch (cell.getState()) {
            case closed -> {
                super.setText("*");
                super.setBackground(Color.white);
            }
            case opened -> {
                if (!cell.isMine()) {
                    super.setBackground(Color.GREEN);
                    super.setText(cell.minesAround==0 ? "": cell.minesAround.toString());
                } else {
                    super.setBackground(Color.RED);
                    super.setText("B");
                }
            }
            case marked -> {
                super.setText("m");
                super.setBackground(Color.YELLOW);
            }
        }
    }

    public int getI() {
        return i;
    }

    public int getJ() {
        return j;
    }
}
