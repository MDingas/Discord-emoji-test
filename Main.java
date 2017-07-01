import java.lang.*;
import java.util.*;
import java.awt.datatransfer.*;
import java.awt.Toolkit;

public class Main {

    public static String StringToNum(char c){
        switch(c){
            case '0':
                return " :zero: ";
            case '1':
                return " :one: ";
            case '2':
                return " :two: ";
            case '3':
                return " :three: ";
            case '4':
                return " :four: ";
            case '5':
                return " :five: ";
            case '6':
                return " :six: ";
            case '7':
                return " :seven: ";
            case '8':
                return " :eight: ";
            case '9':
                return " :nine: ";
            default:
                return "";
        }
    }

    public static void appendTo(String myString,StringBuilder out){
        final int AVG_PER_WORD = 24;        /* average number of characters on an emoji per word */
        final int MAX_LINE = 350;
        final int MAX_TOTAL = 2000;

        int charCounter = 0;                /* total characters (to make sure it doesnt go above Discord's limit */
        int charPerLineCounter = 0;         /* total characters per current line (to prevent a word being split up between lines */
        boolean onSpace = false;            /* I want to only add a newline so i dont break up words between lines */


        out.append(".");                    /* icon align purpose */
        for(char c : myString.toCharArray()){
            char cLower = Character.toLowerCase(c);
            if( (cLower >= 'a' && cLower <= 'z') )
                out.append(" :regional_indicator_" + cLower + ": ");
            else if(Character.isDigit(c)){
                out.append(Main.StringToNum(c));
            }
            else{
                switch(c){
                    case ' ' :
                        out.append(" :black_medium_small_square: ");
                        onSpace = true;
                        break;
                    case '?' :
                        out.append(" :question: ");
                        break;
                    case '!' :
                        out.append(" :exclamation: ");
                        break;
                    case '-' :
                        out.append(" :heavy_minus_sign: ");
                        break;
                    case '#' :
                        out.append(" :hash: ");
                        break;
                    default:
                        out.append(" " + c + " "); /* not known on current dictionary */
                }
            }

            if( (charCounter+= AVG_PER_WORD) > MAX_TOTAL) break;
            else if( ((charPerLineCounter+= AVG_PER_WORD) > MAX_LINE) && onSpace){
                out.append("\n");
                out.append("."); /* icon align purpose */
                charPerLineCounter = 0;
            }
            onSpace = false; /* reset every iteration */
        }
    }

    public static void appendClipboard(Clipboard clpbrd,StringBuilder out){
        clpbrd.setContents(new StringSelection(out.toString()),null);
    }

    public static void main(String args[]){

        Scanner input = new Scanner(System.in);
        Clipboard clpbrd = Toolkit.getDefaultToolkit().getSystemClipboard();
        StringBuilder out = new StringBuilder();
        StringSelection stringSelection;
        String myString;

        /* Terminal input mode */

        System.out.println("Input something");

        while(true){
            myString = input.nextLine();

            out.setLength(0); /* Reset buffer */

            stringSelection = new StringSelection(myString);

            if(myString.equals("end")) return;

            appendTo(myString,out);

            appendClipboard(clpbrd,out);
        }
    }
}
