package jp.co.humane.io.ui;

import java.io.File;

public class FileSelectedEvent
{
	private File selectedFile = null;

	public FileSelectedEvent(File f)
	{
		setSelectedFile(f);
	}

	public File getSelectedFile() {
		return selectedFile;
	}

	public void setSelectedFile(File selectedFile) {
		this.selectedFile = selectedFile;
	}


}
