/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package modelos;

import java.util.Date;

/**
 * Clase que almacena los Usuarios de la base de datos que almacena datos principales del Usuario como
 * nombre, apellidos, ademas de su tienda a que será registrado, user, pass, el tipo de usuario que será
 * y el estado del usuario.
 * @author grupo
 */
public class Usuarios {
    private String nombresCompletos, tienda, usuario, pass, tipo, estado;
    private Date fechaAlta;

    public Usuarios() {
    }   

    public Usuarios(String nombresCompletos, String tienda, String usuario, String pass, String tipo, String estado, Date fechaAlta) {
        this.nombresCompletos = nombresCompletos;
        this.tienda = tienda;
        this.usuario = usuario;
        this.pass = pass;
        this.tipo = tipo;
        this.estado = estado;
        this.fechaAlta = fechaAlta;
    }

    public Usuarios(String nombresCompletos, String usuario, String pass) {
        this.nombresCompletos = nombresCompletos;
        this.usuario = usuario;
        this.pass = pass;
    }

    public Usuarios(String nombresCompletos, String tienda, String usuario, String pass, String tipo, String estado) {
        this.nombresCompletos = nombresCompletos;
        this.tienda = tienda;
        this.usuario = usuario;
        this.pass = pass;
        this.tipo = tipo;
        this.estado = estado;
    }

    public String getNombresCompletos() {
        return nombresCompletos;
    }

    public void setNombresCompletos(String nombresCompletos) {
        this.nombresCompletos = nombresCompletos;
    }

    public String getTienda() {
        return tienda;
    }

    public void setTienda(String tienda) {
        this.tienda = tienda;
    }

    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public String getPass() {
        return pass;
    }

    public void setPass(String pass) {
        this.pass = pass;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public Date getFechaAlta() {
        return fechaAlta;
    }

    public void setFechaAlta(Date fechaAlta) {
        this.fechaAlta = fechaAlta;
    }
    
}// End Class
