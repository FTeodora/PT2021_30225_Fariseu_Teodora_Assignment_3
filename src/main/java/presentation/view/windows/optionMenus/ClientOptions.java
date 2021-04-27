package presentation.view.windows.optionMenus;

import presentation.view.utility.tables.InfosTable;

import java.util.ArrayList;


public class ClientOptions extends  OptionWindow{
    public ClientOptions(InfosTable clientsTable) {
        super("Clients");
        mainTable.setViewportView(clientsTable.getTable());
        itemsTable=clientsTable;
        clientsTable.setParent(mainTable);
        content.validate();
        content.repaint();

    }

    @Override
    public void refresh(ArrayList newItems) {
        itemsTable.refreshTable(newItems);
    }
}
