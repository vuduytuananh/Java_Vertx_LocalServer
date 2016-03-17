package work_transfer;

import java.util.Arrays;

import io.vertx.core.Vertx;

public class Separate {

	public static void main(String[] args) {
		String path = "/Users/vuduytuananh/Desktop/years.txt";
		Vertx vertx = Vertx.vertx();
		vertx.fileSystem().readFile(path, result ->{
			String fileString = new String(result.result().toString("utf-8"));
			String[] separated_event = fileString.split("\n\n");
			Arrays.asList(separated_event).forEach(e->{
			String[] separated_year = e.split("\n");
			System.out.println("<li class=\"space\"><div class=\"row\"><div class=\"year col-xs-1\">"+separated_year[0]+"</div><div class=\"col-xs-11 bg-warning event\">"+separated_year[1]+"</div></div></li>");
			});
		});

	}

}
