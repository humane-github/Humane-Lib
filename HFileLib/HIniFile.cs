using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.IO;
using System.Runtime.InteropServices;

namespace HFileLib
{
    public class HIniFile
    {
        private String Path{get;set;}

        /// <summary>
        /// コンストラクタ
        /// </summary>
        /// <param name="path">INIファイルのパス</param>
        public HIniFile(String path)
        {
            Path = path;
            HFile file = new HFile(Path);
            if (!file.exists()) { file.create(); }
        }

        /// <summary>
        /// 文字データを取得
        /// </summary>
        /// <param name="appname">APP名</param>
        /// <param name="keyname">キー名</param>
        /// <param name="defaultval">デフォルト値</param>
        /// <returns></returns>
        public String getString(String appname,String keyname,String defaultval)
        {
            StringBuilder sb = new StringBuilder(1024);
            GetPrivateProfileString(appname,keyname,defaultval, sb, (uint)sb.Capacity, Path);
            return sb.ToString();
        }

        /// <summary>
        /// 数値データを取得
        /// </summary>
        /// <param name="appname">APP名</param>
        /// <param name="keyname">キー名</param>
        /// <param name="defaultval">デフォルト値</param>
        /// <returns></returns>
        public int getInt(String appname,String keyname,int defaultval)
        {
            return (int)GetPrivateProfileInt(appname,keyname,defaultval,Path);
        }

        /// <summary>
        /// INIファイルの情報を書き換える
        /// </summary>
        /// <param name="appname">APP名</param>
        /// <param name="keyname">キー名</param>
        /// <param name="value">更新値</param>
        public void setInt(String appname,String keyname,int value)
        {
            setString(appname,keyname,value.ToString());
        }

        /// <summary>
        /// 数値データを取得
        /// </summary>
        /// <param name="appname">APP名</param>
        /// <param name="keyname">キー名</param>
        /// <param name="defaultval">デフォルト値</param>
        /// <returns></returns>
        public float getFloat(String appname, String keyname, float defaultval)
        {
            String v = getString(appname, keyname, "0.000");
            return float.Parse(v);
        }

        /// <summary>
        /// INIファイルの情報を書き換える
        /// </summary>
        /// <param name="appname">APP名</param>
        /// <param name="keyname">キー名</param>
        /// <param name="value">更新値</param>
        public void setFloat(String appname, String keyname, float value)
        {
            setString(appname, keyname, value.ToString());
        }


        /// <summary>
        /// INIファイルの情報を書き換える
        /// </summary>
        /// <param name="appname">APP名</param>
        /// <param name="keyname">キー名</param>
        /// <param name="value">更新値</param>
        public void setString(String appname,String keyname,String value)
        {
            WritePrivateProfileString(appname,keyname,value,Path);
        }

        [DllImport("KERNEL32.DLL")]
        public static extern uint
            GetPrivateProfileString(string lpAppName,
            string lpKeyName, string lpDefault,
            StringBuilder lpReturnedString, uint nSize,
            string lpFileName);

        [DllImport("KERNEL32.DLL",
        EntryPoint="GetPrivateProfileStringA")]
        public static extern uint
            GetPrivateProfileStringByByteArray(string lpAppName,
            string lpKeyName, string lpDefault,
            byte [] lpReturnedString, uint nSize,
            string lpFileName);

        [DllImport("KERNEL32.DLL")]
        public static extern uint
            GetPrivateProfileInt( string lpAppName,
            string lpKeyName, int nDefault, string lpFileName );

        [DllImport("KERNEL32.DLL")]
        public static extern uint WritePrivateProfileString(
            string lpAppName,
            string lpKeyName,
            string lpString,
            string lpFileName);
    }
}
