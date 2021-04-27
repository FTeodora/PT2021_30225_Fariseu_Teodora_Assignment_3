package dataAccess;

import java.sql.*;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Clasa pentru stabilirea conexiunii, organizata dupa Singleton Pattern
 * Contine metode pentru stabilirea si inchiderea unei conexiuni, precum si inchiderea unui Statement sau ResultSet
 */
public class ConnectionFactory {
    private static final Logger LOGGER=Logger.getLogger(ConnectionFactory.class.getName());
    private static final String DRIVER="com.mysql.cj.jdbc.Driver";
    private static final String DB_URL="jdbc:mysql://localhost:3306/warehouse";
    private static final String USER="root";
    private static final String PASS="root";
    private static ConnectionFactory singleInstance=new ConnectionFactory();
    private ConnectionFactory(){
        try{
            Class.forName(DRIVER);
        }catch(ClassNotFoundException e){
           LOGGER.log(Level.WARNING,"Driver not found");
        }
    }
    private Connection createConnection(){
        try {
            return DriverManager.getConnection(DB_URL + "?useSSL=false", USER,PASS);
        } catch (SQLException e) {
            LOGGER.log(Level.WARNING,"Couldn't create connection with: "+DB_URL + "?useSSL=false"+ USER+ PASS+"\nReason: "+e.getMessage());
        }
        return null;
    }
    public static Connection getConnection(){
    return ConnectionFactory.singleInstance.createConnection();
    }
    public static void close(Connection connection){
        if(connection!=null){
            try {
                connection.close();
            } catch (SQLException throwable) {
                LOGGER.log(Level.WARNING,"Error closing Connection");
            }
        }
    }
    public static void close(ResultSet resultSet){
        if(resultSet!=null){
            try{
                resultSet.close();
            } catch (SQLException throwable) {
                LOGGER.log(Level.WARNING,"Error closing ResultSet");
            }
        }
    }
    public static void close(Statement statement){
        if(statement!=null){
            try{
                statement.close();
            } catch (SQLException throwable) {
                LOGGER.log(Level.WARNING,"Error closing Statement");
            }
        }
    }
}
