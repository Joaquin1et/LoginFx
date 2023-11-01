
package controller;

import static controller.LoginFxController.user;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import model.Candidatos;
import model.Conexion;
import model.Usuario;


public class GestionCandidatosController implements Initializable {
    
    public static String update_user = "";

    @FXML
    private AnchorPane contenAreaGestionCandidatos;
    @FXML
    private Button btn_nuevoCandidato;
    @FXML
    private Button btn_gestionCandidatos;
    @FXML
    private Button btn_procesoSeleccion;
    @FXML
    private TableView<Candidatos> tbl_candidatos;
    @FXML
    private TableColumn<Candidatos, String> colum_nomApellidos;
    @FXML
    private TableColumn<Candidatos, String> colum_fechaNaci;
    @FXML
    private TableColumn<Candidatos, String> colum_email;
    @FXML
    private TableColumn<Candidatos, String> colum_telefono;
    
    private ObservableList<Candidatos> listaCandidatos;

    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
        listaCandidatos = FXCollections.observableArrayList();
        
        Candidatos.LlenarInformacionCandidatos(Conexion.conectar(), listaCandidatos);
        colum_nomApellidos.setCellValueFactory(new PropertyValueFactory<Candidatos, String>("nom_Apellidos"));
        colum_fechaNaci.setCellValueFactory(new PropertyValueFactory<Candidatos, String>("fecha_Naci"));
        colum_email.setCellValueFactory(new PropertyValueFactory<Candidatos, String>("Email"));
        colum_telefono.setCellValueFactory(new PropertyValueFactory<Candidatos, String>("Telefono"));
        
        tbl_candidatos.setItems(listaCandidatos);
        
        gestionarEventos();
    }    

    @FXML
    private void Ir_nuevoCandidato(ActionEvent event) {
        
        try{
            Parent fxml = FXMLLoader.load(getClass().getResource("/view/SeleccionEmpleados.fxml"));
            contenAreaGestionCandidatos.getChildren().removeAll();
            contenAreaGestionCandidatos.getChildren().setAll(fxml);
        } catch (IOException ex) {
             System.out.println("Valla mierda");
            Logger.getLogger(AdministradorController.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }

    @FXML
    private void ir_gestionCandidatos(ActionEvent event) {
    }

    @FXML
    private void ir_ProcesoSeleccion(ActionEvent event) {
        
        try{
            Parent fxml = FXMLLoader.load(getClass().getResource("/view/ProcesoSeleccion.fxml"));
            contenAreaGestionCandidatos.getChildren().removeAll();
            contenAreaGestionCandidatos.getChildren().setAll(fxml);
        } catch (IOException ex) {
             System.out.println("Valla mierda");
            Logger.getLogger(AdministradorController.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }

    @FXML
    private void FichaCandidatos(MouseEvent event) {
        
        user = LoginFxController.user;
        
        try {

            FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/FichaCandidatos.fxml"));

            Parent root = loader.load();
            FichaCandidatosController controlador = loader.getController();

            Scene scene = new Scene(root);
            Stage stage = new Stage();
            stage.setScene(scene);
            //stage.initModality(Modality.APPLICATION_MODAL);
            stage.setTitle("Información del Candidato - Sesión de "+user);
            stage.setResizable(false);
            //stage.getIcons().add(new javafx.scene.image.Image("images/icono1.png"));

            stage.show();
            
        } catch (IOException ex) {
            Logger.getLogger(GestionEmpleadoController.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }
    
    
    public void gestionarEventos() {
        tbl_candidatos.getSelectionModel().selectedItemProperty().addListener(
                new ChangeListener<Candidatos>() {
            @Override
            public void changed(ObservableValue<? extends Candidatos> observable, Candidatos valorAnterior, Candidatos valorSeleccionado) {

                update_user = valorSeleccionado.getNom_Apellidos();

                System.out.println("Nombre del alumno seleccionado " + update_user);

            }

        });
    }
    
}
