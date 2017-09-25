package entity
{
	import mx.collections.ArrayCollection;
	
	[RemoteClass(alias="ACM.model.entity.DepartamentoSocio")]	
	[Bindable]
	public class DepartamentoSocio
	{
		public var dsId:Number;
		public var dsDepartamento:Departamento;
		public var dsSocio:Socio;
		public var dsPorcentagem:String;		
	}
}