package entity.util
{
	import mx.collections.ArrayCollection;
	
	[RemoteClass(alias="ACM.model.repository.hibernate.CriterioDePesquisaOR")]	
	[Bindable]
	public class CriterioDePesquisaOR implements CriterioDePesquisa
	{
		public var criterio:String = "(";
		public var valor:String = ")";	
		public var criterios:ArrayCollection;
		
		public function getCriterio():String{
			return criterio
		}
		public function getValor():String {
			return valor
		}
		public function getOperador():String {
			var saida:String = "";
	    	var cp:CriterioDePesquisa = null;
            var i:int;
            for (i = 0; i < criterios.length; i++)
            {
            	cp = criterios.getItemAt(i) as CriterioDePesquisa;
	    		saida += cp.getCriterio() + cp.getOperador() + cp.getValor();
	    		if((i + 1) < criterios.length)
	    			 saida += " OR ";    		
	    	}
	        return saida;
		}	
	}
}