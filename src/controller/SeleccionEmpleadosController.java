package controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
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
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javax.swing.JFileChooser;
import model.Conexion;

/**
 * FXML Controller class
 *
 * @author joaqu
 */
public class SeleccionEmpleadosController implements Initializable {

    @FXML
    private Button btn_gestionEmpleados;
    @FXML
    private Button btn_seleccion;
    @FXML
    private AnchorPane contenAreaSeleccion;
    @FXML
    private Button btn_nuevoCandidato;
    @FXML
    private TextField txt_nomApellidos;
    @FXML
    private TextField txt_fechaNaci;
    @FXML
    private TextField txt_telefono;
    @FXML
    private TextField txt_email;
    @FXML
    private TextArea txt_forAcademica;
    @FXML
    private TextArea txt_experiencia;
    @FXML
    private Label lbl_imagenCandidato;
    @FXML
    private ImageView lv_imagenCandidato;
    @FXML
    private TextArea txt_observaciones;
    @FXML
    private Button btn_registrarCandidato;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
        
                
        
        
    }    


    @FXML
    private void ir_nuevoCandidato(ActionEvent event) {
        
        
        
    }

    @FXML
    private void ir_gestionCandidatos(ActionEvent event) {
        try{
            Parent fxml = FXMLLoader.load(getClass().getResource("/view/GestionCandidatos.fxml"));
            contenAreaSeleccion.getChildren().removeAll();
            contenAreaSeleccion.getChildren().setAll(fxml);
        } catch (IOException ex) {
             System.out.println("Valla mierda");
            Logger.getLogger(AdministradorController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @FXML
    private void ir_ProcesoSeleccion(ActionEvent event) {
        
        try{
            Parent fxml = FXMLLoader.load(getClass().getResource("/view/ProcesoSeleccion.fxml"));
            contenAreaSeleccion.getChildren().removeAll();
            contenAreaSeleccion.getChildren().setAll(fxml);
        } catch (IOException ex) {
             System.out.println("Valla mierda");
            Logger.getLogger(AdministradorController.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }
    
    

    @FXML
    private void RegistroCandidato(ActionEvent event) {
        
        //---Recogemos las variables----- 
        int validacion = 0;
        String nomApellidos, fechaNaci, telefono, email, forAcademia, experiencia, observaciones;
        
        nomApellidos = txt_nomApellidos.getText().trim();
        fechaNaci = txt_fechaNaci.getText().trim();
        telefono = txt_telefono.getText().trim();
        email = txt_email.getText().trim();
        forAcademia = txt_forAcademica.getText().trim();
        experiencia = txt_experiencia.getText().trim();
        observaciones = txt_observaciones.getText().trim();
        
        if (nomApellidos.equals("")) {
            txt_nomApellidos.setStyle("-fx-background-color: #F64104 ;");
            validacion++;
        }
        if(fechaNaci.equals("")){
            txt_fechaNaci.setStyle("-fx-background-color: #F64104 ;");
        }
        if(telefono.equals("")){
            txt_telefono.setStyle("-fx-background-color: #F64104 ;");
        }
        if(email.equals("")){
            txt_email.setStyle("-fx-background-color: #F64104 ;");
        }
        if(forAcademia.equals("")){
            txt_forAcademica.setStyle("-fx-background-color: #F64104 ;");
        }
        if(experiencia.equals("")){
            txt_experiencia.setStyle("-fx-background-color: #F64104 ;");
        }
        if(observaciones.equals("")){
            txt_observaciones.setStyle("-fx-background-color: #F64104 ;");
        }
        
        try {
            
            Connection cn = Conexion.conectar();
            PreparedStatement pst = cn.prepareStatement(
                    "select nomapellidos from candidatos where nomapellidos = '" + nomApellidos + "'");
            ResultSet rs = pst.executeQuery();
            
            if(rs.next()){
                
                txt_nomApellidos.setStyle("-fx-background-color: #F0591E;");
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setHeaderText(null);
                    alert.setTitle("Informacion");
                    alert.setContentText("Empleado ya registrado");
                    alert.showAndWait();
                cn.close();
                
            }else{
                cn.close();
                
                if(validacion == 0){
                    
                    try {
                        
                        // Cambiando la foto del producto.
                
                 JFileChooser escoger = new JFileChooser();
                escoger.setFileSelectionMode(JFileChooser.FILES_ONLY);
                int estado = escoger.showOpenDialog(null);
                
                File archivo = escoger.getSelectedFile();
                            
                long longitud = archivo.length();
                            
                
                    FileInputStream flujo;
                    
                        try {
                            flujo = new FileInputStream(archivo);
                            
                            Connection cn2 = Conexion.conectar();
                    PreparedStatement pst2 = cn2.prepareStatement(
                            " insert into candidatos ( nomapellidos, fechanacimiento, telefono, email,"
                                    + " formacion, experiencia, observaciones, imagen)values(?,?,?,?,?,?,?,?)");
                    
                        pst2.setString(1, nomApellidos);
                        pst2.setString(2, fechaNaci);
                        pst2.setString(3, telefono);
                        pst2.setString(4, email);
                        pst2.setString(5, forAcademia);
                        pst2.setString(6, experiencia);
                        pst2.setString(7, observaciones);
                        pst2.setBlob(8, flujo, longitud);
                        
                        pst2.executeUpdate();
                        cn2.close();
                        
                        
                        txt_nomApellidos.setStyle("-fx-background-color: #F0591E;");
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setHeaderText(null);
                    alert.setTitle("Informacion");
                    alert.setContentText("Registrado con exito");
                    alert.showAndWait();
                        
                        Limpiar();   
                            
                        }catch (SQLException e) {
                    Logger.getLogger(SeleccionEmpleadosController.class.getName()).log(Level.SEVERE, null, e);
                }
                        
                    } catch (FileNotFoundException ex) {
                        Logger.getLogger(SeleccionEmpleadosController.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    
                    
                }
            }
            
        } catch (SQLException ex) {
            
            System.err.println("Error al validar el nombre de empleado" + ex);
            
            Alert alert = new Alert(Alert.AlertType.ERROR);
               alert.setHeaderText(null);
               alert.setTitle("Informacion");
               alert.setContentText("¡¡Error al comparar empleado!! Contacte con el administrador. ");
               alert.showAndWait();
            
        }
        
    }
    
    public void Limpiar(){
        
        txt_nomApellidos.setText("");
        txt_fechaNaci.setText("");
        txt_email.setText("");
        txt_telefono.setText("");
        txt_forAcademica.setText("");
        txt_experiencia.setText("");
        txt_observaciones.setText("");
    
  }
    
}
