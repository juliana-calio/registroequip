package util;

import java.util.List;
import javax.faces.model.ListDataModel;
import model.Registro;
import org.primefaces.model.SelectableDataModel;


public class RegistroDataModel extends ListDataModel implements SelectableDataModel<Registro> {

    public RegistroDataModel() {
    }

    public RegistroDataModel(List<Registro> data) {
        super(data);
    }

    @Override
    public Registro getRowData(String rowKey) {
        //In a real app, a more efficient way like a query by rowKey should be implemented to deal with huge data  

        List<Registro> registros = (List<Registro>) getWrappedData();

        for (Registro registro : registros) {
            if (registro.getTipo().equals(rowKey)) {
                return registro;
            }
        }

        return null;
    }

    @Override
    public Object getRowKey(Registro registro) {
        return registro.getTipo();
    }

}
