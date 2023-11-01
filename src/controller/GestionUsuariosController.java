package controller;

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
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import model.Conexion;
import model.Usuario;

/**
 * @author Joaquín
 */
public class GestionUsuariosController implements Initializable {
    
    public static String update_user = "";

    @FXML
    private AnchorPane contenAreaGestionUsu;
    @FXML
    private Button btn_RegUsuarios;
    @FXML
    private Button btn_GesUsuarios;
    @FXML
    private TableView<Usuario> tbl_usuarios;
    @FXML
    private TableColumn<Usuario, String> colum_nombre;
    @FXML
    private TableColumn<Usuario, String> colum_apellido;
    @FXML
    private TableColumn<Usuario, String> colum_email;
    @FXML
    private TableColumn<Usuario, String> colum_telefono;
    @FXML
    private TableColumn<Usuario, String> colum_permiso;
    
    private ObservableList<Usuario> listaUsuarios;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
        listaUsuarios = FXCollections.observableArrayList();
        
        Usuario.llenarInformacionUsuarios(Conexion.conectar(), listaUsuarios);
        
        colum_nombre.setCellValueFactory(new PropertyValueFactory<Usuario, String>("Nombre"));
        colum_apellido.setCellValueFactory(new PropertyValueFactory<Usuario, String>("Apellido"));
        colum_email.setCellValueFactory(new PropertyValueFactory<Usuario, String>("Email"));
        colum_telefono.setCellValueFactory(new PropertyValueFactory<Usuario, String>("Telefono"));
        colum_permiso.setCellValueFactory(new PropertyValueFactory<Usuario, String>("Permiso"));

        tbl_usuarios.setItems(listaUsuarios);

        gestionarEventos();
        
    }    

    @FXML
    private void RegistroUsuarios(ActionEvent event) {
        
        try{
            Parent fxml = FXMLLoader.load(getClass().getResource("/view/Seguridad.fxml"));
            contenAreaGestionUsu.getChildren().removeAll();
            contenAreaGestionUsu.getChildren().setAll(fxml);
        } catch (IOException ex) {
             System.out.println("Valla mierda");
            Logger.getLogger(AdministradorController.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }

    @FXML
    private void GestionUsuarios(ActionEvent event) {
    }

    @FXML
    private void FichaUsuario(MouseEvent event) {
       
        //---Insertar el codigo para un panel
        try{
            Parent fxml = FXMLLoader.load(getClass().getResource("/view/FichaUsuario.fxml"));
            contenAreaGestionUsu.getChildren().removeAll();
            contenAreaGestionUsu.getChildren().setAll(fxml);
        } catch (IOException ex) {
             System.out.println("Valla mierda");
            Logger.getLogger(AdministradorController.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        
    }
    
    public void gestionarEventos() {
        tbl_usuarios.getSelectionModel().selectedItemProperty().addListener(
                new ChangeListener<Usuario>() {
            @Override
            public void changed(ObservableValue<? extends Usuario> observable, Usuario valorAnterior, Usuario valorSeleccionado) {

                update_user = valorSeleccionado.getNombre();

                System.out.println("Nombre del alumno seleccionado " + update_user);

            }

        });
    }
    
}
