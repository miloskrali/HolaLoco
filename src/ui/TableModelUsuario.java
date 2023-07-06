package ui;

import java.util.ArrayList;
import java.util.List;

import javax.swing.table.AbstractTableModel;

import entidades.Usuario;

public class TableModelUsuario extends AbstractTableModel{
	
	private static final int DNI = 0;
	private static final int NOMBRE = 1;
	private static final int CLAVE = 2;


	private String[] columnas = {"DNI", "Nombre", "Clave"};
	
	private Class[] tipoDato = {Integer.class, String.class, String.class};
	
	private List<Usuario> contenido;
	
	public TableModelUsuario() {
		contenido = new ArrayList<Usuario>();
	}

	public TableModelUsuario(List<Usuario> contenidoInicial) {
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
        Usuario usuario = contenido.get(rowIndex);
        
        Object result = null;
        switch (columnIndex) {
            case DNI:
                result = usuario.getDni();
                break;
            case NOMBRE:
                result = usuario.getNombre();
                break;
            case CLAVE:
                result =  usuario.getClave();
                break;
            default:
                result = new String("");
        }
        return result;
	}

	
	public List<Usuario> getContenido(){
		return contenido;
	}
	
	public void setContenido(List<Usuario> contenido) {
		this.contenido = contenido;
	}
}
