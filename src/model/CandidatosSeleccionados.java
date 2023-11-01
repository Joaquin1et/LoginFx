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

public class CandidatosSeleccionados {
    
    private StringProperty nom_Apellidos;
    private StringProperty fecha_Naci;
    private StringProperty email;
    private StringProperty telefono;
    
    public CandidatosSeleccionados(String nom_Apellidos, String fecha_Naci, String email, String telefono) {
        
        this.nom_Apellidos = new SimpleStringProperty(nom_Apellidos);
        this.fecha_Naci = new SimpleStringProperty(fecha_Naci);
        this.email = new SimpleStringProperty(email);
        this.telefono = new SimpleStringProperty(telefono);
        
    }

    public String getNom_Apellidos() {
        return nom_Apellidos.get();
    }

    public void setNom_Apellidos(String nom_Apellidos) {
        this.nom_Apellidos = new SimpleStringProperty(nom_Apellidos);
    }

    public String getFecha_Naci() {
        return fecha_Naci.get();
    }

    public void setFecha_Naci(String fecha_Naci) {
        this.fecha_Naci = new SimpleStringProperty(fecha_Naci);
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
    
    
    public static void LlenarInformacionCandidatosSelec(Connection conectar, ObservableList<CandidatosSeleccionados> listaCandidatosSeleccionados) {
        
        try {
            
            Statement instruccion = conectar.createStatement();
            ResultSet resultado = instruccion.executeQuery(
                    " select nomapellidos, fechanacimiento, telefono, email from candidatos where valoracion = 'Apto'"
            );
            
            while (resultado.next()) {
                
                listaCandidatosSeleccionados.add(
                        new CandidatosSeleccionados(
                                resultado.getString("nomapellidos"),
                                resultado.getString("fechanacimiento"),
                                resultado.getString("telefono"),
                                resultado.getString("email")
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
    
}
