package dataAccess.dao;

import dataAccess.ConnectionFactory;
import model.entity.OrderItem;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;

/**
 * Clasa pentru interogarile specifice pe tabelul OrderItem
 */
public class OrderItemDAO extends AbstractDAO<OrderItem>{
    /**
     * Genereaza interogarea pentru obtinerea tuturor produselor dintr-o comanda
     * De asemenea, se obtin campuri suplimentare din produs, completate tot in OrderItem deoarece extinde clasa Product
     * @return interogarea drept String
     */
    private String generateJoinQuery(){
       return "SELECT product.ID,product.productName,product.brand,product.price,orderItem.requestedQuantity \n" +
                "FROM orderItem \n" +
                "INNER JOIN orders ON orderItem.orderID=orders.ID AND orders.ID=?\n" +
                "INNER JOIN product ON orderItem.productID=product.ID";
    }
    /**
     * Obtine toate produsele dintr-o comanda
     * @param orderID ID-ul comenzii careia i se obtin produsele
     * @return o lista cu obiectele obtinute
     */
    public List<OrderItem> getItemsFromOrder(long orderID){
        Connection connection=null;
        PreparedStatement statement=null;
        ResultSet resultSet=null;
        String query=generateJoinQuery();
        LOGGER.log(Level.FINE,query);
        try{
            connection= ConnectionFactory.getConnection();
            statement= connection.prepareStatement(query);
            statement.setLong(1,orderID);
            resultSet=statement.executeQuery();
            return createObject(resultSet);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        finally{
            ConnectionFactory.close(connection);
            ConnectionFactory.close(statement);
            ConnectionFactory.close(resultSet);
        }
        return null;
    }
    /**
     * Extrage un obiect din primul rand al unui ResultSet
     * @param resultSet rezultatul din care se extrage
     * @return obiectul obtinut
     * @throws SQLException pentru eventuale erori
     */
    private OrderItem createItem(ResultSet resultSet) throws SQLException {
        OrderItem orderItem=new OrderItem();
            orderItem.setProductID(resultSet.getLong(1));
            orderItem.setProductName(resultSet.getString(2));
            orderItem.setBrand(resultSet.getString(3));
            orderItem.setPrice(resultSet.getBigDecimal(4));
            orderItem.setRequestedQuantity(resultSet.getInt(5));
        return orderItem;
    }
    /**
     * Extrage lista tuturor obiectelor de tip OrderItem ce pot fi obtinute dintr-un ResultSet
     * @param resultSet rezultatul din care se extrage
     * @return lista de obiecte obtinuta
     */
    @Override
    protected List<OrderItem> createObject(ResultSet resultSet) {
        ArrayList<OrderItem> items=new ArrayList<>();
        try{
        while(resultSet.next())
            items.add(createItem(resultSet));
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return items;
    }
    /**
     * Returneaza header-ul tabelului care va afisa produsele dintr-o comanda
     * @return lista numelor personalizate ale campurilor
     */
    public List<String> getOrderFieldNames() {
        List<String> rez= new ArrayList<>();
        rez.add("Product ID");
        rez.add("Product Name");
        rez.add("Brand");
        rez.add("price/item");
        rez.add("Bought Quantity");
        return rez;
    }
    /**
     * Obtine valorile atributelor unui obiect de tip OrderItem
     * @param obj obiectul caruia i se obtin valorile
     * @return un Array de Object cu valorile obtinute
     */
    public Object[] getOrderFieldValues(OrderItem obj){
        Object[] values=new Object[5];
        values[0]=obj.getProductID();
        values[1]=obj.getProductName();
        values[2]=obj.getBrand();
        values[3]=obj.getPrice();
        values[4]=obj.getRequestedQuantity();
        return values;
    }
}
