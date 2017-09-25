package entity
{
	[RemoteClass(alias="ACM.model.entity.Retirada")]	
	[Bindable]
	public class Retirada
	{
		public var retId:Number;
		public var retValor:String;
		public var retData:Date;
		public var socio:Socio;
		public var vencimento:Vencimento;
	}
}