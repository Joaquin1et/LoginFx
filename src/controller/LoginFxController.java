
package controller;


import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Modality;
import javafx.stage.Stage;
import model.Conexion;
import javafx.scene.control.PasswordField;
import model.Sonido;

/**
 *
 * @author Joaquín
 */
public class LoginFxController implements Initializable {

    @FXML
    private TextField usuario_txt;
   
    @FXML
    private PasswordField password_txt;
    
    @FXML
    private Button btn_login;
    
    public static String user = "";
    private String pass = "";
    
    Sonido errorInicio = new Sonido();
    Sonido rellenarCampos = new Sonido();
    
    
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    } 
    

    @FXML
    private void Login(ActionEvent event) {
        
        user = usuario_txt.getText().trim();
        pass = password_txt.getText().trim();

        if (!user.equals("") && !pass.equals("")) {
            try {
                Connection cn = Conexion.conectar();
                PreparedStatement pst = cn.prepareStatement("select tipo_nivel, estatus from usuarios where usuario = '" + user + "' and password = '" + pass + "'");

                ResultSet rs = pst.executeQuery();

                if (rs.next()) {
                    
                    String tipo_nivel = rs.getString("tipo_nivel");
                    String estatus = rs.getString("estatus");
                    
                    if(tipo_nivel.equalsIgnoreCase("Administrador") && estatus.equalsIgnoreCase("Activo")){
                        
                        try {
                        
                        FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/Administrador.fxml"));

                        Parent root = loader.load();
                        AdministradorController controlador = loader.getController();

                        Scene scene = new Scene(root);
                        Stage stage = new Stage();
                        stage.setScene(scene);
                        stage.initModality(Modality.APPLICATION_MODAL);
                        stage.setTitle("Administrador - Sesión de " + user);
                        stage.setResizable(false);
                        //stage.getIcons().add(new javafx.scene.image.Image("images/gorrologo.png"));

                        stage.show();

                        stage = (Stage) this.btn_login.getScene().getWindow();
                        stage.close();
                    } catch(Exception e){
                        Alert alert = new Alert(Alert.AlertType.ERROR);
                        alert.setHeaderText(null);
                        alert.setTitle("Error");
                        alert.setContentText("No se ha podido cargar la ventana del Administrador.\n Contacte con el informático.");
                        alert.showAndWait();
                    }
                    
                }else if(tipo_nivel.equalsIgnoreCase("Mecánico") && estatus.equalsIgnoreCase("Activo")){
                    
                    try {
                        
                        FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/Mecanico.fxml"));

                        Parent root = loader.load();
                        MecanicoController controlador = loader.getController();

                        Scene scene = new Scene(root);
                        Stage stage = new Stage();
                        stage.setScene(scene);
                        stage.initModality(Modality.APPLICATION_MODAL);
                        stage.setTitle("Mecánico - Sesión de " + user);
                        stage.setResizable(false);
                        //stage.getIcons().add(new javafx.scene.image.Image("images/gorrologo.png"));

                        stage.show();

                        stage = (Stage) this.btn_login.getScene().getWindow();
                        stage.close();
                    } catch(Exception e){
                        Alert alert = new Alert(Alert.AlertType.ERROR);
                        alert.setHeaderText(null);
                        alert.setTitle("Error");
                        alert.setContentText("No se ha podido cargar la ventana del Mecánico.\n Contacte con el informático.");
                        alert.showAndWait();
                    }
                    
                }

                } else {
                    
                    errorInicio.SonidoErrorInicio();
                    
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setHeaderText(null);
                    alert.setTitle("Error");
                    alert.setContentText("Datos de acceso incorrectos.");
                    alert.showAndWait();
                    
                    Limpiar();
                   
                }

            } catch (SQLException ex) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setHeaderText(null);
                alert.setTitle("Error");
                alert.setContentText("Error en la base de datos.");
                alert.showAndWait();
            }

        } else {
            
            rellenarCampos.SonidoRellenarCampos();
           
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText(null);
            alert.setTitle("Error");
            alert.setContentText("Debe rellenar todos los campos.");
            alert.showAndWait();
            
            Limpiar();
            
        }
        
    }
    
    public void Limpiar() {
        
        usuario_txt.setText("");
        password_txt.setText("");
    }
    
}
