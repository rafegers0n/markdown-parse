

// File reading code from https://howtodoinjava.com/java/io/java-read-file-to-string-examples/
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;

public class MarkdownParse {
    static String[] extensions = {".jpg", ".jpeg,", ".png", ".tiff", ".gif", ".csv", ".svg", ".pdf"};
    public static ArrayList<String> getLinks(String markdown) {
        ArrayList<String> toReturn = new ArrayList<>();
        // find the next [, then find the ], then find the (, then take up to
        // the next )
        int currentIndex = 0;
        int nextOpenBracket = 0;
        int nextCloseBracket = 0;
        int openParen = 0;
        int closeParen = 0;;
        while(currentIndex < markdown.length()) {
            if (nextCloseBracket > openParen) {
                break;
            }
            nextOpenBracket = markdown.indexOf("[", currentIndex);
            nextCloseBracket = markdown.indexOf("]", nextOpenBracket);
            openParen = markdown.indexOf("(", nextCloseBracket);
            closeParen = markdown.indexOf(")", openParen);
            if(nextOpenBracket == -1 || nextCloseBracket == -1 || openParen == -1 || closeParen == -1){
                break;
            }
            if (!isImage(markdown.substring(openParen + 1, closeParen)) && openParen-nextCloseBracket ==1){
                toReturn.add(markdown.substring(openParen + 1, closeParen));
            }
            currentIndex = closeParen + 1;
        }
        return toReturn;
    }
    public static boolean isImage(String appendString){
        for (int i = 0; i < extensions.length; i += 1){
            if (appendString.contains(extensions[i])){
                return true;
            }
        }
        return false;
    }
    public static void main(String[] args) throws IOException {
		Path fileName = Path.of(args[0]);
	    String contents = Files.readString(fileName);
        ArrayList<String> links = getLinks(contents);
        System.out.println(links);
    }
}