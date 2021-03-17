package model.map;

/** 
 * Case ou objet sur une case
 */
public interface CellComponent {
    public boolean allowsEntry(CellComponent mover);
    public void onEntry(CellComponent mover);

    public boolean occupiesLow();
    public boolean occupiesHigh();
    public boolean canShootThrough();

    public void dealDamage(int damage);

    public VisibleElement getVisible();
}
