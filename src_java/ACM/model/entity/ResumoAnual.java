package ACM.model.entity;

import java.math.BigDecimal;

import ACM.util.Tools;

public class ResumoAnual {
	
	private String ano;
	private String receitas;
	private String receitasAvulsas;
	private String despesas;
	private String aplicacoes;
	private String retiradas;
	private String dinheiroRecebidoEmConta;
	
	public String getAno() {
		return ano;
	}
	public void setANO(String ano) {
		this.ano = ano;
	}
	public String getReceitas() {
		return Tools.doubleToStringMonetario(Double.parseDouble(receitas));
	}
	public void setRECEITAS(BigDecimal receita) {
		if(receita != null && !receita.equals("0,00")){
			this.receitas = receita.toString();
		}
		else {
			this.receitas = "0.00";
		}
	}
	public String getDespesas() {
		return Tools.doubleToStringMonetario(Double.parseDouble(despesas));
	}
	public void setDESPESAS(BigDecimal despesas) {
		if(despesas != null && !despesas.equals("0,00")){
			this.despesas = despesas.toString();
		}
		else {
			this.despesas = "0.00";
		}			
	}
	public String getAplicacoes() {
		return Tools.doubleToStringMonetario(Double.parseDouble(aplicacoes));
	}
	public void setAPLICACOES(BigDecimal aplicacoes) {
		if(aplicacoes != null && !aplicacoes.equals("0,00")){
			this.aplicacoes = aplicacoes.toString();
		}
		else {
			this.aplicacoes = "0.00";
		}		
	}
	public String getRetiradas() {
		return Tools.doubleToStringMonetario(Double.parseDouble(retiradas));
	}
	public void setRETIRADAS(BigDecimal retiradas) {
		if(retiradas != null && !retiradas.equals("0,00")){
			this.retiradas = retiradas.toString();
		}
		else {
			this.retiradas = "0.00";
		}		
	}
	public String getDinheiroRecebidoEmConta() {
		return Tools.doubleToStringMonetario(Double.parseDouble(dinheiroRecebidoEmConta));
	}
	public void setDINHEIRORECEBIDOEMCONTA(BigDecimal dinheiroRecebidoEmConta) {
		if(dinheiroRecebidoEmConta != null){
			this.dinheiroRecebidoEmConta = dinheiroRecebidoEmConta.toString();
		}
		else {
			this.dinheiroRecebidoEmConta = "0.00";
		}		
	}
	public void setReceitasAvulsas(String receitasAvulsas) {
		this.receitasAvulsas = receitasAvulsas;
	}
	public String getReceitasAvulsas() {
		return receitasAvulsas;
	}
	public String getResultado() {
		return Tools.doubleToStringMonetario(Double.parseDouble(receitas) + Double.parseDouble(getReceitasAvulsas()) - Double.parseDouble(despesas));
	}
	public String getAReceber() {
		return Tools.doubleToStringMonetario(Double.parseDouble(receitas) + Double.parseDouble(getReceitasAvulsas()) - Double.parseDouble(despesas) - Double.parseDouble(dinheiroRecebidoEmConta));
	}
	public String getResiduo() {
		return Tools.doubleToStringMonetario(Double.parseDouble(receitas) + Double.parseDouble(getReceitasAvulsas()) - Double.parseDouble(despesas) - Double.parseDouble(dinheiroRecebidoEmConta) - Double.parseDouble(retiradas) - Double.parseDouble(aplicacoes));
	}
}