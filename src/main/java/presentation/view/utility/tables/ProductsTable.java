package presentation.view.utility.tables;

import business.BadInputException;
import business.ProductValidator;
import dataAccess.dao.AbstractDAO;
import model.entity.Product;

/**
 * Tabelul pentru gestionarea tuturor produselor din stoc
 */
public class ProductsTable extends InfosTable<Product> {
    public ProductsTable(AbstractDAO<Product> abstractDAO) {
        super(abstractDAO);
        ID="ID";
        IDPlace=0;
        validator=new ProductValidator();
    }
    @Override
    protected Product extractObject(int row) throws BadInputException {
        Product rez=super.extractObject(row);
        rez.setPrice(((ProductValidator)validator).validatePrice(table.getValueAt(row,2).toString()));
        rez.setQuantity(((ProductValidator)validator).validateQuantity(table.getValueAt(row,5).toString()));
        return rez;
    }
    @Override
    protected Object extractID(int row){
        String value=table.getValueAt(row,IDPlace).toString();
        return Integer.parseInt(value);
    }
    @Override
    protected void extractObjectIgnoreNull(int row, boolean ignoreNonEditable) throws BadInputException {
        super.extractObjectIgnoreNull(row,ignoreNonEditable);
        Product instance=new Product();
            if(!ignoreNonEditable&&!table.getValueAt(row,0).toString().equals("")){
                this.fieldNames.add("ID");
                this.objects.add(Integer.parseInt(table.getValueAt(row,0).toString()));
            }
            if(!table.getValueAt(row,2).toString().trim().equals(""))
            {
                instance.setPrice(((ProductValidator)validator).validatePrice(table.getValueAt(row,2).toString()));
                this.fieldNames.add("price");
                this.objects.add(instance.getPrice());
            }
        if(!table.getValueAt(row,5).toString().trim().equals(""))
        {
            instance.setQuantity(((ProductValidator)validator).validateQuantity(table.getValueAt(row,5).toString()));
            fieldNames.add("quantity");
            objects.add(instance.getQuantity());
        }

    }
}
