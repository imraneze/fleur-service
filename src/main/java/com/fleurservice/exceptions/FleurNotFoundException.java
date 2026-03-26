package com.fleurservice.exceptions;

public class FleurNotFoundException extends RuntimeException {

    private final Long id;

    public FleurNotFoundException(Long id) {
        super("Fleur introuvable avec l'id : " + id);
        this.id = id;
    }

    public Long getId() {
        return id;
    }
}