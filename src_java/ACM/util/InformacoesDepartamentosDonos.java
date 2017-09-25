package ACM.util;

import java.util.ArrayList;

import ACM.model.entity.Vencimento;

public class InformacoesDepartamentosDonos {

	public static final String DATA_TRANSICAO = "2014/10";
	public static final Integer US_GG = 1;
	public static final Integer US_VV = 2;
	public static final Integer US_GERAL = 3;
	public static final Integer MX_GERAL = 4;
	public static final Integer MX_GG = 5;
	public static final Integer MX_VV = 6;
	public static final Integer PROCED_GERAL = 7;
	public static final Integer US = 9;
	public static final Integer PROCED_GG = 10;
	public static final Integer PROCED_VV = 11;
	
	public static String getPorcentagemGeral(Integer departamentoDonoId,Integer departamentoInd, Vencimento vencimento) {
		if(vencimento.getVenNome().compareTo(DATA_TRANSICAO) >= 0) {
			return getPorcentagemNovaEstrutura(departamentoDonoId, departamentoInd);
		}
		else {
			return getPorcentagem(departamentoDonoId, departamentoInd);
		}
	}
	
	private static String getPorcentagem(Integer departamentoDonoId,Integer departamentoInd) {
		if(departamentoDonoId.equals(US_GG) || departamentoDonoId.equals(US_VV)){ // US GG) || US VV
			if(departamentoInd.equals(US_GERAL)) {
				return "50,00";
			}
			if(departamentoInd.equals(US)) {
				return "33,00";
			}
		}
		if(departamentoDonoId.equals(US_GERAL)){ // US Geral
			if(departamentoInd.equals(US_GG) || departamentoInd.equals(US_VV)) {
				return "100,00";
			}
			if(departamentoInd.equals(US)) {
				return "66,00";
			}
		}
		if(departamentoDonoId.equals(MX_GERAL)){ // Mx Geral
			if(departamentoInd.equals(MX_GG) || departamentoInd.equals(MX_VV)) {
				return "100,00";
			}
			if(departamentoInd.equals(US)) {
				return "32,00";
			}
		}
		if(departamentoDonoId.equals(MX_GG) || departamentoDonoId.equals(MX_VV)){ // Mx GG) || Mx VV
			if(departamentoInd.equals(MX_GERAL)) {
				return "50,00";
			}
			if(departamentoInd.equals(US)) {
				return "16,00";
			}
		}
		if(departamentoDonoId.equals(PROCED_GERAL)){ // Proced Geral
			if(departamentoInd.equals(PROCED_GG) || departamentoInd.equals(PROCED_VV)) {
				return "100,00";
			}
			if(departamentoInd.equals(US)) {
				return "2,00";
			}
		}
		if(departamentoDonoId.equals(PROCED_GG) || departamentoDonoId.equals(PROCED_VV)){ // Proced GG) || Proced VV
			if(departamentoInd.equals(PROCED_GERAL)) {
				return "50,00";
			}
			if(departamentoInd.equals(US)) {
				return "1,00";
			}
		}
		if(departamentoDonoId.equals(US)){ // US
			return "100,00";
		}
		return null;
	}

	private static String getPorcentagemNovaEstrutura(Integer departamentoDonoId,Integer departamentoInd) {
		if(departamentoDonoId.equals(US_GG) || departamentoDonoId.equals(US_VV)){ // US GG) || US VV
			if(departamentoInd.equals(US_GERAL)) {
				return "50,00";
			}
			if(departamentoInd.equals(US)) {
				return "49,00";
			}
		}
		if(departamentoDonoId.equals(US_GERAL)){ // US Geral
			if(departamentoInd.equals(US_GG) || departamentoInd.equals(US_VV)) {
				return "100,00";
			}
			if(departamentoInd.equals(US)) {
				return "98,00";
			}
		}
		if(departamentoDonoId.equals(MX_GERAL)){ // Mx Geral
			if(departamentoInd.equals(MX_GG) || departamentoInd.equals(MX_VV)) {
				return "100,00";
			}
			if(departamentoInd.equals(US)) {
				return "0,00";
			}
		}
		if(departamentoDonoId.equals(MX_GG) || departamentoDonoId.equals(MX_VV)){ // Mx GG) || Mx VV
			if(departamentoInd.equals(MX_GERAL)) {
				return "50,00";
			}
			if(departamentoInd.equals(US)) {
				return "0,00";
			}
		}
		if(departamentoDonoId.equals(PROCED_GERAL)){ // Proced Geral
			if(departamentoInd.equals(PROCED_GG) || departamentoInd.equals(PROCED_VV)) {
				return "100,00";
			}
			if(departamentoInd.equals(US)) {
				return "2,00";
			}
		}
		if(departamentoDonoId.equals(PROCED_GG) || departamentoDonoId.equals(PROCED_VV)){ // Proced GG) || Proced VV
			if(departamentoInd.equals(PROCED_GERAL)) {
				return "50,00";
			}
			if(departamentoInd.equals(US)) {
				return "1,00";
			}
		}
		if(departamentoDonoId.equals(US)){ // US
			return "100,00";
		}
		return null;
	}
	
	public static ArrayList<String> getDepartamentosIndiretosEstruturaAntiga(Integer depId){ 
		ArrayList<String> departamentoIndiretos = new ArrayList<String>();
		if(depId.equals(US_GG) || depId.equals(US_VV)){ // US GG) || US VV
			departamentoIndiretos.add(US_GERAL.toString());
			departamentoIndiretos.add(US.toString());
		}
		if(depId.equals(US_GERAL)){ // US Geral
			departamentoIndiretos.add(US_GG.toString());
			departamentoIndiretos.add(US_VV.toString());
			departamentoIndiretos.add(US.toString());
		}
		if(depId.equals(MX_GERAL)){ // Mx Geral
			departamentoIndiretos.add(MX_GG.toString());
			departamentoIndiretos.add(MX_VV.toString());
			departamentoIndiretos.add(US.toString());
		}
		if(depId.equals(MX_GG) || depId.equals(MX_VV)){ // Mx GG) || Mx VV
			departamentoIndiretos.add(MX_GERAL.toString());
			departamentoIndiretos.add(US.toString());
		}
		if(depId.equals(PROCED_GERAL)){ // Proced Total
			departamentoIndiretos.add(PROCED_GG.toString());
			departamentoIndiretos.add(PROCED_VV.toString());
			departamentoIndiretos.add(US.toString());
		}
		if(depId.equals(PROCED_GG) || depId.equals(PROCED_VV)){ // Proced GG) || Proced VV
			departamentoIndiretos.add(PROCED_GERAL.toString());
			departamentoIndiretos.add(US.toString());
		}
		if(depId.equals(US)){ // US
			departamentoIndiretos.add(US_GG.toString());
			departamentoIndiretos.add(US_VV.toString());
			departamentoIndiretos.add(US_GERAL.toString());
			departamentoIndiretos.add(MX_GERAL.toString());
			departamentoIndiretos.add(MX_GG.toString());
			departamentoIndiretos.add(MX_VV.toString());
			departamentoIndiretos.add(PROCED_GERAL.toString());
			departamentoIndiretos.add(PROCED_GG.toString());
			departamentoIndiretos.add(PROCED_VV.toString());
		}
		return departamentoIndiretos;
	}

	public static ArrayList<String> getDepartamentosIndiretosNovaEstrutura(Integer depId){ 
		ArrayList<String> departamentoIndiretos = new ArrayList<String>();
		if(depId.equals(US_GG) || depId.equals(US_VV)){ // US GG) || US VV
			departamentoIndiretos.add(US_GERAL.toString());
			departamentoIndiretos.add(US.toString());
		}
		if(depId.equals(US_GERAL)){ // US Geral
			departamentoIndiretos.add(US_GG.toString());
			departamentoIndiretos.add(US_VV.toString());
			departamentoIndiretos.add(US.toString());
		}
		if(depId.equals(MX_GERAL)){ // Mx Geral
			departamentoIndiretos.add(MX_GG.toString());
			departamentoIndiretos.add(MX_VV.toString());
		}
		if(depId.equals(MX_GG) || depId.equals(MX_VV)){ // Mx GG) || Mx VV
			departamentoIndiretos.add(MX_GERAL.toString());
		}
		if(depId.equals(PROCED_GERAL)){ // Proced Total
			departamentoIndiretos.add(PROCED_GG.toString());
			departamentoIndiretos.add(PROCED_VV.toString());
			departamentoIndiretos.add(US.toString());
		}
		if(depId.equals(PROCED_GG) || depId.equals(PROCED_VV)){ // Proced GG) || Proced VV
			departamentoIndiretos.add(PROCED_GERAL.toString());
			departamentoIndiretos.add(US.toString());
		}
		if(depId.equals(US)){ // US
			departamentoIndiretos.add(US_GG.toString());
			departamentoIndiretos.add(US_VV.toString());
			departamentoIndiretos.add(US_GERAL.toString());
			departamentoIndiretos.add(MX_GERAL.toString());
			departamentoIndiretos.add(MX_GG.toString());
			departamentoIndiretos.add(MX_VV.toString());
			departamentoIndiretos.add(PROCED_GERAL.toString());
			departamentoIndiretos.add(PROCED_GG.toString());
			departamentoIndiretos.add(PROCED_VV.toString());
		}
		return departamentoIndiretos;
	}

	
	public static void main(String[] args) {
		System.out.println(DATA_TRANSICAO.compareTo("2014/09"));
		System.out.println(DATA_TRANSICAO.compareTo(DATA_TRANSICAO));
		System.out.println(DATA_TRANSICAO.compareTo("2014/11"));
		System.out.println("2013/10".compareTo("2014/09"));
		System.out.println("2015/10".compareTo("2014/09"));
		System.out.println("2013/10".compareTo("2014/11"));
		System.out.println("2015/10".compareTo("2014/11"));
	}
}
