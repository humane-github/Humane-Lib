package jp.co.humane.morpheme;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import net.reduls.igo.Morpheme;
import net.reduls.igo.Tagger;

public class IgoEngine extends MorphemeEngine
{

	public Tagger m_tagger = null;
	
	/**
	 * コンストラクタ
	 * **/
	public IgoEngine()
	{		
	}
	
	/**
	 * 形態素解析エンジンの初期化
	 * 
	 * @param	ipadic	ipadicのパス
	 * **/
	public void init(String ipadic) throws MorphemeEngineException
	{	
		try
		{
			m_tagger = new Tagger(ipadic);			
		}
		catch( IOException ex )
		{
			ex.printStackTrace();
			throw new MorphemeEngineException(ex.getMessage());
		}
	}
	
	/**
	 * 形態素解析を行う
	 * 
	 * @param	text	解析を行う文字列
	 * @return	List<jp.co.humane.igo.Morpheme>	形態素解析の結果
	 * **/
	public List<jp.co.humane.morpheme.Morpheme> parse(String text)
	{
		ArrayList<jp.co.humane.morpheme.Morpheme> result = new ArrayList<jp.co.humane.morpheme.Morpheme>();
		if( m_tagger == null ){return null;}
		List<Morpheme> tokens = m_tagger.parse(text);
		for( Morpheme token : tokens )
		{
			result.add(new jp.co.humane.morpheme.Morpheme(token.feature, token.start, token.surface));
		}
		return result;
	}
	
	/**
	 * @param args
	 */
	public static void main(String[] args)
	{
		try
		{
			MorphemeEngine engine = MorphemeEngineFactory.create(MorphemeEngineFactory.TYPE.IGO, "C:\\DEV\\05.Igo\\ipadic-shiftjis");		
			List<jp.co.humane.morpheme.Morpheme> result = engine.parse("こんにちは。鈴木好和さんおねがいします。");
			for( jp.co.humane.morpheme.Morpheme token : result )
			{
				System.out.println(String.format("%s|%d|%s", token.Feature(),token.Start(),token.Surface()));
			}			
		}
		catch( FileNotFoundException e1 )
		{
			System.out.println("ipadic not found.");
		}
		catch( MorphemeEngineException e2 )
		{
			System.out.println(e2.getMessage());
		}
	}

}
