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
import javafx.scene.control.Alert;

/**
 * @author Joaquín
 */
public class Personal {
    
    private StringProperty nom_Apellidos;
    private StringProperty email;
    private StringProperty telefono;
    private StringProperty departamento;
    private StringProperty categoria;

    public Personal(String nom_Apellidos, String email, String telefono, String departamento, String categoria) {
        this.nom_Apellidos = new SimpleStringProperty(nom_Apellidos);
        this.email = new SimpleStringProperty(email);
        this.telefono = new SimpleStringProperty(telefono);
        this.departamento = new SimpleStringProperty(departamento);
        this.categoria = new SimpleStringProperty(categoria);
    }

    public String getNom_Apellidos() {
        return nom_Apellidos.get();
    }

    public void setNom_Apellidos(String nom_Apellidos) {
        this.nom_Apellidos = new SimpleStringProperty(nom_Apellidos);
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

    public String getDepartamento() {
        return departamento.get();
    }

    public void setDepartamento(String departamento) {
        this.departamento = new SimpleStringProperty(departamento);
    }

    public String getCategoria() {
        return categoria.get();
    }

    public void setCategoria(String categoria) {
        this.categoria = new SimpleStringProperty(categoria);
    }
    
    public static void llenarInformacionPersonal(Connection conectar, ObservableList<Personal> listaPersonal){
        
        try {
            
            Statement instruccion = conectar.createStatement();
            ResultSet resultado = instruccion.executeQuery(
                    " select nom_apellidos, email, telefono, departamento, categoria from personal");
            
            while(resultado.next()){
                
                listaPersonal.add(
                
                        new Personal(
                                
                                resultado.getString("nom_apellidos"),
                                resultado.getString("email"),
                                resultado.getString("telefono"),
                                resultado.getString("departamento"),
                                resultado.getString("categoria")
                        
                        )
                
                );
            }
            
        } catch (SQLException ex) {
            Logger.getLogger(Personal.class.getName()).log(Level.SEVERE, null, ex);
            
            Alert alert = new Alert(Alert.AlertType.ERROR);
                        alert.setHeaderText(null);
                        alert.setTitle("Error");
                        alert.setContentText("No se ha podido cargar de la base de datos..");
                        alert.showAndWait();
        }
        
    }

    /*public String getNombre() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }*/
    
    
    
    
}
