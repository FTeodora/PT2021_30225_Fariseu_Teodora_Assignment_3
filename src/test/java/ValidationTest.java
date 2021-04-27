import business.*;;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class ValidationTest {
    @Test
    public void clientEMailValidation(){
        ClientValidator validator=new ClientValidator();
        boolean success=true;
        try{
            (validator).validateEmail("aa.aaa1@aa.a");
        } catch (BadInputException e) {
            success=false;
        }
        assertTrue(success);
    }
    @Test
    public void clientEMailValidation2(){
        ClientValidator validator=new ClientValidator();
        boolean success=false;
        try{
            (validator).validateEmail("aaaaaa");
        } catch (BadInputException e) {
            success=true;
        }
        assertTrue(success);
    }
    @Test
    public void clientEMailValidation3(){
        ClientValidator validator=new ClientValidator();
        boolean success=false;
        try{
            (validator).validateEmail("aaaaaa.aa@aa");
        } catch (BadInputException e) {
            success=true;
        }
        assertTrue(success);
    }
    @Test
    public void dateValidation(){
        ClientValidator validator=new ClientValidator();
        boolean success=true;
        try{
            (validator).parseDate("2000-10-19");
        } catch (BadInputException e) {
            success=false;
        }
        assertTrue(success);
    }
    @Test
    public void dateValidation2(){
        ClientValidator validator=new ClientValidator();
        boolean success=true;
        try{
            (validator).parseDate("2000-5-9");
        } catch (BadInputException e) {
            success=false;
        }
        assertTrue(success);
    }
    @Test
    public void dateValidation3(){
        ClientValidator validator=new ClientValidator();
        boolean success=false;
        try{
            (validator).parseDate("aaaaa");
        } catch (BadInputException e) {
            success=true;
        }
        assertTrue(success);
    }
    @Test
    public void numberValidation(){
        ProductValidator validator=new ProductValidator();
        boolean success=true;
        try{
            (validator).validatePrice("50.12");
        } catch (BadInputException e) {
            success=false;
        }
        assertTrue(success);
    }
    @Test
    public void numberValidation2(){
        ProductValidator validator=new ProductValidator();
        boolean success=false;
        try{
            (validator).validatePrice("-12434325454353534");
        } catch (BadInputException e) {
            success=true;
        }
        assertTrue(success);
    }
    @Test
    public void numberValidation3(){
        ProductValidator validator=new ProductValidator();
        boolean success=false;
        try{
            (validator).validatePrice("123aaaa42a");
        } catch (BadInputException e) {
            success=true;
        }
        assertTrue(success);
    }
    @Test
    public void quantityValidation(){
        ProductValidator validator=new ProductValidator();
        boolean success=true;
        try{
            (validator).validateQuantity("12");
        } catch (BadInputException e) {
            success=false;
        }
        assertTrue(success);
    }
    @Test
    public void quantityValidation2(){
        ProductValidator validator=new ProductValidator();
        boolean success=false;
        try{
            (validator).validateQuantity("-12434325454353534");
        } catch (BadInputException e) {
            success=true;
        }
        assertTrue(success);
    }
    @Test
    public void quantityValidation3(){
        ProductValidator validator=new ProductValidator();
        boolean success=false;
        try{
            (validator).validateQuantity("salut");
        } catch (BadInputException e) {
            success=true;
        }
        assertTrue(success);
    }
    @Test
    public void maxQuantityValidation(){
        OrderItemValidator validator=new OrderItemValidator();
        boolean success=true;
        try{
            (validator).validateQuantity("12",12);
        } catch (BadInputException e) {
            success=false;
        }
        assertTrue(success);
    }
    @Test
    public void maxQuantityValidation2(){
        OrderItemValidator validator=new OrderItemValidator();
        boolean success=false;
        try{
            (validator).validateQuantity("1243432",12);
        } catch (BadInputException e) {
            success=true;
        }
        assertTrue(success);
    }
    @Test
    public void maxQuantityValidation3(){
        OrderItemValidator validator=new OrderItemValidator();
        boolean success=false;
        try{
            (validator).validateQuantity("-1",2);
        } catch (BadInputException e) {
            success=true;
        }
        assertTrue(success);
    }

}
