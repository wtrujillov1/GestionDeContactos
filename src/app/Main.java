package app;

import controller.MainController;
import model.ContactRepository;
import view.MainFrame;

import javax.swing.*;

public class Main {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            ContactRepository repo = new ContactRepository();
            MainFrame frame = new MainFrame();
            new MainController(repo, frame);

            frame.setLocationRelativeTo(null);
            frame.setVisible(true);
        });
    }
}