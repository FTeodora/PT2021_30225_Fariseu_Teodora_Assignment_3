package presentation.view.windows;

import presentation.view.utility.OptionButton;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

public class MainMenu extends AppWindow{
    private final OptionButton clientButton,productButton,ordersButton;
    public MainMenu(){
        setSize(new Dimension(670,650));
        setLocationRelativeTo(null);

        JLabel welcomeMessage=new JLabel("Welcome!");
        welcomeMessage.setFont(HEADER_FONT);
        welcomeMessage.setForeground(TEXT_COLOR);
        header.add(welcomeMessage);

        clientButton=new OptionButton("Clients","clients.png");
        productButton=new OptionButton("Products in Stock","inventory.png");
        ordersButton=new OptionButton("Orders","orders.png");
        JPanel buttonPanel=new JPanel(new GridLayout(0,1,20,20));
        buttonPanel.setPreferredSize(new Dimension(500,280));
        buttonPanel.add(clientButton);
        buttonPanel.add(productButton);
        buttonPanel.add(ordersButton);
        center.add(buttonPanel);
        getContentPane().validate();
        getContentPane().repaint();
    }
    public void addClientsListener(ActionListener l){
        clientButton.addActionListener(l);
    }
    public void addProductsListener(ActionListener l){
        productButton.addActionListener(l);
    }
    public void addOrdersListener(ActionListener l){
        ordersButton.addActionListener(l);
    }
}
