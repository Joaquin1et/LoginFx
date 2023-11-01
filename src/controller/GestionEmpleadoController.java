
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
import model.Conexion;
import model.Personal;

/**
 * @author Joaquin
 */
public class GestionEmpleadoController implements Initializable {
    
    public static String update_user = "";

    @FXML
    private AnchorPane contenGestiEmpleado;
    @FXML
    private Button btn_nuevoEmple;
    @FXML
    private Button btn_gestionEmpleados;
    @FXML
    private Button btn_seleccion;
    @FXML
    private TableView<Personal> tbl_personal;
    @FXML
    private TableColumn<Personal, String> colum_nom_Apellidos;
    @FXML
    private TableColumn<Personal, String> colum_email;
    @FXML
    private TableColumn<Personal, String> colum_telefono;
    @FXML
    private TableColumn<Personal, String> colum_departamento;
    @FXML
    private TableColumn<Personal, String> colum_categoria;
    
    private ObservableList<Personal> listaPersonal;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
        listaPersonal = FXCollections.observableArrayList();
        
        Personal.llenarInformacionPersonal(Conexion.conectar(), listaPersonal);
        
        colum_nom_Apellidos.setCellValueFactory(new PropertyValueFactory<Personal, String>("nom_Apellidos"));
        colum_email.setCellValueFactory(new PropertyValueFactory<Personal, String>("Email"));
        colum_telefono.setCellValueFactory(new PropertyValueFactory<Personal, String>("Telefono"));
        colum_departamento.setCellValueFactory(new PropertyValueFactory<Personal, String>("Departamento"));
        colum_categoria.setCellValueFactory(new PropertyValueFactory<Personal, String>("Categoria"));
        
        tbl_personal.setItems(listaPersonal);
        
        gestionarEventos();
        
    }    

    @FXML
    private void Ir_nuevoEmple(ActionEvent event) {
        
        try{
            Parent fxml = FXMLLoader.load(getClass().getResource("/view/RHumanos.fxml"));
            contenGestiEmpleado.getChildren().removeAll();
            contenGestiEmpleado.getChildren().setAll(fxml);
        } catch (IOException ex) {
             System.out.println("Valla mierda");
            Logger.getLogger(AdministradorController.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }

    @FXML
    private void ir_gestionEmple(ActionEvent event) {
    }

    @FXML
    private void ir_seleccion(ActionEvent event) {
        
        try{
            Parent fxml = FXMLLoader.load(getClass().getResource("/view/SeleccionEmpleados.fxml"));
            contenGestiEmpleado.getChildren().removeAll();
            contenGestiEmpleado.getChildren().setAll(fxml);
        } catch (IOException ex) {
             System.out.println("Valla mierda");
            Logger.getLogger(AdministradorController.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }
    
    public void gestionarEventos() {
        tbl_personal.getSelectionModel().selectedItemProperty().addListener(
                new ChangeListener<Personal>() {
            @Override
            public void changed(ObservableValue<? extends Personal> observable, Personal valorAnterior, Personal valorSeleccionado) {

                update_user = valorSeleccionado.getNom_Apellidos();

                System.out.println("Nombre del alumno seleccionado " + update_user);

            }

        });
    }

    @FXML
    private void IrFichaEmpleado(MouseEvent event) {
        
        user = LoginFxController.user;
         
        try {

            FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/FichaEmpleado.fxml"));

            Parent root = loader.load();
            FichaEmpleadoController controlador = loader.getController();

            Scene scene = new Scene(root);
            Stage stage = new Stage();
            stage.setScene(scene);
            //stage.initModality(Modality.APPLICATION_MODAL);
            stage.setTitle("Información del empleado - Sesión de "+user);
            stage.setResizable(false);
            //stage.getIcons().add(new javafx.scene.image.Image("images/icono1.png"));

            stage.show();
            
        } catch (IOException ex) {
            Logger.getLogger(GestionEmpleadoController.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }
    
}
