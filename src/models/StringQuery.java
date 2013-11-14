package models;

public class StringQuery implements IQuery{

	private String[] query;
	private String queryId;
	
	public StringQuery(String[] _query,String _queryId)
	{
		this.query=_query;
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
