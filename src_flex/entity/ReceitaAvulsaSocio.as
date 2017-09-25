package entity
{
	import mx.collections.ArrayCollection;
	
	[RemoteClass(alias="ACM.model.entity.ReceitaAvulsaSocio")]	
	[Bindable]
	public class ReceitaAvulsaSocio
	{
		public var rasId:Number;
		public var rasReceitaAvulsa:ReceitaAvulsa;
		public var rasSocio:Socio;
		public var rasPorcentagem:String;		
	}
}