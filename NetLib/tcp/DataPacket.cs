using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

namespace NetLib.tcp
{
    public class DataPacket
    {
        public static byte[] TYPE_GETIMG = new byte[] { 0x41, 0x30, 0x31 };//A01
        public static byte[] TYPE_SENDIMG = new byte[] { 0x41, 0x30, 0x32 };//A02
        public static byte[] TYPE_GETPROPERTY = new byte[] { 0x42, 0x30, 0x31 };//B01
        public static byte[] TYPE_SENDPROPERTY = new byte[] { 0x42, 0x30, 0x32 };//B02
        public static byte[] TYPE_SENDMSG = new byte[] { 0x43, 0x30, 0x31 };//C01
        private static byte RESERVE = 0x20;

        //ヘッダの長さ
        private static int HEADER_LENGTH = 10;
        //電文
        public byte[] m_data { get; set; }
        public byte[] m_header { get; set; }
        public byte[] m_body { get; set; }

        public DataPacket(byte[] type,byte[] body)
        {
            //初期化
            int idx = 0;
            if (body == null) { body = new byte[] { RESERVE }; }
            m_data = new byte[HEADER_LENGTH+body.Length];
            for (int i = 0; i < m_data.Length; i++)
            {
                m_data[i] = RESERVE;
            }
            //ヘッダー生成
            for (int i = 0; i < type.Length; i++)
            {
                m_data[i] = type[i];
                idx++;
            }
            byte[] lenByteArray = BitConverter.GetBytes(body.Length);
            for (int i = 0; i < lenByteArray.Length; i++)
            {
                m_data[idx + i] = lenByteArray[i];
            }
            //ボディ生成
            for (int i = 0; i < body.Length; i++)
            {
                m_data[i + HEADER_LENGTH] = body[i];
            }
        }

        /// <summary>
        /// コンストラクタ
        /// </summary>
        /// <param name="data"></param>
        public DataPacket(byte[] data)
        {
            //ヘッダ部抽出
            m_header = new byte[HEADER_LENGTH];
            m_data = data;
            for (int i = 0; i < HEADER_LENGTH; i++)
            {
                m_header[i] = m_data[i];
            }
            //ボディ部抽出
            m_body = new byte[m_data.Length - HEADER_LENGTH];
            for (int i = HEADER_LENGTH; i < m_data.Length; i++)
            {
                m_body[i-HEADER_LENGTH] = m_data[i];
            }
        }

        public byte[] toByteArray()
        {
            return m_data;
        }

        public bool isGetIMG()
        {
            return (m_data[0] == TYPE_GETIMG[0] &&
                    m_data[1] == TYPE_GETIMG[1] &&
                    m_data[2] == TYPE_GETIMG[2]);
        }

        public bool isSendIMG()
        {
            return (m_data[0] == TYPE_SENDIMG[0] &&
                    m_data[1] == TYPE_SENDIMG[1] &&
                    m_data[2] == TYPE_SENDIMG[2]);
        }

        public bool isSendMSG()
        {
            return (m_data[0] == TYPE_SENDMSG[0] &&
                    m_data[1] == TYPE_SENDMSG[1] &&
                    m_data[2] == TYPE_SENDMSG[2]);
        }
        public bool isSendPROPERTY()
        {
            return (m_data[0] == TYPE_SENDPROPERTY[0] &&
                    m_data[1] == TYPE_SENDPROPERTY[1] &&
                    m_data[2] == TYPE_SENDPROPERTY[2]);
        }

    }
}
