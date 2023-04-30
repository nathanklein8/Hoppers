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
import javafx.stage.Stage;
import puzzles.common.Observer;
import puzzles.chess.model.ChessModel;

import java.util.HashMap;

public class ChessGUI extends Application implements Observer<ChessModel, String> {
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
    private BorderPane borderPane;
    private boolean initialized;
    private Label status;
    private HashMap<String, Button> pieces;

    /**
     * initializes the board
     */
    @Override
    public void init() {
        // get the file name from the command line
        String filename = getParameters().getRaw().get(0);
        this.model = new ChessModel(filename);
        model.addObserver(this);
        this.initialized = false;
        this.pieces = new HashMap<>();

    }

    @Override
    public void start(Stage stage) throws Exception {
        this.initialized = true;

        this.stage = stage;
        this.borderPane = new BorderPane();
        HBox top = new HBox();
        HBox bottom = new HBox();
        GridPane board = new GridPane();

        this.status = new Label("Load new file.");
        top.getChildren().add(status);
        top.setAlignment(Pos.CENTER);

        Button load = new Button("Load");
        Button reset = new Button("Reset");
        Button hint = new Button("Hint");
        bottom.getChildren().addAll(load, reset, hint);
        bottom.setAlignment(Pos.CENTER);

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
            pieces.get(coor).setOnAction(event -> model.selectCell(Integer.parseInt(coors[0]), Integer.parseInt(coors[1])));
        }


        borderPane.setTop(top);
        borderPane.setCenter(board);
        borderPane.setBottom(bottom);
        Scene scene = new Scene(borderPane);
        stage.setTitle("Chess GUI");
        stage.setScene(scene);
        stage.show();
    }

    @Override
    public void update(ChessModel chessModel, String msg) {
        if(!this.initialized) return;

        this.stage.sizeToScene();  // when a different sized puzzle is loaded
    }

    public static void main(String[] args) {
        Application.launch(args);
    }
}
