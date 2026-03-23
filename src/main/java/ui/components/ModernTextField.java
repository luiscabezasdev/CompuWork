/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ui.components;


import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import javax.swing.JTextField;
/**
 *
 * @author USUARIO
 */
public class ModernTextField extends JTextField {
     private String placeholder;
    
    public ModernTextField(String placeholder) {
        this.placeholder = placeholder;
        setFont(new Font("Segoe UI", Font.PLAIN, 13));
        setBackground(new Color(241, 245, 249));
        setBorder(null);
        setCaretColor(new Color(14, 116, 242));
        setOpaque(false);
    }
    
    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D) g.create();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        
        g2.setColor(getBackground());
        g2.fillRoundRect(0, 0, getWidth(), getHeight(), 8, 8);
        
        super.paintComponent(g2);
        
        if (getText().isEmpty() && placeholder != null) {
            g2.setColor(new Color(148, 163, 184));
            g2.setFont(getFont());
            g2.drawString(placeholder, 12, 
                (getHeight() - getFontMetrics(getFont()).getAscent()) / 2 + 
                getFontMetrics(getFont()).getAscent());
        }
        
        g2.dispose();
    }
    
    @Override
    protected void paintBorder(Graphics g) {
        Graphics2D g2 = (Graphics2D) g.create();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2.setColor(new Color(226, 232, 240));
        g2.drawRoundRect(0, 0, getWidth() - 1, getHeight() - 1, 8, 8);
        g2.dispose();
    }
    
}
