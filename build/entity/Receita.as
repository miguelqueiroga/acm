package entity
{
	[RemoteClass(alias="ACM.model.entity.Receita")]	
	[Bindable]
	public class Receita
	{
		public var recId:Number;
		public var recQuantidadeExames:String;
		public var recValorBruto:String;
		public var recValorDespesas:String;		
		public var recValorLiquido:String;
		public var recValorINSS:String;
		public var recValorIR:String;
		public var recValorTotalImpostos:String;
		public var recDepartamento:Departamento;
		public var recReceitaPorConvenio:ReceitaPorConvenio;	
			
	}
}