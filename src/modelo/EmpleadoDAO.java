package modelo;

import config.Conexion;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;

public class EmpleadoDAO {

    Conexion cn = new Conexion();
    Connection con;
    PreparedStatement ps;
    ResultSet rs;

    public Empleados login(String correo, String pass) {
        String sql = "SELECT cod_empleado,DNI,nombres,apellido_paterno,apellido_materno,celular,correo,password,estado,r.nombre AS rol FROM empleado e"
                + " INNER JOIN rol r ON r.cod_rol=e.cod_rol"
                + " WHERE correo = ? AND password = ?";
        Empleados emp = new Empleados();
        try {
            con = cn.getConexion();
            ps = con.prepareStatement(sql);
            ps.setString(1, correo);
            ps.setString(2, pass);
            rs = ps.executeQuery();
            if (rs.next()) {
                emp.setId(rs.getInt("cod_empleado"));
                emp.setDNI(rs.getString("DNI"));
                emp.setNombres(rs.getString("nombres"));
                emp.setApellido_paterno(rs.getString("apellido_paterno"));
                emp.setApellid_materno(rs.getString("apellido_materno"));
                emp.setCelular(rs.getString("celular"));
                emp.setCorreo(rs.getString("correo"));
                emp.setPassword(rs.getString("password"));
                emp.setEstado(rs.getString("estado"));
                emp.setRol(rs.getString("rol"));
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e.toString());
        }
        return emp;
    }

    public boolean registrar(Empleados emp) {
        String sql = "INSERT INTO empleado (DNI, nombres, apellido_paterno, apellido_materno, celular, correo, password, estado, cod_rol) VALUES (?,?,?,?,?,?,?,?,?)";
        try {
            con = cn.getConexion();
            ps = con.prepareStatement(sql);
            ps.setString(1, emp.getDNI());
            ps.setString(2, emp.getNombres());
            ps.setString(3, emp.getApellido_paterno());
            ps.setString(4, emp.getApellid_materno());
            ps.setString(5, emp.getCelular());
            ps.setString(6, emp.getCorreo());
            ps.setString(7, emp.getPassword());
            ps.setString(8, emp.getEstado());
            ps.setInt(9, emp.getId_rol());
            ps.execute();
            return true;
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e.toString());
            return false;
        }
    }

    public boolean eliminar(int id) {
        String sql = "update empleado set estado='0' where cod_empleado=" + id;
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

    public List listarEmpleados(String valor) {
        List<Empleados> listaEmployees = new ArrayList();
        String sql = "SELECT cod_empleado,DNI,nombres,apellido_paterno,apellido_materno,celular FROM empleado"
                + " WHERE cod_rol='2' AND estado='1' OR cod_rol='3' AND estado='1'";
        String buscar = "SELECT cod_empleado,DNI,nombres,apellido_paterno,apellido_materno,celular FROM empleado "
                + "WHERE cod_rol='2' AND nombres LIKE '%" + valor + "%' AND estado='1' OR "
                + "cod_rol='3' AND nombres LIKE '%" + valor + "%' AND estado='1'";
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
                Empleados emp = new Empleados();
                emp.setId(rs.getInt("cod_empleado"));
                emp.setDNI(rs.getString("DNI"));
                emp.setNombres(rs.getString("nombres"));
                emp.setApellido_paterno(rs.getString("apellido_paterno"));
                emp.setApellid_materno(rs.getString("apellido_materno"));
                emp.setCelular(rs.getString("celular"));
                listaEmployees.add(emp);
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e.toString());
        }
        return listaEmployees;
    }

    public boolean modificar(Empleados emp) {
        String sql = "UPDATE empleado SET DNI=?, nombres=?, apellido_paterno=?, apellido_materno=?, celular=? WHERE cod_empleado = ?";
        try {
            con = cn.getConexion();
            ps = con.prepareStatement(sql);
            ps.setString(1, emp.getDNI());
            ps.setString(2, emp.getNombres());
            ps.setString(3, emp.getApellido_paterno());
            ps.setString(4, emp.getApellid_materno());
            ps.setString(5, emp.getCelular());
            ps.setInt(6, emp.getId());
            ps.execute();
            return true;
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e.toString());
            return false;
        }
    }
}
