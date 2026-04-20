package model;

public class Contact {
    private long id;
    private String name;
    private String phone;
    private String email;
    private boolean favorite;
    private String category;

    public Contact(long id, String name, String phone, String email, boolean favorite, String category) {
        this.id = id;
        this.name = name;
        this.phone = phone;
        this.email = email;
        this.favorite = favorite;
        this.category = category;
    }

    public long getId() { return id; }
    public String getName() { return name; }
    public String getPhone() { return phone; }
    public String getEmail() { return email; }
    public boolean isFavorite() { return favorite; }
    public String getCategory() { return category; }

    public void setName(String name) { this.name = name; }
    public void setPhone(String phone) { this.phone = phone; }
    public void setEmail(String email) { this.email = email; }
    public void setFavorite(boolean favorite) { this.favorite = favorite; }
    public void setCategory(String category) { this.category = category; }
}