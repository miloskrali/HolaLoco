package ui;

import java.util.ArrayList;
import java.util.List;

import javax.swing.table.AbstractTableModel;

import entidades.Tarjeta;

public class TableModelTarjetas extends AbstractTableModel {

	private static final int DNI = 0;
	private static final int NUMERO = 1;
	private static final int DISPONIBLE = 2;
	private static final int SALDOPAGAR = 3;


	private String[] columnas = {"DNI", "Numero_Tarjeta", "Saldo_Disponible", "Saldo_Pagar"};
	
	private Class[] tipoDato = {Integer.class, Integer.class, Double.class,Double.class};
	
	private List<Tarjeta> contenido;
	
	public TableModelTarjetas() {
		contenido = new ArrayList<Tarjeta>();
	}

	public TableModelTarjetas(List<Tarjeta> contenidoInicial) {
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
		Tarjeta tarjeta = contenido.get(rowIndex);
        
        Object result = null;
        switch (columnIndex) {
            case DNI:
                result = tarjeta.getDni();
                break;
            case NUMERO:
                result = tarjeta.getNumero();
                break;
            case DISPONIBLE:
                result =  tarjeta.getDisponible();
                break;
            case SALDOPAGAR:
                result = tarjeta.getSaldoPagar();
                break;
            default:
                result = new String("");
        }
        return result;
	}

	
	public List<Tarjeta> getContenido(){
		return contenido;
	}
	
	public void setContenido(List<Tarjeta> contenido) {
		this.contenido = contenido;
	}
}
