package ACM.model.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

@Entity
@Table(name = "RECEITA")

public class Receita implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "REC_ID", nullable = false)
    private Integer recId;
    @Column(name = "REC_QUANTIDADE_EXAMES", nullable = false)
    private String recQuantidadeExames;
    @Column(name = "REC_VALOR_BRUTO", nullable = false)
    private String recValorBruto;
    @JoinColumn(name = "REC_RECEITA_POR_CONVENIO", referencedColumnName = "RPC_ID")
    @ManyToOne
    private ReceitaPorConvenio recReceitaPorConvenio;    
    @JoinColumn(name = "REC_DEPARTAMENTO", referencedColumnName = "DEP_ID")
    @ManyToOne
    private Departamento recDepartamento;
    @Transient 
    private String recValorINSS;
    @Transient 
    private String recValorIR;
    @Transient 
    private String recValorTotalImpostos;
    @Transient 
    private String recValorDespesas;
    @Transient 
    private String recValorLiquido;
    @Transient
    private String porcentagemDono;
    @Transient
    private String valorRateado;
    @Transient
    private Boolean direta;
    @Transient
    private Socio socio;
    
    public String getPorcentagemDono() {
		return porcentagemDono;
	}

	public void setPorcentagemDono(String porcentagemDono) {
		this.porcentagemDono = porcentagemDono;
	}

	public String getValorRateado() {
		return valorRateado;
	}

	public void setValorRateado(String valorRateado) {
		this.valorRateado = valorRateado;
	}

	public Receita() {
    	getRecValorINSS();    	
    	getRecValorIR();
    	getRecValorTotalImpostos();
    	getRecValorDespesas();
    	getRecValorLiquido();
    }

    public Receita(Integer usuId) {
        this.recId = usuId;
    }

    /**
	 * @return the recValorINSS
	 */
	public String getRecValorINSS() {
		if(recValorINSS == null){ 
			if(this.recReceitaPorConvenio != null)
				recValorINSS = this.recReceitaPorConvenio.getINSSReceita(this);
		}
		return recValorINSS;
	}

	/**
	 * @return the recValorIR
	 */
	public String getRecValorIR() {
		if(recValorIR == null)
			if(this.recReceitaPorConvenio != null)
				recValorIR = this.recReceitaPorConvenio.getIRReceita(this);
    	return recValorIR;
	}

	/**
	 * @return the recValorLiquido
	 */
	public String getRecValorLiquido() {
		if(recValorLiquido == null)
			if(this.recReceitaPorConvenio != null)
				recValorLiquido = this.recReceitaPorConvenio.getValorLiquidoReceita(this);
    	return recValorLiquido;
	}
	
    /**
	 * @return the recValorTotalImpostos
	 */
	public String getRecValorTotalImpostos() {
		if(recValorTotalImpostos == null)
			if(this.recReceitaPorConvenio != null)
				recValorTotalImpostos = this.recReceitaPorConvenio.getImpostoTotalReceita(this);
    	return recValorTotalImpostos;
	}

	/**
	 * @param recValorTotalImpostos the recValorTotalImpostos to set
	 */
	public void setRecValorTotalImpostos(String recValorTotalImpostos) {
		this.recValorTotalImpostos = recValorTotalImpostos;
	}

	/**
	 * @return the recValorDespesas
	 */
	public String getRecValorDespesas() {
		if(recValorDespesas == null)
			if(this.recReceitaPorConvenio != null)
				recValorDespesas = this.recReceitaPorConvenio.getDespesasTotalReceita(this);
    	return recValorDespesas;
	}

	/**
	 * @param recValorDespesas the recValorDespesas to set
	 */
	public void setRecValorDespesas(String recValorDespesas) {
		this.recValorDespesas = recValorDespesas;
	}

	/**
	 * @param recValorLiquido the recValorLiquido to set
	 */
	public void setRecValorLiquido(String recValorLiquido) {
		this.recValorLiquido = recValorLiquido;
	}

	/**
	 * @param recValorINSS the recValorINSS to set
	 */
	public void setRecValorINSS(String recValorINSS) {
		this.recValorINSS = recValorINSS;
	}

	/**
	 * @param recValorIR the recValorIR to set
	 */
	public void setRecValorIR(String recValorIR) {
		this.recValorIR = recValorIR;
	}
    
    public Integer getRecId() {
    	return recId;
    }

    public void setRecId(Integer usuId) {
        this.recId = usuId;
    }

    public String getRecQuantidadeExames() {
        return recQuantidadeExames;
    }

    public void setRecQuantidadeExames(String recNome) {
        this.recQuantidadeExames = recNome;
    }

    public String getRecValorBruto() {
        return recValorBruto;
    }

    public void setRecValorBruto(String recValor) {
        this.recValorBruto = recValor;
    }
    
    public ReceitaPorConvenio getRecReceitaPorConvenio() {
        return recReceitaPorConvenio;
    }

    public void setRecReceitaPorConvenio(ReceitaPorConvenio recReceitaPorConvenio) {
        this.recReceitaPorConvenio = recReceitaPorConvenio;        
    }
    
    public Departamento getRecDepartamento() {
        return recDepartamento;
    }

    public void setRecDepartamento(Departamento recDepartamento) {
        this.recDepartamento = recDepartamento;
    }

	public void setDireta(Boolean direta) {
		this.direta = direta;
	}

	public Boolean getDireta() {
		return direta;
	}
	
	public String getConvenio() { 
		return recReceitaPorConvenio.getRpcConvenio().getConNome();
	}
	
	public String getSocio() { 
		if(socio == null){
			return recReceitaPorConvenio.getRpcConvenio().getConSocio().getSocNome();
		}
		return socio.getSocNome();
	}
	
	public String getVencimento() { 
		return recReceitaPorConvenio.getRpcVencimento().getVenNomeFormatado();
	}

	public void setSocia(Socio dsSocio) {
		this.socio = dsSocio;
	}
}
