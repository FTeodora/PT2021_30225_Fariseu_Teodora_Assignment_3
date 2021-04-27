package presentation.view.windows.optionMenus;

import presentation.view.utility.tables.ProductsTable;

import java.util.ArrayList;

public class ProductOptions extends OptionWindow{
    public ProductOptions(ProductsTable productsTable) {
        super("Products in stock");
        mainTable.setViewportView(productsTable.getTable());
        itemsTable=productsTable;
        productsTable.setParent(mainTable);
        content.validate();
        content.repaint();
    }
    @Override
    public void refresh(ArrayList newItems) {
        itemsTable.refreshTable(newItems);
    }

}
