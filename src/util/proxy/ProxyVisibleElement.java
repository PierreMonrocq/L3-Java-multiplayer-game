package util.proxy;

import model.map.VisibleElement;
import util.VisibleType;

public class ProxyVisibleElement implements DisplayVisibleElement {

    private VisibleElementModel visibles = new VisibleElementModel();

    public ProxyVisibleElement() {}

    /**
     * @param v
     * @param playerID
     * @return Un element modifie par le proxy si le joueur ne possede pas l'element
     */
    @Override
    public VisibleElement displayElement(VisibleElement v,int playerID) {
        if (((v.getType() == VisibleType.BOMB) ||
                (v.getType() == VisibleType.MINE)) &&
                (v.getPlayerId() != playerID) &&
                (playerID != 0)) {
            return new VisibleElement(VisibleType.EMPTY);
        } else {
            return visibles.displayElement(v, playerID);
        }
    }
}
