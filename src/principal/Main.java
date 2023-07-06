package principal;

import db.DBTableManager;
import db.UsuarioDAO;
import db.UsuarioDAOH2;
import entidades.Administrador;
import excepciones.DAOException;
import excepciones.DuplicadoException;
import excepciones.SaldoInsuficienteException;
import excepciones.SaldonegativoException;
import ui.Login;

public class Main {

    public static void main(String[] args) throws SaldonegativoException, SaldoInsuficienteException, DAOException {

        DBTableManager table = new DBTableManager();
        table.createUserTable();
        table.createCuentaTable();
        table.createTarjetaTable();
        table.createMovimientoTable();

//        MovimientoDAO daomovimiento = new MovimientoDAOH2();
        UsuarioDAO dao = new UsuarioDAOH2();

        int dni = 44482701;
        String nombre = "admin";
        String clave = "admin";
        Administrador admin = new Administrador();
        admin.setDni(dni);
        admin.setNombre(nombre);
        admin.setClave(clave);
        
        try {
			dao.crearUsuario(admin);
		} catch (DAOException e) {
			e.printStackTrace();
		} catch (DuplicadoException e) {
			e.printStackTrace();
		}
        
//        Usuario u1 = admin.crearUsuario(44123654, "Jose", "Chicito");
//        Usuario u2 = admin.crearUsuario(30619522, "Nicole", "Cenicienta");
//        Usuario u3 = admin.crearUsuario(20111333, "Emiliano", "Cacatua");

//        System.out.println("------------MODIFICACIÓN DE USUARIO---------");
//        admin.modificarUsuario(u1);
//
//        System.out.println("-----------LISTA DE USUARIOS--------");
//        List<Usuario> listaTodosLosUsuarios = dao.listaTodosLosUsuarios();
//        for (Usuario usuarios : listaTodosLosUsuarios) {
//            System.out.println(usuarios);
//        }
//
////        System.out.println("----------SE ELIMINA USUARIO--------");
////        admin.eliminarUsuario(30619522, table);

//        System.out.println("----------SE CREA CUENTA----------");
//        Cuenta c1 = admin.crearCuenta(u1, "Caja_Ahorro", 01, 10000.0);
//        Cuenta c2 = admin.crearCuenta(u1, "Cta_Cte", 02, 500000.0);
//        Cuenta c3 = admin.crearCuenta(u3, "Caja_Ahorro", 03, 20000.0);

//        System.out.println(u1);
//        System.out.println(u2);
//        System.out.println(u3);

//        System.out.println("----------SE CREA TARJETA----------");
//        Tarjeta t1 = admin.crearTarjeta(u1, 123123, 200000.0, 80000.0);
//        Tarjeta t2 = admin.crearTarjeta(u1, 456456, 50000.0, 20000.0);
//        Tarjeta t3 = admin.crearTarjeta(u2, 789789, 100000.0, 50000.0);
//        Tarjeta t4 = admin.crearTarjeta(u3, 987987, 150000.0, 70000.0);
//        System.out.println(t1);
//        System.out.println(t2);
//        System.out.println(t3);
//        System.out.println(t4);

//        System.out.println("----------SE AGREGAN MOVIMIENTOS----------");
//        Movimiento m1 = new Movimiento(1, t1.getNumero(), 500.0, "Compra en línea",dni);
//        daomovimiento.agregarMovimiento(m1);
//
//        Movimiento m2 = new Movimiento(2, t2.getNumero(), 1000.0, "Retiro de efectivo",dni);
//        daomovimiento.agregarMovimiento(m2);
//
//        Movimiento m3 = new Movimiento(3, t3.getNumero(), 1500.0, "Transferencia",dni);
//        daomovimiento.agregarMovimiento(m3);
//
//        Movimiento m4 = new Movimiento(4, t4.getNumero(), 2000.0, "Pago de factura",dni);
//        daomovimiento.agregarMovimiento(m4);



        Login loginFrame = new Login();
        loginFrame.setVisible(true);
    }
}