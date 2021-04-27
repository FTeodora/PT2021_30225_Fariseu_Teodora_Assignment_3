package presentation.controller.optionsControllers;

import dataAccess.dao.OrdersDAO;
import presentation.view.utility.tables.OrderTable;
import presentation.view.windows.optionMenus.OrdersOptions;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class OrdersController extends OptionsController{
    public OrdersController(JFrame prev) {
        super(prev);
        abstractDAO=new OrdersDAO();
        view=new OrdersOptions(new OrderTable(abstractDAO));
        view.addBackButtonListener(new BackButtonListener());
        ((OrdersOptions)view).addAddButtonListener(new AddButtonListener());
        view.addRefreshListener(new RefreshButtonListener());
    }
    class AddButtonListener implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent e) {
            new AddOrderController(OrdersController.this);
            view.setVisible(false);
        }
    }
}
