using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

namespace HFileLib
{
    public class HFile
    {
        /// <summary>
        /// 操作対象ファイルの絶対パス
        /// </summary>
        private String Path { get; set; }

        /// <summary>
        /// ファイル走査リーダー
        /// </summary>
        private System.IO.StreamReader Reader { get; set; }

        /// <summary>
        /// コンストラクタ
        /// </summary>
        /// <param name="path">操作対象ファイルの絶対パス</param>
        public HFile(String path)
        {
            this.Path = path;
        }

        /// <summary>
        /// ファイルを作成する
        /// </summary>
        public void create()
        {
            System.IO.File.Create(Path);
        }

        /// <summary>
        /// ファイルを開く
        /// </summary>
        /// <returns></returns>
        public bool open()
        {
            if (!exists()) { return false; }
            Reader = new System.IO.StreamReader(Path, System.Text.Encoding.Default);
            return true;
        }

        /// <summary>
        /// ファイルを閉じる
        /// </summary>
        public void close()
        {
            if (Reader != null) { Reader.Close(); }
        }

        /// <summary>
        /// ファイルを一行づつ読み込む
        /// NULLが帰ったら全行読み込み完了
        /// </summary>
        /// <returns></returns>
        public String readLine()
        {
            String result = null;
            if (Reader == null) { return null; }
            if (Reader.Peek() >= 0)
            {
                result = Reader.ReadLine();
            }
            return result;
        }

        /// <summary>
        /// ファイルが存在するか判定する
        /// </summary>
        /// <returns></returns>
        public bool exists()
        {
            return System.IO.File.Exists(Path);
        }
    }
}
