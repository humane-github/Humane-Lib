using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Data.SQLite;

namespace HDatabaseLib.SQLite
{
    public class SQLiteDB : Database
    {
        /// <summary>
        /// 接続DB名
        /// </summary>
        private String DBName { get; set; }
        /// <summary>
        /// SQLiteへの接続オブジェクト
        /// </summary>
        private SQLiteConnection Conn { get; set; }
        /// <summary>
        /// トランザクション
        /// </summary>
        private SQLiteTransaction Transaction { get; set; }

        /// <summary>
        /// コンストラクタ
        /// </summary>
        /// <param name="dbname">接続するDB名</param>
        public SQLiteDB(String dbname)
        {
            DBName = dbname;
            SQLiteConnectionStringBuilder builder = new SQLiteConnectionStringBuilder();
            builder.DataSource = DBName;
            Conn = new SQLiteConnection(builder.ConnectionString);            
        }

        /// <summary>
        /// DBに接続する
        /// </summary>
        public override void open()
        {
            Conn.Open();
            Transaction = Conn.BeginTransaction();
        }

        /// <summary>
        /// DBを閉じる
        /// </summary>
        public override void close()
        {
            Transaction.Commit();
            Conn.Close();
        }

        /// <summary>
        /// クエリーを実行する
        /// </summary>
        /// <param name="query">クエリー文字列</param>
        /// <param name="param">クエリーに渡すパラメーター</param>
        public override void executeUpdate(String query,params Object[] param)
        {
            SQLiteCommand cmd = Conn.CreateCommand();
            try
            {
                cmd.CommandText = query;
                createParam(cmd, param);
                cmd.ExecuteNonQuery();
            }
            catch (Exception ex)
            {
                Transaction.Rollback();
                throw ex;
            }
        }

        /// <summary>
        /// クエリーを実行し結果を取得する
        /// </summary>
        /// <returns></returns>
        public override List<Object[]> executeQuery(String query, params Object[] param)
        {
            List<Object[]> result = new List<object[]>();

            SQLiteCommand cmd = Conn.CreateCommand();
            cmd.CommandText = query;
            createParam(cmd, param);
            using( SQLiteDataReader r = cmd.ExecuteReader() )
            {
                while(r.Read())
                {
                    Object[] record = new Object[r.FieldCount];
                    for (int i = 0; i < r.FieldCount; i++)
                    {
                        record[i] = r.GetValue(i);
                    }
                    result.Add(record);
                }
            }
            return result;
        }

        /// <summary>
        /// テーブルが存在するか判定する
        /// </summary>
        /// <param name="tablename">テーブル名</param>
        /// <returns></returns>
        public override bool tableExists(String tablename)
        {
            String sql = "select tbl_name from sqlite_master where type='table'";
            List<Object[]> result = executeQuery(sql, null);
            foreach (Object[] o in result)
            {
                if (tablename.Equals((String)o[0])) { return true; }
            }
            return false;
        }

        /// <summary>
        /// コミットする
        /// </summary>
        public override void commit() { }

        /// <summary>
        /// ロールバックする
        /// </summary>
        public override void rollback() { }

        /// <summary>
        /// PreparedStatementを設定する
        /// </summary>
        /// <param name="cmd">パラメータを指定するオブジェクト</param>
        /// <param name="param">パラメータ</param>
        private void createParam(SQLiteCommand cmd,Object[] param)
        {
            if (param == null || cmd == null) { return; }
            foreach (Object p in param)
            {
                SQLiteParameter sp = cmd.CreateParameter();
                if (p is String)
                {
                    sp.DbType = System.Data.DbType.String;
                    sp.Value = (String)p;
                }
                else if (p is Int16)
                {
                    sp.DbType = System.Data.DbType.Int16;
                    sp.Value = (int)p;
                }
                else if (p is Int32)
                {
                    sp.DbType = System.Data.DbType.Int32;
                    sp.Value = (int)p;
                }
                cmd.Parameters.Add(sp);
            }
        }

    }
}
