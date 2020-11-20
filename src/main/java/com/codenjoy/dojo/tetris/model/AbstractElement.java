package com.codenjoy.dojo.tetris.model;

import java.util.List;

public abstract class AbstractElement {
    private Elements type;
    private int[] stickingPoints;

    protected AbstractElement(Elements type, int... stickingPoints) {
        this.type = type;
        this.stickingPoints = stickingPoints;
    }

    public Elements getType() {
        return type;
    }

    public int[] getStickingPoints() {
        return stickingPoints;
    }
}
