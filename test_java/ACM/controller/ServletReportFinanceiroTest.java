package ACM.controller;

import java.math.BigDecimal;
import java.util.List;

import junit.framework.TestCase;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;

import ACM.model.entity.Receita;
import ACM.model.entity.ReceitaAvulsa;
import ACM.model.entity.ResumoAnual;
import ACM.model.entity.Retirada;
import ACM.model.repository.hibernate.ConsultaHibernateDao;
import ACM.model.repository.hibernate.RelatorioHibernateDao;
import ACM.model.service.ConsultaServiceImpl;
import ACM.model.service.RelatorioServiceImpl;
import ACM.util.FinancasSocioImpressao;
import ACM.util.ResumoMensal;
import ACM.util.Tools;

@RunWith(SpringJUnit4ClassRunner.class)  
@ContextConfiguration(locations="classpath:./applicationContext.xml")  
@TransactionConfiguration(defaultRollback=true,transactionManager="sessionFactory")  

public class ServletReportFinanceiroTest extends TestCase {

	private static final double APLICACOES_GERUZA_2015 = 7650.0;

	private static final double QUANTIDADE_EXAMES_GERUZA_2015 = 5255.5;

	private static final double QUANTIDADE_EXAMES_COM_VALOR_GERUZA_2015 = 1241.0;

	private static final double QUANTIDADE_EXAMES_SEM_VALOR_GERUZA_2015 = 4014.5;

	private static final double QUANTIDADE_EXAMES_GERAL_2015 = 11400.0;

	private static final double QUANTIDADE_EXAMES_COM_VALOR_GERAL_2015 = 2856.0;

	private static final double QUANTIDADE_EXAMES_SEM_VALOR_GERAL_2015 = 8544.0;

	private static final double RECEITAS_AVULSAS_GERAL_2015 = 11000.0;

	private static final double RETIRADAS_GERAL_2015 = 235000.0;

	private static final double DINHEIRO_RECEBIDO_GERAL_2015 = 452949.32;

	private static final double RECEITAS_GERUZA_2015 = 467084.0;

	private static final double RECEITAS_COM_VALOR_GERUZA_2015 = 155058.0;

	private static final double RECEITAS_SEM_VALOR_GERUZA_2015 = 312026.0;

	private static final double DESPESAS_GERUZA_2015 = 200472.44;

	private static final double RETIRADAS_GERUZA_2015 = 115000.0;

	private static final double DINHEIRO_RECEBIDO_GERUZA_2015 = 182734.29;

	private static final double RECEITAS_GERAL_2015 = 1059189.09;

	private static final double RECEITAS_COM_VALOR_GERAL_2015 = 362659.0;

	private static final double RECEITAS_SEM_VALOR_GERAL_2015 = 696530.09;

	private static final double RECEITAS_AVULSAS_GERUZA_2015 = 5500.0;

	private static final double DESPESAS_GERAIS_2015 = 398401.94;

	private static final double SALDO_FINAL_GERUZA_2015 = 33272.74;

	private static final double APLICACOES_GERAL_2015 = 15300.0;

	private static final double SALDO_FINAL_GERAL_2015 = 31462.17;

	@Autowired
	private ConsultaHibernateDao consulta;
	
	@Autowired
	private RelatorioHibernateDao RelatorioHibernateDao;

	@Test
	public void testGetDistribuicaoAnualGeral() throws Exception {		
		ServletReportDistribuicaoAnual servlet = new ServletReportDistribuicaoAnual();
		((RelatorioServiceImpl)servlet.factoryService).setUsuarioRepository(RelatorioHibernateDao);
		((ConsultaServiceImpl)servlet.factoryServiceConsulta).setConsultaRepository(consulta);
		List<ReceitaAvulsa> receitasAvulsas = servlet.getReceitasAvulsasPorIntervalo("2015/01", "2015/12");
		List<ResumoAnual> resumosAnuais = servlet.getListaResumos(null, "2015/01", "2015/12", receitasAvulsas);
		assertEquals(1, resumosAnuais.size());
		ResumoAnual ra = resumosAnuais.get(0);
		System.out.println(Math.abs((APLICACOES_GERAL_2015) - Tools.stringToDoubleMonetario(ra.getAplicacoes())));
		System.out.println(Math.abs((SALDO_FINAL_GERAL_2015) - Tools.stringToDoubleMonetario(ra.getResiduo())));
		assertTrue(Math.abs((RECEITAS_GERAL_2015 + RECEITAS_AVULSAS_GERAL_2015 - DESPESAS_GERAIS_2015) - Tools.stringToDoubleMonetario(ra.getResultado())) < 1.0);
		assertTrue(Math.abs((DINHEIRO_RECEBIDO_GERAL_2015) - Tools.stringToDoubleMonetario(ra.getDinheiroRecebidoEmConta())) < 1.0);
		assertTrue(Math.abs((RETIRADAS_GERAL_2015) - Tools.stringToDoubleMonetario(ra.getRetiradas())) < 1.0);
		assertTrue(Math.abs((APLICACOES_GERAL_2015) - Tools.stringToDoubleMonetario(ra.getAplicacoes())) < 1.0);
		assertTrue(Math.abs((SALDO_FINAL_GERAL_2015) - Tools.stringToDoubleMonetario(ra.getResiduo())) < 1.0);
	}

	@Test
	public void testGetDistribuicaoAnualPorSocia() throws Exception {		
		ServletReportDistribuicaoAnual servlet = new ServletReportDistribuicaoAnual();
		((RelatorioServiceImpl)servlet.factoryService).setUsuarioRepository(RelatorioHibernateDao);
		((ConsultaServiceImpl)servlet.factoryServiceConsulta).setConsultaRepository(consulta);
		List<ReceitaAvulsa> receitasAvulsas = servlet.getReceitasAvulsasPorIntervaloESocia("1", "2015/01", "2015/12");
		List<ResumoAnual> resumosAnuais = servlet.getListaResumos("1", "2015/01", "2015/12", receitasAvulsas);
		assertEquals(1, resumosAnuais.size());
		ResumoAnual ra = resumosAnuais.get(0);
		assertTrue(Math.abs((RECEITAS_GERUZA_2015 + RECEITAS_AVULSAS_GERUZA_2015 - DESPESAS_GERUZA_2015) - Tools.stringToDoubleMonetario(ra.getResultado())) < 1.0);
		assertTrue(Math.abs((DINHEIRO_RECEBIDO_GERUZA_2015) - Tools.stringToDoubleMonetario(ra.getDinheiroRecebidoEmConta())) < 1.0);
		assertTrue(Math.abs((RETIRADAS_GERUZA_2015) - Tools.stringToDoubleMonetario(ra.getRetiradas())) < 1.0);
		assertTrue(Math.abs((APLICACOES_GERUZA_2015) - Tools.stringToDoubleMonetario(ra.getAplicacoes())) < 1.0);
		assertTrue(Math.abs((SALDO_FINAL_GERUZA_2015) - Tools.stringToDoubleMonetario(ra.getResiduo())) < 1.0);
	}
	
	@Test
	public void testGetExamesPorConvenioPorSocia() {		
		ServletReportExamesPorConvenio servlet = new ServletReportExamesPorConvenio();
		((ConsultaServiceImpl)servlet.factoryService).setConsultaRepository(consulta);
		List<Receita> resumoMensalGeral = servlet.getReceitasPorSocio("1", "2015/01", "2015/12");
		assertTrue(!resumoMensalGeral.isEmpty());
		assertEquals(QUANTIDADE_EXAMES_GERUZA_2015, new BigDecimal(getSomaExames(resumoMensalGeral)).setScale(1, BigDecimal.ROUND_HALF_UP).doubleValue());	
	}
	
	@Test
	public void testGetExamesPorConvenioGeral() {		
		ServletReportExamesPorConvenio servlet = new ServletReportExamesPorConvenio();
		((ConsultaServiceImpl)servlet.factoryService).setConsultaRepository(consulta);
		List<Receita> resumoMensalGeral = servlet.getReceitasGeral("2015/01", "2015/12");
		assertTrue(!resumoMensalGeral.isEmpty());
		assertEquals(QUANTIDADE_EXAMES_GERAL_2015, new BigDecimal(getSomaExames(resumoMensalGeral)).setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue());	
	}
	
	@Test
	public void testGetValorRecebidoPorSocia() {		
		ServletReportDinheiroRecebidoSocio servlet = new ServletReportDinheiroRecebidoSocio();
		((ConsultaServiceImpl)servlet.factoryService).setConsultaRepository(consulta);
		List<Receita> resumoMensalGeral = servlet.getValorRecebidoPorPeriodoSocio("1", "2015/01", "2015/12");
		assertTrue(!resumoMensalGeral.isEmpty());
		assertEquals(DINHEIRO_RECEBIDO_GERUZA_2015, new BigDecimal(getSomaReceitas(resumoMensalGeral)).setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue());	
	}
	
	@Test
	public void testGetReceitasDinheiroAReceberPorSocia() {		
		ServletReportDinheiroAReceberSocio servlet = new ServletReportDinheiroAReceberSocio();
		((ConsultaServiceImpl)servlet.factoryService).setConsultaRepository(consulta);
		List<FinancasSocioImpressao> resumoMensalGeral = servlet.getReceitasPorPeriodoSocio("1", "2015/01", "2015/12");
		assertTrue(!resumoMensalGeral.isEmpty());
		assertEquals(RECEITAS_GERUZA_2015, new BigDecimal(getTotalFinancasSocioImpressao(resumoMensalGeral)).setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue());	
	}

	@Test
	public void testGetDespesasDinheiroAReceberPorSocia() {		
		ServletReportDinheiroAReceberSocio servlet = new ServletReportDinheiroAReceberSocio();
		((ConsultaServiceImpl)servlet.factoryService).setConsultaRepository(consulta);
		List<FinancasSocioImpressao> resumoMensalGeral = servlet.getDespesasPorPeriodoSocio("1", "2015/01", "2015/12");
		assertTrue(!resumoMensalGeral.isEmpty());
		assertEquals(-DESPESAS_GERUZA_2015, new BigDecimal(getTotalFinancasSocioImpressao(resumoMensalGeral)).setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue());	
	}

	@Test
	public void testGetReceitasAvulsasDinheiroAReceberPorSocia() {		
		ServletReportDinheiroAReceberSocio servlet = new ServletReportDinheiroAReceberSocio();
		((ConsultaServiceImpl)servlet.factoryService).setConsultaRepository(consulta);
		List<FinancasSocioImpressao> resumoMensalGeral = servlet.getReceitasAvulsasPorPeriodoSocio("1", "2015/01", "2015/12");
		assertTrue(!resumoMensalGeral.isEmpty());
		assertEquals(RECEITAS_AVULSAS_GERUZA_2015, new BigDecimal(getTotalFinancasSocioImpressao(resumoMensalGeral)).setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue());	
	}

	@Test
	public void testGetRetiradasDinheiroAReceberPorSocia() {		
		ServletReportDinheiroAReceberSocio servlet = new ServletReportDinheiroAReceberSocio();
		((ConsultaServiceImpl)servlet.factoryService).setConsultaRepository(consulta);
		List<FinancasSocioImpressao> resumoMensalGeral = servlet.getRetiradasPorPeriodoSocio("1", "2015/01", "2015/12");
		assertTrue(!resumoMensalGeral.isEmpty());
		assertEquals(-RETIRADAS_GERUZA_2015, new BigDecimal(getTotalFinancasSocioImpressao(resumoMensalGeral)).setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue());	
	}

	@Test
	public void testGetAplicacoesDinheiroAReceberPorSocia() {		
		ServletReportDinheiroAReceberSocio servlet = new ServletReportDinheiroAReceberSocio();
		((ConsultaServiceImpl)servlet.factoryService).setConsultaRepository(consulta);
		List<FinancasSocioImpressao> resumoMensalGeral = servlet.getAplicacaosPorPeriodoSocio("1", "2015/01", "2015/12");
		assertTrue(!resumoMensalGeral.isEmpty());
		assertEquals(-APLICACOES_GERUZA_2015, new BigDecimal(getTotalFinancasSocioImpressao(resumoMensalGeral)).setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue());	
	}

	@Test
	public void testGetValorRecebidoDinheiroAReceberPorSocia() {		
		ServletReportDinheiroAReceberSocio servlet = new ServletReportDinheiroAReceberSocio();
		((ConsultaServiceImpl)servlet.factoryService).setConsultaRepository(consulta);
		List<FinancasSocioImpressao> resumoMensalGeral = servlet.getValorRecebidoPorPeriodoSocio("1", "2015/01", "2015/12");
		assertTrue(!resumoMensalGeral.isEmpty());
		assertEquals(-DINHEIRO_RECEBIDO_GERUZA_2015, new BigDecimal(getTotalFinancasSocioImpressao(resumoMensalGeral)).setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue());	
	}

	@Test
	public void testGetReceitasSaldoGeralPorSocia() {		
		ServletReportGeralSocio servlet = new ServletReportGeralSocio();
		((ConsultaServiceImpl)servlet.factoryService).setConsultaRepository(consulta);
		List<FinancasSocioImpressao> resumoMensalGeral = servlet.getReceitasPorPeriodoSocio("1", "2015/01", "2015/12");
		assertTrue(!resumoMensalGeral.isEmpty());
		assertEquals(RECEITAS_GERUZA_2015, new BigDecimal(getTotalFinancasSocioImpressao(resumoMensalGeral)).setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue());	
	}

	@Test
	public void testGetDespesasSaldoGeralPorSocia() {		
		ServletReportGeralSocio servlet = new ServletReportGeralSocio();
		((ConsultaServiceImpl)servlet.factoryService).setConsultaRepository(consulta);
		List<FinancasSocioImpressao> resumoMensalGeral = servlet.getDespesasPorPeriodoSocio("1", "2015/01", "2015/12");
		assertTrue(!resumoMensalGeral.isEmpty());
		assertEquals(-DESPESAS_GERUZA_2015, new BigDecimal(getTotalFinancasSocioImpressao(resumoMensalGeral)).setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue());	
	}

	@Test
	public void testGetReceitasAvulsasSaldoGeralPorSocia() {		
		ServletReportGeralSocio servlet = new ServletReportGeralSocio();
		((ConsultaServiceImpl)servlet.factoryService).setConsultaRepository(consulta);
		List<FinancasSocioImpressao> resumoMensalGeral = servlet.getReceitasAvulsasPorPeriodoSocio("1", "2015/01", "2015/12");
		assertTrue(!resumoMensalGeral.isEmpty());
		assertEquals(RECEITAS_AVULSAS_GERUZA_2015, new BigDecimal(getTotalFinancasSocioImpressao(resumoMensalGeral)).setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue());	
	}

	@Test
	public void testGetRetiradasSaldoGeralPorSocia() {		
		ServletReportGeralSocio servlet = new ServletReportGeralSocio();
		((ConsultaServiceImpl)servlet.factoryService).setConsultaRepository(consulta);
		List<FinancasSocioImpressao> resumoMensalGeral = servlet.getRetiradasPorPeriodoSocio("1", "2015/01", "2015/12");
		assertTrue(!resumoMensalGeral.isEmpty());
		assertEquals(-RETIRADAS_GERUZA_2015, new BigDecimal(getTotalFinancasSocioImpressao(resumoMensalGeral)).setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue());	
	}

	@Test
	public void testGetAplicacoesSaldoGeralPorSocia() {		
		ServletReportGeralSocio servlet = new ServletReportGeralSocio();
		((ConsultaServiceImpl)servlet.factoryService).setConsultaRepository(consulta);
		List<FinancasSocioImpressao> resumoMensalGeral = servlet.getAplicacaosPorPeriodoSocio("1", "2015/01", "2015/12");
		assertTrue(!resumoMensalGeral.isEmpty());
		assertEquals(-APLICACOES_GERUZA_2015, new BigDecimal(getTotalFinancasSocioImpressao(resumoMensalGeral)).setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue());	
	}

	@Test
	public void testGetDemonstrativoFinanceiroPorSocia() {		
		ServletReportDemonstrativoFinanceiro servlet = new ServletReportDemonstrativoFinanceiro();
		((ConsultaServiceImpl)servlet.factoryService).setConsultaRepository(consulta);
		List<ResumoMensal> resumoMensalGeral = servlet.getResumoMensalPorSocia("1", "", "2015/01", "2015/12");
		assertTrue(!resumoMensalGeral.isEmpty());
		assertEquals(RECEITAS_GERUZA_2015, getTotalReceitas(resumoMensalGeral));
		assertEquals(RECEITAS_COM_VALOR_GERUZA_2015, getTotalReceitasComValor(resumoMensalGeral));
		assertEquals(RECEITAS_SEM_VALOR_GERUZA_2015, getTotalReceitasSemValor(resumoMensalGeral));
		assertEquals(DESPESAS_GERUZA_2015, getTotalDespesas(resumoMensalGeral));		
	}

	@Test
	public void testGetDemonstrativoFinanceiroQuantidadeExamesPorSocia() {		
		ServletReportDemonstrativoFinanceiro servlet = new ServletReportDemonstrativoFinanceiro();
		((ConsultaServiceImpl)servlet.factoryService).setConsultaRepository(consulta);
		List<ResumoMensal> resumoMensalGeral = servlet.getResumoMensalPorSocia("1", "", "2015/01", "2015/12");
		Double quantidadeExames = servlet.getQuantidadeExamesPorSocio("1", "2015/01", "2015/12", resumoMensalGeral);
		assertEquals(QUANTIDADE_EXAMES_COM_VALOR_GERUZA_2015, quantidadeExames);		
		Double quantidadeExamesSemValor = servlet.getQuantidadeExamesSemValorPorSocio("1", "2015/01", "2015/12", resumoMensalGeral);
		assertEquals(QUANTIDADE_EXAMES_SEM_VALOR_GERUZA_2015, quantidadeExamesSemValor);		
	}

	@Test
	public void testGetDemonstrativoFinanceiroReceitasAvulsasPorSocia() {		
		ServletReportDemonstrativoFinanceiro servlet = new ServletReportDemonstrativoFinanceiro();
		((ConsultaServiceImpl)servlet.factoryService).setConsultaRepository(consulta);
		List<ReceitaAvulsa> receitasAvulsas = servlet.getReceitasAvulsasPorSocia("1", "2015/01", "2015/12");
		assertTrue(!receitasAvulsas.isEmpty());
		assertEquals(RECEITAS_AVULSAS_GERUZA_2015, getTotalReceitasAvulsas(receitasAvulsas));		
	}

	@Test
	public void testGetDemonstrativoFinanceiroRetiradasPorSocia() {		
		ServletReportDemonstrativoFinanceiro servlet = new ServletReportDemonstrativoFinanceiro();
		((ConsultaServiceImpl)servlet.factoryService).setConsultaRepository(consulta);
		List<Retirada> retiradas = servlet.getRetiradasPorPeriodoSocio("1", "2015/01", "2015/12");
		assertTrue(!retiradas.isEmpty());
		assertEquals(RETIRADAS_GERUZA_2015, getTotalRetiradas(retiradas));		
	}

	@Test
	public void testGetDemonstrativoFinanceiroValorRecebidoPorSocia() {		
		ServletReportDemonstrativoFinanceiro servlet = new ServletReportDemonstrativoFinanceiro();
		((ConsultaServiceImpl)servlet.factoryService).setConsultaRepository(consulta);
		double retiradas = servlet.getValorRecebidoPorPeriodoSocio("1", "2015/01", "2015/12");
		assertEquals( DINHEIRO_RECEBIDO_GERUZA_2015, retiradas);		
	}

	@Test
	public void testGetDemonstrativoFinanceiroGeral() {		
		ServletReportDemonstrativoFinanceiro servlet = new ServletReportDemonstrativoFinanceiro();
		((ConsultaServiceImpl)servlet.factoryService).setConsultaRepository(consulta);
		List<ResumoMensal> resumoMensalGeral = servlet.getResumoMensalGeral("2015/01", "2015/12");
		assertTrue(!resumoMensalGeral.isEmpty());
		assertEquals(RECEITAS_GERAL_2015, getTotalReceitasGeral(resumoMensalGeral));
		assertEquals(RECEITAS_COM_VALOR_GERAL_2015, getTotalReceitasComValorGeral(resumoMensalGeral));
		assertEquals(RECEITAS_SEM_VALOR_GERAL_2015, new BigDecimal(getTotalReceitasSemValorGeral(resumoMensalGeral)).setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue());
		assertEquals(DESPESAS_GERAIS_2015, getTotalDespesasGeral(resumoMensalGeral));
	}

	@Test
	public void testGetDemonstrativoFinanceiroQuantidadeExamesGeral() {		
		ServletReportDemonstrativoFinanceiro servlet = new ServletReportDemonstrativoFinanceiro();
		((ConsultaServiceImpl)servlet.factoryService).setConsultaRepository(consulta);
		List<ResumoMensal> resumoMensalGeral = servlet.getResumoMensalGeral("2015/01", "2015/12");
		Integer quantidadeExames = servlet.getQuantidadeExamesGeral("2015/01", "2015/12", resumoMensalGeral);
		assertEquals(QUANTIDADE_EXAMES_COM_VALOR_GERAL_2015, quantidadeExames.doubleValue());		
		Integer quantidadeExamesSemValor = servlet.getQuantidadeExamesGeralSemValor("2015/01", "2015/12", resumoMensalGeral);
		assertEquals(QUANTIDADE_EXAMES_SEM_VALOR_GERAL_2015, quantidadeExamesSemValor.doubleValue());		
	}

	@Test
	public void testGetDemonstrativoFinanceiroReceitasAvulsasGeral() {		
		ServletReportDemonstrativoFinanceiro servlet = new ServletReportDemonstrativoFinanceiro();
		((ConsultaServiceImpl)servlet.factoryService).setConsultaRepository(consulta);
		List<ReceitaAvulsa> receitasAvulsas = servlet.getReceitasAvulsasGeral("2015/01", "2015/12");
		assertTrue(!receitasAvulsas.isEmpty());
		assertEquals(RECEITAS_AVULSAS_GERAL_2015, getTotalReceitasAvulsas(receitasAvulsas));		
	}

	@Test
	public void testGetDemonstrativoFinanceiroRetiradasGeral() {		
		ServletReportDemonstrativoFinanceiro servlet = new ServletReportDemonstrativoFinanceiro();
		((ConsultaServiceImpl)servlet.factoryService).setConsultaRepository(consulta);
		List<Retirada> retiradas = servlet.getRetiradasPorPeriodoGeral("2015/01", "2015/12");
		assertTrue(!retiradas.isEmpty());
		assertEquals(RETIRADAS_GERAL_2015, getTotalRetiradas(retiradas));		
	}

	@Test
	public void testGetDemonstrativoFinanceiroValorRecebidoGeral() {		
		ServletReportDemonstrativoFinanceiro servlet = new ServletReportDemonstrativoFinanceiro();
		((ConsultaServiceImpl)servlet.factoryService).setConsultaRepository(consulta);
		double retiradas = servlet.getValorRecebidoPorPeriodoGeral("2015/01", "2015/12");
		assertEquals( DINHEIRO_RECEBIDO_GERAL_2015, new BigDecimal(retiradas).setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue());		
	}
	
	@Test
	public void testGetResumoMensalPorSocia() {		
		ServletReportResumoMensal servlet = new ServletReportResumoMensal();
		((ConsultaServiceImpl)servlet.factoryService).setConsultaRepository(consulta);
		List<ResumoMensal> resumoMensalGeral = servlet.getResumoMensalPorSocia("1", "", "2015/01", "2015/12");
		assertTrue(!resumoMensalGeral.isEmpty());
		assertEquals(RECEITAS_GERUZA_2015, getTotalReceitas(resumoMensalGeral));
		assertEquals(RECEITAS_COM_VALOR_GERUZA_2015, getTotalReceitasComValor(resumoMensalGeral));
		assertEquals(RECEITAS_SEM_VALOR_GERUZA_2015, getTotalReceitasSemValor(resumoMensalGeral));
		assertEquals(DESPESAS_GERUZA_2015, getTotalDespesas(resumoMensalGeral));		
	}

	@Test
	public void testGetResumoMensalReceitasAvulsasPorSocia() {		
		ServletReportResumoMensal servlet = new ServletReportResumoMensal();
		((ConsultaServiceImpl)servlet.factoryService).setConsultaRepository(consulta);
		List<ReceitaAvulsa> receitasAvulsas = servlet.getReceitasAvulsasPorSocia("1", "2015/01", "2015/12");
		assertTrue(!receitasAvulsas.isEmpty());
		assertEquals(RECEITAS_AVULSAS_GERUZA_2015, getTotalReceitasAvulsas(receitasAvulsas));		
	}

	@Test
	public void testGetResumoMensalValorRecebidoPorSocia() {		
		ServletReportResumoMensal servlet = new ServletReportResumoMensal();
		((ConsultaServiceImpl)servlet.factoryService).setConsultaRepository(consulta);
		double retiradas = servlet.getValorRecebidoPorPeriodoSocio("1", "2015/01", "2015/12");
		assertEquals( DINHEIRO_RECEBIDO_GERUZA_2015, retiradas);		
	}

	@Test
	public void testGetResumoMensalGeral() {		
		ServletReportResumoMensal servlet = new ServletReportResumoMensal();
		((ConsultaServiceImpl)servlet.factoryService).setConsultaRepository(consulta);
		List<ResumoMensal> resumoMensalGeral = servlet.getResumoMensalGeral("2015/01", "2015/12");
		assertTrue(!resumoMensalGeral.isEmpty());
		assertEquals(RECEITAS_GERAL_2015, getTotalReceitasGeral(resumoMensalGeral));
		assertEquals(RECEITAS_COM_VALOR_GERAL_2015, getTotalReceitasComValorGeral(resumoMensalGeral));
		assertEquals(RECEITAS_SEM_VALOR_GERAL_2015, new BigDecimal(getTotalReceitasSemValorGeral(resumoMensalGeral)).setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue());
		assertEquals(DESPESAS_GERAIS_2015, getTotalDespesasGeral(resumoMensalGeral));
	}

	@Test
	public void testGetResumoMensalReceitasAvulsasGeral() {		
		ServletReportResumoMensal servlet = new ServletReportResumoMensal();
		((ConsultaServiceImpl)servlet.factoryService).setConsultaRepository(consulta);
		List<ReceitaAvulsa> receitasAvulsas = servlet.getReceitasAvulsasGeral("2015/01", "2015/12");
		assertTrue(!receitasAvulsas.isEmpty());
		assertEquals(RECEITAS_AVULSAS_GERAL_2015, getTotalReceitasAvulsas(receitasAvulsas));		
	}
	
	private Double getTotalFinancasSocioImpressao(List<FinancasSocioImpressao> resumoMensalGeral) {
		double totalReceitas = 0.0;
		for (FinancasSocioImpressao resumoMensal : resumoMensalGeral) {
			totalReceitas += new Double(resumoMensal.getValor().replaceAll("\\.", "").replaceAll(",", "\\."));
		}
		return totalReceitas;
	}
	
	private Double getSomaReceitas(List<Receita> resumoMensalGeral) {
		double totalReceitas = 0.0;
		for (Receita resumoMensal : resumoMensalGeral) {
			totalReceitas += new Double(resumoMensal.getValorRateado().replaceAll("\\.", "").replaceAll(",", "\\."));
		}
		return totalReceitas;
	}
	
	private Double getSomaExames(List<Receita> resumoMensalGeral) {
		double totalReceitas = 0;
		for (Receita resumoMensal : resumoMensalGeral) {
			totalReceitas += (Integer.valueOf(resumoMensal.getRecQuantidadeExames()) * Tools.stringToDouble(resumoMensal.getPorcentagemDono()) / 100);
		}
		return totalReceitas;
	}

	private Double getTotalReceitas(List<ResumoMensal> resumoMensalGeral) {
		return getTotalReceitasComValor(resumoMensalGeral) + getTotalReceitasSemValor(resumoMensalGeral);
	}

	private Double getTotalReceitasComValor(List<ResumoMensal> resumoMensalGeral) {
		double totalReceitas = 0.0;
		for (ResumoMensal resumoMensal : resumoMensalGeral) {
			totalReceitas += new Double(resumoMensal.getValorReceita().replaceAll("\\.", "").replaceAll(",", "\\."));
		}
		return totalReceitas;
	}

	private Double getTotalReceitasSemValor(List<ResumoMensal> resumoMensalGeral) {
		double totalReceitas = 0.0;
		for (ResumoMensal resumoMensal : resumoMensalGeral) {
			totalReceitas += new Double(resumoMensal.getValorReceitaSemValorAVista().replaceAll("\\.", "").replaceAll(",", "\\."));
		}
		return totalReceitas;
	}

	private Double getTotalDespesas(List<ResumoMensal> resumoMensalGeral) {
		double totalDespesas = 0.0;
		for (ResumoMensal resumoMensal : resumoMensalGeral) {
			totalDespesas += new Double(resumoMensal.getValorDespesa().replaceAll("\\.", "").replaceAll(",", "\\."));
		}
		return totalDespesas;
	}

	private Double getTotalReceitasGeral(List<ResumoMensal> resumoMensalGeral) {
		return getTotalReceitasComValorGeral(resumoMensalGeral) + getTotalReceitasSemValorGeral(resumoMensalGeral);
	}

	private Double getTotalReceitasComValorGeral(List<ResumoMensal> resumoMensalGeral) {
		double totalReceitas = 0.0;
		for (ResumoMensal resumoMensal : resumoMensalGeral) {
			totalReceitas += new Double(resumoMensal.getValorReceitaDireta().replaceAll("\\.", "").replaceAll(",", "\\."));
		}
		return totalReceitas;
	}

	private Double getTotalReceitasSemValorGeral(List<ResumoMensal> resumoMensalGeral) {
		double totalReceitas = 0.0;
		for (ResumoMensal resumoMensal : resumoMensalGeral) {
			totalReceitas += new Double(resumoMensal.getValorReceitaDiretaSemValorAVista().replaceAll("\\.", "").replaceAll(",", "\\."));
		}
		return totalReceitas;
	}

	private Double getTotalDespesasGeral(List<ResumoMensal> resumoMensalGeral) {
		double totalDespesas = 0.0;
		for (ResumoMensal resumoMensal : resumoMensalGeral) {
			totalDespesas += new Double(resumoMensal.getValorDespesaDireta().replaceAll("\\.", "").replaceAll(",", "\\."));
		}
		return totalDespesas;
	}

	private Double getTotalReceitasAvulsas(List<ReceitaAvulsa> receitas) {
		double totalReceitasAvulsas = 0.0;
		for (ReceitaAvulsa receita : receitas) {
			totalReceitasAvulsas += new Double(receita.getValorRateado().replaceAll("\\.", "").replaceAll(",", "\\."));
		}
		return totalReceitasAvulsas;
	}

	private Double getTotalRetiradas(List<Retirada> retiradas) {
		double totalRetiradas = 0.0;
		for (Retirada retirada : retiradas) {
			totalRetiradas += new Double(retirada.getRetValor().replaceAll("\\.", "").replaceAll(",", "\\."));
		}
		return totalRetiradas;
	}

}
