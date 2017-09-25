package ACM.util;


public class Tools {
		
	public static String doubleToString(double valor) {
		double fator = Math.pow(10, 2);
		if (valor >= 0) {
			return new java.text.DecimalFormat("#,##0.00", new java.text.DecimalFormatSymbols (new java.util.Locale ("pt", "BR"))).format(((long) (valor * fator + 0.5)) / fator);
		} else {
			return new java.text.DecimalFormat("#,##0.00", new java.text.DecimalFormatSymbols (new java.util.Locale ("pt", "BR"))).format(((long) (valor * fator - 0.5)) / fator);
		}
	}
	
	public static String doubleToStringMonetario(double valor) {
		double fator = Math.pow(10, 2);
		if (valor >= 0) {
			return new java.text.DecimalFormat("R$ #,##0.00", new java.text.DecimalFormatSymbols (new java.util.Locale ("pt", "BR"))).format(((long) (valor * fator + 0.5)) / fator);
		} else {
			return new java.text.DecimalFormat("R$ #,##0.00", new java.text.DecimalFormatSymbols (new java.util.Locale ("pt", "BR"))).format(((long) (valor * fator - 0.5)) / fator);
		}
	}
	
	public static Double stringToDoubleMonetario(String numero) {
		return Double.parseDouble(numero.substring(3).replaceAll("\\.", "").replaceAll(",", "\\."));	
	}
	
	public static Double stringToDouble(String numero) {
		return Double.parseDouble(numero.replaceAll("\\.", "").replaceAll(",", "\\."));	
	}	

}
