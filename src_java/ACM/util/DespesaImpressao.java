package ACM.util;

import java.util.ArrayList;
import java.util.Collection;

import ACM.model.entity.Despesa;
import ACM.model.entity.DespesaPorVencimento;


public class DespesaImpressao {
	
	private Collection<DespesaPorVencimento> despesas;
	private Despesa despesa;
	
	public DespesaImpressao(Despesa despesa) {
		this.despesa = despesa;
		this.despesas = new ArrayList<DespesaPorVencimento>();
	}
	
	/**
	 * @return the despesas
	 */
	public Collection<DespesaPorVencimento> getDespesas() {
		return despesas;
	}

	/**
	 * @param despesas the despesas to set
	 */
	public void setDespesas(Collection<DespesaPorVencimento> despesas) {
		this.despesas = despesas;
	}

	/**
	 * @param despesas the despesas to set
	 */
	public void addDespesa(DespesaPorVencimento despesa) {
		this.despesas.add(despesa);
	}

	/**
	 * @return the despesa
	 */
	public Despesa getDespesa() {
		return despesa;
	}

	/**
	 * @return the despesa
	 */
	public String getDesNome() {
		return despesa.getDesNome();
	}

	/**
	 * @param despesa the despesa to set
	 */
	public void setDespesa(Despesa despesa) {
		this.despesa = despesa;
	}

	public String getJaneiro(){
		for(DespesaPorVencimento des : despesas){
			if(des.getDpvVencimento().getVenNome().startsWith("01"))
				return des.getDpvValor();
		}
		return "";
	}

	public String getFevereiro(){
		for(DespesaPorVencimento des : despesas){
			if(des.getDpvVencimento().getVenNome().startsWith("02"))
				return des.getDpvValor();
		}
		return "";
	}

	public String getMarco(){
		for(DespesaPorVencimento des : despesas){
			if(des.getDpvVencimento().getVenNome().startsWith("03"))
				return des.getDpvValor();
		}
		return "";
	}

	public String getAbril(){
		for(DespesaPorVencimento des : despesas){
			if(des.getDpvVencimento().getVenNome().startsWith("04"))
				return des.getDpvValor();
		}
		return "";
	}

	public String getMaio(){
		for(DespesaPorVencimento des : despesas){
			if(des.getDpvVencimento().getVenNome().startsWith("05"))
				return des.getDpvValor();
		}
		return "";
	}

	public String getJunho(){
		for(DespesaPorVencimento des : despesas){
			if(des.getDpvVencimento().getVenNome().startsWith("06"))
				return des.getDpvValor();
		}
		return "";
	}

	public String getJulho(){
		for(DespesaPorVencimento des : despesas){
			if(des.getDpvVencimento().getVenNome().startsWith("07"))
				return des.getDpvValor();
		}
		return "";
	}

	public String getAgosto(){
		for(DespesaPorVencimento des : despesas){
			if(des.getDpvVencimento().getVenNome().startsWith("08"))
				return des.getDpvValor();
		}
		return "";
	}

	public String getSetembro(){
		for(DespesaPorVencimento des : despesas){
			if(des.getDpvVencimento().getVenNome().startsWith("09"))
				return des.getDpvValor();
		}
		return "";
	}

	public String getOutubro(){
		for(DespesaPorVencimento des : despesas){
			if(des.getDpvVencimento().getVenNome().startsWith("10"))
				return des.getDpvValor();
		}
		return "";
	}

	public String getNovembro(){
		for(DespesaPorVencimento des : despesas){
			if(des.getDpvVencimento().getVenNome().startsWith("11"))
				return des.getDpvValor();
		}
		return "";
	}

	public String getDezembro(){
		for(DespesaPorVencimento des : despesas){
			if(des.getDpvVencimento().getVenNome().startsWith("12"))
				return des.getDpvValor();
		}
		return "";
	}

}
