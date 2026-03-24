package ui.components;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.LinkedHashMap;
import java.util.Map;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class Sidebar extends JPanel {
    public interface NavigationListener {
        void onNavigate(String viewId);
    }

    private final Map<String, JPanel> menuItems;
    private NavigationListener navigationListener;

    public Sidebar() {
        this.menuItems = new LinkedHashMap<String, JPanel>();
        setBackground(new Color(248, 250, 252));
        setPreferredSize(new Dimension(250, 0));
        setLayout(new GridBagLayout());

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(8, 12, 8, 12);

        gbc.gridy = 0;
        gbc.insets = new Insets(28, 20, 28, 20);
        JLabel lblLogo = new JLabel("COMPUWORK");
        lblLogo.setFont(new Font("Segoe UI", Font.BOLD, 22));
        lblLogo.setForeground(new Color(15, 23, 42));
        add(lblLogo, gbc);

        gbc.insets = new Insets(6, 12, 6, 12);
        addMenuItem("EMPLEADOS", "empleados", 1, gbc);
        addMenuItem("DEPARTAMENTOS", "departamentos", 2, gbc);
        addMenuItem("REPORTES", "reportes", 3, gbc);

        gbc.gridy = 4;
        gbc.weighty = 1;
        add(new JPanel(), gbc);

        select("empleados");
    }

    public void setNavigationListener(NavigationListener navigationListener) {
        this.navigationListener = navigationListener;
    }

    public void select(String viewId) {
        for (Map.Entry<String, JPanel> entry : menuItems.entrySet()) {
            entry.getValue().setBackground(entry.getKey().equals(viewId)
                    ? new Color(219, 234, 254)
                    : Color.WHITE);
        }
    }

    private void addMenuItem(String text, final String viewId, int row, GridBagConstraints gbc) {
        JPanel panel = new JPanel(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT, 18, 12));
        panel.setBackground(Color.WHITE);
        panel.setCursor(new Cursor(Cursor.HAND_CURSOR));
        panel.setPreferredSize(new Dimension(210, 45));

        JLabel label = new JLabel(text);
        label.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        label.setForeground(new Color(51, 65, 85));
        panel.add(label);

        panel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                if (!viewId.equals(getSelectedViewId())) {
                    panel.setBackground(new Color(241, 245, 249));
                }
            }

            @Override
            public void mouseExited(MouseEvent e) {
                if (!viewId.equals(getSelectedViewId())) {
                    panel.setBackground(Color.WHITE);
                }
            }

            @Override
            public void mouseClicked(MouseEvent e) {
                select(viewId);
                if (navigationListener != null) {
                    navigationListener.onNavigate(viewId);
                }
            }
        });

        menuItems.put(viewId, panel);
        gbc.gridy = row;
        add(panel, gbc);
    }

    private String getSelectedViewId() {
        for (Map.Entry<String, JPanel> entry : menuItems.entrySet()) {
            if (entry.getValue().getBackground().equals(new Color(219, 234, 254))) {
                return entry.getKey();
            }
        }
        return "";
    }
}
