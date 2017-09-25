package entity
{
	[RemoteClass(alias="ACM.model.entity.Vencimento")]	
	[Bindable]
	public class Vencimento
	{
		public var venId:Number;
		public var venNome:String;
		public function toString():String {
			return venNome.slice(5,7) + "/" + venNome.slice(0,4);
		}
	}
}