package entity
{
	[RemoteClass(alias="ACM.model.entity.CategoriaDespesa")]	
	[Bindable]
	public class CategoriaDespesa
	{
		public var cdId:Number;
		public var cdNome:String;
		public var isSelected:Boolean = false;
		public function toString():String {
			return cdNome;
		}
	}
}