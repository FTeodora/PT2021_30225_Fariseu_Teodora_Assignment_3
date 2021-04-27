package model.entity;

import model.misc.AutoCompleted;
import model.misc.NonEditable;

import java.sql.Date;

/**
 * Clasa corespondenta tabelului pentru clienti. Variabilele instanta sunt denumite la fel ca atributele pentru
 * <br>
 * campuri:
 * <ul>
 *     <li>id - long @AutoCompleted @NonEditable</li>
 *     <li>firstName - String</li>
 *     <li>lastName - String</li>
 *     <li>dateOfBirth - Date</li>
 *     <li>eMail - String</li>
 * </ul>
 * <br>
 * Pentru fiecare camp exista cate un setter si getter
 */
public class Clients {
    @AutoCompleted
    @NonEditable
    private long id;
    private String firstName;
    private String lastName;
    private Date dateOfBirth;
    private String eMail;
    public long getId() {
        return id;
    }
    public void setId(long id) {
        this.id = id;
    }
    public String getFirstName() {
        return firstName;
    }
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }
    public String getLastName() {
        return lastName;
    }
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }
    public Date getDateOfBirth() {
        return dateOfBirth;
    }
    public void setDateOfBirth(Date dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }
    public String getEMail() {
        return eMail;
    }
    public void setEMail(String eMail) {
        this.eMail = eMail;
    }
}
