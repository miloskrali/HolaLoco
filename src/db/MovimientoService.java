package db;

import java.util.List;

import entidades.Movimiento;
import excepciones.DAOException;

public class MovimientoService {
    private MovimientoDAO movimientoDAO;

    public MovimientoService(MovimientoDAO movimientoDAO) {
        this.movimientoDAO = movimientoDAO;
    }

    public List<Movimiento> obtenerMovimientosPorTarjeta(int numeroTarjeta) throws DAOException {
        return movimientoDAO.obtenerMovimientosPorTarjeta(numeroTarjeta);
    }
}