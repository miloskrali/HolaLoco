package ui;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.WindowConstants;

import db.CuentaDAO;
import db.TarjetaDAO;
import db.UsuarioDAO;
import excepciones.DAOException;

public class UXPrincipalAdmin extends JFrame implements ActionListener {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private UsuarioDAO usuarioDAO;
    private CuentaDAO cuentaDAO;
    private TarjetaDAO tarjetaDAO;
    private JTabbedPane tabbedPane;
    private PanelUsuarios panelUsuarios;
    private PanelCuenta panelCuentas;
    private PanelTarjetas panelTarjetas;
    private JButton btnSalir;

    public UXPrincipalAdmin(UsuarioDAO usuarioDAO, CuentaDAO cuentaDAO, TarjetaDAO tarjetaDAO){
        super("Panel de Administración");
        this.usuarioDAO = usuarioDAO;
        this.cuentaDAO = cuentaDAO;
        this.tarjetaDAO = tarjetaDAO;
        armarInterfaz();
    }

	private void armarInterfaz(){
        setSize(800, 600);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        tabbedPane = new JTabbedPane();

        panelUsuarios = new PanelUsuarios(usuarioDAO);
        tabbedPane.addTab("Usuarios", panelUsuarios);

        panelCuentas = new PanelCuenta(cuentaDAO,true);
        tabbedPane.addTab("Cuentas", panelCuentas);

        panelTarjetas = new PanelTarjetas(tarjetaDAO,true);
        tabbedPane.addTab("Tarjetas", panelTarjetas);

        btnSalir = new JButton("Salir");
        btnSalir.addActionListener(this);

        JPanel panelBotones = new JPanel();
        panelBotones.add(btnSalir);

        add(tabbedPane, BorderLayout.CENTER);
        add(panelBotones, BorderLayout.SOUTH);
    }

    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == btnSalir) {
            int opcion = JOptionPane.showConfirmDialog(this, "¿Estás seguro que deseas salir?", "Confirmar Salida", JOptionPane.YES_NO_OPTION);
            if (opcion == JOptionPane.YES_OPTION) {
                System.exit(0);
            }
        }
    }
}