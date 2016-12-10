/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import models.ModelLogin;
import views.ViewIniciarSesion;
import views.ViewMain;

/**
 *
 * @author L.A.M.M#13
 */
public class ControllerLogin implements ActionListener {

    ModelLogin modelLogin;
    ViewIniciarSesion viewIniciarSesion;
    ViewMain viewMain;
    
    public ControllerLogin(ModelLogin modelLogin, ViewIniciarSesion viewIniciarSesion, ViewMain viewMain) {
        this.modelLogin = modelLogin;
        this.viewMain = viewMain;
        this.viewIniciarSesion = viewIniciarSesion;
        this.viewIniciarSesion.jbtnIngresar.addActionListener(this);
        initValues();
    }
    
    public void initValues(){
        this.viewIniciarSesion.setVisible(true);
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
    
    public void enableAll() {
        this.viewMain.jMenuItemUsuario.setEnabled(true);
        this.viewMain.jMenuItemProductos.setEnabled(true);
        this.viewMain.jmiVentas.setEnabled(true);
        this.viewMain.jmiCliente.setEnabled(true);
        this.viewMain.jmiCompras.setEnabled(true);
        this.viewMain.jmiProveedores.setEnabled(true);
        this.viewMain.jmiReporteProductos.setEnabled(true);
        this.viewMain.jmiReporteVenta.setEnabled(true);
        this.viewMain.jmiReporteClientes.setEnabled(true);
        this.viewMain.jmiReporteProveedores.setEnabled(true);
        this.viewMain.jmiReporteCompras.setEnabled(true);
    }

    public void enableOnlyForSeller() {
        this.viewMain.jMenuItemUsuario.setEnabled(false);
        this.viewMain.jMenuItemProductos.setEnabled(true);
        this.viewMain.jmiCliente.setEnabled(true);
        this.viewMain.jmiCompras.setEnabled(true);
        this.viewMain.jmiProveedores.setEnabled(true);
        this.viewMain.jmiVentas.setEnabled(true);
        this.viewMain.jmiReporteProductos.setEnabled(true);
        this.viewMain.jmiReporteVenta.setEnabled(true);
        this.viewMain.jmiReporteClientes.setEnabled(true);
        this.viewMain.jmiReporteProveedores.setEnabled(true);
        this.viewMain.jmiReporteCompras.setEnabled(true);
    }
    
    public void conectar(){
        this.modelLogin.setUs(this.viewIniciarSesion.jtfUser.getText());
        this.modelLogin.setArrc(this.viewIniciarSesion.jPassword.getPassword());
        this.modelLogin.Entrar();
        if(this.modelLogin.inicio == true && this.modelLogin.admin == true && this.viewIniciarSesion.jbtnIngresar.getText().equals("Iniciar")){
            this.viewIniciarSesion.jbtnIngresar.setText("Salir");
            enableAll();
        } else if (this.modelLogin.inicio == true && this.modelLogin.admin == false  && this.viewIniciarSesion.jbtnIngresar.getText().equals("Iniciar"))  {
            this.viewIniciarSesion.jbtnIngresar.setText("Salir");
            enableOnlyForSeller();
        } else if (this.viewIniciarSesion.jbtnIngresar.getText().equals("Salir")) {
            this.modelLogin.admin = false;    //dewd
            this.modelLogin.inicio = false;
            this.viewIniciarSesion.jbtnIngresar.setText("Iniciar");
            disableAll();
        } 
    }
    
    @Override
    public void actionPerformed(ActionEvent ae) {
        if (ae.getSource() == this.viewIniciarSesion.jbtnIngresar) {
            conectar();
        }
    }
}
