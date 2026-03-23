/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package ui.components;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.JButton;

/**
 *
 * @author USUARIO
 */
public class Button extends JButton {
  private boolean isPrimary;
  private boolean hovered = false;
  private boolean pressed = false;
  
  public Button(String text, boolean isPrimary) {
      super(text);
      this.isPrimary = isPrimary;
      setContentAreaFilled(false);
      setBorderPainted(false);
      setFocusPainted(false);
      setCursor(new Cursor(Cursor.HAND_CURSOR));
      setFont(new Font("Segoe UI", Font.BOLD, 13));

      if (isPrimary) {
          setForeground(Color.WHITE);
      } else {
          setForeground(new Color(100, 116, 139));
      }
      
       addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) { hovered = true; repaint(); }
            @Override
            public void mouseExited(MouseEvent e) { hovered = false; repaint(); }
            @Override
            public void mousePressed(MouseEvent e) { pressed = true; repaint(); }
            @Override
            public void mouseReleased(MouseEvent e) { pressed = false; repaint(); }
        });
          
  }
   
   @Override
    protected void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D) g.create();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        
        if (isPrimary) {
            Color btnColor = pressed ? new Color(11, 76, 176) : 
                           hovered ? new Color(29, 98, 210) : new Color(14, 116, 242);
            g2.setColor(btnColor);
            g2.fillRoundRect(0, 0, getWidth(), getHeight(), 8, 8);
        } else {
            Color bgColor = hovered ? new Color(241, 245, 249) : Color.WHITE;
            g2.setColor(bgColor);
            g2.fillRoundRect(0, 0, getWidth(), getHeight(), 8, 8);
            g2.setColor(new Color(226, 232, 240));
            g2.drawRoundRect(0, 0, getWidth() - 1, getHeight() - 1, 8, 8);
        }
        
        super.paintComponent(g2);
        g2.dispose();
    }      
}
