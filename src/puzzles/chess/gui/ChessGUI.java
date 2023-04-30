package puzzles.chess.gui;

import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import puzzles.common.Observer;
import puzzles.chess.model.ChessModel;

import java.io.File;
import java.nio.file.Paths;
import java.util.HashMap;

/**
 * Class runs a Chess GUI making use of ChessModel and ChessConfig
 *
 * @author Madeline Mariano mam5090
 */
public class ChessGUI extends Application implements Observer<ChessModel, String> {
    /** the model being played over*/
    private ChessModel model;

    /** The size of all icons, in square dimension */
    private final static int ICON_SIZE = 75;
    /** the font size for labels and buttons */
    private final static int FONT_SIZE = 12;

    /** The resources directory is located directly underneath the gui package */
    private final static String RESOURCES_DIR = "resources/";

    /** image representation of the bishop*/
    private final Image bishop = new Image(getClass().getResourceAsStream(RESOURCES_DIR+"bishop.png"));
    /** image representation of the king*/
    private final Image king = new Image(getClass().getResourceAsStream(RESOURCES_DIR+"king.png"));
    /** image representation of the knight*/
    private final Image knight = new Image(getClass().getResourceAsStream(RESOURCES_DIR+"knight.png"));
    /** image representation of the pawn*/
    private final Image pawn = new Image(getClass().getResourceAsStream(RESOURCES_DIR+"pawn.png"));
    /** image representation of the queen*/
    private final Image queen = new Image(getClass().getResourceAsStream(RESOURCES_DIR+"queen.png"));
    /** image representation of the rook*/
    private final Image rook = new Image(getClass().getResourceAsStream(RESOURCES_DIR+"rook.png"));

    /** a definition of light and dark and for the button backgrounds */
    private static final Background LIGHT =
            new Background( new BackgroundFill(Color.WHITE, null, null));
    private static final Background DARK =
            new Background( new BackgroundFill(Color.MIDNIGHTBLUE, null, null));

    /** the stage!*/
    private Stage stage;
    /** whether the board was initialized or not*/
    private boolean initialized;
    /**label with status message from model */
    private Label status;
    /** hashmap holding string of coordinates and the button at that cell*/
    private HashMap<String, Button> pieces;
    /** first file program is run with */
    private String first;
    /** gridpane representing chessboard*/
    private GridPane board;

    /**
     * chooses a file when the load button is pressed and resets the board for play
     */
    private void load() {
        FileChooser chooser = new FileChooser();
        chooser.setTitle("Select Chess File");
        String currentPath = Paths.get(".").toAbsolutePath().normalize().toString();
        currentPath += File.separator + "data" + File.separator + "chess";
        chooser.setInitialDirectory(new File(currentPath));
        File selectFile = chooser.showOpenDialog(stage);
        if (selectFile != null) {
            board.getChildren().clear();
            model.loadNew("data/chess/"+selectFile.getName());
            this.pieces = new HashMap<>();
        }
    }

    /**
     * initializes the board and sets initialized to false
     */
    @Override
    public void init() {
        String filename = getParameters().getRaw().get(0);
        this.model = new ChessModel(filename);
        this.initialized = false;
        model.addObserver(this);
        first = filename;
        this.board = new GridPane();
        this.pieces = new HashMap<>();

    }

    /**
     * builds the chess board/updates pieces that may have changed
     */
    public void buildBoard(){
        boolean light = true;
        for(int y = 0; y < model.getRow(); y++){
            for(int x =0; x < model.getCol(); x++){
                char piece = model.getCell(y,x);
                Button button = new Button();
                switch (piece){
                    case 'B' -> button.setGraphic(new ImageView(bishop));
                    case 'K' -> button.setGraphic(new ImageView(king));
                    case 'N' -> button.setGraphic(new ImageView(knight));
                    case 'P' -> button.setGraphic(new ImageView(pawn));
                    case 'Q' -> button.setGraphic(new ImageView(queen));
                    case 'R' -> button.setGraphic(new ImageView(rook));
                    case '.' -> button.setGraphic(null);
                }
                if(light){
                    button.setBackground(LIGHT);
                    light = false;
                }
                else{
                    button.setBackground(DARK);
                    light = true;
                }

                button.setMinSize(ICON_SIZE, ICON_SIZE);
                button.setMaxSize(ICON_SIZE, ICON_SIZE);
                board.add(button, x, y);
                String coor = y + " " + x;
                pieces.put(coor, button);
            }
            light = (y-1)%2 == 0;
        }

        for(String coor : pieces.keySet()){
            String[] coors = coor.split("\\s+");
            pieces.get(coor).setOnAction(e -> model.selectCell(Integer.parseInt(coors[0]), Integer.parseInt(coors[1])));
        }
    }

    /**
     * Starts construction of the stage
     *
     * @param stage the primary stage for this application, onto which
     * the application scene can be set.
     * Applications may create other stages, if needed, but they will not be
     * primary stages.
     * @throws Exception if file not found
     */
    @Override
    public void start(Stage stage) throws Exception {
        this.initialized = true;
        this.stage = stage;
        BorderPane borderPane = new BorderPane();
        HBox top = new HBox();
        HBox bottom = new HBox();

        this.status = new Label("Load new file.");
        status.setFont(new Font(FONT_SIZE));
        top.getChildren().add(status);
        top.setAlignment(Pos.TOP_CENTER);

        Button load = new Button("Load");
        load.setFont(new Font(FONT_SIZE));
        load.setOnAction(e -> load());

        Button reset = new Button("Reset");
        reset.setFont(new Font(FONT_SIZE));
        reset.setOnAction(e -> model.reset());

        Button hint = new Button("Hint");
        hint.setFont(new Font(FONT_SIZE));
        hint.setOnAction(e -> model.hint());

        bottom.getChildren().addAll(load, reset, hint);
        bottom.setAlignment(Pos.BOTTOM_CENTER);

        buildBoard();

        borderPane.setTop(top);
        borderPane.setCenter(board);
        borderPane.setBottom(bottom);
        Scene scene = new Scene(borderPane);
        stage.setTitle("Chess GUI");
        stage.setScene(scene);
        stage.show();

        model.loadNew(first);
    }

    /**
     * receives updates from the model and updates the view
     * @param chessModel the object that wishes to inform this object
     *                about something that has happened.
     * @param msg optional data the server.model can send to the observer
     *
     */
    @Override
    public void update(ChessModel chessModel, String msg) {
        if(!this.initialized) return;

        status.setText(msg);
        buildBoard();
        this.stage.sizeToScene();  // when a different sized puzzle is loaded
    }

    /**
     * launches the program with given file
     * @param args file been given
     */
    public static void main(String[] args) {
        if(args.length != 1){
            System.out.println("Usage: java ChessPTUI filename");
        }
        else{
        Application.launch(args);
        }
    }
}
