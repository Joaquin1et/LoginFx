
package controller;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javax.swing.JOptionPane;
import model.Conexion;

/**
 * @author Joaquín
 */
public class SeguridadController implements Initializable {

    @FXML
    private Button btn_RegUsuarios;
    @FXML
    private Button btn_GesUsuarios;
    @FXML
    private ComboBox<String> cmb_nivel;
    @FXML
    private ComboBox<String> cmb_estatus;
    @FXML
    private AnchorPane contenAreaSecurity;
    @FXML
    private TextField txt_nombre;
    @FXML
    private TextField txt_apellido;
    @FXML
    private TextField txt_email;
    @FXML
    private TextField txt_telefono;
    @FXML
    private TextField txt_usuario;
    @FXML
    private TextField txt_password;
    @FXML
    private Button btn_registrarUsu;
    
    

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
       //--ComboBoxNivel ---------
        ArrayList<String> listaNiveles = new ArrayList<>();
        Collections.addAll(listaNiveles, new String[]{"Administrador", "Mecánico"});

        cmb_nivel.getItems().addAll(listaNiveles);
        
        //--ComboxEstatus ---------
        ArrayList<String> listaEstatus = new ArrayList<>();
        Collections.addAll(listaEstatus, new String[]{"Activo", "Inactivo"});

        cmb_estatus.getItems().addAll(listaEstatus);
        
        //--Final de los Combobox----------
        
        
    } 
    
       

    @FXML
    private void RegistroUsuarios(ActionEvent event) {
        
        try{
            Parent fxml = FXMLLoader.load(getClass().getResource("/view/Seguridad.fxml"));
            contenAreaSecurity.getChildren().removeAll();
            contenAreaSecurity.getChildren().setAll(fxml);
        } catch (IOException ex) {
             System.out.println("Valla mierda");
            Logger.getLogger(AdministradorController.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }

    @FXML
    private void GestionUsuarios(ActionEvent event) {
        
        try{
            Parent fxml = FXMLLoader.load(getClass().getResource("/view/GestionUsuarios.fxml"));
            contenAreaSecurity.getChildren().removeAll();
            contenAreaSecurity.getChildren().setAll(fxml);
        } catch (IOException ex) {
             System.out.println("Valla mierda");
            Logger.getLogger(AdministradorController.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }

    @FXML
    private void RegistrarUsuario(ActionEvent event) {
        
         
        
        //--Variables ------
        int validacion = 0, nivel_cmb, estatus_cmb;
        String nombre, apellido, email, telefono, usuario, password, nivel_string = "", estatus_string = "";
         
        nombre = txt_nombre.getText().trim();
        apellido = txt_apellido.getText().trim();
        email = txt_email.getText().trim();
        telefono = txt_telefono.getText().trim();
        usuario = txt_usuario.getText().trim();
        password = txt_password.getText().trim();
        nivel_cmb = cmb_nivel.getSelectionModel().getSelectedIndex() + 1;
        estatus_cmb = cmb_estatus.getSelectionModel().getSelectedIndex() + 1;
        
        if (nombre.equals("")) {
            txt_nombre.setStyle("-fx-background-color: #F64104 ;");
            validacion++;
        }
        if (apellido.equals("")) {
            txt_apellido.setStyle("-fx-background-color: #F64104 ;");
            validacion++;
        }
        if (email.equals("")) {
            txt_email.setStyle("-fx-background-color: #F64104 ;");
            validacion++;
        }
        if (telefono.equals("")) {
            txt_telefono.setStyle("-fx-background-color: #F64104 ;");
            validacion++;
        }
        if (usuario.equals("")) {
            txt_usuario.setStyle("-fx-background-color: #F64104 ;");
            validacion++;
        }
        if (password .equals("")) {
            txt_password.setStyle("-fx-background-color: #F64104 ;");
            validacion++;
        }
        
        //combo box de Niveles 1

        if (nivel_cmb == 1) {
            nivel_string = "Administrador";
        } else if (nivel_cmb == 2) {
            nivel_string = "Mecánico";
        } 
        
        //combo box de Estatus 1

        if (estatus_cmb == 1) {
            estatus_string = "Activo";
        } else if (estatus_cmb == 2) {
            estatus_string = "Inactivo";
        } 
        
        try {
            
            Connection cn = Conexion.conectar();
            PreparedStatement pst = cn.prepareStatement(
                    "select nombre from usuarios where nombre = '" + nombre + "'");
            
            ResultSet rs = pst.executeQuery();
            
            if (rs.next()) {
                txt_nombre.setStyle("-fx-background-color: #F0591E;");
                JOptionPane.showMessageDialog(null, "Empleado ya registrado");
                cn.close();
            }else{
                cn.close();

                if (validacion == 0) {
                    try {

                        Connection cn2 = Conexion.conectar();
                        PreparedStatement pst2 = cn2.prepareStatement(
                                "insert into usuarios values (?,?,?,?,?,?,?,?)");
                        //pst2.setInt(1, 0);  ------ Esta linea se coloca cuando tienes columna id en MySql
                        pst2.setString(1, nombre);
                        pst2.setString(2, apellido);
                        pst2.setString(3, email);
                        pst2.setString(4, telefono);
                        pst2.setString(5, nivel_string);
                        pst2.setString(6, estatus_string);
                        pst2.setString(7, usuario);
                        pst2.setString(8, password);
                        

                        pst2.executeUpdate();
                        cn2.close();

                        Limpiar();

                        /*txt_nombre.setStyle("-fx-background-color: #55F604 ;");
                        txt_apellido.setStyle("-fx-background-color: #55F604 ;");
                        txt_email.setStyle("-fx-background-color: #55F604 ;");
                        txt_telefono.setStyle("-fx-background-color: #55F604 ;");
                        txt_usuario.setStyle("-fx-background-color: #55F604 ;");
                        txt_password.setStyle("-fx-background-color: #55F604 ;");
                        cmb_nivel.setStyle("-fx-background-color: #55F604 ;");
                        cmb_estatus.setStyle("-fx-background-color: #55F604 ;");*/
                        //txt_moscososCogidos.setStyle("-fx-background-color: #55F604 ;");
                        
                        

                        Alert alert = new Alert(Alert.AlertType.INFORMATION);
                        alert.setHeaderText(null);
                        alert.setTitle("Informacion");
                        alert.setContentText("Registrado correctamente.");
                        alert.showAndWait();

                        //JOptionPane.showMessageDialog(null, "Registrado correctamente. ");
                        this.dispose();
                        
                    } catch (SQLException e) {
                        System.err.println("Error al registrar empleado. " + e);
                        
                        Alert alert = new Alert(Alert.AlertType.ERROR);
                        alert.setHeaderText(null);
                        alert.setTitle("Informacion");
                        alert.setContentText("¡¡ERROR al registrar!! Contacte con el administrador. ");
                        alert.showAndWait();
                    }

                } else {
                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setHeaderText(null);
                    alert.setTitle("Informacion");
                    alert.setContentText("Debes de rellenar todos los campos.");
                    alert.showAndWait();

                    //JOptionPane.showMessageDialog(null, "Debes de rellenar todos los campos. ");
                }
            }
            
        } catch (SQLException e) {
            
            System.err.println("Error al validar el nombre de empleado" + e);
           
                    /*Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setHeaderText(null);
                    alert.setTitle("Informacion");
                    alert.setContentText("¡¡Error al comparar empleado!! Contacte con el administrador. ");
                    alert.showAndWait();*/
        }
          
    }
    
    //----Metodo limpiar ------
    public void Limpiar() {

        txt_nombre.setText("");
        txt_apellido.setText("");
        txt_email.setText("");
        txt_telefono.setText("");
        txt_usuario.setText("");
        txt_password.setText("");
        
        
    }
    
    private void dispose() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
