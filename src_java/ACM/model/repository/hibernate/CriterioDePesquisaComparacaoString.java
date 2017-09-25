package ACM.model.repository.hibernate;

public class CriterioDePesquisaComparacaoString implements CriterioDePesquisa {
	
	private String criterio;
	private Object valor;
	
	public CriterioDePesquisaComparacaoString(){
		
	}
	
	public CriterioDePesquisaComparacaoString(String criterio, Object valor){
		setCriterio(criterio);
		setValor(valor);
	}

	public String getCriterio() {
		return "o." + criterio;
	}

	public void setCriterio(String criterio) {
		this.criterio = criterio;
	}

	public Object getValor() {
		return valor;
	}

	public void setValor(Object valor) {
		this.valor = "'" + valor + "'";
	}

    public String getOperador() {
        return " LIKE ";
    }

}
