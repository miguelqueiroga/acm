package ACM.model.entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;

@Entity
@Table(name = "DESPESA_POR_VENCIMENTO")

public class DespesaPorVencimento implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "DPV_ID", nullable = false)
    private Integer dpvId;
    @Column(name = "DPV_VALOR", nullable = false)
    private String dpvValor;
    @Column(name = "DPV_OBSERVACAO", nullable = true)
    private String dpvObservacao;
    @JoinColumn(name = "DPV_VENCIMENTO", referencedColumnName = "VEN_ID")
    @ManyToOne
    private Vencimento dpvVencimento;    
    @JoinColumn(name = "DPV_DESPESA", referencedColumnName = "DES_ID")
    @ManyToOne
    private Despesa dpvDespesa;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "despesaPorVencimento", fetch = FetchType.EAGER)
    private Collection<DespesaExtra> despesaExtraCollection;
    @Transient
    private String porcentagemDono;
    @Transient
    private String valorRateado;
    @Transient
    private Boolean direta;
    
    
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

	public DespesaPorVencimento() {
		despesaExtraCollection = new ArrayList<DespesaExtra>();
    }

    public DespesaPorVencimento(Integer usuId) {
    	despesaExtraCollection = new ArrayList<DespesaExtra>();
        this.dpvId = usuId;
    }

    public Integer getDpvId() {
        return dpvId;
    }

    public void setDpvId(Integer usuId) {
        this.dpvId = usuId;
    }

    public String getDpvValor() {
        return dpvValor;
    }

    public void setDpvValor(String desValor) {
        this.dpvValor = desValor;
    }
    
    public Vencimento getDpvVencimento() {
        return dpvVencimento;
    }

    public void setDpvVencimento(Vencimento desVencimento) {
        this.dpvVencimento = desVencimento;
    }
    
    /**
	 * @return the dpvDespesa
	 */
	public Despesa getDpvDespesa() {
		return dpvDespesa;
	}

	/**
	 * @param dpvDespesa the dpvDespesa to set
	 */
	public void setDpvDespesa(Despesa dpvDespesa) {
		this.dpvDespesa = dpvDespesa;
	}

	public String getDpvObservacao() {
		return dpvObservacao;
	}

	public void setDpvObservacao(String dpvObservacao) {
		this.dpvObservacao = dpvObservacao;
	}

	public void setDireta(Boolean direta) {
		this.direta = direta;
	}

	public Boolean getDireta() {
		return direta;
	}

	public void setDespesaExtraCollection(Collection<DespesaExtra> despesaExtraCollection) {
		this.despesaExtraCollection = despesaExtraCollection;
	}

	public Collection<DespesaExtra> getDespesaExtraCollection() {
		return despesaExtraCollection;
	}	
}
