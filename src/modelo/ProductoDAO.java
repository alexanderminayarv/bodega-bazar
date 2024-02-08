package modelo;

import config.Conexion;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JComboBox;
import javax.swing.JOptionPane;

public class ProductoDAO {

    int r;
    Conexion cn = new Conexion();
    Connection con;
    PreparedStatement ps;
    ResultSet rs;

    public int actualizarStock(int cant, int idp) {
        String sql = "update producto set stock=? where cod_producto=?";

        try {
            con = cn.getConexion();
            ps = con.prepareStatement(sql);
            ps.setInt(1, cant);
            ps.setInt(2, idp);
            ps.executeUpdate();
        } catch (Exception e) {
        }
        return r;
    }

    /*public int getIDCategoriaOfProduct(int idc) {
        String sql = "select c.cod_categoria from producto p INNER JOIN categoria c ON c.cod_categoria=p.cod_categoria WHERE p.cod_producto=?;";
        try {
            con = cn.getConexion();
            ps = con.prepareStatement(sql);
            ps.setInt(1, idc);
            ps.executeUpdate();
        } catch (Exception e) {
        }
        return r;
    }*/

    public Productos ListarID(int id) {
        Productos p = new Productos();
        String sql = "SELECT * FROM producto WHERE cod_producto=?";
        try {
            con = cn.getConexion();
            ps = con.prepareStatement(sql);
            ps.setInt(1, id);
            rs = ps.executeQuery();
            while (rs.next()) {
                p.setIdPro(rs.getInt(1));
                p.setNombre(rs.getString(2));
                p.setDescripcion(rs.getString(3));
                p.setStock(rs.getInt(4));
                p.setPrecioC(rs.getDouble(5));
                p.setPrecioV(rs.getDouble(6));
                p.setId_categoria(rs.getInt(7));
            }
        } catch (Exception e) {
        }
        return p;
    }

    public boolean RegistrarProductos(Productos p) {
        String sql = "INSERT INTO producto(nombre,descripcion,stock, precioCompra,precioVenta,cod_categoria) VALUES (?,?,?,?,?,?)";
        try {
            con = cn.getConexion();
            ps = con.prepareStatement(sql);
            ps.setString(1, p.getNombre());
            ps.setString(2, p.getDescripcion());
            ps.setInt(3, p.getStock());
            ps.setDouble(4, p.getPrecioC());
            ps.setDouble(5, p.getPrecioV());
            ps.setInt(6, p.getId_categoria());
            ps.execute();
            return true;
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e.toString());
            return false;
        }
    }

    public List ListarProductos(String valor) {
        List<Productos> listaProductos = new ArrayList();
        String sql = "SELECT cod_producto,p.nombre,descripcion,stock,precioCompra,precioVenta,c.cod_categoria,c.nombre AS categoria FROM producto p"
                + " INNER JOIN categoria c ON c.cod_categoria=p.cod_categoria ORDER BY 1";
        String buscar = "SELECT cod_producto,p.nombre,descripcion,stock,precioCompra,precioVenta,c.cod_categoria,c.nombre AS categoria FROM producto p"
                + " INNER JOIN categoria c ON c.cod_categoria=p.cod_categoria"
                + " WHERE p.nombre LIKE'%" + valor + "%' ORDER BY 1";
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
                Productos p = new Productos();
                p.setIdPro(rs.getInt("cod_producto"));
                p.setNombre(rs.getString("nombre"));
                p.setDescripcion(rs.getString("descripcion"));
                p.setStock(rs.getInt("stock"));
                p.setPrecioC(rs.getDouble("precioCompra"));
                p.setPrecioV(rs.getDouble("precioVenta"));
                p.setId_categoria(rs.getInt("cod_categoria"));
                p.setCategoria(rs.getString("categoria"));
                listaProductos.add(p);

            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e.toString());
        }
        return listaProductos;
    }

    public boolean ActualizarProductos(Productos p) {
        String sql = "UPDATE producto SET nombre=?,descripcion=?,precioCompra=?,precioVenta=?,cod_categoria=? WHERE cod_producto=?";

        try {
            con = cn.getConexion();
            ps = con.prepareStatement(sql);

            ps.setString(1, p.getNombre());
            ps.setString(2, p.getDescripcion());
            ps.setDouble(3, p.getPrecioC());
            ps.setDouble(4, p.getPrecioV());
            ps.setInt(5, p.getId_categoria());
            ps.setInt(6, p.getIdPro());
            ps.execute();
            return true;
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e.toString());

        }
        return false;

    }

    public boolean eliminarProductos(String codigo) {
        String sql = "delete from producto where cod_producto=" + codigo;
        try {
            con = cn.getConexion();
            ps = con.prepareStatement(sql);
            ps.executeUpdate();
            return true;
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e.toString());
            return false;
        }
    }

    public void cargar_categoria(JComboBox cbocategoria) {
        String sql = "select cod_categoria, nombre from categoria order by cod_categoria";
        try {
            con = cn.getConexion();
            ps = con.prepareStatement(sql);
            rs = ps.executeQuery();
            cbocategoria.addItem("Seleccione una opción");
            while (rs.next()) {
                cbocategoria.addItem(rs.getString("cod_categoria") + " - " + rs.getString("nombre"));
            }
        } catch (Exception e) {

        }
    }

    public boolean EliminarProducto(int id) {
        String sql = "delete from producto where cod_producto=" + id;
        try {
            con = cn.getConexion();
            ps = con.prepareStatement(sql);
            ps.executeUpdate();
            return true;
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e.toString());
            return false;
        }
    }

}
