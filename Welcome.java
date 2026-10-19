package Siniestro;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JTextArea;
import javax.swing.JScrollPane;

public class Welcome extends JFrame implements ActionListener {

    JMenuBar bm;
    JMenu mArchivo, mBD;
    JMenuItem mBD_C, mBD_R, mBD_U, mBD_D;

    JLabel lbTitulo;
    JLabel lbId, lbAccidente, lbRobo, lbDanoParcial, lbEstado;
    JTextField txtId, txtAccidente, txtRobo, txtDanoParcial, txtEstado;
    
    JButton btnConsultar, btnAgregar, btnActualizar, btnEliminar;

    public Welcome() {
        bm = new JMenuBar();
        this.setJMenuBar(bm);

        mArchivo = new JMenu("Archivo");
        bm.add(mArchivo);

        mBD = new JMenu("Base de datos");
        bm.add(mBD);

        this.mBD_C = new JMenuItem("Crear");
        this.mBD_R = new JMenuItem("Consultar");
        this.mBD_U = new JMenuItem("Actualizar");
        this.mBD_D = new JMenuItem("Eliminar");

        mBD.add(mBD_C);
        mBD.add(mBD_R);
        mBD.add(mBD_U);
        mBD.add(mBD_D);

        this.mBD_C.addActionListener(this);
        this.mBD_R.addActionListener(this);
        this.mBD_U.addActionListener(this);
        this.mBD_D.addActionListener(this);

        this.setBounds(150, 50, 550, 400); 
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setLayout(null);

        this.lbTitulo = new JLabel("Gestión de Siniestros");
        this.lbTitulo.setBounds(200, 20, 200, 30);
        this.add(lbTitulo);

        this.lbId = new JLabel("Id (Para buscar/borrar)");
        this.lbId.setBounds(50, 60, 150, 25);
        this.add(lbId);
        this.txtId = new JTextField();
        this.txtId.setBounds(200, 60, 250, 25);
        this.txtId.setEditable(true); 
        this.add(txtId);

        this.lbAccidente = new JLabel("Accidente");
        this.lbAccidente.setBounds(50, 100, 100, 25);
        this.add(lbAccidente);
        this.txtAccidente = new JTextField();
        this.txtAccidente.setBounds(200, 100, 250, 25);
        this.add(txtAccidente);

        this.lbRobo = new JLabel("Robo");
        this.lbRobo.setBounds(50, 140, 100, 25);
        this.add(lbRobo);
        this.txtRobo = new JTextField();
        this.txtRobo.setBounds(200, 140, 250, 25);
        this.add(txtRobo);

        this.lbDanoParcial = new JLabel("Daño Parcial");
        this.lbDanoParcial.setBounds(50, 180, 100, 25);
        this.add(lbDanoParcial);
        this.txtDanoParcial = new JTextField();
        this.txtDanoParcial.setBounds(200, 180, 250, 25);
        this.add(txtDanoParcial);

        this.lbEstado = new JLabel("Estado");
        this.lbEstado.setBounds(50, 220, 100, 25);
        this.add(lbEstado);
        this.txtEstado = new JTextField();
        this.txtEstado.setBounds(200, 220, 250, 25);
        this.add(txtEstado);

        this.btnConsultar = new JButton("Consultar");
        this.btnConsultar.setBounds(40, 280, 100, 30);
        this.add(btnConsultar);

        this.btnAgregar = new JButton("Agregar");
        this.btnAgregar.setBounds(150, 280, 100, 30);
        this.add(btnAgregar);

        this.btnActualizar = new JButton("Actualizar");
        this.btnActualizar.setBounds(260, 280, 100, 30);
        this.add(btnActualizar);

        this.btnEliminar = new JButton("Eliminar");
        this.btnEliminar.setBounds(370, 280, 100, 30);
        this.add(btnEliminar);

        this.btnConsultar.addActionListener(this);
        this.btnAgregar.addActionListener(this);
        this.btnActualizar.addActionListener(this);
        this.btnEliminar.addActionListener(this);

        this.setVisible(true);
    }

    public static void main(String[] args) {
        new Welcome();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == this.btnConsultar || e.getSource() == this.mBD_R) {
            this.consultar();
        }
        if (e.getSource() == this.btnAgregar || e.getSource() == this.mBD_C) {
            this.agregar();
        }
        if (e.getSource() == this.btnActualizar || e.getSource() == this.mBD_U) {
            this.actualizar();
        }
        if (e.getSource() == this.btnEliminar || e.getSource() == this.mBD_D) {
            this.eliminar();
        }
    }

    public void agregar() {
        try {
            Connection cnx = DriverManager.getConnection("jdbc:mysql://localhost/Siniestro", "root", "");
            PreparedStatement ps = cnx.prepareStatement("insert into siniestro(accidente, robo, dano_parcial, estado) values(?, ?, ?, ?)");
            ps.setString(1, this.txtAccidente.getText());
            ps.setString(2, this.txtRobo.getText());
            ps.setString(3, this.txtDanoParcial.getText());
            ps.setString(4, this.txtEstado.getText());
            ps.execute();
            cnx.close();

            JOptionPane.showMessageDialog(this, "Registro agregado correctamente a la BD.");
            this.limpiarCampos();

        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this, "Error al agregar: " + ex.getMessage());
        }
    }

   public void consultar() {
        try {
            Connection cnx = DriverManager.getConnection("jdbc:mysql://localhost/Siniestro", "root", "");
            PreparedStatement ps = cnx.prepareStatement("select * from siniestro");
            ResultSet rs = ps.executeQuery();

            
            StringBuilder registros = new StringBuilder();
            boolean hayDatos = false;

            while (rs.next()) {
                hayDatos = true;
                registros.append("====================================\n");
                registros.append("ID:             ").append(rs.getString("id")).append("\n");
                registros.append("Accidente:      ").append(rs.getString("accidente")).append("\n");
                registros.append("Robo:           ").append(rs.getString("robo")).append("\n");
                registros.append("Daño Parcial:   ").append(rs.getString("dano_parcial")).append("\n");
                registros.append("Estado:         ").append(rs.getString("estado")).append("\n");
            }
            cnx.close();

            if (hayDatos) {
                registros.append("====================================");
                
                
                JTextArea textArea = new JTextArea(registros.toString());
                textArea.setEditable(false); 
                
                
                textArea.setRows(12);
                textArea.setColumns(35);
                
                
                JScrollPane scroll = new JScrollPane(textArea);
                
                JOptionPane.showMessageDialog(this, scroll, "Registros en la Base de Datos", JOptionPane.INFORMATION_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(this, "La base de datos está vacía.");
            }

        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this, "Error al consultar: " + ex.getMessage());
        }
    }

    public void actualizar() {
        if(txtId.getText().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Por favor, escriba el ID del registro que desea actualizar.");
            return;
        }

        try {
            Connection cnx = DriverManager.getConnection("jdbc:mysql://localhost/Siniestro", "root", "");
            PreparedStatement ps = cnx.prepareStatement("update siniestro set accidente = ?, robo = ?, dano_parcial = ?, estado = ? where id = ?");
            ps.setString(1, this.txtAccidente.getText());
            ps.setString(2, this.txtRobo.getText());
            ps.setString(3, this.txtDanoParcial.getText());
            ps.setString(4, this.txtEstado.getText());
            ps.setString(5, this.txtId.getText());
            
            int filasModificadas = ps.executeUpdate();
            cnx.close();

            if(filasModificadas > 0) {
                JOptionPane.showMessageDialog(this, "Registro actualizado correctamente.");
                this.limpiarCampos();
            } else {
                JOptionPane.showMessageDialog(this, "No se encontró ningún registro con ese ID.");
            }

        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this, "Error al actualizar: " + ex.getMessage());
        }
    }

    public void eliminar() {
        if(txtId.getText().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Por favor, escriba el ID del registro que desea eliminar.");
            return;
        }

        try {
            Connection cnx = DriverManager.getConnection("jdbc:mysql://localhost/Siniestro", "root", "");
            PreparedStatement ps = cnx.prepareStatement("delete from siniestro where id = ?");
            ps.setInt(1, Integer.parseInt(this.txtId.getText())); 
            
            int filasModificadas = ps.executeUpdate();
            cnx.close();

            if(filasModificadas > 0) {
                JOptionPane.showMessageDialog(this, "Registro eliminado correctamente.");
                this.limpiarCampos();
            } else {
                JOptionPane.showMessageDialog(this, "No se encontró ningún registro con ese ID.");
            }

        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this, "Error al eliminar: " + ex.getMessage());
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "El ID debe ser un número.");
        }
    }

    private void limpiarCampos() {
        this.txtId.setText("");
        this.txtAccidente.setText("");
        this.txtRobo.setText("");
        this.txtDanoParcial.setText("");
        this.txtEstado.setText("");
    }
}