package util.mvc;

public interface ListenerModel {

    /**
     * Methode qui est executee quand le modele est mis a jours
     *
     * @param source (permet de selectionner indépendamment certains écouteurs)
     */
    void modelUpdated(Object source);

}
