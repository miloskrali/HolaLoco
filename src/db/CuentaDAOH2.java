package db;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import entidades.Cuenta;
import excepciones.DAOException;
import excepciones.DuplicadoException;
import excepciones.SaldoInsuficienteException;
import excepciones.UsuarioNoExistenteException;

public class CuentaDAOH2 implements CuentaDAO {
	
    public void crearCuenta(Cuenta cuenta) throws DAOException, DuplicadoException, UsuarioNoExistenteException {
        int dni = cuenta.getDni();
        String tipo= cuenta.getTipo();
        int numeroCuenta = cuenta.getNumeroCuenta();
        Double saldo = cuenta.getSaldo();

        Connection c = DBManager.connect();
        
        try {
        	   Statement s = c.createStatement();
               String verificarUsuarioSql = "SELECT COUNT(*) FROM usuarios WHERE dni = " + dni;
               ResultSet resultado = s.executeQuery(verificarUsuarioSql);
               resultado.next();
               int cuentaUsuarios = resultado.getInt(1);
               
               if (cuentaUsuarios == 0) {
                   throw new UsuarioNoExistenteException();
               }else {
               String insertarCuentaSql = "INSERT INTO cuentas (dni, tipoCuenta, numeroCuenta, saldo) VALUES ('" + dni + "', '"
                       + tipo + "', '" + numeroCuenta + "', '" + saldo + "')";
               s.executeUpdate(insertarCuentaSql);
               c.commit();
               }
        } catch (SQLException e) {
            try {
                if (e.getErrorCode() == 23505) {
                    throw new DuplicadoException();
                }
                e.printStackTrace();
                c.rollback();
            } catch (SQLException e1) {
                e.printStackTrace();
            }
		} finally {
            try {
                c.close();
            } catch (SQLException e1) {
                e1.printStackTrace();
            }
        }
    }

    public void borrarCuenta(int numeroCuenta) throws DAOException {
        String sql = "DELETE FROM cuentas WHERE numerocuenta = '" + numeroCuenta + "'";
        Connection c = DBManager.connect();
        try {
            Statement s = c.createStatement();
            s.executeUpdate(sql);
            c.commit();
        } catch (SQLException e) {
            try {
                c.rollback();
            } catch (SQLException ex) {
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

    public void actualizarCuenta(Cuenta cuenta) throws DAOException {
        Double saldo = cuenta.getSaldo();

        String sql = "UPDATE cuentas set saldo = '" + saldo + "' WHERE numeroCuenta = '" + cuenta.getNumeroCuenta() + "'";
        Connection c = DBManager.connect();
        try {
            Statement s = c.createStatement();
            s.executeUpdate(sql);
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
            } catch (SQLException e1) {
                e1.printStackTrace();
            }
        }
    }

    public Cuenta mostrarCuenta(int dni) {
        String sql = "SELECT * FROM cuentas WHERE dni = '" + dni + "'";
        Connection c = DBManager.connect();
        try {
            Statement s = c.createStatement();
            ResultSet rs = s.executeQuery(sql);

            if (rs.next()) {
            	int dniCuenta = rs.getInt("dni");
            	String tipo = rs.getString("tipoCuenta");
            	int numeroCuenta = rs.getInt("numeroCuenta");
            	double saldo = rs.getDouble("saldo");
                Cuenta cta = new Cuenta(dniCuenta, tipo, numeroCuenta, saldo);
                return cta;
            }

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
            } catch (SQLException e1) {
                e1.printStackTrace();
            }
        }
        return null;
    }

    public List<Cuenta> listarCuentasUsuario(int dni){
    	List<Cuenta> resultado = new ArrayList<>();
        String sql = "SELECT * FROM cuentas WHERE dni = " + dni;
        Connection c = DBManager.connect();
        try {
            Statement s = c.createStatement();
            ResultSet rs = s.executeQuery(sql);

            
            while (rs.next()) {
            	int dniCuenta = rs.getInt("dni");
            	String tipo = rs.getString("tipoCuenta");
            	int numeroCuenta = rs.getInt("numeroCuenta");
            	double saldo = rs.getDouble("saldo");
                Cuenta cta = new Cuenta(dniCuenta, tipo, numeroCuenta, saldo);
                resultado.add(cta);

            }
        } catch (SQLException e) {
            try {
                c.rollback();
            } catch (SQLException e1) {
                e1.printStackTrace();
            }
        } finally {
            try {
                c.close();
            } catch (SQLException e1) {
                e1.printStackTrace();
            }
        }
        return resultado;
    }
    
        public List<Cuenta> listarTodasLasCuentas() {
        	List<Cuenta> resultado = new ArrayList<>();
            String sql = "SELECT * FROM cuentas";
            Connection c = DBManager.connect();
            try {
                Statement s = c.createStatement();
                ResultSet rs = s.executeQuery(sql);

                
                while (rs.next()) {
                	int dniCuenta = rs.getInt("dni");
                	String tipo = rs.getString("tipoCuenta");
                	int numeroCuenta = rs.getInt("numeroCuenta");
                	double saldo = rs.getDouble("saldo");
                    Cuenta cta = new Cuenta(dniCuenta, tipo, numeroCuenta, saldo);
                    resultado.add(cta);

                }
            } catch (SQLException e) {
                try {
                    c.rollback();
                } catch (SQLException e1) {
                    e1.printStackTrace();
                }
            } finally {
                try {
                    c.close();
                } catch (SQLException e1) {
                    e1.printStackTrace();
                }
            }
            return resultado;
    }

		public void debitarCuenta(int dni, double saldo) throws SaldoInsuficienteException{
		    String sql = "SELECT saldo FROM cuentas WHERE dni = " + dni;
		    Connection c = DBManager.connect();
		    try {
		        Statement s = c.createStatement();
		        ResultSet rs = s.executeQuery(sql);

		        if (rs.next()) {
		            double saldoActual = rs.getDouble("saldo");
		            double nuevoSaldo = saldoActual - saldo;

		            if (nuevoSaldo >= 0) {
		                sql = "UPDATE cuentas SET saldo = " + nuevoSaldo + " WHERE dni = " + dni;
		                s.executeUpdate(sql);
		                c.commit();
		            } else {
		                throw new SaldoInsuficienteException("Saldo insuficiente");
		            }
		        }
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
		        } catch (SQLException e1) {
		            e1.printStackTrace();
		        }
		    }
		}
		public void acreditarCuenta(int dni, double saldo) throws DAOException {
		    String sql = "SELECT saldo FROM cuentas WHERE dni = " + dni;
		    Connection c = DBManager.connect();
		    try {
		        Statement s = c.createStatement();
		        ResultSet rs = s.executeQuery(sql);

		        if (rs.next()) {
		            double saldoActual = rs.getDouble("saldo");
		            double nuevoSaldo = saldoActual + saldo;

		            sql = "UPDATE cuentas SET saldo = " + nuevoSaldo + " WHERE dni = " + dni;
		            s.executeUpdate(sql);
		            c.commit();
		        }
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
		        } catch (SQLException e1) {
		            e1.printStackTrace();
		        }
		    }
		}
		
		public Cuenta buscarCuentaPorID(int idCuenta) {
		    String sql = "SELECT * FROM cuentas WHERE numeroCuenta = " + idCuenta;
		    Connection c = DBManager.connect();
		    try {
		        Statement s = c.createStatement();
		        ResultSet rs = s.executeQuery(sql);

		        if (rs.next()) {
		            int dniCuenta = rs.getInt("dni");
		            String tipoCuentaStr = rs.getString("tipoCuenta");
		            int numeroCuenta = rs.getInt("numeroCuenta");
		            double saldo = rs.getDouble("saldo");

		            return new Cuenta(dniCuenta, tipoCuentaStr, numeroCuenta, saldo);
		        }
		    } catch (SQLException e) {
		        e.printStackTrace();
		    } finally {
		        try {
		            c.close();
		        } catch (SQLException e1) {
		            e1.printStackTrace();
		        }
		    }

		    return null;
		}
		
		public void realizarTransferencia(Cuenta cuentaOrigen, int numeroCuentaDestino, double monto)
		        throws DAOException, SaldoInsuficienteException {
		    int numeroCuentaOrigen = cuentaOrigen.getNumeroCuenta();

		    double saldoOrigen = obtenerSaldoCuenta(numeroCuentaOrigen);

		    if (saldoOrigen < monto) {
		        throw new SaldoInsuficienteException("Saldo insuficiente en la cuenta de origen.");
		    }

		    double nuevoSaldoOrigen = saldoOrigen - monto;
		    actualizarSaldoCuenta(numeroCuentaOrigen, nuevoSaldoOrigen);

		    double saldoDestino = obtenerSaldoCuenta(numeroCuentaDestino);
		    double nuevoSaldoDestino = saldoDestino + monto;
		    actualizarSaldoCuenta(numeroCuentaDestino, nuevoSaldoDestino);
		}


		private double obtenerSaldoCuenta(int numeroCuenta) throws DAOException {
		    Connection c = DBManager.connect();
		    try {
		        Statement s = c.createStatement();
		        ResultSet rs = s.executeQuery("SELECT saldo FROM cuentas WHERE numeroCuenta = " + numeroCuenta);

		        if (rs.next()) {
		            return rs.getDouble("saldo");
		        }
		    } catch (SQLException e) {
		        e.printStackTrace();
		    } finally {
		        try {
		            c.close();
		        } catch (SQLException e1) {
		            e1.printStackTrace();
		        }
		    }
		    throw new DAOException("La cuenta no existe.");
		}

		private void actualizarSaldoCuenta(int numeroCuenta, double nuevoSaldo) throws DAOException {
		    Connection c = DBManager.connect();
		    try {
		        Statement s = c.createStatement();
		        s.executeUpdate("UPDATE cuentas SET saldo = " + nuevoSaldo + " WHERE numeroCuenta = " + numeroCuenta);
		        c.commit();
		    } catch (SQLException e) {
		        try {
		            c.rollback();
		        } catch (SQLException ex) {
		            ex.printStackTrace();
		        }
		        e.printStackTrace();
		    } finally {
		        try {
		            c.close();
		        } catch (SQLException e1) {
		            e1.printStackTrace();
		        }
		    }
		}
}
