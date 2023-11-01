
package controller;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javax.swing.JOptionPane;
import model.Conexion;

/**
 * FXML Controller class
 *
 * @author joaqu
 */
public class FichaVehiculoController implements Initializable {
    
    String update_user = "";

    @FXML
    private Button btn_actualizarFichaVehiculo;
    @FXML
    private Button btn_eliminarFichaVehiculo;
    @FXML
    private TextField txt_nomApellidos;
    @FXML
    private TextField txt_telefono;
    @FXML
    private TextField txt_email;
    @FXML
    private TextField txt_diaIngreso;
    @FXML
    private TextField txt_diaSalida;
    @FXML
    private TextField txt_marca;
    @FXML
    private TextField txt_modelo;
    @FXML
    private TextField txt_color;
    @FXML
    private TextField txt_kilometros;
    @FXML
    private TextField txt_matricula;
    @FXML
    private TextField txt_numMotor;
    @FXML
    private TextArea txt_trabajoRealizar;
    @FXML
    private TextArea txt_observaciones;
    @FXML
    private TextField txt_precioReparacion;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
        update_user = EnReparacionController.update_user;
        
        try {
            
            Connection cn = Conexion.conectar();
            PreparedStatement ps = cn.prepareStatement("select * from vehiculos where nomapellidos = '" + update_user + "'");
            
            ResultSet rs = ps.executeQuery();
            System.out.println("entra bien el vehiculo" + update_user);
            
            if (rs.next()) {
                
                txt_nomApellidos.setText(rs.getString("nomapellidos"));
                txt_telefono.setText(rs.getString("telefono"));
                txt_email.setText(rs.getString("email"));
                txt_diaIngreso.setText(rs.getString("diaingreso"));
                txt_diaSalida.setText(rs.getString("diasalida"));
                txt_marca.setText(rs.getString("marca"));
                txt_modelo.setText(rs.getString("modelo"));
                txt_color.setText(rs.getString("color"));
                txt_kilometros.setText(rs.getString("kilometros"));
                txt_matricula.setText(rs.getString("matricula"));
                txt_numMotor.setText(rs.getString("numeromotor"));
                txt_trabajoRealizar.setText(rs.getString("trabajorealizar"));
                txt_observaciones.setText(rs.getString("observaciones"));
                txt_precioReparacion.setText(rs.getString("precioreparacion"));
                
                
            }

            cn.close();
            
        } catch (SQLException ex) {
            
            System.err.println("Error al cargar empleado." + ex);
            JOptionPane.showMessageDialog(null, "¡¡ERROR al cargar!! Contacte con el administrador.");
        }
        
        
    }    

    @FXML
    private void EliminarFichaVehiculo(ActionEvent event) {
        
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
            
            PreparedStatement ps = cn.prepareStatement("delete from vehiculos where nomapellidos = ?");
            
            ps.setString(1, txt_nomApellidos.getText().trim());
            ps.executeUpdate();
            
            txt_nomApellidos.setText("");
            txt_telefono.setText("");
            txt_email.setText("");
            txt_diaIngreso.setText("");
            txt_diaSalida.setText("");
            txt_trabajoRealizar.setText("");
            txt_marca.setText("");
            txt_modelo.setText("");
            txt_color.setText("");
            txt_kilometros.setText("");
            txt_matricula.setText("");
            txt_numMotor.setText("");
            txt_observaciones.setText("");
            txt_precioReparacion.setText("");
            
            Alert alert1 = new Alert(Alert.AlertType.INFORMATION);
            alert1.setHeaderText(null);
            alert1.setTitle("Informacion");
            alert1.setContentText("La ficha ha sido borrada correctamente.");
            alert1.showAndWait();
            
            
        } catch (SQLException e) {
            
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setHeaderText(null);
            alert.setTitle("Informacion");
            alert.setContentText("La ficha no ha sido eliminada.");
            alert.showAndWait();
        }
        
    }

    @FXML
    private void ActualizarFichaVehiculo(ActionEvent event) {
        
        try {
            
            int validacion = 0;
            String nom_apellidos, telefono, email, dia_ingreso, dia_salida, trabajoRealizar, marca, modelo, color,
                    kilometros, matricula, numeroMotor, observaciones, precioReparacion;
            
            nom_apellidos = txt_nomApellidos.getText().trim();
            telefono = txt_telefono.getText().trim();
            email = txt_email.getText().trim();
            dia_ingreso = txt_diaIngreso.getText().trim();
            dia_salida = txt_diaSalida.getText().trim();
            trabajoRealizar = txt_trabajoRealizar.getText().trim();
            marca = txt_marca.getText().trim();
            modelo = txt_modelo.getText().trim();
            color = txt_color.getText().trim();
            kilometros = txt_kilometros.getText().trim();
            matricula = txt_matricula.getText().trim();
            numeroMotor = txt_numMotor.getText().trim();
            observaciones = txt_observaciones.getText().trim();
            precioReparacion = txt_precioReparacion.getText().trim();
            
            Connection cn1 = Conexion.conectar();
            PreparedStatement ps2 = cn1.prepareStatement("update vehiculos set nomapellidos=?, telefono=?, email=?, diaingreso=?, diasalida=?, trabajorealizar=?, marca=?, modelo=?, "
                    + "color=?, kilometros=?, matricula=?, numeromotor=?, observaciones=?, precioreparacion=? where nomapellidos = '" + update_user + "'");
            
            ps2.setString(1, nom_apellidos);
            ps2.setString(2, telefono);
            ps2.setString(3, email);
            ps2.setString(4, dia_ingreso);
            ps2.setString(5, dia_salida);
            ps2.setString(6, trabajoRealizar);
            ps2.setString(7, marca);
            ps2.setString(8, modelo);
            ps2.setString(9, color);
            ps2.setString(10, kilometros);
            ps2.setString(11, matricula);
            ps2.setString(12, numeroMotor);
            ps2.setString(13, observaciones);
            ps2.setString(14, precioReparacion);
            
            ps2.executeUpdate();
            
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setHeaderText(null);
            alert.setTitle("Información");
            alert.setContentText("Modificación correcta.");
            alert.showAndWait();
            
            cn1.close();
            
        } catch (SQLException e) {
            
            System.err.println("Error al actualizar el vehiculo." + e);
        }
    }
    
}
