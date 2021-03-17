package model.map.wall;

public class Wall {
    int left;
    int bottom;
    int right;
    int top;
    
    public Wall(int left, int bottom, int width, int height){
        this.left = left;
        this.bottom = bottom;
        this.right = left + width;
        this.top = bottom + height;
    }

    public int getLeft() {
        return left;
    }

    public int getBottom() {
        return bottom;
    }

    public int getRight() {
        return right;
    }

    public int getTop() {
        return top;
    }
    /**
     * Verifie l'intersection des murs entre eux (peut etre utilise pour la generation de la carte)
     * @param other
     * @return 
     */
    public boolean intersects(Wall other){
        return !((right <= other.getLeft()) ||
                 (left >= other.getRight()) ||
                 (bottom >= other.getTop()) ||
                 (top <= other.getBottom()));
    }
}
