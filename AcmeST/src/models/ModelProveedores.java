/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package models;
import java.sql.DriverManager;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import javax.swing.JOptionPane;
import sax.DBConnection;
/**
 *
 * @author WINDOWS
 */
public class ModelProveedores {
    
    private DBConnection conection = new DBConnection(3306,"localhost", "acme", "root", "1234");
    private String nombre;
    private String rfc;
    private String calle;
    private String no;
    private String colonia;
    private String ciudad;
    private String estado;
    private String contacto;
    private String telefono;
    private String email;
    public String sql;
    /**
     * @return the nombre
     */
    public String getNombre() {
        return nombre;
    }

    /**
     * @param nombre the nombre to set
     */
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    /**
     * @return the rfc
     */
    public String getRfc() {
        return rfc;
    }

    /**
     * @param rfc the rfc to set
     */
    public void setRfc(String rfc) {
        this.rfc = rfc;
    }

    /**
     * @return the calle
     */
    public String getCalle() {
        return calle;
    }

    /**
     * @param calle the calle to set
     */
    public void setCalle(String calle) {
        this.calle = calle;
    }

    /**
     * @return the no
     */
    public String getNo() {
        return no;
    }

    /**
     * @param no the no to set
     */
    public void setNo(String no) {
        this.no = no;
    }

    /**
     * @return the colonia
     */
    public String getColonia() {
        return colonia;
    }

    /**
     * @param colonia the colonia to set
     */
    public void setColonia(String colonia) {
        this.colonia = colonia;
    }

    /**
     * @return the ciudad
     */
    public String getCiudad() {
        return ciudad;
    }

    /**
     * @param ciudad the ciudad to set
     */
    public void setCiudad(String ciudad) {
        this.ciudad = ciudad;
    }

    /**
     * @return the estado
     */
    public String getEstado() {
        return estado;
    }

    /**
     * @param estado the estado to set
     */
    public void setEstado(String estado) {
        this.estado = estado;
    }

    /**
     * @return the contacto
     */
    public String getContacto() {
        return contacto;
    }

    /**
     * @param contacto the contacto to set
     */
    public void setContacto(String contacto) {
        this.contacto = contacto;
    }

    /**
     * @return the telefono
     */
    public String getTelefono() {
        return telefono;
    }

    /**
     * @param telefono the telefono to set
     */
    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    /**
     * @return the email
     */
    public String getEmail() {
        return email;
    }

    /**
     * @param email the email to set
     */
    public void setEmail(String email) {
        this.email = email;
    }
    
    public void moveNext() {
        conection.moveNext();
        setValues();
    }

    public void movePrevious() {
        conection.movePrevious();
        setValues();
    }

    public void moveFirst() {
        conection.moveFirst();
        setValues();
    }

    public void moveLast() {
        conection.moveLast();
        setValues();
    }

    public void initValues() {
        conection.executeQuery("SELECT * FROM proveedores;");
        conection.moveNext();
        setValues();
    }

    public void setValues() {
        this.nombre = conection.getString("nombre");
        this.rfc = conection.getString("rfc");
        this.calle = conection.getString("calle");
        this.no = "" + conection.getInteger("num");
        this.colonia = conection.getString("colonia");
        this.ciudad = conection.getString("ciudad");
        this.estado = conection.getString("estado");
        this.contacto = conection.getString("nombre_contacto");
        this.telefono = conection.getString("telefono");
        this.email = conection.getString("email");
    }
    
    public String insertSql(){
        String id = JOptionPane.showInputDialog("Dame la id del PROVEEDOR","");
        sql = "INSERT INTO proveedor(id_proveedor,nombre,rfc,calle,num,colonia,ciudad,estado,nombre_contacto,telefono,email) VALUES('"+id+"','"+nombre+"','"+rfc+"','"+calle+"','"+no+"','"+colonia+"','"+ciudad+"','"+estado+"','"+contacto+"','"+telefono+"','"+email+"');";
        return sql;
    }
    
    public String deleteSql(){
        int id = Integer.parseInt(JOptionPane.showInputDialog("Dame la id del PROVEEDOR",""));
        sql = "delete from proveedor where id_proveedor='"+id+"' ";
        return sql;
    }
    
    public String updateSql(){
        String id = JOptionPane.showInputDialog("Dame la id del PROVEEDOR","");
        sql = "UPDATE proveedor SET nombre='"+nombre+"',rfc='"+rfc+"',calle='"+calle+"',no='"+no+"',colonia='"+colonia+"',ciudad='"+ciudad+"',estado='"+estado+"',nombre_contacto='"+contacto+"',telefono='"+telefono+"',email='"+email+"' WHERE id_proveedor='"+id+"'";
        return sql;
    }
}
