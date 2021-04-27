package model.misc;

import java.lang.annotation.*;

@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.FIELD,ElementType.LOCAL_VARIABLE})
@Documented
/**
 * O adnotare care indica faptul ca un camp nu mai poate fi editat din motive logice (exemplu: ID-ul autoincrementat, data comenzii)
 * Folosita pentru a ignora astfel de atribute in extragerea campurilor pentru editare
 */
public @interface NonEditable {

}
