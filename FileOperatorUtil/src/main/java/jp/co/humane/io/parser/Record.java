package jp.co.humane.io.parser;

import java.util.HashMap;

public class Record
{
	// 行を構成するフィールド定義
	private HashMap<FieldName,Field> fields = new  HashMap<FieldName,Field>();

	// コンストラクタ
	public Record(){}

	// フィールド定義に従って行を解析する

	public void parse(String line)
	{
		for( FieldName n : fields.keySet() )
		{
			fields.get(n).parse(line);
		}
	}

	// フィールド定義を追加する
	public void addField( Field f )
	{
		fields.put(f.getName(),f);
	}

	// フィールドを取得する

	public Field getField( FieldName n ){ return fields.get(n);}

	/*********************************************************/
	/*********************** getter / setter *****************/
	/*********************************************************/
}
