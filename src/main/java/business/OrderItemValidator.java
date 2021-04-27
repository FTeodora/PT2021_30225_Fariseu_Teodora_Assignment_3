package business;

/**
 * Verifica daca datele pentru un produs introdus in comanda sunt corecte
 * Pentru a fi corecte, cantitatea cumparata trebuie sa fie un numar intreg pozitiv nenul, mai mic decat cantitatea de pe stoc
 */
public class OrderItemValidator implements Validator{
    /**
     * Verifica corectitudinea cantitatii introduse
     * @param nr cantitatea sub forma de String pentru verificarea corectitudinii numerice
     * @param maxQuantity cantitatea maxima de pe stoc pentru verificarea limitei superioare
     * @return numarul obtinut din String-ul transmis
     * @throws BadInputException daca in String nu este un numar, daca cantitatea e negativa sau mai mare decat maxQuantity
     */
    public int validateQuantity(String nr,int maxQuantity) throws BadInputException {
        int n;
        try{
            n=Integer.parseInt(nr);
        }catch (NumberFormatException e){
            throw new BadInputException("requestedQuantity","quantity must be a positive integer");
        }
        if(n<0)
            throw new BadInputException("requestedQuantity","quantity must be a positive integer");
        if(n>maxQuantity)
            throw new BadInputException("requestedQuantity","Not enough products in stock(Only "+maxQuantity+" left )");
        return n;
    }
}
