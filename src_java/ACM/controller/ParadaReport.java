package ACM.controller;

import java.io.IOException;

import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.config.AutowireCapableBeanFactory;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import ACM.model.service.ConsultaService;
import ACM.model.service.ConsultaServiceImpl;

/**
 * Servlet implementation class ParadaReport
 */
public class ParadaReport extends HttpServlet {
	private static final long serialVersionUID = 1L;

	private ServletContext sc;

	private ConsultaService factoryService = new ConsultaServiceImpl();

	public void init(ServletConfig config) throws ServletException {
		super.init(config);

		sc = config.getServletContext();

		WebApplicationContext webApplicationContext = WebApplicationContextUtils
				.getWebApplicationContext(sc);

		AutowireCapableBeanFactory autowireCapableBeanFactory = webApplicationContext
				.getAutowireCapableBeanFactory();

		autowireCapableBeanFactory.configureBean(factoryService,
				"despesaPorVencimentoService");

	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPost(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Integer linha = Integer.parseInt(request.getParameter("linha"));
		
		/*try {
			if(linha < 0){
				geraRelatorioMultiplo(request, response);				
			} else 
				geraRelatorioUnico(request, response);
		} catch (Exception e1) {
			e1.printStackTrace();
			throw new ServletException(e1);
		}*/

		
	}
	
	/*@SuppressWarnings("unchecked")
	public void geraRelatorioUnico(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
		File reportFile = null;
		
		HashMap parameters = new HashMap();

		String dataInicial = request.getParameter("dataInicial");
		String dataFinal = request.getParameter("dataFinal");
		String mes = request.getParameter("mes");
		Integer linha = Integer.parseInt(request.getParameter("linha"));
		System.out.println(dataInicial + " " + dataFinal + " " + mes + " " + linha);
		
		List<Parada> dados = null;
		try {
			parameters.put("mes", mes);
			parameters.put("linha", factoryService.linhaService.getLinha(linha).getLinDescricao());
			
			dados = factoryService.paradaService
					.getParadas(dataInicial, dataFinal, linha);
			
			corrigeDados(dados, mes);
		} catch (Exception e1) {
			e1.printStackTrace();
			throw new ServletException(e1);
		}

		reportFile = new File(request.getSession().getServletContext()
				.getRealPath("WEB-INF/relatorios/paradas.jasper"));
		
		JRDataSource jrds = new JRBeanCollectionDataSource(dados);
		
		byte[] bytes = null;
		try {
			bytes = JasperRunManager.runReportToPdf(reportFile.getPath(),
					parameters,jrds);
			
			ReportUtils.exportByteArrayToPdfStream(bytes, response);
		} catch (JRException e) {
			e.printStackTrace();
		}
	}
	
	@SuppressWarnings("unchecked")
	public void geraRelatorioMultiplo(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
		String dataInicial = request.getParameter("dataInicial");
		String dataFinal = request.getParameter("dataFinal");
		String mes = request.getParameter("mes");
						
		try {
			String reportFilePath = request.getSession().getServletContext()
						.getRealPath("WEB-INF/relatorios/paradas.jasper");
			
			List<JasperPrint> prints = new ArrayList<JasperPrint>();
			List<Linha> linhas = factoryService.linhaService.getList();
			for(Linha linha: linhas){
				if (temParada(linha)) {
					HashMap parameters = new HashMap();
					parameters.put("mes", mes);
					parameters.put("linha", linha.getLinDescricao());
					
					List<Parada> dados = factoryService.paradaService
							.getParadas(dataInicial, dataFinal, linha.getLinId());
					
					corrigeDados(dados, mes);
					
					JRDataSource jrds = new JRBeanCollectionDataSource(dados);
					JasperPrint jasperPrint = JasperFillManager.fillReport(
							reportFilePath, parameters, jrds);
					prints.add(jasperPrint);
				}
				
			}
			
			ReportUtils.exportMultipleReportsToPdfStream(prints, response.getOutputStream());
			
		} catch (Exception e) {
			e.printStackTrace();
			throw new ServletException(e);
		}
	}

	@SuppressWarnings("unchecked")
	private void corrigeDados(List<Parada> dados, String mes) throws Exception {
		
		String[] sts = mes.split("/");
		int ano = Integer.parseInt(sts[1]);
		int mesInt = Integer.parseInt(sts[0]) - 1;
		GregorianCalendar cal = new GregorianCalendar();
		cal.set(ano, mesInt, 0, 0, 0, 0);
		int max = cal.getMaximum(Calendar.DAY_OF_MONTH);
		List<MotivoParada> motivos;
		try {
			motivos = factoryService.motivoParadaService.getList();
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
		int motivosSize = motivos.size();
		
		List<Parada>[][] diaMotivo = new List[motivosSize+1][max+1];
		
		for (Parada parada : dados) {
			@SuppressWarnings("deprecation")
			int dia = parada.getParData().getDate();
			MotivoParada motivo = parada.getParMotivo();
			for (int i = 1; i <= motivosSize; i++ ) {
				if(motivo.equals(motivos.get(i-1))){
					if(diaMotivo[i][dia] == null)
						diaMotivo[i][dia] = new ArrayList();
					
					diaMotivo[i][dia].add(parada);
				}
			}
		}
		
		for(int i = 1; i <= motivosSize; i++){
			int acumulado = 0;
			for ( int j = 1; j <= max; j++ ) {     
    			if (diaMotivo[i][j] != null && !diaMotivo[i][j].isEmpty()){
    				List<Parada> paradas = diaMotivo[i][j];
    				int acumuladoParcial = 0;
    				for (Parada parada : paradas) {
    					acumuladoParcial += parada.getParTempo();
					}
    				for (Parada parada : paradas) {
    					parada.setParAcumulado(acumulado+acumuladoParcial);
					}
    				
    				acumulado += acumuladoParcial;
    			} else {
    				Parada parada = new Parada();
    				cal.set(ano, mesInt, j, 0, 0, 0);
    				parada.setParId(0);
    				parada.setParData(cal.getTime());
    				parada.setParTempo(0);
    				parada.setParMotivo(motivos.get(i-1)); 
    				parada.setParAcumulado(acumulado);

    				dados.add(parada);
    			}
            }  
		}
		
		Collections.sort(dados, new Comparator<Parada>(){

			@Override
			public int compare(Parada p1, Parada p2) {
				return p1.getParData().compareTo(p2.getParData());
			}
			
		});
		
	}
	
	private boolean temParada(Linha linha){
		for (Extrusao extrusao : linha.getExtrusaoCollection()) {
			if(extrusao.getExtTemParada())
				return true;
		}
		
		return false;
	}*/
	
}
