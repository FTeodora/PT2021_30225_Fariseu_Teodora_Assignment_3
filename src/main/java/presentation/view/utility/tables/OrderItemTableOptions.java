package presentation.view.utility.tables;

import business.BadInputException;
import business.OrderItemValidator;
import dataAccess.dao.AbstractDAO;
import model.entity.OrderItem;
import model.entity.Product;
import presentation.view.utility.NewOrderInput;
import presentation.view.windows.AppWindow;

import javax.swing.*;
import javax.swing.table.TableCellEditor;

import java.awt.*;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.math.BigDecimal;
import java.text.ParseException;
import java.util.ArrayList;

/**
 * Tabelul pentru produsele de selectat pentru o comanda
 */
public class OrderItemTableOptions extends ProductsTable {
    private NewOrderInput source;
    public OrderItemTableOptions(AbstractDAO<Product> abstractDAO, NewOrderInput source) {
        super(abstractDAO);
        validator=new OrderItemValidator();
        refreshTable((ArrayList<Product>) abstractDAO.selectAll());
        this.source=source;

    }
    public void refreshTable(ArrayList<Product> allItems){
        ArrayList<String> columns=(ArrayList<String>)abstractDAO.getFieldNames();
        columns.add("requestedQuantity");
        Object[][] rows=new Object[allItems.size()][];
        for(int i=0;i<allItems.size();i++){
            ArrayList<Object> rowItem=(ArrayList<Object>) abstractDAO.getValues( allItems.get(i));
            rowItem.add("0");
            rows[i]=rowItem.toArray();
        }
        table=new JTable(rows,columns.toArray()){
            @Override
            public boolean isCellEditable(int row,int column){
                return column==(columns.size()-1);
            }
        };
        table.getColumnModel().getColumn(table.getColumnCount()-1).setCellEditor(new RequestedQuantityEditor());
        colorTable();
        if(parent!=null){
            parent.remove(table);
            parent.setViewportView(table);
            parent.validate();
            parent.repaint();
        }
    }
    public ArrayList<OrderItem> getItems(){
        ArrayList<OrderItem> items=new ArrayList<>();
        for(int i=0;i<table.getRowCount();i++){
            if(Integer.parseInt(table.getValueAt(i,table.getColumnCount()-1).toString())>0){
                OrderItem item=new OrderItem();
                item.setProductID(Long.parseLong(table.getValueAt(i,0).toString()));
                item.setRequestedQuantity(Integer.parseInt(table.getValueAt(i,table.getColumnCount()-1).toString()));
                items.add(item);
            }
        }
        return items;
    }
    class RequestedQuantityEditor extends AbstractCellEditor implements TableCellEditor{
        JSpinner spinner;
        @Override
        public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected, int row, int column) {

            spinner=new JSpinner(new SpinnerNumberModel(Integer.parseInt(value.toString()),0,Integer.parseInt(table.getValueAt(row,column-1).toString()),1));
            spinner.getEditor().getComponent(0).setBackground(AppWindow.HIGHLIGHT_COLOR);
            spinner.getEditor().getComponent(0).setForeground(AppWindow.SHADE_COLOR);
            ((JSpinner.DefaultEditor)spinner.getEditor()).getTextField().addPropertyChangeListener(new PropertyChangeListener() {
                @Override
                public void propertyChange(PropertyChangeEvent evt) {
                    JFormattedTextField field = (JFormattedTextField) evt.getSource();
                    if(!field.getText().equals("")){
                        int nr=-1;
                        try {
                            nr=((OrderItemValidator)validator).validateQuantity(field.getText(),Integer.parseInt(table.getValueAt(row,column-1).toString()));
                        } catch (BadInputException badInputException) {
                            JOptionPane.showMessageDialog(null,badInputException.getMessage(),"Err",JOptionPane.ERROR_MESSAGE);
                        }
                        if(nr!=-1){
                            try {
                                spinner.commitEdit();
                            } catch (ParseException e) {
                                e.printStackTrace();
                            }
                        }
                    }
                }
            });
            spinner.addPropertyChangeListener(new PropertyChangeListener() {
                        @Override
                        public void propertyChange(PropertyChangeEvent evt) {
                            BigDecimal newPrice = new BigDecimal(0.00);
                            for (int i = 0; i < table.getRowCount(); i++) {
                                newPrice = newPrice.add(new BigDecimal(table.getValueAt(i, 2).toString()).multiply(new BigDecimal(table.getValueAt(i, table.getColumnCount() - 1).toString())));
                            }
                            source.updateTotalPrice(newPrice);
                        }
                    });
            return spinner;
        }
        @Override
        public Object getCellEditorValue() {
            return spinner.getValue();
        }
    }
}

