package jp.co.humane.morpheme;

import java.util.List;

public abstract class MorphemeEngine
{
	/**
	 * 形態素解析エンジンの初期化
	 * 
	 * @param	ipadic	ipadicのパス
	 * @return	int		0:成功
	 * 					-1:ipadicが存在しない
	 * 					-2:エンジン初期化失敗
	 * **/
	public abstract void init(String ipadic) throws MorphemeEngineException;
	
	/**
	 * 形態素解析を行う
	 * 
	 * @param	text	解析を行う文字列
	 * @return	List<jp.co.humane.igo.Morpheme>	形態素解析の結果
	 * **/
	public abstract List<Morpheme> parse(String text);

}
