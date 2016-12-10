package jp.co.humane.morpheme;

/**
 * 形態素
 * **/
public class Morpheme
{
	private String m_feature = null;
	private int m_start = 0;
	private String m_surface = null;
	public String Feature(){return m_feature;}
	public String[] Features()
	{
		if( Feature() == null ){return new String[]{};}
		return Feature().split(",");		
	}
	
	public int Start(){return m_start;}
	public String Surface(){return m_surface;}
	
	/**
	 * コンストラクタ
	 * **/
	public Morpheme(String feature,int start,String surface)
	{
		m_feature = feature;
		m_start = start;
		m_surface = surface;
	}	
}
