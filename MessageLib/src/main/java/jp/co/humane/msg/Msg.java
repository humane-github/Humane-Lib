package jp.co.humane.msg;
/*
 * �쐬���F 2005/08/29
 *
 * TODO ���̐������ꂽ�t�@�C���̃e���v���[�g��ύX����ɂ͎����Q�ƁB
 * �E�B���h�E �� �ݒ� �� Java �� �R�[�h�E�X�^�C�� �� �R�[�h�E�e���v���[�g
 */

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.PropertyResourceBundle;

/**
 * ���P�[���𔻒f���āA���b�Z�[�W��Ԃ��N���X
 * @author suzuki
 */
public class Msg
{
	/** ���{�ꃁ�b�Z�[�W�ǂݍ��� **/
	public final static int LANG_JAPANESE = 0;
	/** �p�ꃁ�b�Z�[�W�ǂݍ��� **/
	public final static int LANG_ENGLISH = 1;
	/** �����ꃁ�b�Z�[�W�ǂݍ��� **/
	public final static int LANG_CHINESE = 2;
	/** �ݒ�t�@�C���̌���w��œǂݍ��� **/
	public final static int LANG_AUTO = 3;
	/**
	 * ���{��p���b�Z�[�W�t�@�C���̃t�@�C����
	 * **/
	public final static String JAPANESE_MSGFILE = "japanese.properties";
	
	/**
	 * �p��p���b�Z�[�W�t�@�C���̃t�@�C����
	 * **/
	public final static String ENGLISH_MSGFILE = "english.properties";
	
	/**
	 * ������p���b�Z�[�W�t�@�C���̃t�@�C����
	 * **/
	public final static String CHINESE_MSGFILE = "chinese.properties";
	
	/**
	 * ���b�Z�[�W�ǂݍ��ݗp�o���h���N���X
	 * **/
	private static PropertyResourceBundle _bundle = null;
	
	/**
	 * ���\�[�X�o���h���I�u�W�F�N�g�𐶐�����
	 * 
	 * **/
	private static PropertyResourceBundle getResourceBundle( int lang )
	{
		// OS�̃��P�[�����烁�b�Z�[�W�t�@�C���̃t�@�C�������擾
		
		String msgfilename = JAPANESE_MSGFILE;
		
		/**
		 * ���b�Z�[�W�t�@�C����InputStream���擾
		 * **/
		try
		{
			File msgFile = new File(System.getProperty("user.dir"),msgfilename);
			FileInputStream fis = new FileInputStream(msgFile);
			PropertyResourceBundle property = new PropertyResourceBundle(fis);
			fis.close();
			return property;
		}
		catch( IOException ioe )
		{
			System.out.println("message file:"+System.getProperty("user.dir")+File.separator+msgfilename+" not found...");
		}
			
		return null;
	}
	
	/**
	 * ���b�Z�[�W���擾����<br>
	 * ���b�Z�[�W�L�[�R�[�h�ɑΉ����郁�b�Z�[�W��������Ȃ��ꍇ��
	 *�@�����Ŏw�肵�����b�Z�[�W�L�[�R�[�h��Ԃ��B
	 * 
	 * @param	key		���b�Z�[�W�L�[�R�[�h
	 * @return	String	���b�Z�[�W
	 * **/
	public static String get( String key )
	{
		return get(key,Msg.LANG_AUTO);
	}
	
	/**
	 * ���b�Z�[�W���擾����B<br>
	 * ���b�Z�[�W�L�[�R�[�h�ɑΉ����郁�b�Z�[�W��������Ȃ��ꍇ��
	 * �����Ŏw�肵�����b�Z�[�W�L�[�R�[�h��Ԃ��B
	 * ���P�[���́A�����Ŏw�肳�ꂽ���̂��g�p����
	 * 
	 * @param	key		���b�Z�[�W�L�[�R�[�h
	 * @param  lang	����w��
	 * @return	String	���b�Z�[�W	
	 * **/
	public static String get( String key, int lang )
	{
		String msg = null;
		if( _bundle == null ) _bundle = getResourceBundle(lang);
		
		if( _bundle == null ) return key;
		else msg = (String)_bundle.handleGetObject(key);	
		
		if( msg != null && msg.length() > 0 ) return msg;
		else return key;		
	}
	
	/**
	 * ���b�Z�[�W���擾����B<br>
	 * ���b�Z�[�W�L�[�R�[�h�ɑΉ����郁�b�Z�[�W��������Ȃ��ꍇ��
	 * �����Ŏw�肵�����b�Z�[�W�L�[�R�[�h��Ԃ��B
	 * ���P�[���́A�����Ŏw�肳�ꂽ���̂��g�p����
	 * ���̃��\�b�h�́A�s�x���b�Z�[�W�t�@�C����ǂݍ���ŏ������s���B
	 * ���̂��߁Aget���\�b�h�ɔ�ׂď������x���B
	 * �{���\�b�h�́A���{����œ��삵�Ă���Ƃ��ɁA�p�ꃁ�b�Z�[�W���擾�������ꍇ�ȂǂŎg�p����
	 * 
	 * @param	key		���b�Z�[�W�L�[�R�[�h
	 * @param  lang	����w��
	 * @return	String	���b�Z�[�W	
	 * **/
	public static String getMessageFromFile( String key, int lang )
	{
		String msg = null;
		PropertyResourceBundle bundle = getResourceBundle(lang);
		
		if( bundle == null ) return key;
		else msg = (String)bundle.handleGetObject(key);	
		
		if( msg != null && msg.length() > 0 ) return msg;
		else return key;				
	}
	
	/**
	 * ���b�Z�[�W���擾����B<br>
	 * �擾�������b�Z�[�W�Ƀp�����[�^�[�u�������L�q�q�����ߍ��܂�Ă���ꍇ��<br>
	 * �����Ŏw�肳�ꂽ�p�����[�^�[�z��̒l�ɒu�����������b�Z�[�W��Ԃ��B<br>
	 * �p�����[�^�[�u�������L�q�q�́A{}�ň͂܂ꂽ0�`n�̐��l�Ŏw�肷��B<br>
	 * <br>
	 * ��j<br>
	 * ���b�Z�[�W = ���̃��b�Z�[�W��{0}�j����{1}���񂩂瑗�M����܂����B<br>
	 * �p�����[�^�[= parameteres[0] = ��<br>
	 *               parameteres[1] = ���<br>
	 * <br>
	 * ��L�̏ꍇ�A�Ԃ���郁�b�Z�[�W��<br>
	 * <br>
	 * ���̃��b�Z�[�W�͉Ηj���ɗ�؂��񂩂瑗�M����܂����B<br>
	 * <br>
	 * �ƂȂ�<br>
	 * <br>
	 * @param	key			���b�Z�[�W�L�[�R�[�h
	 * @param	parameters	���b�Z�[�W�ɖ��ߍ��ރp�����[�^�[�z��
	 * @return	String		���b�Z�[�W
	 * **/
    public static String get(String key,Object[]parameters)
    {
        if(parameters==null || parameters.length < 1 )
        {
        	return get(key);
        }
        
        for(int cnt=0;cnt<parameters.length;cnt++)
        {
            if(parameters[cnt]==null)
            {
            	get(key);
            }
            parameters[cnt]=stuffingGetMessageFinalizer(parameters[cnt]+"",true); // DECODING ���̌딻��̉��
        }
        String message;

        message = get(key);
        message=java.text.MessageFormat.format(message,parameters); // ��{�I�� NO-EXCEPTION and NO-ERROR ���H

        return getMessageFinalizer(message);
    }
	
    private static String getMessageFinalizer(String message)
    {
        return stuffingGetMessageFinalizer(message,false);
    }
    
    private static String stuffingGetMessageFinalizer(String message,boolean encoding)
    {
        return stuffing(message,'\\',"S\\"," \\",encoding);
    }
    
    private static String stuffing(String string,char controlChr,String encodedChrs,String decodedChrs,boolean encoding) // ROUTINE
    {
        StringBuffer buffer=new StringBuffer();

        try {

        for(int stringIndex=0;stringIndex<string.length();stringIndex++)
        {
            char chr=string.charAt(stringIndex);

            if(encoding) // ENCODING
            {
                int cnt;

                for(cnt=0;cnt<decodedChrs.length();cnt++)
                    if(decodedChrs.charAt(cnt)==chr)
                        break;

                if(cnt<decodedChrs.length())
                {
                    buffer.append(controlChr);
                    buffer.append(encodedChrs.charAt(cnt));
                }
                else
                {
                    buffer.append(chr);
                }
            }
            else // DECODING
            {
                if(chr==controlChr)
                {
                    int cnt;

                    stringIndex++;

                    if(stringIndex==string.length())
                        throw new Error(string);

                    chr=string.charAt(stringIndex);

                    for(cnt=0;cnt<encodedChrs.length();cnt++)
                        if(encodedChrs.charAt(cnt)==chr)
                            break;

                    if(cnt==encodedChrs.length())
                        throw new Error(string);

                    buffer.append(decodedChrs.charAt(cnt));
                }
                else
                {
                    buffer.append(chr);
                }
            }
        }
        }
        catch(Error e)
        {
            e.printStackTrace();
            throw e;
        }
        return buffer+"";
    }
}
