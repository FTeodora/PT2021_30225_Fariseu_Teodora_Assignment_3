package model.misc;

import java.lang.annotation.*;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
@Documented
/**
 *  O adnotare care indica faptul ca atributul tabelului corespondent in MySQL este completat automat (fie prin triggere, fie prin auto_incremented)
 *  Folosita pentru a ignora aceste atribute la inserarea in tabel
 */
public @interface AutoCompleted {

}
