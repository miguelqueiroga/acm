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
@Table(name = "RESGATE")
public class Resgate implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "RES_ID")
    private Integer resId;
    @Column(name = "RES_VALOR")
    private String resValor;
    @Column(name = "RES_DATA")
    @Temporal(TemporalType.DATE)
    private Date resData;
    @Column(name = "RES_DESCRICAO")
    private String resDescricao;
    @JoinColumn(name = "RES_VENCIMENTO", referencedColumnName = "VEN_ID")
    @ManyToOne
    private Vencimento vencimento;
    @JoinColumn(name = "RES_SOCIO", referencedColumnName = "SOC_ID")
    @ManyToOne
    private Socio socio;

    public Resgate() {
    }

    public Resgate(Integer resId) {
        this.resId = resId;
    }

    public Resgate(Integer resId, String resValor, Date resData, String resDescricao) {
        this.resId = resId;
        this.resValor = resValor;
        this.resData = resData;
        this.resDescricao = resDescricao;
    }

    public Integer getResId() {
        return resId;
    }

    public void setResId(Integer resId) {
        this.resId = resId;
    }

    public String getResValor() {
        return resValor;
    }

    public void setResValor(String resValor) {
        this.resValor = resValor;
    }

    public Date getResData() {
        return resData;
    }

    public void setResData(Date resData) {
        this.resData = resData;
    }

    public String getResDescricao() {
        return resDescricao;
    }

    public void setResDescricao(String resDescricao) {
        this.resDescricao = resDescricao;
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
        hash += (resId != null ? resId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Resgate)) {
            return false;
        }
        Resgate other = (Resgate) object;
        if ((this.resId == null && other.resId != null) || (this.resId != null && !this.resId.equals(other.resId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "javaapplication4.Resgate[resId=" + resId + "]";
    }

}
