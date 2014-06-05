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
	 * �R���X�g���N�^
	 * **/
	public IgoEngine()
	{		
	}
	
	/**
	 * �`�ԑf��̓G���W���̏�����
	 * 
	 * @param	ipadic	ipadic�̃p�X
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
	 * �`�ԑf��͂��s��
	 * 
	 * @param	text	��͂��s��������
	 * @return	List<jp.co.humane.igo.Morpheme>	�`�ԑf��͂̌���
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
			List<jp.co.humane.morpheme.Morpheme> result = engine.parse("����ɂ��́B��؍D�a���񂨂˂������܂��B");
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
