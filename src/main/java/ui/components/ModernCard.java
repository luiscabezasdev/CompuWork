/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ui.components;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import javax.swing.JPanel;
/**
 *
 * @author USUARIO
 */
public class ModernCard extends JPanel {
     private int borderRadius = 12;
    
    public ModernCard() {
        setBackground(Color.BLUE);
        setOpaque(false);
    }
    
    public void setBorderRadius(int radius) {
        this.borderRadius = radius;
        repaint();
    }
    
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g.create();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        
        // Sombra suave
        g2.setColor(new Color(0, 0, 0, 8));
        g2.fillRoundRect(2, 2, getWidth() - 4, getHeight() - 4, borderRadius, borderRadius);
        
        // Fondo
        g2.setColor(getBackground());
        g2.fillRoundRect(0, 0, getWidth() - 1, getHeight() - 1, borderRadius, borderRadius);
        
        // Borde sutil
        g2.setColor(new Color(241, 245, 249));
        g2.drawRoundRect(0, 0, getWidth() - 1, getHeight() - 1, borderRadius, borderRadius);
        
        g2.dispose();
    }
    
}
