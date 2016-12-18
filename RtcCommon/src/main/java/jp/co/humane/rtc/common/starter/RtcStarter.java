package jp.co.humane.rtc.common.starter;
import jp.go.aist.rtm.RTC.Manager;
import jp.go.aist.rtm.RTC.ModuleInitProc;
import jp.go.aist.rtm.RTC.RTObject_impl;
import jp.go.aist.rtm.RTC.util.Properties;

/**
 * RTC開始クラス。
 * @author terada
 *
 */
public class RtcStarter {

    /** RTCマネージャ */
    protected Manager manager = null;

    /** RTC起動処理のファクトリクラス */
    protected RtcNewFuncFactory newFuncFactory = null;

    /** RTC終了処理のファクトリクラス */
    protected RtcDeleteFuncFactory deleteFuncFactory = null;

    /** RTCプロパティ生成処理のファクトリクラス */
    protected RtcConfFactory confFactory = null;

    /** コンポーネント名 */
    protected String componentName = null;

    /** ブロッキングモード */
    protected boolean isBlocking = true;

    /**
     * デフォルトコンストラクタ。
     * init以外で生成しないようにprotectedで定義。
     */
    protected RtcStarter() {
        super();
    }

    /**
     * RtcStarterの初期化処理。
     * @param args 起動引数。
     * @return インスタンス。
     */
    public static RtcStarter init(String[] args) {
        RtcStarter rtcStarter = new RtcStarter();
        rtcStarter.setManager(Manager.init(args));
        return rtcStarter;
    }

    /**
     * RTCを起動する。
     * @param args  起動引数。
     * @param clazz 起動対象のRTCクラス。
     */
    public void start(final Class<? extends RTObject_impl> clazz) {

        // 各種ファクトリクラスを取得
        final RtcNewFuncFactory newFactory = getNewFuncFactory(clazz);
        final RtcDeleteFuncFactory delFancFactory = getDeleteFuncFactory(clazz);
        final RtcConfFactory cfgFactory = getConfFactory(clazz);

        // モジュール初期化処理設定する
        manager.setModuleInitProc(new ModuleInitProc() {

            @Override
            public void myModuleInit(Manager mgr) {

                // 設定情報を格納
                Properties prop = cfgFactory.create(mgr);
                mgr.registerFactory(prop, newFactory.create(mgr), delFancFactory.create(mgr));

                // Create a component
                String name = getComponentName(clazz);
                RTObject_impl comp = mgr.createComponent(name);
                if( comp==null ) {
                    System.err.println("Component(" + name + ") create failed.");
                    System.exit(0);
                }
            }
        });

        // マネージャをアクティブにしてコンポーネントをネーミングサービスに登録
        manager.activateManager();

        // マネージャを起動
        manager.runManager(!isBlocking);
    }

    /**
     * RTC生成ファクトリクラスを返す。
     * @param 起動対象のRTCクラス。
     * @return RTC生成ファクトリクラス。
     */
    protected RtcNewFuncFactory getNewFuncFactory(Class<? extends RTObject_impl> clazz) {

        // 指定されていない場合はデフォルトのファクトリクラスを返す
        RtcNewFuncFactory ret = newFuncFactory;
        if (null == ret) {
            ret = new DefaultRtcNewFuncFactory(clazz);
        }
        return ret;
    }

    /**
     * RTC終了ファクトリクラスを返す。
     * @param 終了対象のRTCクラス。
     * @return RTC生成ファクトリクラス。
     */
    protected RtcDeleteFuncFactory getDeleteFuncFactory(Class<? extends RTObject_impl> clazz) {

        // 指定されていない場合はデフォルトのファクトリクラスを返す
        RtcDeleteFuncFactory ret = deleteFuncFactory;
        if (null == ret) {
            ret = new DefaultRtcDeleteFuncFactory();
        }
        return ret;
    }

    /**
     * RTC設定情報のファクトリクラスを返す。
     * @param 設定情報が定義されたRTCクラス。
     * @return RTC設定情報のファクトリクラス。
     */
    protected RtcConfFactory getConfFactory(Class<?> clazz) {

        // 指定されていない場合はデフォルトのファクトリクラスを返す
        RtcConfFactory ret = confFactory;
        if (null == ret) {
            ret = new DefaultRtcConfFactory(clazz);
        }
        return ret;
    }

    /**
     * コンポーネント名を返す。
     * @param  設定情報が定義されたRTCクラス。
     * @return コンポーネント名。
     */
    protected String getComponentName(Class<?> clazz) {

        // 指定されていない場合はクラス名(Implは除く)を返す
        String ret = componentName;
        if (null == ret) {
            String[] arr = clazz.getCanonicalName().split("\\.");
            ret = arr[arr.length - 1];
            ret = ret.replace("Impl", "");
        }
        return ret;
    }

    // 以下、アクセッサ

    /**
     * RTCマネージャを取得する。
     * @return RTCマネージャ。
     */
    public Manager getManager() {
        return manager;
    }


    /**
     * RTCマネージャを設定する。
     * @param manager RTCマネージャ。
     */
    protected void setManager(Manager manager) {
        this.manager = manager;
    }

    /**
     * RTC起動処理のファクトリクラスを取得する。
     * @return RTC起動処理のファクトリクラス。
     */
    public RtcNewFuncFactory getNewFuncFactory() {
        return newFuncFactory;
    }

    /**
     * RTC起動処理のファクトリクラスを設定する。
     * @param newFuncFactory RTC起動処理のファクトリクラス。
     */
    public void setNewFuncFactory(RtcNewFuncFactory newFuncFactory) {
        this.newFuncFactory = newFuncFactory;
    }

    /**
     * RTC終了処理のファクトリクラスを取得する。
     * @return RTC終了処理のファクトリクラス。
     */
    public RtcDeleteFuncFactory getDeleteFuncFactory() {
        return deleteFuncFactory;
    }

    /**
     * RTC終了処理のファクトリクラスを設定する。
     * @param deleteFuncFactory RTC終了処理のファクトリクラス。
     */
    public void setDeleteFuncFactory(RtcDeleteFuncFactory deleteFuncFactory) {
        this.deleteFuncFactory = deleteFuncFactory;
    }

    /**
     * RTCプロパティ生成処理のファクトリクラスを取得する。
     * @return RTCプロパティ生成処理のファクトリクラス。
     */
    public RtcConfFactory getConfFactory() {
        return confFactory;
    }

    /**
     * RTCプロパティ生成処理のファクトリクラスを設定する。
     * @param confFactory RTCプロパティ生成処理のファクトリクラス。
     */
    public void setConfFactory(RtcConfFactory confFactory) {
        this.confFactory = confFactory;
    }

    /**
     * コンポーネント名を取得する。
     * @return コンポーネント名。
     */
    public String getComponentName() {
        return componentName;
    }

    /**
     * コンポーネント名を設定する。
     * @param componentName コンポーネント名。
     */
    public void setComponentName(String componentName) {
        this.componentName = componentName;
    }

    /**
     * ブロッキングモードの設定を取得。
     * @return ブロッキングモードの有無。
     */
    public boolean isBlocking() {
        return isBlocking;
    }

    /**
     * ブロッキングモードを設定する。
     * @param isBlocking ブロッキングモード。
     */
    public void setBlocking(boolean isBlocking) {
        this.isBlocking = isBlocking;
    }
}
