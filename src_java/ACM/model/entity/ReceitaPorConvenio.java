package ACM.model.entity;

import java.io.Serializable;
import java.util.Collection;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;

import net.sf.jasperreports.engine.JRDataSource;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;

import ACM.util.Tools;

@Entity
@Table(name = "RECEITA_POR_CONVENIO")

public class ReceitaPorConvenio implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "RPC_ID", nullable = false)
    private Integer rpcId;
    @Column(name = "RPC_DATA_DEPOSITO", nullable = false)
    private String rpcDataDeposito;
    @Column(name = "RPC_QUANTIDADE_EXAMES_TOTAL", nullable = false)
    private String rpcQuantidadeExamesTotal;
    @Column(name = "RPC_ALIQUOTA_IR", nullable = false)
    private String rpcAliquotaIR;
    @Column(name = "RPC_DEDUCAO_IR", nullable = false)
    private String rpcDeducaoIR;
    @Column(name = "RPC_VALOR_EXAMES", nullable = false)
    private String rpcValorExames;
    @Column(name = "RPC_VALOR_MATERIAIS", nullable = false)
    private String rpcValorMateriais;
    @Column(name = "RPC_VALOR_MEDICAMENTOS", nullable = false)
    private String rpcValorMedicamentos;
    @Column(name = "RPC_VALOR_FILMES", nullable = false)
    private String rpcValorFilmes;
    @Column(name = "RPC_INSS_TOTAL", nullable = false)
    private String rpcINSSTotal;
    @Column(name = "RPC_IR_TOTAL", nullable = false)
    private String rpcIRTotal;
    @Column(name = "RPC_PAGAMENTO_PARTICULAR", nullable = false)
    private String rpcPagamentoParticular;
    @Column(name = "RPC_OUTRAS_DESPESAS", nullable = false)
    private String rpcOutrasDespesas;
    @Column(name = "RPC_VALOR_BRUTO_TOTAL", nullable = false)
    private String rpcValorBrutoTotal;
    @Column(name = "RPC_VALOR_DESPESAS_TOTAL", nullable = false)
    private String rpcValorDespesasTotal;
    @Column(name = "RPC_VALOR_CREDITADO", nullable = false)
    private String rpcValorCreditado;
    @JoinColumn(name = "RPC_CONVENIO", referencedColumnName = "CON_ID")
    @ManyToOne
    private Convenio rpcConvenio;    
    @JoinColumn(name = "RPC_VENCIMENTO", referencedColumnName = "VEN_ID")
    @ManyToOne
    private Vencimento rpcVencimento;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "recReceitaPorConvenio", fetch=FetchType.EAGER)
    private Collection<Receita> receitasCollection;    
    @Transient
    private JRBeanCollectionDataSource receitasCollectionJR;    
    /**
	 * @return the rpcAliquotaIR
	 */
	public String getRpcAliquotaIR() {
		return rpcAliquotaIR;
	}

	/**
	 * @param rpcAliquotaIR the rpcAliquotaIR to set
	 */
	public void setRpcAliquotaIR(String rpcAliquotaIR) {
		this.rpcAliquotaIR = rpcAliquotaIR;
	}

	/**
	 * @return the rpcDeducaoIR
	 */
	public String getRpcDeducaoIR() {
		return rpcDeducaoIR;
	}

	/**
	 * @param rpcDeducaoIR the rpcDeducaoIR to set
	 */
	public void setRpcDeducaoIR(String rpcDeducaoIR) {
		this.rpcDeducaoIR = rpcDeducaoIR;
	}

	/**
	 * @return the rpcValorExames
	 */
	public String getRpcValorExames() {
		return rpcValorExames;
	}

	/**
	 * @param rpcValorExames the rpcValorExames to set
	 */
	public void setRpcValorExames(String rpcValorExames) {
		this.rpcValorExames = rpcValorExames;
	}

	/**
	 * @return the rpcValorMateriais
	 */
	public String getRpcValorMateriais() {
		return rpcValorMateriais;
	}

	/**
	 * @param rpcValorMateriais the rpcValorMateriais to set
	 */
	public void setRpcValorMateriais(String rpcValorMateriais) {
		this.rpcValorMateriais = rpcValorMateriais;
	}

	/**
	 * @return the rpcValorMedicamentos
	 */
	public String getRpcValorMedicamentos() {
		return rpcValorMedicamentos;
	}

	/**
	 * @param rpcValorMedicamentos the rpcValorMedicamentos to set
	 */
	public void setRpcValorMedicamentos(String rpcValorMedicamentos) {
		this.rpcValorMedicamentos = rpcValorMedicamentos;
	}

	/**
	 * @return the rpcValorFilmes
	 */
	public String getRpcValorFilmes() {
		return rpcValorFilmes;
	}

	/**
	 * @param rpcValorFilmes the rpcValorFilmes to set
	 */
	public void setRpcValorFilmes(String rpcValorFilmes) {
		this.rpcValorFilmes = rpcValorFilmes;
	}

	/**
	 * @return the rpcValorDespesasTotal
	 */
	public String getRpcValorDespesasTotal() {
		return rpcValorDespesasTotal;
	}

	/**
	 * @param rpcValorDespesasTotal the rpcValorDespesasTotal to set
	 */
	public void setRpcValorDespesasTotal(String rpcValorDespesasTotal) {
		this.rpcValorDespesasTotal = rpcValorDespesasTotal;
	}

	/**
	 * @return the rpcValorCreditado
	 */
	public String getRpcValorCreditado() {
		return rpcValorCreditado;
	}

	/**
	 * @param rpcValorCreditado the rpcValorCreditado to set
	 */
	public void setRpcValorCreditado(String rpcValorCreditado) {
		this.rpcValorCreditado = rpcValorCreditado;
	}

	public Collection<Receita> getReceitasCollection() {
		return receitasCollection;
	}

	public JRDataSource getReceitasCollectionJR() {
		if(receitasCollectionJR == null)
			receitasCollectionJR = new JRBeanCollectionDataSource(receitasCollection);
		return receitasCollectionJR;
	}
	

	public void setReceitasCollection(Collection<Receita> receitasCollection) {
		this.receitasCollection = receitasCollection;
	}

	public ReceitaPorConvenio() {
    
    }

    public ReceitaPorConvenio(Integer usuId) {
        this.rpcId = usuId;
    }

    public Integer getRpcId() {
        return rpcId;
    }

    public void setRpcId(Integer usuId) {
        this.rpcId = usuId;
    }

    public String getRpcQuantidadeExamesTotal() {
        return rpcQuantidadeExamesTotal;
    }

    public void setRpcQuantidadeExamesTotal(String rpcNome) {
        this.rpcQuantidadeExamesTotal = rpcNome;
    }

    public String getRpcValorBrutoTotal() {
        return rpcValorBrutoTotal;
    }

    public void setRpcValorBrutoTotal(String valorBrutoTotal) {
        this.rpcValorBrutoTotal = valorBrutoTotal;
    }

    public String getRpcINSSTotal() {
        return rpcINSSTotal;
    }

    public void setRpcINSSTotal(String INSSTotal) {
        this.rpcINSSTotal = INSSTotal;
    }

    public String getRpcIRTotal() {
        return rpcIRTotal;
    }

    public void setRpcIRTotal(String IRTotal) {
        this.rpcIRTotal = IRTotal;
    }

    public String getRpcDataDeposito() {
        return rpcDataDeposito;
    }

    public void setRpcDataDeposito(String dataDeposito) {
        this.rpcDataDeposito = dataDeposito;
    }

    public String getRpcPagamentoParticular() {
        return rpcPagamentoParticular;
    }

    public void setRpcPagamentoParticular(String pagamentoParticular) {
        this.rpcPagamentoParticular = pagamentoParticular;
    }

    public String getRpcOutrasDespesas() {
        return rpcOutrasDespesas;
    }

    public void setRpcOutrasDespesas(String outrasDespesas) {
        this.rpcOutrasDespesas = outrasDespesas;
    }
    
    public Convenio getRpcConvenio() {
        return rpcConvenio;
    }

    public void setRpcConvenio(Convenio rpcConvenio) {
        this.rpcConvenio = rpcConvenio;
    }
    
    public Vencimento getRpcVencimento() {
        return rpcVencimento;
    }

    public void setRpcVencimento(Vencimento rpcVencimento) {
        this.rpcVencimento = rpcVencimento;
    }

    public String getINSSReceita(Receita receita) {
		return Tools.doubleToString(Tools.stringToDouble(getRpcINSSTotal()) * getProporcaoReceita(receita));
	}

    public String getIRReceita(Receita receita) {
    	return Tools.doubleToString(Tools.stringToDouble(getRpcIRTotal()) * getProporcaoReceita(receita));
	}

    public String getPagamentoParticularReceita(Receita receita) {
		return Tools.doubleToString(Tools.stringToDouble(getRpcPagamentoParticular()) * getProporcaoReceita(receita));
	}

    public String getOutrasDespesasReceita(Receita receita) {
    	return Tools.doubleToString(Tools.stringToDouble(getRpcOutrasDespesas()) * getProporcaoReceita(receita));
	}

    public String getImpostoTotalReceita(Receita receita) {
    	return Tools.doubleToString(getImpostoTotalReceitaDouble(receita));
	}

    public String getDespesasTotalReceita(Receita receita) {
    	return Tools.doubleToString(getDespesasTotalReceitaDouble(receita));
	}
    
    public double getINSSReceitaDouble(Receita receita) {
		return Tools.stringToDouble(getRpcINSSTotal()) * getProporcaoReceita(receita);
	}

    public double getIRReceitaDouble(Receita receita) {
    	return Tools.stringToDouble(getRpcIRTotal()) * getProporcaoReceita(receita);
	}

    public double getPagamentoParticularReceitaDouble(Receita receita) {
		return Tools.stringToDouble(getRpcPagamentoParticular()) * getProporcaoReceita(receita);
	}

    public double getOutrasDespesasReceitaDouble(Receita receita) {
    	return Tools.stringToDouble(getRpcOutrasDespesas()) * getProporcaoReceita(receita);
	}

    public double getImpostoTotalReceitaDouble(Receita receita) {
    	double inss = getINSSReceitaDouble(receita);
    	double ir = getIRReceitaDouble(receita);
    	return inss + ir;
	}

    public double getDespesasTotalReceitaDouble(Receita receita) {
    	double inss = getINSSReceitaDouble(receita);
    	double ir = getIRReceitaDouble(receita);
    	double outras = getOutrasDespesasReceitaDouble(receita);
    	return inss + ir + outras;
	}

	public String getValorLiquidoReceita(Receita receita) {
		double despesas = getDespesasTotalReceitaDouble(receita);
    	double bruto = Tools.stringToDouble(receita.getRecValorBruto());
    	return Tools.doubleToString(bruto - despesas);
	}
	
	public double getProporcaoReceita(Receita receita) {
		double result = Double.parseDouble(receita.getRecQuantidadeExames())/Double.parseDouble(getRpcQuantidadeExamesTotal());
		return result;	
	}

	public double getValorBrutoRestante() {
		double valorBruto = Tools.stringToDouble(getRpcValorBrutoTotal());
		double valorBrutoDeclarado = getValorBrutoReceitas();
		return valorBruto - valorBrutoDeclarado;
	}

	private double getValorBrutoReceitas() {
		double valorBrutoDeclarado = 0.0;
		for (Receita r : receitasCollection) {
			valorBrutoDeclarado += Tools.stringToDouble(r.getRecValorBruto());
		}
		return valorBrutoDeclarado;
	}

	public int getQuantidadeExamesRestante() {
		double quantidadeExames = Tools.stringToDouble(getRpcQuantidadeExamesTotal());
		double quantidadeExamesDeclarado = getQuantidadeExamesReceitas();
		return (int)(quantidadeExames - quantidadeExamesDeclarado);
	}

	private double getQuantidadeExamesReceitas() {
		double quantidadeExamesDeclarado = 0.0;
		for (Receita r : receitasCollection) {
			quantidadeExamesDeclarado += Tools.stringToDouble(r.getRecQuantidadeExames());
		}
		return quantidadeExamesDeclarado;
	}
}
