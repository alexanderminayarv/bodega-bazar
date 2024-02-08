package modelo;

import config.Conexion;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class PedidoDAO {

    Conexion cn = new Conexion();
    Connection con;
    PreparedStatement ps;
    ResultSet rs;
    int r = 0;
    
     public String IdPed(){
        String idp="";
        String sql="select max(cod_pedido) from pedido";
        try {
            con=cn.getConexion();
            ps=con.prepareStatement(sql);
            rs=ps.executeQuery();
            while (rs.next()) {
                idp=rs.getString(1);
            }
            if (idp==null) {
                idp="1";
            }
        } catch (Exception e) {
        }
        return idp;
    }

    public int GuardarPedidos(Pedidos v) {
        //Ventas ventas=new Pedidos();
        String sql = "insert into pedido(cod_empleado, cod_proveedor, fecha, subtotal, igv, total) values (?,?,?,?,?,?)";
        try {
            con = cn.getConexion();
            ps = con.prepareStatement(sql);
            ps.setInt(1, v.getIdEmpleado());
            ps.setInt(2, v.getIdProveedor());
            ps.setString(3, v.getFecha());
            ps.setDouble(4, v.getSubtotal());
            ps.setDouble(5, v.getIGV());
            ps.setDouble(6, v.getTotal());
            ps.execute();
        } catch (Exception e) {
        }
        return r;
    }

    public int GuardarDetallePedidos(DetallePedido dp) {
        String sql = "insert into detalle_pedido(cod_pedido, cod_producto, cantidad, precioCompra) values (?,?,?,?)";
        try {
            con = cn.getConexion();
            ps = con.prepareStatement(sql);
            ps.setInt(1, dp.getIdPedido());
            ps.setInt(2, dp.getIdProducto());
            ps.setInt(3, dp.getCantidad());
            ps.setDouble(4, dp.getPreCompra());
            ps.execute();
        } catch (Exception e) {
        }
        return r;
    }
}
