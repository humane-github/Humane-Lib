package jp.co.humane.opencvlib;

import java.util.HashMap;

import jp.co.humane.opencvlib.exception.CascadeClassifierException;

import org.opencv.core.Mat;
import org.opencv.core.MatOfRect;
import org.opencv.core.Size;
import org.opencv.objdetect.CascadeClassifier;
import org.opencv.objdetect.Objdetect;

public class CascadeFaceDetector
{
	private HashMap<String,CascadeClassifier> cascadeMap = null;
	
	/**
	 * �R���X�g���N�^
	 * **/
	public CascadeFaceDetector(){}
	
	/**
	 * Cascade���쐬����
	 * 
	 * @param name	����
	 * @param path	Cascade�t�@�C���̐�΃p�X
	 * **/
	public void addCascade(String name,String path) throws CascadeClassifierException
	{
		if( name == null || name.length() < 1 ){return;}
		if( path == null || path.length() < 1 ){return;}		
		if( cascadeMap == null ){cascadeMap = new HashMap<String,CascadeClassifier>();}
		
		CascadeClassifier cascade = new CascadeClassifier();
		if( !cascade.load(path) ){throw new CascadeClassifierException(path+" load error", null);}
		else
		{
			cascadeMap.put(name, cascade);
		}
	}

	/**
	 * �w�肵���摜������猟�o���s��
	 * 
	 * @param	targetMat	�����Ώۂ̉摜�iMat�`���j
	 * @return	int			���o������̐�
	 * **/
	public int detect(Mat targetMat)
	{
		for( String name : cascadeMap.keySet() )
		{
			int faces = workDetect(name,targetMat);
			if( faces > 0 ){return faces;}
		}
		return 0;
	}
	
	private int workDetect(String name,Mat targetMat)
	{
		MatOfRect faces = new MatOfRect();
		CascadeClassifier cascade = cascadeMap.get(name);
		cascade.detectMultiScale(targetMat, faces, 1.1, 2,Objdetect.CASCADE_SCALE_IMAGE , new Size(30,30),new Size());
		return faces.toArray().length;
	}
}
