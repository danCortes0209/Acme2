/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package models;
import javax.swing.JOptionPane;
import sax.DBConnection;
/**
 *
 * @author L.A.M.M#13
 */
public class ModelUsuario {
    private DBConnection conection = new DBConnection(3306,"localhost", "acme", "root", "1234");
    private String id_admin;
    private String nombre;
    private String ap_pat;
    private String ap_mat;
    private String nivel;
    private String estado;
    private String usuario;
    private String contrasena;

    public String getId_admin() {
        return id_admin;
    }

    public void setId_admin(String id_admin) {
        this.id_admin = id_admin;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getAp_pat() {
        return ap_pat;
    }

    public void setAp_pat(String ap_pat) {
        this.ap_pat = ap_pat;
    }

    public String getAp_mat() {
        return ap_mat;
    }

    public void setAp_mat(String ap_mat) {
        this.ap_mat = ap_mat;
    }

    public String getNivel() {
        return nivel;
    }

    public void setNivel(String nivel) {
        this.nivel = nivel;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }
    
    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public String getContrasena() {
        return contrasena;
    }

    public void setContrasena(String contrasena) {
        this.contrasena = contrasena;
    }
    
    public void moveNext(){
        conection.moveNext();
        setValues();
    }
    
    public void movePrevious(){
        conection.movePrevious();
        setValues();
    }
    
    public void moveFirst(){
        conection.moveFirst();
        setValues();
    }
    
    public void moveLast(){
        conection.moveLast();
        setValues();
    }
    
    public void initValues(){
        conection.executeQuery("SELECT id_user,nombre,apellido_pat,apellido_mat,usuario,contrasena,nivel,estado FROM users;");
        conection.moveNext();
        setValues();
    }
    
    public void setValues(){
        this.id_admin = conection.getString("id_user");
        this.nombre = conection.getString("nombre");
        this.ap_pat = conection.getString("apellido_pat");
        this.ap_mat = conection.getString("apellido_mat");
        this.usuario = conection.getString("usuario");
        this.contrasena = conection.getString("contrasena");
        this.nivel = conection.getString("nivel");
        this.estado = conection.getString("estado");
    }
    
    
    
    public void guardar(){
        String sql = "insert into users(nombre,apellido_pat,apellido_mat,"
                + "usuario,contrasena,nivel,estado) values ("+"'"+
                nombre+"','"+ap_pat+"','"+ap_mat+"','"+usuario+"',("
                + "MD5('"+contrasena+"')),'"+nivel+"','"+estado+"');";
        System.out.println("SQL " + sql);
        conection.executeUpdate(sql);
    }
    
    public void borrar(){
        this.id_admin = JOptionPane.showInputDialog("Dame la id del usuario","");
        conection.executeUpdate("delete from users where id_user=" + id_admin);
    }
    
    public void editar(){
        this.id_admin = JOptionPane.showInputDialog("Dame la id del usuario","");
        conection.executeUpdate("update users set nombre='"+nombre+"',apellido_pat='"+ap_pat
                +"',apellido_mat='"+ap_mat+"',usuario='"+usuario+"',contrasena='"+contrasena
                +"',nivel='"+nivel+"',estado='"+estado+"'where id_user'"+id_admin+"';");
    }
}