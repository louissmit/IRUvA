/**
 * 
 */
package models;

/**
 * @author hubert
 * This interface is needed in case we develop boolean queries (in that case it is better to pass
 * iQuery object instead of string[]
 */
public interface IQuery {
	String[] getQuery();
	String getQueryID();

}
