package jp.co.humane.morpheme;

import java.util.List;

public abstract class MorphemeEngine
{
	/**
	 * �`�ԑf��̓G���W���̏�����
	 * 
	 * @param	ipadic	ipadic�̃p�X
	 * @return	int		0:����
	 * 					-1:ipadic�����݂��Ȃ�
	 * 					-2:�G���W�����������s
	 * **/
	public abstract void init(String ipadic) throws MorphemeEngineException;
	
	/**
	 * �`�ԑf��͂��s��
	 * 
	 * @param	text	��͂��s��������
	 * @return	List<jp.co.humane.igo.Morpheme>	�`�ԑf��͂̌���
	 * **/
	public abstract List<Morpheme> parse(String text);

}
