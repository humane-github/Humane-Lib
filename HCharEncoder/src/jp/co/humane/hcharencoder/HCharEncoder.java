package jp.co.humane.hcharencoder;

import java.io.UnsupportedEncodingException;
import java.nio.ByteBuffer;
import java.util.ArrayList;

public class HCharEncoder
{
	/**
	 * ���l���o�C�g�z��ɕϊ�����
	 * **/
	public static byte[] int2byte(int value)
	{
		byte[] byteArray = new byte[4];
		ByteBuffer.wrap(byteArray).putInt(value);
		return byteArray;
	}
	public static String byte2Str(byte[] bytes,String encoding)
	{
		try {
			return new String(bytes,encoding);
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return "";
		}
	}
	/**
	 * �����R�[�h���擾����
	 * 
	 * @param	�����R�[�h���擾���镶����
	 * @return	�����R�[�h
	 * **/
	public static String[] toCharCode(String str,String encoding,boolean bom)
	{
		byte[] bytes = null;
		String[] result = null;
		if( str == null ){return new String[]{};}
		try
		{
			ArrayList<String> codes = new ArrayList<String>();
			int bytenum = 0;
			String code = "";
			bytes = str.getBytes(encoding);
			for( int idx = 0; idx < bytes.length; idx++ )
			{
				//BOM�t�̏ꍇ�͐擪��BOM�𖳎�����
				if( bom && (idx == 0 || idx == 1)){continue;}
				code += Integer.toHexString((bytes[idx] & 0xF0) >> 4);
				code += Integer.toHexString(bytes[idx] & 0xF);
				bytenum++;
				if( bytenum > 1 )
				{
					codes.add(code);
					bytenum = 0;
					code = "";
				}
			}
			
			result = new String[codes.size()];
			result = (String[])codes.toArray(result);
		}
		catch (UnsupportedEncodingException e)
		{
			e.printStackTrace();
		}
		return result;
	}
		
	/**
	 * �����R�[�h���當����ɕϊ�����
	 * 
	 * @param	charcode	�����R�[�h
	 * @return	String
	 * **/
	public static String toString(String[] charcode)
	{
		String result = "";
		for( String tcode : charcode)
		{
			char c = (char) Integer.parseInt(tcode, 16);
			result += c;
		}
		return result;
	}
	
	public static void main(String[] args)
	{
		String[] ch = HCharEncoder.toCharCode("���", "utf-16", true);
		String val = "";
		for( String c : ch )
		{
			val += c;
		}
		StringBuffer buff = new StringBuffer();
		ArrayList<String> recvdata = new ArrayList<String>();		
		for( int i=0; i<val.length();i+=4)
		{
			String token = val.substring(i,i+4);
			buff.append(token.charAt(2)).append(token.charAt(3)).append(token.charAt(0)).append(token.charAt(1));
			recvdata.add(buff.toString());
			buff.delete(0, buff.length());
		}
		String[] recvdataArray = new String[recvdata.size()];
		recvdataArray = (String[])recvdata.toArray(recvdataArray);
		
		for(String s:recvdataArray)
		{
			System.out.println(s);
		}
	}
}
