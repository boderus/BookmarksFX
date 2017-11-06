package sample;

import javax.xml.stream.XMLStreamException;
import java.io.File;
import java.io.IOException;
import java.util.List;

public interface BookmarksImporter {

    public List<Bookmark> importBookmarks(File file) throws IOException, XMLStreamException;

}
