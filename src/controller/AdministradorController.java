
package controller;

import static controller.LoginFxController.user;
import java.io.IOException;
import java.net.URL;
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
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import model.Sonido;
import model.Sonidos;

/**
 * FXML Controller class
 *
 * @author joaqu
 */
public class AdministradorController implements Initializable {

    Sonido saludoSecretaria = new Sonido();
    
    @FXML
    private Button adminSecurity;
    @FXML
    private Pane contenAreaAdmin;
    @FXML
    private Button btn_mecanicos;
    @FXML
    private Button administracion;
    @FXML
    private Button r_humanos;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        saludoSecretaria.SonidoSaludoSecre();
    }    

    @FXML
    private void adminSecurity(ActionEvent event) {
        
        try{
            Parent fxml = FXMLLoader.load(getClass().getResource("/view/Seguridad.fxml"));
            contenAreaAdmin.getChildren().removeAll();
            contenAreaAdmin.getChildren().setAll(fxml);
        } catch (IOException ex) {
             System.out.println("Valla mierda");
            Logger.getLogger(AdministradorController.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }

    @FXML
    private void irMecanicos(ActionEvent event) {
        
        try {
                        
                        FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/Mecanico.fxml"));

                        Parent root = loader.load();
                        MecanicoController controlador = loader.getController();

                        Scene scene = new Scene(root);
                        Stage stage = new Stage();
                        stage.setScene(scene);
                        stage.initModality(Modality.APPLICATION_MODAL);
                        stage.setTitle("Administrador - Sesión de " + user);
                        stage.setResizable(false);
                        //stage.getIcons().add(new javafx.scene.image.Image("images/gorrologo.png"));

                        stage.show();
                        
                        stage.setOnCloseRequest(e -> controlador.closeWindowws());

                        stage = (Stage) this.btn_mecanicos.getScene().getWindow();
                        stage.close();
                        
                    } catch(Exception e){
                        Alert alert = new Alert(Alert.AlertType.ERROR);
                        alert.setHeaderText(null);
                        alert.setTitle("Error");
                        alert.setContentText("No se ha podido cargar la ventana de los Mecanicos.\n Contacte con el informático.");
                        alert.showAndWait();
                    }
        
    }

    @FXML
    private void irAdministracion(ActionEvent event) {
        
        try{
            Parent fxml = FXMLLoader.load(getClass().getResource("/view/Administracion.fxml"));
            contenAreaAdmin.getChildren().removeAll();
            contenAreaAdmin.getChildren().setAll(fxml);
        } catch (IOException ex) {
             System.out.println("Valla mierda");
            Logger.getLogger(AdministradorController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @FXML
    private void irRHumanos(ActionEvent event) {
        
        try{
            Parent fxml = FXMLLoader.load(getClass().getResource("/view/RHumanos.fxml"));
            contenAreaAdmin.getChildren().removeAll();
            contenAreaAdmin.getChildren().setAll(fxml);
        } catch (IOException ex) {
             System.out.println("Valla mierda");
            Logger.getLogger(AdministradorController.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }

    

    
}
