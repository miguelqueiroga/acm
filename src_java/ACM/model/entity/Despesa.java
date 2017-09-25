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

@Entity
@Table(name = "DESPESA")

public class Despesa implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "DES_ID", nullable = false)
    private Integer desId;
    @Column(name = "DES_NOME", nullable = false)
    private String desNome;
    @Column(name = "DES_ESTA_ATIVA")
    private String desEstaAtiva = "S";
    @Column(name = "DES_EH_EXTRA")
    private String desEhExtra;
    @JoinColumn(name = "DES_CATEGORIA", referencedColumnName = "CD_ID")
    @ManyToOne
    private CategoriaDespesa desCategoria;    
    @JoinColumn(name = "DES_DEPARTAMENTO", referencedColumnName = "DEP_ID")
    @ManyToOne
    private Departamento desDepartamento;    
    
    /**
	 * @return the desEstaAtiva
	 */
	public String getDesEstaAtiva() {
		return desEstaAtiva;
	}

	/**
	 * @param desEstaAtiva the desEstaAtiva to set
	 */
	public void setDesEstaAtiva(String desEstaAtiva) {
		this.desEstaAtiva = desEstaAtiva;
	}

	
    public Despesa() {
    
    }

    public Despesa(Integer desId) {
        this.desId = desId;
    }

    public Integer getDesId() {
        return desId;
    }

    public void setDesId(Integer desId) {
        this.desId = desId;
    }

    public String getDesNome() {
        return desNome;
    }

    public void setDesNome(String desNome) {
        this.desNome = desNome;
    }
    
    public CategoriaDespesa getDesCategoria() {
        return desCategoria;
    }

    public void setDesCategoria(CategoriaDespesa desCategoria) {
        this.desCategoria = desCategoria;
    }
    
    public Departamento getDesDepartamento() {
        return desDepartamento;
    }

    public void setDesDepartamento(Departamento desDepartamento) {
        this.desDepartamento = desDepartamento;
    }

	public void setDesEhExtra(String desEhExtra) {
		this.desEhExtra = desEhExtra;
	}

	public String getDesEhExtra() {
		return desEhExtra;
	}
}
