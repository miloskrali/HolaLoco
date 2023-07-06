package excepciones;

public class DuplicadoException extends Exception {

	private static final long serialVersionUID = 1L;

	public DuplicadoException() {
        super();
    }

	public DuplicadoException(String mensaje) {
        super(mensaje);
    }
}
