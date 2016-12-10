package jp.co.humane.morpheme;

import java.io.File;
import java.io.FileNotFoundException;

public class MorphemeEngineFactory
{
	//形態素解析エンジンの種類
	public enum TYPE
	{
		IGO
	}
	
	/**
	 * 形態素解析エンジンのインスタンスを生成する
	 * 
	 * @param	type			形態素解析エンジンの種類
	 * @param	ipadic			ipadicのパス
	 * @return	MorphemeEngine
	 * **/
	public static MorphemeEngine create(TYPE type,String ipadic) throws MorphemeEngineException,FileNotFoundException
	{
		MorphemeEngine engine = null;
		//ipadicのチェック
		int result = 0;
		File file = new File(ipadic);
		if( !file.exists() ){throw new FileNotFoundException(ipadic);}
		switch(type)
		{
		case IGO:
			engine = new IgoEngine();
			break;
		}
		engine.init(ipadic);
		return engine;
	}
}
