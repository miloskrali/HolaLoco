package entidades;

import java.util.ArrayList;
import java.util.List;

public class Usuario {
	private int dni;
	private String nombre;
	private String clave;
    private List<Cuenta> cuentas = new ArrayList<>();
    private List<Tarjeta> tarjetas = new ArrayList<>();
	
	public Usuario() {
	}
	
	public Usuario(int dni, String nombre ,String clave) {
		super();
		this.dni = dni;
		this.nombre = nombre;
		this.clave = clave;
	}
	public int getDni() {
		return dni;
	}
	public String getNombre() {
		return nombre;
	}
	
	public String getClave() {
		return clave;
	}

	public void setDni(int dni) {
		this.dni = dni;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	public void setClave(String clave) {
		this.clave = clave;
	}

	public String toString() {
		return "Usuario [dni=" + dni + ", nombre=" + nombre + ", clave=" + clave + ", cuentas=" + cuentas
				+ ", tarjetas=" + tarjetas + "]";
	}

	public List<Cuenta> getCuentas() {
		return cuentas;
	}

	public void setCuentas(List<Cuenta> cuentas) {
		this.cuentas = cuentas;
	}
	
	public void agregarCuenta(Cuenta cuenta) {
		this.cuentas.add(cuenta);
	}

	public List<Tarjeta> getTarjetas() {
		return tarjetas;
	}

	public void setTarjetas(List<Tarjeta> tarjetas) {
		this.tarjetas = tarjetas;
	}

	public void agregarTarjeta(Tarjeta tarjeta) {
		this.tarjetas.add(tarjeta);
	}
	
}
