/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package models;

import java.sql.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;

/**
 *
 * @author L.A.M.M#13
 */
public class ModelLogin {

    Connection cn;
    private String us;
    private char[] arrc;
    public boolean inicio = false;
    public boolean admin = false;

    public String getUs() {
        return us;
    }

    public void setUs(String us) {
        this.us = us;
    }

    public char[] getArrc() {
        return arrc;
    }
    
    public void setArrc(char[] arrc){
        this.arrc = arrc;
    }

    public void Entrar() {
        try {
            cn = DriverManager.getConnection("jdbc:mysql://localhost/acme", "root", "1234");
            String nivel = "";
            String estado = "";
            String pass = new String(arrc);
            String sql = "SELECT * FROM users WHERE usuario = ? && contrasena = ? ";
            PreparedStatement st = cn.prepareStatement(sql);
            st.setString(1, us);
            st.setString(2, pass);
            ResultSet rs = st.executeQuery();
            if (rs.next()) {
                nivel = rs.getString("nivel");
                estado = rs.getString("estado");
            }
            if (nivel.equals("administrador") && estado.equals("Activo")) {
                JOptionPane.showMessageDialog(null, "Bienvenid@  " + us);
                inicio = true;
                admin = true;
                System.out.print("Admin");
            } else if (nivel.equals("vendedor") && estado.equals("Activo")) {
                JOptionPane.showMessageDialog(null, "Bienvenid@  " + us);
                inicio = true;
                admin = false;
                System.out.print("vendedor");
            } else {
                JOptionPane.showMessageDialog(null, "Vuelve a intentar");
                inicio = false;
                System.out.print("Falso");
            }
        } catch (SQLException ex) {
            Logger.getLogger(ModelLogin.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
