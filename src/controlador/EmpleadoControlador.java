package controlador;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.List;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

import modelo.EmpleadoDAO;
import modelo.Empleados;
import vista.FrmPrincipal;

public class EmpleadoControlador implements ActionListener, MouseListener, KeyListener {
    private Empleados emp;
    private EmpleadoDAO empDao;
    private FrmPrincipal vista;

    DefaultTableModel modelo = new DefaultTableModel();

    public EmpleadoControlador(Empleados emp, EmpleadoDAO empDao, FrmPrincipal vista,
            String vendedor_o_administrador) {

        this.emp = emp;
        String rol = vendedor_o_administrador;
        this.empDao = empDao;
        this.vista = vista;
        this.vista.btnModificarUsuario.addActionListener(this);
        this.vista.btnEliminarUsuario.addActionListener(this);
        switch (rol) {
            case "Almacenero":
                this.vista.btnModificarUsuario.setEnabled(false);
                this.vista.btnEliminarUsuario.setEnabled(false);
                this.vista.txtDNIEmpleado.setEnabled(false);
                this.vista.txtNombresEmpleado.setEnabled(false);
                this.vista.txtApellidoPaternoEmpleado.setEnabled(false);
                this.vista.txtApellidoMaternoEmpleado.setEnabled(false);
                this.vista.txtCelularEmpleado.setEnabled(false);
                break;
            case "Vendedor":
                this.vista.btnModificarUsuario.setEnabled(false);
                this.vista.btnEliminarUsuario.setEnabled(false);
                this.vista.txtDNIEmpleado.setEnabled(false);
                this.vista.txtNombresEmpleado.setEnabled(false);
                this.vista.txtApellidoPaternoEmpleado.setEnabled(false);
                this.vista.txtApellidoMaternoEmpleado.setEnabled(false);
                this.vista.txtCelularEmpleado.setEnabled(false);
                break;
            case "Administrador":
                this.vista.btnModificarUsuario.setEnabled(true);
                this.vista.btnEliminarUsuario.setEnabled(true);
                this.vista.txtDNIEmpleado.setEnabled(true);
                this.vista.txtNombresEmpleado.setEnabled(true);
                this.vista.txtApellidoPaternoEmpleado.setEnabled(true);
                this.vista.txtApellidoMaternoEmpleado.setEnabled(true);
                this.vista.txtCelularEmpleado.setEnabled(true);
                break;
            default:
        }
        this.vista.txtBusquedaEmpleado.addKeyListener(this);
        this.vista.listadoEmpleados.addMouseListener(this);
        listarEmpleados();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == vista.btnModificarUsuario) {
            if (vista.txtIdEmpleado.getText().equals("")
                    || vista.txtDNIEmpleado.getText().equals("")
                    || vista.txtNombresEmpleado.getText().equals("")
                    || vista.txtApellidoPaternoEmpleado.getText().equals("")
                    || vista.txtApellidoMaternoEmpleado.getText().equals("")
                    || vista.txtCelularEmpleado.getText().equals("")) {
                JOptionPane.showMessageDialog(null, "Todos los campos son obligatorios");
            } else {
                emp.setDNI(vista.txtDNIEmpleado.getText());
                emp.setNombres(vista.txtNombresEmpleado.getText());
                emp.setApellido_paterno(vista.txtApellidoPaternoEmpleado.getText());
                emp.setApellid_materno(vista.txtApellidoMaternoEmpleado.getText());
                emp.setCelular(vista.txtCelularEmpleado.getText());
                emp.setId(Integer.parseInt(vista.txtIdEmpleado.getText()));
                if (empDao.modificar(emp)) {
                    JOptionPane.showMessageDialog(null, "Empleado modificado con exito");
                    limpiarTable();
                    listarEmpleados();
                } else {
                    JOptionPane.showMessageDialog(null, "No se pudo modificar al Empleado");
                }
                limpiarCampos();
            }
        } else if (e.getSource() == vista.btnEliminarUsuario) {
            if (JOptionPane.showConfirmDialog(null,
                    "¿Estás seguro que quieres eliminar?", "Aviso", JOptionPane.YES_NO_OPTION)
                    == JOptionPane.YES_OPTION) {
                int id = Integer.parseInt(vista.txtIdEmpleado.getText());
                boolean delete = empDao.eliminar(id);
                if (delete) {
                    JOptionPane.showMessageDialog(null, "Empleado eliminado con exito");
                    limpiarTable();
                    listarEmpleados();
                } else {
                    JOptionPane.showMessageDialog(null, "No se pudo eliminar al Empleado");
                }
                limpiarCampos();
            }

        }

    }

    public void listarEmpleados() {
        List<Empleados> lista = empDao.listarEmpleados(vista.txtBusquedaEmpleado.getText());
        modelo = (DefaultTableModel) vista.listadoEmpleados.getModel();
        Object[] ob = new Object[6];
        for (int i = 0; i < lista.size(); i++) {
            ob[0] = lista.get(i).getId();
            ob[1] = lista.get(i).getDNI();
            ob[2] = lista.get(i).getNombres();
            ob[3] = lista.get(i).getApellido_paterno();
            ob[4] = lista.get(i).getApellid_materno();
            ob[5] = lista.get(i).getCelular();
            modelo.addRow(ob);
        }
        vista.listadoEmpleados.setModel(modelo);

    }

    //para que no se duplique
    public void limpiarTable() {
        for (int i = 0; i < modelo.getRowCount(); i++) {
            modelo.removeRow(i);
            i = i - 1;
        }
    }

    //limpiar campos
    public void limpiarCampos() {
        vista.txtIdEmpleado.setText("");
        vista.txtDNIEmpleado.setText("");
        vista.txtNombresEmpleado.setText("");
        vista.txtApellidoPaternoEmpleado.setText("");
        vista.txtApellidoMaternoEmpleado.setText("");
        vista.txtCelularEmpleado.setText("");
    }

    @Override
    public void keyTyped(KeyEvent ke) {

    }

    @Override
    public void keyPressed(KeyEvent ke) {

    }

    @Override
    public void keyReleased(KeyEvent e) {
        if (e.getSource() == vista.txtBusquedaEmpleado) {
            limpiarTable();
            listarEmpleados();
        }
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        if (e.getSource() == vista.listadoEmpleados) {
            int fila = vista.listadoEmpleados.rowAtPoint(e.getPoint());
            vista.txtIdEmpleado.setText(vista.listadoEmpleados.getValueAt(fila, 0).toString());
            vista.txtDNIEmpleado.setText(vista.listadoEmpleados.getValueAt(fila, 1).toString());
            vista.txtNombresEmpleado.setText(vista.listadoEmpleados.getValueAt(fila, 2).toString());
            vista.txtApellidoPaternoEmpleado.setText(vista.listadoEmpleados.getValueAt(fila, 3).toString());
            vista.txtApellidoMaternoEmpleado.setText(vista.listadoEmpleados.getValueAt(fila, 4).toString());
            vista.txtCelularEmpleado.setText(vista.listadoEmpleados.getValueAt(fila, 5).toString());
        }
    }

    @Override
    public void mousePressed(MouseEvent e) {

    }

    @Override
    public void mouseReleased(MouseEvent e) {
      
    }

    @Override
    public void mouseEntered(MouseEvent e) {
     
    }

    @Override
    public void mouseExited(MouseEvent e) {
       
    }
}
