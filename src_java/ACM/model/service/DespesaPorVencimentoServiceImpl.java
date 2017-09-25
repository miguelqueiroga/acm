package ACM.model.service;

import java.io.File;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;

import net.sf.jasperreports.engine.JRDataSource;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;

import org.hibernate.exception.ConstraintViolationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import ACM.model.entity.DespesaPorVencimento;
import ACM.model.entity.Vencimento;
import ACM.model.repository.hibernate.CriterioDePesquisa;
import ACM.model.repository.hibernate.DespesaPorVencimentoHibernateDao;

@Service(value="despesaPorVencimentoService")
@Transactional(propagation=Propagation.REQUIRED, rollbackFor=Exception.class)
public class DespesaPorVencimentoServiceImpl implements DespesaPorVencimentoService {

	private DespesaPorVencimentoHibernateDao DespesaPorVencimentoHibernateDao;

	@Autowired
	public void setDespesaPorVencimentoHibernateDao(DespesaPorVencimentoHibernateDao DespesaPorVencimentoHibernateDao) {
		this.DespesaPorVencimentoHibernateDao = DespesaPorVencimentoHibernateDao;
	}

	public DespesaPorVencimento save(DespesaPorVencimento dpv) throws Exception {
		try {
			this.DespesaPorVencimentoHibernateDao.save(dpv);
			return dpv;
		} catch (DataIntegrityViolationException e) {
			e.printStackTrace();
			throw new Exception("Não foi possível salvar. \nEsta Despesa já está associada ao Vencimento selecionado!");
		} catch (Exception e) {
			e.printStackTrace();
			if ( e.getCause() instanceof ConstraintViolationException )
				throw new Exception("Não foi possível salvar. \nEsta Despesa já está associada ao Vencimento selecionado!");
			throw new Exception("Não foi possível salvar. " + e.getMessage());
		}
	}

	public void remove(DespesaPorVencimento p_Usuario) throws Exception {
		try {	
			this.DespesaPorVencimentoHibernateDao.remove(p_Usuario);
		} catch (DataIntegrityViolationException e) {
			e.printStackTrace();
			throw new Exception("Não foi possível excluir. \nOutro cadastro faz referência ao que está tentando ser excluído");
		} catch (Exception e) {
			e.printStackTrace();
			if ( e.getCause() instanceof ConstraintViolationException ) 
				throw new Exception("Não foi possível excluir. \nOutro cadastro faz referência ao que está tentando ser excluído");
			throw new Exception("Não foi possível excluir. " + e.getMessage());
		}
	}

	public List<DespesaPorVencimento> getList() throws Exception {
		try {
			//System.out.println(this.UsuarioRepository.getList().size());
			return this.DespesaPorVencimentoHibernateDao.listaTudo();
		} catch (Exception e) {
			e.printStackTrace();
			throw new Exception("Não foi possível listar."+e.getMessage());
		}
	}

	public List<DespesaPorVencimento> getListPorVencimento(Vencimento v)
			throws Exception {
		try {
			//System.out.println(this.UsuarioRepository.getList().size());
			List<DespesaPorVencimento> resultado = this.DespesaPorVencimentoHibernateDao.getListPorVencimento(v);
			return resultado;
		} catch (Exception e) {
			e.printStackTrace();
			throw new Exception("Não foi possível listar."+e.getMessage());
		}
	}

	public List<DespesaPorVencimento> getListPorVencimentoString(String s) throws Exception {
		try {
			return this.DespesaPorVencimentoHibernateDao.getListPorVencimentoString(s);
		} catch (Exception e) {
			throw new Exception("Não foi possível listar."+e.getMessage());
		}
	}

	public String getRelatorioGeralDespesas(List<DespesaPorVencimento> despesas, String url) throws Exception{
		System.out.println(System.getProperty("file.enconding"));
		System.setProperty("file.encoding", "iso-8859-1");  
		//String reportXML = "";
		byte[] bytes = null;
		HashMap<String, String> parameters = new HashMap<String, String>();
		File reportFile = null;
		JasperPrint reportPrint = null;
		
		reportFile = new File("C:/Program Files/Apache Software Foundation/Tomcat 6.0/webapps/acm/WEB-INF/relatorios/Relatorio_Despesas.jasper");
		
		//System.out.println(dados);
		System.out.println(reportFile);
		System.out.println(reportFile.getAbsolutePath());
		System.out.println(reportFile.getCanonicalPath());
		
		
		JRDataSource jrds = new JRBeanCollectionDataSource(despesas);
		
		reportPrint = JasperFillManager.fillReport(reportFile.getAbsolutePath(),  parameters, jrds);

		JasperExportManager.exportReportToPdfFile(reportPrint, "C:/teste.pdf");
		
		bytes = JasperExportManager.exportReportToPdf(reportPrint);
		
		return bytes.toString();
	}

	public List<DespesaPorVencimento> buscaPorCriterios(Collection<CriterioDePesquisa> criterios) throws Exception {
		try {
			List<DespesaPorVencimento> resultado = this.DespesaPorVencimentoHibernateDao.listaPorCriterios(criterios); 
			Collections.sort (resultado, new Comparator<DespesaPorVencimento>() {  
	            public int compare(DespesaPorVencimento o1, DespesaPorVencimento o2) {  
	            	if (o1.getDpvVencimento().getVenNome().compareTo(o2.getDpvVencimento().getVenNome()) == 0)
	            		return o1.getDpvDespesa().getDesNome().compareTo(o2.getDpvDespesa().getDesNome());
	            	else return o1.getDpvVencimento().getVenNome().compareTo(o2.getDpvVencimento().getVenNome());  
	            }  
	        });
			System.out.println(resultado);
			return resultado;
		} catch (Exception e) {
			e.printStackTrace();
			throw new Exception("Não foi possível listar."+e.getMessage());
		}
	}
}