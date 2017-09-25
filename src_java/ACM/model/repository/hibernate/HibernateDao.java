package ACM.model.repository.hibernate;

import java.util.Collection;
import java.util.Iterator;
import java.util.List;

import org.hibernate.SQLQuery;
import org.hibernate.SessionFactory;
import org.hibernate.transform.Transformers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

import ACM.model.entity.Convenio;
import ACM.model.entity.ResumoAnual;

public abstract class HibernateDao extends HibernateDaoSupport {
	@Autowired
	public HibernateDao(@Qualifier("sessionFactory")SessionFactory factory) {
		super.setSessionFactory(factory);
	}
	
	public abstract Class<?> getClassName();
	
	public Object save(Object p_Usuario) {
		getHibernateTemplate().saveOrUpdate(p_Usuario);
		return p_Usuario;// ID POPULADA
	}

	public void remove(Object p_Usuario) {
		getHibernateTemplate().delete(p_Usuario);		
		getHibernateTemplate().flush();
	}

	@SuppressWarnings("unchecked")
	public List<Object> getList() {
		return (List<Object>) getHibernateTemplate().loadAll(Convenio.class);
	}

    public <T> List<T> listaTudo() {
        String consulta = "from " + getClassName().getName() + " as o";
        return getHibernateTemplate().find(consulta);        
    }

    public <T> Collection<T> listaTudoOrdenado(String ordem) {
        String consulta = "from " + getClassName().getName() + " as o ORDER BY o." + ordem;
        return getHibernateTemplate().find(consulta);        
    }

    public <T> Collection<T> listaPorCriterio(CriterioDePesquisa criterio) {
        String consulta = "from " + getClassName().getName() + " as o where " + criterio.getCriterio() + criterio.getOperador() + criterio.getValor();
        return getHibernateTemplate().find(consulta);                		
    }

    public <T> List<T> listaPorCriterios(Collection<CriterioDePesquisa> criterios) {
        String stringConsulta = "from " + getClassName().getName() + " as o";
        if(criterios.size() > 0) {
            stringConsulta += " where";
            CriterioDePesquisa criterio = null;
            for (Iterator<CriterioDePesquisa> it = criterios.iterator() ; it.hasNext() ;){
                criterio = (CriterioDePesquisa) it.next();
                stringConsulta += " " + criterio.getCriterio();
                stringConsulta += criterio.getOperador();
                stringConsulta += criterio.getValor() + (it.hasNext() ? " AND": "");  
            }
        }
        System.out.println(stringConsulta);
        return getHibernateTemplate().find(stringConsulta);        
    }

    public <T> List<T> listaPorConsulta(String consulta) {
    	return getHibernateTemplate().find(consulta);        
    }

    public void inicializa(Object o) {
        getHibernateTemplate().initialize(o);
    }

    public Object getElementoMaiorAtributo(String atributo, CriterioDePesquisa criterio) {
        String stringConsulta = "from " + getClassName().getName() + " as o where o."
                                + criterio.getCriterio() + criterio.getOperador() + criterio.getValor() +
                                " AND o." + atributo + " = " +
                                "( SELECT MAX(" + atributo + ") from " + getClassName().getName() + ")";
        try {
            //System.out.println(stringConsulta);
            return getHibernateTemplate().find(stringConsulta).iterator().next();
        } catch (Exception e) {
            System.out.println(e.getCause());
            return null;
        }        
    }
    
    public <T> Collection<T> listaTudoOrdenadoDistinto(String campo) {
        String consulta = "select distinct o." + campo + " from "+ getClassName().getName() + " as o ORDER BY o." + campo;
        return getHibernateTemplate().find(consulta);
    }
    
    
    @SuppressWarnings("unchecked")
	public <T> List<T>  getValorPorProcedimento(String functionName, String idSocio){
    	SQLQuery q = super.getSession().createSQLQuery("call " + functionName + "(?)");
    	q.setParameter(0, idSocio);
    	q.setResultTransformer(Transformers.aliasToBean(ResumoAnual.class));
    	List<T> lista = q.list();
    	return lista;
    }
}