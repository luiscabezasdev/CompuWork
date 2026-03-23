/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ui.components;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.JLabel;
import javax.swing.JPanel;
/**
 *
 * @author USUARIO
 */
public class Sidebar extends JPanel {
   private JPanel btnDashboard;
    private JPanel btnEmpleados;
    private JPanel btnDepartamentos;
    private JPanel btnReportes;
    
    public Sidebar() {
        setBackground(new Color(248, 250, 252));
        setPreferredSize(new java.awt.Dimension(260, 0));
        setLayout(new GridBagLayout());
        
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(2, 0, 2, 0);
        
        // Logo
        gbc.gridy = 0;
        gbc.insets = new Insets(30, 20, 30, 20);
        JLabel lblLogo = new JLabel("🏢 CompuWork");
        lblLogo.setFont(new Font("Segoe UI", Font.BOLD, 22));
        lblLogo.setForeground(new Color(15, 23, 42));
        add(lblLogo, gbc);
        
        // Menú
        gbc.insets = new Insets(5, 10, 5, 10);
        
        btnDashboard = createMenuItem("📊 Dashboard");
        gbc.gridy = 1;
        add(btnDashboard, gbc);
        
        btnEmpleados = createMenuItem("👥 Empleados");
        gbc.gridy = 2;
        add(btnEmpleados, gbc);
        
        btnDepartamentos = createMenuItem("🏢 Departamentos");
        gbc.gridy = 3;
        add(btnDepartamentos, gbc);
        
        btnReportes = createMenuItem("📈 Reportes");
        gbc.gridy = 4;
        add(btnReportes, gbc);
        
        // Separador
        gbc.gridy = 5;
        gbc.insets = new Insets(30, 20, 30, 20);
        JPanel separator = new JPanel();
        separator.setPreferredSize(new java.awt.Dimension(0, 1));
        separator.setBackground(new Color(226, 232, 240));
        add(separator, gbc);
        
        // Settings y Logout
        gbc.insets = new Insets(5, 10, 5, 10);
        
        JPanel btnSettings = createMenuItem("⚙️ Configuración");
        gbc.gridy = 6;
        add(btnSettings, gbc);
        
        JPanel btnLogout = createMenuItem("🚪 Cerrar Sesión");
        gbc.gridy = 7;
        add(btnLogout, gbc);
        
        // Seleccionar Empleados por defecto
        selectButton(btnEmpleados);
    }
    
    private JPanel createMenuItem(String text) {
        JPanel panel = new JPanel();
        panel.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT, 20, 12));
        panel.setBackground(Color.WHITE);
        panel.setCursor(new Cursor(Cursor.HAND_CURSOR));
        panel.setPreferredSize(new java.awt.Dimension(220, 45));
        
        JLabel label = new JLabel(text);
        label.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        label.setForeground(new Color(71, 85, 105));
        panel.add(label);
        
        // Efecto hover
        panel.addMouseListener(new MouseAdapter() {
            public void mouseEntered(MouseEvent e) {
                if (!panel.getBackground().equals(new Color(239, 246, 255))) {
                    panel.setBackground(new Color(241, 245, 249));
                }
            }
            public void mouseExited(MouseEvent e) {
                if (!panel.getBackground().equals(new Color(239, 246, 255))) {
                    panel.setBackground(Color.WHITE);
                }
            }
        });
        
        return panel;
    }
    
    private void selectButton(JPanel button) {
        // Reset todos
        btnDashboard.setBackground(Color.WHITE);
        btnEmpleados.setBackground(Color.WHITE);
        btnDepartamentos.setBackground(Color.WHITE);
        btnReportes.setBackground(Color.WHITE);
        
        // Seleccionar actual
        button.setBackground(new Color(239, 246, 255));
    } 
    
}
