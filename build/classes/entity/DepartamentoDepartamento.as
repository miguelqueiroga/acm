package entity
{
	import mx.collections.ArrayCollection;
	
	[RemoteClass(alias="ACM.model.entity.DepartamentoDepartamento")]	
	[Bindable]
	public class DepartamentoDepartamento
	{
		public var ddId:Number;
		public var ddDepartamento:Departamento;
		public var ddDepartamentoDono:Departamento;
		public var ddPorcentagem:String;		
	}
}