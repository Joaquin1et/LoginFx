package controller;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import model.Conexion;

public class AdmisionVehController implements Initializable {

    @FXML
    private Button btn_ordenServicio;
    @FXML
    private Button btn_enReparacion;
    @FXML
    private Button btn_ordenFinalizada;
    @FXML
    private AnchorPane contenAreaAdmisionVeh;
    @FXML
    private Button btn_vehiculo;
    @FXML
    private TextField txt_precio;
    @FXML
    private TextField txt_precioIva;
    @FXML
    private TextField txt_nomApellidos;
    @FXML
    private TextField txt_telefono;
    @FXML
    private TextField txt_email;
    @FXML
    private TextField txt_diaIngreso;
    @FXML
    private TextArea txt_trabajorRealizar;
    @FXML
    private TextField txt_marca;
    @FXML
    private TextField txt_modelo;
    @FXML
    private TextField txt_color;
    @FXML
    private TextField txt_kilometros;
    @FXML
    private TextField txt_matricula;
    @FXML
    private TextField txt_numMotor;
    @FXML
    private TextArea txt_observaciones;
    @FXML
    private Label lbl_numFolio;
    @FXML
    private Label lbl_horaSalidaInicial;
    

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
    }    

    @FXML
    private void IrOrdenServicio(ActionEvent event) {
        
        try{
            Parent fxml = FXMLLoader.load(getClass().getResource("/view/AdmisionVeh.fxml"));
            contenAreaAdmisionVeh.getChildren().removeAll();
            contenAreaAdmisionVeh.getChildren().setAll(fxml);
        } catch (IOException ex) {
             System.out.println("Valla mierda");
            Logger.getLogger(AdmisionVehController.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }

    @FXML
    private void IrEnReparacion(ActionEvent event) {
        
        try {
            Parent fxml = FXMLLoader.load(getClass().getResource("/view/EnReparacion.fxml"));
            contenAreaAdmisionVeh.getChildren().removeAll();
            contenAreaAdmisionVeh.getChildren().setAll(fxml);
            
        } catch (IOException ex) {
            
            System.out.println("Valla mierda");
            Logger.getLogger(AdmisionVehController.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }

    @FXML
    private void IrOrdenFinalizada(ActionEvent event) {
        
        try{
            Parent fxml = FXMLLoader.load(getClass().getResource("/view/OrdenFinalizada.fxml"));
            contenAreaAdmisionVeh.getChildren().removeAll();
            contenAreaAdmisionVeh.getChildren().setAll(fxml);
        } catch (IOException ex) {
             System.out.println("Valla mierda");
            Logger.getLogger(AdmisionVehController.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }

    @FXML
    private void RegistrarVehiculo(ActionEvent event) {
        
        //---Recoger las variables
        int validacion = 0;
        String nomApellidos, telefono, email, diaIngreso, trabajoRealizar, precioReparacion, marca, modelo, 
                color, kilometraje, matricula, numMotor, observaciones, diaSalida = "00/00/0000" ;
        
        nomApellidos = txt_nomApellidos.getText().trim();
        telefono = txt_telefono.getText().trim();
        email = txt_email.getText().trim();
        diaIngreso = txt_diaIngreso.getText().trim();
        diaSalida = diaSalida;
        trabajoRealizar = txt_trabajorRealizar.getText().trim();
        precioReparacion = txt_precio.getText().trim();
        marca = txt_marca.getText().trim();
        modelo = txt_modelo.getText().trim();
        color = txt_color.getText().trim();
        kilometraje = txt_kilometros.getText().trim();
        matricula = txt_matricula.getText().trim();
        numMotor = txt_numMotor.getText().trim();
        observaciones = txt_observaciones.getText().trim();
        
        
        if(nomApellidos.equals("")&& telefono.equals("")&& email.equals("")&& diaIngreso.equals("")&& trabajoRealizar.equals("")&& precioReparacion.equals("")
                && marca.equals("")&& modelo.equals("")&& color.equals("")&& kilometraje.equals("")&& matricula.equals("")
                && numMotor.equals("")&& observaciones.equals("")){
            
            txt_nomApellidos.setStyle("-fx-background-color: #F64104 ;");
            txt_telefono.setStyle("-fx-background-color: #F64104 ;");
            txt_email.setStyle("-fx-background-color: #F64104 ;");
            txt_diaIngreso.setStyle("-fx-background-color: #F64104 ;");
            txt_trabajorRealizar.setStyle("-fx-background-color: #F64104 ;");
            txt_precio.setStyle("-fx-background-color: #F64104 ;");
            txt_marca.setStyle("-fx-background-color: #F64104 ;");
            txt_modelo.setStyle("-fx-background-color: #F64104 ;");
            txt_color.setStyle("-fx-background-color: #F64104 ;");
            txt_kilometros.setStyle("-fx-background-color: #F64104 ;");
            txt_matricula.setStyle("-fx-background-color: #F64104 ;");
            txt_numMotor.setStyle("-fx-background-color: #F64104 ;");
            txt_observaciones.setStyle("-fx-background-color: #F64104 ;");
            
                        Alert alert = new Alert(Alert.AlertType.ERROR);
                        alert.setHeaderText(null);
                        alert.setTitle("Informacion");
                        alert.setContentText(" Debe rellenar todos los campos. ");
                        alert.showAndWait();
            
        }else{
        
        try {
            
            Connection cn = Conexion.conectar();
            PreparedStatement pst = cn.prepareStatement("insert into vehiculos ( nomapellidos, telefono, email, diaingreso, diasalida, trabajorealizar, marca,"
                                    + "modelo, color, kilometros, matricula, numeromotor, observaciones, precioreparacion)values(?,?,?,?,?,?,?,?,?,?,?,?,?,?)");
            
            pst.setString(1, nomApellidos);
            pst.setString(2, telefono);
            pst.setString(3, email);
            pst.setString(4, diaIngreso);
            pst.setString(5, diaSalida);
            pst.setString(6, trabajoRealizar);
            pst.setString(7, marca);
            pst.setString(8, modelo);
            pst.setString(9, color);
            pst.setString(10, kilometraje);
            pst.setString(11, matricula);
            pst.setString(12, numMotor);
            pst.setString(13, observaciones);
            pst.setString(14, precioReparacion);
            
            pst.executeUpdate();
            cn.close();
            
            Limpiar();
            
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
                        alert.setHeaderText(null);
                        alert.setTitle("Informacion");
                        alert.setContentText("Registrado correctamente.");
                        alert.showAndWait();
                        
                        this.dispose();
            
        } catch (SQLException ex) {
            
            System.err.println("Error al registrar empleado. " + ex);
                        
                        Alert alert = new Alert(Alert.AlertType.ERROR);
                        alert.setHeaderText(null);
                        alert.setTitle("Informacion");
                        alert.setContentText("¡¡ERROR al registrar!! Contacte con el administrador. ");
                        alert.showAndWait();
        }
                
      }          
    }
    
    public void Limpiar(){
        
        txt_nomApellidos.setText("");
        txt_telefono.setText("");
        txt_email.setText("");
        txt_diaIngreso.setText("");
        txt_trabajorRealizar.setText("");
        txt_marca.setText("");
        txt_modelo.setText("");
        txt_color.setText("");
        txt_kilometros.setText("");
        txt_matricula.setText("");
        txt_numMotor.setText("");
        txt_precio.setText("");
        txt_observaciones.setText("");
    }
    
    private void dispose() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
