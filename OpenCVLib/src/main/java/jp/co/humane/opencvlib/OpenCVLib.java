package jp.co.humane.opencvlib;

import org.opencv.core.Core;

public class OpenCVLib
{
	/**
	 * OpenCVÇÃDLLÇÉçÅ[ÉhÇ∑ÇÈ
	 * **/
	public static void LoadDLL()
	{
    	System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
	}
}
