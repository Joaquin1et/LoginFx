package controller;

import static controller.EnReparacionController.update_user;
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
import model.Conexion;
import model.Vehiculo;

public class OrdenFinalizadaController implements Initializable {
    
    public static String update_user = "";

    @FXML
    private AnchorPane contenAreaOrdenFinalizada;
    @FXML
    private Button btn_ordenServicio;
    @FXML
    private Button btn_enReparacion;
    @FXML
    private Button btn_ordenFinalizada;
    @FXML
    private TableView<Vehiculo> tbl_vehiculoFinalizado;
    @FXML
    private TableColumn<Vehiculo, String> colum_nomApellidos;
    @FXML
    private TableColumn<Vehiculo, String> colum_telefono;
    @FXML
    private TableColumn<Vehiculo, String> colum_marca;
    @FXML
    private TableColumn<Vehiculo, String> colum_modelo;
    @FXML
    private TableColumn<Vehiculo, String> colum_matricula;
    
    private ObservableList<Vehiculo> listaVehiculoTerminado;
    

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
        listaVehiculoTerminado = FXCollections.observableArrayList();
        
        Vehiculo.llenarInformacionVehiculoTerminado(Conexion.conectar(), listaVehiculoTerminado);
        
        colum_nomApellidos.setCellValueFactory(new PropertyValueFactory<Vehiculo, String>("nom_Apellidos"));
        colum_telefono.setCellValueFactory(new PropertyValueFactory<Vehiculo, String>("telefono"));
        colum_marca.setCellValueFactory(new PropertyValueFactory<Vehiculo, String>("marca"));
        colum_modelo.setCellValueFactory(new PropertyValueFactory<Vehiculo, String>("modelo"));
        colum_matricula.setCellValueFactory(new PropertyValueFactory<Vehiculo, String>("matricula"));
        
        tbl_vehiculoFinalizado.setItems(listaVehiculoTerminado);
        
        gestionarEventos();
        
    }    

    @FXML
    private void irOrdenServicio(ActionEvent event) {
        
        try{
            Parent fxml = FXMLLoader.load(getClass().getResource("/view/AdmisionVeh.fxml"));
            contenAreaOrdenFinalizada.getChildren().removeAll();
            contenAreaOrdenFinalizada.getChildren().setAll(fxml);
        } catch (IOException ex) {
             System.out.println("Valla mierda");
            Logger.getLogger(AdmisionVehController.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }

    @FXML
    private void irEnReparacion(ActionEvent event) {
        
        try{
            Parent fxml = FXMLLoader.load(getClass().getResource("/view/EnReparacion.fxml"));
            contenAreaOrdenFinalizada.getChildren().removeAll();
            contenAreaOrdenFinalizada.getChildren().setAll(fxml);
        } catch (IOException ex) {
             System.out.println("Valla mierda");
            Logger.getLogger(AdmisionVehController.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }

    @FXML
    private void irOrdenFinalizada(ActionEvent event) {
    }

    @FXML
    private void IrFIchaVehiculoFinalizado(MouseEvent event) {
        
        user = LoginFxController.user;
         
        try {

            FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/FichaVehiculoFinalizado.fxml"));

            Parent root = loader.load();
            FichaVehiculoFinalizadoController controlador = loader.getController();

            Scene scene = new Scene(root);
            Stage stage = new Stage();
            stage.setScene(scene);
            //stage.initModality(Modality.APPLICATION_MODAL);
            stage.setTitle("Información del vehícilo reparado - Sesión de "+user);
            stage.setResizable(false);
            //stage.getIcons().add(new javafx.scene.image.Image("images/icono1.png"));

            stage.show();
            
        } catch (IOException ex) {
            Logger.getLogger(GestionEmpleadoController.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }
    
    public void gestionarEventos() {
        tbl_vehiculoFinalizado.getSelectionModel().selectedItemProperty().addListener(
                new ChangeListener<Vehiculo>() {
            @Override
            public void changed(ObservableValue<? extends Vehiculo> observable, Vehiculo valorAnterior, Vehiculo valorSeleccionado) {

                update_user = valorSeleccionado.getNom_Apellidos();

                System.out.println("Nombre del propietario " + update_user);

            }

        });
    }
    
}
