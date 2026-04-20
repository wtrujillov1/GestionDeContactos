package controller;

import model.*;
import view.*;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.table.TableRowSorter;
import java.awt.event.*;
import java.io.File;
import java.util.Comparator;
import java.util.List;
import java.util.Map;

public class MainController {
    private final ContactRepository repo;
    private final MainFrame frame;

    private final ContactsPanel contactsView;
    private final StatsPanel statsView;

    private final TableRowSorter<ContactTableModel> sorter;

    public MainController(ContactRepository repo, MainFrame frame) {
        this.repo = repo;
        this.frame = frame;

        this.contactsView = frame.getContactsPanel();
        this.statsView = frame.getStatsPanel();

        // Sorter for JTable
        sorter = new TableRowSorter<>(contactsView.getTableModel());
        sorter.setComparator(0, Comparator.comparingLong(a -> (Long) a));
        contactsView.getTable().setRowSorter(sorter);

        wireEvents();
        refreshTableAndStats();
    }

    private void wireEvents() {
        // Buttons
        contactsView.getBtnAdd().addActionListener(e -> onAdd());
        contactsView.getBtnUpdate().addActionListener(e -> onUpdate());
        contactsView.getBtnDelete().addActionListener(e -> onDeleteSelected());
        contactsView.getBtnExportCsv().addActionListener(e -> onExportCsv());
        contactsView.getBtnLoad().addActionListener(e -> onLoadDemoWithProgress());

        // Popup actions
        contactsView.getMiEdit().addActionListener(e -> loadSelectedIntoForm());
        contactsView.getMiDelete().addActionListener(e -> onDeleteSelected());
        contactsView.getMiToggleFav().addActionListener(e -> onToggleFavoriteSelected());

        // Mouse: double-click to edit
        contactsView.getTable().addMouseListener(new MouseAdapter() {
            @Override public void mouseClicked(MouseEvent e) {
                if (e.getClickCount() == 2 && SwingUtilities.isLeftMouseButton(e)) {
                    loadSelectedIntoForm();
                }
            }
        });

        // Filter by name (typing)
        contactsView.getTxtFilter().getDocument().addDocumentListener(new DocumentListener() {
            @Override public void insertUpdate(DocumentEvent e) { applyFilter(); }
            @Override public void removeUpdate(DocumentEvent e) { applyFilter(); }
            @Override public void changedUpdate(DocumentEvent e) { applyFilter(); }
        });

        // Keyboard shortcuts (InputMap/ActionMap)
        JComponent root = frame.getRootPane();
        InputMap im = root.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW);
        ActionMap am = root.getActionMap();

        im.put(KeyStroke.getKeyStroke(KeyEvent.VK_N, InputEvent.CTRL_DOWN_MASK), "new");
        am.put("new", new AbstractAction() {
            @Override public void actionPerformed(ActionEvent e) { clearForm(); }
        });

        im.put(KeyStroke.getKeyStroke(KeyEvent.VK_S, InputEvent.CTRL_DOWN_MASK), "save");
        am.put("save", new AbstractAction() {
            @Override public void actionPerformed(ActionEvent e) { onAdd(); }
        });

        im.put(KeyStroke.getKeyStroke(KeyEvent.VK_DELETE, 0), "delete");
        am.put("delete", new AbstractAction() {
            @Override public void actionPerformed(ActionEvent e) { onDeleteSelected(); }
        });
    }

    private void applyFilter() {
        String text = contactsView.getTxtFilter().getText();
        if (text == null || text.isBlank()) {
            sorter.setRowFilter(null);
        } else {
            sorter.setRowFilter(RowFilter.regexFilter("(?i)" + PatternUtil.escapeRegex(text.trim()), 1));
        }
    }

    private void onAdd() {
        String name = contactsView.getTxtName().getText().trim();
        String phone = contactsView.getTxtPhone().getText().trim();
        String email = contactsView.getTxtEmail().getText().trim();
        boolean fav = contactsView.getChkFav().isSelected();
        String cat = (String) contactsView.getCboCategory().getSelectedItem();

        if (name.isBlank()) {
            JOptionPane.showMessageDialog(frame, "El nombre es obligatorio.", "Validación", JOptionPane.WARNING_MESSAGE);
            return;
        }

        repo.add(name, phone, email, fav, cat);
        refreshTableAndStats();
        clearForm();
    }

    private void onUpdate() {
        Contact selected = getSelectedContact();
        if (selected == null) {
            JOptionPane.showMessageDialog(frame, "Seleccione un contacto en la tabla para modificar.", "Info", JOptionPane.INFORMATION_MESSAGE);
            return;
        }

        String name = contactsView.getTxtName().getText().trim();
        String phone = contactsView.getTxtPhone().getText().trim();
        String email = contactsView.getTxtEmail().getText().trim();
        boolean fav = contactsView.getChkFav().isSelected();
        String cat = (String) contactsView.getCboCategory().getSelectedItem();

        if (name.isBlank()) {
            JOptionPane.showMessageDialog(frame, "El nombre es obligatorio.", "Validación", JOptionPane.WARNING_MESSAGE);
            return;
        }

        repo.update(selected, name, phone, email, fav, cat);
        refreshTableAndStats();
    }

    private void onDeleteSelected() {
        Contact selected = getSelectedContact();
        if (selected == null) return;

        int ok = JOptionPane.showConfirmDialog(frame,
                "¿Eliminar el contacto seleccionado?",
                "Confirmar", JOptionPane.YES_NO_OPTION);

        if (ok == JOptionPane.YES_OPTION) {
            repo.delete(selected);
            refreshTableAndStats();
            clearForm();
        }
    }

    private void onToggleFavoriteSelected() {
        Contact selected = getSelectedContact();
        if (selected == null) return;

        repo.update(selected,
                selected.getName(),
                selected.getPhone(),
                selected.getEmail(),
                !selected.isFavorite(),
                selected.getCategory());

        refreshTableAndStats();
    }

    private void onExportCsv() {
        JFileChooser chooser = new JFileChooser();
        chooser.setDialogTitle("Guardar contactos como CSV");
        chooser.setSelectedFile(new File("contactos.csv"));

        int result = chooser.showSaveDialog(frame);
        if (result != JFileChooser.APPROVE_OPTION) return;

        try {
            // Exportar lo que se ve en la tabla (ya filtrado/ordenado) sería más avanzado,
            // aquí exportamos todo del repositorio:
            CsvExporter.export(chooser.getSelectedFile(), repo.findAll());
            JOptionPane.showMessageDialog(frame, "Exportado correctamente.", "CSV", JOptionPane.INFORMATION_MESSAGE);
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(frame, "Error exportando CSV: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void onLoadDemoWithProgress() {
        contactsView.getBtnLoad().setEnabled(false);
        contactsView.getProgress().setValue(0);

        SwingWorker<Void, Integer> worker = new SwingWorker<>() {
            @Override
            protected Void doInBackground() throws Exception {
                // Simular carga
                for (int i = 1; i <= 100; i++) {
                    Thread.sleep(12);
                    publish(i);
                }
                return null;
            }

            @Override
            protected void process(List<Integer> chunks) {
                int last = chunks.get(chunks.size() - 1);
                contactsView.getProgress().setValue(last);
            }

            @Override
            protected void done() {
                // Cargar datos demo al final
                repo.seedDemoData();
                refreshTableAndStats();
                contactsView.getBtnLoad().setEnabled(true);
            }
        };

        worker.execute();
    }

    private void refreshTableAndStats() {
        contactsView.getTableModel().setRows(repo.findAll());
        updateStats(repo.findAll());
    }

    private void updateStats(List<Contact> contacts) {
        statsView.setTotal(ContactStats.total(contacts));
        statsView.setFavorites(ContactStats.favorites(contacts));

        Map<String, Long> map = ContactStats.byCategory(contacts);
        StringBuilder sb = new StringBuilder("Contactos por categoría:\n\n");
        map.entrySet().stream()
                .sorted(Map.Entry.comparingByKey())
                .forEach(e -> sb.append(String.format("- %-12s : %d%n", e.getKey(), e.getValue())));

        statsView.setByCategoryText(sb.toString());
    }

    private void clearForm() {
        contactsView.getTxtName().setText("");
        contactsView.getTxtPhone().setText("");
        contactsView.getTxtEmail().setText("");
        contactsView.getChkFav().setSelected(false);
        contactsView.getCboCategory().setSelectedIndex(0);
        contactsView.getTxtName().requestFocus();
    }

    private void loadSelectedIntoForm() {
        Contact selected = getSelectedContact();
        if (selected == null) return;

        contactsView.getTxtName().setText(selected.getName());
        contactsView.getTxtPhone().setText(selected.getPhone());
        contactsView.getTxtEmail().setText(selected.getEmail());
        contactsView.getChkFav().setSelected(selected.isFavorite());
        contactsView.getCboCategory().setSelectedItem(selected.getCategory());
    }

    private Contact getSelectedContact() {
        int viewRow = contactsView.getTable().getSelectedRow();
        if (viewRow < 0) return null;

        int modelRow = contactsView.getTable().convertRowIndexToModel(viewRow);
        return contactsView.getTableModel().getAt(modelRow);
    }

    // util para regex
    private static final class PatternUtil {
        static String escapeRegex(String input) {
            // Escape básico para que el texto del usuario no rompa el regex
            return input.replace("\\", "\\\\")
                    .replace(".", "\\.")
                    .replace("*", "\\*")
                    .replace("+", "\\+")
                    .replace("?", "\\?")
                    .replace("^", "\\^")
                    .replace("$", "\\$")
                    .replace("{", "\\{")
                    .replace("}", "\\}")
                    .replace("(", "\\(")
                    .replace(")", "\\)")
                    .replace("|", "\\|")
                    .replace("[", "\\[")
                    .replace("]", "\\]");
        }
    }
}