package view;

import model.ContactTableModel;

import javax.swing.*;
import javax.swing.GroupLayout.Alignment;
import javax.swing.LayoutStyle.ComponentPlacement;
import java.awt.*;

public class ContactsPanel extends JPanel {

    private JTextField txtName;
    private JTextField txtPhone;
    private JTextField txtEmail;
    private JCheckBox chkFav;
    private JComboBox<String> cboCategory;

    private JButton btnAdd;
    private JButton btnUpdate;
    private JButton btnDelete;
    private JButton btnExportCsv;
    private JButton btnLoadDemo;

    private JTable table;
    private ContactTableModel tableModel;

    private JTextField txtFilter;
    private JProgressBar progress;

    private JPopupMenu popupMenu;
    private JMenuItem miEdit;
    private JMenuItem miDelete;
    private JMenuItem miToggleFav;

    public ContactsPanel() {
        initComponents();
    }

    private void initComponents() {
        JLabel lblTitle = new JLabel("GESTIÓN DE CONTACTOS");
        lblTitle.setFont(lblTitle.getFont().deriveFont(Font.BOLD, 14f));

        JLabel lblName = new JLabel("NOMBRES:");
        JLabel lblPhone = new JLabel("TELÉFONO:");
        JLabel lblEmail = new JLabel("EMAIL:");

        txtName = new JTextField();
        txtPhone = new JTextField();
        txtEmail = new JTextField();

        chkFav = new JCheckBox("CONTACTO FAVORITO");

        cboCategory = new JComboBox<>(new String[] { "Amigos", "Familia", "Trabajo", "Otros" });

        btnAdd = new JButton("AGREGAR");
        btnUpdate = new JButton("MODIFICAR");
        btnDelete = new JButton("ELIMINAR");
        btnExportCsv = new JButton("Exportar CSV");
        btnLoadDemo = new JButton("Cargar (demo)");

        // Tabla
        tableModel = new ContactTableModel();
        table = new JTable(tableModel);
        table.setFillsViewportHeight(true);
        table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        JScrollPane scrollPane = new JScrollPane(table);

        // Buscador
        JLabel lblFilter = new JLabel("BUSCAR POR NOMBRE:");
        txtFilter = new JTextField();

        // Progress
        progress = new JProgressBar(0, 100);
        progress.setStringPainted(true);
        progress.setValue(0);

        // Menú contextual
        popupMenu = new JPopupMenu();
        miEdit = new JMenuItem("Editar");
        miDelete = new JMenuItem("Eliminar");
        miToggleFav = new JMenuItem("Marcar/Desmarcar favorito");
        popupMenu.add(miEdit);
        popupMenu.add(miDelete);
        popupMenu.addSeparator();
        popupMenu.add(miToggleFav);
        table.setComponentPopupMenu(popupMenu);

        // Panel botones derecha (parecido a imagen)
        JPanel panelButtons = new JPanel();
        GroupLayout gl_panelButtons = new GroupLayout(panelButtons);
        gl_panelButtons.setHorizontalGroup(
            gl_panelButtons.createParallelGroup(Alignment.LEADING)
                .addComponent(btnAdd, GroupLayout.DEFAULT_SIZE, 150, Short.MAX_VALUE)
                .addComponent(btnUpdate, GroupLayout.DEFAULT_SIZE, 150, Short.MAX_VALUE)
                .addComponent(btnDelete, GroupLayout.DEFAULT_SIZE, 150, Short.MAX_VALUE)
                .addComponent(btnExportCsv, GroupLayout.DEFAULT_SIZE, 150, Short.MAX_VALUE)
                .addComponent(btnLoadDemo, GroupLayout.DEFAULT_SIZE, 150, Short.MAX_VALUE)
        );
        gl_panelButtons.setVerticalGroup(
            gl_panelButtons.createSequentialGroup()
                .addComponent(btnAdd, GroupLayout.PREFERRED_SIZE, 36, GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(ComponentPlacement.UNRELATED)
                .addComponent(btnUpdate, GroupLayout.PREFERRED_SIZE, 36, GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(ComponentPlacement.UNRELATED)
                .addComponent(btnDelete, GroupLayout.PREFERRED_SIZE, 36, GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(ComponentPlacement.UNRELATED)
                .addComponent(btnExportCsv, GroupLayout.PREFERRED_SIZE, 34, GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(ComponentPlacement.UNRELATED)
                .addComponent(btnLoadDemo, GroupLayout.PREFERRED_SIZE, 34, GroupLayout.PREFERRED_SIZE)
        );
        panelButtons.setLayout(gl_panelButtons);

        // Layout principal
        GroupLayout groupLayout = new GroupLayout(this);
        groupLayout.setHorizontalGroup(
            groupLayout.createParallelGroup(Alignment.LEADING)
                .addGroup(groupLayout.createSequentialGroup()
                    .addContainerGap()
                    .addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
                        .addComponent(lblTitle)
                        .addGroup(groupLayout.createSequentialGroup()
                            .addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
                                .addGroup(groupLayout.createSequentialGroup()
                                    .addComponent(lblName, GroupLayout.PREFERRED_SIZE, 90, GroupLayout.PREFERRED_SIZE)
                                    .addPreferredGap(ComponentPlacement.UNRELATED)
                                    .addComponent(txtName))
                                .addGroup(groupLayout.createSequentialGroup()
                                    .addComponent(lblPhone, GroupLayout.PREFERRED_SIZE, 90, GroupLayout.PREFERRED_SIZE)
                                    .addPreferredGap(ComponentPlacement.UNRELATED)
                                    .addComponent(txtPhone))
                                .addGroup(groupLayout.createSequentialGroup()
                                    .addComponent(lblEmail, GroupLayout.PREFERRED_SIZE, 90, GroupLayout.PREFERRED_SIZE)
                                    .addPreferredGap(ComponentPlacement.UNRELATED)
                                    .addComponent(txtEmail))
                                .addGroup(groupLayout.createSequentialGroup()
                                    .addComponent(chkFav)
                                    .addPreferredGap(ComponentPlacement.UNRELATED)
                                    .addComponent(cboCategory, 0, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                            .addPreferredGap(ComponentPlacement.UNRELATED)
                            .addComponent(panelButtons, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
                        .addComponent(scrollPane, GroupLayout.DEFAULT_SIZE, 900, Short.MAX_VALUE)
                        .addGroup(groupLayout.createSequentialGroup()
                            .addComponent(lblFilter)
                            .addPreferredGap(ComponentPlacement.UNRELATED)
                            .addComponent(txtFilter, GroupLayout.DEFAULT_SIZE, 600, Short.MAX_VALUE))
                        .addComponent(progress, GroupLayout.DEFAULT_SIZE, 900, Short.MAX_VALUE))
                    .addContainerGap())
        );
        groupLayout.setVerticalGroup(
            groupLayout.createParallelGroup(Alignment.LEADING)
                .addGroup(groupLayout.createSequentialGroup()
                    .addContainerGap()
                    .addComponent(lblTitle)
                    .addPreferredGap(ComponentPlacement.UNRELATED)
                    .addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
                        .addGroup(groupLayout.createSequentialGroup()
                            .addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
                                .addComponent(lblName)
                                .addComponent(txtName, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
                            .addPreferredGap(ComponentPlacement.UNRELATED)
                            .addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
                                .addComponent(lblPhone)
                                .addComponent(txtPhone, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
                            .addPreferredGap(ComponentPlacement.UNRELATED)
                            .addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
                                .addComponent(lblEmail)
                                .addComponent(txtEmail, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
                            .addPreferredGap(ComponentPlacement.UNRELATED)
                            .addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
                                .addComponent(chkFav)
                                .addComponent(cboCategory, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)))
                        .addComponent(panelButtons))
                    .addPreferredGap(ComponentPlacement.UNRELATED)
                    .addComponent(scrollPane, GroupLayout.DEFAULT_SIZE, 280, Short.MAX_VALUE)
                    .addPreferredGap(ComponentPlacement.UNRELATED)
                    .addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
                        .addComponent(lblFilter)
                        .addComponent(txtFilter, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
                    .addPreferredGap(ComponentPlacement.UNRELATED)
                    .addComponent(progress, GroupLayout.PREFERRED_SIZE, 22, GroupLayout.PREFERRED_SIZE)
                    .addContainerGap())
        );
        setLayout(groupLayout);
    }

    // ===== Getters para el Controller (MVC) =====
    public JTextField getTxtName() { return txtName; }
    public JTextField getTxtPhone() { return txtPhone; }
    public JTextField getTxtEmail() { return txtEmail; }
    public JCheckBox getChkFav() { return chkFav; }
    public JComboBox<String> getCboCategory() { return cboCategory; }

    public JButton getBtnAdd() { return btnAdd; }
    public JButton getBtnUpdate() { return btnUpdate; }
    public JButton getBtnDelete() { return btnDelete; }
    public JButton getBtnExportCsv() { return btnExportCsv; }
    public JButton getBtnLoad() { return btnLoadDemo; }

    public JTable getTable() { return table; }
    public ContactTableModel getTableModel() { return tableModel; }

    public JTextField getTxtFilter() { return txtFilter; }
    public JProgressBar getProgress() { return progress; }

    public JMenuItem getMiEdit() { return miEdit; }
    public JMenuItem getMiDelete() { return miDelete; }
    public JMenuItem getMiToggleFav() { return miToggleFav; }
}