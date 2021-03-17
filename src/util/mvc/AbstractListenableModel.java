package util.mvc;

import java.util.ArrayList;

public abstract class AbstractListenableModel implements ListenableModel{
    
    ArrayList<ListenerModel> listeners;

    public AbstractListenableModel() {
        listeners = new ArrayList();
    }
    
    @Override
    public void addListener(ListenerModel e) {
        listeners.add(e);
    }

    @Override
    public void removeListener(ListenerModel e) {
        listeners.remove(e);
    }

    public void fireChange() {
        listeners.forEach((e) -> {
            e.modelUpdated(this);
        });
    }
}
