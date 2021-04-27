package business;

import model.entity.Orders;
import model.misc.ProductStatus;

/**
 * Clasa care verifica corectitudinea datelor pentru editarea unei comenzi
 */
public class OrdersValidator implements Validator<Orders>{
    public ProductStatus validateStatus(String status)throws BadInputException{
        ProductStatus rez=ProductStatus.toObject(status);
        if(rez!=null)
            throw new BadInputException("stat","Not a valid status. Valid statuses are CONFIRMED,SHIPPED,DELIVERED");
        return rez;
    }
}
