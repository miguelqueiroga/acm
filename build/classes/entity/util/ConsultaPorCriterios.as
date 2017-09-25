package entity.util
{
	import mx.collections.ArrayCollection;
	import mx.controls.Alert;
	
	public class ConsultaPorCriterios
	{
		public static function getConsultaPorCriterios(criterios: ArrayCollection, nomeClasse:String):String {
			var stringConsulta:String = "from ACM.model.entity." + nomeClasse + " as o";
			if(criterios.length > 0) {
	            stringConsulta += " where";
	        	var i:int = 0;
	            var criterio:CriterioDePesquisa = null;
	            for (i = 0; i < criterios.length; i++)
                {
                	criterio = criterios.getItemAt(i) as CriterioDePesquisa;
                	stringConsulta += " " + criterio.getCriterio();
	                stringConsulta += criterio.getOperador();
	                stringConsulta += criterio.getValor();
	        		if((i + 1) < criterios.length)
	                	 stringConsulta += " AND";
	            }
	        }
	        return stringConsulta;
		}
	}
}