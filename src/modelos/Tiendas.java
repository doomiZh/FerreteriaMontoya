/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package modelos;

/**
 * Clase que almacena las tiendas
 * @author grupo
 */
public class Tiendas {
    private String denominacion, direccion, responsable;

    public Tiendas() {
    }

    public Tiendas(String denominacion, String direccion, String responsable) {
        this.denominacion = denominacion;
        this.direccion = direccion;
        this.responsable = responsable;
    }

    public String getDenominacion() {
        return denominacion;
    }

    public void setDenominacion(String denominacion) {
        this.denominacion = denominacion;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public String getResponsable() {
        return responsable;
    }

    public void setResponsable(String responsable) {
        this.responsable = responsable;
    }
    
}// End Class
