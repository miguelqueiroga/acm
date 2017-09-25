package ACM.model.entity;

import java.io.Serializable;
import java.util.Collection;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "SOCIO")

public class Socio implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "SOC_ID", nullable = false, unique=true)
    private Integer socId;
    @Column(name = "SOC_NOME", nullable = false)
    private String socNome;
    
    /**
	 * @return the departamentosDependentes
	 */
    
    /*public Collection<DepartamentoSocio> getDepartamentosDependentes() {
		return departamentosDependentes;
	}*/

	/**
	 * @param departamentosDependentes the departamentosDependentes to set
	 */
	/*public void setDepartamentosDependentes(
			Collection<DepartamentoSocio> departamentosDependentes) {
		this.departamentosDependentes = new HashSet<DepartamentoSocio>(departamentosDependentes);
	}*/

	public Socio() {
    
    }

    public Socio(Integer usuId) {
        this.socId = usuId;
    }

    public Socio(Integer usuId, String usuLogin, String usuSenha) {
        this.socId = usuId;
        this.socNome = usuLogin;
    }

    public Integer getSocId() {
        return socId;
    }

    public void setSocId(Integer usuId) {
        this.socId = usuId;
    }

    public String getSocNome() {
        return socNome;
    }

    public void setSocNome(String usuLogin) {
        this.socNome = usuLogin;
    }

	public boolean isDono(Departamento dep) {
		Collection<DepartamentoSocio> dss = dep.getSociosDonos();
		for(DepartamentoSocio ds : dss){
			if(ds.getDsSocio().socId.equals(socId))
				return true;
		}
		return false;
	}
}
