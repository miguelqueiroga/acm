package entity
{
	[RemoteClass(alias="ACM.model.entity.Resgate")]	
	[Bindable]
	public class Resgate
	{
		public var resId:Number;
		public var resValor:String;
		public var resData:Date;
		public var resDescricao:String;
		public var socio:Socio;
		public var vencimento:Vencimento;
		public function toString():String {
			return resDescricao;
		}
	}
}