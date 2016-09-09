namespace MessageCounter
{
    partial class Form1
    {
        /// <summary>
        /// Required designer variable.
        /// </summary>
        private System.ComponentModel.IContainer components = null;

        /// <summary>
        /// Clean up any resources being used.
        /// </summary>
        /// <param name="disposing">true if managed resources should be disposed; otherwise, false.</param>
        protected override void Dispose(bool disposing)
        {
            if (disposing && (components != null))
            {
                components.Dispose();
            }
            base.Dispose(disposing);
        }

        #region Windows Form Designer generated code

        /// <summary>
        /// Required method for Designer support - do not modify
        /// the contents of this method with the code editor.
        /// </summary>
        private void InitializeComponent()
        {
            System.ComponentModel.ComponentResourceManager resources = new System.ComponentModel.ComponentResourceManager(typeof(Form1));
            FarPoint.Win.Spread.TipAppearance tipAppearance1 = new FarPoint.Win.Spread.TipAppearance();
            this.label1 = new System.Windows.Forms.Label();
            this.tbxLogTargetFolder = new System.Windows.Forms.TextBox();
            this.groupBox1 = new System.Windows.Forms.GroupBox();
            this.btnLoadFile = new System.Windows.Forms.Button();
            this.splitContainer1 = new System.Windows.Forms.SplitContainer();
            this.tChart1 = new Steema.TeeChart.TChart();
            this.line1 = new Steema.TeeChart.Styles.Line();
            this.fpSpread1 = new FarPoint.Win.Spread.FpSpread();
            this.fpSpread1_Sheet1 = new FarPoint.Win.Spread.SheetView();
            this.lstbox = new System.Windows.Forms.ListBox();
            this.tbxAvg = new System.Windows.Forms.TextBox();
            this.lblAvg = new System.Windows.Forms.Label();
            this.groupBox1.SuspendLayout();
            this.splitContainer1.Panel1.SuspendLayout();
            this.splitContainer1.Panel2.SuspendLayout();
            this.splitContainer1.SuspendLayout();
            ((System.ComponentModel.ISupportInitialize)(this.fpSpread1)).BeginInit();
            ((System.ComponentModel.ISupportInitialize)(this.fpSpread1_Sheet1)).BeginInit();
            this.SuspendLayout();
            // 
            // label1
            // 
            this.label1.AutoSize = true;
            this.label1.Location = new System.Drawing.Point(12, 17);
            this.label1.Name = "label1";
            this.label1.Size = new System.Drawing.Size(105, 12);
            this.label1.TabIndex = 7;
            this.label1.Text = "Log Target Folder";
            // 
            // tbxLogTargetFolder
            // 
            this.tbxLogTargetFolder.Location = new System.Drawing.Point(12, 32);
            this.tbxLogTargetFolder.Name = "tbxLogTargetFolder";
            this.tbxLogTargetFolder.Size = new System.Drawing.Size(508, 21);
            this.tbxLogTargetFolder.TabIndex = 6;
            this.tbxLogTargetFolder.Text = "C:\\Users\\jornathan\\Desktop\\Empty";
            // 
            // groupBox1
            // 
            this.groupBox1.Controls.Add(this.lblAvg);
            this.groupBox1.Controls.Add(this.tbxAvg);
            this.groupBox1.Controls.Add(this.btnLoadFile);
            this.groupBox1.Controls.Add(this.label1);
            this.groupBox1.Controls.Add(this.tbxLogTargetFolder);
            this.groupBox1.Dock = System.Windows.Forms.DockStyle.Top;
            this.groupBox1.Location = new System.Drawing.Point(0, 0);
            this.groupBox1.Name = "groupBox1";
            this.groupBox1.Size = new System.Drawing.Size(992, 86);
            this.groupBox1.TabIndex = 8;
            this.groupBox1.TabStop = false;
            this.groupBox1.Text = "groupBox1";
            // 
            // btnLoadFile
            // 
            this.btnLoadFile.Location = new System.Drawing.Point(544, 29);
            this.btnLoadFile.Name = "btnLoadFile";
            this.btnLoadFile.Size = new System.Drawing.Size(111, 23);
            this.btnLoadFile.TabIndex = 8;
            this.btnLoadFile.Text = "Load File";
            this.btnLoadFile.UseVisualStyleBackColor = true;
            this.btnLoadFile.Click += new System.EventHandler(this.btnLoadFile_Click);
            // 
            // splitContainer1
            // 
            this.splitContainer1.Dock = System.Windows.Forms.DockStyle.Fill;
            this.splitContainer1.Location = new System.Drawing.Point(0, 86);
            this.splitContainer1.Name = "splitContainer1";
            // 
            // splitContainer1.Panel1
            // 
            this.splitContainer1.Panel1.Controls.Add(this.tChart1);
            // 
            // splitContainer1.Panel2
            // 
            this.splitContainer1.Panel2.Controls.Add(this.fpSpread1);
            this.splitContainer1.Panel2.Controls.Add(this.lstbox);
            this.splitContainer1.Size = new System.Drawing.Size(992, 393);
            this.splitContainer1.SplitterDistance = 684;
            this.splitContainer1.TabIndex = 9;
            // 
            // tChart1
            // 
            // 
            // 
            // 
            this.tChart1.Aspect.Chart3DPercent = 17;
            this.tChart1.Aspect.View3D = false;
            this.tChart1.Aspect.ZOffset = 0D;
            // 
            // 
            // 
            // 
            // 
            // 
            this.tChart1.Axes.Bottom.MaximumOffset = 3;
            this.tChart1.Axes.Bottom.MinimumOffset = 3;
            // 
            // 
            // 
            this.tChart1.Axes.Bottom.Title.Transparent = true;
            // 
            // 
            // 
            // 
            // 
            // 
            this.tChart1.Axes.Depth.Title.Transparent = true;
            // 
            // 
            // 
            // 
            // 
            // 
            this.tChart1.Axes.DepthTop.Title.Transparent = true;
            // 
            // 
            // 
            this.tChart1.Axes.Left.MaximumOffset = 3;
            this.tChart1.Axes.Left.MinimumOffset = 3;
            // 
            // 
            // 
            this.tChart1.Axes.Left.Title.Transparent = true;
            // 
            // 
            // 
            // 
            // 
            // 
            this.tChart1.Axes.Right.Title.Transparent = true;
            // 
            // 
            // 
            // 
            // 
            // 
            this.tChart1.Axes.Top.Title.Transparent = true;
            this.tChart1.Dock = System.Windows.Forms.DockStyle.Fill;
            this.tChart1.Location = new System.Drawing.Point(0, 0);
            this.tChart1.Name = "tChart1";
            this.tChart1.Series.Add(this.line1);
            this.tChart1.Size = new System.Drawing.Size(684, 393);
            this.tChart1.TabIndex = 0;
            this.tChart1.Click += new System.EventHandler(this.tChart1_Click);
            // 
            // line1
            // 
            // 
            // 
            // 
            this.line1.Brush.Color = System.Drawing.Color.FromArgb(((int)(((byte)(68)))), ((int)(((byte)(102)))), ((int)(((byte)(163)))));
            this.line1.Color = System.Drawing.Color.FromArgb(((int)(((byte)(68)))), ((int)(((byte)(102)))), ((int)(((byte)(163)))));
            this.line1.ColorEach = false;
            this.line1.Dark3D = false;
            // 
            // 
            // 
            this.line1.LinePen.Color = System.Drawing.Color.FromArgb(((int)(((byte)(41)))), ((int)(((byte)(61)))), ((int)(((byte)(98)))));
            // 
            // 
            // 
            // 
            // 
            // 
            this.line1.Marks.Callout.ArrowHead = Steema.TeeChart.Styles.ArrowHeadStyles.None;
            this.line1.Marks.Callout.ArrowHeadSize = 8;
            // 
            // 
            // 
            this.line1.Marks.Callout.Brush.Color = System.Drawing.Color.Black;
            this.line1.Marks.Callout.Distance = 0;
            this.line1.Marks.Callout.Draw3D = false;
            this.line1.Marks.Callout.Length = 10;
            this.line1.Marks.Callout.Series = this.line1;
            this.line1.Marks.Callout.Style = Steema.TeeChart.Styles.PointerStyles.Rectangle;
            this.line1.Marks.Callout.Visible = false;
            this.line1.Marks.Series = this.line1;
            // 
            // 
            // 
            // 
            // 
            // 
            // 
            // 
            // 
            this.line1.Pointer.Brush.Gradient.Transparency = 100;
            this.line1.Pointer.Dark3D = false;
            this.line1.Pointer.Draw3D = false;
            this.line1.Pointer.HorizSize = 2;
            this.line1.Pointer.Series = this.line1;
            this.line1.Pointer.Style = Steema.TeeChart.Styles.PointerStyles.Circle;
            this.line1.Pointer.VertSize = 2;
            this.line1.Pointer.Visible = true;
            this.line1.Title = "line1";
            // 
            // 
            // 
            this.line1.XValues.DataMember = "X";
            this.line1.XValues.Order = Steema.TeeChart.Styles.ValueListOrder.Ascending;
            // 
            // 
            // 
            this.line1.YValues.DataMember = "Y";
            // 
            // fpSpread1
            // 
            this.fpSpread1.About = "3.0.2005.2005";
            this.fpSpread1.AccessibleDescription = "";
            this.fpSpread1.Dock = System.Windows.Forms.DockStyle.Fill;
            this.fpSpread1.Location = new System.Drawing.Point(0, 0);
            this.fpSpread1.Name = "fpSpread1";
            this.fpSpread1.Sheets.AddRange(new FarPoint.Win.Spread.SheetView[] {
            this.fpSpread1_Sheet1});
            this.fpSpread1.Size = new System.Drawing.Size(184, 393);
            this.fpSpread1.TabIndex = 1;
            tipAppearance1.BackColor = System.Drawing.SystemColors.Info;
            tipAppearance1.Font = new System.Drawing.Font("Gulim", 9F, System.Drawing.FontStyle.Regular, System.Drawing.GraphicsUnit.Point, ((byte)(129)));
            tipAppearance1.ForeColor = System.Drawing.SystemColors.InfoText;
            this.fpSpread1.TextTipAppearance = tipAppearance1;
            // 
            // fpSpread1_Sheet1
            // 
            this.fpSpread1_Sheet1.Reset();
            this.fpSpread1_Sheet1.SheetName = "Sheet1";
            // 
            // lstbox
            // 
            this.lstbox.Dock = System.Windows.Forms.DockStyle.Right;
            this.lstbox.FormattingEnabled = true;
            this.lstbox.ItemHeight = 12;
            this.lstbox.Location = new System.Drawing.Point(184, 0);
            this.lstbox.Name = "lstbox";
            this.lstbox.Size = new System.Drawing.Size(120, 393);
            this.lstbox.TabIndex = 0;
            this.lstbox.MouseDoubleClick += new System.Windows.Forms.MouseEventHandler(this.lstbox_MouseDoubleClick);
            // 
            // tbxAvg
            // 
            this.tbxAvg.Location = new System.Drawing.Point(688, 59);
            this.tbxAvg.Name = "tbxAvg";
            this.tbxAvg.Size = new System.Drawing.Size(215, 21);
            this.tbxAvg.TabIndex = 9;
            // 
            // lblAvg
            // 
            this.lblAvg.AutoSize = true;
            this.lblAvg.Location = new System.Drawing.Point(631, 62);
            this.lblAvg.Name = "lblAvg";
            this.lblAvg.Size = new System.Drawing.Size(51, 12);
            this.lblAvg.TabIndex = 10;
            this.lblAvg.Text = "Average";
            // 
            // Form1
            // 
            this.AutoScaleDimensions = new System.Drawing.SizeF(7F, 12F);
            this.AutoScaleMode = System.Windows.Forms.AutoScaleMode.Font;
            this.ClientSize = new System.Drawing.Size(992, 479);
            this.Controls.Add(this.splitContainer1);
            this.Controls.Add(this.groupBox1);
            this.Name = "Form1";
            this.Text = "Form1";
            this.Load += new System.EventHandler(this.Form1_Load);
            this.groupBox1.ResumeLayout(false);
            this.groupBox1.PerformLayout();
            this.splitContainer1.Panel1.ResumeLayout(false);
            this.splitContainer1.Panel2.ResumeLayout(false);
            this.splitContainer1.ResumeLayout(false);
            ((System.ComponentModel.ISupportInitialize)(this.fpSpread1)).EndInit();
            ((System.ComponentModel.ISupportInitialize)(this.fpSpread1_Sheet1)).EndInit();
            this.ResumeLayout(false);

        }

        #endregion

        private System.Windows.Forms.Label label1;
        private System.Windows.Forms.TextBox tbxLogTargetFolder;
        private System.Windows.Forms.GroupBox groupBox1;
        private System.Windows.Forms.SplitContainer splitContainer1;
        private System.Windows.Forms.Button btnLoadFile;
        private System.Windows.Forms.ListBox lstbox;
        private Steema.TeeChart.TChart tChart1;
        private FarPoint.Win.Spread.FpSpread fpSpread1;
        private FarPoint.Win.Spread.SheetView fpSpread1_Sheet1;
        private Steema.TeeChart.Styles.Line line1;
        private System.Windows.Forms.Label lblAvg;
        private System.Windows.Forms.TextBox tbxAvg;
    }
}

