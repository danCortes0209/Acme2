/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers;
import sax.DBConnection;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection; 
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet; 
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement; 
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import java.util.logging.Level;
import java.util.logging.Logger;
import models.ModelProductos;
import views.ViewProductos;

/**
 *
 * @author usuario
 */
public class ControllerProductos implements ActionListener{
    private DBConnection conection = new DBConnection(3306, "localhost", "acme", "root", "1234");
    ModelProductos modelProductos;
    ViewProductos viewProductos;
    PreparedStatement ps;
    ResultSetMetaData rsm;
    DefaultTableModel dtm;
    Connection cn;
    ResultSet rs;
    Statement s;
    public ControllerProductos(ModelProductos modelProductos, ViewProductos viewProductos) {
        this.modelProductos = modelProductos;
        this.viewProductos = viewProductos;
        this.viewProductos.jbtnAgregar.addActionListener(this);
        this.viewProductos.jbtnBusacr.addActionListener(this);
        this.viewProductos.jbtnEditar.addActionListener(this);
        this.viewProductos.jbtnEliminar.addActionListener(this);
        this.viewProductos.jbtnPrimero.addActionListener(this);
        this.viewProductos.jbtnSiguiente.addActionListener(this);
        this.viewProductos.jbtnUltimo.addActionListener(this);
        this.viewProductos.jbtnNuevo.addActionListener(this);
        this.viewProductos.jbtnAnter.addActionListener(this);
        this.viewProductos.setVisible(true);
        init_view();
    }

    public void init_view() {
        modelProductos.initValues();
        showValues();
    }

    private void showValues() {
        viewProductos.jtfProducto.setText("" + modelProductos.getProducto());
        viewProductos.jtfPrecioC.setText(modelProductos.getPrecioCompra());
        viewProductos.jtfPrecioV.setText(modelProductos.getPrecioVenta());
        viewProductos.jTextAreaDescripción.setText(modelProductos.getDescripcion());
        viewProductos.jtfExistencia.setText(modelProductos.getExistencia());
    }


    public void bloquearDesbloquear(boolean todos) {
        this.viewProductos.jbtnAgregar.setEnabled(todos);
        this.viewProductos.jbtnAnter.setEnabled(todos);
        this.viewProductos.jbtnBusacr.setEnabled(todos);
        this.viewProductos.jbtnEditar.setEnabled(todos);
        this.viewProductos.jbtnEliminar.setEnabled(!todos);
        this.viewProductos.jbtnPrimero.setEnabled(!todos);
        this.viewProductos.jbtnSiguiente.setEnabled(todos);
        this.viewProductos.jbtnUltimo.setEnabled(todos);
    }

    public void Guardar() {
        try {
            String producto = this.viewProductos.jtfProducto.getText();
            String descripción = this.viewProductos.jTextAreaDescripción.getText();
            String precioCompra = this.viewProductos.jtfPrecioC.getText();
            String precioVenta = this.viewProductos.jtfPrecioV.getText();
            String existencia = this.viewProductos.jtfExistencia.getText();
            String sql = "insert into productos(producto,descripción,precio_compra,precio_venta,existencia) values (" + "'" + producto + "','" + descripción + "','" + precioCompra + "','" + precioVenta + "','" + existencia + "');";
            System.out.println("Nombre " + producto);
            System.out.println("SQL " + sql);
            conection.executeUpdate(sql);
            conection.executeQuery("Select * from productos");
            Primero();
            showValues();
        } catch (Exception err) {
            JOptionPane.showMessageDialog(this.viewProductos, "No hay producto seleccionado");
        }
    }

    public void Primero() {
        modelProductos.moveFirst();
        showValues();
    }

    public void Siguiente() {
        modelProductos.moveNext();
        showValues();

    }

    public void Anterior() {
        modelProductos.movePrevious();
        showValues();
    }

    public void Eliminar() {
        try {
            String idproducto = JOptionPane.showInputDialog("Dame el id del producto","");
            conection.executeUpdate("delete from productos where id_producto ='"+idproducto+"'");
            conection.executeQuery("Select * from productos order by id_producto");
            this.viewProductos.jtfProducto.setText("");
            this.viewProductos.jTextAreaDescripción.setText("");
            this.viewProductos.jtfPrecioC.setText("");
            this.viewProductos.jtfPrecioV.setText("");
            this.viewProductos.jtfExistencia.setText("");
            //this.viewProductos.JlImagen.setIcon(null);
        } catch (Exception err) {
            JOptionPane.showMessageDialog(null, "No hay producto seleccionado");
        }
    }

    public void Editar() {
        String producto = this.viewProductos.jtfProducto.getText();
        //String id_producto=this.viewProductos.jtfId.getText();
        String descripción = this.viewProductos.jTextAreaDescripción.getText();
        String precioCompra = this.viewProductos.jtfPrecioC.getText();
        String precioVenta = this.viewProductos.jtfPrecioV.getText();
        String existencia = this.viewProductos.jtfExistencia.getText();
        try {
            //modelProductos.editar();
            String idproducto = JOptionPane.showInputDialog("Dame el id del producto","");
            conection.executeUpdate("update productos set producto='" + producto + "',descripción='" + descripción + "',precio_compra='" + precioCompra + "',precio_venta='" + precioVenta + "',existencia='" + existencia + "' where id_producto='" + idproducto + "';");
            this.viewProductos.jtfProducto.setText("");
            this.viewProductos.jTextAreaDescripción.setText("");
            this.viewProductos.jtfPrecioC.setText("");
            this.viewProductos.jtfPrecioV.setText("");
            this.viewProductos.jtfExistencia.setText("");
            //this.rs.first();   
        } catch (Exception err) {
            JOptionPane.showMessageDialog(null, "No hay producto seleccionado ");
        }
    }

    public void Ultimo() {
        modelProductos.moveLast();
        showValues();
    }

    public void Nuevo() {
        this.viewProductos.jtfProducto.setText("");
        this.viewProductos.jTextAreaDescripción.setText("");
        this.viewProductos.jtfPrecioC.setText("");
        this.viewProductos.jtfPrecioV.setText("");
        this.viewProductos.jtfExistencia.setText("");
        //this.viewProductos.JlImagen.setIcon(null);
    }

    public void Tabla() {
        try {
            DefaultTableModel modelo = new DefaultTableModel();
            cn = DriverManager.getConnection("jdbc:mysql://localhost/acme", "root", "1234");
            modelo.addColumn("Id");
            modelo.addColumn("Nombre");
            modelo.addColumn("Descripcion");
            modelo.addColumn("P_compra");
            modelo.addColumn("P_venta");
            modelo.addColumn("Existencias");
            this.viewProductos.jTable1.setModel(modelo);
            String datos[] = new String[6];
            String sql = "";
            if (this.viewProductos.jcbSpecific.isSelected()){
                String producto = JOptionPane.showInputDialog("Deme el nombre del producto","");
                sql = "Select * from productos where producto ='"+producto+"'"; 
            } else {
                sql = "Select * from productos";
            }
            s = cn.createStatement();
            rs = s.executeQuery(sql);
            rsm = rs.getMetaData();
            while (rs.next()) {
                datos[0] = rs.getString(1);
                datos[1] = rs.getString(2);
                datos[2] = rs.getString(3);
                datos[3] = rs.getString(4);
                datos[4] = rs.getString(5);
                datos[5] = rs.getString(6);
                modelo.addRow(datos);
            }
            this.viewProductos.jTable1.setModel(modelo);
        } catch (SQLException ex) {
            Logger.getLogger(ControllerProductos.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    public void actionPerformed(ActionEvent ae) {
        if (ae.getSource() == this.viewProductos.jbtnAgregar) {
            Guardar();
        } else if (ae.getSource() == this.viewProductos.jbtnSiguiente) {
            Siguiente();
        } else if (ae.getSource() == this.viewProductos.jbtnAnter) {
            Anterior();
        } else if (ae.getSource() == this.viewProductos.jbtnEditar) {
            Editar();
        } else if (ae.getSource() == this.viewProductos.jbtnEliminar) {
            Eliminar();
        } else if (ae.getSource() == this.viewProductos.jbtnUltimo) {
            Ultimo();
        } else if (ae.getSource() == this.viewProductos.jbtnPrimero) {
            Primero();
        } else if (ae.getSource() == this.viewProductos.jbtnNuevo) {
            Nuevo();
        } else if (ae.getSource() == this.viewProductos.jbtnBusacr){
            Tabla();
        }
    }
}