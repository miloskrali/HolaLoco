package db;

import java.util.List;

import entidades.Tarjeta;
import excepciones.DAOException;
import excepciones.DuplicadoException;
import excepciones.UsuarioNoExistenteException;

public interface TarjetaDAO {

    void agregarTarjeta(Tarjeta tarjeta) throws DAOException, DuplicadoException, UsuarioNoExistenteException;

    void eliminarTarjeta(int numeroTarjeta) throws DAOException;

    List<Tarjeta> listarTodasLasTarjetas();

    Tarjeta obtenerTarjetaPorNumero(int numero) throws DAOException;

    void actualizarTarjeta(Tarjeta tarjeta) throws DAOException;
    
    public List<Tarjeta> listarTarjetasUsuario(int dni);

    
}