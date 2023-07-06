package ui;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import db.CuentaDAO;
import db.CuentaDAOH2;
import db.DBManager;
import db.TarjetaDAO;
import db.TarjetaDAOH2;
import db.UsuarioDAO;
import db.UsuarioDAOH2;
import entidades.Usuario;

public class Login extends JFrame {
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JTextField usernameField;
    private JPasswordField passwordField;
    private Connection connection;
    
    public Login() {
        super("Inicio de sesión");

        UsuarioDAO dao = new UsuarioDAOH2();
        CuentaDAO daocuenta = new CuentaDAOH2();
        Usuario usuario = new Usuario();
        JLabel usernameLabel = new JLabel("Nombre de usuario:");
        JLabel passwordLabel = new JLabel("Contraseña:");
        usernameField = new JTextField(20);
        passwordField = new JPasswordField(20);
        JButton loginButton = new JButton("Ingresar");

        setLayout(new GridLayout(3, 2));
        add(usernameLabel);
        add(usernameField);
        add(passwordLabel);
        add(passwordField);
        add(new JLabel());
        add(loginButton);

        try {
            String url = "jdbc:h2:tcp://localhost/C:/Users/Usuario/HolaLoco/h2/base_de_datos/banco";
            String user = "sa";
            String passwordDB = "";
            connection = DriverManager.getConnection(url, user, passwordDB);
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error al conectar a la base de datos");
            System.exit(1);
        }

        loginButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String username = usernameField.getText();
                String password = new String(passwordField.getPassword());
                int dni;

                if (validateLogin(username, password)) {
                    JOptionPane.showMessageDialog(Login.this, "Inicio de sesión exitoso");
                    dispose();
                    if (username.equals("admin")) {
                		UsuarioDAO usuarioDAO = new UsuarioDAOH2();
                		CuentaDAO cuentaDAO = new CuentaDAOH2();
                		TarjetaDAO tarjetaDAO = new TarjetaDAOH2();

                		UXPrincipalAdmin ventana = new UXPrincipalAdmin(usuarioDAO, cuentaDAO, tarjetaDAO);
                		ventana.setVisible(true);
					}else {
						dni = buscarCuentaPorID(username, password);
	                    UXPrincipal ventanaPrincipal = new UXPrincipal(dni);
	                    ventanaPrincipal.setVisible(true);
					}

                } else {
                    JOptionPane.showMessageDialog(Login.this, "Credenciales inválidas");
                }
            }
        });

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        pack();
        setLocationRelativeTo(null);
    }

    
	public int buscarCuentaPorID(String username, String password) {
        String sql = "SELECT dni FROM usuarios WHERE nombre = '" + username + "' AND clave = '" + password + "'";
	    Connection c = DBManager.connect();
	    try {
	        Statement s = c.createStatement();
	        ResultSet rs = s.executeQuery(sql);

	        if (rs.next()) {
	            int dniUsuario = rs.getInt("dni");

	            return dniUsuario;
	        }
	    } catch (SQLException e) {
	        e.printStackTrace();
	    } finally {
	        try {
	            c.close();
	        } catch (SQLException e1) {
	            e1.printStackTrace();
	        }
	    }

	    return 0;
	}
    
    private boolean validateLogin(String username, String password) {
        try {
            String query = "SELECT * FROM usuarios WHERE nombre = ? AND clave = ?";
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1, username);
            statement.setString(2, password);
            ResultSet resultSet = statement.executeQuery();
            return resultSet.next();
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}