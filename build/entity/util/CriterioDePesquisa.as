package entity.util
{
	[RemoteClass(alias="ACM.model.repository.hibernate.CriterioDePesquisa")]	
	[Bindable]
	public interface CriterioDePesquisa
	{
		function getCriterio():String;
		function getOperador():String;
		function getValor():String;	
	}
}