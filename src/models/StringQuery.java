package models;

public class StringQuery implements IQuery{

	private String[] query;
	
	public StringQuery(String[] _query)
	{
		this.query=_query;
	}
	@Override
	public String[] getQuery() {
		return this.query;
	}

}
