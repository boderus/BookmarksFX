package sample;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

public class SoupImporter implements BookmarksImporter {

    private Stack<String> directories = new Stack<>();
//    private String currentDir;
    List<Bookmark> result = new ArrayList<>();

    public static void main(String[] args) throws IOException {
        SoupImporter importer = new SoupImporter();
        importer.importBookmarks(new File("resources/bookmarks.html"));
    }

    @Override
    public List<Bookmark> importBookmarks(File file) throws IOException {

        //String content = new String(Files.readAllBytes(Paths.get(fileName)));

        System.out.println(file.getAbsolutePath());

        Document doc = Jsoup.parse(file, "UTF-8");

        directories.push("default");

        System.out.println("Git Test");


/*
        for (Element e : doc.getElementsByTag("DL"))
        {
            handleDL(e);
        }
*/


        for (Element e : doc.getAllElements()) {


            if ("h3".equals(e.nodeName()) || "DL".equalsIgnoreCase(e.nodeName())){
                processElement(e);
            }
        }

//        result.forEach(System.out::println);

        //System.out.println(doc.title());


        //System.out.println("Importuje");
        return result;
    }

    private void handleDL(Element e) {

        e.getElementsByTag("DL").forEach(p -> handleDL(p));
        String dir =  e.siblingElements().stream().filter(f -> f.nodeName().equalsIgnoreCase("h3")).findFirst().map(g -> g.nodeName()).orElse("default");


    }

    private void processDL(Element element)
    {
//        element.siblingElements()

        element.getElementsByTag("A").forEach(a -> result.add(new Bookmark(directories.peek(), element.ownText(), element.attr("HREF"))));

    }

    private void processElement(Element elem) {

        if ("h3".equals(elem.nodeName()))
        {
            //currentDir = elem.ownText();
            directories.push(elem.ownText());
            return;
        }

        if ("a".equals(elem.nodeName()))
        {
            result.add(new Bookmark(directories.peek(),elem.ownText(), elem.attr("HREF")));
        }

        if ("dl".equalsIgnoreCase(elem.nodeName()))
        {
            elem.getElementsByTag("A").forEach(a->processElement(a));
            directories.pop();
        }

        /*
        System.out.println(currentDir);
        System.out.println(elem.nodeName());
        System.out.println(elem.ownText());
        System.out.println(elem.attr("HREF"));
        */
        /*
        for (Element e : elem.children())
        {
            System.out.println(e.nodeName());
        }
        */
    }

}
