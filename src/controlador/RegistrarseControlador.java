package controlador;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JOptionPane;
import modelo.EmpleadoDAO;
import modelo.Empleados;
import vista.FrmLogin;
import vista.FrmRegistrarse;

public class RegistrarseControlador implements ActionListener {

    private Empleados emp;
    private EmpleadoDAO empDao;
    private FrmRegistrarse vista;

    public RegistrarseControlador(Empleados emp, EmpleadoDAO empDao, FrmRegistrarse vista) {
        this.emp = emp;
        this.empDao = empDao;
        this.vista = vista;
        this.vista.btnRegistrar.addActionListener(this);
        this.vista.btnRegresar.addActionListener(this);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == vista.btnRegistrar) {
            if (vista.txtEDNI.getText().equals("")
                    || vista.txtEDNI.getText().equals("")
                    || vista.txtEApellidoPaterno.getText().equals("")
                    || vista.txtEApellidoMaterno.getText().equals("")
                    || vista.txtECelular.getText().equals("")
                    || vista.txtECorreo.getText().equals("")
                    || String.valueOf(vista.txtEContrasenia.getPassword()).equals("")) {
                JOptionPane.showMessageDialog(null, "Los campos estan vacios");
            } else {
                String DNIE = vista.txtEDNI.getText();
                String nombresE = vista.txtENombres.getText();
                String apellidos_paterno = vista.txtEApellidoPaterno.getText();
                String apellidos_materno = vista.txtEApellidoMaterno.getText();
                String celularE = vista.txtECelular.getText();
                String correoE= vista.txtECorreo.getText();
                String passwordE = String.valueOf(vista.txtEContrasenia.getPassword());
                //Por defecto lo dejamos en vendedor
                int rolE = 3;
                String estadoE="1";
                emp.setDNI(DNIE);
                emp.setNombres(nombresE);
                emp.setApellido_paterno(apellidos_paterno);
                emp.setApellid_materno(apellidos_materno);
                emp.setCelular(celularE);
                emp.setCorreo(correoE);
                emp.setPassword(passwordE);
                emp.setEstado(estadoE);
                emp.setId_rol(rolE);
                boolean register = empDao.registrar(emp);
                if (register) {
                    JOptionPane.showMessageDialog(null, "Empleado Registrado");
                    FrmRegistrarse registrar = new FrmRegistrarse();
                    registrar.setVisible(true);
                    this.vista.dispose();
                } else {
                    JOptionPane.showMessageDialog(null, "Empleado No Registrado");
                }
            }
        } else if (e.getSource() == vista.btnRegresar) {
            FrmLogin regresar_login = new FrmLogin();
            regresar_login.setVisible(true);
            this.vista.dispose();
        }
    }
}
