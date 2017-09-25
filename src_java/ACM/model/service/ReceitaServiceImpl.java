package ACM.model.service;

import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import ACM.model.entity.Receita;
import ACM.model.entity.Vencimento;
import ACM.model.repository.hibernate.CriterioDePesquisa;
import ACM.model.repository.hibernate.ReceitaHibernateDao;

@Service(value="receitaService")
@Transactional(propagation=Propagation.REQUIRED, rollbackFor=Exception.class)
public class ReceitaServiceImpl implements ReceitaService {

	private ReceitaHibernateDao ReceitaHibernateDao;

	@Autowired
	public void setUsuarioRepository(ReceitaHibernateDao ReceitaHibernateDao) {
		this.ReceitaHibernateDao = ReceitaHibernateDao;
	}

	public Receita save(Receita p_Usuario) throws Exception {
		try {
			this.ReceitaHibernateDao.save(p_Usuario);
			return p_Usuario;
		} catch (Exception e) {
			throw new Exception("Não foi possível salvar." +e.getCause());
		}
	}

	public void remove(Receita p_Usuario) throws Exception {
		try {	
			this.ReceitaHibernateDao.remove(p_Usuario);
		} catch (Exception e) {
			throw new Exception("Não foi possível excluir." +e.getMessage());
		}
	}

	public List<Receita> getList() throws Exception {
		try {
			//System.out.println(this.UsuarioRepository.getList().size());
			return this.ReceitaHibernateDao.listaTudo();
		} catch (Exception e) {
			throw new Exception("Não foi possível listar."+e.getMessage());
		}
	}

	public List<Receita> getListPorVencimento(Vencimento r) throws Exception {
		try {
			//System.out.println(this.UsuarioRepository.getList().size());
			return this.ReceitaHibernateDao.getListPorVencimento(r);
		} catch (Exception e) {
			throw new Exception("Não foi possível listar."+e.getMessage());
		}
	}

	public List<Receita> buscaPorCriterios(Collection<CriterioDePesquisa> criterios) throws Exception {
		try {
			List<Receita> resultado = this.ReceitaHibernateDao.listaPorCriterios(criterios);
			Collections.sort (resultado, new Comparator<Receita>() {  
	            public int compare(Receita o1, Receita o2) {  
	                return o1.getRecReceitaPorConvenio().getRpcConvenio().getConNome().compareTo(o2.getRecReceitaPorConvenio().getRpcConvenio().getConNome());  
	            }  
	        });	
			System.out.println(resultado);
			return resultado;
		} catch (Exception e) {
			throw new Exception("Não foi possível listar."+e.getMessage());
		}
	}
}