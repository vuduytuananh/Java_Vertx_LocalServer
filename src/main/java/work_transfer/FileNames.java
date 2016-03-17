package work_transfer;

public enum FileNames {
	INDEX_HTML("index.html","text/html"), SCRIPT_JS("script.js","text/javascript"), STYLE_CSS("style.css","text/css"), BG_JPG("bg.jpg","image/jpeg");
	private String fileName;
	private String MIMEtype;
	FileNames(String fileName, String type){
		this.fileName = fileName;
		this.MIMEtype = type;
	}
	public String giveName(){
		return this.fileName;
	}
	public String giveMIMEType(){
		return MIMEtype;
	}
}
