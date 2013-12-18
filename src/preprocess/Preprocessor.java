/**
 * 
 */
package preprocess;

import java.text.Normalizer;

import org.tartarus.snowball.SnowballStemmer;
import org.tartarus.snowball.ext.englishStemmer;

/**
 * @author louissmit
 *
 */
public class Preprocessor {

    private SnowballStemmer stemmer;

    public Preprocessor(){
        this.stemmer = new englishStemmer();
    }

    public String[] stem(String[] strings) {
        String[] result = new String[strings.length];
        int i = 0;
        for(String s: strings) {
        	s = Normalizer.normalize(s, Normalizer.Form.NFD); 
	    	s = s.replaceAll("[^\\p{ASCII}]", "");//removing diatrics
	        s= s.toLowerCase();
            result[i] = this.stem(s);
            i++;
        }
        return result;
    }

    public String stem(String string) {
        stemmer.setCurrent(string);
        stemmer.stem();
        return stemmer.getCurrent();
    }

}
