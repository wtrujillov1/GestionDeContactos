package view;

import javax.swing.*;
import java.awt.*;

public class MainFrame extends JFrame {
    private final JTabbedPane tabbedPane;
    private final ContactsPanel contactsPanel;
    private final StatsPanel statsPanel;

    public MainFrame() {
        setTitle("Gestión de Contactos");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(950, 580);

        tabbedPane = new JTabbedPane(JTabbedPane.TOP);
        contactsPanel = new ContactsPanel();
        statsPanel = new StatsPanel();

        tabbedPane.addTab("Contactos", contactsPanel);
        tabbedPane.addTab("Estadísticas", statsPanel);

        getContentPane().setLayout(new BorderLayout());
        getContentPane().add(tabbedPane, BorderLayout.CENTER);
    }

    public ContactsPanel getContactsPanel() {
        return contactsPanel;
    }

    public StatsPanel getStatsPanel() {
        return statsPanel;
    }
}