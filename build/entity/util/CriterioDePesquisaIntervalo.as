package entity.util
{
	[RemoteClass(alias="ACM.model.repository.hibernate.CriterioDePesquisaIntervalo")]	
	[Bindable]
	public class CriterioDePesquisaIntervalo implements CriterioDePesquisa
	{
		public var criterio:String;
		public var valor:String;	
		public var valorInicial:Object;
    	public var valorFinal:Object;        
	
		
		public function getCriterio():String{
			return "(o." + criterio
		}
		public function getValor():String {
			return "'" + valorInicial + "' AND '" + valorFinal + "')"
		}
		public function getOperador():String {
			return " BETWEEN "
		}	
	}
}