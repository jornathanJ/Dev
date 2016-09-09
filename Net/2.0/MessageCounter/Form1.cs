using System;
using System.Collections.Generic;
using System.ComponentModel;
using System.Data;
using System.Drawing;
using System.Text;
using System.Windows.Forms;

namespace MessageCounter
{
    public partial class Form1 : Form
    {
        public Form1()
        {
            InitializeComponent();
            this.tChart1.Series.Clear();
            this.tChart1.Aspect.View3D = false;
        }

        private DataSet dsResult = new DataSet();

        private void Form1_Load(object sender, EventArgs e)
        {

        }

        private void btnLoadFile_Click(object sender, EventArgs e)
        {
            System.IO.FileInfo[] fileList = null;

            this.lstbox.Items.Clear();

            try
            {
                fileList = GetFileList(this.tbxLogTargetFolder.Text);
                for (int index = 0; index < fileList.Length; index++)
                {
                    this.lstbox.Items.Add(fileList[index].Name);
                }
            }
            catch (Exception ex)
            {
                MessageBox.Show(ex.Message);
            }
        }

        private System.IO.FileInfo[] GetFileList(string strFolderPath)
        {
            System.IO.DirectoryInfo dicInfo = new System.IO.DirectoryInfo(strFolderPath);
            System.IO.FileInfo[] fileList = dicInfo.GetFiles();

            return fileList;
        }

        private void lstbox_MouseDoubleClick(object sender, MouseEventArgs e)
        {
            this.AnalyzeLog(string.Format("{0}\\{1}", tbxLogTargetFolder.Text, lstbox.Text), "");
        }

        private void AnalyzeLog(string strFullName, string strFileName)
        {
            int count = 0;
            Dictionary<string, int> dicCounter = new Dictionary<string, int>();
            string strReadText = "";
            string strTemp = "";
            int position = 1;
            StringBuilder sb = new StringBuilder();

            dsResult = new DataSet();
            dsResult.Tables.Add(new DataTable("Result"));
            dsResult.Tables[0].Columns.Add(new DataColumn("TimeText"));
            dsResult.Tables[0].Columns.Add(new DataColumn("Count"));

            try
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

                        if (strReadText == "" || strReadText.Length < 19)
                        {
                            continue;
                        }
                        strTemp = strReadText.Substring(0, 19);
                        if (dicCounter.ContainsKey(strTemp) == true)
                        {
                            int oldCount = dicCounter[strTemp];
                            dicCounter[strTemp] = oldCount + 1;
                        }
                        else
                        {
                            dicCounter.Add(strTemp, 1);
                        }
                    }
                }

                Dictionary<string, int>.Enumerator enumeraltor = dicCounter.GetEnumerator();

                this.tChart1.Series.Clear();

                Steema.TeeChart.Styles.Line lineSeries = new Steema.TeeChart.Styles.Line();
                lineSeries.Pointer.Visible = true;
                lineSeries.Pointer.Style = Steema.TeeChart.Styles.PointerStyles.Circle;
                lineSeries.Pointer.VertSize = 2;
                lineSeries.Pointer.HorizSize = 2;
                this.tChart1.Series.Add(lineSeries);

                do
                {
                    if (false == enumeraltor.MoveNext())
                    {
                        break;
                    }

                    DataRow dRow = dsResult.Tables[0].NewRow();
                    dsResult.Tables[0].Rows.Add(dRow);

                    dRow[0] = enumeraltor.Current.Key;
                    dRow[1] = enumeraltor.Current.Value;

                    count += enumeraltor.Current.Value;

                    lineSeries.Add(position++, enumeraltor.Current.Value);

                } while (true);

                this.fpSpread1.ActiveSheet.DataSource = this.dsResult.Tables[0].DefaultView;

                tbxAvg.Text = (count / dicCounter.Count).ToString();

            }
            catch (Exception e)
            {
                Console.WriteLine(e.Message);
            }
        }

        private void tChart1_Click(object sender, EventArgs e)
        {

        }

        

        

        
    }
}
