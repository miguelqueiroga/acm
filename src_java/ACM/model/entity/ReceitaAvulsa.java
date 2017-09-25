package ACM.model.entity;

import java.io.Serializable;
import java.util.Set;

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

import ACM.util.Tools;

@Entity
@Table(name = "RECEITA_AVULSA")

public class ReceitaAvulsa implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "RA_ID", nullable = false)
    private Integer raId;
    @Column(name = "RA_VALOR", nullable = false)
    private String raValor;
    @Column(name = "RA_NOME", nullable = false)
    private String raNome;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "rasReceitaAvulsa", fetch=FetchType.EAGER)
    private Set<ReceitaAvulsaSocio> raReceitasAvulsasSocio;    
    @JoinColumn(name = "RA_VENCIMENTO", referencedColumnName = "VEN_ID")
    @ManyToOne
    private Vencimento raVencimento;
    @Transient
    private String valorRateado;
    
    public String getValorRateado() {
		return valorRateado;
	}

	public void setValorRateado(String valorRateado) {
		this.valorRateado = valorRateado;
	}

	public ReceitaAvulsa() {
    	this.raId = null;
    }

    public ReceitaAvulsa(Integer raId) {
        this.raId = raId;
    }

    public Integer getRaId() {
    	return raId;
    }

    public void setRaId(Integer raId) {
        this.raId = raId;
    }

    public String getRaValor() {
        return raValor;
    }

    public void setRaValor(String raValor) {
        this.raValor = raValor;
    }
    
    public Vencimento getRaVencimento() {
        return raVencimento;
    }

    public void setRaVencimento(Vencimento raVencimento) {
        this.raVencimento = raVencimento;
    }

	public void setRaNome(String raNome) {
		this.raNome = raNome;
	}

	public String getRaNome() {
		return raNome;
	}

	public void setRaReceitasAvulsasSocio(Set<ReceitaAvulsaSocio> raReceitasAvulsasSocio) {
		this.raReceitasAvulsasSocio = raReceitasAvulsasSocio;
	}

	public Set<ReceitaAvulsaSocio> getRaReceitasAvulsasSocio() {
		return raReceitasAvulsasSocio;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((raId == null) ? 0 : raId.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		ReceitaAvulsa other = (ReceitaAvulsa) obj;
		if (raId == null) {
			if (other.raId != null)
				return false;
		} else if (!raId.equals(other.raId))
			return false;
		return true;
	}
	
}