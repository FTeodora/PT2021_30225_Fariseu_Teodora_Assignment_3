package presentation.controller.optionsControllers;

import dataAccess.dao.AbstractDAO;
import presentation.view.windows.optionMenus.OptionWindow;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public abstract class OptionsController {
    protected JFrame prev;
    protected AbstractDAO abstractDAO;
    protected OptionWindow view,refresh;
    public OptionsController(JFrame prev){
        this.prev=prev;
    }
    class BackButtonListener implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent e) {
            view.dispose();
            prev.setVisible(true);
        }
    }
    class RefreshButtonListener implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent e) {
            view.refresh((ArrayList<Object>) abstractDAO.selectAll());
        }
    }
}
