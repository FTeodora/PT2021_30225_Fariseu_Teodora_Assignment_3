package presentation.controller.optionsControllers;

import dataAccess.dao.ClientDAO;
import presentation.view.utility.tables.ClientsTable;
import presentation.view.windows.MainMenu;
import presentation.view.windows.optionMenus.ClientOptions;

public class ClientsController extends OptionsController{
    public ClientsController(MainMenu prev) {
        super(prev);
        abstractDAO=new ClientDAO();
        view=new ClientOptions(new ClientsTable(abstractDAO));
        view.addBackButtonListener(new BackButtonListener());
        view.addRefreshListener(new RefreshButtonListener());
    }
}
