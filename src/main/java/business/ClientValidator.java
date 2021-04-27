package business;

import model.entity.Clients;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Clasa pentru validatea datelor din client.
 *
 * Campurile care trebuie sa fie validate sunt :
 *          e-mail-ul (care trebuie sa fie litere@host.domeniu)
 *
 */
public class ClientValidator implements Validator<Clients>{
    public static final String eMailPattern="(@[A-Z]+\\.[A-Z]{1,10})$";
    /**
     * Verifica daca e-mail-ul introdus este corect prin pattern matching
     * @param email sirul de caractere de verificat
     * @throws BadInputException daca nu se respecta pattern-ul (litere@host.domeniu)
     */
    public void validateEmail(String email) throws BadInputException {
        Pattern pattern=Pattern.compile(eMailPattern,Pattern.CASE_INSENSITIVE);
        Matcher m=pattern.matcher(email);
        if(!m.find())
            throw new BadInputException("eMail","eMail must be letters@host.domain");
        if(!email.endsWith(m.group()))
                throw new BadInputException("eMail","eMail must be letters@host.domain");
    }
}
