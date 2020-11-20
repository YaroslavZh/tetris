package com.codenjoy.dojo.tetris.model;

public class ElementsFactory {

    public AbstractElement createElement(Elements elementType) {
        AbstractElement element = null;
        switch (elementType) {
            case YELLOW:
                element = createOElement();
                break;
            case BLUE:
                element = createIElement();
                break;
            case CYAN:
                element = createJElement();
        }
        return element;
    }

    public OElement createOElement() {
        return new OElement();
    }

    public IElement createIElement() {
        return new IElement();
    }

    public JElement createJElement() {
        return new JElement();
    }

}
