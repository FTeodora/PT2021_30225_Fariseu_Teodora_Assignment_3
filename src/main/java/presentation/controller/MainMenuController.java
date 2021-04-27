package presentation.controller;

import presentation.controller.optionsControllers.ClientsController;
import presentation.controller.optionsControllers.OrdersController;
import presentation.controller.optionsControllers.ProductsController;
import presentation.view.windows.MainMenu;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Fereastra unde se afla butoanele de optiuni:
 * <ul>
 *     <li>gestionare clienti</li>
 *     <li>gestionare produse in stoc</li>
 *     <li>gestionare comenzi</li>
 * </ul>
 */
public class MainMenuController {
    private MainMenu view;
    public MainMenuController(){
        this.view=new MainMenu();
        view.addClientsListener(new ClientsButtonListener());
        view.addOrdersListener(new OrderButtonListener());
        view.addProductsListener(new ProductsButtonListener());
    }
class ClientsButtonListener implements ActionListener{
    @Override
    public void actionPerformed(ActionEvent e) {
        view.setVisible(false);
        new ClientsController(view);
    }
}
    class ProductsButtonListener implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent e) {
            view.setVisible(false);
            new ProductsController(view);
        }
    }
    class OrderButtonListener implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent e) {
            view.setVisible(false);
            new OrdersController(view);
        }
    }
}
