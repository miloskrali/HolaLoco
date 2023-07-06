package db;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

public class DBTableManager {
	
    private CuentaDAO cuentaDAO;

    public DBTableManager(CuentaDAO cuentaDAO) {
        this.cuentaDAO = cuentaDAO;
    }

    public DBTableManager() {
	}

	public CuentaDAO getCuentaDAO() {
        return cuentaDAO;
    }

    public void createUserTable() {
        Connection c = DBManager.connect();

        String sql = "CREATE TABLE usuarios (dni INTEGER(8) PRIMARY KEY, nombre VARCHAR(40), clave VARCHAR(10))";

        try {
            Statement s = c.createStatement();
            s.execute(sql);
        } catch (SQLException e) {
            try {
                c.rollback();
                e.printStackTrace();
            } catch (SQLException e1) {
                e1.printStackTrace();
            }
        } finally {
            try {
                c.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public void deleteUserTable(int dni) {
        Connection c = DBManager.connect();

        String sql = "DELETE FROM usuarios WHERE dni = '" + dni + "'";

        try {
            Statement s = c.createStatement();
            s.execute(sql);
            c.commit();
        } catch (SQLException e) {
            try {
                c.rollback();
            } catch (SQLException e1) {
                e1.printStackTrace();
            }
        } finally {
            try {
                c.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public void createCuentaTable() {
        Connection c = DBManager.connect();

        String sql = "CREATE TABLE cuentas (dni INTEGER, tipoCuenta VARCHAR(40), numeroCuenta INTEGER PRIMARY KEY, saldo DECIMAL(10, 2), FOREIGN KEY (dni) REFERENCES usuarios(dni))";

        try {
            Statement s = c.createStatement();
            s.execute(sql);
        } catch (SQLException e) {
            try {
                c.rollback();
                e.printStackTrace();
            } catch (SQLException e1) {
                e1.printStackTrace();
            }
        } finally {
            try {
                c.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public void deleteCuentaTable() {
        Connection c = DBManager.connect();

        String sql = "DROP TABLE cuentas";

        try {
            Statement s = c.createStatement();
            s.execute(sql);
            c.commit();
        } catch (SQLException e) {
            try {
                c.rollback();
            } catch (SQLException e1) {
                e1.printStackTrace();
            }
        } finally {
            try {
                c.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public void createTarjetaTable() {
        Connection c = DBManager.connect();

        String sql = "CREATE TABLE tarjetas (numeroTarjeta INTEGER PRIMARY KEY, disponible DOUBLE, saldoPagar DOUBLE, dniTitular INTEGER, FOREIGN KEY (dniTitular) REFERENCES usuarios(dni))";

        try {
            Statement s = c.createStatement();
            s.execute(sql);
        } catch (SQLException e) {
            try {
                c.rollback();
                e.printStackTrace();
            } catch (SQLException e1) {
                e1.printStackTrace();
            }
        } finally {
            try {
                c.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public void deleteTarjetaTable() {
        Connection c = DBManager.connect();

        String sql = "DROP TABLE tarjetas";

        try {
            Statement s = c.createStatement();
            s.execute(sql);
            c.commit();
        } catch (SQLException e) {
            try {
                c.rollback();
            } catch (SQLException e1) {
                e1.printStackTrace();
            }
        } finally {
            try {
                c.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public void createMovimientoTable() {
        Connection c = DBManager.connect();

        String sql = "CREATE TABLE movimientos (id INTEGER PRIMARY KEY AUTO_INCREMENT, numeroTarjeta INTEGER, monto DECIMAL(10, 2), descripcion VARCHAR(100), dni INTEGER, FOREIGN KEY (numeroTarjeta) REFERENCES tarjetas(numeroTarjeta), FOREIGN KEY (dni) REFERENCES usuarios(dni))";

        try {
            Statement s = c.createStatement();
            s.execute(sql);
        } catch (SQLException e) {
            try {
                c.rollback();
                e.printStackTrace();
            } catch (SQLException e1) {
                e1.printStackTrace();
            }
        } finally {
            try {
                c.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public void deleteMovimientoTable() {
        Connection c = DBManager.connect();

        String sql = "DROP TABLE movimientos";

        try {
            Statement s = c.createStatement();
            s.execute(sql);
            c.commit();
        } catch (SQLException e) {
            try {
                c.rollback();
                e.printStackTrace();
            } catch (SQLException e1) {
                e1.printStackTrace();
            }
        } finally {
            try {
                c.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}