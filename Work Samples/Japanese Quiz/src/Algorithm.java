import java.util.*;
import java.io.*;

/*
    @author Dr. Newell
    @class Honors Capstone Project
*/

public class Algorithm {

    protected static String [ ] taglines, levellines, typelines;
    protected static double [ ] tagperc, levelperc, typeperc;
    protected static String [ ]  lines;
    protected static String [ ] fields;
    protected static double [ ] percs;
    protected static double  totaldiff;
    protected static double  totalperc;
    protected static double  totalres;
    protected static int [ ] counts;
    protected static String filein;
    protected static String fileout;
    protected static String firstline;

    public static void main ( String[] args ) throws FileNotFoundException   {

        filein = "results.txt";

        // open file and create scanner
        File f = new File(filein);
        Scanner inf = new Scanner( f);

        // read the 3 integers from first line of data file
        firstline = inf.nextLine( );
        String [ ]  cnt = firstline.split("\\s+");   // split it into 3 strings one for each count
        counts = new int [ cnt.length ];   // parse Strings into integers
        for (int x =0; x < cnt.length; x++)
           counts[x] = Integer.parseInt(cnt[x]);

        // set up arrays for field names and eventual percentages
        fields = new String [ counts[0]+counts[1]+counts[2]];
        percs = new double [ counts[0]+counts[1]+counts[2]];

        // now cnt array contains the count for tags, levels, types  now can read data into a big array of Strings
        taglines =  new String [ counts[0]] ;    // lines for each tag/topic
        levellines = new String [ counts[1]];    // lines for each level results
        typelines = new String [ counts[2]];   // lines for each question type results
        lines = new String [ counts[0] + counts[1] + counts[2]];  // holds all lines

        for (int x = 0; x < counts[0]+counts[1]+counts[2]; x++)     //  read file line by line
            lines[x] = inf.nextLine( );

        // now load tags, levels and types
        //int[] slicedArray = Arrays.copyOfRange(array, startIndex, endIndex);
        // get the 3 categories of lines
        taglines = Arrays.copyOfRange(lines, 0, counts[0]);
        levellines = Arrays.copyOfRange(lines,counts[0],counts[0]+counts[1]);
        typelines = Arrays.copyOfRange(lines, counts[0]+counts[1], counts[0]+counts[1]+counts[2]);

        // now all three categories are stored in the String arrays - now we can get the actual data results percentages
        totaldiff = getdifficulty( );
        System.out.println("Total difficuly of given quiz was : " + totaldiff);
        getdifficultyresults( );
        //System.out.println("Resulting percentage was  :  " + totalres);

        getpercents(  );  // calculate the percentages
        testpercs(  );

        genquiz( );   // pass the percentages in for generation of "quiz"  file
    }  // end main method

    public static double getdifficulty( ) {
        double T = 0.0;

        for ( int x =0; x < levellines.length; x++ ) {
            String [ ] vals = levellines[x].split("\\s+");
            T = T + (double) ( (x+1) * Integer.parseInt(vals[1]) );
        }

        return T;
    }

    public static void getdifficultyresults( ) {
        double T = 0.0;
        int correctcnt = 0;
        int qcount = 0;

        for (String levelline : levellines) {
            String[] vals = levelline.split("\\s+");
            correctcnt += Integer.parseInt(vals[2]);
            qcount += Integer.parseInt(vals[1]);
            //   if ( Integer.parseInt(vals[1]) != 0)
            //    T = T + (double) ( ((x+1) * Integer.parseInt(vals[1]) ) * (double) ( (double)Integer.parseInt(vals[2]) / (double) Integer.parseInt(vals[1])) );
        }

        System.out.println("Overall performance (percentage of correct responses) was : %" + (correctcnt/(double)qcount)* 100.0);
    }


    public static void  genquiz( ) {
        // this code will produce output file or perhaps just the values to be used to produce output file
        // calculate their avg difficulty level and produce an approximation of that

        // prepare file for output
        fileout = "numbers.txt";
        PrintWriter pw;

        try {
            pw = new PrintWriter(fileout);
            // calculate avg difficulty they can solve
            double p = 0.0;
            int  sumlevels = 0;
            int qcount = 0;
            int y = 1;
            for (int x =counts[0]; x < counts[0]+counts[1]; x++ ) {
                String [ ] vals = lines[x].split("\\s+");
                sumlevels += Integer.parseInt(vals[1]) * y ;
                qcount += Integer.parseInt(vals[1]);
                p = p + percs[x];
                y++;
            }
            double avg =  (p /  (double) (counts[1])) * counts[1];
            double actualdiff = (sumlevels / (double)y);

            System.out.println("Average performance difficulty level of questions solved correctly was " + avg);
            System.out.println("ACTUAL average difficulty level of quiz questions was " + actualdiff / qcount * counts[1]);

            // round off avg
            int intavg = (int) (avg + 0.5);

            // now calculate actual difficulty level of the questions asked

            // first shot just walk down topics and use percentages to determine which ones are needed
            int c = 0;
            for (int x = 0; x < counts[0]; x++) {
                if (percs[x] < 0.7 ) {
                  c++;
                }
            }
            System.out.println("Number of Topics scoring less than acceptable (less than 70%) was : " + c);

            // now going with approx 100 difficulty we need to take average difficulty and do about 70%
            // divide 100 by intavg and round off for number of questions
            int numquestions = (int)  (( 100/(double)intavg ) + 0.5);

            System.out.println("Total questions to be produced in next quiz is " + numquestions + " at approximately difficulty level of " + avg);

            if (c == 0) c++;    // avoid dividing by 0
            int pertopic = (int)((numquestions / c) * 0.85);  // num per bad topic

            // now figure out the random questions to add
            //
            int diff = numquestions - pertopic * c;

            int numleft = counts[0] - c;

            for (int x = 0; x < counts[0]; x++) {
                if (percs[x] < 0.7 ) {
                    pw.println(fields[x] + "\t" + pertopic);
                }
                else {
                    if (diff == 0)
                        pw.println(fields[x] + "\t" + 0);
                    else if (numleft > 1) {
                        pw.println(fields[x] + "\t" + (diff / (counts[0]-c)));
                        diff = diff - (diff / ( counts[0] - c));
                    }
                    else {
                        pw.println(fields[x] + "\t" + diff );
                        diff = 0;
                    }
                    numleft--;
                }
            }

            //  now need to handle diff level question counts
            // do same type of thing for these but do like 65% of approx level and 35% of the others
            int numkeylevelquests =  (int) (0.65 * numquestions);
            int extra = numquestions - (numkeylevelquests);
            int dec = extra / (counts[1]-1);

            c = 1;
            for (int x = counts[0]; x < counts[0]+counts[1]; x++) {
                if (  c == intavg) {
                    pw.println(fields[x] + "\t" + numkeylevelquests);
                }
                else if ( c == counts[1] ){
                    pw.println(fields[x] + "\t" + extra );
                }
                else  {
                    pw.println(fields[x] + "\t" + dec );
                    extra = extra - dec;
                }
                c++;
            }

            // now do the types of questions - do it by using percentages time # of questions and toss on extra for final type
            // OK - so add up total error, calculate percentage of error for each type then split up total questions by that percentage

            numleft = numquestions;
            double totalerror = 0.0;
            c = 1;

            for (int x = counts[0]+counts[1]; x < counts[0]+counts[1] + counts[2]; x++ ) {
                totalerror += 1.0 - percs[x];
            }

            for (int x = counts[0]+counts[1]; x < counts[0]+counts[1] + counts[2]; x++ ) {
                if ( c == counts[2])
                    pw.println(fields[x] + "\t" + numleft);
                else {
                    pw.println(fields[x] + "\t" + (int) (numquestions * ((1.0 -percs[x])/ totalerror)));
                    numleft = numleft - (int)(numquestions * ((1.0 -percs[x])/totalerror));
                }
                c++;
            }

            pw.close( );  // remember to close printwriter
        }  catch (IOException ex) {  System.out.println("IO Exception for printwriter");}

    }

    public static void  getpercents( ) {
        for ( int x = 0; x < lines.length; x++ ) {
            String L = lines[x];
            String [ ] vals = L.split("\\s+");
            fields[x] = vals[0];
            if (  Integer.parseInt( vals[1]) != 0 )
               percs[x] = (  (Integer.parseInt(vals[2])) / (double) ( Integer.parseInt(vals[1]))  );
            else
               percs[x] = 1.0;

            totalperc = totalperc + percs[x];
        }
    }


    public static void testpercs( ) {
        for ( int x =0; x < fields.length; x++)
             System.out.println( fields[x] + "\t" + percs[x]);
    }

}   // end class stuff