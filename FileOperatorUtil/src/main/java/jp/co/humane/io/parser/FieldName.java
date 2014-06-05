package jp.co.humane.io.parser;

// フィールドにつける名称を表す型
public final class FieldName
{
	private final String name;

	// コンストラクタ
	public FieldName( String n )
	{
		name = n;
	}

	private String getName(){return name;}

	@Override
	public boolean equals(Object obj)
	{
		if( obj instanceof FieldName )
		{
			return ((FieldName)obj).getName().equals(getName());
		}
		else
		{
			return super.equals(obj);
		}
	}

	@Override
	public int hashCode()
	{
		return name.hashCode();
	}

}
