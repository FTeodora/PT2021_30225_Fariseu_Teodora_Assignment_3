package presentation.controller.optionsControllers;

import dataAccess.dao.OrderItemDAO;
import dataAccess.dao.OrdersDAO;
import model.entity.OrderItem;
import model.entity.Orders;
import presentation.view.windows.optionMenus.CreateOrder;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class AddOrderController extends OptionsController{

    public AddOrderController(OrdersController prev){
        super(prev.view);
        abstractDAO=new OrdersDAO();
        view=new CreateOrder(abstractDAO);
        this.prev=prev.view;
        view.addBackButtonListener(new BackButtonListener());
        ((CreateOrder)view).addConfirmButtonListener(new ConfirmOrderListener());
    }
    class ConfirmOrderListener implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent e) {
            Orders insertedOrder=((CreateOrder)view).getCreatedOrder();
            if(insertedOrder!=null){
                long insertedID=abstractDAO.insertItem(insertedOrder);
                if(insertedID!=-1){
                    ArrayList<OrderItem> items=((CreateOrder)view).getOrderedItems();
                    OrderItemDAO orderItemDAO=new OrderItemDAO();
                    for(OrderItem i:items){
                        i.setOrderID(insertedID);
                        orderItemDAO.insertItem(i);
                    }
                    JOptionPane.showMessageDialog(null,"Order added!","Success",JOptionPane.INFORMATION_MESSAGE);
                }
            }
        }
    }

}
