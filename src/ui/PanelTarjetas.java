package ui;

import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;

import db.TarjetaDAO;
import entidades.Tarjeta;
import excepciones.DAOException;
import excepciones.DuplicadoException;
import excepciones.UsuarioNoExistenteException;

public class PanelTarjetas extends JPanel implements ActionListener {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JTable tablaTarjetas;
    private TableModelTarjetas modelo;
    private TarjetaDAO tarjetaDAO;
    private JScrollPane scroll;
    private JButton buttonBorrar;
    private JButton buttonAgregar;
    private JList<String> listarTarjetasPropias;

    public PanelTarjetas(TarjetaDAO tarjetaDAO, boolean esAdministrador){
        super();
        this.tarjetaDAO = tarjetaDAO;
        armarPanel();
        cargarTodasLasTarjetas();
    }


    public PanelTarjetas(int dni, TarjetaDAO tarjetaDAO) {
      this.tarjetaDAO = tarjetaDAO;
      JLabel lblTarjetaPropia = new JLabel("Tarjetas propias:");
      lblTarjetaPropia.setBounds(40, 40, 100, 40);
      add(lblTarjetaPropia);
      listarTarjetasPropias = new JList<>();
      JScrollPane scrollPane = new JScrollPane(listarTarjetasPropias);
      scrollPane.setBounds(130, 60, 200, 100);
      add(scrollPane);
      cargarTarjetas(dni);
    }
    
    private void armarPanel() {
        this.setLayout(new FlowLayout());

        modelo = new TableModelTarjetas();
        tablaTarjetas = new JTable(modelo);
        scroll = new JScrollPane(tablaTarjetas);
        this.add(scroll);

        buttonAgregar = new JButton("Agregar");
        buttonAgregar.addActionListener(this);
        this.add(buttonAgregar);

        buttonBorrar = new JButton("Borrar");
        buttonBorrar.addActionListener(this);
        this.add(buttonBorrar);
    }


	private void cargarTarjetas(int dni) {
        List<Tarjeta> tarjetasPropias = tarjetaDAO.listarTarjetasUsuario(dni);
        String[] tarjetasPropiasArr = new String[tarjetasPropias.size()];
        for (int i = 0; i < tarjetasPropias.size(); i++) {
            Tarjeta tarjeta = tarjetasPropias.get(i);
            String tarjetaInfo = " Dni: " + tarjeta.getDni() + " - Numero Tarjeta: " + tarjeta.getNumero() + " - Disponible: " + tarjeta.getDisponible() + " - Saldo a Pagar: " + tarjeta.getSaldoPagar(); 
            tarjetasPropiasArr[i] = tarjetaInfo;
        }
        listarTarjetasPropias.setListData(tarjetasPropiasArr);
	}
    
    public void cargarTodasLasTarjetas(){
        List<Tarjeta> listaTarjetas = tarjetaDAO.listarTodasLasTarjetas();
        modelo.setContenido(listaTarjetas);
        modelo.fireTableDataChanged();
    }

    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == buttonBorrar) {
            int filaSeleccionada = this.tablaTarjetas.getSelectedRow();
            int clave = (int) modelo.getValueAt(filaSeleccionada, 1);
            modelo.getContenido().remove(filaSeleccionada);
            modelo.fireTableDataChanged();
            try {
                tarjetaDAO.eliminarTarjeta(clave);
            } catch (DAOException e1) {
				e1.printStackTrace();
            }
        } else if (e.getSource() == buttonAgregar) {
            try {
            	Tarjeta nuevaTarjeta= crearTarjeta();
                try {
					tarjetaDAO.agregarTarjeta(nuevaTarjeta);
					modelo.getContenido().add(nuevaTarjeta);
		            modelo.fireTableDataChanged();
				} catch (UsuarioNoExistenteException e1) {
					JOptionPane.showMessageDialog(this, "El numero de cuenta no existe.", "Error", JOptionPane.ERROR_MESSAGE);
				}
               
            } catch (DuplicadoException ex) {
            	JOptionPane.showMessageDialog(this, "El numero de tarjeta ya existe.", "Error", JOptionPane.ERROR_MESSAGE);
            } catch (DAOException e1) {
				e1.printStackTrace();
			}
        }
    }


    private Tarjeta crearTarjeta() {
        int dni = obtenerDniDesdeInput();
        int numeroTarjeta = obtenerNumeroTarjetaDesdeInput();
        double disponible = obtenerDisponibleDesdeInput();
        double saldoPagar = obtenerSaldoPagarDesdeInput();

        return new Tarjeta(dni, numeroTarjeta, disponible, saldoPagar);
    }

    private int obtenerDniDesdeInput() {
        String dniStr = JOptionPane.showInputDialog("Ingrese el DNI: ");
        return Integer.parseInt(dniStr);
    }

    private int obtenerNumeroTarjetaDesdeInput() {
        String numeroTarjetaStr = JOptionPane.showInputDialog("Ingrese el número de tarjeta: ");
        
        return Integer.parseInt(numeroTarjetaStr);
    }

    private double obtenerDisponibleDesdeInput() {
        String disponibleStr = JOptionPane.showInputDialog("Ingrese el disponible: ");
        return Double.parseDouble(disponibleStr);
    }

    private double obtenerSaldoPagarDesdeInput() {
        String saldoPagarStr = JOptionPane.showInputDialog("Ingrese el saldo a pagar: ");
        return Double.parseDouble(saldoPagarStr);
    }
}