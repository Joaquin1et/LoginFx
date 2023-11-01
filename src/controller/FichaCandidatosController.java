package controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.sql.Blob;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.embed.swing.SwingFXUtils;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.FileChooser;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import model.Conexion;

public class FichaCandidatosController implements Initializable {
    
    String update_user = "";

    @FXML
    private AnchorPane contenAreaFichaCandidatos;
    @FXML
    private Label lbl_imagenCandidato;
    @FXML
    private ImageView lv_imagenCandidato;
    @FXML
    private TextField txt_nomCandidato;
    @FXML
    private TextField txt_fechaNaci;
    @FXML
    private TextField txt_telefono;
    @FXML
    private Button btn_actualizarCandidato;
    @FXML
    private Button btn_eliminarCandidato;
    @FXML
    private TextArea txt_formacion;
    @FXML
    private TextArea txt_experiencia;
    @FXML
    private TextArea txt_observaciones;
    @FXML
    private TextField txt_email;
    @FXML
    private ComboBox<String> cmb_valoracion;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
        update_user = GestionCandidatosController.update_user;
        
        ArrayList<String> valoracion = new ArrayList<>();
        Collections.addAll(valoracion, new String[]{"Apto", "No Apto"});
        cmb_valoracion.getItems().addAll(valoracion);
        
        
        
        try {
            
            Connection cn = Conexion.conectar();
            PreparedStatement ps = cn.prepareStatement("select * from candidatos where nomapellidos = '" + update_user + "'");
            
            ResultSet rs = ps.executeQuery();
            System.out.println("entra bien" + update_user);
            
            if (rs.next()) {
                
                //CARGAR LA IMAGEN DE LA BASE DE DATOS---------------------------------------
                Image i = null;
                Blob blob = rs.getBlob("imagen");
                
                try {
                    i = SwingFXUtils.toFXImage(javax.imageio.ImageIO.read(blob.getBinaryStream()), null);
                } catch (IOException ex) {
                    Logger.getLogger(FichaCandidatosController.class.getName()).log(Level.SEVERE, null, ex);
                    
                    System.out.println("Me cago en tu padre.");
                }
                
                lv_imagenCandidato.setImage(i); //Aqui colocamos la ImageView
                
                //FINALIZA EL CODIGO DE LA CARGA DE LA IMAGEN--------------------------------------   
                
                //ID = rs.getInt("id_empleado");

                txt_nomCandidato.setText(rs.getString("nomapellidos"));
                txt_fechaNaci.setText(rs.getString("fechanacimiento"));
                txt_telefono.setText(rs.getString("telefono"));
                txt_email.setText(rs.getString("email"));
                txt_formacion.setText(rs.getString("formacion"));
                txt_experiencia.setText(rs.getString("experiencia"));
                txt_observaciones.setText(rs.getString("observaciones"));
                
                
            }

            cn.close();
            
        } catch (SQLException ex) {
            
            System.err.println("Error al cargar empleado." + ex);
            JOptionPane.showMessageDialog(null, "¡¡ERROR al cargar!! Contacte con el administrador.");
        }
        
    }    

    @FXML
    private void ActualizarCandidato(ActionEvent event) {
        
        try {
            
            int valoracion, validacion = 0;
            String nomApellidos, fechaNaci, telefono, email, formacion, experiencia, observaciones, valoracion_string="";
            
            nomApellidos = txt_nomCandidato.getText().trim();
            fechaNaci = txt_fechaNaci.getText().trim();
            telefono = txt_telefono.getText().trim();
            email = txt_email.getText().trim();
            formacion = txt_formacion.getText().trim();
            experiencia = txt_experiencia.getText().trim();
            observaciones = txt_observaciones.getText().trim();
            valoracion = cmb_valoracion.getSelectionModel().getSelectedIndex() + 1;
            
            if (valoracion == 1) {
                 valoracion_string = "Apto";
              } else if (valoracion == 2) {
                 valoracion_string = "No Apto";
           }
            
            Connection cn = Conexion.conectar();
            
            Alert alert1 = new Alert(Alert.AlertType.CONFIRMATION);
            alert1.setHeaderText(null);
            alert1.setTitle("Confirmación");
            alert1.setContentText("¿Quiere modificar la imagen?");
            Optional<ButtonType> action = alert1.showAndWait();
            
            if(action.get() != ButtonType.OK){
                
                PreparedStatement ps1 = cn.prepareStatement(
                "update candidatos set nomapellidos=?, fechanacimiento=?, telefono=?, email=?, formacion=?, experiencia=?, observaciones=?, valoracion=?"
                        + " where nomapellidos = '" + update_user + "'"
                );
                
                ps1.setString(1, nomApellidos);
                ps1.setString(2, fechaNaci);
                ps1.setString(3, telefono);
                ps1.setString(4, email);
                ps1.setString(5, formacion);
                ps1.setString(6, experiencia);
                ps1.setString(7, observaciones);
                ps1.setString(8, valoracion_string);
                
                ps1.executeUpdate();
                
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setHeaderText(null);
                alert.setTitle("Información");
                alert.setContentText("Modificación correcta.");
                alert.showAndWait();
                
                cn.close();
                
            }else{
                
                // Cambiando la foto del Candidato.
                
                JFileChooser escoger = new JFileChooser();
                escoger.setFileSelectionMode(JFileChooser.FILES_ONLY);
                int estado = escoger.showOpenDialog(null);
                
                File archivo = escoger.getSelectedFile();
                long longitud = archivo.length();
                FileInputStream flujo;
                
                try {
                    flujo = new FileInputStream(archivo);
                
            PreparedStatement pst2 = cn.prepareStatement(
                   
                    "update candidatos set nomapellidos=?, fechanacimiento=?, telefono=?, email=?, formacion=?, experiencia=?, observaciones=?, imagen=?, valoracion=?"
                        + " where nomapellidos = '" + update_user + "'");

                pst2.setString(1, nomApellidos);
                pst2.setString(2, fechaNaci);
                pst2.setString(3, telefono);
                pst2.setString(4, email);
                pst2.setString(5, formacion);
                pst2.setString(6, experiencia);
                pst2.setString(7, observaciones);
                pst2.setBlob(8, flujo, longitud);
                pst2.setString(9, valoracion_string);
           
            pst2.executeUpdate();

            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setHeaderText(null);
            alert.setTitle("Información");
            alert.setContentText("Modificación correcta.");
            alert.showAndWait();

            //JOptionPane.showMessageDialog(null, "Modificación correcta. ");
            cn.close();
            
            } catch (FileNotFoundException ex) {
                    Logger.getLogger(FichaCandidatosController.class.getName()).log(Level.SEVERE, null, ex);
                }
                
                
            }
            
        } catch (SQLException e) {
            
            Alert alert1 = new Alert(Alert.AlertType.ERROR);
            alert1.setHeaderText(null);
            alert1.setTitle("Error");
            alert1.setContentText("ERROR Al actualizar"+e);
            
        }
        
    }

    @FXML
    private void EliminarCandidato(ActionEvent event) {
        
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
                    "delete from candidatos where nomapellidos = ?");

            pst.setString(1, txt_nomCandidato.getText().trim());
            pst.executeUpdate();

            txt_nomCandidato.setText("");
            txt_fechaNaci.setText("");
            txt_telefono.setText("");
            txt_email.setText("");
            txt_formacion.setText("");
            txt_experiencia.setText("");
            txt_observaciones.setText("");
            
            Alert alert1 = new Alert(Alert.AlertType.INFORMATION);
            alert1.setHeaderText(null);
            alert1.setTitle("Informacion");
            alert1.setContentText("El Candidato ha sido borrado correctamente.");
            alert1.showAndWait();
          
            //JOptionPane.showMessageDialog(null, "El empleado ha sido borrado correctamente ");

        } catch (Exception e) {
            //JOptionPane.showMessageDialog(null, "El empeado no ha sido eliminado.");
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setHeaderText(null);
            alert.setTitle("Informacion");
            alert.showAndWait();
        }
        
        
    }
    
}
