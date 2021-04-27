package dataAccess.dao;

import dataAccess.ConnectionFactory;
import dataAccess.QueryFactory;
import model.misc.AutoCompleted;
import model.misc.ProductStatus;

import java.beans.IntrospectionException;
import java.beans.PropertyDescriptor;
import java.lang.reflect.*;
import java.lang.reflect.ParameterizedType;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Clasa parametrizata pentru metode generalizate de formulare a interogarilor SQL
 * @param <T> Clasa din care se obtine tabelul si campurile pentru a face interogari
 */
public class AbstractDAO<T>{

    protected static final Logger LOGGER= Logger.getLogger(AbstractDAO.class.getName());
    private final Class<T> type;
    @SuppressWarnings("unchecked")
    public AbstractDAO(){
        this.type=(Class<T>)((ParameterizedType)getClass().getGenericSuperclass()).getActualTypeArguments()[0];
    }
    @org.jetbrains.annotations.NotNull
    private String createSelectQuery(){
        return QueryFactory.createSelectQuery(type.getSimpleName());
    }
    @org.jetbrains.annotations.NotNull
    protected String createSelectQuery(String field){
        return QueryFactory.createSelectQuery(type.getSimpleName(),field);
    }
    @org.jetbrains.annotations.NotNull
    private String createDeleteQuery(String field){
        return QueryFactory.createDeleteQuery(type.getSimpleName(),field);
    }
    @org.jetbrains.annotations.NotNull
    private String createUpdateQuery(ArrayList<String> fieldNames,String identification){
        return QueryFactory.createUpdateQuery(type.getSimpleName(),fieldNames,identification);
    }
    @org.jetbrains.annotations.NotNull
    private String createInsertQuery(T insertedObject){
        return QueryFactory.createInsertQuery(type.getSimpleName(),insertedObject);
    }
    @org.jetbrains.annotations.NotNull
    private String createSelectQuery(ArrayList<String> fields,ArrayList<Object> values){
        return QueryFactory.createSelectQuery(type.getSimpleName(),fields,values);
    }
    /**
     * Insereaza un obiect intr-un tabel dat de parametrul de tip al clasei AbstractDAO, executand un INSERT in MySQL
     * Se ignora campurile autocompletate (cu adnotarea @AutoCompleted)
     * @param item informatiile de introdus in tabel transmise printr-un obiect de clasa corespunzatoare tabelului din MySQL
     * @return obiectul creat
     */
    public long insertItem(T item){
        Connection connection=null;
        PreparedStatement statement=null;
        ResultSet resultSet=null;
        String query=createInsertQuery(item);
        try{
            connection= ConnectionFactory.getConnection();
            statement= connection.prepareStatement(query,Statement.RETURN_GENERATED_KEYS);
            setParameters(statement,item, AutoCompleted.class);
            if(statement.executeUpdate()==0)
                return -1;
            resultSet=statement.getGeneratedKeys();
            if(resultSet.next())
                return resultSet.getLong(1);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        finally{
            ConnectionFactory.close(connection);
            ConnectionFactory.close(statement);
            ConnectionFactory.close(resultSet);
        }
        return -1;
    }
    /**
     * Modifica datele unui rand din baza de date deja existente, executand un UPDATE in MySQL
     * Campurile si noile valori(+ valoarea de identificare a randului de modificat) sunt transmise prin liste
     * deoarece nu se doreste tot timpul modificarea tuturor campurilor simultan
     * @param names numele campurilor de modificat
     * @param values valorile campurilor de modificat
     * @param id numele coloanei de identificare dupa care se vor modifica datele
     * @return o valoare booleana care indica daca modificarea s-a realizat cu succes
     */
    public boolean updateItem(ArrayList<String> names,ArrayList<Object> values,String id){
        Connection connection=null;
        PreparedStatement statement=null;
        String query=createUpdateQuery(names,id);
        try{
            connection= ConnectionFactory.getConnection();
            statement= connection.prepareStatement(query);
            setParameters(statement,values);
            return statement.executeUpdate()>0;
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        finally{
            ConnectionFactory.close(connection);
            ConnectionFactory.close(statement);
        }
        return false;
    }
    /**
     * Sterge un rand dintr-un tabel, executand un DELETE in MySQL
     * @param field numele coloanei de identificare dupa care se gaseste randul de sters
     * @param value valoarea de identificare dupa care se sterge
     * @return o valoare booleana care indica daca stergerea s-a realizat cu succes
     */
    public boolean deleteByID(String field,Object value){
        Connection connection=null;
        PreparedStatement statement=null;
        String query=createDeleteQuery(field);
        try{
            connection= ConnectionFactory.getConnection();
            statement= connection.prepareStatement(query);
            statement.setObject(1,value);
            return statement.executeUpdate()>0;
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        finally{
            ConnectionFactory.close(connection);
            ConnectionFactory.close(statement);
        }
        return false;
    }
    /**
     * Selecteaza toate valorile dintr-un tabel
     * @return obiectele din tabel sub forma de lista de obiecte de clasa tipului din parametrul lui AbstractDAO
     */
    public List<T> selectAll(){
        Connection connection=null;
        PreparedStatement statement=null;
        ResultSet resultSet=null;
        String query=createSelectQuery();
        try{
            connection=ConnectionFactory.getConnection();
            statement= connection.prepareStatement(query);
            resultSet=statement.executeQuery();
            return createObject(resultSet);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        finally{
            ConnectionFactory.close(connection);
            ConnectionFactory.close(statement);
            ConnectionFactory.close(resultSet);
        }
        return null;
    }
    /**
     * Cauta toate liniile dintr-un tabel dupa un anumit filtru
     * Numele coloanelor si valorile lor se transmit prin liste deoarece nu se cauta intotdeauna
     * @param field numele coloanelor dupa care se filtreaza
     * @param values valorile care se cauta
     * @return o lista cu obiectele gasite
     */
    public List<T> searchAll(ArrayList<String> field,ArrayList<Object> values){
        Connection connection=null;
        PreparedStatement statement=null;
        ResultSet resultSet=null;
        String query=createSelectQuery(field,values);
        try{
            connection=ConnectionFactory.getConnection();
            statement= connection.prepareStatement(query);
            resultSet=statement.executeQuery();
            return createObject(resultSet);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        finally{
            ConnectionFactory.close(connection);
            ConnectionFactory.close(statement);
            ConnectionFactory.close(resultSet);
        }
        return null;
    }
    /**
     * Extrage obiectele corespunzatoare in Java dintr-un rezultat MySQL
     * @param resultSet rezultatul din care se extrage
     * @return lista de obiecte obtinuta
     */
    protected List<T> createObject(ResultSet resultSet){
        List<T> objects=new ArrayList<>();
        try{
            while(resultSet.next()){
                T instance=type.newInstance();
                for(Field i:type.getDeclaredFields()){
                    Object value=resultSet.getObject(i.getName());
                   // System.out.println(value.getClass()+" "+value);
                    if(i.getType().equals(ProductStatus.class))
                        value=ProductStatus.valueOf((String)value);
                    PropertyDescriptor propertyDescriptor=new PropertyDescriptor(i.getName(),type);
                    Method method=propertyDescriptor.getWriteMethod();
                    method.invoke(instance,value);
                }
                objects.add(instance);
            }
        } catch (IllegalAccessException | IntrospectionException | SQLException | InstantiationException | InvocationTargetException e) {
           LOGGER.log(Level.SEVERE,"Error from createObject: "+e.getClass().getSimpleName()+":"+e.getMessage());
        }
        return objects;
    }
    /**
     * Extrage numele variabilelor instanta ale obiectului din parametrul T
     * @return o lista cu numele variabilelor instanta drept String-uri
     */
    public List<String> getFieldNames(){
        List<String> fields=new ArrayList<>();
        for(Field i:type.getDeclaredFields()){
            fields.add(i.getName());
        }
        return fields;
    }
    /**
     * Obtine valorile variabilelor instanta ale unui obiect de clasa parametrului T
     * @param obj obiectul caruia i se extrag valorile
     * @return lista cu valorile obtinute
     */
    public List<Object> getValues(T obj){
        List<Object> fields=new ArrayList<>();
        for(Field i:type.getDeclaredFields()){
            try {
                i.setAccessible(true);
                fields.add(i.get(obj));
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }
        return fields;
    }
    public Class<T> getType(){
        return this.type;
    }
    /**
     * Seteaza parametri pentru un preparedStatement, ignorand valorile cu o anumita adnotare
     * @param statement statement-ul caruia i se seteaza parametri
     * @param values valorile care se seteaza, date ca obiect de clasa parametru
     * @param ignore tipul de adnotare care se ignora
     */
    private void setParameters(PreparedStatement statement,T values,Class ignore){
        int n=1;
        for(Field i:type.getDeclaredFields()){
            i.setAccessible(true);
            if(i.getAnnotationsByType(ignore).length==0){
                try {
                    statement.setObject(n,i.get(values));
                    n++;
                } catch (IllegalAccessException|SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    }
    /**
     * Seteaza valorile unui statement ca valorile dintr-o lista de obiecte
     * @param statement statement-ul caruia i se seteaza valorile
     * @param values valorile care se seteaza, date ca ArrayList de obiecte
     */
    private void setParameters(PreparedStatement statement,ArrayList<Object> values){
        int n=1;
        for(Object i:values){
            if(i instanceof ProductStatus)
                i=i.toString();
                try {
                    statement.setObject(n,i);
                    n++;
                } catch (SQLException e) {
                    e.printStackTrace();
                }
        }
    }
}
