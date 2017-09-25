package ACM.util;

public class FinancasSocioImpressao {
	
	private String nome;
	private String vencimento;
	private String valor;
	
	public FinancasSocioImpressao(String nome, String valor, String mes) {
		this.nome = nome;
		this.vencimento = mes;
		this.valor = valor;		
	}
	
	public FinancasSocioImpressao(){
		
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getVencimento() {
		return vencimento;
	}

	public void setVencimento(String mes) {
		this.vencimento = mes;
	}

	public String getValor() {
		return valor;
	}

	public void setValor(String valor) {
		this.valor = valor;
	}
	


}
