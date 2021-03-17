package model.map;

import util.VisibleType;

public class VisibleElement {

    private VisibleType type;
    private int playerId;
   
    /**
     * Definit un element visible 
     * @param type
     * @param id 
     */
    public VisibleElement(VisibleType type, int id) {
        this.playerId = id;
        this.type = type;
    }
    
    public VisibleElement(VisibleType type) {
        this(type, -1);
    }
    /**
     * Permet de recuperer le type de l'element
     * @return 
     */
    public VisibleType getType() {
        return type;
    }
    /**
     * Permet de recuperer le proprietaire de l'element, si il existe
     * @return 
     */
    public int getPlayerId() {
        return playerId;
    }
}
