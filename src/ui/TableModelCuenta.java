package ui;

import java.util.ArrayList;
import java.util.List;

import javax.swing.table.AbstractTableModel;

import entidades.Cuenta;

public class TableModelCuenta extends AbstractTableModel{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private static final int DNI = 0;
	private static final int TIPOCUENTA = 1;
	private static final int NUMEROCUENTA = 2;
	private static final int SALDO = 3;


	private String[] columnas = {"DNI", "Tipo_Cuenta", "Numero_Cuenta", "Saldo"};
	
	private Class[] tipoDato = {Integer.class, Enum.class, Integer.class,Double.class};
	
	private List<Cuenta> contenido;
	
	public TableModelCuenta() {
		contenido = new ArrayList<Cuenta>();
	}

	public TableModelCuenta(List<Cuenta> contenidoInicial) {
		contenido = contenidoInicial;
	}
	
	public int getRowCount() {
		return contenido.size();
	}

	
	public int getColumnCount() {
		return columnas.length;
	}

	public String getColumnName(int col) {
		return columnas[col];
	}
	
	public Class getColumnClass(int col) {
		return tipoDato[col];
	}
	
	public Object getValueAt(int rowIndex, int columnIndex) {
        Cuenta cuenta = contenido.get(rowIndex);
        
        Object result = null;
        switch (columnIndex) {
            case DNI:
                result = cuenta.getDni();
                break;
            case TIPOCUENTA:
                result = cuenta.getTipo();
                break;
            case NUMEROCUENTA:
                result =  cuenta.getNumeroCuenta();
                break;
            case SALDO:
                result = cuenta.getSaldo();
                break;
            default:
                result = new String("");
        }
        return result;
	}

	
	public List<Cuenta> getContenido(){
		return contenido;
	}
	
	public void setContenido(List<Cuenta> contenido) {
		this.contenido = contenido;
	}
}
