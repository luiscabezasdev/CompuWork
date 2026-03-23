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
public class Panel extends JPanel {
    private int borderRadius = 20;
    private Color bgColor = new Color(230, 230, 250); // Lavanda suave
    
    public Panel() {
        setOpaque(false);
        setBackground(bgColor);
    }
    
    public void setBorderRadius(int radius) {
        this.borderRadius = radius;
    }
    
    public void setBgColor(Color color) {
        this.bgColor = color;
        setBackground(color);
    }
    
    public int getBorderRadius() {
        return borderRadius;
    }
    
    public Color getBgColor() {
        return bgColor;
    }
    
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g.create();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
        
        g2.setColor(bgColor);
        g2.fillRoundRect(0, 0, getWidth(), getHeight(), borderRadius, borderRadius);
        
        g2.setColor(new Color(255, 255, 255, 150)); // Luz arriba-izquierda
        g2.fillRoundRect(0, 0, getWidth(), getHeight() / 2, borderRadius, borderRadius);
        
        g2.setColor(new Color(0, 0, 0, 30)); // Sombra abajo-derecha
        g2.fillRoundRect(0, getHeight() / 2, getWidth(), getHeight() / 2, borderRadius, borderRadius);
        
        g2.setColor(new Color(200, 200, 220));
        g2.drawRoundRect(0, 0, getWidth() - 1, getHeight() - 1, borderRadius, borderRadius);

        g2.dispose();
    }
    
    
}
