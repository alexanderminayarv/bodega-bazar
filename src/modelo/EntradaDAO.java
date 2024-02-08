
package modelo;

import config.Conexion;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;


public class EntradaDAO {
     int r;
    Conexion cn = new Conexion();
    Connection con;
    PreparedStatement ps;
    ResultSet rs;
    
    
     public List ListarEntradas(String valor) {
        List<Entradas> listaEntradas = new ArrayList();
        String sql = "SELECT p.cod_producto,p.nombre,cantidad,fecha,"
                + "CONCAT(nombres,' ',apellido_paterno,' ',apellido_materno) AS empleado, cantidad * dp.precioCompra AS total "
                + "FROM detalle_pedido dp "
                + "INNER JOIN producto p on p.cod_producto=dp.cod_producto "
                + "INNER JOIN pedido ped on ped.cod_pedido=dp.cod_pedido "
                + "INNER JOIN empleado e on e.cod_empleado=ped.cod_empleado "
                + "ORDER BY fecha DESC;";
        String buscar = "SELECT p.cod_producto,p.nombre,cantidad,fecha,"
                + "CONCAT(nombres,' ',apellido_paterno,' ',apellido_materno) AS empleado, cantidad * dp.precioCompra AS total "
                + "FROM detalle_pedido dp "
                + "INNER JOIN producto p on p.cod_producto=dp.cod_producto "
                + "INNER JOIN pedido ped on ped.cod_pedido=dp.cod_pedido "
                + "INNER JOIN empleado e on e.cod_empleado=ped.cod_empleado "
                + "WHERE p.nombre LIKE '%" + valor + "%' "
                + "ORDER BY fecha DESC;";
        try {
            con = cn.getConexion();
            if (valor.equalsIgnoreCase("")) {
                ps = con.prepareStatement(sql);
                rs = ps.executeQuery();
            } else {
                ps = con.prepareStatement(buscar);
                rs = ps.executeQuery();
            }
            while (rs.next()) {
                Entradas e = new Entradas();
                e.setIdProducto(rs.getInt("cod_producto"));
                e.setNombre(rs.getString("nombre"));
                e.setCantidad(rs.getInt("cantidad"));
                e.setFecha(rs.getString("fecha"));
                e.setEmpleado(rs.getString("empleado"));
                e.setTotal(rs.getDouble("total"));
                listaEntradas.add(e);

            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e.toString());
        }
        return listaEntradas;
    }
}
