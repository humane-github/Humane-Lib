package jp.co.humane.rtc.common.starter;

import java.lang.reflect.Field;

import jp.go.aist.rtm.RTC.Manager;
import jp.go.aist.rtm.RTC.util.Properties;

/**
 * RTCの設定情報を生成するファクトリクラス。
 * @author terada.
 *
 */
public class DefaultRtcConfFactory implements RtcConfFactory {

    /** 設定情報が定義されているクラス */
    private Class<?> clazz = null;

    /** 配列が定義されているフィールド名 */
    private String fieldName = "component_conf";

    /**
     * コンストラクタ。
     * @param clazz 設定情報が定義されているクラス。
     */
    public DefaultRtcConfFactory(Class<?> clazz) {
        super();
        this.clazz = clazz;
    }

    /**
     * コンストラクタ。
     * @param clazz     設定情報が定義されているクラス。
     * @param fieldName 設定情報が定義されているフィールド名。
     */
    public DefaultRtcConfFactory(Class<?> clazz, String fieldName) {
        super();
        this.clazz = clazz;
        this.fieldName = fieldName;
    }

    /**
     * 設定情報がstaticなフィールドに定義されているものとして生成処理を行う。
     * @param manager RTCマネージャ。
     * @return 設定情報。
     */
    @Override
    public Properties create(Manager manager) {

        String[] confArr = new String[]{};
        try {
            Field field = clazz.getField(fieldName);
            confArr = (String[]) field.get(null);
        } catch (Exception ex) {
            ex.printStackTrace();
            System.exit(1);
        }

        return new Properties(confArr);
    }


}
