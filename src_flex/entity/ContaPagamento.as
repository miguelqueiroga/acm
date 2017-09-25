package entity
{
	[RemoteClass(alias="ACM.model.entity.ContaPagamento")]	
	[Bindable]
	public class ContaPagamento
	{
		public var cpId:Number;
		public var cpNome:String;
		public var cpBanco:String;
		public var cpAgencia:String;
		public var cpConta:String;
		public var cpSocio:Socio;
		public function toString():String {
			return cpNome + ": " + cpBanco;
		}
	}
}