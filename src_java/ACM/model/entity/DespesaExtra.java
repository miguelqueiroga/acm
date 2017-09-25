/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

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

/**
 *
 * @author CAMPINATEC2
 */
@Entity
@Table(name = "DESPESA_EXTRA")
public class DespesaExtra implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "DE_ID")
    private Integer deId;
    @Column(name = "DE_VALOR")
    private String deValor;
    @Column(name = "DE_DESCRICAO")
    private String deDescricao;
    @JoinColumn(name = "DE_DESPESA_POR_VENCIMENTO", referencedColumnName = "DPV_ID")
    @ManyToOne
    private DespesaPorVencimento despesaPorVencimento;

    public DespesaExtra() {
    }

    public DespesaExtra(Integer deId) {
        this.deId = deId;
    }

    public DespesaExtra(Integer deId, String deValor, String deDescricao) {
        this.deId = deId;
        this.deValor = deValor;
        this.deDescricao = deDescricao;
    }

    public Integer getDeId() {
        return deId;
    }

    public void setDeId(Integer deId) {
        this.deId = deId;
    }

    public String getDeValor() {
        return deValor;
    }

    public void setDeValor(String deValor) {
        this.deValor = deValor;
    }

    public String getDeDescricao() {
        return deDescricao;
    }

    public void setDeDescricao(String deDescricao) {
        this.deDescricao = deDescricao;
    }

    public DespesaPorVencimento getDespesaPorVencimento() {
        return despesaPorVencimento;
    }

    public void setDespesaPorVencimento(DespesaPorVencimento despesaPorVencimento) {
        this.despesaPorVencimento = despesaPorVencimento;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (deId != null ? deId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof DespesaExtra)) {
            return false;
        }
        DespesaExtra other = (DespesaExtra) object;
        if ((this.deId == null && other.deId != null) || (this.deId != null && !this.deId.equals(other.deId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "javaapplication3.DespesaExtra[deId=" + deId + "]";
    }

}
