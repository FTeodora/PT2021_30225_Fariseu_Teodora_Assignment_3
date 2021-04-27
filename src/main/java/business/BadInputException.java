package business;

/**
 *  Clasa pentru exceptiile de introducere incorecta a datelor
 */
public class BadInputException extends Exception{
    /**
     *
     * @param field campul cu date eronate
     * @param reason motivul pentru care datele sunt eronate
     */
    public BadInputException(String field,String reason){
        super(field+": "+reason);
    }
}
