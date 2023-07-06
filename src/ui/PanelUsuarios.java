package ui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;

import db.UsuarioDAO;
import entidades.Usuario;
import excepciones.DAOException;
import excepciones.DuplicadoException;

public class PanelUsuarios extends JPanel implements ActionListener {

    private JTable tablaUsuarios;
    private TableModelUsuario modelo;
    private UsuarioDAO usuarioDAO;
    private JScrollPane scroll;
    private JButton buttonBorrar;
    private JButton buttonAgregar;

    public PanelUsuarios(UsuarioDAO usuarioDAO) {
        super();
        this.usuarioDAO = usuarioDAO;
        armarPanel();
        cargarUsuarios();
    }

    private void armarPanel() {
        this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

        modelo = new TableModelUsuario();
        tablaUsuarios = new JTable(modelo);
        scroll = new JScrollPane(tablaUsuarios);
        this.add(scroll);

        buttonAgregar = new JButton("Agregar");
        buttonAgregar.addActionListener(this);
        this.add(buttonAgregar);

        buttonBorrar = new JButton("Borrar");
        buttonBorrar.addActionListener(this);
        this.add(buttonBorrar);
    }

    private void cargarUsuarios() {
        List<Usuario> listaUsuarios = usuarioDAO.listaTodosLosUsuarios();
        modelo.setContenido(listaUsuarios);
        modelo.fireTableDataChanged();
    }

    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == buttonAgregar) {
            String dniString = JOptionPane.showInputDialog(this, "Ingrese el DNI del nuevo usuario:");
            if (dniString == null || dniString.isEmpty()) {
                return;
            }

            int dni;
            try {
                dni = Integer.parseInt(dniString);
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "El DNI debe ser un número válido.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            String nombre = JOptionPane.showInputDialog(this, "Ingrese el nombre del nuevo usuario:");
            if (nombre == null || nombre.isEmpty()) {
                return;
            }

            String clave = JOptionPane.showInputDialog(this, "Ingrese la clave del nuevo usuario:");
            if (clave == null || clave.isEmpty()) {
                return;
            }

            Usuario nuevoUsuario = new Usuario(dni, nombre, clave);

            try {
                usuarioDAO.crearUsuario(nuevoUsuario);
                modelo.getContenido().add(nuevoUsuario);
                modelo.fireTableDataChanged();
            } catch (DuplicadoException ex) {
                JOptionPane.showMessageDialog(this, "El usuario ya existe.", "Error", JOptionPane.ERROR_MESSAGE);
            } catch (DAOException e1) {
                e1.printStackTrace();
            }
        }

        if (e.getSource() == buttonBorrar) {
            int filaSeleccionada = this.tablaUsuarios.getSelectedRow();
            Usuario usuario = this.modelo.getContenido().get(filaSeleccionada);

            this.modelo.getContenido().remove(filaSeleccionada);
            modelo.fireTableDataChanged();
        }
    }
}
