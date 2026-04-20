package view;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

public class StatsPanel extends JPanel {
    private JLabel lblTotal;
    private JLabel lblFav;
    private JTextArea txtByCategory;

    public StatsPanel() {
        setBorder(new EmptyBorder(10, 10, 10, 10));
        setLayout(new BorderLayout(10, 10));

        JPanel top = new JPanel(new GridLayout(1, 2, 10, 10));
        lblTotal = new JLabel("Total: 0");
        lblFav = new JLabel("Favoritos: 0");
        top.add(lblTotal);
        top.add(lblFav);

        txtByCategory = new JTextArea();
        txtByCategory.setEditable(false);
        txtByCategory.setFont(new Font(Font.MONOSPACED, Font.PLAIN, 12));

        add(top, BorderLayout.NORTH);
        add(new JScrollPane(txtByCategory), BorderLayout.CENTER);
    }

    public void setTotal(int total) { lblTotal.setText("Total: " + total); }
    public void setFavorites(long fav) { lblFav.setText("Favoritos: " + fav); }
    public void setByCategoryText(String text) { txtByCategory.setText(text); }
}