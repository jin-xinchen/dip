using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace category2sql.learn
{
    class ByteAndDate
    {
        /** 
         * From htt ps: //mp.weixin.qq.com/s/60bxveJWNj2sQjsZCEb0pQ
          * 将传入的年月日存储在3个字节的数组中 
          * 3个字节总共24位，从左到右前15位存年份，中间4位存月份，最后5位存储天数 
          * @param year 年份 
          * @param month 月份 
          * @param day 天数 
          * @return 返回存储字节数组 
          */
        public static byte[] date2bytes(int year, int month, int day)
        {
            //?????111    //7    //0x7
            //???11111    //31   //0x1F
            //?1111111    //127  //0x7F
            //????1000    //8    //0x8
     //111111110000000    //32640//0x7F80 
            byte[] bytes = new byte[3];
            bytes[2] = (byte)(((0x7 & month) << 5) | (0x1F & day));
            bytes[1] = (byte)(((0x7F & year) << 1) | ((0x8 & month) >> 3));
            bytes[0] = (byte)((0x7F80 & year) >> 7);
            return bytes;
        }

        /** 

         * 从存储年月日的3个字节中解析出年月日数据 
         * @param bytes 待解析的字节数组 
         * @return 返回长度为3的数组，依次表示年，月和日 
         */

        public static int[] bytes2date(byte[] bytes)
        {

            int[] date = new int[3];

            date[2] = bytes[2] & 0x1F;

            date[1] = ((bytes[1] & 0x1) << 3) | ((bytes[2] & 0xE0) >> 5);

            date[0] = ((bytes[0] & 0xFF) << 7) | ((bytes[1] & 0xFE) >> 1);

            return date;

        }
        static void Main(String[] arg)
        {
            int v = 0x7;
            Console.WriteLine(Convert.ToString(v, 2).PadLeft(8, '\u2630'));
            Console.WriteLine(Convert.ToString(0x1F, 2).PadLeft(8, '?') +","+ 0x1F);
            Console.WriteLine(Convert.ToString(0x7F, 2).PadLeft(8, '?') + "," + 0x7F);
            Console.WriteLine(Convert.ToString(0x08, 2).PadLeft(8, '?') + "," + 0x08);
            Console.WriteLine(Convert.ToString(0x7F80, 2).PadLeft(8, '\u2630') + "," + 0x7F80);
            //Console.WriteLine(Convert.ToString(v, 2).PadRight(8, '0'));
            byte[] bytes = ByteAndDate.date2bytes(2022, 01, 29);
            Console.WriteLine(string.Join(",", bytes));
            int[] date = ByteAndDate.bytes2date(bytes);
            Console.WriteLine(string.Join(",", date));
        }
        private string OutPutBinary()
        {
            string text = "C# 输出二进制字符串";
            byte[] arrByte = System.Text.Encoding.GetEncoding("GB2312").GetBytes(text);
            string byteStr = null;

            foreach (byte b in arrByte)
            {
                if (byteStr == null)
                    byteStr = Convert.ToString(b, 2);
                else
                    byteStr += "，" + Convert.ToString(b, 2);
            }
            return byteStr;
        }
    }
}

