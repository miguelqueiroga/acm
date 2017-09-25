package entity


{
	import mx.collections.ArrayCollection;
	[RemoteClass(alias="ACM.model.entity.ReceitaPorConvenio")]	
	[Bindable]
	public class ReceitaPorConvenio
	{
		public var rpcId:Number;
		public var rpcDataDeposito:String;
		public var rpcQuantidadeExamesTotal:String;
		public var rpcAliquotaIR:String;
		public var rpcDeducaoIR:String;
		public var rpcValorExames:String;
		public var rpcValorMedicamentos:String;
		public var rpcValorFilmes:String;
		public var rpcValorMateriais:String;
		public var rpcINSSTotal:String;
		public var rpcIRTotal:String;
		public var rpcPagamentoParticular:String;
		public var rpcOutrasDespesas:String;
		public var rpcValorBrutoTotal:String;
		public var rpcValorDespesasTotal:String;
		public var rpcValorCreditado:String;
		public var rpcVencimento:Vencimento;
		public var rpcConvenio:Convenio;
		public var receitasCollection:ArrayCollection;
	}
}