
package controller;

import static controller.GestionCandidatosController.update_user;
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
import model.CandidatosSeleccionados;
import model.Conexion;

public class ProcesoSeleccionController implements Initializable {

    @FXML
    private AnchorPane contenAreaProcesoSeleccion;
    @FXML
    private Button btn_nuevoCandidato;
    @FXML
    private Button btn_gestionCandidatos;
    @FXML
    private Button btn_procesoSeleccion;
    @FXML
    private TableView<CandidatosSeleccionados> tbl_candidatosSeleccionados;
    @FXML
    private TableColumn<CandidatosSeleccionados, String> colum_nomApellidos;
    @FXML
    private TableColumn<CandidatosSeleccionados, String> colum_fechaNaci;
    @FXML
    private TableColumn<CandidatosSeleccionados, String> colum_email;
    @FXML
    private TableColumn<CandidatosSeleccionados, String> colum_telefono;
    
    private ObservableList<CandidatosSeleccionados> listaCandidatosSeleccionados;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
        listaCandidatosSeleccionados = FXCollections.observableArrayList();
        
        CandidatosSeleccionados.LlenarInformacionCandidatosSelec(Conexion.conectar(), listaCandidatosSeleccionados);
        colum_nomApellidos.setCellValueFactory(new PropertyValueFactory<CandidatosSeleccionados, String>("nom_Apellidos"));
        colum_fechaNaci.setCellValueFactory(new PropertyValueFactory<CandidatosSeleccionados, String>("fecha_Naci"));
        colum_email.setCellValueFactory(new PropertyValueFactory<CandidatosSeleccionados, String>("Email"));
        colum_telefono.setCellValueFactory(new PropertyValueFactory<CandidatosSeleccionados, String>("Telefono"));
        
        tbl_candidatosSeleccionados.setItems(listaCandidatosSeleccionados);
        
        gestionarEventos();
    }    

    @FXML
    private void ir_NuevoCandidato(ActionEvent event) {
        
        try{
            Parent fxml = FXMLLoader.load(getClass().getResource("/view/SeleccionEmpleados.fxml"));
            contenAreaProcesoSeleccion.getChildren().removeAll();
            contenAreaProcesoSeleccion.getChildren().setAll(fxml);
        } catch (IOException ex) {
             System.out.println("Valla mierda");
            Logger.getLogger(AdministradorController.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }

    @FXML
    private void Ir_gestionCandidatos(ActionEvent event) {
        
        try{
            Parent fxml = FXMLLoader.load(getClass().getResource("/view/GestionCandidatos.fxml"));
            contenAreaProcesoSeleccion.getChildren().removeAll();
            contenAreaProcesoSeleccion.getChildren().setAll(fxml);
        } catch (IOException ex) {
             System.out.println("Valla mierda");
            Logger.getLogger(AdministradorController.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }

    @FXML
    private void ir_procesoSeleccion(ActionEvent event) {
    }

    @FXML
    private void FichaCandidatoSeleccionado(MouseEvent event) {
        
        user = LoginFxController.user;
        
        try {

            FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/FichaCandidatosSeleccionados.fxml"));

            Parent root = loader.load();
            FichaCandidatosSeleccionadosController controlador = loader.getController();

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
        tbl_candidatosSeleccionados.getSelectionModel().selectedItemProperty().addListener(
                new ChangeListener<CandidatosSeleccionados>() {
            @Override
            public void changed(ObservableValue<? extends CandidatosSeleccionados> observable, CandidatosSeleccionados valorAnterior, CandidatosSeleccionados valorSeleccionado) {

                update_user = valorSeleccionado.getNom_Apellidos();

                System.out.println("Nombre del alumno seleccionado " + update_user);

            }

        });
    }
    
}
