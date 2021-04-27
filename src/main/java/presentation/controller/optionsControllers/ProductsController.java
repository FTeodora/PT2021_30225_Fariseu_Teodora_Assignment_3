package presentation.controller.optionsControllers;

import dataAccess.dao.ProductsDAO;
import presentation.view.utility.tables.ProductsTable;
import presentation.view.windows.MainMenu;
import presentation.view.windows.optionMenus.ProductOptions;

public class ProductsController extends presentation.controller.optionsControllers.OptionsController {
    public ProductsController(MainMenu prev) {
        super(prev);
        abstractDAO=new ProductsDAO();
        view=new ProductOptions(new ProductsTable(abstractDAO));
        view.addBackButtonListener(new BackButtonListener());
        view.addRefreshListener(new RefreshButtonListener());
    }
}
