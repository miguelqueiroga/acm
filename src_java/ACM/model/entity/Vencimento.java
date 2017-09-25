package ACM.model.entity;

import java.io.Serializable;
import java.text.DateFormatSymbols;
import java.text.ParseException;
import java.text.SimpleDateFormat;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "VENCIMENTO")

public class Vencimento implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "VEN_ID", nullable = false)
    private Integer venId;
    @Column(name = "VEN_NOME", nullable = false)
    private String venNome;
    
    public Vencimento() {
    	
    }

    public Vencimento(Integer usuId) {
        this.venId = usuId;
    }

    public Vencimento(Integer usuId, String usuLogin) {
        this.venId = usuId;
        this.venNome = usuLogin;
    }

    public Integer getVenId() {
        return venId;
    }

    public void setVenId(Integer usuId) {
        this.venId = usuId;
    }

    public String getVenNome() {
    	return venNome.substring(0, 4) + "/" + venNome.substring(5, 7);
    }

    public String getVenNomeFormatado() {
    	SimpleDateFormat dateOrigem = new SimpleDateFormat("yyyy/MM");
		SimpleDateFormat dateFormatada = new SimpleDateFormat("MMM/yyyy", new DateFormatSymbols(new java.util.Locale ("pt", "BR")));
		String venNomeFormatado = getVenNome();
		try {
			venNomeFormatado = dateFormatada.format(dateOrigem.parse(venNome));
		} catch (ParseException e) { e.printStackTrace();}
		return venNomeFormatado;
    }

    public void setVenNome(String venNome) {
        this.venNome = venNome;
    }
}
