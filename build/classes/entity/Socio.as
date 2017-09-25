package entity
{
	import mx.collections.ArrayCollection;
	
	[RemoteClass(alias="ACM.model.entity.Socio")]	
	[Bindable]
	public class Socio
	{
		public var socId:Number;
		public var socNome:String;
		public function toString():String {
			return socNome;
		}
	}
}