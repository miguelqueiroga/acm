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
@Table(name = "CONVENIO")

public class Convenio implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "CON_ID", nullable = false)
    private Integer conId;
    @Column(name = "CON_NOME", nullable = false)
    private String conNome;
    @Column(name = "CON_PAGO_A_VISTA", nullable = false)
    private String conPagoAVista;
    @JoinColumn(name = "CON_CONTA_PAGAMENTO", referencedColumnName = "CP_ID")
    @ManyToOne
    private ContaPagamento conContaPagamento;    
    @JoinColumn(name = "CON_SOCIO", referencedColumnName = "SOC_ID")
    @ManyToOne
    private Socio conSocio;    
    
    /**
	 * @return the conDepartamento
	 */
	public Socio getConSocio() {
		return conSocio;
	}

	/**
	 * @param conDepartamento the conDepartamento to set
	 */
	public void setConSocio(Socio conDepartamento) {
		this.conSocio = conDepartamento;
	}

	public Convenio() {
    
    }

    public Convenio(Integer usuId) {
        this.conId = usuId;
    }

    public Convenio(Integer usuId, String usuLogin, String usuSenha) {
        this.conId = usuId;
        this.conNome = usuLogin;
        this.conPagoAVista = usuSenha;
    }

    public Integer getConId() {
        return conId;
    }

    public void setConId(Integer usuId) {
        this.conId = usuId;
    }

    public String getConNome() {
        return conNome;
    }

    public void setConNome(String usuLogin) {
        this.conNome = usuLogin;
    }

    public String getConPagoAVista() {
        return conPagoAVista;
    }

    public void setConPagoAVista(String usuSenha) {
        this.conPagoAVista = usuSenha;
    }
    
    public ContaPagamento getConContaPagamento() {
        return conContaPagamento;
    }

    public void setConContaPagamento(ContaPagamento usuNivel) {
        this.conContaPagamento = usuNivel;
    }
}
