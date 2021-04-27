package presentation.view.windows.optionMenus;

import business.BadInputException;
import dataAccess.dao.AbstractDAO;
import dataAccess.dao.ClientDAO;
import dataAccess.dao.ProductsDAO;
import model.entity.OrderItem;
import model.entity.Orders;
import presentation.view.utility.NewOrderInput;
import presentation.view.utility.tables.OrderClientsTable;
import presentation.view.utility.tables.OrderItemTableOptions;
import presentation.view.windows.AppWindow;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class CreateOrder extends OptionWindow{
    protected OrderClientsTable clientsOptions;
    protected OrderItemTableOptions orderItemTableOptions;
    protected JScrollPane clients=new JScrollPane();
    protected JScrollPane products=new JScrollPane();
    protected JButton confirm;
    protected NewOrderInput input=new NewOrderInput();
    public CreateOrder(AbstractDAO abstractDAO) {
        super("Create Order");
        input=new NewOrderInput();
        content.remove(mainTable);
        content.add(input.getContent());

        clientsOptions=new OrderClientsTable(new ClientDAO(),input);
        clients.setViewportView(clientsOptions.getTable());
        clientsOptions.setParent(clients);

        orderItemTableOptions=new OrderItemTableOptions(new ProductsDAO(),input);
        products.setViewportView(orderItemTableOptions.getTable());
        content.add(clients);
        content.add(products);
        content.validate();
        content.repaint();

        confirm=new JButton("Confirm");
        confirm.setBackground(AppWindow.DARK_COLOR);
        confirm.setForeground(AppWindow.HIGHLIGHT_COLOR);
        confirm.setFont(AppWindow.BUTTON_FONT);
        confirm.setBorder(new EmptyBorder(6,6,6,6));

        footer.add(confirm);
        setVisible(true);

    }
    public void addConfirmButtonListener(ActionListener l){
        confirm.addActionListener(l);
    }
    public Orders getCreatedOrder(){
        try{
            return input.getObject();
        } catch (BadInputException e) {
            JOptionPane.showMessageDialog(this,e.getMessage(),"Err",JOptionPane.ERROR_MESSAGE);
        }
        return null;
    }
    public ArrayList<OrderItem> getOrderedItems(){
        return this.orderItemTableOptions.getItems();
    }

    @Override
    public void refresh(ArrayList items) {

    }
}
