package presentation.view.utility.tables;

import business.BadInputException;
import business.OrdersValidator;
import dataAccess.dao.AbstractDAO;
import model.entity.Orders;
import model.misc.ProductStatus;
import presentation.controller.optionsControllers.OrderDetailsController;

import javax.swing.*;
import javax.swing.table.TableCellEditor;
import javax.swing.table.TableCellRenderer;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.math.BigDecimal;
import java.util.ArrayList;

public class OrderTable extends InfosTable<Orders> {
    private JFrame frame;
    public OrderTable(AbstractDAO<Orders> abstractDAO) {
        super(abstractDAO);
        ID="ID";
        IDPlace=0;
        validator=new OrdersValidator();
    }
    @Override
    protected Orders extractObject(int row) throws BadInputException {
        Orders rez=super.extractObject(row);
        rez.setId(Integer.parseInt(table.getValueAt(row,0).toString()));
        rez.setClientID(Integer.parseInt(table.getValueAt(row,1).toString()));
        rez.setState((ProductStatus) table.getCellEditor(row,2).getCellEditorValue());
        rez.setOrderDate(validator.parseDate(table.getValueAt(row,3).toString()));
        rez.setTotalPrice(new BigDecimal(table.getValueAt(row,6).toString()));
        return rez;
    }
    @Override
    protected Object extractID(int row){
        String value=table.getValueAt(row,IDPlace).toString();
        return Integer.parseInt(value);
    }
    @Override
    protected void extractObjectIgnoreNull(int row, boolean ignoreNonEditable) throws BadInputException{
        super.extractObjectIgnoreNull(row,ignoreNonEditable);
        if(table.getValueAt(row,1)!=null&&!ignoreNonEditable&&!table.getValueAt(row,0).toString().trim().equals("")){
            this.fieldNames.add("clientID");
            this.objects.add(Integer.parseInt(table.getValueAt(row,1).toString()));
        }
        if(table.getValueAt(row,2)!=null&&!table.getValueAt(row,2).toString().trim().equals("")) {
            this.fieldNames.add("state");
            this.objects.add((ProductStatus) table.getCellEditor(row,2).getCellEditorValue());
        }
        if(!ignoreNonEditable&&table.getValueAt(row,3)!=null&&!table.getValueAt(row,3).toString().trim().equals("")) {

            this.fieldNames.add("orderDate");
            this.objects.add(validator.parseDate(table.getValueAt(row,3).toString()));
        }
        if(table.getValueAt(row,0)!=null&&!ignoreNonEditable&&!table.getValueAt(row,0).toString().trim().equals("")){
            this.fieldNames.add("ID");
            this.objects.add(Integer.parseInt(table.getValueAt(row,0).toString()));
        }
    }
    public void refreshTable(ArrayList<Orders> allItems) {
        ArrayList<String> columns = (ArrayList<String>) abstractDAO.getFieldNames();
        columns.add(" ");
        columns.add(" ");
        columns.add(" ");
        Object[][] rows = new Object[allItems.size() + 1][];
        for (int i = 0; i < allItems.size(); i++) {
            ArrayList<Object> rowItem = (ArrayList<Object>) abstractDAO.getValues((Orders) allItems.get(i));
            rowItem.add("DETAILS");
            rowItem.add("EDIT");
            rowItem.add("DELETE");
            rows[i] = rowItem.toArray();
        }
        rows[allItems.size()] = new Object[columns.size()];
        for (int i = 0; i < columns.size() - 3; i++)
            rows[allItems.size()][i] = "";
        rows[allItems.size()][columns.size() - 3] = "SEARCH";
        rows[allItems.size()][columns.size() - 2] = "";
        rows[allItems.size()][columns.size() - 1] = "";
        TableCellRenderer buttonRenderer = new ButtonRenderer();
        table = new JTable(rows, columns.toArray()) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return column < (columns.size() - 3);
            }
        };
        table.getColumnModel().getColumn(2).setCellEditor(new StatusEditor());
        table.getColumnModel().getColumn(table.getColumnCount() - 3).setCellRenderer(buttonRenderer);
        table.getColumnModel().getColumn(table.getColumnCount() - 2).setCellRenderer(buttonRenderer);
        table.getColumnModel().getColumn(table.getColumnCount() - 1).setCellRenderer(buttonRenderer);
        table.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                JTable target = (JTable) e.getSource();
                int row = target.getSelectedRow();
                int column = target.getSelectedColumn();
                if (!target.isCellEditable(row, column)) {
                    boolean success = true;
                    try {
                        if ((row == target.getRowCount() - 1) && column == target.getColumnCount() - 3) {
                                //cautare
                                OrderTable.this.extractObjectIgnoreNull(row, false);
                                refreshTable((ArrayList<Orders>) abstractDAO.searchAll(OrderTable.this.fieldNames, OrderTable.this.objects));
                            }
                        else {
                            //editare
                            if(column==table.getColumnCount()-3){
                                frame.setVisible(false);
                                new OrderDetailsController(frame,OrderTable.this.extractObject(row));


                            }else{
                                if (column == target.getColumnCount() - 2) {
                                    OrderTable.this.extractObjectIgnoreNull(row, true);
                                    objects.add(OrderTable.this.extractID(row));
                                    success = abstractDAO.updateItem(OrderTable.this.fieldNames, OrderTable.this.objects, ID);
                                } else {
                                    //stergere
                                    if(column== target.getColumnCount()-1)
                                        success = abstractDAO.deleteByID(ID, OrderTable.this.extractID(row));
                                }
                            }
                        }
                    } catch (BadInputException er) {
                        success = false;
                        JOptionPane.showMessageDialog(null, er.getMessage(), "Err", JOptionPane.ERROR_MESSAGE);
                    }
                    if(column != target.getColumnCount() - 3){
                        if (success)
                            JOptionPane.showMessageDialog(null, "Operation executed successfully", "Success", JOptionPane.INFORMATION_MESSAGE);
                        else
                            JOptionPane.showMessageDialog(null, "Operation failed", "Error", JOptionPane.ERROR_MESSAGE);
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
    public void setFrame(JFrame frame){this.frame=frame;}
        class StatusEditor extends AbstractCellEditor implements TableCellEditor {
            JComboBox<ProductStatus> comboBox;
            public StatusEditor(){
                comboBox = new JComboBox<>();
                comboBox.addItem(ProductStatus.CONFIRMED);
                comboBox.addItem(ProductStatus.SHIPPED);
                comboBox.addItem(ProductStatus.DELIVERED);
            }
            @Override
            public Object getCellEditorValue() {
                if (comboBox == null)
                    return null;
                return comboBox.getSelectedItem();
            }

            @Override
            public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected, int row, int column) {
                return comboBox;
            }
        }
}
