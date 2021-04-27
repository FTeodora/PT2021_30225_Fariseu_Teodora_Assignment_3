package presentation.view.utility.tables;

import business.BadInputException;
import business.Validator;
import dataAccess.dao.AbstractDAO;
import model.misc.AutoCompleted;
import model.misc.NonEditable;
import presentation.view.windows.AppWindow;

import javax.swing.*;
import javax.swing.border.LineBorder;
import javax.swing.table.TableCellRenderer;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.beans.IntrospectionException;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;

/**
 * Clasa care se ocupa cu personalizarea tabelelor pentru afisarea, editarea, cautarea, adaugarea si stergerea de informatii
 * In tabelele incluse sunt integrate si butoanele pentru operatii CRUD, puse in tabel cu un TableCellRenderer
 * In functie de atributele claselor pastrate in spate, fiecare tabela poate contine clase interne pentru TableCellRenderer si TableCellEditor personalizate
 * @param <T> tipul de obiecte retinut in tabel
 */
public class InfosTable<T> {
    protected JTable table;
    protected AbstractDAO<T> abstractDAO;
    protected Class<T> type;
    protected JScrollPane parent;
    protected String ID;
    protected int IDPlace;
    protected ArrayList<String> fieldNames;
    protected ArrayList<Object> objects;
    protected Validator<T> validator;
    public InfosTable(){ }
    public InfosTable(AbstractDAO<T> abstractDAO){
        this.abstractDAO=abstractDAO;
        type=abstractDAO.getType();
        ArrayList<T> allItems=(ArrayList<T>) abstractDAO.selectAll();
        refreshTable(allItems);
        colorTable();
        this.table.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);

    }
    /**
     * Extrage informatiile de pe o linie sub forma de obiect de tipul stocat in tabel
     * @param row randul de pe care se extrage
     * @return un obiect cu informatiile obtinute
     * @throws BadInputException daca se gasesc informatii eronate
     */
    protected T extractObject(int row) throws BadInputException{
        T instance=null;
        try {
            instance=type.newInstance();
            int inc=0;
            for(Field i:abstractDAO.getType().getDeclaredFields()){

                if(i.getAnnotationsByType(AutoCompleted.class).length==0)
                {
                    if(table.getValueAt(row,inc).toString().trim().equals(""))
                        throw new BadInputException(i.getName(),"Field is empty");
                    if(i.getType().equals(String.class)){
                        Object value=table.getValueAt(row,inc);
                        PropertyDescriptor propertyDescriptor=new PropertyDescriptor(i.getName(),abstractDAO.getType());
                        Method method=propertyDescriptor.getWriteMethod();
                        method.invoke(instance,value);
                    }
                }
                inc++;
            }
        } catch (InstantiationException | IntrospectionException | InvocationTargetException | IllegalAccessException e) {
            e.printStackTrace();
        }
        return instance;
    }
    /**
     * Extrage campurile de pe o linie sub forma unor liste de nume si valori retinute ca variabile instanta in clasa InfosTable
     * @param row linia de pe care se extrag informatiile
     * @param ignoreNonEditable valoare booeleana care spune daca se ignora sau nu campurile cu adnotarea @NonEditable
     * @throws BadInputException daca informatiile dintr-un camp sunt eronate
     */
    protected void extractObjectIgnoreNull(int row,boolean ignoreNonEditable) throws BadInputException {
        this.fieldNames=new ArrayList<>();
        this.objects=new ArrayList<>();
        T instance;
        try {
            instance=type.newInstance();
            int inc=0;
            for(Field i:abstractDAO.getType().getDeclaredFields()){
                i.setAccessible(true);
                if(table.getValueAt(row,inc)!=null&&!table.getValueAt(row,inc).toString().trim().equals("")&&i.getType().equals(String.class)&&
                !(ignoreNonEditable&&i.getAnnotationsByType(NonEditable.class).length!=0)){
                    fieldNames.add(i.getName());
                    Object value=table.getValueAt(row,inc);
                    PropertyDescriptor propertyDescriptor=new PropertyDescriptor(i.getName(),abstractDAO.getType());
                    Method method=propertyDescriptor.getWriteMethod();
                    method.invoke(instance,value);
                    this.objects.add(i.get(instance));
                }
                inc++;
            }
        } catch (InstantiationException | IntrospectionException | InvocationTargetException | IllegalAccessException e) {
            throw new BadInputException("Err","undefined");
        }
    }
    public JTable getTable(){return this.table;}
    public void refreshTable(ArrayList<T> allItems){
        ArrayList<String> columns=(ArrayList<String>)abstractDAO.getFieldNames();
        columns.add(" ");
        columns.add(" ");
        Object[][] rows=new Object[allItems.size()+1][];
        for(int i=0;i<allItems.size();i++){
            ArrayList<Object> rowItem=(ArrayList<Object>) abstractDAO.getValues(allItems.get(i));
            rowItem.add("EDIT");
            rowItem.add("DELETE");
            rows[i]=rowItem.toArray();
        }
        rows[allItems.size()]=new Object[columns.size()];
        for(int i=0;i<columns.size()-2;i++)
            rows[allItems.size()][i]= "";
        rows[allItems.size()][columns.size()-2]= "ADD";
        rows[allItems.size()][columns.size()-1]= "SEARCH";
        TableCellRenderer buttonRenderer=new ButtonRenderer();
        table=new JTable(rows,columns.toArray()){
            @Override
            public boolean isCellEditable(int row,int column){
                return column < (columns.size() - 2);
            }
            @Override
            public TableCellRenderer getCellRenderer(int row, int column) {
                if (column>=(columns.size()-2)) {
                    return buttonRenderer;
                }
                return super.getCellRenderer(row, column);
            }
        };
        table.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                JTable target = (JTable)e.getSource();
                int row = target.getSelectedRow();
                int column = target.getSelectedColumn();
                if(!target.isCellEditable(row,column)){
                    boolean success=true;
                   try{
                       if(row==target.getRowCount()-1)
                       {
                           if(column==target.getColumnCount()-2){
                               //butonul de insert
                              long inserted=abstractDAO.insertItem(InfosTable.this.extractObject(row));
                               success=inserted!=-1;
                           }
                           else{
                               //cautare
                               InfosTable.this.extractObjectIgnoreNull(row,false);
                               refreshTable((ArrayList<T>) abstractDAO.searchAll(InfosTable.this.fieldNames,InfosTable.this.objects));
                           }
                       }else{
                           //editare
                           if(column==target.getColumnCount()-2){
                               InfosTable.this.extractObjectIgnoreNull(row,true);
                               objects.add(InfosTable.this.extractID(row));
                               success=abstractDAO.updateItem(InfosTable.this.fieldNames,InfosTable.this.objects,ID);
                           }
                           else{
                               //stergere
                               success=abstractDAO.deleteByID(ID,InfosTable.this.extractID(row));
                           }
                       }
                   }catch (BadInputException er){
                       success=false;
                       JOptionPane.showMessageDialog(null,er.getMessage(),"Err",JOptionPane.ERROR_MESSAGE);
                   }
                   if(success)
                       JOptionPane.showMessageDialog(null,"Operation executed successfully","Success",JOptionPane.INFORMATION_MESSAGE);
                   else
                       JOptionPane.showMessageDialog(null,"Operation failed","Error",JOptionPane.ERROR_MESSAGE);

                }
            }
        });
        colorTable();
        if(parent!=null){
            parent.remove(table);
            parent.setViewportView(table);
            parent.validate();
            parent.repaint();
        }
    }
    protected void colorTable(){
        table.setBackground(AppWindow.HIGHLIGHT_COLOR);
        table.setForeground(AppWindow.SHADE_COLOR);
        table.setFont(AppWindow.TABLE_FONT);

        table.getTableHeader().setBackground(AppWindow.SHADE_COLOR);
        table.getTableHeader().setForeground(AppWindow.HIGHLIGHT_COLOR);
        table.getTableHeader().setFont(AppWindow.TABLE_FONT);
        table.setMinimumSize(new Dimension(900,900));
    }
    public void setParent(JScrollPane parent) {
        this.parent = parent;
    }
    protected Object extractID(int row){
        return null;
    }
    static class ButtonRenderer extends JButton implements TableCellRenderer {
        @Override
        public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
            String text=value.toString();
            super.setText(text);
            super.setBackground(AppWindow.DARK_COLOR);
            super.setForeground(AppWindow.HIGHLIGHT_COLOR);
            super.setBorder(new LineBorder(AppWindow.HIGHLIGHT_COLOR,1));
            return this;
        }
    }
}
