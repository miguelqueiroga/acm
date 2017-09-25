package entity
{
	[RemoteClass(alias="ACM.model.entity.Despesa")]	
	[Bindable]
	public class Despesa
	{
		public var desId:Number;
		public var desNome:String;
		public var desDepartamento:Departamento;
		public var desCategoria:CategoriaDespesa;
		public var desEhExtra:String;
		public function toString():String {
			return desNome;
		}
	}
}