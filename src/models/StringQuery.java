package models;

import preprocess.Preprocessor;
import preprocess.Tokenizer;

public class StringQuery implements IQuery{

	private String[] query;
	private String queryId;
	
	public StringQuery(String _query,String _queryId)
	{
        Preprocessor pp = new Preprocessor();
        String [] tempQuery = Tokenizer.tokenizeQuery(_query);
		this.query= pp.stem(tempQuery);
		this.queryId=_queryId;
	}
	@Override
	public String[] getQuery() {
		return this.query;
	}
	@Override
	public String getQueryID() {
		return this.queryId;
	}

}
