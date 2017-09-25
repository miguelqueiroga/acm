package ACM.util;

import java.util.ArrayList;
import java.util.Collection;

import ACM.model.entity.Convenio;
import ACM.model.entity.ReceitaPorConvenio;


public class ReceitaImpressao {
	
	private Collection<ReceitaPorConvenio> receitas;
	private Convenio convenio;
	
	public ReceitaImpressao(Convenio convenio) {
		this.convenio = convenio;
		this.receitas = new ArrayList<ReceitaPorConvenio>();
	}
	
	/**
	 * @return the receitas
	 */
	public Collection<ReceitaPorConvenio> getReceitas() {
		return receitas;
	}

	/**
	 * @param receitas the receitas to set
	 */
	public void setReceitas(Collection<ReceitaPorConvenio> receitas) {
		this.receitas = receitas;
	}

	/**
	 * @param receitas the receitas to set
	 */
	public void addReceita(ReceitaPorConvenio receita) {
		this.receitas.add(receita);
	}

	/**
	 * @return the convenio
	 */
	public Convenio getConvenio() {
		return convenio;
	}

	/**
	 * @return the convenio name
	 */
	public String getConNome() {
		return convenio.getConNome();
	}

	/**
	 * @param receita the convenio to set
	 */
	public void setConvenio(Convenio convenio) {
		this.convenio = convenio;
	}

	public String getJaneiro(){
		for(ReceitaPorConvenio des : receitas){
			if(des.getRpcVencimento().getVenNome().startsWith("01"))
				return des.getRpcValorBrutoTotal();
		}
		return "";
	}

	public String getFevereiro(){
		for(ReceitaPorConvenio des : receitas){
			if(des.getRpcVencimento().getVenNome().startsWith("02"))
				return des.getRpcValorBrutoTotal();
		}
		return "";
	}

	public String getMarco(){
		for(ReceitaPorConvenio des : receitas){
			if(des.getRpcVencimento().getVenNome().startsWith("03"))
				return des.getRpcValorBrutoTotal();
		}
		return "";
	}

	public String getAbril(){
		for(ReceitaPorConvenio des : receitas){
			if(des.getRpcVencimento().getVenNome().startsWith("04"))
				return des.getRpcValorBrutoTotal();
		}
		return "";
	}

	public String getMaio(){
		for(ReceitaPorConvenio des : receitas){
			if(des.getRpcVencimento().getVenNome().startsWith("05"))
				return des.getRpcValorBrutoTotal();
		}
		return "";
	}

	public String getJunho(){
		for(ReceitaPorConvenio des : receitas){
			if(des.getRpcVencimento().getVenNome().startsWith("06"))
				return des.getRpcValorBrutoTotal();
		}
		return "";
	}

	public String getJulho(){
		for(ReceitaPorConvenio des : receitas){
			if(des.getRpcVencimento().getVenNome().startsWith("07"))
				return des.getRpcValorBrutoTotal();
		}
		return "";
	}

	public String getAgosto(){
		for(ReceitaPorConvenio des : receitas){
			if(des.getRpcVencimento().getVenNome().startsWith("08"))
				return des.getRpcValorBrutoTotal();
		}
		return "";
	}

	public String getSetembro(){
		for(ReceitaPorConvenio des : receitas){
			if(des.getRpcVencimento().getVenNome().startsWith("09"))
				return des.getRpcValorBrutoTotal();
		}
		return "";
	}

	public String getOutubro(){
		for(ReceitaPorConvenio des : receitas){
			if(des.getRpcVencimento().getVenNome().startsWith("10"))
				return des.getRpcValorBrutoTotal();
		}
		return "";
	}

	public String getNovembro(){
		for(ReceitaPorConvenio des : receitas){
			if(des.getRpcVencimento().getVenNome().startsWith("11"))
				return des.getRpcValorBrutoTotal();
		}
		return "";
	}

	public String getDezembro(){
		for(ReceitaPorConvenio des : receitas){
			if(des.getRpcVencimento().getVenNome().startsWith("12"))
				return des.getRpcValorBrutoTotal();
		}
		return "";
	}

}
