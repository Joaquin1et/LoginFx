
package controller;

import static controller.LoginFxController.user;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;
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
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javax.swing.JOptionPane;
import model.Conexion;

/**
 *
 * @author Joaquin
 */
public class FichaUsuarioController implements Initializable {
    
    String update_user = "";

    @FXML
    private AnchorPane contenAreaFichaUsuario;
    @FXML
    private Button btn_RegUsuarios;
    @FXML
    private Button btn_GesUsuarios;
    @FXML
    private TextField txt_nombre;
    @FXML
    private TextField txt_apellido;
    @FXML
    private TextField txt_email;
    @FXML
    private TextField txt_telefono;
    @FXML
    private TextField txt_permiso;
    @FXML
    private TextField txt_estatus;
    @FXML
    private TextField txt_usuario;
    @FXML
    private Button btn_actualizarUsuario;
    @FXML
    private Button btn_restaurarPassword;
    @FXML
    private Button btn_elinimarUsuario;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
        update_user = GestionUsuariosController.update_user;
        
        //MOSTRAR LA INFORMACIÓN DEL USUARIO------------
        
        try {
            Connection cn = Conexion.conectar();
            PreparedStatement pst = cn.prepareStatement(
                    "select * from usuarios where nombre = '" + update_user + "'");
            ResultSet rs = pst.executeQuery();
            System.out.println("entra bien" + update_user);

            if (rs.next()) {
                //ID = rs.getInt("id_empleado");

                txt_nombre.setText(rs.getString("nombre"));
                txt_apellido.setText(rs.getString("apellido"));
                txt_email.setText(rs.getString("email"));
                txt_telefono.setText(rs.getString("telefono"));
                txt_permiso.setText(rs.getString("tipo_nivel"));
                txt_estatus.setText(rs.getString("estatus"));
                txt_usuario.setText(rs.getString("usuario"));
                
            }

            cn.close();

        } catch (SQLException e) {
            System.err.println("Error al cargar empleado." + e);
            JOptionPane.showMessageDialog(null, "¡¡ERROR al cargar!! Contacte con el administrador.");

        }
        
    }    

    @FXML
    private void RegistroUsuarios(ActionEvent event) {
        
        try{
            Parent fxml = FXMLLoader.load(getClass().getResource("/view/Seguridad.fxml"));
            contenAreaFichaUsuario.getChildren().removeAll();
            contenAreaFichaUsuario.getChildren().setAll(fxml);
        } catch (IOException ex) {
             System.out.println("Valla mierda");
            Logger.getLogger(AdministradorController.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }

    @FXML
    private void GestionUsuarios(ActionEvent event) {
        
        try{
            Parent fxml = FXMLLoader.load(getClass().getResource("/view/GestionUsuarios.fxml"));
            contenAreaFichaUsuario.getChildren().removeAll();
            contenAreaFichaUsuario.getChildren().setAll(fxml);
        } catch (IOException ex) {
             System.out.println("Valla mierda");
            Logger.getLogger(AdministradorController.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }

    @FXML
    private void ActualizarUsuario(ActionEvent event) {
        
        try {

            int validacion = 0;
            String nombre, apellido, email, telefono, permiso, estatus, usuario;

            nombre = txt_nombre.getText().trim();
            apellido = txt_apellido.getText().trim();
            email = txt_email.getText().trim();
            telefono = txt_telefono.getText().trim();
            usuario = txt_usuario.getText().trim();
            permiso = txt_permiso.getText().trim();
            estatus = txt_estatus.getText().trim();
            
            Connection cn = Conexion.conectar();
            PreparedStatement pst2 = cn.prepareStatement(
                    "update usuarios set nombre=?, apellido=?, email=?, telefono=?, tipo_nivel=?, estatus=?, usuario=? "
                    + "where nombre = '" + update_user + "'");

            pst2.setString(1, nombre);
            pst2.setString(2, apellido);
            pst2.setString(3, email);
            pst2.setString(4, telefono);
            pst2.setString(5, permiso);
            pst2.setString(6, estatus);
            pst2.setString(7, usuario);
            
            

            pst2.executeUpdate();

            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setHeaderText(null);
            alert.setTitle("Información");
            alert.setContentText("Modificación correcta.");
            alert.showAndWait();

            //JOptionPane.showMessageDialog(null, "Modificación correcta. ");
            cn.close();

        } catch (SQLException e) {
            System.err.println("Error al actualizar." + e);
        }
        
    }

    @FXML
    private void RestaurarPassword(ActionEvent event) {
        
        user = LoginFxController.user;
        
        try {

            FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/CambioPassword.fxml"));

            Parent root = loader.load();
            CambioPasswordController controlador = loader.getController();

            Scene scene = new Scene(root);
            Stage stage = new Stage();
            stage.setScene(scene);
            //stage.initModality(Modality.APPLICATION_MODAL);
            stage.setTitle(" Sesión de "+user);
            stage.setResizable(false);
            //stage.getIcons().add(new javafx.scene.image.Image("images/icono1.png"));

            stage.show();
            
        } catch (IOException ex) {
            Logger.getLogger(CambioPasswordController.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }

    @FXML
    private void EliminarUsuario(ActionEvent event) {
        
        int validacion = 0;
        String email = "";
        
        try {

            Connection cn = Conexion.conectar();

            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setHeaderText(null);
            alert.setTitle("Confirmación");
            alert.setContentText("¿Seguro que quiere eliminarlo?");
            Optional<ButtonType> action = alert.showAndWait();

            if (action.get() != ButtonType.OK) {

                cn.close();

            } else {

            }

            PreparedStatement pst = cn.prepareStatement(
                    "delete from usuarios where nombre = ?");

            pst.setString(1, txt_nombre.getText().trim());
            pst.executeUpdate();
            
            Limpiar();

                        txt_nombre.setStyle("-fx-background-color: #6DF80C ;");
                        txt_email.setStyle("-fx-background-color: #6DF80C ;");
                        txt_telefono.setStyle("-fx-background-color: #6DF80C ;");
                        txt_usuario.setStyle("-fx-background-color: #6DF80C ;");
                        txt_permiso.setStyle("-fx-background-color: #6DF80C ;");
                        txt_estatus.setStyle("-fx-background-color: #6DF80C ;");
                        txt_apellido.setStyle("-fx-background-color: #6DF80C ;");

            Alert alert1 = new Alert(Alert.AlertType.INFORMATION);
            alert1.setHeaderText(null);
            alert1.setTitle("Informacion");
            alert1.setContentText("El usuario ha sido borrado correctamente.");
            alert1.showAndWait();
          
            //JOptionPane.showMessageDialog(null, "El empleado ha sido borrado correctamente ");

        } catch (Exception e) {
            //JOptionPane.showMessageDialog(null, "El empeado no ha sido eliminado.");
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setHeaderText(null);
            alert.setTitle("Informacion");
            alert.setContentText("El usuario no ha sido eliminado.");
            alert.showAndWait();
        }
        
    }
    
    public void Limpiar() {
        txt_nombre.setText("");
        txt_email.setText("");
        txt_telefono.setText("");
        txt_usuario.setText("");
        txt_permiso.setText("");
        txt_estatus.setText("");
        txt_apellido.setText("");

    }
    
}
