/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controlador;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JOptionPane;
import modelo.Empleados;
import modelo.EmpleadoDAO;
import vista.FrmLogin;
import vista.FrmPrincipal;
import vista.FrmRegistrarse;

public class LoginControlador implements ActionListener {

    private Empleados us;
    private EmpleadoDAO usDao;
    private FrmLogin vista;

    public LoginControlador(Empleados us, EmpleadoDAO usDao, FrmLogin vista) {
        this.us = us;
        this.usDao = usDao;
        this.vista = vista;
        this.vista.btnIngresar.addActionListener(this);
        this.vista.btnRegistrarse.addActionListener(this);
        this.vista.setLocationRelativeTo(null);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == vista.btnIngresar) {
            if (vista.txtCorreo.getText().equals("")
                    || String.valueOf(vista.txtContrasenia.getPassword()).equals("")) {
                JOptionPane.showMessageDialog(null, "Los campos estan vacios");
            } else {
                String correo = vista.txtCorreo.getText();
                String pass = String.valueOf(vista.txtContrasenia.getPassword());
                us = usDao.login(correo, pass);
                if (us.getCorreo() != null) {
                    int id=us.getId();
                    String rol=us.getRol();
                    //System.out.println("Id: " + id);
                    FrmPrincipal principal = new FrmPrincipal(id,rol);
                    principal.setVisible(true);
                    this.vista.dispose();
                } else {
                    JOptionPane.showMessageDialog(null, "Correo o contraseña incorrecto");
                }
            }
        } else if (e.getSource() == vista.btnRegistrarse) {
            FrmRegistrarse registrarse = new FrmRegistrarse();
            registrarse.setVisible(true);
            this.vista.dispose();
        }

    }

}
