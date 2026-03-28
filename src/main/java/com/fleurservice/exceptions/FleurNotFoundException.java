package com.fleurservice.exceptions;

public class FleurNotFoundException extends RuntimeException {

    private Long id;

    public FleurNotFoundException(Long id) {
        super("Fleur introuvable avec l'id : " + id);
        this.id = id;
    }

    public FleurNotFoundException(String couleur) {
        super("Aucune fleur trouvée avec la couleur : " + couleur);
    }

    public FleurNotFoundException(Double prix) {
        super("Aucune fleur trouvée avec un prix moin de : " + prix);
    }

    public Long getId() {
        return id;
    }
}