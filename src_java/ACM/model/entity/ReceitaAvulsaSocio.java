package ACM.model.entity;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
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

/**
 *
 * @author
 */
@Entity
@Table(name = "RECEITA_AVULSA_SOCIO")
public class ReceitaAvulsaSocio implements Serializable, Comparable<ReceitaAvulsaSocio> {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "RAS_ID")
    private Integer rasId;
    @JoinColumn(name = "RAS_RECEITA_AVULSA", referencedColumnName = "RA_ID")
    @ManyToOne
    private ReceitaAvulsa rasReceitaAvulsa;
    @JoinColumn(name = "RAS_SOCIO", referencedColumnName = "SOC_ID")
    @ManyToOne
    private Socio rasSocio;
    @Column(name = "RAS_PORCENTAGEM")
    private String rasPorcentagem;
    @Transient
    private PropertyChangeSupport changeSupport = new PropertyChangeSupport(this);
    @Transient
    private static final long serialVersionUID = 1L;
    
    public ReceitaAvulsaSocio() {
		this.rasId = null;
    }

    public ReceitaAvulsaSocio(Integer dsId) {
        this.rasId = dsId;
    }

    public ReceitaAvulsaSocio(Integer rasId, ReceitaAvulsa rasReceitaAvulsa, Socio rasSocio, String rasPorcentagem) {
        this.rasId = rasId;
        this.rasReceitaAvulsa = rasReceitaAvulsa;
        this.rasSocio = rasSocio;
        this.rasPorcentagem = rasPorcentagem;
    }

    public Integer getRasId() {
        return rasId;
    }

    public void setRasId(Integer dsId) {
        Integer oldDsId = this.rasId;
        this.rasId = dsId;
        changeSupport.firePropertyChange("dsId", oldDsId, dsId);
    }

    public ReceitaAvulsa getRasReceitaAvulsa() {
        return rasReceitaAvulsa;
    }

    public void setRasReceitaAvulsa(ReceitaAvulsa rasReceitaAvulsa) {
    	ReceitaAvulsa oldRasReceitaAvulsa = this.rasReceitaAvulsa;
        this.rasReceitaAvulsa = rasReceitaAvulsa;
        changeSupport.firePropertyChange("rasReceitaAvulsa", oldRasReceitaAvulsa, rasReceitaAvulsa);
    }

    public Socio getRasSocio() {
        return rasSocio;
    }

    public void setRasSocio(Socio dsSocio) {
    	Socio oldDsSocio = this.rasSocio;
        this.rasSocio = dsSocio;
        changeSupport.firePropertyChange("dsSocio", oldDsSocio, dsSocio);
    }

    public String getRasPorcentagem() {
        return rasPorcentagem;
    }

    public void setRasPorcentagem(String rasPorcentagem) {
    	this.rasPorcentagem = rasPorcentagem;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (rasId != null ? rasId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof ReceitaAvulsaSocio)) {
            return false;
        }
        ReceitaAvulsaSocio other = (ReceitaAvulsaSocio) object;
        if ((this.rasId == null && other.rasId != null) || (this.rasId != null && !this.rasId.equals(other.rasId))) {
            return false;
        }
        return true;
    }

    public String toString() {
        return "desktopapplication2.ReceitaAvulsaSocio[rasId=" + rasId + "]";
    }

    public void addPropertyChangeListener(PropertyChangeListener listener) {
        changeSupport.addPropertyChangeListener(listener);
    }

    public void removePropertyChangeListener(PropertyChangeListener listener) {
        changeSupport.removePropertyChangeListener(listener);
    }

	public int compareTo(ReceitaAvulsaSocio o) {
		return (getRasId() != null && o.getRasId()!= null) ? getRasId().compareTo(o.getRasId()) : 0;
	}

}
