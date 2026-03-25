/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package modelos;

import java.util.Date;

/**
 * Clase donde almacena los Accesos de la base de datos
 * @author grupo
 */
public class Accesos {
    private String usuario;
    private Date fechaAcceso;
    private String ip;

    public Accesos() {
    }

    public Accesos(String usuario, Date fechaAcceso, String ip) {
        this.usuario = usuario;
        this.fechaAcceso = fechaAcceso;
        this.ip = ip;
    }
    
    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public Date getFechaAcceso() {
        return fechaAcceso;
    }

    public void setFechaAcceso(Date fechaAcceso) {
        this.fechaAcceso = fechaAcceso;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

}// End Class
