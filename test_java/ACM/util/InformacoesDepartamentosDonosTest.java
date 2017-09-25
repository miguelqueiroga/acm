package ACM.util;

import static ACM.util.InformacoesDepartamentosDonos.US_GG;
import static ACM.util.InformacoesDepartamentosDonos.US_VV;
import static ACM.util.InformacoesDepartamentosDonos.US_GERAL;
import static ACM.util.InformacoesDepartamentosDonos.MX_GERAL;
import static ACM.util.InformacoesDepartamentosDonos.MX_GG;
import static ACM.util.InformacoesDepartamentosDonos.MX_VV;
import static ACM.util.InformacoesDepartamentosDonos.PROCED_GERAL;
import static ACM.util.InformacoesDepartamentosDonos.US;
import static ACM.util.InformacoesDepartamentosDonos.PROCED_GG;
import static ACM.util.InformacoesDepartamentosDonos.PROCED_VV;

import java.util.Arrays;

import ACM.model.entity.Vencimento;
import junit.framework.TestCase;

public class InformacoesDepartamentosDonosTest extends TestCase {

	public void testGetPorcentagensAntesNovaEstrutura() {
		Vencimento vencimentoAnteriorNovaEstrutura = new Vencimento();
		vencimentoAnteriorNovaEstrutura.setVenNome("2014/09");
		assertEquals("50,00", InformacoesDepartamentosDonos.getPorcentagemGeral(US_GG, US_GERAL, vencimentoAnteriorNovaEstrutura));
		assertEquals("33,00", InformacoesDepartamentosDonos.getPorcentagemGeral(US_GG, US, vencimentoAnteriorNovaEstrutura));		
		assertEquals("50,00", InformacoesDepartamentosDonos.getPorcentagemGeral(US_VV, US_GERAL, vencimentoAnteriorNovaEstrutura));
		assertEquals("33,00", InformacoesDepartamentosDonos.getPorcentagemGeral(US_VV, US, vencimentoAnteriorNovaEstrutura));		
		assertEquals("66,00", InformacoesDepartamentosDonos.getPorcentagemGeral(US_GERAL, US, vencimentoAnteriorNovaEstrutura));
		assertEquals("100,00", InformacoesDepartamentosDonos.getPorcentagemGeral(US_GERAL, US_GG, vencimentoAnteriorNovaEstrutura));		
		assertEquals("100,00", InformacoesDepartamentosDonos.getPorcentagemGeral(US_GERAL, US_VV, vencimentoAnteriorNovaEstrutura));		
		assertEquals("32,00", InformacoesDepartamentosDonos.getPorcentagemGeral(MX_GERAL, US, vencimentoAnteriorNovaEstrutura));
		assertEquals("100,00", InformacoesDepartamentosDonos.getPorcentagemGeral(MX_GERAL, MX_GG, vencimentoAnteriorNovaEstrutura));		
		assertEquals("100,00", InformacoesDepartamentosDonos.getPorcentagemGeral(MX_GERAL, MX_VV, vencimentoAnteriorNovaEstrutura));		
		assertEquals("50,00", InformacoesDepartamentosDonos.getPorcentagemGeral(MX_GG, MX_GERAL, vencimentoAnteriorNovaEstrutura));
		assertEquals("16,00", InformacoesDepartamentosDonos.getPorcentagemGeral(MX_GG, US, vencimentoAnteriorNovaEstrutura));		
		assertEquals("50,00", InformacoesDepartamentosDonos.getPorcentagemGeral(MX_VV, MX_GERAL, vencimentoAnteriorNovaEstrutura));
		assertEquals("16,00", InformacoesDepartamentosDonos.getPorcentagemGeral(MX_VV, US, vencimentoAnteriorNovaEstrutura));		
		assertEquals("2,00", InformacoesDepartamentosDonos.getPorcentagemGeral(PROCED_GERAL, US, vencimentoAnteriorNovaEstrutura));
		assertEquals("100,00", InformacoesDepartamentosDonos.getPorcentagemGeral(PROCED_GERAL, PROCED_GG, vencimentoAnteriorNovaEstrutura));		
		assertEquals("100,00", InformacoesDepartamentosDonos.getPorcentagemGeral(PROCED_GERAL, PROCED_VV, vencimentoAnteriorNovaEstrutura));		
		assertEquals("50,00", InformacoesDepartamentosDonos.getPorcentagemGeral(PROCED_GG, PROCED_GERAL, vencimentoAnteriorNovaEstrutura));
		assertEquals("1,00", InformacoesDepartamentosDonos.getPorcentagemGeral(PROCED_GG, US, vencimentoAnteriorNovaEstrutura));		
		assertEquals("50,00", InformacoesDepartamentosDonos.getPorcentagemGeral(PROCED_VV, PROCED_GERAL, vencimentoAnteriorNovaEstrutura));
		assertEquals("1,00", InformacoesDepartamentosDonos.getPorcentagemGeral(PROCED_VV, US, vencimentoAnteriorNovaEstrutura));		
	}
	
	public void testGetPorcentagensAposNovaEstrutura() {
		Vencimento vencimentoAnteriorNovaEstrutura = new Vencimento();
		vencimentoAnteriorNovaEstrutura.setVenNome("2014/10");
		assertEquals("50,00", InformacoesDepartamentosDonos.getPorcentagemGeral(US_GG, US_GERAL, vencimentoAnteriorNovaEstrutura));
		assertEquals("49,00", InformacoesDepartamentosDonos.getPorcentagemGeral(US_GG, US, vencimentoAnteriorNovaEstrutura));		
		assertEquals("50,00", InformacoesDepartamentosDonos.getPorcentagemGeral(US_VV, US_GERAL, vencimentoAnteriorNovaEstrutura));
		assertEquals("49,00", InformacoesDepartamentosDonos.getPorcentagemGeral(US_VV, US, vencimentoAnteriorNovaEstrutura));		
		assertEquals("98,00", InformacoesDepartamentosDonos.getPorcentagemGeral(US_GERAL, US, vencimentoAnteriorNovaEstrutura));
		assertEquals("100,00", InformacoesDepartamentosDonos.getPorcentagemGeral(US_GERAL, US_GG, vencimentoAnteriorNovaEstrutura));		
		assertEquals("100,00", InformacoesDepartamentosDonos.getPorcentagemGeral(US_GERAL, US_VV, vencimentoAnteriorNovaEstrutura));		
		assertEquals("0,00", InformacoesDepartamentosDonos.getPorcentagemGeral(MX_GERAL, US, vencimentoAnteriorNovaEstrutura));
		assertEquals("100,00", InformacoesDepartamentosDonos.getPorcentagemGeral(MX_GERAL, MX_GG, vencimentoAnteriorNovaEstrutura));		
		assertEquals("100,00", InformacoesDepartamentosDonos.getPorcentagemGeral(MX_GERAL, MX_VV, vencimentoAnteriorNovaEstrutura));		
		assertEquals("50,00", InformacoesDepartamentosDonos.getPorcentagemGeral(MX_GG, MX_GERAL, vencimentoAnteriorNovaEstrutura));
		assertEquals("0,00", InformacoesDepartamentosDonos.getPorcentagemGeral(MX_GG, US, vencimentoAnteriorNovaEstrutura));		
		assertEquals("50,00", InformacoesDepartamentosDonos.getPorcentagemGeral(MX_VV, MX_GERAL, vencimentoAnteriorNovaEstrutura));
		assertEquals("0,00", InformacoesDepartamentosDonos.getPorcentagemGeral(MX_VV, US, vencimentoAnteriorNovaEstrutura));		
		assertEquals("2,00", InformacoesDepartamentosDonos.getPorcentagemGeral(PROCED_GERAL, US, vencimentoAnteriorNovaEstrutura));
		assertEquals("100,00", InformacoesDepartamentosDonos.getPorcentagemGeral(PROCED_GERAL, PROCED_GG, vencimentoAnteriorNovaEstrutura));		
		assertEquals("100,00", InformacoesDepartamentosDonos.getPorcentagemGeral(PROCED_GERAL, PROCED_VV, vencimentoAnteriorNovaEstrutura));		
		assertEquals("50,00", InformacoesDepartamentosDonos.getPorcentagemGeral(PROCED_GG, PROCED_GERAL, vencimentoAnteriorNovaEstrutura));
		assertEquals("1,00", InformacoesDepartamentosDonos.getPorcentagemGeral(PROCED_GG, US, vencimentoAnteriorNovaEstrutura));		
		assertEquals("50,00", InformacoesDepartamentosDonos.getPorcentagemGeral(PROCED_VV, PROCED_GERAL, vencimentoAnteriorNovaEstrutura));
		assertEquals("1,00", InformacoesDepartamentosDonos.getPorcentagemGeral(PROCED_VV, US, vencimentoAnteriorNovaEstrutura));		
	}

	public void testGetDepartamentosIndiretosAntesNovaEstrutura() {
		Vencimento vencimentoAnteriorNovaEstrutura = new Vencimento();
		vencimentoAnteriorNovaEstrutura.setVenNome("2014/09");
		assertEquals(Arrays.asList(US_GERAL.toString(), US.toString()), InformacoesDepartamentosDonos.getDepartamentosIndiretosEstruturaAntiga(US_GG));
		assertEquals(Arrays.asList(US_GERAL.toString(), US.toString()), InformacoesDepartamentosDonos.getDepartamentosIndiretosEstruturaAntiga(US_VV));
		assertEquals(Arrays.asList(US_GG.toString(), US_VV.toString(), US.toString()), InformacoesDepartamentosDonos.getDepartamentosIndiretosEstruturaAntiga(US_GERAL));
		assertEquals(Arrays.asList(MX_GERAL.toString(), US.toString()), InformacoesDepartamentosDonos.getDepartamentosIndiretosEstruturaAntiga(MX_GG));
		assertEquals(Arrays.asList(MX_GERAL.toString(), US.toString()), InformacoesDepartamentosDonos.getDepartamentosIndiretosEstruturaAntiga(MX_VV));
		assertEquals(Arrays.asList(MX_GG.toString(), MX_VV.toString(), US.toString()), InformacoesDepartamentosDonos.getDepartamentosIndiretosEstruturaAntiga(MX_GERAL));
		assertEquals(Arrays.asList(PROCED_GERAL.toString(), US.toString()), InformacoesDepartamentosDonos.getDepartamentosIndiretosEstruturaAntiga(PROCED_GG));
		assertEquals(Arrays.asList(PROCED_GERAL.toString(), US.toString()), InformacoesDepartamentosDonos.getDepartamentosIndiretosEstruturaAntiga(PROCED_VV));
		assertEquals(Arrays.asList(PROCED_GG.toString(), PROCED_VV.toString(), US.toString()), InformacoesDepartamentosDonos.getDepartamentosIndiretosEstruturaAntiga(PROCED_GERAL));
		assertEquals(Arrays.asList(US_GG.toString(), US_VV.toString(), US_GERAL.toString(), MX_GERAL.toString(), MX_GG.toString(), MX_VV.toString(), PROCED_GERAL.toString(), PROCED_GG.toString(), PROCED_VV.toString()), InformacoesDepartamentosDonos.getDepartamentosIndiretosEstruturaAntiga(US));		
	}

	public void testGetDepartamentosIndiretosAposNovaEstrutura() {
		Vencimento vencimentoAnteriorNovaEstrutura = new Vencimento();
		vencimentoAnteriorNovaEstrutura.setVenNome("2014/10");
		assertEquals(Arrays.asList(US_GERAL.toString(), US.toString()), InformacoesDepartamentosDonos.getDepartamentosIndiretosNovaEstrutura(US_GG));
		assertEquals(Arrays.asList(US_GERAL.toString(), US.toString()), InformacoesDepartamentosDonos.getDepartamentosIndiretosNovaEstrutura(US_VV));
		assertEquals(Arrays.asList(US_GG.toString(), US_VV.toString(), US.toString()), InformacoesDepartamentosDonos.getDepartamentosIndiretosNovaEstrutura(US_GERAL));
		assertEquals(Arrays.asList(MX_GERAL.toString()), InformacoesDepartamentosDonos.getDepartamentosIndiretosNovaEstrutura(MX_GG));
		assertEquals(Arrays.asList(MX_GERAL.toString()), InformacoesDepartamentosDonos.getDepartamentosIndiretosNovaEstrutura(MX_VV));
		assertEquals(Arrays.asList(MX_GG.toString(), MX_VV.toString()), InformacoesDepartamentosDonos.getDepartamentosIndiretosNovaEstrutura(MX_GERAL));
		assertEquals(Arrays.asList(PROCED_GERAL.toString(), US.toString()), InformacoesDepartamentosDonos.getDepartamentosIndiretosNovaEstrutura(PROCED_GG));
		assertEquals(Arrays.asList(PROCED_GERAL.toString(), US.toString()), InformacoesDepartamentosDonos.getDepartamentosIndiretosNovaEstrutura(PROCED_VV));
		assertEquals(Arrays.asList(PROCED_GG.toString(), PROCED_VV.toString(), US.toString()), InformacoesDepartamentosDonos.getDepartamentosIndiretosNovaEstrutura(PROCED_GERAL));
		assertEquals(Arrays.asList(US_GG.toString(), US_VV.toString(), US_GERAL.toString(), MX_GERAL.toString(), MX_GG.toString(), MX_VV.toString(), PROCED_GERAL.toString(), PROCED_GG.toString(), PROCED_VV.toString()), InformacoesDepartamentosDonos.getDepartamentosIndiretosNovaEstrutura(US));		
	}
}
