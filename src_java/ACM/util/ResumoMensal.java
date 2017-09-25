package ACM.util;

import java.util.ArrayList;
import java.util.Collection;

import ACM.model.entity.DespesaPorVencimento;
import ACM.model.entity.Receita;

public class ResumoMensal {
	
	private String nome;
	private String departamento;
	private String dataInicial;
	private String dataFinal;
	private String valorReceita;
	private String valorDespesa;
	private String valorReceitaDireta;
	private String valorDespesaDireta;
	private Collection<DespesaPorVencimento> despesas;
	private Collection<Receita> receitas;
	private String valorReceitaSemValorAVista;
	private String valorReceitaDiretaSemValorAVista;
	
	public ResumoMensal(String nome, String departamento, String dataInicial, String dataFinal, String valorReceita, String valorDespesa) {
		this.nome = nome;
		this.departamento = departamento;
		this.dataInicial = dataInicial;
		this.dataFinal = dataFinal;
		this.valorReceita = valorReceita;
		this.valorDespesa = valorDespesa;
		this.receitas = new ArrayList<Receita>();
		this.despesas = new ArrayList<DespesaPorVencimento>();
	}
	
	public String getDepartamento() {
		return departamento;
	}

	public void setDepartamento(String departamento) {
		this.departamento = departamento;
	}

	public String getValorReceita() {
		return valorReceita;
	}

	public void setValorReceita(String valorReceita) {
		this.valorReceita = valorReceita;
	}

	public String getValorDespesa() {
		return valorDespesa;
	}

	public void setValorDespesa(String valorDespesa) {
		this.valorDespesa = valorDespesa;
	}

	public String getValorReceitaDireta() {
		return valorReceitaDireta;
	}

	public void setValorReceitaDireta(String valorReceita) {
		this.valorReceitaDireta = valorReceita;
	}

	public void setValorReceitaSemValorAVista(String somaReceitaSemValorAVista) {
		this.valorReceitaSemValorAVista = somaReceitaSemValorAVista;
	}
	
	public String getValorReceitaSemValorAVista() {
		return valorReceitaSemValorAVista;
	}

	public void setValorReceitaDiretaSemValorAVista(String somaReceitaSemValorAVista) {
		this.valorReceitaDiretaSemValorAVista = somaReceitaSemValorAVista;
	}
	
	public String getValorReceitaDiretaSemValorAVista() {
		return valorReceitaDiretaSemValorAVista;
	}
	
	public String getValorDespesaDireta() {
		return valorDespesaDireta;
	}

	public void setValorDespesaDireta(String valorDespesa) {
		this.valorDespesaDireta = valorDespesa;
	}

	public ResumoMensal(){
		
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getDataInicial() {
		return dataInicial;
	}

	public void setDataInicial(String dataInicial) {
		this.dataInicial = dataInicial;
	}

	public String getDataFinal() {
		return dataFinal;
	}

	public void setDataFinal(String dataFinal) {
		this.dataFinal = dataFinal;
	}

	/**
	 * @return the receitas
	 */
	public Collection<Receita> getReceitas() {
		return receitas;
	}

	/**
	 * @param receitas the receitas to set
	 */
	public void setReceitas(Collection<Receita> receitas) {
		this.receitas = receitas;
	}

	/**
	 * @param receitas the receitas to set
	 */
	public void addReceita(Receita receita) {
		this.receitas.add(receita);
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

	public void setValorReceitas(Collection<Receita> receitas2) {
		double soma = 0;
		double somaReceitaSemValorAVista = 0;
		for(Receita rec: receitas2){
			if(rec.getRecReceitaPorConvenio().getRpcConvenio().getConPagoAVista().equalsIgnoreCase("SIM")) {
				soma += Tools.stringToDouble(rec.getValorRateado());
			}
			else {
				somaReceitaSemValorAVista += Tools.stringToDouble(rec.getValorRateado());
			}
		}
		setValorReceita(Tools.doubleToString(soma));
		setValorReceitaSemValorAVista(Tools.doubleToString(somaReceitaSemValorAVista));
		setValorReceitasDiretas(receitas2);
	}

	public void setValorDespesas(Collection<DespesaPorVencimento> despesas2) {
		double soma = 0;
		for(DespesaPorVencimento des: despesas2){
			soma += Tools.stringToDouble(des.getValorRateado());
		}
		setValorDespesa(Tools.doubleToString(soma));
		setValorDespesasDiretas(despesas2);
	}

	public void setValorReceitasDiretas(Collection<Receita> receitas2) {
		double soma = 0;
		double somaReceitaDiretaSemValorAVista = 0;
		for(Receita rec: receitas2){
			if(rec.getDireta()){
				if(rec.getRecReceitaPorConvenio().getRpcConvenio().getConPagoAVista().equalsIgnoreCase("SIM")) {
					soma += Tools.stringToDouble(rec.getValorRateado());
				}
				else {
					somaReceitaDiretaSemValorAVista += Tools.stringToDouble(rec.getValorRateado());
				}
			}
		}
		setValorReceitaDireta(Tools.doubleToString(soma));
		setValorReceitaDiretaSemValorAVista(Tools.doubleToString(somaReceitaDiretaSemValorAVista));
	}

	public void setValorDespesasDiretas(Collection<DespesaPorVencimento> despesas2) {
		double soma = 0;
		for(DespesaPorVencimento des: despesas2){
			if(des.getDireta()){
				soma += Tools.stringToDouble(des.getValorRateado());
			}
		}
		setValorDespesaDireta(Tools.doubleToString(soma));
	}
}
