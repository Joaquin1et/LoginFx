
package model;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.ObservableList;

/**
 *author Joaquín
 */
public class Usuario {
    
    private StringProperty nombre;
    private StringProperty apellido;
    private StringProperty email;
    private StringProperty telefono;
    private StringProperty permiso;

    public Usuario(String nombre, String apellido, String email, String telefono, String permiso) {
        
        this.nombre = new SimpleStringProperty(nombre);
        this.apellido = new SimpleStringProperty(apellido);
        this.email = new SimpleStringProperty(email);
        this.telefono = new SimpleStringProperty(telefono);
        this.permiso = new SimpleStringProperty(permiso);
    }

    public String getNombre() {
        return nombre.get();
    }

    public void setNombre(String nombre) {
        this.nombre = new SimpleStringProperty(nombre);
    }

    public String getApellido() {
        return apellido.get();
    }

    public void setApellido(String apellido) {
        this.apellido = new SimpleStringProperty(apellido);
    }

    public String getEmail() {
        return email.get();
    }

    public void setEmail(String email) {
        this.email = new SimpleStringProperty(email);
    }

    public String getTelefono() {
        return telefono.get();
    }

    public void setTelefono(String telefono) {
        this.telefono = new SimpleStringProperty(telefono);
    }

    public String getPermiso() {
        return permiso.get();
    }

    public void setPermiso(String permiso) {
        this.permiso = new SimpleStringProperty(permiso);
    }

    public static void llenarInformacionUsuarios(Connection conectar, ObservableList<Usuario> lista){
        
        try {
            Statement instruccion = conectar.createStatement();
            ResultSet resultado = instruccion.executeQuery("select nombre, apellido, email, telefono, tipo_nivel from usuarios");
            
            while(resultado.next()){
                
                lista.add(
                        new Usuario(
                                
                                resultado.getString("nombre"),
                                resultado.getString("apellido"),
                                resultado.getString("email"),
                                resultado.getString("telefono"),
                                resultado.getString("tipo_nivel")
                               
                        )
                );
                
            }
            
        } catch (SQLException ex) {
            Logger.getLogger(Usuario.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }

    
}
