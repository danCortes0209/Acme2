/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package main;
import models.*;
import views.*;
import controllers.*;
import javax.swing.JPanel;
/** 
 *
 * @author usuario
 */
public class Main {
    public static void main(String[] ODST) {
        Object modules[] = new Object[7];
        ViewProductos viewProductos = new ViewProductos();
        ModelProductos modelProductos = new ModelProductos();
        ControllerProductos controllerProducto = new ControllerProductos (modelProductos, viewProductos);
        
        ViewMain viewMain = new ViewMain();
        ViewIniciarSesion viewIniciarSecion = new ViewIniciarSesion();
        ModelLogin modelLogin = new ModelLogin();
        ControllerLogin controllerLogin = new ControllerLogin(modelLogin, viewIniciarSecion, viewMain);

        ModelUsuario modelUsuario = new ModelUsuario();
        ViewUsuario viewUsuario = new ViewUsuario();
        ControllerUsuario controllerUsuario = new ControllerUsuario(modelUsuario, viewUsuario);

        ModelVentas modelVentas = new ModelVentas();
        ViewVentas viewVenta = new ViewVentas();
        ControllerVentas controllerVenta = new ControllerVentas(viewVenta, modelVentas);

        ModelClientes modelClientes = new ModelClientes();
        ViewClientes viewClient = new ViewClientes();
        ControllerClientes controllerClientes = new ControllerClientes(modelClientes, viewClient);
        
        ModelCompras modelCompras = new ModelCompras();
        viewCompras viewCompras = new viewCompras();
        ControllerCompras controllerCompra = new ControllerCompras(viewCompras, modelCompras);
        
        ModelProveedores modelProveedores = new ModelProveedores();
        ViewProveedores viewProveedores = new ViewProveedores();
        ControllerProveedores controllerProveedores = new ControllerProveedores(modelProveedores, viewProveedores);
        
        modules[0] = controllerProducto;
        modules[1] = controllerUsuario;
        modules[2] = controllerLogin;
        modules[3] = controllerVenta;
        modules[4] = controllerClientes;
        modules[5] = controllerCompra;
        modules[6] = controllerProveedores;
        
        ModelMain modelMain = new ModelMain();
        ControllerMain controllerMain = new ControllerMain(modelMain, viewMain, modules);
        controllerMain.init_view();
    }
}
