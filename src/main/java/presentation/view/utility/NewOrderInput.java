package presentation.view.utility;

import business.BadInputException;
import model.entity.Orders;
import presentation.view.windows.AppWindow;

import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.math.BigDecimal;

/**
 *  Clasa cu campuri pentru introducerea unei comenzi noi
 */
public class NewOrderInput extends JPanel {
    private final JLabel clientID;
    private final JTextField courier;
    private final JTextPane address;
    private final JLabel totalPrice;
    private final JPanel content;
    public NewOrderInput(){
        content=new JPanel();
        content.setLayout(new BoxLayout(content,BoxLayout.Y_AXIS));

        JLabel cl=new JLabel("Client ID: ");

        cl.setFont(AppWindow.LABEL_FONT);
        cl.setForeground(AppWindow.SHADE_COLOR);

        clientID=new JLabel();
        clientID.setFont(AppWindow.LABEL_FONT);
        clientID.setForeground(AppWindow.SHADE_COLOR);
        content.add(cl,Component.LEFT_ALIGNMENT);
        content.add(clientID,Component.LEFT_ALIGNMENT);

        JLabel co=new JLabel("Courier: ");
        co.setForeground(AppWindow.SHADE_COLOR);
        co.setFont(AppWindow.LABEL_FONT);

        courier=new JTextField();
        courier.setBackground(AppWindow.HIGHLIGHT_COLOR);
        courier.setForeground(AppWindow.SHADE_COLOR);
        courier.setFont(AppWindow.LABEL_FONT);
        courier.setPreferredSize(new Dimension(800,30));
        courier.setMaximumSize(new Dimension(800,30));
        courier.setBorder(new LineBorder(AppWindow.SHADE_COLOR,1));
        content.add(co,Component.LEFT_ALIGNMENT);
        content.add(courier,Component.LEFT_ALIGNMENT);

        JLabel ad=new JLabel("Delivery Address: ");
        ad.setForeground(AppWindow.SHADE_COLOR);
        ad.setFont(AppWindow.LABEL_FONT);


        address=new JTextPane();
        JScrollPane adr=new JScrollPane(address);
        adr.getViewport().setPreferredSize(new Dimension(900,200));
        address.setBackground(AppWindow.HIGHLIGHT_COLOR);
        address.setForeground(AppWindow.SHADE_COLOR);
        address.setFont(AppWindow.LABEL_FONT);
        address.setBorder(new LineBorder(AppWindow.SHADE_COLOR,1));
        content.add(ad,Component.LEFT_ALIGNMENT);
        content.add(adr,Component.LEFT_ALIGNMENT);

        JLabel pr=new JLabel("Total Price: ");
        pr.setForeground(AppWindow.SHADE_COLOR);
        pr.setFont(AppWindow.LABEL_FONT);

        totalPrice=new JLabel();
        totalPrice.setFont(AppWindow.LABEL_FONT);
        totalPrice.setForeground(AppWindow.SHADE_COLOR);
        content.add(pr,Component.LEFT_ALIGNMENT);
        content.add(totalPrice,Component.LEFT_ALIGNMENT);

        this.add(content);
        content.validate();
        content.repaint();
    }
    public Orders getObject() throws BadInputException{
        Orders rez=new Orders();
        if(clientID.getText()==null||clientID.getText().equals(""))
            throw new BadInputException("clientID","Please select a client");
        rez.setClientID(Long.parseLong(clientID.getText()));
        if(courier.getText()==null||courier.getText().equals(""))
            throw new BadInputException("Courier","Please enter a courier");
        rez.setCourier(courier.getText());
        if(address.getText()==null||address.getText().equals(""))
            throw new BadInputException("Delivery Address","Please enter an address");
        rez.setDeliveryAddress(address.getText());
        if(totalPrice.getText()==null||totalPrice.getText().equals("")||totalPrice.getText().equals("0.00"))
            throw new BadInputException("Total Price","Please select at least an item");
        rez.setTotalPrice(new BigDecimal(totalPrice.getText()));
        return rez;
    }
    /**
     * Actualizeaza clientul pentru care se plaseaza comanda
     * @param ID ID-ul cu care se actualizeaza
     */
    public void updateClient(long ID){
        clientID.setText(Long.toString(ID));
        clientID.validate();
        clientID.repaint();
    }
    /**
     * Actualizeaza pretul total al comenzii
     * @param newTotal pretul cu care se actualizeaza
     */
    public void updateTotalPrice(BigDecimal newTotal){
        totalPrice.setText(newTotal.toString());
        totalPrice.validate();
        totalPrice.repaint();
    }
    public JPanel getContent(){
        return this.content;
    }
}
