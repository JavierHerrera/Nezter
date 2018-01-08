package myapps.jherrera.nezter.objects;

public class Product {

    private int id;
    private String name;
    private int stock;
    private boolean active;

    //Mensaje para los Toast

    public Product(int id, String name, int stock, boolean active) {
        this.id = id;
        this.name = name;
        this.stock = stock;
        this.active = active;
    }

    public Product(String name, int stock){
        this.name = name;
        this.stock = stock;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getStock() {
        return stock;
    }

    public void setStock(int stock) {
        this.stock = stock;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }
}
