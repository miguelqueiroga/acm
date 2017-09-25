package components
{
	import mx.controls.DataGrid;
	import flash.display.Sprite;

	 

	public class DataGridColor extends DataGrid
	{
		public var rowColorFunction:Function; 
		public function DataGridColor(){
			super();
		}
		
		override protected function drawRowBackground(s:Sprite, rowIndex:int, y:Number, height:Number, color:uint, dataIndex:int):void
			{
				// Se um dataProvider existir e o valor de dataIndex for menor que o
				// tamanho do DP, executa o método que define a cor da linha.
				if (this.dataProvider && dataIndex < this.dataProvider.length && this.rowColorFunction != null)
				{
				var data:Object = this.dataProvider[dataIndex];
				
				// Chamando a função que define a cor de fundo do item.
				color = this.rowColorFunction(data, color);
				}
				
				// Chamando o método da superclasse
				super.drawRowBackground(s, rowIndex, y, height, color, dataIndex);
			}
	}
}