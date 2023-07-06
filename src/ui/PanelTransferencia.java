package ui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import db.CuentaDAO;
import db.CuentaDAOH2;
import entidades.Cuenta;
import excepciones.DAOException;
import excepciones.SaldoInsuficienteException;

public class PanelTransferencia extends JPanel {
    private CuentaDAO cuentaDAO;
    private JComboBox<String> comboBoxCuentasOrigen;
    private JComboBox<String> comboBoxCuentasDestino;
    private JTextField textFieldMonto;
    private JButton btnTransferir;
    private JLabel lblMensaje;

    public PanelTransferencia() {
        cuentaDAO = new CuentaDAOH2();

        setLayout(null);

        JLabel lblCuentaOrigen = new JLabel("Cuenta Origen:");
        lblCuentaOrigen.setBounds(20, 20, 100, 20);
        add(lblCuentaOrigen);

        comboBoxCuentasOrigen = new JComboBox<>();
        comboBoxCuentasOrigen.setBounds(130, 20, 200, 20);
        add(comboBoxCuentasOrigen);

        JLabel lblCuentaDestino = new JLabel("Cuenta Destino:");
        lblCuentaDestino.setBounds(20, 50, 100, 20);
        add(lblCuentaDestino);

        comboBoxCuentasDestino = new JComboBox<>();
        comboBoxCuentasDestino.setBounds(130, 50, 200, 20);
        add(comboBoxCuentasDestino);

        JLabel lblMonto = new JLabel("Monto:");
        lblMonto.setBounds(20, 80, 100, 20);
        add(lblMonto);

        textFieldMonto = new JTextField();
        textFieldMonto.setBounds(130, 80, 200, 20);
        add(textFieldMonto);

        btnTransferir = new JButton("Transferir");
        btnTransferir.setBounds(130, 110, 100, 30);
        add(btnTransferir);

        lblMensaje = new JLabel();
        lblMensaje.setBounds(20, 150, 400, 20);
        add(lblMensaje);

        cargarCuentas();

        btnTransferir.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                transferir();
            }
        });
    }

    private void cargarCuentas() {
        comboBoxCuentasOrigen.removeAllItems();
        comboBoxCuentasDestino.removeAllItems();

        for (Cuenta cuenta : cuentaDAO.listarTodasLasCuentas()) {
            String cuentaInfo = cuenta.getTipo() + " - " + cuenta.getNumeroCuenta();
            comboBoxCuentasOrigen.addItem(cuentaInfo);
            comboBoxCuentasDestino.addItem(cuentaInfo);
        }
    }

    private void transferir() {
        String cuentaOrigenInfo = (String) comboBoxCuentasOrigen.getSelectedItem();
        String cuentaDestinoInfo = (String) comboBoxCuentasDestino.getSelectedItem();
        String montoStr = textFieldMonto.getText();

        if (montoStr.isEmpty()) {
            lblMensaje.setText("Debe ingresar un monto");
            return;
        }

        double monto = Double.parseDouble(montoStr);

        if (monto < 0) {
            lblMensaje.setText("El monto no puede ser negativo");
            return;
        }

        int numeroCuentaOrigen = obtenerNumeroCuenta(cuentaOrigenInfo);
        int numeroCuentaDestino = obtenerNumeroCuenta(cuentaDestinoInfo);

        if (numeroCuentaOrigen == numeroCuentaDestino) {
            lblMensaje.setText("No se puede transferir dinero a la misma cuenta");
            return;
        }

        try {
            Cuenta cuentaOrigen = null;
            for (Cuenta cuenta : cuentaDAO.listarTodasLasCuentas()) {
                if (cuenta.getNumeroCuenta() == numeroCuentaOrigen) {
                    cuentaOrigen = cuenta;
                    break;
                }
            }

            if (cuentaOrigen != null) {
                Cuenta cuentaDestino = cuentaDAO.buscarCuentaPorID(numeroCuentaDestino);
                if (cuentaDestino != null) {
                    cuentaDAO.realizarTransferencia(cuentaOrigen, numeroCuentaDestino, monto);

                    cargarCuentas();

                    lblMensaje.setText("Transferencia realizada con éxito");

                    comboBoxCuentasOrigen.setSelectedIndex(0);
                    comboBoxCuentasDestino.setSelectedIndex(0);
                    textFieldMonto.setText("");
                } else {
                    lblMensaje.setText("No se encontró la cuenta destino");
                }
            } else {
                lblMensaje.setText("No se encontró la cuenta origen");
            }
        } catch (DAOException e) {
            lblMensaje.setText("Error al realizar la transferencia");
            e.printStackTrace();
        } catch (SaldoInsuficienteException e) {
            lblMensaje.setText("Saldo insuficiente en la cuenta origen");
        }
    }

    private int obtenerNumeroCuenta(String cuentaInfo) {
        String[] parts = cuentaInfo.split(" - ");
        return Integer.parseInt(parts[1]);
    }
}