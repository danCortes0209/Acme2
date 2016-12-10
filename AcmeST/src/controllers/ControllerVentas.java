/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JOptionPane;
import views.ViewVentas;
import models.ModelVentas;

/**
 *
 * @author usuario
 */
public class ControllerVentas implements ActionListener {

    ViewVentas viewVentas;
    ModelVentas modelVentas;

    public ControllerVentas(ViewVentas viewVentas, ModelVentas modelVentas) {
        this.viewVentas = viewVentas;
        this.modelVentas = modelVentas;
        this.viewVentas.jbAdd.addActionListener(this);
        this.viewVentas.jbCancel.addActionListener(this);
        this.viewVentas.jbNew.addActionListener(this);
        this.viewVentas.jbAddProduct.addActionListener(this);
        this.viewVentas.jbBuscarProductos.addActionListener(this);
        this.viewVentas.jbBuscarProveedores.addActionListener(this);
        initView();
    }

    public void obtenerDatosDeInterfaz() {
        try {
            this.modelVentas.setCantidad(Integer.parseInt(this.viewVentas.jtfCantidad.getText()));
            this.modelVentas.setNumcliente(Integer.parseInt(this.viewVentas.jtfCliente.getText()));
            this.modelVentas.setNumProducto(Integer.parseInt(this.viewVentas.jtfProducto.getText()));
        } catch (Exception err) {
            JOptionPane.showMessageDialog(null, "No hay producto seleccionado" + err);
        }
    }

    public void guardarVenta() {
        if (this.viewVentas.jbAdd.getText().equals("Cobrar")) {
            JOptionPane.showMessageDialog(null, "Dar clic en guardar");
            this.viewVentas.jbAdd.setText("Guardar");
            this.viewVentas.jbAddProduct.setEnabled(true);
            this.viewVentas.jbAddProduct.setVisible(true);
            this.viewVentas.jbCancel.setEnabled(true);
            this.viewVentas.jbCancel.setVisible(true);
        } else if (this.viewVentas.jbAdd.getText().equals("Guardar")) {
            obtenerDatosDeInterfaz();
            this.modelVentas.addVenta();
            this.viewVentas.jbAdd.setText("Confirmar");
        } else if (this.viewVentas.jbAdd.getText().equals("Confirmar")){
            this.modelVentas.setCommit();
            this.viewVentas.jtVentas.setModel(this.modelVentas.tableVentas);
            this.viewVentas.jbAddProduct.setEnabled(false);
            this.viewVentas.jbAddProduct.setVisible(false);
            this.viewVentas.jbAdd.setText("Cobrar");
        }
    }

    public void initView() {
        this.modelVentas.setActualDate();
        this.viewVentas.jbAddProduct.setEnabled(false);
        this.viewVentas.jbAddProduct.setVisible(false);
        this.viewVentas.jbCancel.setEnabled(false);
        this.viewVentas.jbCancel.setVisible(false);
        this.viewVentas.jtfFecha.setText(this.modelVentas.getFecha());
        this.viewVentas.jtfFecha.setEditable(false);
    }

    public void Limpiar() {
        this.viewVentas.jtfCantidad.setText("");
        this.viewVentas.jtfCliente.setText("");
        this.viewVentas.jtfProducto.setText("");
    }

    public void tablaProductos() {
        String sql = "";
        if (this.viewVentas.jcbSpecificBrand.isSelected()) {
            String producto = JOptionPane.showInputDialog("Deme el nombre del producto", "");
            sql = "Select * from productos where producto ='" + producto + "'";
        } else {
            sql = "Select * from productos";
        }
        this.modelVentas.setTableProductos(sql);
        this.viewVentas.jtBuscaProductos.setModel(this.modelVentas.tableProductos);
    }

    public void tablaClientes() {
        String sql = "";
        if (this.viewVentas.jcbSpecificCostumer.isSelected()) {
            String cliente = JOptionPane.showInputDialog("Deme el nombre del cliente", "");
            sql = "Select * from clientes where nombre ='" + cliente + "'";
        } else {
            sql = "Select * from clientes";
        }
        this.modelVentas.setTableClientes(sql);
        this.viewVentas.jtBuscaClientes.setModel(this.modelVentas.tableClientes);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == this.viewVentas.jbAdd) {
            guardarVenta();
        } else if (e.getSource() == this.viewVentas.jbNew) {
            Limpiar();
        } else if (e.getSource() == this.viewVentas.jbCancel) {
            this.modelVentas.setRollback();
        } else if (e.getSource() == this.viewVentas.jbBuscarProveedores) {
            tablaClientes();
        } else if (e.getSource() == this.viewVentas.jbBuscarProductos) {
            tablaProductos();
        } else if(e.getSource() == this.viewVentas.jbAddProduct){
            this.viewVentas.jtfProducto.setText("");
            obtenerDatosDeInterfaz();
            this.modelVentas.newProduct();
        }
    }
}
