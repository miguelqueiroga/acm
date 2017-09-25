package entity.util
{
	[RemoteClass(alias="ACM.model.repository.hibernate.CriterioDePesquisaComparacaoString")]	
	[Bindable]
	public class CriterioDePesquisaComparacaoString implements CriterioDePesquisa
	{
		public var criterio:String;
		public var valor:String;	
		
		public function getCriterio():String{
			return "o." + criterio;
		}
		public function getValor():String {
			return "'" + valor + "'";
		}
		public function getOperador():String {
			return " LIKE ";
		}	
	}
}