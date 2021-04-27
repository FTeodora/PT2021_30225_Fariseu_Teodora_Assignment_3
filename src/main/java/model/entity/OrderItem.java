package model.entity;

import model.misc.NonEditable;

/**
 * Clasa corespondenta tabelului pentru produsele din comenzi. Variabilele instanta sunt denumite la fel ca atributele pentru
 * <br>
 * campuri:
 * <ul>
 *     <li>productID - long</li>
 *     <li>orderID - long @NonEditable</li>
 *     <li>requestedQuantity - int @NonEditable</li>
 *
 * </ul>
 * <br>
 * Pentru fiecare camp exista cate un setter si getter
 */
public class OrderItem extends Product{
    @NonEditable
    private long productID;
    @NonEditable
    private long orderID;
    private int requestedQuantity;
    public long getProductID() {
        return productID;
    }
    public void setProductID(long productID) {
        this.productID = productID;
    }
    public long getOrderID() {
        return orderID;
    }
    public void setOrderID(long orderID) {
        this.orderID = orderID;
    }
    public int getRequestedQuantity() {
        return requestedQuantity;
    }
    public void setRequestedQuantity(int requestedQuantity) {
        this.requestedQuantity = requestedQuantity;
    }
}
