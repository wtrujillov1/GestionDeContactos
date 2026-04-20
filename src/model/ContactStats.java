package model;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class ContactStats {
    public static int total(List<Contact> contacts) {
        return contacts.size();
    }

    public static long favorites(List<Contact> contacts) {
        return contacts.stream().filter(Contact::isFavorite).count();
    }

    public static Map<String, Long> byCategory(List<Contact> contacts) {
        return contacts.stream()
                .collect(Collectors.groupingBy(
                        c -> c.getCategory() == null ? "Sin categoría" : c.getCategory(),
                        Collectors.counting()
                ));
    }
}