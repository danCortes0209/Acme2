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
public class ModelCompras {

    Connection cn;
    PreparedStatement ps;
    Statement s;
    ResultSet rs;
    public DefaultTableModel tableProveedores = new DefaultTableModel();
    public DefaultTableModel tableProductos = new DefaultTableModel();
    public DefaultTableModel tableCompras = new DefaultTableModel();

    private int cantidad;
    private int numProveedor;
    private int numProducto;
    private String fecha;

    public float precio;
    public int existencia;
    public int nuevaExistencia;
    public int numCompra;
    public float totalPrecProd;
    public float subtotal;
    public float total;

    public int getNumcliente() {
        return numProveedor;
    }

    public void setNumcliente(int numProveedor) {
        this.numProveedor = numProveedor;
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
            rs = s.executeQuery("SELECT id_compra FROM compras");
            while (rs.next()) {
                numCompra = rs.getInt("id_compra");
            }
            numCompra = numCompra + 1;
            System.out.println(""+numCompra);
            addDetalleVenta();
            subtotal = totalPrecProd;
            total = (float) (subtotal * 1.16);
            s.executeUpdate("INSERT INTO compras(fecha,id_proveedor,subtotal,iva,total) VALUES('" + this.fecha + "','" + numProveedor + "','" + subtotal + "','16','" + total + "')");
        } catch (SQLException ex) {
            Logger.getLogger(ModelVentas.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void addDetalleVenta() {
        try {
            getConnection();
            updateStock();
            rs = s.executeQuery("SELECT precio_compra FROM productos WHERE id_producto='" + numProducto + "'");
            if (rs.next()) {
                precio = rs.getFloat("precio_compra");
            }
            totalPrecProd = precio * cantidad;
            s.executeUpdate("INSERT INTO detalle_compra(id_compra,id_producto,cantidad,total_producto,precio) VALUES('" + numCompra + "','" + numProducto + "','" + cantidad + "','" + totalPrecProd + "','" + precio + "')");
        } catch (SQLException ex) {
            Logger.getLogger(ModelVentas.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void newProduct() {
        try {
            getConnection();
            addDetalleVenta();
            rs = s.executeQuery("SELECT total FROM ventas WHERE id_venta='" + numCompra + "'");
            if (rs.next()){
                subtotal = rs.getFloat("total");
            }
            total = (float) ((totalPrecProd * 1.16) + subtotal);
            s.executeUpdate("UPDATE ventas SET total='" + total + "' WHERE id_venta='" + numCompra + "'");
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
            nuevaExistencia = existencia + cantidad;
            s.executeUpdate("UPDATE productos SET existencia='" + nuevaExistencia + "' WHERE id_producto='" + numProducto + "'");
        } catch (SQLException ex) {
            Logger.getLogger(ModelVentas.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void setTableProveedores(String sql) {
        try {
            System.out.println("modelcompras "+ sql);
            getConnection();
            tableProveedores.addColumn("id");
            tableProveedores.addColumn("nombre");
            tableProveedores.addColumn("rfc");
            tableProveedores.addColumn("calle");
            tableProveedores.addColumn("no");
            tableProveedores.addColumn("colonia");
            tableProveedores.addColumn("ciudad");
            tableProveedores.addColumn("estado");
            tableProveedores.addColumn("contacto");
            tableProveedores.addColumn("telefono");
            tableProveedores.addColumn("email");
            String datos[] = new String[11];
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
                tableProveedores.addRow(datos);
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
            tableCompras.addColumn("venta");
            tableCompras.addColumn("fecha");
            tableCompras.addColumn("cliente");
            tableCompras.addColumn("producto");
            tableCompras.addColumn("cantidad");
            tableCompras.addColumn("precio");
            tableCompras.addColumn("total");
            String datos[] = new String[7];
            s = cn.createStatement();
            rs = s.executeQuery("SELECT compras.id_compra,compras.fecha,proveedores.nombre,"
                    + "productos.producto,detalle_compra.cantidad,detalle_compra.precio,"
                    + "compras.total FROM compras, proveedores, detalle_compra, productos WHERE "
                    + "proveedores.id_proveedor = compras.id_proveedor AND compras.id_compra = detalle_compra.id_compra "
                    + "AND productos.id_producto = detalle_compra.id_producto AND compras.id_compra='" + numCompra + "'");
            while (rs.next()) {
                datos[0] = rs.getString(1);
                datos[1] = rs.getString(2);
                datos[2] = rs.getString(3);
                datos[3] = rs.getString(4);
                datos[4] = rs.getString(5);
                datos[5] = rs.getString(6);
                datos[6] = rs.getString(7);
                tableCompras.addRow(datos);
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
