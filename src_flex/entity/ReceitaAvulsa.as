package entity
{
	import mx.collections.ArrayCollection;
	
	[RemoteClass(alias="ACM.model.entity.ReceitaAvulsa")]	
	[Bindable]
	public class ReceitaAvulsa
	{
		public var raId:Number;
		public var raNome:String;
		public var raValor:String;
		public var raReceitasAvulsasSocio:ArrayCollection;
		public var raVencimento:Vencimento;
			
			
	}
}