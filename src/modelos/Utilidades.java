/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package modelos;

import java.awt.Component;
import javax.swing.JComboBox;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

/**
 *
 * @author Marco Antonio
 */
public class Utilidades {
    public static boolean ComprobarTextoVacio(JPanel panelDatos) {
        for (Component c : panelDatos.getComponents()) {
            if (c instanceof JTextField datos) {
                if (datos.getText().isBlank()) {
                    JOptionPane.showMessageDialog(null, "El dato " + datos.getName() + "es obligatorio");
                    return false;
                }
            }
        }
        return true;
    }
    
    public static boolean ComboSeleccionado(JComboBox campo) {
        if (campo.getSelectedIndex() == 0) {
            JOptionPane.showMessageDialog(null, "El dato " + campo.getName() + "es obligatorio");
                    return false;
        } return true;
    }
    
} //Fin de la clase
