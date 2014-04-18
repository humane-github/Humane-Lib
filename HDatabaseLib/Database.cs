using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

namespace HDatabaseLib
{
    public abstract class Database
    {
        /// <summary>
        /// DBを開く
        /// </summary>
        public abstract void open();
        /// <summary>
        /// DBを閉じる
        /// </summary>
        public abstract void close();
        /// <summary>
        /// コミットする
        /// </summary>
        public abstract void commit();
        /// <summary>
        /// ロールバックする
        /// </summary>
        public abstract void rollback();
        /// <summary>
        /// クエリーを実行し結果を取得する
        /// </summary>
        /// <returns></returns>
        public abstract List<Object[]> executeQuery(String query,params Object[] param);
        /// <summary>
        /// クエリーを実行する
        /// </summary>
        public abstract void executeUpdate(String query,params Object[] param);
        /// <summary>
        /// テーブルが存在するか判定する
        /// </summary>
        /// <param name="tablename">テーブル名</param>
        /// <returns></returns>
        public abstract bool tableExists(String tablename);

    }
}
