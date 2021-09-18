package com.company.alekseyvalouev;

public class Cell {
    private boolean alive;
    private int neighbors;
    public boolean checkState() {
        return this.alive;

    }
    public void setState(boolean newState) {
        this.alive = newState;
    }
    public void setNeighbors(int newNeighbors) {
        this.neighbors = newNeighbors;
    }
    public void switchState() {
        if (this.alive == true) {
            this.alive = false;
        } else {
            this.alive = true;
        }
    }
    public void updateCell() {

        switch (neighbors) {
            case 2:
                if (alive == true) {
                    alive = true;
                }
                break;
            case 3:
                alive = true;
                break;
            default:
                alive = false;
                break;
        }
    }
}
