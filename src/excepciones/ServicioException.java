package excepciones;

public class ServicioException extends Exception{
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public ServicioException(DAOException e) {
        super();
    }

    public ServicioException(String mensaje) {
        super(mensaje);
    }

    public ServicioException(DuplicadoException e) {
    }
}
