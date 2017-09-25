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
@Table(name = "RETIRADA")
public class Retirada implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "RET_ID")
    private Integer retId;
    @Column(name = "RET_VALOR")
    private String retValor;
    @Column(name = "RET_DATA")
    @Temporal(TemporalType.DATE)
    private Date retData;
    @JoinColumn(name = "RET_VENCIMENTO", referencedColumnName = "VEN_ID")
    @ManyToOne
    private Vencimento vencimento;
    @JoinColumn(name = "RET_SOCIO", referencedColumnName = "SOC_ID")
    @ManyToOne
    private Socio socio;

    public Retirada() {
    }

    public Retirada(Integer retId) {
        this.retId = retId;
    }

    public Retirada(Integer retId, String retValor, Date retData) {
        this.retId = retId;
        this.retValor = retValor;
        this.retData = retData;
    }

    public Integer getRetId() {
        return retId;
    }

    public void setRetId(Integer retId) {
        this.retId = retId;
    }

    public String getRetValor() {
        return retValor;
    }

    public void setRetValor(String retValor) {
        this.retValor = retValor;
    }

    public Date getRetData() {
        return retData;
    }

    public void setRetData(Date retData) {
        this.retData = retData;
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
        hash += (retId != null ? retId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Retirada)) {
            return false;
        }
        Retirada other = (Retirada) object;
        if ((this.retId == null && other.retId != null) || (this.retId != null && !this.retId.equals(other.retId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "javaapplication4.Retirada[retId=" + retId + "]";
    }

}
