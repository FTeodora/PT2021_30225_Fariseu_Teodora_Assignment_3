package model.entity;

import model.misc.AutoCompleted;
import model.misc.NonEditable;
import model.misc.ProductStatus;

import java.math.BigDecimal;
import java.util.Date;
/**
 * Clasa corespondenta tabelului pentru comenzi. Variabilele instanta sunt denumite la fel ca atributele pentru
 * <br>
 * campuri:
 * <ul>
 *     <li>id - long @AutoCompleted</li>
 *     <li>clientID - long</li>
 *     <li>state - ProductStatus @AutoCompleted</li>
 *     <li>orderDate - Date @NonEditable @AutoCompleted</li>
 *     <li>courier - String</li>
 *     <li>deliveryAddress - String </li>
 *     <li>totalPrice - BigDecimal @NonEditable</li>
 * </ul>
 * <br>
 * Pentru fiecare camp exista cate un setter si getter
 */
public class Orders {
    @AutoCompleted
    private long id;
    private long clientID;
    @AutoCompleted
    private ProductStatus state;
    @NonEditable
    @AutoCompleted
    private Date orderDate;
    private String courier;
    private String deliveryAddress;
    @NonEditable
    private BigDecimal totalPrice;
    public void setClientID(long clientID) {
        this.clientID = clientID;
    }
    public long getClientID(){ return this.clientID;}
    public String getDeliveryAddress() {
        return deliveryAddress;
    }
    public void setDeliveryAddress(String deliveryAddress) {
        this.deliveryAddress = deliveryAddress;
    }
    public long getId() {
        return id;
    }
    public void setId(long id) {
        this.id = id;
    }
    public ProductStatus getState() {
        return state;
    }
    public void setState(ProductStatus state) {
        this.state = state;
    }
    public Date getOrderDate() {
        return orderDate;
    }
    public void setOrderDate(Date orderDate) {
        this.orderDate = orderDate;
    }
    public String getCourier() {
        return courier;
    }
    public void setCourier(String courier) {
        this.courier = courier;
    }
    public BigDecimal getTotalPrice() {
        return totalPrice;
    }
    public void setTotalPrice(BigDecimal totalPrice) {
        this.totalPrice = totalPrice;
    }
}
