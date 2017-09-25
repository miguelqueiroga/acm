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
@Table(name = "CONTA_PAGAMENTO")

public class ContaPagamento implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "CP_ID", nullable = false)
    private Integer cpId;
    @Column(name = "CP_NOME", nullable = false)
    private String cpNome;
    @Column(name = "CP_BANCO", nullable = false)
    private String cpBanco;
    @Column(name = "CP_AGENCIA", nullable = false)
    private String cpAgencia;    
    @Column(name = "CP_CONTA", nullable = false)
    private String cpConta;
    @JoinColumn(name = "CP_SOCIO", referencedColumnName = "SOC_ID")
    @ManyToOne
    private Socio cpSocio;    
    
    
    public ContaPagamento() {
    	    
    }

    public ContaPagamento(Integer usuId) {
        this.cpId = usuId;
    }

    public ContaPagamento(Integer usuId, String usuLogin, String usuSenha) {
        this.cpId = usuId;
        this.cpNome = usuLogin;
        this.cpBanco = usuSenha;
    }

    public Integer getCpId() {
        return cpId;
    }

    public void setCpId(Integer usuId) {
        this.cpId = usuId;
    }

    public String getCpNome() {
        return cpNome;
    }

    public void setCpNome(String usuLogin) {
        this.cpNome = usuLogin;
    }

    public String getCpBanco() {
        return cpBanco;
    }

    public void setCpBanco(String usuSenha) {
        this.cpBanco = usuSenha;
    }
    
    public String getCpAgencia() {
        return cpAgencia;
    }

    public void setCpAgencia(String usuNivel) {
        this.cpAgencia = usuNivel;
    }
    
    public String getCpConta() {
        return cpConta;
    }

    public void setCpConta(String usuNivel) {
        this.cpConta = usuNivel;
    }

	public void setCpSocio(Socio cpSocio) {
		this.cpSocio = cpSocio;
	}

	public Socio getCpSocio() {
		return cpSocio;
	}
}
