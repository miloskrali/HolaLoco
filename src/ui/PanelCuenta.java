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

import db.CuentaDAO;
import entidades.Cuenta;
import excepciones.DAOException;
import excepciones.DuplicadoException;
import excepciones.UsuarioNoExistenteException;

public class PanelCuenta extends JPanel implements ActionListener {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JTable tablaCuenta;
    private TableModelCuenta modelo;
    private CuentaDAO cuentaDAO;
    private JScrollPane scroll;
    private JButton buttonTrans;
    private JButton buttonBorrar;
    private JButton buttonAgregar;
    private JList<String> listaCuentasPropias;
    private boolean esAdministrador;

    public PanelCuenta(CuentaDAO cuentaDAO, boolean esAdministrador) {
        super();
        this.cuentaDAO = cuentaDAO;
        this.esAdministrador = esAdministrador;
        armarPanel();
        cargarTodasLasCuentas();
    }

    public PanelCuenta(int dni, CuentaDAO cuentaDAO) {
        this.cuentaDAO = cuentaDAO;
        JLabel lblCuentaPropia = new JLabel("Cuentas propias:");
        lblCuentaPropia.setBounds(40, 40, 100, 40);
        add(lblCuentaPropia);
        listaCuentasPropias = new JList<>();
        JScrollPane scrollPane = new JScrollPane(listaCuentasPropias);
        scrollPane.setBounds(130, 60, 200, 100);
        add(scrollPane);
        cargarCuentas(dni);
	}

	private void armarPanel() {
        this.setLayout(new FlowLayout());

        modelo = new TableModelCuenta();
        tablaCuenta = new JTable(modelo);
        scroll = new JScrollPane(tablaCuenta);
        this.add(scroll);

        buttonAgregar = new JButton("Agregar");
        buttonAgregar.addActionListener(this);
        this.add(buttonAgregar);

        buttonBorrar = new JButton("Borrar");
        buttonBorrar.addActionListener(this);
        this.add(buttonBorrar);

        if (!esAdministrador) {
            buttonTrans = new JButton("Realizar Transferencia");
            buttonTrans.addActionListener(this);
            this.add(buttonTrans);
        }
    }

	private void cargarCuentas(int dni) {
        List<Cuenta> cuentasPropias = cuentaDAO.listarCuentasUsuario(dni);
        String[] cuentasPropiasArr = new String[cuentasPropias.size()];
        for (int i = 0; i < cuentasPropias.size(); i++) {
            Cuenta cuenta = cuentasPropias.get(i);
            String cuentaInfo = " Tipo de cuenta: " +cuenta.getTipo() + " - Numero de cuenta: " + cuenta.getNumeroCuenta() + " - Saldo: " + cuenta.getSaldo();
            cuentasPropiasArr[i] = cuentaInfo;
        }
        listaCuentasPropias.setListData(cuentasPropiasArr);
	}
	
    public void cargarTodasLasCuentas() {
        List<Cuenta> listaCuentas = cuentaDAO.listarTodasLasCuentas();
        modelo.setContenido(listaCuentas);
        modelo.fireTableDataChanged();
    }

    public void actionPerformed(ActionEvent e) {
    	if (e.getSource() == buttonBorrar) {
            int filaSeleccionada = this.tablaCuenta.getSelectedRow();
            int clave = (int) modelo.getValueAt(filaSeleccionada, 2);
            this.modelo.getContenido().remove(filaSeleccionada);
            modelo.fireTableDataChanged();
            try {
				cuentaDAO.borrarCuenta(clave);
			} catch (DAOException e1) {
				e1.printStackTrace();
			}
        } else if (e.getSource() == buttonAgregar) {
            Cuenta nuevaCuenta = crearCuenta();
            try {
				try {
					cuentaDAO.crearCuenta(nuevaCuenta);
					modelo.getContenido().add(nuevaCuenta);
		            modelo.fireTableDataChanged();
				} catch (UsuarioNoExistenteException e1) {
					JOptionPane.showMessageDialog(this, "El numero de cuenta no existe.", "Error", JOptionPane.ERROR_MESSAGE);
				}
			} catch (DuplicadoException ex) {
            	JOptionPane.showMessageDialog(this, "El numero de cuenta ya existe.", "Error", JOptionPane.ERROR_MESSAGE);
            } catch (DAOException e1) {
				e1.printStackTrace();
			}
            
        }
    }
    

    private Cuenta crearCuenta() {
        int dni = obtenerDniDesdeInput();
        String tipo = obtenerTipoDesdeInput();
        int numeroCuenta = obtenerNumeroCuentaDesdeInput();
        double saldo = obtenerSaldoDesdeInput();

        return new Cuenta(dni, tipo, numeroCuenta, saldo);
    }

    private int obtenerDniDesdeInput() {
        String dniStr = JOptionPane.showInputDialog("Ingrese el DNI: ");
        return Integer.parseInt(dniStr);
    }

    private String obtenerTipoDesdeInput() {
        String tipo = JOptionPane.showInputDialog("Ingrese el tipo de cuenta: ");
        return tipo;
    }

    private int obtenerNumeroCuentaDesdeInput() {
        String numeroCuentaStr = JOptionPane.showInputDialog("Ingrese el número de cuenta: ");
        return Integer.parseInt(numeroCuentaStr);
    }

    private double obtenerSaldoDesdeInput() {
        String saldoStr = JOptionPane.showInputDialog("Ingrese el saldo: ");
        return Double.parseDouble(saldoStr);
    }
}