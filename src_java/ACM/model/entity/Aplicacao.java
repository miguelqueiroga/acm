/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package ACM.model.entity;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 *
 * @author CAMPINATEC2
 */
@Entity
@Table(name = "APLICACAO")
public class Aplicacao implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "APL_ID")
    private Integer aplId;
    @Column(name = "APL_VALOR")
    private String aplValor;
    @Column(name = "APL_DATA")
    @Temporal(TemporalType.DATE)
    private Date aplData;
    @Column(name = "APL_DESCRICAO")
    private String aplDescricao;
    @JoinColumn(name = "APL_VENCIMENTO", referencedColumnName = "VEN_ID")
    @ManyToOne
    private Vencimento vencimento;
    @JoinColumn(name = "APL_SOCIO", referencedColumnName = "SOC_ID")
    @ManyToOne
    private Socio socio;

    public Aplicacao() {
    }

    public Aplicacao(Integer aplId) {
        this.aplId = aplId;
    }

    public Aplicacao(Integer aplId, String aplValor, Date aplData, String aplDescricao) {
        this.aplId = aplId;
        this.aplValor = aplValor;
        this.aplData = aplData;
        this.aplDescricao = aplDescricao;
    }

    public Integer getAplId() {
        return aplId;
    }

    public void setAplId(Integer aplId) {
        this.aplId = aplId;
    }

    public String getAplValor() {
        return aplValor;
    }

    public void setAplValor(String aplValor) {
        this.aplValor = aplValor;
    }

    public Date getAplData() {
        return aplData;
    }

    public void setAplData(Date aplData) {
        this.aplData = aplData;
    }

    public String getAplDescricao() {
        return aplDescricao;
    }

    public void setAplDescricao(String aplDescricao) {
        this.aplDescricao = aplDescricao;
    }

    public Vencimento getVencimento() {
        return vencimento;
    }

    public void setVencimento(Vencimento vencimento) {
        this.vencimento = vencimento;
    }

    public Socio getSocio() {
        return socio;
    }

    public void setSocio(Socio socio) {
        this.socio = socio;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (aplId != null ? aplId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Aplicacao)) {
            return false;
        }
        Aplicacao other = (Aplicacao) object;
        if ((this.aplId == null && other.aplId != null) || (this.aplId != null && !this.aplId.equals(other.aplId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "javaapplication4.Aplicacao[aplId=" + aplId + "]";
    }

}
