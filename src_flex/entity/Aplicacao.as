package entity
{
	[RemoteClass(alias="ACM.model.entity.Aplicacao")]	
	[Bindable]
	public class Aplicacao
	{
		public var aplId:Number;
		public var aplValor:String;
		public var aplData:Date;
		public var aplDescricao:String;
		public var socio:Socio;
		public var vencimento:Vencimento;
		public function toString():String {
			return aplDescricao;
		}
	}
}