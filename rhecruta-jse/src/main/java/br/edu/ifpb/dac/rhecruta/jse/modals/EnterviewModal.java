package br.edu.ifpb.dac.rhecruta.jse.modals;

import br.edu.ifpb.dac.rhecruta.shared.domain.entities.Enterview;
import java.util.ArrayList;
import java.util.List;
import javax.swing.ListModel;
import javax.swing.event.ListDataEvent;
import javax.swing.event.ListDataListener;

/**
 *
 * @author Wensttay de Sousa Alencar <yattsnew@gmail.com>
 * @date 07/05/2017, 14:59:06
 */
public class EnterviewModal implements ListModel<Enterview>{
    
    private List<Enterview> enterviews;

    public EnterviewModal(List<Enterview> enterviews) {
        this.enterviews = enterviews;
    }

    @Override
    public int getSize() {
        return enterviews.size();
    }

    @Override
    public Enterview getElementAt(int index) {
        return enterviews.get(index);
    }

    @Override
    public void addListDataListener(ListDataListener l) {
    }

    @Override
    public void removeListDataListener(ListDataListener l) {
    }

}
