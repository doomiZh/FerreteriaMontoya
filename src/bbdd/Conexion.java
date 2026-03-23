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
                    "root","mysql");
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
    
    
    
}// End Class
