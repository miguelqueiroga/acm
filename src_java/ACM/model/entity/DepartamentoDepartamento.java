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
@Table(name = "DEPARTAMENTO_DEPARTAMENTO")
public class DepartamentoDepartamento implements Serializable, Comparable<DepartamentoDepartamento> {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "DD_ID")
    private Integer ddId;
    @JoinColumn(name = "DD_DEPARTAMENTO", referencedColumnName = "DEP_ID")
    @ManyToOne
    private Departamento ddDepartamento;
    @JoinColumn(name = "DD_DEPARTAMENTO_DONO", referencedColumnName = "DEP_ID")
    @ManyToOne
    private Departamento ddDepartamentoDono;
    @Column(name = "DD_PORCENTAGEM")
    private String ddPorcentagem;
    @Transient
    private PropertyChangeSupport changeSupport = new PropertyChangeSupport(this);
    @Transient
    private static final long serialVersionUID = 1L;
    
    public DepartamentoDepartamento() {
    	this.ddId = null;
    }

    public DepartamentoDepartamento(Integer ddId) {
        this.ddId = ddId;
    }

    public DepartamentoDepartamento(Integer ddId, Departamento ddDepartamento, Departamento ddDepartamentoDono, String dsPorcentagem) {
        this.ddId = ddId;
        this.ddDepartamento = ddDepartamento;
        this.ddDepartamentoDono = ddDepartamentoDono;
        this.ddPorcentagem = dsPorcentagem;
    }

    public Integer getDdId() {
        return ddId;
    }

    public void setDdId(Integer ddId) {
        Integer oldDdId = this.ddId;
        this.ddId = ddId;
        changeSupport.firePropertyChange("ddId", oldDdId, ddId);
    }

    public Departamento getDdDepartamento() {
        return ddDepartamento;
    }

    public void setDdDepartamento(Departamento ddDepartamento) {
    	Departamento oldDdDepartamento = this.ddDepartamento;
        this.ddDepartamento = ddDepartamento;
        changeSupport.firePropertyChange("ddDepartamento", oldDdDepartamento, ddDepartamento);
    }

    public Departamento getDdDepartamentoDono() {
        return ddDepartamentoDono;
    }

    public void setDdDepartamentoDono(Departamento ddDepartamentoDono) {
    	Departamento oldDdDepartamentoDono = this.ddDepartamentoDono;
        this.ddDepartamentoDono = ddDepartamentoDono;
        changeSupport.firePropertyChange("ddDepartamentoDono", oldDdDepartamentoDono, ddDepartamentoDono);
    }

    public String getDdPorcentagem() {
        return ddPorcentagem;
    }

    public void setDdPorcentagem(String dsPorcentagem) {
        String oldDsPorcentagem = this.ddPorcentagem;
        this.ddPorcentagem = dsPorcentagem;
        changeSupport.firePropertyChange("ddPorcentagem", oldDsPorcentagem, dsPorcentagem);
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (ddId != null ? ddId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof DepartamentoDepartamento)) {
            return false;
        }
        DepartamentoDepartamento other = (DepartamentoDepartamento) object;
        if ((this.ddId == null && other.ddId != null) || (this.ddId != null && !this.ddId.equals(other.ddId))) {
            return false;
        }
        return true;
    }

    public String toString() {
        return "desktopapplication1.DepartamentoDepartamento[ddId=" + ddId + "]";
    }

    public void addPropertyChangeListener(PropertyChangeListener listener) {
        changeSupport.addPropertyChangeListener(listener);
    }

    public void removePropertyChangeListener(PropertyChangeListener listener) {
        changeSupport.removePropertyChangeListener(listener);
    }

	public int compareTo(DepartamentoDepartamento o) {
		return (getDdId() != null && o.getDdId()!= null) ? getDdId().compareTo(o.getDdId()) : 0;
	}

}
