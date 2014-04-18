package jp.co.humane.io.parser;

public class Field
{
	// フィールド名
	private FieldName name = null;
	// フィールドの開始位置
	private int startIndex = 0;
	// フィールドの終了位置
	private int endIndex = 0;
	// 値
	private String value = null;

	/**
	 * コンストラクタ
	 *
	 * @param	n	フィールド名
	 * @param	si	フィールドの開始位置（1から始まる番号）
	 * @param	ei	フィールドの終了位置
	 * **/
	public Field( String n,int si, int ei )
	{
		// String.substringメソッドは、0から開始する番号で文字の位置を特定するため
		// ここで-1する
		setStartIndex(--si);
		setEndIndex(ei);
		setName(new FieldName(n));
	}

	// フィールド定義に従って行を解析する
	public void parse( String line )
	{
		// 初期化
		setValue(null);

		// エラーチェック
		if( getStartIndex() < 0 || getEndIndex() < 0 ){return;}
		if( getStartIndex() > getEndIndex() ){return;}
		if( line == null || line.trim().length() < 1 ){return;}

		// 行から指定位置の文字列を抜き出す
		if( getStartIndex() > line.length() )
		{
			setValue(null);
		}
		else if( getEndIndex() > line.length() )
		{
			setValue(line.substring(getStartIndex(), line.length()));
		}
		else
		{
			setValue(line.substring(getStartIndex(), getEndIndex()));
		}
	}

	public int getInt()
	{
		if( getValue() == null || getValue().trim().length() < 1 ){ return 0;}
		else return Integer.parseInt(getValue().trim());
	}

	public String getString() {return getValue();}
	public double getDouble()
	{
		if( getValue() == null || getValue().trim().length() < 1 ){return 0.0;}
		else return Double.parseDouble(getValue());
	}

	/*********************************************************/
	/*********************** getter / setter *****************/
	/*********************************************************/
	public int getStartIndex() {return startIndex;}
	public void setStartIndex(int startIndex) {this.startIndex = startIndex;}
	public int getEndIndex() {return endIndex;}
	public void setEndIndex(int endIndex) {this.endIndex = endIndex;}
	public void setValue(String value) {this.value = value;}
	public String getValue(){ return value;}
	public FieldName getName() {return name;}
	public void setName(FieldName name) {this.name = name;}


}
