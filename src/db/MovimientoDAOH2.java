package db;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import entidades.Movimiento;
import excepciones.DAOException;

public class MovimientoDAOH2 implements MovimientoDAO {

    public void agregarMovimiento(Movimiento movimiento) throws DAOException {
        int numeroTarjeta = movimiento.getNumeroTarjeta();
        double monto = movimiento.getMonto();
        String descripcion = movimiento.getDescripcion();
        int dni = movimiento.getDni();

        Connection c = DBManager.connect();
        try {
            Statement s = c.createStatement();
            String sql = "INSERT INTO movimientos (numeroTarjeta, monto, descripcion, dni) VALUES (" + numeroTarjeta + ", "
                    + monto + ", '" + descripcion + "', " + dni + ")";
            s.executeUpdate(sql);
            c.commit();
        } catch (SQLException e) {
            try {
                c.rollback();
                e.printStackTrace();
            } catch (SQLException e1) {
                e1.printStackTrace();
            }
            throw new DAOException();
        } finally {
            try {
                c.close();
            } catch (SQLException e1) {
                e1.printStackTrace();
            }
        }
    }

    public List<Movimiento> obtenerMovimientosPorTarjeta(int numeroTarjeta) throws DAOException {
        List<Movimiento> movimientos = new ArrayList<>();
        String sql = "SELECT * FROM movimientos WHERE numeroTarjeta = " + numeroTarjeta;

        Connection c = DBManager.connect();
        try {
            Statement s = c.createStatement();
            ResultSet rs = s.executeQuery(sql);

            while (rs.next()) {
                int id = rs.getInt("id");
                double monto = rs.getDouble("monto");
                String descripcion = rs.getString("descripcion");
                int dni = rs.getInt("dni");

                Movimiento movimiento = new Movimiento(id, numeroTarjeta, monto, descripcion, dni);
                movimientos.add(movimiento);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new DAOException();
        } finally {
            try {
                c.close();
            } catch (SQLException e1) {
                e1.printStackTrace();
            }
        }

        return movimientos;
    }
}