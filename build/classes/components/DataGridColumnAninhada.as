package components

import mx.controls.DateField;
import mx.controls.dataGridClasses.DataGridColumn;

public class DataGridColunaAninhada extends DataGridColumn {


	public function DataGridColunaAninhada(columnName : String = null) {
		labelFunction = descricao
		super(columnName);
	}
	
	private var aux : Array = new Array;
	
	public var acesso : String;
	
	protected function descricao(obj : Object , data : DataGridColumn) : Object {
		var objeto : Object = carregarObjeto(obj)
		//Caso seja data ja converte no formato data 
		if (objeto is Date) {
			return DateField.dateToString(objeto as Date, "DD/MM/YYYY");
		}
		return objeto;
	}
	protected final function carregarObjeto(obj : Object) : Object {
		if (!acesso) {
			acesso = dataField;
		}
		aux = acesso.split(".");
	
	
		if (obj != null && aux.length > 1) {
			for (var i : int = 0 ; i < aux.length ; i++) {
				if (obj == null) {
					break;
				}
				obj = obj[aux[i]]
			}
		} else {
			obj = obj[dataField]
		}
		return obj != null ? obj : "";
	}
}
}