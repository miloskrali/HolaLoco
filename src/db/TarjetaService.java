package db;

import java.util.List;

import entidades.Tarjeta;
import excepciones.DAOException;
import excepciones.DuplicadoException;
import excepciones.ServicioException;

public class TarjetaService {

    private TarjetaDAO tarjetaDAO;

    public TarjetaService(TarjetaDAO tarjetaDAO) {
        this.tarjetaDAO = tarjetaDAO;
    }

    public List<Tarjeta> listarTodasLasTarjetas() throws DAOException {
        return tarjetaDAO.listarTodasLasTarjetas();
    }

    public void agregarTarjeta(Tarjeta tarjeta) throws DAOException, DuplicadoException {
        tarjetaDAO.agregarTarjeta(tarjeta);
    }

    public void eliminarTarjeta(int numeroTarjeta) throws DAOException {
        tarjetaDAO.eliminarTarjeta(numeroTarjeta);
    }

    public Tarjeta obtenerTarjetaPorNumero(int numero) throws DAOException {
        return tarjetaDAO.obtenerTarjetaPorNumero(numero);
    }

    public void actualizarTarjeta(Tarjeta tarjeta) throws DAOException {
        tarjetaDAO.actualizarTarjeta(tarjeta);
    }

    public List<Tarjeta> listarTarjetas() throws ServicioException {
        TarjetaDAO d = new TarjetaDAOH2();
        List<Tarjeta> tarjetas;
        tarjetas = d.listarTodasLasTarjetas();
        return tarjetas;
    }

    public void agregarTarjetaNueva(Tarjeta tarjeta) throws ServicioException, DuplicadoException {
        TarjetaDAO d = new TarjetaDAOH2();
        try {
            d.agregarTarjeta(tarjeta);
        } catch (DAOException e) {
            throw new ServicioException(e);
        }
    }
}