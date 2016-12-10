/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.util.JRLoader;
import net.sf.jasperreports.view.JasperViewer;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import views.*;
import models.*;
/**
 *
 * @author usuario
 */
public class ControllerMain implements ActionListener {

    ModelMain modelMain;
    ViewMain viewMain;
    Object modules[];

    Connection cn;
    
    ControllerProductos controllerProducto;
    ControllerLogin controllerLogin;
    ControllerUsuario controllerUsuario;
    ControllerVentas controllerVenta;
    ControllerClientes controllerCliente;
    ControllerCompras controllerCompra;
    ControllerProveedores controllerProveedores;

    public ControllerMain(ModelMain modelMain, ViewMain viewMain, Object modules[]) {
        this.viewMain = viewMain;
        this.modelMain = modelMain;
        this.modules = modules;
        controllerProducto = (ControllerProductos) modules[0];
        controllerUsuario = (ControllerUsuario) modules[1];
        controllerLogin = (ControllerLogin) modules[2];
        controllerVenta = (ControllerVentas) modules[3];
        controllerCliente = (ControllerClientes) modules[4];
        controllerCompra = (ControllerCompras) modules[5];
        controllerProveedores = (ControllerProveedores) modules[6];
        this.controllerLogin.viewIniciarSesion.jbtnIngresar.addActionListener(this);
        this.viewMain.jMenuItemProductos.addActionListener(this);
        this.viewMain.jMenuItemIniciar.addActionListener(this);
        this.viewMain.jMenuItemUsuario.addActionListener(this);
        this.viewMain.jmiVentas.addActionListener(this);
        this.viewMain.jmiCliente.addActionListener(this);
        this.viewMain.jmiCompras.addActionListener(this);
        this.viewMain.jmiProveedores.addActionListener(this);
        this.viewMain.jmiReporteClientes.addActionListener(this);
        this.viewMain.jmiReporteVenta.addActionListener(this);
        this.viewMain.jmiReporteCompras.addActionListener(this);
        this.viewMain.jmiReporteProveedores.addActionListener(this);
        this.viewMain.jmiReporteProductos.addActionListener(this);
        init_view();
    }

    public void init_view() {
        this.viewMain.setTitle("Acme");
        this.viewMain.setLocationRelativeTo(null);
        this.viewMain.setVisible(true);
        disableAll();
    }

    public void jmiProductosActionPerfomed() {
        this.viewMain.setContentPane(controllerProducto.viewProductos);
        this.viewMain.revalidate();
        this.viewMain.repaint();

    }

    public void jmiUsuarioActionPerfomed() {
        this.viewMain.setContentPane(controllerUsuario.viewUsuario);
        this.viewMain.revalidate();
        this.viewMain.repaint();

    }

    public void jmiVentaActionPerfomed() {
        this.viewMain.setContentPane(controllerVenta.viewVentas);
        this.viewMain.revalidate();
        this.viewMain.repaint();

    }

    public void jmiCompraActionPerfomed() {
        this.viewMain.setContentPane(controllerCompra.viewCompras);
        this.viewMain.revalidate();
        this.viewMain.repaint();

    }

    public void jmiClienteActionPerfomed() {
        this.viewMain.setContentPane(controllerCliente.viewClientes);
        this.viewMain.revalidate();
        this.viewMain.repaint();

    }

    public void jmiProveedoresActionPerformed() {
        this.viewMain.setContentPane(controllerProveedores.viewProveedores);
        this.viewMain.revalidate();
        this.viewMain.repaint();
    }

    public void jmiInicioSesionActionPerformed() {
        this.viewMain.setContentPane(controllerLogin.viewIniciarSesion);
        this.viewMain.revalidate();
        this.viewMain.repaint();
    }

    public void disableAll() {
        this.viewMain.jMenuItemUsuario.setEnabled(false);
        this.viewMain.jMenuItemProductos.setEnabled(false);
        this.viewMain.jmiCliente.setEnabled(false);
        this.viewMain.jmiCompras.setEnabled(false);
        this.viewMain.jmiProveedores.setEnabled(false);
        this.viewMain.jmiVentas.setEnabled(false);
        this.viewMain.jmiReporteProductos.setEnabled(false);
        this.viewMain.jmiReporteVenta.setEnabled(false);
        this.viewMain.jmiReporteClientes.setEnabled(false);
        this.viewMain.jmiReporteProveedores.setEnabled(false);
        this.viewMain.jmiReporteCompras.setEnabled(false);
    }
    
    public void crearReporteVentas(){
        try {
            conectarADataBase();
            JasperReport jr = (JasperReport) JRLoader.loadObjectFromFile("D:\\Acme\\src\\reports\\reporteVenta.jasper");
            JasperPrint jp =  JasperFillManager.fillReport(jr, null,cn);
            JasperViewer jv = new JasperViewer(jp);
            jv.setVisible(true);
        } catch (JRException ex) {
            Logger.getLogger(ControllerMain.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void crearReporteCompras(){
        try {
            conectarADataBase();
            JasperReport jr = (JasperReport) JRLoader.loadObjectFromFile("D:\\Acme\\src\\reports\\reporteCompras.jasper");
            JasperPrint jp =  JasperFillManager.fillReport(jr, null,cn);
            JasperViewer jv = new JasperViewer(jp);
            jv.setVisible(true);
        } catch (JRException ex) {
            Logger.getLogger(ControllerMain.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void crearReporteProductos(){
        try {
            conectarADataBase();
            JasperReport jr = (JasperReport) JRLoader.loadObjectFromFile("D:\\Acme\\src\\reports\\reporteProductos.jasper");
            JasperPrint jp =  JasperFillManager.fillReport(jr, null,cn);
            JasperViewer jv = new JasperViewer(jp);
            jv.setVisible(true);
        } catch (JRException ex) {
            Logger.getLogger(ControllerMain.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void crearReporteProveedores(){
        try {
            conectarADataBase();
            JasperReport jr = (JasperReport) JRLoader.loadObjectFromFile("D:\\Acme\\src\\reports\\reporteProveedores.jasper");
            JasperPrint jp =  JasperFillManager.fillReport(jr, null,cn);
            JasperViewer jv = new JasperViewer(jp);
            jv.setVisible(true);
        } catch (JRException ex) {
            Logger.getLogger(ControllerMain.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void crearReporteClientes(){
        try {
            conectarADataBase();
            JasperReport jr = (JasperReport) JRLoader.loadObjectFromFile("D:\\Acme\\src\\reports\\reporteClientes.jasper");
            JasperPrint jp =  JasperFillManager.fillReport(jr, null,cn);
            JasperViewer jv = new JasperViewer(jp);
            jv.setVisible(true);
        } catch (JRException ex) {
            Logger.getLogger(ControllerMain.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void conectarADataBase(){
        try {
            cn = DriverManager.getConnection("jdbc:mysql://localhost/acme","root","1234");
        } catch (SQLException ex) {
            Logger.getLogger(ControllerMain.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void actionPerformed(ActionEvent ae) {
        if (ae.getSource() == this.viewMain.jMenuItemProductos) {
            jmiProductosActionPerfomed();
        } else if (ae.getSource() == this.viewMain.jMenuItemIniciar) {
            jmiInicioSesionActionPerformed();
        } else if (ae.getSource() == this.viewMain.jMenuItemUsuario) {
            jmiUsuarioActionPerfomed();
        } else if (ae.getSource() == this.viewMain.jmiVentas) {
            jmiVentaActionPerfomed();
        } else if (ae.getSource() == this.viewMain.jmiCompras) {
            jmiCompraActionPerfomed();
        } else if (ae.getSource() == this.viewMain.jmiCliente) {
            jmiClienteActionPerfomed();
        } else if (ae.getSource() == this.viewMain.jmiProveedores) {
            jmiProveedoresActionPerformed();
        } else if (ae.getSource() == this.viewMain.jmiReporteCompras){
            crearReporteCompras();
        }else if (ae.getSource() == this.viewMain.jmiReporteVenta){
            crearReporteVentas();
        }else if (ae.getSource() == this.viewMain.jmiReporteClientes){
            crearReporteClientes();
        }else if (ae.getSource() == this.viewMain.jmiReporteProductos){
            crearReporteProductos();
        }else if (ae.getSource() == this.viewMain.jmiReporteProveedores){
            crearReporteProveedores();
        }
    }
}