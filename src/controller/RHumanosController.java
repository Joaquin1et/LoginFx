
package controller;

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
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import model.Conexion;

/**
 * @author joaquín
 */
public class RHumanosController implements Initializable {

    @FXML
    private TextField txt_nomApellidos;
    @FXML
    private TextField txt_edad;
    @FXML
    private TextField txt_email;
    @FXML
    private TextField txt_telefono;
    @FXML
    private TextField txt_departamento;
    @FXML
    private TextField txt_categoria;
    @FXML
    private TextField txt_fechaContrato;
    @FXML
    private Button btn_regisEmpleado;
    @FXML
    private TextArea txt_observaciones;
    @FXML
    private Button btn_nuevoEmpleado;
    @FXML
    private Button btn_gesEmpleados;
    @FXML
    private Button btn_seleccion;
    @FXML
    private Pane contenAreaRHumanos;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    

    @FXML
    private void GuardarEmpleado(ActionEvent event) {
        
        //---Recoger las variables
        int validacion = 0;
        String nomApellidos, edad, email, telefono, departamento, categoria, fechaContrato, observaciones;
        
        nomApellidos = txt_nomApellidos.getText().trim();
        edad = txt_edad.getText().trim();
        email = txt_email.getText().trim();
        telefono = txt_telefono.getText().trim();
        departamento = txt_departamento.getText().trim();
        categoria = txt_categoria.getText().trim();
        fechaContrato = txt_fechaContrato.getText().trim();
        observaciones = txt_observaciones.getText().trim();
        
        if (nomApellidos.equals("")) {
            txt_nomApellidos.setStyle("-fx-background-color: #F64104 ;");
            validacion++;
        }
        if(edad.equals("")){
            txt_edad.setStyle("-fx-background-color: #F64104 ;");
            validacion++;
        }
        if(email.equals("")){
            txt_email.setStyle("-fx-background-color: #F64104 ;");
            validacion++;
        }
        if(telefono.equals("")){
            txt_telefono.setStyle("-fx-background-color: #F64104 ;");
            validacion++;
        }
        if(departamento.equals("")){
            txt_departamento.setStyle("-fx-background-color: #F64104 ;");
            validacion++;
        }
        if(categoria.equals("")){
            txt_categoria.setStyle("-fx-background-color: #F64104 ;");
            validacion++;
        }
        if(fechaContrato.equals("")){
            txt_fechaContrato.setStyle("-fx-background-color: #F64104 ;");
            validacion++;
        }
        
        try {
            
            Connection cn = Conexion.conectar();
            PreparedStatement pst = cn.prepareStatement(
                    "select nom_apellidos from personal where nom_apellidos = '" + nomApellidos + "'");
            
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
                        
                        Connection cn2 = Conexion.conectar();
                        PreparedStatement pst2 = cn2.prepareStatement(
                                "insert into personal values (?,?,?,?,?,?,?,?)");
                        //pst2.setInt(1, 0);  ------ Esta linea se coloca cuando tienes columna id en MySql
                        pst2.setString(1, nomApellidos);
                        pst2.setString(2, edad);
                        pst2.setString(3, email);
                        pst2.setString(4, telefono);
                        pst2.setString(5, departamento);
                        pst2.setString(6, categoria);
                        pst2.setString(7, fechaContrato);
                        pst2.setString(8, observaciones);
                        
                        pst2.executeUpdate();
                        cn2.close();

                        Limpiar();

                        Alert alert = new Alert(Alert.AlertType.INFORMATION);
                        alert.setHeaderText(null);
                        alert.setTitle("Informacion");
                        alert.setContentText("Registrado correctamente.");
                        alert.showAndWait();

                        //JOptionPane.showMessageDialog(null, "Registrado correctamente. ");
                        this.dispose();
                        
                    }catch (SQLException e){
                        
                        System.err.println("Error al registrar empleado. " + e);
                        
                        Alert alert = new Alert(Alert.AlertType.ERROR);
                        alert.setHeaderText(null);
                        alert.setTitle("Informacion");
                        alert.setContentText("¡¡ERROR al registrar!! Contacte con el administrador. ");
                        alert.showAndWait();
                        
                    }
                    
                }else{
                    
                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setHeaderText(null);
                    alert.setTitle("Informacion");
                    alert.setContentText("Debes de rellenar todos los campos.");
                    alert.showAndWait();
                    
                }
                
            }
            
        } catch (SQLException e) {
            
            System.err.println("Error al validar el nombre de empleado" + e);
            
            Alert alert = new Alert(Alert.AlertType.ERROR);
               alert.setHeaderText(null);
               alert.setTitle("Informacion");
                alert.setContentText("¡¡Error al comparar empleado!! Contacte con el administrador. ");
                alert.showAndWait();
            
        }
        
      
        
    }
    
    public void Limpiar(){
        
        txt_nomApellidos.setText("");
        txt_edad.setText("");
        txt_email.setText("");
        txt_telefono.setText("");
        txt_departamento.setText("");
        txt_categoria.setText("");
        txt_fechaContrato.setText("");
        txt_observaciones.setText("");
    }
    
    private void dispose() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @FXML
    private void irNuevoEmpleado(ActionEvent event) {
        
    }

    @FXML
    private void irGestionEmpleados(ActionEvent event) {
        
        try{
            Parent fxml = FXMLLoader.load(getClass().getResource("/view/GestionEmpleado.fxml"));
            contenAreaRHumanos.getChildren().removeAll();
            contenAreaRHumanos.getChildren().setAll(fxml);
        } catch (IOException ex) {
             System.out.println("Valla mierda");
            Logger.getLogger(AdministradorController.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }

    @FXML
    private void irSeleccion(ActionEvent event) {
        
        try{
            Parent fxml = FXMLLoader.load(getClass().getResource("/view/SeleccionEmpleados.fxml"));
            contenAreaRHumanos.getChildren().removeAll();
            contenAreaRHumanos.getChildren().setAll(fxml);
        } catch (IOException ex) {
             System.out.println("Valla mierda");
            Logger.getLogger(AdministradorController.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }
    
}
