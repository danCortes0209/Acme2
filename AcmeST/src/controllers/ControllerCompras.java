/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JOptionPane;
import views.viewCompras;
import models.ModelCompras;

/**
 *
 * @author usuario
 */
public class ControllerCompras implements ActionListener {

    viewCompras viewCompras;
    ModelCompras modelCompras;

    public ControllerCompras(viewCompras viewCompras, ModelCompras modelCompras) {
        this.viewCompras = viewCompras;
        this.modelCompras = modelCompras;
        this.viewCompras.jbAdd.addActionListener(this);
        this.viewCompras.jbCancel.addActionListener(this);
        this.viewCompras.jbNew.addActionListener(this);
        this.viewCompras.jbBuscarProveedores.addActionListener(this);
        this.viewCompras.jbBuscarProductos.addActionListener(this);
        this.viewCompras.jbAddProduct.addActionListener(this);
        initView();
    }
    
    public void initView() {
        this.modelCompras.setActualDate();
        this.viewCompras.jbAddProduct.setEnabled(false);
        this.viewCompras.jbAddProduct.setVisible(false);
        this.viewCompras.jbCancel.setEnabled(false);
        this.viewCompras.jbCancel.setVisible(false);
        this.viewCompras.jtfFecha.setText(this.modelCompras.getFecha());
        this.viewCompras.jtfFecha.setEditable(false);
    }

    public void obtenerDatosDeInterfaz() {
        try {
            this.modelCompras.setCantidad(Integer.parseInt(this.viewCompras.jtfCantidad.getText()));
            this.modelCompras.setNumcliente(Integer.parseInt(this.viewCompras.jtfProveedor.getText()));
            this.modelCompras.setNumProducto(Integer.parseInt(this.viewCompras.jtfProducto.getText()));
        } catch (Exception err) {
            JOptionPane.showMessageDialog(null, "No hay producto seleccionado" + err);
        }
    }
    
    public void Limpiar() {
        this.viewCompras.jtfCantidad.setText("");
        this.viewCompras.jtfProveedor.setText("");
        this.viewCompras.jtfProducto.setText("");
    }
    
    public void guardarVenta() {
        if (this.viewCompras.jbAdd.getText().equals("Cobrar")) {
            JOptionPane.showMessageDialog(null, "Dar clic en guardar");
            this.viewCompras.jbAdd.setText("Guardar");
            this.viewCompras.jbAddProduct.setEnabled(true);
            this.viewCompras.jbAddProduct.setVisible(true);
            this.viewCompras.jbCancel.setEnabled(true);
            this.viewCompras.jbCancel.setVisible(true);
        } else if (this.viewCompras.jbAdd.getText().equals("Guardar")) {
            obtenerDatosDeInterfaz();
            this.modelCompras.addVenta();
            this.viewCompras.jbAdd.setText("Confirmar");
        } else if (this.viewCompras.jbAdd.getText().equals("Confirmar")){
            this.modelCompras.setCommit();
            this.viewCompras.jtCompra.setModel(this.modelCompras.tableCompras);
            this.viewCompras.jbAddProduct.setEnabled(false);
            this.viewCompras.jbAddProduct.setVisible(false);
            this.viewCompras.jbAdd.setText("Cobrar");
        }
    }
    
    public void tablaProductos() {
        String sql = "";
        if (this.viewCompras.jcbSpecificBrand.isSelected()) {
            String producto = JOptionPane.showInputDialog("Deme el nombre del producto", "");
            sql = "Select * from productos where producto ='" + producto + "'";
        } else {
            sql = "Select * from productos";
        }
        this.modelCompras.setTableProductos(sql);
        this.viewCompras.jtBuscaProductos.setModel(this.modelCompras.tableProductos);
    }

    public void tablaProveedores() {
        String sql = "";
        if (this.viewCompras.jcbSpecificProduct.isSelected()) {
            String prov = JOptionPane.showInputDialog("Deme el nombre del proveedor", "");
            sql = "Select * from proveedor where nombre ='" + prov + "'";
        } else {
            sql = "Select * from proveedor";
        }
        System.out.println("controllerCompras "+sql);
        this.modelCompras.setTableProveedores(sql);
        this.viewCompras.jtBuscaProveedores.setModel(this.modelCompras.tableProveedores);
    }
    
    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == this.viewCompras.jbAdd) {
            guardarVenta();
        } else if (e.getSource() == this.viewCompras.jbCancel) {
            this.modelCompras.setRollback();
        } else if (e.getSource() == this.viewCompras.jbNew) {
            Limpiar();
        } else if (e.getSource() == this.viewCompras.jbBuscarProveedores) {
            tablaProveedores();
        } else if (e.getSource() == this.viewCompras.jbBuscarProductos) {
            tablaProductos();
        } else if (e.getSource() == this.viewCompras.jbAddProduct){
            this.viewCompras.jtfProducto.setText("");
            obtenerDatosDeInterfaz();
            this.modelCompras.newProduct();
        }
    }
}
