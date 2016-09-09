using System;
using System.Collections.Generic;
using System.ComponentModel;
using System.Data;
using System.Drawing;
using System.Text;
using System.Windows.Forms;

namespace WindowsFormsApplication3
{
    public partial class Form1 : Form
    {
        public Form1()
        {
            InitializeComponent();
        }

        private void Form1_Load(object sender, EventArgs e)
        {

            
        
        }

        private void GUIDTest()
        {
            string[] strGUIDList = new string[] {
                "80a2e366-2856-4696-a367-7e01f34d31fd",
                "3f0e333e-d16a-4f80-8c16-660f3e3d003d",
                "84ff2f84-7ee6-453f-8f66-631b401a1d3e",
                "f6841438-d025-4663-854a-1aa05f225a97"};


            //string strFirst = "33F64875-9D60-4891-8846-44A1695F8D47"; //[Guid("33F64875-9D60-4891-8846-44A1695F8D47")]
            ////string strSecond  = "959075D3-D7B2-4673-A037-4FF56DC1D63C"; //[Guid("959075D3-D7B2-4673-A037-4FF56DC1D63C")]

            //System.Guid _guid1 = System.Guid.Parse(strGUIDList[0]);
            //System.Guid _guid2 = System.Guid.Parse(strGUIDList[1]);
            //System.Guid _guid3 = System.Guid.Parse(strGUIDList[2]);
            //System.Guid _guid4 = System.Guid.Parse(strGUIDList[3]);

            //int a1 = _guid1.GetHashCode();
            //int a2 = _guid2.GetHashCode();
            //int a3 = _guid3.GetHashCode();
            //int a4 = _guid4.GetHashCode();


            //int result1 = _guid1.CompareTo(_guid1);
            //int result2 = _guid1.CompareTo(_guid2);
            //int result3 = _guid1.CompareTo(_guid3);
            //int result4 = _guid1.CompareTo(_guid4);

            //int result5 = _guid3.CompareTo(_guid4);

            //System.Console.WriteLine(_guid1.ToString());
            //System.Console.WriteLine(_guid2.ToString());
        }

        private void userControl11_Load(object sender, EventArgs e)
        {

        }

        private void button2_Click(object sender, EventArgs e)
        {
            Password = "bistel01";
            //string strTemp1 = this.EncryptedPassword("065063062");
            //string strTemp1 = this.EncryptedPassword("065063062");

            //string strTemp2 = this.DecryptedPassword();


            string strTemp3 = this.ConvertCrypto(Password);
        }

        private string ConvertCrypto(string src)
        {
            return Convert.ToBase64String(Encoding.ASCII.GetBytes(src));
        }

        private string Password = "ABC";

        public string DecryptedPassword()
        {
            System.Text.StringBuilder builder = new System.Text.StringBuilder();

            int pos = 0;
            foreach (char c in Password.ToCharArray())
            {
                int i = c;
                if (pos == 1)
                    i -= 3;
                else
                    if (pos == 2)
                        i -= 5;

                builder.AppendFormat("{0:000}", i);
                ++pos;
            }

            return builder.ToString();
        }

        /// <summary>
        /// Encrypt/decrypt password
        /// </summary>
        public string EncryptedPassword(string value)
        {
            //set           //decrypt password
            //{
                try
                {
                    StringBuilder builder = new StringBuilder();

                    if (value.Length % 3 != 0)
                        throw new ArgumentException("Encrypted password length is wrong");

                    int count = 0;
                    for (int i = 0; i != value.Length; i += 3)
                    {
                        int ch = int.Parse(value.Substring(i, 3));
                        if (count == 1)
                            ch += 3;
                        else
                            if (count == 2)
                                ch += 5;

                        builder.Append((char)ch);

                        ++count;
                    }

                    Password = builder.ToString();
                }
                catch (Exception ex)
                {
                    //handle errors
                }

            return Password;
            }

        private void comboBox1_DropDown(object sender, EventArgs e)
        {
            System.Console.WriteLine("comboBox1_DropDown");
        }

        private void comboBox1_Click(object sender, EventArgs e)
        {
            System.Console.WriteLine("comboBox1_Click");
        }

        private void comboBox1_Enter(object sender, EventArgs e)
        {
            System.Console.WriteLine("comboBox1_Enter");
        }

        private void comboBox1_DrawItem(object sender, DrawItemEventArgs e)
        {
            System.Console.WriteLine("comboBox1_DrawItem");
        }

        private void comboBox1_ContextMenuStripChanged(object sender, EventArgs e)
        {
            System.Console.WriteLine("comboBox1_ContextMenuStripChanged");
        }

        private void comboBox1_EnabledChanged(object sender, EventArgs e)
        {
            System.Console.WriteLine("comboBox1_EnabledChanged");
        }

        
            //get                    //Encrypt password
            //{
            //    System.Text.StringBuilder builder = new System.Text.StringBuilder();

            //    int pos = 0;
            //    foreach (char c in Password.ToCharArray())
            //    {
            //        int i = c;
            //        if (pos == 1)
            //            i -= 3;
            //        else
            //            if (pos == 2)
            //                i -= 5;

            //        builder.AppendFormat("{0:000}", i);
            //        ++pos;
            //    }

            //    return builder.ToString();
            //}
    
    }
}
