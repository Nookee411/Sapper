package engine.core;

import engine.core.State;

public class Cell {
    private Boolean isMine;
    public Integer minesAround;
    private State state;

    /**
     * Constructs cell for minefield
     * @param isMine defines if cell contains mine
     */
    public Cell(Boolean isMine) {
        this.isMine = isMine;
        this.state = State.closed;
        this.minesAround = 0;
    }

    public Boolean isMine() {
        return isMine;
    }

    public State getState() {
        return this.state;
    }

    public void setState(State state) {
        this.state = state;
    }
}
