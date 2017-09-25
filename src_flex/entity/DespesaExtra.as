package entity
{
	import mx.collections.ArrayCollection;
	
	[RemoteClass(alias="ACM.model.entity.DespesaExtra")]	
	[Bindable]
	public class DespesaExtra
	{
		public var deId:Number;
		public var deValor:String;
		public var deDescricao:String;
		public var despesaPorVencimento:DespesaPorVencimento;
	}
}