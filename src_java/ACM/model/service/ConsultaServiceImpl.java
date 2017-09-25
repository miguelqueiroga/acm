package ACM.model.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import ACM.model.repository.hibernate.ConsultaHibernateDao;

@Service(value="consultaService")
@Transactional(propagation=Propagation.REQUIRED, rollbackFor=Exception.class)
public class ConsultaServiceImpl implements ConsultaService {

	protected ConsultaHibernateDao ConsultaHibernateDao;

	@Autowired
	public void setConsultaRepository(ConsultaHibernateDao ConsultaHibernateDao) {
		this.ConsultaHibernateDao = ConsultaHibernateDao;
	}

	public <T> List<T> getListPorConsulta(String consulta) throws Exception {
		try {
			//System.out.println(this.UsuarioRepository.getList().size());
			return this.ConsultaHibernateDao.listaPorConsulta(consulta);
		} catch (Exception e) {
			e.printStackTrace();
			throw new Exception("Não foi possível listar."+e.getMessage());
		}
	}
}