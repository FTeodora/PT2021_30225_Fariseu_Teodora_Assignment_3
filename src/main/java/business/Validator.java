package business;

import java.text.ParseException;
import java.text.SimpleDateFormat;

/**
 * Interfata pentru verificarea corectitudinii datelor
 * De asemenea, contine o metoda pentru verificarea formatului datei (yyyy-MM-dd)
 * deoarece acesta este general pentru fiecare atribut de timp Date al tabelului
 * @param <T> tabelul pentru care se face validarea
 */
public interface Validator<T> {
    SimpleDateFormat dateFormat=new SimpleDateFormat("yyyy-MM-dd");
    String decimalFormat="[\\d]{1,13}\\.[\\d]{2}";
    /**
     * Verifica daca data dintr-un String are formatul corect
     * @param date sirul sursa
     * @return un obiect de clasa java.sql.Date obtinut din sirul sursa
     * @throws BadInputException daca formatul din sursa este incorect
     */
    default java.sql.Date parseDate(String date) throws BadInputException {
        try{
            return new java.sql.Date(dateFormat.parse(date).getTime());
        } catch (ParseException e) {
            throw new BadInputException("date","Incorrect date format. Date format must be yyyy-MM-dd");
        }
    }
}

