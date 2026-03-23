/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package bbdd;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import javax.swing.table.DefaultTableModel;

/**
 * Clase donde almacena la conexion con la base de datos
 * @author grupo
 */
public class Conexion {
    
    /**
     * Variable que representa la conexión a la base de datos
     */
    public static Connection conn;
    
    /**
     * Método para conectar la base de datos MySQL y crear una nueva conexión
     */
    public static void Conectar(){
        try {
            Class.forName("com.mysql.jdbc.Driver");
            conn = DriverManager.getConnection("jdbc:mysql://localhost/proyectoferreteria",
                    "root","");
            System.out.println("Conexion abierta");
        } catch (ClassNotFoundException | SQLException ex) {
            System.getLogger(Conexion.class.getName()).log(System.Logger.Level.ERROR, (String) null, ex);
        }
    }
    
    /**
     * Método para cerrar la conexión con la base de datos
     */
    public static void Cerrar(){
        try {
            conn.close();
            System.out.println("Conexion cerrada");
        } catch (SQLException ex) {
            System.getLogger(Conexion.class.getName()).log(System.Logger.Level.ERROR, (String) null, ex);
        }
    }
    
    /* VISTA LOGIN */
    /**
     * Consulta que listara el usuario y la contraseña si el usuario y la contraseña esten almacenados en la base de datos.
     * @param user Usuario que desea iniciar sesion.
     * @param pass Contraseña ingresada del usuario a logarse.
     * @return true de un user logado.
     */
    public static boolean Acceder(String user, String pass){
        String consulta = "SELECT usuario, pass FROM usuarios where usuario=? and pass=?";
        Conectar();
        try {
            PreparedStatement ps;
            ResultSet rs;
            ps = conn.prepareStatement(consulta);
            ps.setString(1, user);
            ps.setString(2, pass);
            rs = ps.executeQuery();
            return rs.next();
        } catch (SQLException ex) {
            System.getLogger(Conexion.class.getName()).log(System.Logger.Level.ERROR, (String) null, ex);
        } finally {
            Cerrar();
        }
        return false;
    }
    
    /**
     * Método para recuperar el tipo de usuario logado si es User o si es Admin
     * @param user Usuario logado correcto
     * @return tipo del usuario logado
     */
    public static String RecuperarTipo(String user){
        String tipo = "";
        String consulta = "SELECT tipo FROM usuarios WHERE usuario = '"+user+"'";
        Conectar();
        try {
            Statement st;
            ResultSet rs;
            st = conn.createStatement();
            rs = st.executeQuery(consulta);
            if(rs.next()){
                tipo = rs.getString(1);
            }
        } catch (SQLException ex) {
            System.getLogger(Conexion.class.getName()).log(System.Logger.Level.ERROR, (String) null, ex);
        } finally {
            Cerrar();
        }
        return tipo;
    }
    
    /**
     * Método para obtener los ultimos tres articulos registrados en la tabla de productos
     * y asi imprimiendo en un modelo de tabla para asi mostrar
     * el codigo del producto, nombre del articulo, su categoria, el precio de venta
     * y el stock ordenado por la fecha de alta de manera descendente
     * @param modelo Modelo de tabla donde se tendra que mostrar estos datos
     */
    public static void ObtenerUltimosTresArticulos(DefaultTableModel modelo){
        Object datos[] = new Object[5];
        String consulta = "select codProducto, nombre, categoria, precio_venta, stock from producto " +
                "order by fecha_alta desc " +
                "limit 3";
        Conectar();
        try {
            Statement st = conn.createStatement();
            ResultSet rs = st.executeQuery(consulta);
            while (rs.next()) {
                datos[0] = rs.getString(1);
                datos[1] = rs.getString(2);
                datos[2] = rs.getString(3);
                datos[3] = rs.getDouble(4);
                datos[4] = rs.getInt(5);
                modelo.addRow(datos);
            }
        } catch (SQLException ex) {
            System.getLogger(Conexion.class.getName()).log(System.Logger.Level.ERROR, (String) null, ex);
        } finally {
            Cerrar();
        }
    }
    
}// End Class
