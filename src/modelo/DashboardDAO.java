package modelo;

import config.Conexion;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;

public class DashboardDAO {

    Connection con;
    Conexion cn = new Conexion();
    PreparedStatement ps;
    ResultSet rs;
    int r = 0;

    public int getCantidadProductos() {
        int cantidad = 0;
        String sql = "select count(*) from producto";
        try {
            con = cn.getConexion();
            ps = con.prepareStatement(sql);
            rs = ps.executeQuery();
            if (rs.next()) {
                cantidad = rs.getInt(1);
            }
        } catch (Exception e) {
        }
        return cantidad;
    }
    
    public int getCantidadProveedores() {
        int cantidad = 0;
        String sql = "select count(*) from proveedor";
        try {
            con = cn.getConexion();
            ps = con.prepareStatement(sql);
            rs = ps.executeQuery();
            if (rs.next()) {
                cantidad = rs.getInt(1);
            }
        } catch (Exception e) {
        }
        return cantidad;
    }
    
   public List listarTotalXDiaPedidos() {
        List<Pedidos> listaTotalPerDayOfOrders = new ArrayList();
        String sql = "SELECT SUM(subtotal) AS subtotal,SUM(igv)AS igv,SUM(total) AS total, fecha FROM pedido GROUP BY fecha ORDER BY fecha DESC";
        try {
            con = cn.getConexion();
            ps = con.prepareStatement(sql);
            rs = ps.executeQuery();
            
            while (rs.next()) {
                Pedidos ped = new Pedidos();
                ped.setSubtotal(rs.getDouble("subtotal"));
                ped.setIGV(rs.getDouble("igv"));
                ped.setTotal(rs.getDouble("total"));
                ped.setFecha(rs.getString("fecha"));
                listaTotalPerDayOfOrders.add(ped);
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e.toString());
        }
        return listaTotalPerDayOfOrders;
    }
   
    public List listarTotalXDiaVentas() {
        List<Ventas> listaTotalPerDayOfSales = new ArrayList();
        String sql = "SELECT SUM(subtotal) AS subtotal,SUM(igv) AS igv,SUM(total) AS total, fecha FROM venta GROUP BY fecha ORDER BY fecha DESC";
        try {
            con = cn.getConexion();
            ps = con.prepareStatement(sql);
            rs = ps.executeQuery();
            
            while (rs.next()) {
                Ventas v = new Ventas();
                v.setSubtotal(rs.getDouble("subtotal"));
                v.setIGV(rs.getDouble("igv"));
                v.setTotal(rs.getDouble("total"));
                v.setFecha(rs.getString("fecha"));
                listaTotalPerDayOfSales.add(v);
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e.toString());
        }
        return listaTotalPerDayOfSales;
    }
     
    public int getCantidadVentas() {
        int cantidad = 0;
        String sql = "select count(*) from venta";
        try {
            con = cn.getConexion();
            ps = con.prepareStatement(sql);
            rs = ps.executeQuery();
            if (rs.next()) {
                cantidad = rs.getInt(1);
            }
        } catch (Exception e) {
        }
        return cantidad;
    }
   
    public int getCantidadEmpleados() {
        int cantidad = 0;
        String sql = "select count(*) from empleado";
        try {
            con = cn.getConexion();
            ps = con.prepareStatement(sql);
            rs = ps.executeQuery();
            if (rs.next()) {
                cantidad = rs.getInt(1);
            }
        } catch (Exception e) {
        }
        return cantidad;
    }
    
     public int getCantidadPedidos() {
        int cantidad = 0;
        String sql = "select count(*) from pedido";
        try {
            con = cn.getConexion();
            ps = con.prepareStatement(sql);
            rs = ps.executeQuery();
            if (rs.next()) {
                cantidad = rs.getInt(1);
            }
        } catch (Exception e) {
        }
        return cantidad;
    }

}
