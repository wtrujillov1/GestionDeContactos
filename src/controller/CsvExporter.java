package controller;

import model.Contact;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.List;

public class CsvExporter {
    public static void export(File file, List<Contact> contacts) throws IOException {
        try (Writer w = new OutputStreamWriter(new FileOutputStream(file), StandardCharsets.UTF_8);
             BufferedWriter bw = new BufferedWriter(w)) {

            bw.write("id,nombre,telefono,email,favorito,categoria");
            bw.newLine();

            for (Contact c : contacts) {
                bw.write(c.getId() + "," +
                        csv(c.getName()) + "," +
                        csv(c.getPhone()) + "," +
                        csv(c.getEmail()) + "," +
                        (c.isFavorite() ? "true" : "false") + "," +
                        csv(c.getCategory()));
                bw.newLine();
            }
        }
    }

    private static String csv(String s) {
        if (s == null) return "";
        String v = s.replace("\"", "\"\"");
        if (v.contains(",") || v.contains("\"") || v.contains("\n")) {
            return "\"" + v + "\"";
        }
        return v;
    }
}