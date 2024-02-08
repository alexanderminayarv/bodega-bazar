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

public class ProveedorDAO {

    Conexion cn = new Conexion();
    Connection con;
    PreparedStatement ps;
    ResultSet rs;

    public boolean RegistrarProveedor(Proveedores p) {
        String sql = "INSERT INTO proveedor(ruc,nombre, direccion, email,celular,categoria) VALUES (?,?,?,?,?,?)";
        try {
            con = cn.getConexion();
            ps = con.prepareStatement(sql);
            ps.setString(1, p.getRuc());
            ps.setString(2, p.getNombre());
            ps.setString(3, p.getDireccion());
            ps.setString(4, p.getEmail());
            ps.setString(5, p.getCelular());
            ps.setInt(6, p.getId_categoria());
            ps.execute();
            return true;

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e.toString());
            return false;

        }
    }
    
    public Proveedores ListarID(int id){
        Proveedores prov=new Proveedores();
        String sql="SELECT * FROM proveedor WHERE cod_proveedor=?";
        try {
            con=cn.getConexion();
            ps=con.prepareStatement(sql);
            ps.setInt(1, id);
            rs=ps.executeQuery();
            while (rs.next()) {
                prov.setIdProv(rs.getInt(1));
                prov.setRuc(rs.getString(2));
                prov.setNombre(rs.getString(3));
                prov.setDireccion(rs.getString(4));
                prov.setEmail(rs.getString(5));
                prov.setCelular(rs.getString(6));
                prov.setId_categoria(rs.getInt(7));
            }
        } catch (Exception e) {
        }
        return prov;
    }

    public List ListarProveedor(String valor) {
        List<Proveedores> listaProveedor = new ArrayList();
        String sql = "SELECT p.cod_proveedor,ruc,p.nombre,direccion,email,celular,c.cod_categoria AS id_categoria, c.nombre AS categoria FROM proveedor p INNER JOIN categoria c ON c.cod_categoria=p.categoria;";
        String buscar = "SELECT p.cod_proveedor,ruc,p.nombre,direccion,email,celular,c.cod_categoria AS id_categoria,c.nombre AS categoria FROM proveedor p INNER JOIN categoria c ON c.cod_categoria=p.categoria"
                + " WHERE p.nombre LIKE '%" + valor + "%'";
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
                Proveedores pr = new Proveedores();
                pr.setIdProv(rs.getInt("cod_proveedor"));
                pr.setRuc(rs.getString("ruc"));
                pr.setNombre(rs.getString("nombre"));
                pr.setDireccion(rs.getString("direccion"));
                pr.setEmail(rs.getString("email"));
                pr.setCelular(rs.getString("celular"));
                pr.setId_categoria(rs.getInt("id_categoria"));
                pr.setCategoria(rs.getString("categoria"));
                listaProveedor.add(pr);
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e.toString());
        }
        return listaProveedor;
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

    public boolean EliminarProveedor(int idProv) {
        String sql = "delete from proveedor WHERE cod_proveedor = " + idProv;
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

    public boolean ModificarProveedor(Proveedores pr) {
        String sql = "UPDATE proveedor SET ruc = ?, nombre = ?, direccion = ?, email = ?, celular = ?, categoria=? WHERE cod_proveedor = ?";
        try {
            con = cn.getConexion();
            ps = con.prepareStatement(sql);
            ps.setString(1, pr.getRuc());
            ps.setString(2, pr.getNombre());
            ps.setString(3, pr.getDireccion());
            ps.setString(4, pr.getEmail());
            ps.setString(5, pr.getCelular());
            ps.setInt(6, pr.getId_categoria());
            ps.setInt(7, pr.getIdProv());
            ps.execute();
            return true;
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e.toString());
            return false;

        }
    }

}
