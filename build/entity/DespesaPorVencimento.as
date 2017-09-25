package entity
{
	import mx.collections.ArrayCollection;
	
	[RemoteClass(alias="ACM.model.entity.DespesaPorVencimento")]	
	[Bindable]
	public class DespesaPorVencimento
	{
		public var dpvId:Number;
		public var dpvValor:String;
		public var dpvObservacao:String;
		public var dpvVencimento:Vencimento;
		public var dpvDespesa:Despesa;
		public var despesaExtraCollection:ArrayCollection = new ArrayCollection;
	}
}