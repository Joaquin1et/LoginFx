package controller;

import com.mysql.jdbc.Connection;
import static controller.LoginFxController.user;
import java.io.IOException;
import java.net.URL;
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
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import model.Conexion;

/**
 * @author Joaquín
 */
public class CambioPasswordController implements Initializable {
    
    String update_user = "";

    @FXML
    private PasswordField txt_cambiarPassword;
    @FXML
    private PasswordField txt_confirmarPassword;
    @FXML
    private Button btn_restaurarPassword;
    @FXML
    private AnchorPane paneCambioPassword;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        
        
        
    }  
    
    @FXML
    private void RestaurarPasword(ActionEvent event) {
        
        update_user = GestionUsuariosController.update_user;
        String password, confirmacion_pass;
        
        password = txt_cambiarPassword.getText().trim();
        confirmacion_pass = txt_confirmarPassword.getText().trim();
        
        if(!password.equals("") && !confirmacion_pass.equals("")){
            
            if(password.equals(confirmacion_pass)){
                
                try {

                    java.sql.Connection cn = Conexion.conectar();
                    PreparedStatement pst = cn.prepareStatement(
                            "update usuarios set password=? where usuario= '" + update_user + "'");

                    pst.setString(1, password);
                    pst.executeUpdate();
                    cn.close();

                    txt_cambiarPassword.setStyle("-fx-background-color: #6DF80C ;");
                    txt_confirmarPassword.setStyle("-fx-background-color: #6DF80C ;");

                    //JOptionPane.showMessageDialog(null, "Restauracion correcta. ");
                    Alert alert1 = new Alert(Alert.AlertType.INFORMATION);
                    alert1.setHeaderText(null);
                    alert1.setTitle("Información");
                    alert1.setContentText("Restauración correcta.");
                    alert1.showAndWait();
                    
                      Limpiar();
                    //this.dispose();
                           
                } catch (SQLException e) {
                    System.out.println("Error al restaurar el password " + e);
                }
                
            }else{
                
                txt_confirmarPassword.setStyle("-fx-background-color: #F98854 ;");
                
                Alert alert1 = new Alert(Alert.AlertType.ERROR);
                    alert1.setHeaderText(null);
                    alert1.setTitle("Error");
                    alert1.setContentText("Las contraseñas no coinciden.");
                    alert1.showAndWait();
                
            }
            
        }else{
            
            txt_cambiarPassword.setStyle("-fx-background-color: #F98854 ;");
            txt_confirmarPassword.setStyle("-fx-background-color: #F98854 ;");
            
            Alert alert1 = new Alert(Alert.AlertType.ERROR);
                    alert1.setHeaderText(null);
                    alert1.setTitle("Error");
                    alert1.setContentText("No se admiten campos vacios.");
                    alert1.showAndWait();
            
        }
        
    }
    
    public void Limpiar(){
        
        txt_cambiarPassword.setText("");
        txt_confirmarPassword.setText("");
                
        
    }
      
}
