package puzzles.hoppers.gui;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.text.Font;
import javafx.stage.FileChooser;
import puzzles.common.Observer;
import puzzles.hoppers.model.HoppersModel;

import javafx.application.Application;
import javafx.stage.Stage;

import java.io.File;
import java.nio.file.Paths;
import java.util.List;

public class HoppersGUI extends Application implements Observer<HoppersModel, String> {
    /** The size of all icons, in square dimension */
    private final static int ICON_SIZE = 75;
    /** the font size for labels and buttons */
    private final static int FONT_SIZE = 16;
    /** The resources directory is located directly underneath the gui package */
    private final static String RDIR = "resources/";

    // for demonstration purposes
    private Image redFrog = new Image(getClass().getResourceAsStream(RDIR+"red_frog.png"));
    private Image greenFrog = new Image(getClass().getResourceAsStream(RDIR+"green_frog.png"));
    private Image lilyPad = new Image(getClass().getResourceAsStream(RDIR+"lily_pad.png"));
    private Image water = new Image(getClass().getResourceAsStream(RDIR+"water.png"));

    private Stage stage;
    private GridPane pond;
    private Label message;

    private HoppersModel model;

    private boolean initialized;
    private String firstFile;

    private void load() {   // pretty sure this works...
        FileChooser chooser = new FileChooser();
        chooser.setTitle("Select Hoppers File");
        String currentPath = Paths.get(".").toAbsolutePath().normalize().toString();
        currentPath += File.separator + "data" + File.separator + "hoppers";
        chooser.setInitialDirectory(new File(currentPath));
        File selectedFile = chooser.showOpenDialog(stage);
        if (selectedFile != null) {
            pond.getChildren().clear();
            model.load("data/hoppers/"+selectedFile.getName());
        }
    }

    public void init() {
        this.initialized = false;
        String filename = getParameters().getRaw().get(0);
        model = new HoppersModel(filename);
        model.addObserver(this);
        firstFile = filename;
    }

    @Override
    public void start(Stage stage) throws Exception {
        this.initialized = true;
        this.stage = stage;

        BorderPane pane = new BorderPane();

        // Make Buttons Section @ Bottom
        HBox buttons = new HBox();
        Button load = new Button("Load");
        load.setFont(new Font(FONT_SIZE));
        load.setOnAction(e -> load());
        Button reset = new Button("Reset");
        reset.setFont(new Font(FONT_SIZE));
        reset.setOnAction(e -> model.reset());
        Button hint = new Button("Hint");
        hint.setFont(new Font(FONT_SIZE));
        hint.setOnAction(e -> model.hint());
        buttons.getChildren().addAll(List.of(load, reset, hint));
        buttons.setAlignment(Pos.BOTTOM_CENTER);

        // Make Message Box
        HBox text = new HBox();
        message = new Label();
        message.setFont(new Font(FONT_SIZE));
        text.getChildren().add(message);
        text.setAlignment(Pos.TOP_CENTER);

        // Make pond
        pond = new GridPane();
        pond.setAlignment(Pos.CENTER);

        // Add stuff to main pane
        pane.setCenter(pond);
        pane.setBottom(buttons);
        pane.setTop(text);

        Scene scene = new Scene(pane);
        stage.setScene(scene);
        stage.setTitle("Hoppers");
        stage.show();

        model.load(firstFile);
    }

    @Override
    public void update(HoppersModel hoppersModel, String msg) {
        if (!initialized) {
            return;
        }
        message.setText(msg);
        if (pond.getChildren().size()==0) {

        }
        for (int r=0; r<model.getRows(); r++) {
            for (int c=0; c<model.getCols(); c++) {
                int row = r;
                int col = c;
                Button daButton = new Button();
                char cell = model.getCell(r, c);
                switch (cell) {
                    case 'G' -> daButton.setGraphic(new ImageView(greenFrog));
                    case 'R' -> daButton.setGraphic(new ImageView(redFrog));
                    case '*' -> daButton.setGraphic(new ImageView(water));
                    case '.' -> daButton.setGraphic(new ImageView(lilyPad));
                }
                daButton.setMinSize(ICON_SIZE, ICON_SIZE);
                daButton.setMaxSize(ICON_SIZE, ICON_SIZE);
                daButton.setOnAction(e -> model.select(row, col));
                pond.add(daButton, c, r);
            }
        }
        this.stage.sizeToScene();  // when a different sized puzzle is loaded
    }

    public static void main(String[] args) {
        if (args.length != 1) {
            System.out.println("Usage: java HoppersPTUI filename");
        } else {
            Application.launch(args);
        }
    }
}
