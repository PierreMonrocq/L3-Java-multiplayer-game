package util;

public class Vec2 {
    private int x;
    private int y;

    /**
     * Classe qui definit un vecteur a deux dimensions
     * @param x
     * @param y 
     */
    public Vec2(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public Vec2(Vec2 from) {
        this(from.x, from.y);
    }

    public Vec2() {
        this(0, 0);
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public void setX(int x) {
        this.x = x;
    }

    public void setY(int y) {
        this.y = y;
    }

    static public Vec2 sum(Vec2 a, Vec2 b) {
        return new Vec2(a.x + b.x, a.y + b.y);
    }

    static public Vec2 sum(Vec2 a, int x, int y) {
        return new Vec2(a.x + x, a.y + y);
    }

    static public Vec2 diff(Vec2 a, Vec2 b) {
        return new Vec2(a.x - b.x, a.y - b.y);
    }

    static public Vec2 diff(Vec2 a, int x, int y) {
        return new Vec2(a.x - x, a.y - y);
    }
    
    public boolean equals(Vec2 a,Vec2 b){
        return ((a.x == b.x) && (a.y == b.y));
    }

    public boolean equals(Vec2 other) {
        return ((x == other.getX()) && (y == other.getY()));
    }

    public void add(Vec2 other) {
        x += other.x;
        y += other.y;
    }

    public void add(int x, int y) {
        this.x += x;
        this.y += y;
    }

    public void sub(Vec2 other) {
        x -= other.x;
        y -= other.y;
    }

    @Override
    public String toString() {
        return "[" + x + ", " + y + "]";
    }
}
