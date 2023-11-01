package model;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.beans.InvalidationListener;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.ObservableList;
import javafx.scene.control.Alert;



public class Vehiculo {
    private StringProperty nom_Apellidos;
    private StringProperty telefono;
    private StringProperty marca;
    private StringProperty modelo;
    private StringProperty matricula;

    public Vehiculo(String nom_Apellidos, String telefono, String marca, String modelo, String matricula) {
        this.nom_Apellidos = new SimpleStringProperty(nom_Apellidos);
        this.telefono = new SimpleStringProperty(telefono);
        this.marca = new SimpleStringProperty(marca);
        this.modelo = new SimpleStringProperty(modelo);
        this.matricula = new SimpleStringProperty(matricula);
        
    }

    public String getNom_Apellidos() {
        return nom_Apellidos.get();
    }

    public void setNom_Apellidos(String nom_Apellidos) {
        this.nom_Apellidos = new SimpleStringProperty(nom_Apellidos);
    }

    public String getTelefono() {
        return telefono.get();
    }

    public void setTelefono(String telefono) {
        this.telefono = new SimpleStringProperty(telefono);
    }

    public String getMarca() {
        return marca.get();
    }

    public void setMarca(String marca) {
        this.marca = new SimpleStringProperty(marca);
    }

    public String getModelo() {
        return modelo.get();
    }

    public void setModelo(String modelo) {
        this.modelo = new SimpleStringProperty(modelo);
    }

    public String getMatricula() {
        return matricula.get();
    }

    public void setMatricula(String matricula) {
        this.matricula = new SimpleStringProperty(matricula);
    }
    
  public static void llenarInformacionVehiculo(Connection conectar, ObservableList<Vehiculo> listaVehiculo){
        
        try {
            
            Statement instruccion = conectar.createStatement();
            ResultSet resultado = instruccion.executeQuery(
                    " select nomapellidos, telefono, marca, modelo, matricula from vehiculos where diasalida = '00/00/0000'");
            
            while(resultado.next()){
                
                listaVehiculo.add(
                
                        new Vehiculo(
                                
                                resultado.getString("nomapellidos"),
                                resultado.getString("telefono"),
                                resultado.getString("marca"),
                                resultado.getString("modelo"),
                                resultado.getString("matricula")
                        
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
  
  public static void llenarInformacionVehiculoTerminado(Connection conectar, ObservableList<Vehiculo> listaVehiculo){
        
        try {
            
            Statement instruccion = conectar.createStatement();
            ResultSet resultado = instruccion.executeQuery(
                    " select nomapellidos, telefono, marca, modelo, matricula from vehiculos where diasalida <> '00/00/0000'");
            
            while(resultado.next()){
                
                listaVehiculo.add(
                
                        new Vehiculo(
                                
                                resultado.getString("nomapellidos"),
                                resultado.getString("telefono"),
                                resultado.getString("marca"),
                                resultado.getString("modelo"),
                                resultado.getString("matricula")
                        
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
