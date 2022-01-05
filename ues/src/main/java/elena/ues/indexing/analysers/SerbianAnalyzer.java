package elena.ues.indexing.analysers;

import org.apache.lucene.analysis.Analyzer.TokenStreamComponents;
import org.apache.lucene.analysis.LowerCaseFilter;
import org.apache.lucene.analysis.StopFilter;
import org.apache.lucene.analysis.TokenStream;
import org.apache.lucene.analysis.Tokenizer;
import org.apache.lucene.analysis.standard.StandardTokenizer;

import elena.ues.indexing.filters.CyrilicToLatinFilter;

public class SerbianAnalyzer {


	 /**
    * An array containing some common English words
    * that are usually not useful for searching.
    */
   public static final String[] STOP_WORDS =
           {
                   "i","a","ili","ali","pa","te","da","u","po","na"
           };

   /**
    * Builds an analyzer.
    */
   public SerbianAnalyzer()
   {
   }


   protected TokenStreamComponents createComponents(String arg0) {
       Tokenizer source = new StandardTokenizer();
       TokenStream result = new CyrilicToLatinFilter(source);
       result = new LowerCaseFilter(result);
       result = new StopFilter(result,StopFilter.makeStopSet(STOP_WORDS));
       return new TokenStreamComponents(source, result);
   }
}
