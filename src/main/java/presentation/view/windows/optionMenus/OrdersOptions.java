package presentation.view.windows.optionMenus;

import presentation.view.utility.tables.OrderTable;
import presentation.view.windows.AppWindow;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class OrdersOptions extends OptionWindow{
    private final JButton add;
    public OrdersOptions(OrderTable orderTable) {
        super("Orders");
        mainTable.setViewportView(orderTable.getTable());
        itemsTable=orderTable;
        ((OrderTable)itemsTable).setFrame(this);
        orderTable.setParent(mainTable);

        add=new JButton("new order");
        add.setBorder(new EmptyBorder(6,6,6,6));
        add.setBackground(AppWindow.DARK_COLOR);
        add.setForeground(AppWindow.HIGHLIGHT_COLOR);
        add.setFont(AppWindow.BUTTON_FONT);

        footer.add(add);
        this.getContentPane().validate();
        this.getContentPane().repaint();
    }
    public void addAddButtonListener(ActionListener l){
        add.addActionListener(l);
    }

    @Override
    public void refresh(ArrayList newItems) {
        itemsTable.refreshTable(newItems);
    }
}
