/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package bbdd;

import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.table.DefaultTableModel;
import modelos.Accesos;
import modelos.Categorias;
import modelos.Origenes;
import modelos.Productos;
import modelos.Tiendas;
import modelos.Usuarios;

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
            conn = DriverManager.getConnection("jdbc:mysql://localhost:3307/proyectoferreteria",
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
        String consulta = "SELECT usuario, pass FROM usuarios where usuario=? and pass=? and estado ='activo'";
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
    
    /* ACCESOS */
    /**
     * Método para registrar los accesos del usuario donde registra el nombre de usuario,
     * fecha de acceso, y la conexion ip del usuario a cual ingresa al programa
     * @param cli Clase Accesos del proyecto donde se almacena el acceso
     * @return true si se registro el acceso de un usuario.
     */
    public static boolean RegistrarAccesos(Accesos cli) {
        String consulta = "INSERT INTO accesos (usuario, fecha, ip) VALUES (?, ?, ?)";
        Conectar();
        try {
            PreparedStatement st = conn.prepareStatement(consulta);
            st.setString(1, cli.getUsuario());
            st.setDate(2, new Date(cli.getFechaAcceso().getTime()));
            st.setString(3, cli.getIp());
            st.execute();
            return true;
        } catch (SQLException ex) {
            System.getLogger(Conexion.class.getName()).log(System.Logger.Level.ERROR, (String) null, ex);
        } finally {
            Cerrar();
        }
        return false;
    }
    
    
    /* PANTALLA PRINCIPAL */
    
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
    
    /* OPCIONES */
    
    /**
     * Método para imprimr el listado de articulo en un JTable donde tendra
     * el codigo, nombre, categoria y precio de venta del producto o artículo.
     * @param modelo JTable donde se cargará los datos.
     */
    public static void ObtenerListadoArticulo(DefaultTableModel modelo){
        modelo.setRowCount(0);
        Object datos[] = new Object[4];
        String consulta = "select codProducto, nombre, categoria, precio_venta from producto";
        Conectar();
        try {
            Statement st = conn.createStatement();
            ResultSet rs = st.executeQuery(consulta);
            while (rs.next()) {
                datos[0] = rs.getString(1);
                datos[1] = rs.getString(2);
                datos[2] = rs.getString(3);
                datos[3] = rs.getDouble(4);
                modelo.addRow(datos);
            }
        } catch (SQLException ex) {
            System.getLogger(Conexion.class.getName()).log(System.Logger.Level.ERROR, (String) null, ex);
        } finally {
            Cerrar();
        }
    }
    
    /**
     * Método para imprimir los articulos que son destacados en un JTable donde
     * tendra el codigo, nombre, categoria y precio de venta del producto o
     * artículo sea si destacado es 'SI'
     * @param modelo JTable donde se cargará los datos
     */
    public static void ObtenerListadoArtDestacado(DefaultTableModel modelo){
        Object datos[] = new Object[4];
        String consulta = "select codProducto, nombre, categoria, precio_venta from producto where destacado='SI'";
        Conectar();
        try {
            Statement st = conn.createStatement();
            ResultSet rs = st.executeQuery(consulta);
            while (rs.next()){
                datos[0] = rs.getString(1);
                datos[1] = rs.getString(2);
                datos[2] = rs.getString(3);
                datos[3] = rs.getDouble(4);
                modelo.addRow(datos);
            }
        } catch (SQLException ex) {
            System.getLogger(Conexion.class.getName()).log(System.Logger.Level.ERROR, (String) null, ex);
        } finally {
            Cerrar();
        }
    }
    
    /**
     * Método para imprimir los articulos que son destacados y estan en oferta
     * en un JTable donde tendra el codigo, nombre, categoria y precio de venta
     * del producto o artículo sea si el destacado y el oferta es 'SI'
     * @param modelo JTable donde se cargará los datos
     */
    public static void ObtenerListadoArtOferta(DefaultTableModel modelo){
        Object datos[] = new Object[4];
        String consulta = "select codProducto, nombre, categoria, precio_venta from producto where destacado='SI' and oferta='SI'";
        Conectar();
        try {
            Statement st = conn.createStatement();
            ResultSet rs = st.executeQuery(consulta);
            while (rs.next()){
                datos[0] = rs.getString(1);
                datos[1] = rs.getString(2);
                datos[2] = rs.getString(3);
                datos[3] = rs.getDouble(4);
                modelo.addRow(datos);
            }
        } catch (SQLException ex) {
            System.getLogger(Conexion.class.getName()).log(System.Logger.Level.ERROR, (String) null, ex);
        } finally {
            Cerrar();
        }
    }
    
    /**
     * Método para cargar las categorias de un combo box almacenado en la tabla categorias de bbdd
     * @param combo JComboBox donde mostrará los datos de categorias donde se requiera.
     */
    public static void CargarCategoria(JComboBox combo){
        String consulta = "select categoria from categorias";
        Conectar();
        try {
            Statement st = conn.createStatement();
            ResultSet rs = st.executeQuery(consulta);
            while (rs.next()) {
                combo.addItem(rs.getString(1));
            }
        } catch (SQLException ex) {
            System.getLogger(Conexion.class.getName()).log(System.Logger.Level.ERROR, (String) null, ex);
        } finally {
            Cerrar();
        }
    }
    
    /**
     * Método para cargar los origenes de un combo box almacenado en la tabla origen de bbdd
     * @param combo JComboBox donde mostrará los datos de origen donde se requiera.
     */
    public static void CargarOrigen(JComboBox combo){
        String consulta = "select origen from origen";
        Conectar();
        try {
            Statement st = conn.createStatement();
            ResultSet rs = st.executeQuery(consulta);
            while (rs.next()) {
                combo.addItem(rs.getString(1));
            }
        } catch (SQLException ex) {
            System.getLogger(Conexion.class.getName()).log(System.Logger.Level.ERROR, (String) null, ex);
        } finally {
            Cerrar();
        }
    }
    
    /**
     * Método para cargar las tiendas de un combo box almacenado en la tabla origen de bbdd
     * @param combo JComboBox donde mostrará los datos de origen donde se requiera.
     */
    public static void CargarTienda(JComboBox combo){
        String consulta = "select denominacion from tiendas";
        Conectar();
        try {
            Statement st = conn.createStatement();
            ResultSet rs = st.executeQuery(consulta);
            while (rs.next()) {
                combo.addItem(rs.getString(1));
            }
        } catch (SQLException ex) {
            System.getLogger(Conexion.class.getName()).log(System.Logger.Level.ERROR, (String) null, ex);
        } finally {
            Cerrar();
        }
    }
    
    /**
     * Método para mostrar el formulario de cada artículo donde almacena el nombre,
     * categoria, descripcion, precio de compra, precio de venta, stock (cantidad),
     * origen, si es destacado y si es oferta.
     * @param codigo Codigo del producto que esta seleccionado
     * @return Información del producto almacenado por cada columna distribuida en orden de envio de 
     * la consulta.
     */
    public static Productos MostrarFormularioArticulo(String codigo) {
        Productos p = null;
        String consulta = "select nombre, categoria, descripcion, precio_compra, precio_venta, stock, origen, destacado, oferta from producto "
                        + "where codProducto = '"+codigo+"'";
        Conectar();
        try {
            PreparedStatement ps = conn.prepareStatement(consulta);
            ResultSet rs = ps.executeQuery();
            if(rs.next()){
                p = new Productos(
                rs.getString(1),
                rs.getString(2),
                rs.getString(3),
                rs.getDouble(4),
                rs.getDouble(5),
                rs.getInt(6),
                rs.getString(7),
                rs.getString(8),
                rs.getString(9)
                );
            }
        } catch (SQLException ex) {
            System.getLogger(Conexion.class.getName()).log(System.Logger.Level.ERROR, (String) null, ex);
        } finally {
            Cerrar();
        }
        return p;
    }
    
    /**
     * Método para mostrar la informacion del usuario logado llamando al nombre y apellidos,
     * usuario y contraseña del usuario.
     * @param user Usuario logado para enviar a la consulta su resultado
     * @return listado de datos de usuario para imprimirlo en los campos
     */
    public static Usuarios MostrarFormularioUsuarios(String user){
        Usuarios u = null;
        String consulta = "select nombre_apellidos, usuario, pass from usuarios where usuario='"+user+"'";
        Conectar();
        try {
            PreparedStatement ps = conn.prepareStatement(consulta);
            ResultSet rs = ps.executeQuery();
            if (rs.next()){
                u = new Usuarios(rs.getString(1), rs.getString(2), rs.getString(3));
            }
        } catch (SQLException ex) {
            System.getLogger(Conexion.class.getName()).log(System.Logger.Level.ERROR, (String) null, ex);
        } finally {
            Cerrar();
        }
        return u;
    }
    
    /**
     * Método para actualizar la cuenta de un usuario siempre y cuando el usuario sea logado
     * @param cli Clase de Usuarios almacenados de nombres completos y contraseña
     * @return true si esta actualizado y false en caso contrario no se pudo actualizar los datos.
     */
    public static boolean ActualizarCuenta(Usuarios cli){
        String consulta = "update usuarios set nombre_apellidos = ?, pass = ? where usuario = ?";
        Conectar();
        try {
            PreparedStatement ps = conn.prepareStatement(consulta);
            ps.setString(1, cli.getNombresCompletos());
            ps.setString(2, cli.getPass());
            ps.setString(3, cli.getUsuario());
            ps.execute();
            return true;
        } catch (SQLException ex) {
            System.getLogger(Conexion.class.getName()).log(System.Logger.Level.ERROR, (String) null, ex);
        } finally {
            Cerrar();
        }
        return false;
    }
    
    /**
     * Método para obtener el último articulo registrado para mostrarlo en un label
     * @param lb JLabel para mostrar en la vista
     */
    public static void ObtenerUltimoArticuloRegistrado (JLabel lb) {
        lb.setText("");
        String consulta = "SELECT codProducto FROM producto order by codProducto desc limit 1";
        Conectar();
        try {
            Statement st;
            ResultSet rs;
            st = conn.createStatement();
            rs = st.executeQuery(consulta);
            while (rs.next()){
                lb.setText(rs.getString(1));
            }
        } catch (SQLException ex) {
            System.getLogger(Conexion.class.getName()).log(System.Logger.Level.ERROR, (String) null, ex);
        } finally {
            Cerrar();
        }
    }
    
    /**
     * Método para comprobar si el producto a registrar ya existe o no para evaluarlo
     * e insertar un nuevo artículo nuevo, evitando duplicidad en el registro.
     * Tener en cuenta que significativamente el producto puede duplicarse al colocar
     * al mismo nombre del producto, solo valida si es igual al codigo que quiere el usuario registrar.
     * @param producto Id del producto obtenido en la base de datos
     * @return true si existe producto, false si no existe el producto 
     */
    public static boolean CompruebaProducto(String producto) {
        String consulta = "SELECT codProducto FROM producto WHERE codProducto =?";
        Conectar();
        try {
            PreparedStatement ps;
            ResultSet rs;
            ps = conn.prepareStatement(consulta);
            ps.setString(1, producto);
            rs = ps.executeQuery();
            if (rs.next()) {
                return true;
            }
        } catch (SQLException ex) {
            System.getLogger(Conexion.class.getName()).log(System.Logger.Level.ERROR, (String) null, ex);
        } finally {
            Cerrar();
        }
        return false;
    }
    
    /**
     * Método para registrar el artículo a la base de datos de Productos usando el constructor
     * de la clase de Productos para registrar todos los campos.
     * @param cli Clase productos para insertar uno nuevo
     * @return true si el registro se realizo correctamente, false si no ha sido posible
     * el registro del producto.
     */
    public static  boolean RegistrarArticulo(Productos cli){
        String consulta = "INSERT INTO producto (codProducto, nombre, categoria, descripcion, precio_compra, precio_venta, stock, origen, destacado, oferta, fecha_alta) "
                    + "VALUES (?,?,?,?,?,?,?,?,?,?,?)";
        Conectar();
        try {
            PreparedStatement ps = conn.prepareStatement(consulta);
            ps.setString(1, cli.getCodigo());
            ps.setString(2, cli.getNombre());
            ps.setString(3, cli.getCategoria());
            ps.setString(4, cli.getDescripcion());
            ps.setDouble(5, cli.getPrecioCompra());
            ps.setDouble(6, cli.getPrecioVenta());
            ps.setInt(7, cli.getStock());
            ps.setString(8, cli.getOrigen());
            ps.setString(9, cli.getDestacado());
            ps.setString(10, cli.getOferta());
            ps.setDate(11, new Date(cli.getFechaAlta().getTime()));
            ps.execute();
            return true;
        } catch (SQLException ex) {
            System.getLogger(Conexion.class.getName()).log(System.Logger.Level.ERROR, (String) null, ex);
        } finally {
            Cerrar();
        }
        return false;
    }
    
    /**
     * Método para actualizar el artículo del producto seleccionado.
     * @param cli Clase producto seleccionado en tablas
     * @return true si se actualizó correctamente, false si no
     */
    public static boolean ActualizarArticulo(Productos cli){
        String consulta = "UPDATE producto set nombre=?, categoria=?, descripcion=?, precio_compra=?, precio_venta=?, stock=?, origen=?, destacado=?, oferta=? "
                    + "where codProducto=?";
        Conectar();
        try {
            PreparedStatement ps = conn.prepareStatement(consulta);
            ps.setString(1, cli.getNombre());
            ps.setString(2, cli.getCategoria());
            ps.setString(3, cli.getDescripcion());
            ps.setDouble(4, cli.getPrecioCompra());
            ps.setDouble(5, cli.getPrecioVenta());
            ps.setInt(6, cli.getStock());
            ps.setString(7, cli.getOrigen());
            ps.setString(8, cli.getDestacado());
            ps.setString(9, cli.getOferta());
            ps.setString(10, cli.getCodigo());
            ps.executeUpdate();
            return true;
        } catch (SQLException ex) {
            System.getLogger(Conexion.class.getName()).log(System.Logger.Level.ERROR, (String) null, ex);
        } finally {
            Cerrar();
        }
        return false;
    }
    
    /**
     * Método para eliminar el artículo seleccionado de la base de datos al obtener 
     * el codigo del articulo seleccionado.
     * @param codigo Id del producto seleccionado
     * @return true si se elimina correctamente producto, false si no.
     */
    public static boolean EliminarArticulo(String codigo){
        String consulta = "DELETE from producto where codProducto=?";
        Conectar();
        try {
            PreparedStatement ps = conn.prepareStatement(consulta);
            ps.setString(1, codigo);
            ps.executeUpdate();
            return true;
        } catch (SQLException ex) {
            System.getLogger(Conexion.class.getName()).log(System.Logger.Level.ERROR, (String) null, ex);
        } finally {
            Cerrar();
        }
        return false;
    }
    
    /**
     * Método para imprimir los articulos que son destacados en un JTable donde
     * tendra el codigo, nombre, categoria, precio de venta del producto o
     * artículo y destacado sea si destacado es 'SI'. Solo sirve para el Administrador
     * @param modelo JTable donde se cargará los datos
     */
    public static void ObtenerListadoArtDestacadoAdmin(DefaultTableModel modelo){
        modelo.setRowCount(0);
        Object datos[] = new Object[5];
        String consulta = "select codProducto, nombre, categoria, precio_venta, destacado "
                          + "from producto where destacado = 'SI'";
        Conectar();
        try {
            Statement st = conn.createStatement();
            ResultSet rs = st.executeQuery(consulta);
            while (rs.next()){
                datos[0] = rs.getString(1);
                datos[1] = rs.getString(2);
                datos[2] = rs.getString(3);
                datos[3] = rs.getDouble(4);
                datos[4] = rs.getString(5);
                modelo.addRow(datos);
            }
        } catch (SQLException ex) {
            System.getLogger(Conexion.class.getName()).log(System.Logger.Level.ERROR, (String) null, ex);
        } finally {
            Cerrar();
        }
    }
    
    /**
     * Método para imprimir los articulos que son destacados en un JTable donde
     * tendra el codigo, nombre, categoria, precio de venta del producto o
     * artículo y oferta sea si oferta es 'SI'. Solo sirve para el Administrador
     * @param modelo JTable donde se cargará los datos
     */
    public static void ObtenerListadoArtOfertaAdmin(DefaultTableModel modelo){
        modelo.setRowCount(0);
        Object datos[] = new Object[5];
        String consulta = "select codProducto, nombre, categoria, precio_venta, oferta "
                          + "from producto where oferta = 'SI'";
        Conectar();
        try {
            Statement st = conn.createStatement();
            ResultSet rs = st.executeQuery(consulta);
            while (rs.next()){
                datos[0] = rs.getString(1);
                datos[1] = rs.getString(2);
                datos[2] = rs.getString(3);
                datos[3] = rs.getDouble(4);
                datos[4] = rs.getString(5);
                modelo.addRow(datos);
            }
        } catch (SQLException ex) {
            System.getLogger(Conexion.class.getName()).log(System.Logger.Level.ERROR, (String) null, ex);
        } finally {
            Cerrar();
        }
    }
    
    /**
     * Método para obtener el listado de todos los usuarios de la base de datos 
     * y mostrarlo en la tabla del usuario
     * @param modelo JTable a mostrar el listado total de usuarios
     */
    public static void ObtenerListadoUsuario(DefaultTableModel modelo){
        modelo.setRowCount(0);
        Object datos[] = new Object[8];
        String consulta = "SELECT idUsuario, nombre_apellidos, tienda, usuario, pass, tipo, estado, fecha_alta FROM usuarios";
        Conectar();
        try {
            Statement st = conn.createStatement();
            ResultSet rs = st.executeQuery(consulta);
            while (rs.next()) {
                datos[0] = rs.getInt(1);
                datos[1] = rs.getString(2);
                datos[2] = rs.getString(3);
                datos[3] = rs.getString(4);
                datos[4] = rs.getString(5);
                datos[5] = rs.getString(6);
                datos[6] = rs.getString(7);
                datos[7] = rs.getDate(8);
                modelo.addRow(datos);
            }
        } catch (SQLException ex) {
            System.getLogger(Conexion.class.getName()).log(System.Logger.Level.ERROR, (String) null, ex);
        } finally {
            Cerrar();
        }
    }
    
    /**
     * Método para mostrar la informacion completa del usuario seleccionado 
     * llamando a todos los campos del usuario. Solo funciona en Administracion
     * @param user Usuario seleccionado para mostrarlo en el formulario
     * @return listado completo de datos de usuario para imprimirlo en los campos
     */
    public static Usuarios MostrarFormularioUsuariosAdmin(String user){
        Usuarios u = null;
        String consulta = "select nombre_apellidos, tienda, usuario, pass, tipo, estado "
                        + "from usuarios where usuario='"+user+"'";
        Conectar();
        try {
            PreparedStatement ps = conn.prepareStatement(consulta);
            ResultSet rs = ps.executeQuery();
            if (rs.next()){
                u = new Usuarios(
                        rs.getString(1), 
                        rs.getString(2), 
                        rs.getString(3),
                        rs.getString(4),
                        rs.getString(5),
                        rs.getString(6)
                );
            }
        } catch (SQLException ex) {
            System.getLogger(Conexion.class.getName()).log(System.Logger.Level.ERROR, (String) null, ex);
        } finally {
            Cerrar();
        }
        return u;
    }
    
    /**
     * Método para actualizar el usuario de la base de datos
     * donde obtiene el usuario seleccionado y actualiza los campos de tienda, tipo y estado 
     * llamando a la clase Usuarios
     * @param cli Clase Usuarios que almacena la tienda, tipo y estado
     * @param user parametro para obtener el usuario seleccionado y enviarlo a la condicional
     * de la consulta
     * @return true si actualiza exitosamente y false en caso contrario.
     */
    public static boolean ActualizarUsuario(Usuarios cli, String user){
        String consulta = "update usuarios set tienda=?, tipo=?, estado=? where usuario='"+user+"'";
        Conectar();
        try {
            PreparedStatement ps = conn.prepareStatement(consulta);
            ps.setString(1, cli.getTienda());
            ps.setString(2, cli.getTipo());
            ps.setString(3, cli.getEstado());
            ps.executeUpdate();
            return true;
        } catch (SQLException ex) {
            System.getLogger(Conexion.class.getName()).log(System.Logger.Level.ERROR, (String) null, ex);
        } finally {
            Cerrar();
        }
        return false;
    }
    
    /**
     * Método para obtener el total de categorias registradas en la base de datos
     * y mostrarlas en un JTable a visualizar las categorias
     * @param modelo JTable a visualizar el listado de categorias.
     */
    public static void ObtenerListadoCategorias(DefaultTableModel modelo) {
        modelo.setRowCount(0);
        Object datos[] = new Object[2];
        String consulta = "select categoria, descripcion from categorias";
        Conectar();
        try {
            Statement st;
            ResultSet rs;
            st = conn.createStatement();
            rs = st.executeQuery(consulta);
            while (rs.next()) {
                datos[0] = rs.getString(1);
                datos[1] = rs.getString(2);
                modelo.addRow(datos);
            }
        } catch (SQLException ex) {
            System.getLogger(Conexion.class.getName()).log(System.Logger.Level.ERROR, (String) null, ex);
        } finally {
            Cerrar();
        }
    }
    
    /**
     * Método para comprobar la categoria si existe o no, asi evitar registrar 
     * una categoria.
     * @param categoria ID Categoria existente en la base de datos
     * @return true si existe, false en caso contrario.
     */
    public static boolean ComprobarCategoria(String categoria){
        String consulta = "select categoria from categorias where categoria = ?";
        Conectar();
        try {
            PreparedStatement ps;
            ResultSet rs;
            ps = conn.prepareStatement(consulta);
            ps.setString(1, categoria);
            rs = ps.executeQuery();
            if (rs.next()) {
                return true;
            }
        } catch (SQLException ex) {
            System.getLogger(Conexion.class.getName()).log(System.Logger.Level.ERROR, (String) null, ex);
        } finally {
            Cerrar();
        }
        return false;
    }
    
    /**
     * Método para registrar una nueva categoria en la base de datos
     * @param cli Clase Categoria donde almacena los datos a registrar
     * @return true si registro correctamente, false caso contrario.
     */
    public static boolean RegistrarCategoria(Categorias cli){
        String consulta = "INSERT INTO categorias (categoria, descripcion) VALUES (?,?)";
        Conectar();
        try {
            PreparedStatement ps = conn.prepareStatement(consulta);
            ps.setString(1, cli.getCategoria());
            ps.setString(2, cli.getDescripcion());
            ps.execute();
            return true;
        } catch (SQLException ex) {
            System.getLogger(Conexion.class.getName()).log(System.Logger.Level.ERROR, (String) null, ex);
        } finally {
            Cerrar();
        }
        return false;
    }
    
    /**
     * Método para obtener el total de origenes registradas en la base de datos
     * y mostrarlas en un JTable a visualizar los origenes
     * @param modelo JTable a visualizar el listado de origenes.
     */
    public static void ObtenerListadoOrigenes(DefaultTableModel modelo) {
        modelo.setRowCount(0);
        Object datos[] = new Object[2];
        String consulta = "select origen, descripcion from origen";
        Conectar();
        try {
            Statement st;
            ResultSet rs;
            st = conn.createStatement();
            rs = st.executeQuery(consulta);
            while (rs.next()) {
                datos[0] = rs.getString(1);
                datos[1] = rs.getString(2);
                modelo.addRow(datos);
            }
        } catch (SQLException ex) {
            System.getLogger(Conexion.class.getName()).log(System.Logger.Level.ERROR, (String) null, ex);
        } finally {
            Cerrar();
        }
    }
    
    /**
     * Método para comprobar el origen si existe o no, asi evitar registrar 
     * un origen nuevo.
     * @param origen ID Origen existente en la base de datos
     * @return true si existe, false en caso contrario.
     */
    public static boolean ComprobarOrigen(String origen){
        String consulta = "select origen from origen where origen = ?";
        Conectar();
        try {
            PreparedStatement ps;
            ResultSet rs;
            ps = conn.prepareStatement(consulta);
            ps.setString(1, origen);
            rs = ps.executeQuery();
            if (rs.next()) {
                return true;
            }
        } catch (SQLException ex) {
            System.getLogger(Conexion.class.getName()).log(System.Logger.Level.ERROR, (String) null, ex);
        } finally {
            Cerrar();
        }
        return false;
    }
    
    /**
     * Método para registrar un nuevo origen en la base de datos
     * @param cli Clase Origenes donde almacena los datos a registrar
     * @return true si registro correctamente, false caso contrario.
     */
    public static boolean RegistrarOrigenes(Origenes cli){
        String consulta = "INSERT INTO origen (origen, descripcion) VALUES (?,?)";
        Conectar();
        try {
            PreparedStatement ps = conn.prepareStatement(consulta);
            ps.setString(1, cli.getOrigen());
            ps.setString(2, cli.getDescripcion());
            ps.execute();
            return true;
        } catch (SQLException ex) {
            System.getLogger(Conexion.class.getName()).log(System.Logger.Level.ERROR, (String) null, ex);
        } finally {
            Cerrar();
        }
        return false;
    }
    
    /**
     * Método para obtener el total de tiendas registradas en la base de datos
     * y mostrarlas en un JTable a visualizar las tiendas disponibles
     * @param modelo JTable a visualizar el listado de tiendas.
     */
    public static void ObtenerListadoTiendas(DefaultTableModel modelo) {
        modelo.setRowCount(0);
        Object datos[] = new Object[3];
        String consulta = "select denominacion, direccion, responsable from tiendas";
        Conectar();
        try {
            Statement st;
            ResultSet rs;
            st = conn.createStatement();
            rs = st.executeQuery(consulta);
            while (rs.next()) {
                datos[0] = rs.getString(1);
                datos[1] = rs.getString(2);
                datos[2] = rs.getString(3);
                modelo.addRow(datos);
            }
        } catch (SQLException ex) {
            System.getLogger(Conexion.class.getName()).log(System.Logger.Level.ERROR, (String) null, ex);
        } finally {
            Cerrar();
        }
    }
    
    /**
     * Método para cargar los responsables de la tienda y cargarlos en el combo box.
     * @param combo JComboBox del responsable de tienda para cargar sus datos
     */
    public static void CargarResponsablesTienda(JComboBox combo){
        String consulta = "select nombre_apellidos from responsables_tienda";
        Conectar();
        try {
            Statement st = conn.createStatement();
            ResultSet rs = st.executeQuery(consulta);
            while (rs.next()) {
                combo.addItem(rs.getString(1));
            }
        } catch (SQLException ex) {
            System.getLogger(Conexion.class.getName()).log(System.Logger.Level.ERROR, (String) null, ex);
        } finally {
            Cerrar();
        }
    }
    
    /**
     * Método para comprobar las tiendas si existe o no, asi evitar registrar 
     * una tienda nueva en la base de datos.
     * @param denominacion ID Denominacion existente en la base de datos
     * @return true si existe, false en caso contrario.
     */
    public static boolean ComprobarTiendas(String denominacion){
        String consulta = "select denominacion from tiendas where denominacion = ?";
        Conectar();
        try {
            PreparedStatement ps;
            ResultSet rs;
            ps = conn.prepareStatement(consulta);
            ps.setString(1, denominacion);
            rs = ps.executeQuery();
            if (rs.next()) {
                return true;
            }
        } catch (SQLException ex) {
            System.getLogger(Conexion.class.getName()).log(System.Logger.Level.ERROR, (String) null, ex);
        } finally {
            Cerrar();
        }
        return false;
    }
    
    /**
     * Método para registrar una nueva tienda en la base de datos
     * @param cli Clase Tiendas donde almacena los datos a registrar
     * @return true si registró correctamente, false caso contrario.
     */
    public static boolean RegistrarTiendas(Tiendas cli){
        String consulta = "INSERT INTO tiendas (denominacion, direccion, responsable) VALUES (?,?,?)";
        Conectar();
        try {
            PreparedStatement ps = conn.prepareStatement(consulta);
            ps.setString(1, cli.getDenominacion());
            ps.setString(2, cli.getDireccion());
            ps.setString(3, cli.getResponsable());
            ps.execute();
            return true;
        } catch (SQLException ex) {
            System.getLogger(Conexion.class.getName()).log(System.Logger.Level.ERROR, (String) null, ex);
        } finally {
            Cerrar();
        }
        return false;
    }
    
}// End Class
