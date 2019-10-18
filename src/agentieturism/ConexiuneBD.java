/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package agentieturism;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 *
 * @author Diana
 */
public class ConexiuneBD {
    
    public static final String USERNAME  = "root";
    public static final String PASSWORD = "root";
    public static final String CONN_STRING = "jdbc:mysql://localhost:3306/agentieTurism?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC";
    
    public static Connection getConnection(){
        Connection conn = null;
        
        try{
            conn = DriverManager.getConnection(CONN_STRING, USERNAME, PASSWORD);
            
        }catch(SQLException e){
            System.err.println(e);
        }
        return conn;
    }
    
     public static void codFinally(ResultSet rs, PreparedStatement stmt, Connection conn) {
        if (rs != null) {
            try {
                rs.close();
            } catch (SQLException ex) {
                System.out.println("Probleme inchidere ResultSet");
            }
        }
        if (stmt != null) {
            try {
                stmt.close();
            } catch (SQLException ex) {
                System.out.println("Probleme inchidere Statement");
            }
        }
        if (conn != null) {
            try {
                conn.close();
            } catch (SQLException ex) {
                System.out.println("Probleme inchidere Conexiune");
            }
        }
     }
}

