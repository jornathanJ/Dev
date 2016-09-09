using System;
using System.Collections.Generic;
using System.ComponentModel;
using System.Data;
using System.Drawing;
using System.Text;
using System.Windows.Forms;

namespace RemoveLF
{
    public partial class RemoveForm : Form
    {
        private char DC4 = Convert.ToChar(0x14);
        private char SO = Convert.ToChar(0x0E);
        private string strLogDestPath = "";

        public RemoveForm()
        {
            InitializeComponent();
        }


        private void ConvertAMQLog(string strFullName, string strFileName)
        {
            string startText = DC4 + "{" + SO;
            string strEnd = DC4 + "}" + SO;
            string strReadText = "";

            StringBuilder sb = new StringBuilder();

            try
            {
                string strTargetFileName = tbxConnDestFolder.Text + "\\" + strFileName + ".cvt";
                System.IO.DirectoryInfo direcltoryInfo = new System.IO.DirectoryInfo(tbxConnDestFolder.Text);

                if (direcltoryInfo.Exists == false)
                {
                    direcltoryInfo.Create();
                }

                using (System.IO.StreamWriter streamWriter = new System.IO.StreamWriter(strTargetFileName))
                {
                    using (System.IO.StreamReader sReader = new System.IO.StreamReader(strFullName))
                    {
                        while (true)
                        {
                            strReadText = sReader.ReadLine();
                            if (strReadText == null)
                            {
                                break;
                            }

                            if (strReadText.IndexOf(startText) == 0)
                            {
                                if (strReadText[strReadText.Length-1] == '{')
                                {
                                    //streamWriter.WriteLine(strReadText.Substring(0, strReadText.Length-1));
                                    
                                    sb = new StringBuilder();
                                    sb.Append(strReadText);
                                }
                                else
                                {
                                    streamWriter.WriteLine(strReadText);
                                }
                            }
                            else
                            {
                                strReadText = strReadText.Replace("     ", "");
                                strReadText = strReadText.Replace("    ", "");
                                strReadText = strReadText.Replace("   ", "");
                                strReadText = strReadText.Replace("  ", "");
                                strReadText = strReadText.Replace(" ", "");
                                sb.Append(strReadText);

                                if (strReadText.IndexOf(strEnd) > 0)
                                {
                                    streamWriter.WriteLine(sb.ToString());
                                    sb.Remove(0, sb.Length);
                                }
                            }
                        }
                    }

                    streamWriter.Flush();
                }
            }
            catch (Exception e)
            {
                Console.WriteLine(e.Message);
            }
        }

        private void ConvertJSON(string strFullName, string strFileName)
        {
            string startText = DC4 + "{" + SO;
            string strEnd = DC4 + "}" + SO;
            string strReadText = "";

            StringBuilder sb = new StringBuilder();

            try
            {
                string strTargetFileName = tbxJSONDestFolder.Text + "\\" + strFileName + ".cvt";
                System.IO.DirectoryInfo direcltoryInfo = new System.IO.DirectoryInfo(tbxJSONDestFolder.Text);

                if (direcltoryInfo.Exists == false)
                {
                    direcltoryInfo.Create();
                }

                using (System.IO.StreamWriter streamWriter = new System.IO.StreamWriter(strTargetFileName))
                {
                    using (System.IO.StreamReader sReader = new System.IO.StreamReader(strFullName))
                    {
                        while (true)
                        {
                            strReadText = sReader.ReadLine();
                            if (strReadText == null)
                            {
                                break;
                            }

                            if (strReadText.IndexOf(startText) == 0)
                            {
                                if (strReadText[strReadText.Length - 1] == '{')
                                {
                                    //streamWriter.WriteLine(strReadText.Substring(0, strReadText.Length-1));

                                    sb = new StringBuilder();
                                    sb.Append(strReadText);
                                }
                                else
                                {
                                    streamWriter.WriteLine(strReadText);
                                }
                            }
                            else
                            {
                                strReadText = strReadText.Replace("\t", "");
                                //strReadText = strReadText.Replace("    ", "");
                                //strReadText = strReadText.Replace("   ", "");
                                //strReadText = strReadText.Replace("  ", "");
                                //strReadText = strReadText.Replace(" ", "");
                                sb.Append(strReadText);

                                if (strReadText.IndexOf(strEnd) > 0)
                                {
                                    streamWriter.WriteLine(sb.ToString());
                                    sb.Remove(0, sb.Length);
                                }
                            }
                        }
                    }
                    streamWriter.WriteLine(sb.ToString());
                    streamWriter.Flush();
                }
            }
            catch (Exception e)
            {
                Console.WriteLine(e.Message);
            }
        }

        private System.IO.FileInfo[] GetFileList()
        {
            System.IO.DirectoryInfo dicInfo = new System.IO.DirectoryInfo(tbxLogTargetFolder.Text);
            System.IO.FileInfo [] fileList = dicInfo.GetFiles();

            return fileList;
        }

        private System.IO.FileInfo[] GetFileList(string strFolderPath)
        {
            System.IO.DirectoryInfo dicInfo = new System.IO.DirectoryInfo(strFolderPath);
            System.IO.FileInfo[] fileList = dicInfo.GetFiles();

            return fileList;
        }


        private void button2_Click(object sender, EventArgs e)
        {
            System.IO.FileInfo[] fileList = this.GetFileList();
            for(int index = 0; index < fileList.Length; index++)
            {
                if (fileList[index].Extension == ".log")
                {
                    if (fileList[index].Name.Contains("복사본") || fileList[index].Name.Contains("Copy"))
                    {
                        continue;
                    }
                    this.ConvertAMQLog(fileList[index].FullName, fileList[index].Name);
                }
            }
            MessageBox.Show("Complete");
        }

        private void button1_Click(object sender, EventArgs e)
        {

        }

        private void btnConvertJSON_Click(object sender, EventArgs e)
        {
            System.IO.FileInfo[] fileList = this.GetFileList(this.tbxJSONDestFolder.Text);
            for (int index = 0; index < fileList.Length; index++)
            {
                if (fileList[index].Extension == ".txt" || fileList[index].Extension == ".js")
                {
                    if (fileList[index].Name.Contains("복사본") || fileList[index].Name.Contains("Copy"))
                    {
                        continue;
                    }
                    this.ConvertJSON(fileList[index].FullName, fileList[index].Name);
                }
                
            }
            MessageBox.Show("Complete");
        }
    }
}
