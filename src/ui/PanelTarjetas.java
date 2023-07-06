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
import db.TarjetaDAOH2;
import entidades.Tarjeta;

public class PanelTarjetas extends JPanel implements ActionListener {

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
    	TarjetaDAOH2 dao = (TarjetaDAOH2) tarjetaDAO;
        if (e.getSource() == buttonBorrar) {
            int filaSeleccionada = this.tablaTarjetas.getSelectedRow();
            Tarjeta tarjeta = this.modelo.getContenido().get(filaSeleccionada);
            try {
                modelo.getContenido().remove(filaSeleccionada);
                modelo.fireTableDataChanged();
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Error al borrar la tarjeta: " + ex.getMessage());
            }
        } else if (e.getSource() == buttonAgregar) {
            try {
                Tarjeta tarjeta = crearTarjeta();
                modelo.getContenido().add(tarjeta);
                modelo.fireTableDataChanged();
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Error al agregar la tarjeta: " + ex.getMessage());
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