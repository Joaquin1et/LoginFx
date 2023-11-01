package controller;

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
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javax.swing.JOptionPane;
import model.Conexion;

/**
 * @author Joaquín
 */
public class FichaEmpleadoController implements Initializable {
    
    String update_user = "";

    @FXML
    private AnchorPane contenAreaFichaEmpleados;
    @FXML
    private TextField txt_nomApellidos;
    @FXML
    private TextField txt_edad;
    @FXML
    private TextField txt_email;
    @FXML
    private TextField txt_telefono;
    @FXML
    private TextField txt_departamento;
    @FXML
    private TextField txt_fechaContrato;
    @FXML
    private Button btn_actualizarEmpleado;
    @FXML
    private Button btn_eliminarEmpleado;
    @FXML
    private TextArea txt_observaciones;
    @FXML
    private TextField txt_categoria;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
        update_user = GestionEmpleadoController.update_user;
        
        try {
            
            Connection cn = Conexion.conectar();
            PreparedStatement pst = cn.prepareStatement("select * from personal where nom_apellidos = '" + update_user + "'");
            
            ResultSet rs = pst.executeQuery();
            System.out.println("entra bien" + update_user);
            
            if (rs.next()) {
                //ID = rs.getInt("id_empleado");

                txt_nomApellidos.setText(rs.getString("nom_apellidos"));
                txt_edad.setText(rs.getString("edad"));
                txt_email.setText(rs.getString("email"));
                txt_telefono.setText(rs.getString("telefono"));
                txt_departamento.setText(rs.getString("departamento"));
                txt_categoria.setText(rs.getString("categoria"));
                txt_fechaContrato.setText(rs.getString("fecha_contrato"));
                txt_observaciones.setText(rs.getString("observaciones"));
                
            }

            cn.close();
            
        } catch (SQLException ex) {
            
            System.err.println("Error al cargar empleado." + ex);
            JOptionPane.showMessageDialog(null, "¡¡ERROR al cargar!! Contacte con el administrador.");
            
        }
        
    }    

    @FXML
    private void ActualizarEmpleado(ActionEvent event) {
        
        try {

            int validacion = 0;
            String nom_apellidos, edad, email, telefono, departamento, categoria, fecha_contrato, observaciones;

            nom_apellidos = txt_nomApellidos.getText().trim();
            edad = txt_edad.getText().trim();
            email = txt_email.getText().trim();
            telefono = txt_telefono.getText().trim();
            departamento = txt_departamento.getText().trim();
            categoria = txt_categoria.getText().trim();
            fecha_contrato = txt_fechaContrato.getText().trim();
            observaciones = txt_observaciones.getText().trim();

            Connection cn = Conexion.conectar();
            PreparedStatement pst2 = cn.prepareStatement(
                    "update personal set nom_apellidos=?, edad=?, email=?, telefono=?,departamento=?, categoria=?, fecha_contrato=?, observaciones=? "
                    + "where nom_apellidos = '" + update_user + "'");

            pst2.setString(1, nom_apellidos);
            pst2.setString(2, edad);
            pst2.setString(3, email);
            pst2.setString(4, telefono);
            pst2.setString(5, departamento);
            pst2.setString(6, categoria);
            pst2.setString(7, fecha_contrato);
            pst2.setString(8, observaciones);

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
    private void EliminarEmpleado(ActionEvent event) {
        
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
                    "delete from personal where nom_apellidos = ?");

            pst.setString(1, txt_nomApellidos.getText().trim());
            pst.executeUpdate();

            txt_nomApellidos.setText("");
            txt_edad.setText("");
            txt_email.setText("");
            txt_telefono.setText("");
            txt_departamento.setText("");
            txt_categoria.setText("");
            txt_fechaContrato.setText("");
            txt_observaciones.setText("");
            
            Alert alert1 = new Alert(Alert.AlertType.INFORMATION);
            alert1.setHeaderText(null);
            alert1.setTitle("Informacion");
            alert1.setContentText("El empleado ha sido borrado correctamente.");
            alert1.showAndWait();
          
            //JOptionPane.showMessageDialog(null, "El empleado ha sido borrado correctamente ");

        } catch (Exception e) {
            //JOptionPane.showMessageDialog(null, "El empeado no ha sido eliminado.");
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setHeaderText(null);
            alert.setTitle("Informacion");
            alert.setContentText("El empeado no ha sido eliminado.");
            alert.showAndWait();
        }
        
    }
    
}
