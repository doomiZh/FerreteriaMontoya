/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package modelos;

import java.awt.Color;
import java.awt.Component;
import javax.swing.JComboBox;
import javax.swing.JOptionPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

/**
 *
 * @author Marco Antonio
 */
public class Utilidades {

    /**
     * Método util para comprobar los campos de texto y combos si estan vacios o seleccionados
     * enviando un mensaje de error caso false, en caso true continuara el codigo. Para obtener los campos de manera escalonada
     * se pedira de parametros un varargs (Argumentos Variables) para hacer que al momento que pediremos los campos a validar
     * se pueda colocar los campos de manera ordenada, asi el panel no valida de manera deshordenada
     * @param panelDatos varargs (Argumento Variable) de tipo Component para obtener los componentes que se 
     * desee validar.
     * @return true si los campos estan correctos y false en caso uno de ellos no se valide su funcion,
     * es decir, que el campo esta vacio o que el campo no esta seleccionado
     */
    public static boolean ComprobarCampos(Component... panelDatos) {
        for (Component c : panelDatos) {
            if (c instanceof JTextField texto) {
                if (texto.getText().isBlank()) {
                    JOptionPane.showMessageDialog(null, "El campo " + texto.getName() + " es obligatorio");
                    texto.setBackground(Color.red);
                    return false;
                }
            }
            if (c instanceof JTextArea texto){
                if (texto.getText().isBlank()) {
                    JOptionPane.showMessageDialog(null, "El campo " + texto.getName() + " es obligatorio");
                    texto.setBackground(Color.red);
                    return false;
                }
            }
            if (c instanceof JComboBox campo) {
                if (campo.getSelectedIndex() == 0) {
                    JOptionPane.showMessageDialog(null, "El campo " + campo.getName() + " debe estar seleccionado");
                    return false;
                }
            }
        }
        return true;
    }

    /**
     * Método util para comprobar si el campo es un número entero.
     * Esta funcion intentará cambiar el valor tecleado si es un entero o no.
     * @param campo JTextField del campo donde validará si es un entero o no.
     * @return true si es correcto y false en caso contrario.
     */
    public static boolean ComprobarNumeroEntero (JTextField campo){
        String numeroTecleado = campo.getText();
        int minumero;    
        try {
            minumero = Integer.parseInt(numeroTecleado.trim());
            return true;
        } catch (NumberFormatException ex) {
            return false;
        }
    }
    
    /**
     * Método util para comprobar si el campo es un número decimal.
     * Esta funcion intentará cambiar el valor tecleado si es un decimal o no.
     * @param campo JTextField del campo donde validará si es un decimal o no.
     * @return true si es correcto y false en caso contrario.
     */
    public static boolean ComprobarNumeroDecimal (JTextField campo){
        String numeroTecleado = campo.getText();
        double minumero;    
        try {
            minumero = Double.parseDouble(numeroTecleado.trim());
            return true;
        } catch (NumberFormatException ex) {
            return false;
        }
    }
    
    /**
     * Método para validar el campo del codigo del producto, asi verificar que
     * el campo Codigo de producto se inserte iniciando el codigo P y lo despues sean minimo
     * tres caracteres 0+0+0, maximo ilimitado
     * @param campo JTextField a obtener el Id del Producto.
     * @return true si es correcto, falso en caso sea el codigo invalido
     */
    public static boolean ValidarIdProducto(JTextField campo){
        String patron = "^P\\d{3,}$";
        return !campo.getText().matches(patron);
    }
    
    /**
     * Método util para mandar una alerta sobre que el numero no es un entero, 
     * y el campo lo colorea a rojo
     * @param campo JTextField donde el numero no es un entero
     */
    public static void AlertaNumeroEntero(JTextField campo){
        JOptionPane.showMessageDialog(null, "El campo "+campo.getName()+" solo acepta números enteros.", "Número", JOptionPane.INFORMATION_MESSAGE);
        campo.setBackground(Color.red);
        campo.setText("");
    }
    
    /**
     * Método util para mandar una alerta sobre que el numero no es un entero, 
     * y el campo lo colorea a rojo
     * @param campo JTextField donde el numero no es un decimal
     */
    public static void AlertaNumeroDecimal(JTextField campo){
        JOptionPane.showMessageDialog(null, "El campo "+campo.getName()+" solo acepta números decimales.", "Número", JOptionPane.INFORMATION_MESSAGE);
        campo.setBackground(Color.red);
        campo.setText("");
    }
    
    /**
     * Método útil para mandar una alerta indicando el formato incorrecto del Id Producto.
     * Se le indica el formato ejemplar para que el usuario tenga como guia para el proximo registro
     * @param campo JTextField donde el id producto esta mal formateado
     */
    public static void AlertaIdProducto(JTextField campo){
        JOptionPane.showMessageDialog(null, "El CODIGO DEL PRODUCTO no tiene el formato correcto.", "Codigo Producto", JOptionPane.WARNING_MESSAGE);
        JOptionPane.showMessageDialog(null, "El formato debe iniciar con la P y luego los numeros.\nEjemplo: P001, P010, P099, P100, P1000, etc.", "Consejo", JOptionPane.INFORMATION_MESSAGE);
        campo.setBackground(Color.red);
        campo.setText("");
    }
    
} //Fin de la clase
