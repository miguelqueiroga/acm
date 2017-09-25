package entity
{
	import mx.collections.ArrayCollection;
	
	[RemoteClass(alias="ACM.model.entity.Departamento")]	
	[Bindable]
	public class Departamento
	{
		public var depId:Number;
		public var depNome:String;
		public var depEhEspecial:String;
		public var sociosDonos:ArrayCollection;
		public var departamentosDependentes:ArrayCollection;
		public var departamentosDonos:ArrayCollection;
		public var isSelected:Boolean = false;
		public function toString():String {
			return depNome;
		}
	}
}