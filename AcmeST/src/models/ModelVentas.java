/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package models;

import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import javax.swing.table.DefaultTableModel;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;

/**
 *
 * @author usuario
 */
public class ModelVentas {

    Connection cn;
    PreparedStatement ps;
    Statement s;
    ResultSet rs;
    public DefaultTableModel tableClientes = new DefaultTableModel();
    public DefaultTableModel tableProductos = new DefaultTableModel();
    public DefaultTableModel tableVentas = new DefaultTableModel();

    private int cantidad;
    private int numcliente;
    private int numProducto;
    private String fecha;

    public float precio;
    public int existencia;
    public int nuevaExistencia;
    public int numVenta;
    public float totalPrecProd;
    public float subtotal;
    public float total;

    public int getNumcliente() {
        return numcliente;
    }

    public void setNumcliente(int numcliente) {
        this.numcliente = numcliente;
    }

    public int getNumProducto() {
        return numProducto;
    }

    public void setNumProducto(int numProducto) {
        this.numProducto = numProducto;
    }

    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public void setActualDate() {
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.DATE, 1);
        SimpleDateFormat formato1 = new SimpleDateFormat("yyyy-MM-dd");
        this.fecha = formato1.format(cal.getTime());
    }

    public void getConnection() {
        try {
            cn = DriverManager.getConnection("jdbc:mysql://localhost/acme", "root", "1234");
            s = cn.createStatement();
            cn.setAutoCommit(false);
        } catch (SQLException ex) {
            Logger.getLogger(ModelVentas.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void addVenta() {
        try {
            getConnection();
            rs = s.executeQuery("SELECT id_venta FROM venta");
            while (rs.next()) {
                numVenta = rs.getInt("id_venta");
            }
            numVenta = numVenta + 1;
            System.out.println(""+numVenta);
            addDetalleVenta();
            subtotal = totalPrecProd;
            total = (float) (subtotal * 1.16);
            s.executeUpdate("INSERT INTO venta(fecha,id_cliente,subtotal,iva,total) VALUES('"+this.fecha+"','"+numcliente+"','"+subtotal+"','16','"+total+"')");
        } catch (SQLException ex) {
            Logger.getLogger(ModelVentas.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void addDetalleVenta() {
        try {
            getConnection();
            updateStock();
            rs = s.executeQuery("SELECT precio_venta FROM productos WHERE id_producto='" + numProducto + "'");
            if (rs.next()) {
                precio = rs.getFloat("precio_venta");
            }
            totalPrecProd = precio * cantidad;
            s.executeUpdate("INSERT INTO detalle_venta(id_venta,id_producto,cantidad,total_producto,precio) VALUES('" + numVenta + "','" + numProducto + "','" + cantidad + "','" + totalPrecProd + "','" + precio + "')");
        } catch (SQLException ex) {
            Logger.getLogger(ModelVentas.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void newProduct() {
        try {
            getConnection();
            updateStock();
            rs = s.executeQuery("SELECT precio_venta FROM productos WHERE id_producto='" + numProducto + "'");
            if (rs.next()) {
                precio = rs.getFloat("precio_venta");
            }
            totalPrecProd = precio * cantidad;
            s.executeUpdate("INSERT INTO detalle_venta(id_venta,id_producto,cantidad,total_producto,precio) VALUES('" + numVenta + "','" + numProducto + "','" + cantidad + "','" + totalPrecProd + "','" + precio + "')");
            rs = s.executeQuery("SELECT total FROM ventas WHERE id_venta='" + numVenta + "'");
            if (rs.next()){
                subtotal = rs.getFloat("total");
            }
            total = (float) ((totalPrecProd * 1.16) + subtotal);
            s.executeUpdate("UPDATE ventas SET total='" + total + "' WHERE id_venta='" + numVenta + "'");
        } catch (SQLException ex) {
            Logger.getLogger(ModelVentas.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void updateStock() {
        try {
            getConnection();
            rs = s.executeQuery("SELECT existencia FROM productos WHERE id_producto='" + numProducto + "'");
            if (rs.next()) {
                existencia = rs.getInt("existencia");
            }
            if (cantidad > existencia) {
                JOptionPane.showMessageDialog(null, "No hay suficientes productos en stock");
            } else if (existencia > cantidad) {
                nuevaExistencia = existencia - cantidad;
                s.executeUpdate("UPDATE productos SET existencia='" + nuevaExistencia + "' WHERE id_producto='" + numProducto + "'");
            }
        } catch (SQLException ex) {
            Logger.getLogger(ModelVentas.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void setTableClientes(String sql) {
        try {
            getConnection();
            tableClientes.addColumn("id");
            tableClientes.addColumn("nombre");
            tableClientes.addColumn("APM");
            tableClientes.addColumn("APP");
            tableClientes.addColumn("telefono");
            tableClientes.addColumn("email");
            tableClientes.addColumn("rfc");
            tableClientes.addColumn("calle");
            tableClientes.addColumn("numero");
            tableClientes.addColumn("colonia");
            tableClientes.addColumn("ciudad");
            tableClientes.addColumn("estado");
            String datos[] = new String[12];
            rs = s.executeQuery(sql);
            while (rs.next()) {
                datos[0] = rs.getString(1);
                datos[1] = rs.getString(2);
                datos[2] = rs.getString(3);
                datos[3] = rs.getString(4);
                datos[4] = rs.getString(5);
                datos[5] = rs.getString(6);
                datos[6] = rs.getString(7);
                datos[7] = rs.getString(8);
                datos[8] = rs.getString(9);
                datos[9] = rs.getString(10);
                datos[10] = rs.getString(11);
                datos[11] = rs.getString(12);
                tableClientes.addRow(datos);
            }
        } catch (SQLException ex) {
            Logger.getLogger(ModelVentas.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void setTableProductos(String sql) {
        try {
            getConnection();
            tableProductos.addColumn("Id");
            tableProductos.addColumn("Nombre");
            tableProductos.addColumn("Descripcion");
            tableProductos.addColumn("P_compra");
            tableProductos.addColumn("P_venta");
            tableProductos.addColumn("Existencias");
            String datos[] = new String[6];
            rs = s.executeQuery(sql);
            while (rs.next()) {
                datos[0] = rs.getString(1);
                datos[1] = rs.getString(2);
                datos[2] = rs.getString(3);
                datos[3] = rs.getString(4);
                datos[4] = rs.getString(5);
                datos[5] = rs.getString(6);
                tableProductos.addRow(datos);
            }
        } catch (SQLException ex) {
            Logger.getLogger(ModelVentas.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void setTableVentas() {
        try {
            getConnection();
            tableVentas.addColumn("venta");
            tableVentas.addColumn("fecha");
            tableVentas.addColumn("cliente");
            tableVentas.addColumn("producto");
            tableVentas.addColumn("cantidad");
            tableVentas.addColumn("precio");
            tableVentas.addColumn("total");
            String datos[] = new String[7];
            s = cn.createStatement();
            rs = s.executeQuery("SELECT ventas.id_venta,ventas.fecha,cliente.nombre,"
                    + "productos.producto,detalle_venta.cantidad,detalle_venta.precio,"
                    + "ventas.total FROM cliente, ventas, detalle_venta, productos WHERE "
                    + "cliente.id_cliente = ventas.id_cliente AND ventas.id_venta = detalle_venta.id_venta "
                    + "AND productos.id_producto = detalle_venta.id_producto AND ventas.id_venta='" + numVenta + "'");
            while (rs.next()) {
                datos[0] = rs.getString(1);
                datos[1] = rs.getString(2);
                datos[2] = rs.getString(3);
                datos[3] = rs.getString(4);
                datos[4] = rs.getString(5);
                datos[5] = rs.getString(6);
                datos[6] = rs.getString(7);
                tableVentas.addRow(datos);
            }
        } catch (SQLException ex) {
            Logger.getLogger(ModelVentas.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void setCommit() {
        try {
            getConnection();
            cn.commit();
        } catch (SQLException ex) {
            Logger.getLogger(ModelVentas.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void setRollback() {
        try {
            getConnection();
            cn.rollback();
        } catch (SQLException ex) {
            Logger.getLogger(ModelVentas.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
