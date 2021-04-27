package presentation.view.utility.tables;

import business.BadInputException;
import dataAccess.dao.AbstractDAO;
import model.entity.Clients;
import presentation.view.utility.NewOrderInput;

import javax.swing.*;
import javax.swing.table.TableCellRenderer;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

/**
 * Tabel pentru selectarea clientului la crearea unei noi comenzi
 */
public class OrderClientsTable extends ClientsTable {
    private final NewOrderInput source;
    public OrderClientsTable(AbstractDAO<Clients> abstractDAO, NewOrderInput source) {
        super(abstractDAO);
        refreshTable(new ArrayList<>());
        this.source=source;
    }
    @Override
    public void refreshTable(ArrayList<Clients> allItems){
        ArrayList<String> columns=(ArrayList<String>)abstractDAO.getFieldNames();
        columns.add(" ");
        Object[][] rows=new Object[allItems.size()+1][];
        for(int i=0;i<allItems.size();i++){
            ArrayList<Object> rowItem=(ArrayList<Object>) abstractDAO.getValues(allItems.get(i));
            rowItem.add("SELECT");
            rows[i]=rowItem.toArray();
        }
        rows[allItems.size()]=new Object[columns.size()];
        for(int i=0;i<columns.size()-2;i++)
            rows[allItems.size()][i]= "";
        rows[allItems.size()][columns.size()-1]= "SEARCH";
        TableCellRenderer buttonRenderer=new ButtonRenderer();
        table=new JTable(rows,columns.toArray()){
            @Override
            public boolean isCellEditable(int row,int column){
                if(column==table.getColumnCount()-1)
                    return false;
                return row == table.getRowCount() - 1;
            }
            @Override
            public TableCellRenderer getCellRenderer(int row, int column) {
                if (column==(columns.size()-1)) {
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
                if(column==table.getColumnCount()-1)
                {
                    if(row==table.getRowCount()-1){
                        try {
                            OrderClientsTable.this.extractObjectIgnoreNull(row,false);
                        } catch (BadInputException badInputException) {
                            JOptionPane.showMessageDialog(null,badInputException.getMessage(),"Err",JOptionPane.ERROR_MESSAGE);
                        }
                        refreshTable((ArrayList<Clients>) abstractDAO.searchAll(OrderClientsTable.this.fieldNames,OrderClientsTable.this.objects));

                    }
                    else{
                        source.updateClient(Long.parseLong(table.getValueAt(row,0).toString()));
                    }
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

}
