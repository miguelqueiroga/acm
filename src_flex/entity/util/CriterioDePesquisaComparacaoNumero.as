package entity.util
{
	[RemoteClass(alias="ACM.model.repository.hibernate.CriterioDePesquisaComparacaoNumero")]	
	[Bindable]
	public class CriterioDePesquisaComparacaoNumero implements CriterioDePesquisa
	{
		public var criterio:String;
		public var valor:String;	
		
		public function getCriterio():String{
			return "o." + criterio
		}
		public function getValor():String {
			return valor
		}
		public function getOperador():String {
			return " = "
		}	
	}
}