package business;

import java.math.BigDecimal;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Verifica daca se introduc produse cu date valide
 * Campurile care trebuie verificare sunt:
 *          price(trebuie sa fie un numar pozitiv cu 2 zecimale si maxim 15 cifre in total)
 *          quantity (trebuie sa fie un numar intreg >=0)
 */
public class ProductValidator implements Validator{
    /**
     * Verifica corectitudinea informatiei pentru pret
     * @param price pretul de verificat, transmis ca String
     * @return numarul obtinut din sirul transmis
     * @throws BadInputException daca nu se poate parsa sau daca numarul e negativ
     */
    public BigDecimal validatePrice(String price) throws BadInputException{
        Pattern pattern=Pattern.compile(decimalFormat);
        Matcher m=pattern.matcher(price);
        if(!m.find())
            throw new BadInputException("price","Price must be a positive number with maximum 13 digits and 2 decimals");
        if(!m.group().equals(price))
            throw new BadInputException("price","Price must be a positive number with maximum 13 digits and 2 decimals");
        return new BigDecimal(price);
    }

    /**
     * Validarea informatiilor pentru cantitate prin incercarea de a parsa la Integer
     * @param quantity informatia de verificat transmisa ca String
     * @return intregul obtinut din parsare
     * @throws BadInputException daca nu se poate parsa intreg, sau daca numarul e negativ
     */
    public int validateQuantity(String quantity) throws BadInputException{
        int nr;
        try{
            nr=Integer.parseInt(quantity);
        }
        catch (NumberFormatException e){
            throw new BadInputException("quantity","quantity must be an integer");
        }
        if(nr<0)
            throw new BadInputException("quantity","quantity must be positive or zero");
        return nr;
    }
}
