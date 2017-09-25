package ACM.model.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import ACM.model.entity.ReceitaAvulsa;
import ACM.model.entity.ResumoAnual;
import ACM.model.repository.hibernate.RelatorioHibernateDao;

@Service(value="relatorioService")
@Transactional(propagation=Propagation.REQUIRED, rollbackFor=Exception.class)
public class RelatorioServiceImpl implements RelatorioService {

	private RelatorioHibernateDao RelatorioHibernateDao;

	@Autowired
	public void setUsuarioRepository(RelatorioHibernateDao RelatorioHibernateDao) {
		this.RelatorioHibernateDao = RelatorioHibernateDao;
	}

	public List<ResumoAnual> getListResumosAnuais(String socio, String dataInicial, String dataFinal, List<ReceitaAvulsa> receitasAvulsas) throws Exception {
		return RelatorioHibernateDao.getListResumosAnuaisPorSocio(socio, dataInicial, dataFinal, receitasAvulsas);
	}
}