package entity
{
	[RemoteClass(alias="ACM.model.entity.Convenio")]	
	[Bindable]
	public class Convenio
	{
		public var conId:Number;
		public var conNome:String;
		public var conPagoAVista:String;
		public var conContaPagamento:ContaPagamento;
		public var conSocio:Socio;
		public var isSelected:Boolean = false;
		public function toString():String {
			return conNome;
		}
	}
}