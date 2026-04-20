package model;

import javax.swing.table.AbstractTableModel;
import java.util.ArrayList;
import java.util.List;

public class ContactTableModel extends AbstractTableModel {
    private final String[] columns = {"ID", "Nombre", "Teléfono", "Email", "Favorito", "Categoría"};
    private List<Contact> rows = new ArrayList<>();

    public void setRows(List<Contact> rows) {
        this.rows = new ArrayList<>(rows);
        fireTableDataChanged();
    }

    public Contact getAt(int modelRow) {
        return rows.get(modelRow);
    }

    public List<Contact> getAll() {
        return new ArrayList<>(rows);
    }

    @Override public int getRowCount() { return rows.size(); }
    @Override public int getColumnCount() { return columns.length; }
    @Override public String getColumnName(int column) { return columns[column]; }

    @Override
    public Class<?> getColumnClass(int columnIndex) {
        return switch (columnIndex) {
            case 0 -> Long.class;
            case 4 -> Boolean.class;
            default -> String.class;
        };
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        Contact c = rows.get(rowIndex);
        return switch (columnIndex) {
            case 0 -> c.getId();
            case 1 -> c.getName();
            case 2 -> c.getPhone();
            case 3 -> c.getEmail();
            case 4 -> c.isFavorite();
            case 5 -> c.getCategory();
            default -> "";
        };
    }

    @Override
    public boolean isCellEditable(int rowIndex, int columnIndex) {
        return false; // edición por formulario/botón, no inline
    }
}