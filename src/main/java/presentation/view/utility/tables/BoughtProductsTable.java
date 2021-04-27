package presentation.view.utility.tables;

import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import dataAccess.dao.OrderItemDAO;
import model.entity.OrderItem;

import javax.swing.*;
import java.util.ArrayList;

/**
 * Tabelul cu produsele dintr-o comanda
 */
public class BoughtProductsTable extends InfosTable<OrderItem> {
    private final ArrayList<OrderItem> items;
    ArrayList<String> columns;
    public BoughtProductsTable(OrderItemDAO abstractDAO,long orderID) {
        this.abstractDAO=abstractDAO;
        type=abstractDAO.getType();
        items=(ArrayList<OrderItem>) (abstractDAO).getItemsFromOrder(orderID);
        columns=(ArrayList<String>)(abstractDAO).getOrderFieldNames();
        refreshTable(items);
        colorTable();
        this.table.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
    }
    public PdfPTable toPDF(){
        PdfPTable table = new PdfPTable(columns.size());
        for(String i:columns)
        {
            PdfPCell header = new PdfPCell();
            header.setBackgroundColor(BaseColor.LIGHT_GRAY);
            header.setBorderWidth(2);
            header.setPhrase(new Phrase(i));
            table.addCell(header);
        }
        for(OrderItem i:this.items){
            table.addCell(Long.toString(i.getProductID()));
            table.addCell(i.getProductName());
            table.addCell(i.getBrand());
            table.addCell(i.getPrice().toString());
            table.addCell(Integer.toString(i.getRequestedQuantity()));
        }
        return table;
    }
    @Override
    public void refreshTable(ArrayList<OrderItem> allItems) {
        ArrayList<String> columns=this.columns;
        Object[][] rows=new Object[allItems.size()][];
        int n=0;
        for(OrderItem i: allItems){
            rows[n]=((OrderItemDAO)abstractDAO).getOrderFieldValues(i);
            n++;
        }
        table=new JTable(rows,columns.toArray()){
            @Override
            public boolean isCellEditable(int row,int column){
                return false;
            }
        };
    }
}
