package presentation.view.windows.optionMenus;

import presentation.view.utility.tables.InfosTable;
import presentation.view.utility.OptionButton;
import presentation.view.windows.AppWindow;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionListener;
import java.util.ArrayList;

/**
 * Modelul general al unei ferestre care se deschide dupa alegerea unei optiuni din meniul principal
 * Fereastra are Layout-ul de BorderLayout si are initializate:
 * <ul>
 *     <li>BorderLayout.NORTH - cuprinde un buton de back, unul de refresh si un label de titlu</li>
 *     <li>BorderLayout.CENTER - initializeaza un JScrollPane pentru eventuale componente</li>
 *     <li>BorderLayout.SOUTH - un footer pentru eventuale butoane suplimentare</li>
 * </ul>
 */
public abstract class OptionWindow extends AppWindow {
    protected final OptionButton back,refresh;
    protected final JPanel content;
    protected JScrollPane mainTable;
    protected final JPanel footer;
    protected InfosTable itemsTable;
        OptionWindow(String optionName){
            setSize(new Dimension (990,720));
            setLocationRelativeTo(null);
            back=new OptionButton("back.png");
            refresh=new OptionButton("refresh.png");
            JLabel name=new JLabel(optionName);
            name.setFont(AppWindow.HEADER_FONT);
            name.setForeground(AppWindow.TEXT_COLOR);

            header.add(back);
            header.add(refresh);
            header.add(name);

            center.setLayout(new BoxLayout(center,BoxLayout.Y_AXIS));
            content=new JPanel(new GridLayout(0,1));
            mainTable=new JScrollPane();
            mainTable.setBorder(new EmptyBorder(0,0,0,0));
            content.add(mainTable);

            center.add(content);
            footer=new JPanel(new FlowLayout(FlowLayout.TRAILING));
            footer.setBackground(AppWindow.MAIN_COLOR);

            add(footer,BorderLayout.SOUTH);

            getContentPane().validate();
            getContentPane().repaint();

        }
        public void addBackButtonListener(ActionListener l){
            back.addActionListener(l);
        }
        public void addRefreshListener(ActionListener l){ refresh.addActionListener(l); }
        public abstract void refresh(ArrayList<Object> newItems);
}
