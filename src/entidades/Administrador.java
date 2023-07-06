package entidades;

import db.CuentaDAO;
import db.CuentaDAOH2;
import db.DBTableManager;
import db.TarjetaDAO;
import db.TarjetaDAOH2;
import db.UsuarioDAO;
import db.UsuarioDAOH2;
import excepciones.DAOException;
import excepciones.DuplicadoException;

public class Administrador extends Usuario {

	public Administrador(int dni, String nombre, String clave) {
		super(dni, nombre, clave);
	}

	public Administrador() {
	}

	public Usuario crearUsuario(int dni, String nombre, String clave) {
		UsuarioDAO dao = new UsuarioDAOH2();
		Usuario usuario1 = new Usuario();
		usuario1.setDni(dni);
		usuario1.setNombre(nombre);
		usuario1.setClave(clave);
		try {
			dao.crearUsuario(usuario1);
		} catch (DAOException | DuplicadoException e) {
			System.out.println("El usuario con el dni" + dni + " ya se encuentra registrado");
		}
		return usuario1;
	}

	public void modificarUsuario(Usuario usuario) {
		UsuarioDAO dao = new UsuarioDAOH2();
		String nombreEdit = "Fariña";
		String claveEdit = "Argentina";
		usuario.setNombre(nombreEdit);
		usuario.setClave(claveEdit);
		dao.actualizaUsuario(usuario);
		System.out.println(usuario);
	}

	public void eliminarUsuario(int dni, DBTableManager table) {
		try {
			UsuarioDAO dao = new UsuarioDAOH2();

			dao.borraUsuario(dni);
		} catch (Exception e) {
			System.out.println("ERROR AL BORRAR USUARIO");
		}

		table.deleteUserTable(dni);
		System.out.println(dni);
	}

	public Cuenta crearCuenta(Usuario usuario, String tipo, int numeroCuenta, double saldo) {
		CuentaDAO dao = new CuentaDAOH2();
		Cuenta cuenta = new Cuenta();
		cuenta.setDni(usuario.getDni());
		cuenta.setTipo(tipo);
		cuenta.setNumeroCuenta(numeroCuenta);
		cuenta.setSaldo(saldo);
		usuario.agregarCuenta(cuenta);
		try {
			dao.crearCuenta(cuenta);
		} catch (Exception e) {
			System.out.println("El numero de cuenta " + numeroCuenta + " ya se encuentra registrada");
		}
		return cuenta;
	}
	
	public Tarjeta crearTarjeta(Usuario usuario, int numero, double disponible, double saldoPagar) {
		TarjetaDAO dao = new TarjetaDAOH2();
		Tarjeta tarjeta = new Tarjeta();
		tarjeta.setDni(usuario.getDni());
		tarjeta.setNumero(numero);
		tarjeta.setDisponible(disponible);
		tarjeta.setSaldoPagar(saldoPagar);
		usuario.agregarTarjeta(tarjeta);
		try {
			dao.agregarTarjeta(tarjeta);
		} catch (Exception e) {
			System.out.println("La tarjeta " + numero + " ya se encuentra registrada");
		}
		return tarjeta;
	}

}
