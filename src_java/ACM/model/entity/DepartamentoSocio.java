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
@Table(name = "DEPARTAMENTO_SOCIO")
public class DepartamentoSocio implements Serializable, Comparable<DepartamentoSocio> {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "DS_ID")
    private Integer dsId;
    @JoinColumn(name = "DS_DEPARTAMENTO", referencedColumnName = "DEP_ID")
    @ManyToOne
    private Departamento dsDepartamento;
    @JoinColumn(name = "DS_SOCIO", referencedColumnName = "SOC_ID")
    @ManyToOne
    private Socio dsSocio;
    @Column(name = "DS_PORCENTAGEM")
    private String dsPorcentagem;
    @Transient
    private PropertyChangeSupport changeSupport = new PropertyChangeSupport(this);
    @Transient
    private static final long serialVersionUID = 1L;
    
    public DepartamentoSocio() {
    	this.dsId = null;
    }

    public DepartamentoSocio(Integer dsId) {
        this.dsId = dsId;
    }

    public DepartamentoSocio(Integer dsId, Departamento dsDepartamento, Socio dsSocio, String dsPorcentagem) {
        this.dsId = dsId;
        this.dsDepartamento = dsDepartamento;
        this.dsSocio = dsSocio;
        this.dsPorcentagem = dsPorcentagem;
    }

    public Integer getDsId() {
        return dsId;
    }

    public void setDsId(Integer dsId) {
        Integer oldDsId = this.dsId;
        this.dsId = dsId;
        changeSupport.firePropertyChange("dsId", oldDsId, dsId);
    }

    public Departamento getDsDepartamento() {
        return dsDepartamento;
    }

    public void setDsDepartamento(Departamento dsDepartamento) {
    	Departamento oldDsDepartamento = this.dsDepartamento;
        this.dsDepartamento = dsDepartamento;
        changeSupport.firePropertyChange("dsDepartamento", oldDsDepartamento, dsDepartamento);
    }

    public Socio getDsSocio() {
        return dsSocio;
    }

    public void setDsSocio(Socio dsSocio) {
    	Socio oldDsSocio = this.dsSocio;
        this.dsSocio = dsSocio;
        changeSupport.firePropertyChange("dsSocio", oldDsSocio, dsSocio);
    }

    public String getDsPorcentagem() {
        return dsPorcentagem;
    }

    public void setDsPorcentagem(String dsPorcentagem) {
        String oldDsPorcentagem = this.dsPorcentagem;
        this.dsPorcentagem = dsPorcentagem;
        changeSupport.firePropertyChange("dsPorcentagem", oldDsPorcentagem, dsPorcentagem);
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (dsId != null ? dsId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof DepartamentoSocio)) {
            return false;
        }
        DepartamentoSocio other = (DepartamentoSocio) object;
        if ((this.dsId == null && other.dsId != null) || (this.dsId != null && !this.dsId.equals(other.dsId))) {
            return false;
        }
        return true;
    }

    public String toString() {
        return "desktopapplication2.DepartamentoSocio[dsId=" + dsId + "]";
    }

    public void addPropertyChangeListener(PropertyChangeListener listener) {
        changeSupport.addPropertyChangeListener(listener);
    }

    public void removePropertyChangeListener(PropertyChangeListener listener) {
        changeSupport.removePropertyChangeListener(listener);
    }

	public int compareTo(DepartamentoSocio o) {
		return (getDsId() != null && o.getDsId()!= null) ? getDsId().compareTo(o.getDsId()) : 0;
	}

}
