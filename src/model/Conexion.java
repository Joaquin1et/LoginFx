package model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

/**
 *
 * @author Joaquín
 */
public class Conexion {
    
    public static Connection conectar(){
        
       Connection con = null;
        try {
            Class.forName("org.apache.derby.jdbc.ClientDriver");
            con = DriverManager.getConnection("jdbc:derby://localhost:1527/loginfx");
        
        } catch (Exception ex) {
            System.out.println("Error base de datos Hsqldb"+ex);
        }
        return con;
    }
    
}
