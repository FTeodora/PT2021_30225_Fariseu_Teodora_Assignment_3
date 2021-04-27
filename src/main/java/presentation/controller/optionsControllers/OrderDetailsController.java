package presentation.controller.optionsControllers;

import dataAccess.dao.ClientDAO;
import dataAccess.dao.OrderItemDAO;
import model.entity.Clients;
import model.entity.Orders;
import presentation.view.windows.optionMenus.OrderDetails;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class OrderDetailsController extends OptionsController{
    public OrderDetailsController(JFrame prev, Orders order) {
        super(prev);
        abstractDAO=new OrderItemDAO();
        Clients client=new ClientDAO().getName(order.getClientID());
        view=new OrderDetails(order,client,(OrderItemDAO) abstractDAO);
        view.addBackButtonListener(new BackButtonListener());
        ((OrderDetails)view).addDownloadButtonListener(new DownloadButtonListener());
    }
    class DownloadButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            ((OrderDetails)view).toPDF();
        }
    }
}
