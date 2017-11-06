package sample;

import javax.xml.stream.*;
import javax.xml.stream.events.XMLEvent;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.Collections;
import java.util.List;

public class StaxImporter implements BookmarksImporter {

    @Override
    public List<Bookmark> importBookmarks(File file) throws FileNotFoundException, XMLStreamException {
        return go(file);
    }

    private List<Bookmark> go(File f) throws FileNotFoundException, XMLStreamException {

        List<Bookmark> result = Collections.emptyList();

        XMLInputFactory inputFactory = XMLInputFactory.newInstance();

        XMLStreamReader eventReader = inputFactory.createXMLStreamReader(new FileReader(f));


//        XMLEventReader streamReader = inputFactory.createFilteredReader(streamReader, new CustomFilter());

        while(eventReader.hasNext())
        {
            eventReader.next();

            System.out.println(eventReader.getText());

            int event = eventReader.getEventType();

            if (event == XMLStreamReader.START_ELEMENT)
            {
                System.out.println(eventReader.getLocalName());
            }
        }

        return result;

    }

    private class CustomFilter implements EventFilter{

        @Override
        public boolean accept(XMLEvent event) {
            String s = event.asStartElement().getName().getLocalPart();
            return event.isStartElement() && "h3".equalsIgnoreCase(s);
        }
    }

    public static void main(String[] args) throws FileNotFoundException, XMLStreamException {
        StaxImporter si = new StaxImporter();
        String fileName = "resources/bookmarks.html";
        si.importBookmarks(new File(fileName));
    }
}
