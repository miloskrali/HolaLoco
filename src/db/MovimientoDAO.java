package db;

import java.util.List;

import entidades.Movimiento;
import excepciones.DAOException;

public interface MovimientoDAO {

    void agregarMovimiento(Movimiento movimiento) throws DAOException;

    List<Movimiento> obtenerMovimientosPorTarjeta(int numeroTarjeta) throws DAOException;

}