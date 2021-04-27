package model.entity;

import model.misc.AutoCompleted;

import java.math.BigDecimal;

/**
 * Clasa corespondenta tabelului pentru produsele din stoc. Variabilele instanta sunt denumite la fel ca atributele pentru
 * <br>
 * campuri:
 * <ul>
 *     <li>ID - long @AutoCompleted</li>
 *     <li>price - BigDecimal</li>
 *     <li>category - String</li>
 *     <li>brand - String</li>
 *     <li>quantity - int</li>
 * </ul>
 * <br>
 * Pentru fiecare camp exista cate un setter si getter
 */
public class Product {
    @AutoCompleted
    private long ID;
    private String productName;
    private BigDecimal price;
    private String category;
    private String brand;
    private int quantity;
    public long getID() {
        return ID;
    }
    public void setID(long ID) {
        this.ID = ID;
    }
    public String getProductName() {
        return productName;
    }
    public void setProductName(String productName) {
        this.productName = productName;
    }
    public BigDecimal getPrice() {
        return price;
    }
    public void setPrice(BigDecimal price) {
        this.price = price;
    }
    public String getCategory() {
        return category;
    }
    public void setCategory(String category) {
        this.category = category;
    }
    public String getBrand() {
        return brand;
    }
    public void setBrand(String brand) {
        this.brand = brand;
    }
    public int getQuantity() {
        return quantity;
    }
    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
}
