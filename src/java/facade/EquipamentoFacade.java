/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package facade;

import controller.HibernateUtil;
import java.util.List;
import javax.ejb.Stateless;
import model.Equipamento;
import org.hibernate.Criteria;
import org.hibernate.Hibernate;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;

/**
 *
 * @author Juliana
 */
@Stateless
public class EquipamentoFacade extends AbstractFacade<Equipamento>{
    
    public EquipamentoFacade() {
        super(Equipamento.class);
    }

    @Override
    protected SessionFactory getSessionFactory() {

        return HibernateUtil.getSessionFactory();

    }
    
    /**public Equipamento inicializarColecaoRegistros(Equipamento e) {

        Session session = getSessionFactory().openSession();
        session.refresh(e);
        Hibernate.initialize(e);
        Hibernate.initialize(e.getRegistros());
        session.close();
        return e;

    }**/
    
    public List<Equipamento> buscarPorAtivo(String numAtivo){
        
        
        Session session = getSessionFactory().openSession();
        Criteria criteria = session.createCriteria(Equipamento.class);
        criteria.add(Restrictions.eq("numAtivo", numAtivo));
        criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);

        List results = criteria.list();
        session.close();

        return results;
        
               
    }
}