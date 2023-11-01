package model;

import java.applet.AudioClip;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

/**
 * @author Joaquín
 */
public class Sonido {
    
    AudioClip AccesoIncorrecto;
    AudioClip rellenarCampos;
    AudioClip secretaria;
    AudioClip IniEncargado;
    
    public void SonidoErrorInicio(){
        
        AccesoIncorrecto = java.applet.Applet.newAudioClip(getClass().getResource("/voces/Acceso_taller_incorrecto.wav"));
        AccesoIncorrecto.play();
        
    }
    
    public void SonidoSaludoSecre(){
        
        secretaria = java.applet.Applet.newAudioClip(getClass().getResource("/voces/secretaria.wav"));
        secretaria.play();
        
    }
    
    public void SonidoRellenarCampos(){
        
        rellenarCampos = java.applet.Applet.newAudioClip(getClass().getResource("/voces/rellenarCampos.wav"));
        rellenarCampos.play();
        
    }
    
    public void SonidoInicioTaller(){
        
        IniEncargado = java.applet.Applet.newAudioClip(getClass().getResource("/voces/encargado.wav"));
        IniEncargado.play();
        
    }
    
    
}
