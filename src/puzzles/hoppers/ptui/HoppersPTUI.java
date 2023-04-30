package puzzles.hoppers.ptui;

import puzzles.common.Observer;
import puzzles.hoppers.model.HoppersModel;

import java.io.IOException;
import java.util.Scanner;

/**
 * Class to create a Plain Text UI using HoppersConfig and HoppersModel
 *
 * @author Nathan Klein nek7125@rit.edu
 */
public class HoppersPTUI implements Observer<HoppersModel, String> {
    private HoppersModel model;

    public void init(String filename) throws IOException {
        this.model = new HoppersModel(filename);
        this.model.addObserver(this);
        System.out.println("Loaded: " + filename);
        System.out.println(model.getDisplay());
        displayHelp();
    }

    @Override
    public void update(HoppersModel model, String data) {
        // for demonstration purposes
        System.out.println(data);
        System.out.println(model.getDisplay());
    }

    /**
     * displays valid commands
     */
    private void displayHelp() {
        System.out.println( "h(int)              -- hint next move" );
        System.out.println( "l(oad) filename     -- load new puzzle file" );
        System.out.println( "s(elect) r c        -- select cell at r, c" );
        System.out.println( "q(uit)              -- quit the game" );
        System.out.println( "r(eset)             -- reset the current game" );
    }

    /**
     * loops while the game is being played
     */
    public void run() {
        Scanner in = new Scanner( System.in );
        for ( ; ; ) {
            System.out.print( "> " );
            String line = in.nextLine();
            String[] words = line.split( "\\s+" );
            if (words.length > 0) {
                if (words[0].startsWith("q")) {         // quit command
                    break;
                } else if (words[0].startsWith("h")) {  // hint command
                    model.hint();
                } else if (words[0].startsWith("r")) {  // reset command
                    model.reset();
                } else if (words[0].startsWith("s")) {  // select command
                    int row = Integer.parseInt(words[1]);
                    int col = Integer.parseInt(words[2]);
                    model.select(row, col);
                } else if (words[0].startsWith("l")) {  // load command
                    model.load(words[1]);
                }
                else {
                    displayHelp();
                }
            }
        }
    }

    public static void main(String[] args) {
        if (args.length != 1) {
            System.out.println("Usage: java HoppersPTUI filename");
        } else {
            try {
                HoppersPTUI ptui = new HoppersPTUI();
                ptui.init(args[0]);
                ptui.run();
            } catch (IOException ioe) {
                System.out.println(ioe.getMessage());
            }
        }
    }
}
