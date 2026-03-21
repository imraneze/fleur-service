package fleurs.web.exceptions;

public class FleurNotFoundException extends RuntimeException {

    public FleurNotFoundException(Long id) {
        super("Fleur non trouvée avec l'id : " + id);
    }
}