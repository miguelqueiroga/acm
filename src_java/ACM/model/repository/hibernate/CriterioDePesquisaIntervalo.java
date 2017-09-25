package ACM.model.repository.hibernate;


public class CriterioDePesquisaIntervalo implements CriterioDePesquisa{
	
	private String criterio;
	private Object valorInicial;
    private Object valorFinal;        
	
    public CriterioDePesquisaIntervalo() {
		
	}
	
	public CriterioDePesquisaIntervalo(String criterio, Object valorInicial, Object valorFinal){
		setCriterio(criterio);
		setValorInicial(valorInicial);
                setValorFinal(valorFinal);                
	}

	public String getCriterio() {
		return "(o." + criterio;
	}

	public void setCriterio(String criterio) {
		this.criterio = criterio;
	}

	public Object getValorInicial() {
		return valorInicial;
	}

	public void setValorInicial(Object valorInicial) {
		this.valorInicial = valorInicial;
	}
        
        public Object getValorFinal() {
		return valorFinal;
	}

	public void setValorFinal(Object valorFinal) {
		this.valorFinal = valorFinal;
	}
        
    public Object getValor() {
        return "'" + valorInicial + "' AND '" + valorFinal + "')";
	}

    public String getOperador() {
        return " BETWEEN ";
    }

}
