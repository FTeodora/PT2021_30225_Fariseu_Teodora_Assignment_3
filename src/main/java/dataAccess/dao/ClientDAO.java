package dataAccess.dao;

import dataAccess.ConnectionFactory;
import model.entity.Clients;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Clasa pentru interogari specifice pentru tabela CLients
 */
public class ClientDAO extends AbstractDAO<Clients>{
    /**
     * Obtine numele si prenumele unui client
     * @param ID ID-ul clientului dorit
     * @return un obiect Clients cu valorile cerute
     */
    public Clients getName(long ID){
            Connection connection=null;
            PreparedStatement statement=null;
            ResultSet resultSet=null;
            String query=createSelectQuery("ID");
            try{
                connection= ConnectionFactory.getConnection();
                statement= connection.prepareStatement(query);
                statement.setLong(1,ID);
                resultSet=statement.executeQuery();
                return createObject(resultSet).get(0);
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
}
