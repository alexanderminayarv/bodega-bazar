
package modelo;

import config.Conexion;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;


public class SalidaDAO {
     int r;
    Conexion cn = new Conexion();
    Connection con;
    PreparedStatement ps;
    ResultSet rs;
    
     public List ListarSalidas(String valor) {
        List<Salidas> listaSalidas = new ArrayList();
        String sql = "SELECT p.cod_producto,p.nombre,cantidad,fecha,"
                + "CONCAT(nombres,' ',apellido_paterno,' ',apellido_materno) AS empleado, cantidad * dv.precioVenta AS total "
                + "FROM detalle_venta dv "
                + "INNER JOIN producto p on p.cod_producto=dv.cod_producto "
                + "INNER JOIN venta v on v.cod_venta=dv.cod_venta "
                + "INNER JOIN empleado e on e.cod_empleado=v.cod_empleado "
                + "ORDER BY fecha DESC;";
        String buscar = "SELECT p.cod_producto,p.nombre,cantidad,fecha,"
                + "CONCAT(nombres,' ',apellido_paterno,' ',apellido_materno) AS empleado, cantidad * dv.precioVenta AS total "
                + "FROM detalle_venta dv "
                + "INNER JOIN producto p on p.cod_producto=dv.cod_producto "
                + "INNER JOIN venta v on v.cod_venta=dv.cod_venta "
                + "INNER JOIN empleado e on e.cod_empleado=v.cod_empleado "
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
                Salidas s = new Salidas();
                s.setIdProducto(rs.getInt("cod_producto"));
                s.setNombre(rs.getString("nombre"));
                s.setCantidad(rs.getInt("cantidad"));
                s.setFecha(rs.getString("fecha"));
                s.setEmpleado(rs.getString("empleado"));
                s.setTotal(rs.getDouble("total"));
                listaSalidas.add(s);

            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e.toString());
        }
        return listaSalidas;
    }
}
