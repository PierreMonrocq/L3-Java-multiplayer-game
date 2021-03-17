package util;

import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;

public class DeferredElementRemover<E> implements Iterable<E> {
    private Collection<E> collection;
    private HashSet<E> deferredRemovals;

    /**
     * Classe qui s'occupe de la suppression des elements
     * @param collection 
     */
    public DeferredElementRemover(Collection<E> collection) {
        this.collection = collection;
        deferredRemovals = new HashSet<>();
    }

    /**
     * Ajoute un element a supprimer plus tard
     * @param element 
     */
    public void add(E element) {
        deferredRemovals.add(element);
    }
    
    /**
     * Effectue les suppressions.
     */
    public void enactRemovals() {
        for(E element : deferredRemovals) {
            collection.remove(element);
        }

        deferredRemovals.clear();
    }

    @Override
    public Iterator<E> iterator() {
        return deferredRemovals.iterator();
    }

    public int size() {
        return deferredRemovals.size();
    }
}
