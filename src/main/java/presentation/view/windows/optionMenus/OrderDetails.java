package presentation.view.windows.optionMenus;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.PdfWriter;
import dataAccess.dao.OrderItemDAO;
import model.entity.Clients;
import model.entity.Orders;
import presentation.view.utility.tables.BoughtProductsTable;
import presentation.view.windows.AppWindow;

import javax.swing.*;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.util.ArrayList;

public class OrderDetails extends OptionWindow{
    protected JButton downloadBill;
    protected final ArrayList<JLabel> labels;
    private String orderName;
    public OrderDetails(Orders order, Clients client, OrderItemDAO orderItemDAO) {
        super("Order "+order.getId());
        orderName="Order_no_"+order.getId()+"_"+order.getOrderDate();
        center.remove(content);
        labels=new ArrayList<>();
        labels.add(new JLabel("Client Name: "+client.getFirstName()+" "+client.getLastName()));
        labels.add(new JLabel("Order status: "+order.getState()));
        labels.add(new JLabel("Order date: "+order.getOrderDate()));
        labels.add(new JLabel("Courier: "+order.getCourier()));
        labels.add(new JLabel("Delivery Address: "+order.getDeliveryAddress()));
        for(JLabel i:labels)
            center.add(i);
        labels.add(new JLabel("Total Price: "+order.getTotalPrice()));
        for(JLabel i:labels){
            i.setForeground(AppWindow.SHADE_COLOR);
            i.setFont(AppWindow.LABEL_FONT);
        }
        JScrollPane items=new JScrollPane();
        itemsTable=new BoughtProductsTable(orderItemDAO,order.getId());
        items.setViewportView(itemsTable.getTable());
        center.add(items);

        center.add(labels.get(labels.size()-1));

        center.validate();
        center.repaint();

        downloadBill=new JButton("Download Bill");
        downloadBill.setBackground(AppWindow.DARK_COLOR);
        downloadBill.setForeground(AppWindow.HIGHLIGHT_COLOR);
        downloadBill.setFont(AppWindow.BUTTON_FONT);
        footer.add(downloadBill);
    }
    /**
     * Scrie datele comenzii intr-un PDF
     */
    public void toPDF(){
        try{
            File dir=new File("Bills");
            dir.mkdir();

            Document document = new Document();
            PdfWriter.getInstance(document, new FileOutputStream(dir.getAbsolutePath()+File.separator+orderName+".pdf"));
            document.open();
            Font font = FontFactory.getFont(FontFactory.COURIER, 14, BaseColor.BLACK);
            for(int i=0;i<labels.size()-1;i++){
                document.add(new Paragraph (labels.get(i).getText(),font));
            }
            document.add(Chunk.NEWLINE);
            document.add(((BoughtProductsTable)itemsTable).toPDF());
            Paragraph p=new Paragraph (labels.get(labels.size()-1).getText(),font);
            p.setAlignment(Paragraph.ALIGN_RIGHT);
            document.add(p);
            document.close();
            JOptionPane.showMessageDialog(null,"Pdf created. Check the Bills folder","Success",JOptionPane.INFORMATION_MESSAGE);
        } catch (FileNotFoundException | DocumentException e) {
            JOptionPane.showMessageDialog(null,"Error creating bill","Err",JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        }
    }
    public void addDownloadButtonListener(ActionListener l){
        downloadBill.addActionListener(l);
    }
    @Override
    public void refresh(ArrayList items) {

    }
}
