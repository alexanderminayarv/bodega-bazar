
package modelo;

import config.Conexion;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;


public class VentaDAO {
    Conexion cn=new Conexion();
    Connection con;
    PreparedStatement ps;
    ResultSet rs;
    int r=0;
    
     public String IdVta(){
        String idv="";
        String sql="select max(cod_venta) from venta";
        try {
            con=cn.getConexion();
            ps=con.prepareStatement(sql);
            rs=ps.executeQuery();
            while (rs.next()) {
                idv=rs.getString(1);
            }
            if (idv==null) {
                idv="1";
            }
        } catch (Exception e) {
        }
        return idv;
    }
    
    public int GuardarVentas(Ventas v) {
        String sql="insert into venta(cod_empleado, fecha, subtotal, igv, total) values (?,?,?,?,?)";
        try {
            con=cn.getConexion();
            ps=con.prepareStatement(sql);
            ps.setInt(1, v.getIdEmpleado());
            ps.setString(2, v.getFecha());
            ps.setDouble(3, v.getSubtotal());
            ps.setDouble(4, v.getIGV());
            ps.setDouble(5, v.getTotal());
            ps.execute();
        } catch (Exception e) {
        }
        return r;
    }
    public int GuardarDetalleVentas(DetalleVenta dv){
        String sql="insert into detalle_venta(cod_venta, cod_producto, cantidad, precioVenta) values (?,?,?,?)";
        try {
            con=cn.getConexion();
            ps=con.prepareStatement(sql);
            ps.setInt(1, dv.getIdVenta());
            ps.setInt(2, dv.getIdProducto());
            ps.setInt(3, dv.getCantidad());
            ps.setDouble(4, dv.getPreVenta());
            ps.execute();
        } catch (Exception e) {
        }
        return r;
    }
}
