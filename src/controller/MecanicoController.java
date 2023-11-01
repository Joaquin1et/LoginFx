
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
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import model.Sonido;

/**
 * FXML Controller class
 *
 * @author Joaquín
 */
public class MecanicoController implements Initializable {

    @FXML
    private Button btn_Reparando;
    
    Sonido iniEncargado = new Sonido();
    @FXML
    private Button btn_admisionVeh;
    @FXML
    private AnchorPane contenAreaMecanico;
    @FXML
    private Pane contenAreaPaneMecanico;
    @FXML
    private Button btn_salidaVehiculos;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
        iniEncargado.SonidoInicioTaller();
        
    } 

    public void closeWindowws(){
        
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

                        stage = (Stage) this.btn_Reparando.getScene().getWindow();
                        stage.close();
                        
                    } catch(Exception e){
                        /*Alert alert = new Alert(Alert.AlertType.ERROR);
                        alert.setHeaderText(null);
                        alert.setTitle("Error");
                        alert.setContentText("No se ha podido cargar la ventana de los Mecanicos.\n Contacte con el informático.");
                        alert.showAndWait();*/
                    }
        
    }

    @FXML
    private void irReparacion(ActionEvent event) {
        
        try{
            Parent fxml = FXMLLoader.load(getClass().getResource("/view/EnReparacion.fxml"));
            contenAreaPaneMecanico.getChildren().removeAll();
            contenAreaPaneMecanico.getChildren().setAll(fxml);
        } catch (IOException ex) {
             System.out.println("Valla mierda");
            Logger.getLogger(AdmisionVehController.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }

    @FXML
    private void AdmisionVehiculo(ActionEvent event) {
        
        try{
            Parent fxml = FXMLLoader.load(getClass().getResource("/view/AdmisionVeh.fxml"));
            contenAreaPaneMecanico.getChildren().removeAll();
            contenAreaPaneMecanico.getChildren().setAll(fxml);
        } catch (IOException ex) {
             System.out.println("Valla mierda");
            Logger.getLogger(AdmisionVehController.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }

    @FXML
    private void irSalidaVehiculos(ActionEvent event) {
        
        try{
            Parent fxml = FXMLLoader.load(getClass().getResource("/view/OrdenFinalizada.fxml"));
            contenAreaPaneMecanico.getChildren().removeAll();
            contenAreaPaneMecanico.getChildren().setAll(fxml);
        } catch (IOException ex) {
             System.out.println("Valla mierda");
            Logger.getLogger(AdmisionVehController.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }
    
    
    
}
