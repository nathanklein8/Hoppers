package puzzles.chess.ptui;

import puzzles.common.Observer;
import puzzles.chess.model.ChessModel;

import java.io.IOException;
import java.util.Scanner;
/**
 * A Plain-Text user interface for the Solitare Chess game
 *
 * @author Madeline Mariano mam5090
 */
public class ChessPTUI implements Observer<ChessModel, String> {
    private ChessModel model;


    public void init(String filename) throws IOException {
        this.model = new ChessModel(filename);
        this.model.addObserver(this);
        System.out.println("Loaded: " + filename);
        System.out.println(model.getDisplay());
        displayHelp();
    }


    @Override
    public void update(ChessModel model, String data) {
        // for demonstration purposes
        System.out.println(data);
        System.out.print(model.getDisplay());



    }

    private void displayHelp() {
        System.out.println( "h(int)              -- hint next move" );
        System.out.println( "l(oad) filename     -- load new puzzle file" );
        System.out.println( "s(elect) r c        -- select cell at r, c" );
        System.out.println( "q(uit)              -- quit the game" );
        System.out.println( "r(eset)             -- reset the current game" );
    }

    public void run() throws IOException {
        Scanner in = new Scanner( System.in );
        for ( ; ; ) {
            System.out.print( "> " );
            String line = in.nextLine();
            String[] words = line.split( "\\s+" );
            if (words.length > 0) {
                if (words[0].startsWith( "q" )) {
                    break;
                }
                else if(words[0].startsWith( "h" )){
                    model.hint();
                } else if(words[0].startsWith( "l" )){
                   if(words.length > 1){
                       model.loadNew(words[1]);
                   }
                   else{
                       System.out.println("No file included, invalid command.");
                       displayHelp();
                   }
                } else if(words[0].startsWith( "r" )){
                        model.reset();
                } else if(words[0].startsWith( "s" )){
                    if(words.length ==3){
                        model.selectCell(Integer.parseInt(words[1]), Integer.parseInt(words[2]));
                    } else{
                        System.out.println("Invalid command, coordinates missing");
                        displayHelp();
                    }
                } else {
                    displayHelp();
                }
            }
        }
    }

    public static void main(String[] args) {
        if (args.length != 1) {
            System.out.println("Usage: java ChessPTUI filename");
        } else {
            try {
                ChessPTUI ptui = new ChessPTUI();
                ptui.init(args[0]);
                ptui.run();
            } catch (IOException ioe) {
                System.out.println(ioe.getMessage());
            }
        }
    }
}

