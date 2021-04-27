package presentation.view.utility.tables;

import business.BadInputException;
import business.ClientValidator;
import dataAccess.dao.AbstractDAO;
import model.entity.Clients;

/**
 * Tabelul pentru gestionarea clientilor
 */
public class ClientsTable extends InfosTable<Clients> {
    public ClientsTable(AbstractDAO<Clients> abstractDAO) {
        super(abstractDAO);
        ID="ID";
        IDPlace=0;
        validator=new ClientValidator();
    }
    @Override
    protected Clients extractObject(int row) throws BadInputException{
        Clients rez=super.extractObject(row);
            rez.setDateOfBirth(validator.parseDate(table.getValueAt(row,3).toString()));
            ((ClientValidator) validator).validateEmail(rez.getEMail());
        return rez;
    }
    @Override
    protected Object extractID(int row){
        String value=table.getValueAt(row,IDPlace).toString();
        return Integer.parseInt(value);
    }
    @Override
    protected void extractObjectIgnoreNull(int row, boolean ignoreNonEditable) throws BadInputException{
        super.extractObjectIgnoreNull(row,ignoreNonEditable);
        Clients instance;
        instance=new Clients();
        if(table.getValueAt(row,3)!=null&&!table.getValueAt(row,3).toString().trim().equals(""))
        {
            instance.setDateOfBirth(validator.parseDate(table.getValueAt(row,3).toString()));
            this.fieldNames.add("dateOfBirth");
            this.objects.add(instance.getDateOfBirth());
        }
        if(table.getValueAt(row,4)!=null&&!table.getValueAt(row,4).toString().trim().equals("")) {
            ((ClientValidator) validator).validateEmail(table.getValueAt(row,4).toString());
        }
        if(table.getValueAt(row,0)!=null&&!ignoreNonEditable&&!table.getValueAt(row,0).toString().trim().equals("")){
               this.fieldNames.add("ID");
               this.objects.add(Integer.parseInt(table.getValueAt(row,0).toString()));
        }

    }
}
