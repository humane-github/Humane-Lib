package jp.co.humane.morpheme;

import java.io.File;
import java.io.FileNotFoundException;

public class MorphemeEngineFactory
{
	//�`�ԑf��̓G���W���̎��
	public enum TYPE
	{
		IGO
	}
	
	/**
	 * �`�ԑf��̓G���W���̃C���X�^���X�𐶐�����
	 * 
	 * @param	type			�`�ԑf��̓G���W���̎��
	 * @param	ipadic			ipadic�̃p�X
	 * @return	MorphemeEngine
	 * **/
	public static MorphemeEngine create(TYPE type,String ipadic) throws MorphemeEngineException,FileNotFoundException
	{
		MorphemeEngine engine = null;
		//ipadic�̃`�F�b�N
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
