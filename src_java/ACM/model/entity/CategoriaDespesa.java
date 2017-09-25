package ACM.model.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "CATEGORIA_DESPESA")

public class CategoriaDespesa implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "CD_ID", nullable = false)
    private Integer cdId;
    @Column(name = "CD_NOME", nullable = false)
    private String cdNome;
    
    public CategoriaDespesa() {
    
    }

    public CategoriaDespesa(Integer usuId) {
        this.cdId = usuId;
    }

    public CategoriaDespesa(Integer cdId, String cdNome) {
        this.cdId = cdId;
        this.cdNome = cdNome;
    }

    public Integer getCdId() {
        return cdId;
    }

    public void setCdId(Integer usuId) {
        this.cdId = usuId;
    }

    public String getCdNome() {
        return cdNome;
    }

    public void setCdNome(String cdNome) {
        this.cdNome = cdNome;
    }
}
