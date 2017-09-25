package ACM.model.entity;

import java.io.Serializable;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "DEPARTAMENTO")

public class Departamento implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "DEP_ID", nullable = false, unique=true)
    private Integer depId;
    @Column(name = "DEP_NOME", nullable = false)
    private String depNome;
    @Column(name = "DEP_EH_ESPECIAL")
    private String depEhEspecial;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "ddDepartamentoDono", fetch=FetchType.EAGER)
    private Set<DepartamentoDepartamento> departamentosDependentes;    
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "ddDepartamento", fetch=FetchType.EAGER)
    private Set<DepartamentoDepartamento> departamentosDonos;    
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "dsDepartamento", fetch=FetchType.EAGER)
    private Set<DepartamentoSocio> sociosDonos;    
    
    /**
	 * @return the sociosDonos
	 */
	public Collection<DepartamentoSocio> getSociosDonos() {
		return sociosDonos;
	}

	/**
	 * @param sociosDonos the sociosDonos to set
	 */
	public void setSociosDonos(Collection<DepartamentoSocio> sociosDonos) {
		this.sociosDonos = new HashSet<DepartamentoSocio>(sociosDonos);
	}

	/**
	 * @return the departamentosDependentes
	 */
	public Collection<DepartamentoDepartamento> getDepartamentosDependentes() {
		return departamentosDependentes;
	}

	/**
	 * @param departamentosDependentes the departamentosDependentes to set
	 */
	public void setDepartamentosDependentes(
			Collection<DepartamentoDepartamento> departamentosDependentes) {
		this.departamentosDependentes = new HashSet<DepartamentoDepartamento>(departamentosDependentes);
	}

	/**
	 * @return the departamentosDonos
	 */
	public Collection<DepartamentoDepartamento> getDepartamentosDonos() {
		return departamentosDonos;
	}

	/**
	 * @param departamentosDonos the departamentosDonos to set
	 */
	public void setDepartamentosDonos(Collection<DepartamentoDepartamento> departamentosDonos) {
		this.departamentosDonos = new HashSet<DepartamentoDepartamento>(departamentosDonos);
	}

	public Departamento() {
    
    }

    public Departamento(Integer usuId) {
        this.depId = usuId;
    }

    public Departamento(Integer usuId, String usuLogin, String usuSenha) {
        this.depId = usuId;
        this.depNome = usuLogin;
    }

    public Integer getDepId() {
        return depId;
    }

    public void setDepId(Integer usuId) {
        this.depId = usuId;
    }

    public String getDepNome() {
        return depNome;
    }

    public void setDepNome(String usuLogin) {
        this.depNome = usuLogin;
    }
    
    public String getDepEhEspecial() {
		return depEhEspecial;
	}

	public void setDepEhEspecial(String depEhEspecial) {
		this.depEhEspecial = depEhEspecial;
	}	
}
