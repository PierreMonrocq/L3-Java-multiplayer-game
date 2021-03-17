package util.proxy;

import model.map.VisibleElement;

public class VisibleElementModel implements DisplayVisibleElement {
    
    @Override
    public VisibleElement displayElement(VisibleElement v, int playerID) {
        return v;
    }
}
