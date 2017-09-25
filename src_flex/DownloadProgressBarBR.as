package
{
	import mx.preloaders.DownloadProgressBar;

	public class DownloadProgressBarBR extends DownloadProgressBar
	{
		public function DownloadProgressBarBR(){
			super();
			downloadingLabel = "Carregando...";
			initializingLabel = "Iniciando...";
		}
	}
}