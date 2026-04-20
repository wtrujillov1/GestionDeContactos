package model;

import java.util.*;
import java.util.concurrent.atomic.AtomicLong;

public class ContactRepository {
    private final List<Contact> contacts = new ArrayList<>();
    private final AtomicLong seq = new AtomicLong(1);

    public List<Contact> findAll() {
        return Collections.unmodifiableList(contacts);
    }

    public Contact add(String name, String phone, String email, boolean favorite, String category) {
        Contact c = new Contact(seq.getAndIncrement(), name, phone, email, favorite, category);
        contacts.add(c);
        return c;
    }

    public void update(Contact c, String name, String phone, String email, boolean favorite, String category) {
        c.setName(name);
        c.setPhone(phone);
        c.setEmail(email);
        c.setFavorite(favorite);
        c.setCategory(category);
    }

    public void delete(Contact c) {
        contacts.removeIf(x -> x.getId() == c.getId());
    }

    public Optional<Contact> findById(long id) {
        return contacts.stream().filter(c -> c.getId() == id).findFirst();
    }

    public void seedDemoData() {
        add("Ana Pérez", "555-111-222", "ana@correo.com", true, "Amigos");
        add("Carlos Ruiz", "555-333-444", "carlos@correo.com", false, "Trabajo");
        add("María López", "555-777-888", "maria@correo.com", true, "Familia");
        add("José Martínez", "555-999-000", "jose@correo.com", false, "Otros");
    }
}