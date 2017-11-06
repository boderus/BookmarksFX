package sample;

import javafx.application.Application;
import javafx.geometry.HPos;
import javafx.geometry.VPos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import javafx.stage.Stage;

import javax.xml.stream.XMLStreamException;
import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import static java.util.stream.Collectors.groupingBy;


public class BookmarkManager extends Application {

/*
    private final Node rootIcon = new ImageView(
            new Image(getClass().getResourceAsStream("folder_16.png"))
    );
*/

    WebView browser = new WebView();
    final WebEngine webEngine = browser.getEngine();
    BookmarksImporter importer = new SoupImporter();
    String fileName = "resources/bookmarks.html";

    List<String> urls = Arrays.asList("http://www.onet.pl", "http://google.pl");

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws IOException, XMLStreamException {
        primaryStage.setTitle("Tree View Sample");

        TreeItem<Bookmark> rootItem = new TreeItem<Bookmark> (new Bookmark("MAIN", "MAIN", "Main"));
        rootItem.setExpanded(true);

        List<Bookmark> all = importer.importBookmarks(new File(fileName));
        Map<String, List<Bookmark>> grouped = all.stream().collect(groupingBy(Bookmark::getDirectory));

        grouped.forEach((a, b) -> System.out.println(a));

        grouped.forEach((a, b) -> {
            TreeItem<Bookmark> item = new TreeItem<>(new Bookmark("", a, ""));
            b.forEach(bookmark -> item.getChildren().add(new TreeItem<>(bookmark)));
            rootItem.getChildren().add(item);
        });
        /*
        importer.importBookmarks(fileName).forEach(b -> {
            TreeItem<Bookmark> item = new TreeItem<>(b);
            rootItem.getChildren().add(item);
        });
        */


        /*
        for (int i = 1; i < 6; i++) {
            TreeItem<String> item = new TreeItem<String> (urls.get(i%2));
            rootItem.getChildren().add(item);
        }
        */

        TreeView<Bookmark> tree = new TreeView<Bookmark> (rootItem);
        StackPane root = new StackPane();


        tree.setCellFactory(t -> {
            TreeCell<Bookmark> cell = new TreeCell<Bookmark>() {
                @Override
                public void updateItem(Bookmark item, boolean empty) {
                    super.updateItem(item, empty) ;
                    if (empty) {
                        setText(null);
                    } else {
                        setText(item.getName());
                    }
                }
            };
            cell.setOnMouseClicked(event -> {
                if (! cell.isEmpty()) {
                    TreeItem<Bookmark> treeItem = cell.getTreeItem();
                    System.out.println("Klik" + treeItem.getValue());
                    webEngine.load(treeItem.getValue().getUrl());
                }
            });
            return cell ;
        });


        //root.getChildren().add(tree);

        //HBox hBox = new HBox();

        BorderPane borderPane = new BorderPane();

        MenuBar menuBar = new MenuBar();

        // --- Menu File
        Menu menuFile = new Menu("File");

        // --- Menu Edit
        Menu menuEdit = new Menu("Edit");

        // --- Menu View
        Menu menuView = new Menu("View");

        menuBar.getMenus().addAll(menuFile, menuEdit, menuView);


        borderPane.setTop(menuBar);
        borderPane.setLeft(tree);
        //borderPane.setCenter(new Browser());

        //hBox.getChildren().add(tree);
        //hBox.getChildren().add(new Browser());

        //WebView browser = new WebView();
        //final WebEngine webEngine = browser.getEngine();

        webEngine.load("http://www.wp.pl");

        borderPane.setCenter(browser);


        //root.getChildren().add(new Browser(), 700, 800, Color.web("666970"));

        //Scene scene = new Scene(new Browser(),750,500, Color.web("#666970"));

        //StackPane stackPane = new StackPane();

//        VBox box = new VBox();

  //      box.getChildren().add(browser);

        //borderPane.setCenter(browser);

        Scene s = new Scene(borderPane);

        //root.setCenter(tree);

        //root.getChildren().add(tree);
        //primaryStage.setScene(new Scene(root, 300, 250));
        primaryStage.setScene(s);
        primaryStage.show();
    }

    class Browser extends Region {

        final WebView browser = new WebView();
        final WebEngine webEngine = browser.getEngine();

        public Browser() {
            //apply the styles
            getStyleClass().add("browser");
            // load the web page
            webEngine.load("http://www.wp.pl");
            //add the web view to the scene
            getChildren().add(browser);

        }
        private Node createSpacer() {
            Region spacer = new Region();
            HBox.setHgrow(spacer, Priority.ALWAYS);
            return spacer;
        }

        @Override protected void layoutChildren() {
            double w = getWidth();
            double h = getHeight();
            layoutInArea(browser,0,0,w,h,0, HPos.CENTER, VPos.CENTER);
        }

        @Override protected double computePrefWidth(double height) {
            return 750;
        }

        @Override protected double computePrefHeight(double width) {
            return 500;
        }
    }

}
