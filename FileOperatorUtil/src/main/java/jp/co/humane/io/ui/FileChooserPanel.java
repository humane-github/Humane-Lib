package jp.co.humane.io.ui;

import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.filechooser.FileFilter;

public class FileChooserPanel extends JPanel implements ActionListener
{
	private static String DEFAULT_FILENAME = "default.txt";
	private JTextField txtPath = new JTextField(50);
	private JButton btnSelectDir = new JButton("...");
	private FileFilter filter = null;
	private String title = null;
	private File defaultPath = null;
	private String defaultFilename = null;

	private FileSelectedListener listener = null;

	/**
	 * コンストラクタ
	 *
	 * @param title
	 * @param filter
	 * @throws IOException
	 */
	public FileChooserPanel(String title,FileFilter filter) throws IOException
	{
		setFilter(filter);
		setTitle(title);
		constract();
	}

	/**
	 * コンストラクタ
	 *
	 * @param title
	 * @param filter
	 * @param defaultPath
	 * @param defaultFilename
	 * @throws IOException
	 */
	public FileChooserPanel(String title,FileFilter filter,File defaultPath,String defaultFilename) throws IOException
	{
		setFilter(filter);
		setTitle(title);

		setDefaultPath(defaultPath);
		setDefaultFilename(defaultFilename);
		constract();
	}

	private void constract() throws IOException
	{
		setLayout(new FlowLayout(FlowLayout.LEFT));
		txtPath.setBackground(getBackground());
		txtPath.setEnabled(false);
		add(new JLabel(getTitle()));
		add(txtPath);
		add(btnSelectDir);

		btnSelectDir.addActionListener(this);

		// デフォルトパスが存在しない場合は作る
		if( getDefaultPath() != null && !getDefaultPath().exists() )
		{
			getDefaultPath().mkdir();
		}

		// 初期パスを設定する
		if(getDefaultPath() != null && getDefaultFilename() != null )
		{
			txtPath.setText(getDefaultPath().getAbsolutePath() + File.separator + getDefaultFilename());
		}
	}

	public void addCallbackListener(FileSelectedListener l)
	{
		listener = l;
	}

	public String getSelectedPath()
	{
		return txtPath.getText();
	}

	public void actionPerformed(ActionEvent e)
	{
		if( e.getSource() == btnSelectDir )
		{
			showFileChooser();
		}

		if( listener != null ){listener.selected(new FileSelectedEvent(new File(txtPath.getText())));}
	}

	private void showFileChooser()
	{
		JFileChooser chooser = new JFileChooser(getDefaultPath());
		if( getFilter() != null ){chooser.setFileFilter(getFilter());}
		chooser.setAcceptAllFileFilterUsed(false);

		// 初期ファイル名を設定
		if( getDefaultPath() == null )
		{
			setDefaultPath(new File(System.getProperty("user.dir")));
		}

		File defFilename = new File(getDefaultPath().getAbsolutePath()+File.separator + getDefaultFilename());
		chooser.setSelectedFile(defFilename);
		int returnVal = chooser.showOpenDialog(this);
		if(returnVal == JFileChooser.APPROVE_OPTION)
		{
			txtPath.setText(chooser.getSelectedFile().getAbsolutePath());
		}
	}

	public void setEnable( boolean flg )
	{
		btnSelectDir.setEnabled(flg);
	}

	public FileFilter getFilter() {return filter;}
	public void setFilter(FileFilter filter) {this.filter = filter;}
	public String getTitle() {return title;}
	public void setTitle(String title) {this.title = title;}
	public File getDefaultPath() {return defaultPath;}
	public void setDefaultPath(File defaultPath) {this.defaultPath = defaultPath;}
	public String getDefaultFilename() {return defaultFilename;}
	public void setDefaultFilename(String defaultFilename) {this.defaultFilename = defaultFilename;}

}

