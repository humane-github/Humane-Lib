package jp.co.humane.opencvlib;

import java.awt.FlowLayout;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferByte;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;

import org.opencv.core.Mat;

/**
 * Matイメージのビューア。
 * {@lin khttp://answers.opencv.org/question/31505/how-load-and-display-images-with-java-using-opencv-solved/}
 *
 * 使用方法：<br/>
 * <code>
 *   MatViewer viewer = new MatViewer("タイトル");
 *   while(true) {
 *      Thread.sleep(100);
 *      Mat mat = getMatData();
 *      viewer.updateImage(mat);
 *   }
 * </code>
 *
 * @author terada
 *
 */
public class MatViewer {

    /** イメージ */
    private ImageIcon imageIcon = null;

    /** フレーム */
    private JFrame frame = null;

    /** ラベル */
    private JLabel label = null;

    /** タイトル */
    private String title = "MatViewer";

    /**
     * コンストラクタ。
     * @param title タイトル。
     */
    public MatViewer(String title) {
        this.title = title;
    }

    /**
     * MatデータをImage形式に変換する。
     * @param mat Matデータ。
     * @return イメージデータ。
     */
    private BufferedImage Mat2BufferedImage(Mat mat) {

        // イメージ形式を特定
        int type = BufferedImage.TYPE_BYTE_GRAY;
        if ( mat.channels() > 1 ) {
            type = BufferedImage.TYPE_3BYTE_BGR;
        }

        // Matのデータをバイト配列で取得
        int bufferSize = mat.channels() * mat.cols() * mat.rows();
        byte [] b = new byte[bufferSize];
        mat.get(0, 0, b);

        // イメージのバッファデータにMatのデータをコピー
        BufferedImage image = new BufferedImage(mat.cols(), mat.rows(), type);
        final byte[] targetPixels = ((DataBufferByte) image.getRaster().getDataBuffer()).getData();
        System.arraycopy(b, 0, targetPixels, 0, b.length);

        return image;

    }

    /**
     * Matイメージを画面に表示する。
     * @param mat Matイメージ。
     */
    public void updateImage(Mat mat) {

        // Matデータをイメージに変換
        Image image = Mat2BufferedImage(mat);

        // フレーム作成済みの場合はイメージの更新のみを行う
        if (null != imageIcon) {
            imageIcon.setImage(image);
            label.repaint();
            return;
        }

        // フレーム未作成の場合はフレームの作成を行う
        imageIcon = new ImageIcon(image);
        frame = new JFrame(title);
        frame.setLayout(new FlowLayout());
        frame.setSize(image.getWidth(null) + 50, image.getHeight(null) + 50);
        label = new JLabel();
        label.setIcon(imageIcon);
        frame.add(label);
        frame.setVisible(true);
        frame.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
    }

    /**
     * 画面を削除する。
     */
    public void release() {
        if (null != frame) {
            label = null;
            frame.dispose();
            frame = null;
        }
    }

}
