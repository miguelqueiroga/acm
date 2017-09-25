package entity.util
{
	import entity.Vencimento;
	
	import mx.collections.ArrayCollection;
	
	public class InformacoesDepartamentosDonos
	{
		public static function getDepartamentosIndiretosEstruturaAntiga(depId:Number):ArrayCollection{ 
			var departamentoIndiretos:ArrayCollection = new ArrayCollection();
			if(depId == 1 || depId == 2){ // US GG || US VV
				departamentoIndiretos.addItem("3");
				departamentoIndiretos.addItem("9");
			}
			if(depId == 3){ // US Geral
				departamentoIndiretos.addItem("1");
				departamentoIndiretos.addItem("2");
				departamentoIndiretos.addItem("9");
			}
			if(depId == 4){ // Mx Geral
				departamentoIndiretos.addItem("5");
				departamentoIndiretos.addItem("6");
				departamentoIndiretos.addItem("9");
			}
			if(depId == 5 || depId == 6){ // Mx GG || Mx VV
				departamentoIndiretos.addItem("4");
				departamentoIndiretos.addItem("9");
			}
			if(depId == 8){ // Clinica
				departamentoIndiretos.addItem("1");
				departamentoIndiretos.addItem("2");
				departamentoIndiretos.addItem("3");
				departamentoIndiretos.addItem("4");
				departamentoIndiretos.addItem("5");
				departamentoIndiretos.addItem("6");
			}
			if(depId == 7){ // Proced Total
				departamentoIndiretos.addItem("9");
				departamentoIndiretos.addItem("10");
				departamentoIndiretos.addItem("11");
			}
			if(depId == 10 || depId == 11){ // Proced GG || Proced VV
				departamentoIndiretos.addItem("7");
				departamentoIndiretos.addItem("9");
			}
			if(depId == 9){ // US
				departamentoIndiretos.addItem("1");
				departamentoIndiretos.addItem("2");
				departamentoIndiretos.addItem("3");
				departamentoIndiretos.addItem("4");
				departamentoIndiretos.addItem("5");
				departamentoIndiretos.addItem("6");
				departamentoIndiretos.addItem("7");
				departamentoIndiretos.addItem("8");
				departamentoIndiretos.addItem("10");
				departamentoIndiretos.addItem("11");
			}
			return departamentoIndiretos;
		}
		
		public static function getDepartamentosIndiretosEstruturaNova(depId:Number):ArrayCollection{ 
			var departamentoIndiretos:ArrayCollection = new ArrayCollection();
			if(depId == 1 || depId == 2){ // US GG || US VV
				departamentoIndiretos.addItem("3");
				departamentoIndiretos.addItem("9");
			}
			if(depId == 3){ // US Geral
				departamentoIndiretos.addItem("1");
				departamentoIndiretos.addItem("2");
				departamentoIndiretos.addItem("9");
			}
			if(depId == 4){ // Mx Geral
				departamentoIndiretos.addItem("5");
				departamentoIndiretos.addItem("6");
			}
			if(depId == 5 || depId == 6){ // Mx GG || Mx VV
				departamentoIndiretos.addItem("4");
			}
			if(depId == 7){ // Proced Total
				departamentoIndiretos.addItem("9");
				departamentoIndiretos.addItem("10");
				departamentoIndiretos.addItem("11");
			}
			if(depId == 10 || depId == 11){ // Proced GG || Proced VV
				departamentoIndiretos.addItem("7");
				departamentoIndiretos.addItem("9");
			}
			if(depId == 9){ // US
				departamentoIndiretos.addItem("1");
				departamentoIndiretos.addItem("2");
				departamentoIndiretos.addItem("3");
				departamentoIndiretos.addItem("4");
				departamentoIndiretos.addItem("5");
				departamentoIndiretos.addItem("6");
				departamentoIndiretos.addItem("7");
				departamentoIndiretos.addItem("10");
				departamentoIndiretos.addItem("11");
			}
			return departamentoIndiretos;
		}
		
		public static function getPorcentagemPorData(departamentoDonoId:Number,departamentoInd:Number,vencimento:Vencimento):String {
			if(vencimento.venNome < "2014/10"){
				return getPorcentagemEstruturaAntiga(departamentoDonoId,departamentoInd);
			}
			else {
				return getPorcentagemEstruturaNova(departamentoDonoId,departamentoInd);
			}
		}
		
  		private static function getPorcentagemEstruturaAntiga(departamentoDonoId:Number,departamentoInd:Number):String {
			if(departamentoDonoId == 1 || departamentoDonoId == 2){ // US GG || US VV
				if(departamentoInd == 3) {
					return "50,00";
				}
				if(departamentoInd == 9) {
					return "33,00";
				}
			}
			if(departamentoDonoId == 3){ // US Geral
				if(departamentoInd == 1 || departamentoInd == 2) {
					return "100,00";
				}
				if(departamentoInd == 9) {
					return "66,00";
				}
			}
			if(departamentoDonoId == 4){ // Mx Geral
				if(departamentoInd == 5 || departamentoInd == 6) {
					return "100,00";
				}
				if(departamentoInd == 9) {
					return "32,00";
				}
			}
			if(departamentoDonoId == 5 || departamentoDonoId == 6){ // Mx GG || Mx VV
				if(departamentoInd == 4) {
					return "50,00";
				}
				if(departamentoInd == 9) {
					return "16,00";
				}
			}
			if(departamentoDonoId == 7){ // Proced Geral
				if(departamentoInd == 10 || departamentoInd == 11) {
					return "100,00";
				}
				if(departamentoInd == 9) {
					return "2,00";
				}
			}
			if(departamentoDonoId == 10 || departamentoDonoId == 11){ // Proced GG || Proced VV
				if(departamentoInd == 7) {
					return "50,00";
				}
				if(departamentoInd == 9) {
					return "1,00";
				}
			}
			if(departamentoDonoId == 9){ // UM
				return "100,00";
			}
			return null;
		}
		
		private static function getPorcentagemEstruturaNova(departamentoDonoId:Number,departamentoInd:Number):String {
			if(departamentoDonoId == 1 || departamentoDonoId == 2){ // US GG || US VV
				if(departamentoInd == 3) {
					return "50,00";
				}
				if(departamentoInd == 9) {
					return "49,00";
				}
			}
			if(departamentoDonoId == 3){ // US Geral
				if(departamentoInd == 1 || departamentoInd == 2) {
					return "100,00";
				}
				if(departamentoInd == 9) {
					return "98,00";
				}
			}
			if(departamentoDonoId == 4){ // Mx Geral
				if(departamentoInd == 5 || departamentoInd == 6) {
					return "100,00";
				}
				if(departamentoInd == 9) {
					return "0,00";
				}
			}
			if(departamentoDonoId == 5 || departamentoDonoId == 6){ // Mx GG || Mx VV
				if(departamentoInd == 4) {
					return "50,00";
				}
				if(departamentoInd == 9) {
					return "0,00";
				}
			}
			if(departamentoDonoId == 7){ // Proced Geral
				if(departamentoInd == 10 || departamentoInd == 11) {
					return "100,00";
				}
				if(departamentoInd == 9) {
					return "2,00";
				}
			}
			if(departamentoDonoId == 10 || departamentoDonoId == 11){ // Proced GG || Proced VV
				if(departamentoInd == 7) {
					return "50,00";
				}
				if(departamentoInd == 9) {
					return "1,00";
				}
			}
			if(departamentoDonoId == 9){ // UM
				return "100,00";
			}
			return null;
		}
	}
}