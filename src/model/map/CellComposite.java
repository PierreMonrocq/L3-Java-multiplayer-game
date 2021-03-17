package model.map;

import util.mvc.AbstractListenableModel;
import util.DeferredElementRemover;
import util.Vec2;

import java.util.ArrayList;
import java.util.Iterator;

import util.VisibleType;

public class CellComposite extends AbstractListenableModel implements CellComponent {
    private Vec2 position;
    private ArrayList<CellComponent> components;
    private DeferredElementRemover<CellComponent> componentRemover;
    private boolean iterating;

    public CellComposite(Vec2 position) {
        components = new ArrayList<>();
        componentRemover = new DeferredElementRemover<>(components);
        this.position = position;
        iterating = false;
    }
    
    /**
     * Ajoute un nouveau composant a la cellule
     * @param component 
     */
    private void addComponent(CellComponent component) {
        components.add(component);
    }
    /**
     * Supprime un composant d'une cellule
     * @param component 
     */
    public void removeComponent(CellComponent component) {
        if(iterating) {
            componentRemover.add(component);
        } else {
            components.remove(component);
            fireChange();
        }
    }

    @Override
    public boolean allowsEntry(CellComponent mover) {
        boolean result = true;

        for(CellComponent c : components) {
            if(!c.allowsEntry(mover)) {
                result = false;
                break;
            }
        }

        return result;
    }

    @Override
    public void onEntry(CellComponent mover) {
        iterating = true;

        for(Iterator<CellComponent> it = components.iterator(); it.hasNext();) {
            CellComponent c = it.next();
            c.onEntry(mover);
        }

        componentRemover.enactRemovals();
        iterating = false;

        components.add(mover);
        fireChange();
    }

    @Override
    public boolean occupiesLow() {
        boolean result = false;

        for(CellComponent c : components) {
            if(c.occupiesLow()) {
                result = true;
            }
        }

        return result;
    }

    @Override
    public boolean occupiesHigh() {
        boolean result = false;

        for(CellComponent c : components) {
            if(c.occupiesHigh()) {
                result = true;
            }
        }

        return result;
    }

    @Override
    public boolean canShootThrough() {
        boolean result = true;

        for(CellComponent c : components) {
            if(!c.canShootThrough()) {
                result = false;
                break;
            }
        }

        return result;
    }

    @Override
    public void dealDamage(int damage) {
        for(CellComponent c : components) {
            c.dealDamage(damage);
        }
    }

    @Override
    public VisibleElement getVisible() {
        VisibleElement result = new VisibleElement(VisibleType.EMPTY);

        for(CellComponent c : components) {
            if(c.occupiesLow()) {
                result = c.getVisible();
            }

            if(c.occupiesHigh()) {
                result = c.getVisible();
                break;
            }
        }

        return result;
    }
}
