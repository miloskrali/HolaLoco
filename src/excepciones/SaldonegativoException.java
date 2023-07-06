package excepciones;

public class SaldonegativoException extends Exception{
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public SaldonegativoException(){
        super();
    }

    public SaldonegativoException(String mensaje){
        super(mensaje);
    }
}
