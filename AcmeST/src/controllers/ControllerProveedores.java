/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener; 
import java.sql.DriverManager;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import models.ModelProveedores;
import views.ViewProveedores;
/**
 *
 * @author WINDOWS
 */
public class ControllerProveedores implements ActionListener{
    Connection conexion;
    Statement st;
    ResultSet rs;
    ModelProveedores modelProveedores;
    ViewProveedores viewProveedores;
    ResultSetMetaData rsm;
    public ControllerProveedores(ModelProveedores modelProveedores, ViewProveedores viewProveedores){
        this.modelProveedores = modelProveedores;
        this.viewProveedores = viewProveedores;
        this.viewProveedores.jbAdd.addActionListener(this);
        this.viewProveedores.jbDelete.addActionListener(this);
        this.viewProveedores.jbEdit.addActionListener(this);
        this.viewProveedores.jbSearch.addActionListener(this);
        initView();
    }
    
    public void initView(){
        this.viewProveedores.setVisible(true);
        mostrarValues();
    }
    
    public void mostrarValues(){
        this.modelProveedores.initValues();
        this.viewProveedores.jtfCalle.setText(this.modelProveedores.getCalle());
        this.viewProveedores.jtfCity.setText(this.modelProveedores.getCiudad());
        this.viewProveedores.jtfColony.setText(this.modelProveedores.getColonia());
        this.viewProveedores.jtfContact.setText(this.modelProveedores.getContacto());
        this.viewProveedores.jtfEmail.setText(this.modelProveedores.getEmail());
        this.viewProveedores.jtfName.setText(this.modelProveedores.getNombre());
        this.viewProveedores.jtfNumber.setText(""+this.modelProveedores.getNo());
        this.viewProveedores.jtfRFC.setText(this.modelProveedores.getRfc());
        this.viewProveedores.jtfState.setText(this.modelProveedores.getEstado());
        this.viewProveedores.jtfTelephone.setText(this.modelProveedores.getTelefono());
    }
    
    public void llenarDatos(){
        this.modelProveedores.setCalle(this.viewProveedores.jtfCalle.getText());
        this.modelProveedores.setCiudad(this.viewProveedores.jtfCity.getText());
        this.modelProveedores.setColonia(this.viewProveedores.jtfColony.getText());
        this.modelProveedores.setContacto(this.viewProveedores.jtfContact.getText());
        this.modelProveedores.setEmail(this.viewProveedores.jtfEmail.getText());
        this.modelProveedores.setEstado(this.viewProveedores.jtfState.getText());
        this.modelProveedores.setNo(this.viewProveedores.jtfNumber.getText());
        this.modelProveedores.setNombre(this.viewProveedores.jtfName.getText());
        this.modelProveedores.setRfc(this.viewProveedores.jtfRFC.getText());
        this.modelProveedores.setTelefono(this.viewProveedores.jtfTelephone.getText());
    }
    
    public void conectar(){
        try{ 
            conexion=DriverManager.getConnection("jdbc:mysql://localhost/acme","root","1234");                     
            st=conexion.createStatement();
        }catch(SQLException err){
            JOptionPane.showMessageDialog(null,"Ocurrio un problema al conectar con la base de datos"); 
        }
    }
    private void borrarProv() {
        llenarDatos();
        conectar();
        try {
            st.executeUpdate(this.modelProveedores.deleteSql());
            JOptionPane.showMessageDialog(null, "Proveedor Borrado Exitosamente");
        } catch (SQLException ex) {
            Logger.getLogger(ControllerProveedores.class.getName()).log(Level.SEVERE, null, ex);
            JOptionPane.showMessageDialog(null, "HA HABIDO UN ERROR");
        }
    }
    
    private void guardarProv() {
        llenarDatos();
        conectar();
        try {
            String sql = this.modelProveedores.insertSql();
            st.executeUpdate(sql); 
            JOptionPane.showMessageDialog(null, "Proveedor Añadido Exitosamente");
        } catch (SQLException ex) {
            Logger.getLogger(ControllerProveedores.class.getName()).log(Level.SEVERE, null, ex);
            JOptionPane.showMessageDialog(null, "HA HABIDO UN ERROR");
        }
    }
    
    private void updateProv() {
        llenarDatos();
        conectar();
        try {
            st.executeUpdate(this.modelProveedores.updateSql()); 
            JOptionPane.showMessageDialog(null, "Proveedor Guardado Exitosamente");
        } catch (SQLException ex) {
            Logger.getLogger(ControllerProveedores.class.getName()).log(Level.SEVERE, null, ex);
            JOptionPane.showMessageDialog(null, "HA HABIDO UN ERROR");
        }
    }
    
    public void mostrarTablaProveedores(){
        try {
            DefaultTableModel model = new DefaultTableModel();
            conexion = DriverManager.getConnection("jdbc:mysql://localhost/acme","root","1234"); 
            model.addColumn("id");
            model.addColumn("Nombre");
            model.addColumn("RFC");
            model.addColumn("Calle");
            model.addColumn("No");
            model.addColumn("Colonia");
            model.addColumn("Ciudad");
            model.addColumn("Estado");
            model.addColumn("Contacto");
            model.addColumn("Telefono");
            model.addColumn("Email");
            this.viewProveedores.jtBusqueda.setModel(model);
            String datos[] = new String [11];                    
            st=conexion.createStatement();
            rs = st.executeQuery("SELECT * FROM proveedor");
            rsm = rs.getMetaData();
            while(rs.next()){
                datos[0]=rs.getString(1);
                datos[1]=rs.getString(2);
                datos[2]=rs.getString(3);
                datos[3]=rs.getString(4);
                datos[4]=rs.getString(5);
                datos[5]=rs.getString(6);
                datos[6]=rs.getString(7);
                datos[7]=rs.getString(8);
                datos[8]=rs.getString(9);
                datos[9]=rs.getString(10);
                datos[10]=rs.getString(11);
                model.addRow(datos);
            }
            this.viewProveedores.jtBusqueda.setModel(model);
        } catch (SQLException ex) {
            Logger.getLogger(ControllerProveedores.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }
    
    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource() == this.viewProveedores.jbAdd){
            guardarProv();
        } else if(e.getSource() == this.viewProveedores.jbDelete){
            borrarProv();
        } else if(e.getSource() == this.viewProveedores.jbEdit){
            updateProv();
        } else if(e.getSource() == this.viewProveedores.jbSearch){
            mostrarTablaProveedores();
        } else if(e.getSource() == this.viewProveedores.jbFirst){
            mostrarValues();
            this.modelProveedores.moveFirst();
        } else if(e.getSource() == this.viewProveedores.jbLast){
            mostrarValues();
            this.modelProveedores.moveLast();
        } else if(e.getSource() == this.viewProveedores.jbNext){
            mostrarValues();
            this.modelProveedores.moveNext();
        }else if(e.getSource() == this.viewProveedores.jbPrev){
             mostrarValues();
            this.modelProveedores.movePrevious();
        }
    }
    
}
