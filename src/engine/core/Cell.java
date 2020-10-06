package engine.core;

import engine.core.State;

public class Cell {
    private Boolean isMine;
    public Integer minesAround;
    private State state;


    public Cell(Boolean isMine, Integer minesAround) {
        this.isMine = isMine;
        this.state = State.closed;
        this.minesAround = minesAround;
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
