package ACM.model.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "USUARIO")

public class Usuario implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "USU_ID", nullable = false)
    private Integer usuId;
    @Column(name = "USU_LOGIN", nullable = false)
    private String usuLogin;
    @Column(name = "USU_SENHA", nullable = false)
    private String usuSenha;
    @Column(name = "USU_NIVEL", nullable = false)
    private String usuNivel;    
    
    public Usuario() {
    
    }

    public Usuario(Integer usuId) {
        this.usuId = usuId;
    }

    public Usuario(Integer usuId, String usuLogin, String usuSenha) {
        this.usuId = usuId;
        this.usuLogin = usuLogin;
        this.usuSenha = usuSenha;
    }

    public Integer getUsuId() {
        return usuId;
    }

    public void setUsuId(Integer usuId) {
        this.usuId = usuId;
    }

    public String getUsuLogin() {
        return usuLogin;
    }

    public void setUsuLogin(String usuLogin) {
        this.usuLogin = usuLogin;
    }

    public String getUsuSenha() {
        return usuSenha;
    }

    public void setUsuSenha(String usuSenha) {
        this.usuSenha = usuSenha;
    }
    
    public String getUsuNivel() {
        return usuNivel;
    }

    public void setUsuNivel(String usuNivel) {
        this.usuNivel = usuNivel;
    }
}
