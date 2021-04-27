package dataAccess;

import model.misc.AutoCompleted;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * O clasa care genereaza statement-uri generale dupa pattern-ul sintaxei din MySQL,
 * Organizata dupa pattern-ul singleton
 */
public class QueryFactory {
    private static final Logger LOGGER=Logger.getLogger(QueryFactory.class.getName());
    private static final QueryFactory singleInstance=new QueryFactory();
    public QueryFactory getInstance(){
        return singleInstance;
    }
    /**
     * Genereaza o interogare de SELECT care selecteaza toate campurile din tabelul din tipul parametrizat
     * @return "SELECT * FROM table"
     */
    public static String createSelectQuery(String table){
        return "SELECT * FROM "+table;
    }
    /**
     * Se genereaza un SELECT care filtreaza exact (cu =) dupa un camp anume
     * @param field campul dupa care se filtreaza
     * @return "SELECT * FROM table WHERE field=?"
     */
    public static String createSelectQuery(String table, String field){
        return "SELECT * FROM " +
                table +
                " WHERE " +
                field +
                "=?";
    }
    /**
     * Se genereaza un query de stergere dupa un identificator
     * @param field identificatorul dupa care se cauta coloana stearsa
     * @return "DELETE FROM tabel WHERE field=?"
     */
    public static String createDeleteQuery(String table,String field){
        return "DELETE FROM "+
                table +
                " WHERE "+
                field+
                "=?";
    }
    /**
     * Se genereaza un query generic de UPDATE pentru anumite campuri
     * @param fieldNames campurile care se vor modifica
     * @param identification identificatorul dupa care se modifica acestea
     * @return "UPDATE tabel SET valoare1=?,valoare2=?,...valoareN=? WHERE identificator=?"
     */
    @org.jetbrains.annotations.NotNull
    public static String createUpdateQuery(String table,ArrayList<String> fieldNames, String identification){
        StringBuilder query=new StringBuilder();
        query.append("UPDATE ");
        query.append(table);
        query.append(" SET ");
        for(String i:fieldNames){
            query.append(i);
            query.append("=?, ");
        }
        query=new StringBuilder(query.substring(0,query.length()-2));
        query.append(" WHERE ");
        query.append(identification);
        query.append("=?");
        LOGGER.log(Level.INFO,"Query created: "+query);
        return query.toString();
    }
    /**
     * Se genereaza un query de INSERT generic dupa un obiect transmis ca parametru. Se ignora campurile incrementate automat
     * @param insertedObject datele de inserat transmise ca obiectul respectiv
     * @return "INSERT INTO tabel(camp1,camp2,...,campN) VALUES(val1,val2,...,valN)
     */
    @org.jetbrains.annotations.NotNull
    public static String createInsertQuery(String table,Object insertedObject){
        StringBuilder query=new StringBuilder();
        query.append(" INSERT INTO ");
        query.append(table);
        query.append("(");
        for(Field i:insertedObject.getClass().getDeclaredFields()){
            if(i.getAnnotationsByType(AutoCompleted.class).length==0)
            {
                query.append(i.getName());
                query.append(",");
            }
        }
        query=new StringBuilder(query.substring(0,query.length()-1));
        query.append(") values(");
        for(Field i:insertedObject.getClass().getDeclaredFields()){
            if(i.getAnnotationsByType(AutoCompleted.class).length==0)
            {
                query.append("?,");
            }
        }
        query=new StringBuilder(query.substring(0,query.length()-1));
        query.append(")");
        LOGGER.log(Level.INFO,"Query created: "+query);
        return query.toString();
    }
    /**
     * Genereaza un select generic folosind LIKE. Deoarece LIKE este incadrat intre ghilimele, semnul intrebarii de la
     * parametrul pentru PreparedStatement va fi considerat drept caracter normal
     * @param fields numele campurilor
     * @param values valorile dupa care se cauta
     * @return "SELECT * FROM tabel WHERE field1 LIKE 'value1' AND field2 LIKE 'value2'..."
     */
    public static String createSelectQuery(String table,ArrayList<String> fields,ArrayList<Object> values){
        if(fields.size()==0||values.size()==0)
            return createSelectQuery(table);
        StringBuilder query=new StringBuilder();
        query.append(" SELECT * FROM ");
        query.append(table);
        query.append(" WHERE ");
        Iterator<Object> it=values.iterator();
        for(String i:fields){
            query.append(i);
            query.append(" LIKE '");
            query.append(it.next());
            query.append("' AND ");
        }
        query=new StringBuilder(query.substring(0,query.length()-4));
        LOGGER.log(Level.INFO,"Query created: "+query);
        return query.toString();
    }
}
