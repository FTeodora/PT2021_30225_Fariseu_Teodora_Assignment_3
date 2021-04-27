package presentation.view.windows;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

public abstract class AppWindow extends JFrame {
    public static final Color DARK_COLOR=new Color(23,36,42);
    public static final Color MAIN_COLOR=new Color(58,175,169);
    public static final Color SHADE_COLOR=new Color(43,122,119);
    public static final Color HIGHLIGHT_COLOR=new Color(222,242,241);
    public static final Color TEXT_COLOR=Color.WHITE;
    public static final Font HEADER_FONT=new Font("Lucida Sans",Font.ITALIC,28);
    public static final Font BUTTON_FONT=new Font("Bahnschrift",Font.BOLD,18);
    public static final Font TABLE_FONT=new Font("Verdana",Font.PLAIN,12);
    public static final Font LABEL_FONT=new Font("Lucida Sans",Font.PLAIN,16);

    protected JPanel header,center;
    public AppWindow(){
        super("Warehouse");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        header=new JPanel(new FlowLayout(FlowLayout.LEADING));
        header.setBackground(MAIN_COLOR);
        header.setBorder(new EmptyBorder(10,10,10,10));
        add(header,BorderLayout.NORTH);

        center=new JPanel();
        JScrollPane scroll=new JScrollPane(center);
        center.setBorder(new EmptyBorder(40,20,10,20));
        add(scroll,BorderLayout.CENTER);
        setVisible(true);
    }

}
