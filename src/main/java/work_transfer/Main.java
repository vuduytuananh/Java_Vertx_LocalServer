package work_transfer;

import io.vertx.core.Vertx;
import io.vertx.core.buffer.Buffer;
import io.vertx.core.http.HttpServerRequest;
import io.vertx.ext.web.Router;
import io.vertx.ext.web.RoutingContext;

public class Main {
	static String path = "/Users/vuduytuananh/Desktop/tribute";
	static Vertx vertx = Vertx.vertx();
	private static void handle(FileNames fileName, RoutingContext rc){
		long start = System.currentTimeMillis();
		vertx.fileSystem().readFile(path+"/" + fileName.giveName(), result ->{
			if (result.succeeded()){
				rc.response().putHeader("content-type", fileName.giveMIMEType())
				.putHeader("content-length", ""+result.result().length())
				.write(result.result()).end();	
			}else{
				System.out.println("failed: "+ fileName.giveName() );
			}
		});
		System.out.println(fileName.giveName()+" "+ (System.currentTimeMillis()-start));
	}
	private static void read(FileNames fileName, RoutingContext rc){
		final String checkpath = "/Users/vuduytuananh/Desktop/watch";
		final String workingpath =path;
		vertx.fileSystem().readFile(workingpath+"/"+fileName.giveName(), workingResult -> {
			vertx.fileSystem().readFile(checkpath+"/"+ fileName.giveName(), checkResult -> {
				if (!notChanged(workingResult.result(), checkResult.result()) && !rc.response().ended()){
					vertx.fileSystem().writeFile(checkpath+"/"+ fileName.giveName(), workingResult.result(), after -> {});
					System.out.println("yes");
					rc.response().putHeader("Content-Type", "text/html")
					.putHeader("Content-Length", ""+Buffer.buffer("y").length())
					.write(Buffer.buffer("y")).end();
				}else if (fileName == FileNames.BG_JPG && !rc.response().ended()){
					rc.response().putHeader("Content-Type", "text/html")
					.putHeader("Content-Length", ""+Buffer.buffer("n").length())
					.write(Buffer.buffer("n")).end();
					System.out.println("no");
				}
			});
		});
	}
	private static boolean notChanged(Buffer first, Buffer second){
		if(first == null || second == null) return false;
		if(first.length() != second.length()){ 
			return false;
		}
		else {
			for(int i = first.length() - 1; i>=0; i--){
					if(first.getByte(i) != second.getByte(i)) return false;
			}
		}
		return true;
	}
	public static void main(String[] args) {
		Router router = Router.router(vertx);
		router.route("/index.html").handler(rc -> {handle(FileNames.INDEX_HTML,rc);});
		router.route("/script.js").handler(rc ->{handle(FileNames.SCRIPT_JS,rc);});
		router.route("/style.css").handler(rc ->{handle(FileNames.STYLE_CSS,rc);});
		router.route("/bg.jpg").handler(rc -> {handle(FileNames.BG_JPG,rc);});
		router.route("/checkReload").handler(rc -> {
			for(int i =0; i <FileNames.values().length; i++){
				read(FileNames.values()[i], rc);
			}
		});
		vertx.createHttpServer().requestHandler(router::accept).listen(8080);	
	}
}