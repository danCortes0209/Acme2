/*

 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import sax.DBConnection;
import models.ModelUsuario;
import views.ViewUsuario;

/**
 *
 * @author L.A.M.M#13
 */
public class ControllerUsuario implements ActionListener {
    Connection conexion;
    Statement st;
    ResultSet rs;
    ResultSetMetaData rsm;
    ModelUsuario modelUsuario;
    ViewUsuario viewUsuario;

    public ControllerUsuario(ModelUsuario modelUsuario, ViewUsuario viewUsuario) {
        this.modelUsuario = modelUsuario;
        this.viewUsuario = viewUsuario;
        this.viewUsuario.jbtnAgregar.addActionListener(this);
        this.viewUsuario.jbtnAnterior.addActionListener(this);
        this.viewUsuario.jbtnEditar.addActionListener(this);
        this.viewUsuario.jbtnEliminar.addActionListener(this);
        this.viewUsuario.jbtnSiguiente.addActionListener(this);
        this.viewUsuario.jbtnUltimo.addActionListener(this);
        this.viewUsuario.jbntPrimero.addActionListener(this);
        this.viewUsuario.jbtnNuevo.addActionListener(this);
        this.viewUsuario.jbBuscar.addActionListener(this);
        init_view();
    }

    public void init_view() {
        this.viewUsuario.setVisible(true);
        modelUsuario.initValues();
        showValues();
    }

    private void showValues() {
        this.viewUsuario.jtfId.setText("" + modelUsuario.getId_admin());
        this.viewUsuario.jPassword.setText("" + modelUsuario.getContrasena());
        this.viewUsuario.jtfUsuario.setText("" + modelUsuario.getUsuario());
        this.viewUsuario.jtfNombre.setText("" + modelUsuario.getNombre());
        this.viewUsuario.jtfAp_pat.setText("" + modelUsuario.getAp_pat());
        this.viewUsuario.jtfAp_mat.setText("" + modelUsuario.getAp_mat());
        this.viewUsuario.jComboBoxEstado.setSelectedItem("" + modelUsuario.getEstado());
        this.viewUsuario.jComboBoxNivel.setSelectedItem("" + modelUsuario.getNivel());
    }

    public void Guardar() {
        try {
            obtener();
            this.modelUsuario.guardar();
            showValues();
        } catch (Exception err) {
            JOptionPane.showMessageDialog(this.viewUsuario, "No hay usuario seleccionado");
        }

    }

    public void Primero() {
        modelUsuario.moveFirst();
        showValues();
    }

    public void Siguiente() {
        modelUsuario.moveNext();
        showValues();

    }

    public void Anterior() {
        modelUsuario.movePrevious();
        showValues();
    }

    public void ultimo() {
        modelUsuario.moveLast();
        showValues();
    }

    public void Eliminar() {
        try {
            this.modelUsuario.borrar();
            limpiar();
        } catch (Exception err) {
            JOptionPane.showMessageDialog(null, "No hay usuario seleccionado");
        }
    }

    public void Editar() {
        try {
            obtener();
            this.modelUsuario.editar();
            limpiar();
        } catch (Exception err) {
            JOptionPane.showMessageDialog(null, "No hay usuario seleccionado ");
        }
    }
    
    public void mostrarTablaUsuarios(){
        try {
            DefaultTableModel model = new DefaultTableModel();
            conexion = DriverManager.getConnection("jdbc:mysql://localhost/acme","root","1234"); 
            model.addColumn("id");
            model.addColumn("Nombre");
            model.addColumn("ApM");
            model.addColumn("ApP");
            model.addColumn("Usuario");
            model.addColumn("Contraseña");
            model.addColumn("Nivel");
            model.addColumn("Estado");
            this.viewUsuario.jtUsuario.setModel(model);
            String datos[] = new String [8];     
            String sql = "";
            if (this.viewUsuario.jrbSpecific.isSelected()){
                String nombre = JOptionPane.showInputDialog(null, "dame el nombre del usuario","");
                sql = "SELECT * FROM users WHERE nombre='"+nombre+"'";
            } else {
                sql = "SELECT * FROM users";
            }
            st=conexion.createStatement();
            rs = st.executeQuery(sql);
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
                model.addRow(datos);
            }
            this.viewUsuario.jtUsuario.setModel(model);
        } catch (SQLException ex) {
            Logger.getLogger(ControllerUsuario.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }

    public void limpiar() {
        this.viewUsuario.jtfId.setText("");
        this.viewUsuario.jPassword.setText("");
        this.viewUsuario.jtfUsuario.setText("");
        this.viewUsuario.jtfNombre.setText("");
        this.viewUsuario.jtfAp_pat.setText("");
        this.viewUsuario.jtfAp_mat.setText("");
        this.viewUsuario.jComboBoxEstado.setSelectedItem("");
        this.viewUsuario.jComboBoxNivel.setSelectedItem("");
    }

    public void obtener(){
        this.modelUsuario.setNombre(this.viewUsuario.jtfNombre.getText());
        this.modelUsuario.setAp_pat(this.viewUsuario.jtfAp_pat.getText());
        this.modelUsuario.setAp_mat(""+this.viewUsuario.jtfAp_mat.getText());
        this.modelUsuario.setUsuario(this.viewUsuario.jtfUsuario.getText());
        this.modelUsuario.setContrasena(this.viewUsuario.jPassword.getText());
        this.modelUsuario.setEstado(""+this.viewUsuario.jComboBoxEstado.getSelectedItem());
        this.modelUsuario.setNivel(""+this.viewUsuario.jComboBoxNivel.getSelectedItem());
    }
    
    @Override
    public void actionPerformed(ActionEvent ae) {
        if (ae.getSource() == this.viewUsuario.jbtnAgregar) {
            Guardar();
        } else if (ae.getSource() == this.viewUsuario.jbtnAnterior) {
            Anterior();
        } else if (ae.getSource() == this.viewUsuario.jbtnEditar) {
            Editar();
        } else if (ae.getSource() == this.viewUsuario.jbtnEliminar) {
            Eliminar();
        } else if (ae.getSource() == this.viewUsuario.jbtnSiguiente) {
            Siguiente();
        } else if (ae.getSource() == this.viewUsuario.jbtnUltimo) {
            ultimo();
        } else if (ae.getSource() == this.viewUsuario.jbntPrimero) {
            Primero();
        } else if (ae.getSource() == this.viewUsuario.jbtnNuevo) {
            limpiar();
        } else if (ae.getSource() == this.viewUsuario.jbBuscar){
            mostrarTablaUsuarios();
        }
    }
}
