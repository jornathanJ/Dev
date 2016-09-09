namespace RemoveLF
{
    partial class RemoveForm
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
            this.btnConvertWebSocket = new System.Windows.Forms.Button();
            this.btnConvertConnectivity = new System.Windows.Forms.Button();
            this.panel1 = new System.Windows.Forms.Panel();
            this.label3 = new System.Windows.Forms.Label();
            this.label2 = new System.Windows.Forms.Label();
            this.label1 = new System.Windows.Forms.Label();
            this.tbxWebSocketDestFolder = new System.Windows.Forms.TextBox();
            this.tbxConnDestFolder = new System.Windows.Forms.TextBox();
            this.tbxLogTargetFolder = new System.Windows.Forms.TextBox();
            this.splitContainer1 = new System.Windows.Forms.SplitContainer();
            this.groupBox1 = new System.Windows.Forms.GroupBox();
            this.rTbxConnStatus = new System.Windows.Forms.RichTextBox();
            this.groupBox2 = new System.Windows.Forms.GroupBox();
            this.rTbxWebSocketStatus = new System.Windows.Forms.RichTextBox();
            this.label4 = new System.Windows.Forms.Label();
            this.tbxJSONDestFolder = new System.Windows.Forms.TextBox();
            this.btnConvertJSON = new System.Windows.Forms.Button();
            this.panel1.SuspendLayout();
            this.splitContainer1.Panel1.SuspendLayout();
            this.splitContainer1.Panel2.SuspendLayout();
            this.splitContainer1.SuspendLayout();
            this.groupBox1.SuspendLayout();
            this.groupBox2.SuspendLayout();
            this.SuspendLayout();
            // 
            // btnConvertWebSocket
            // 
            this.btnConvertWebSocket.Location = new System.Drawing.Point(517, 98);
            this.btnConvertWebSocket.Name = "btnConvertWebSocket";
            this.btnConvertWebSocket.Size = new System.Drawing.Size(193, 23);
            this.btnConvertWebSocket.TabIndex = 0;
            this.btnConvertWebSocket.Text = "Convert Web Socket Log";
            this.btnConvertWebSocket.UseVisualStyleBackColor = true;
            this.btnConvertWebSocket.Click += new System.EventHandler(this.button1_Click);
            // 
            // btnConvertConnectivity
            // 
            this.btnConvertConnectivity.Location = new System.Drawing.Point(517, 59);
            this.btnConvertConnectivity.Name = "btnConvertConnectivity";
            this.btnConvertConnectivity.Size = new System.Drawing.Size(193, 23);
            this.btnConvertConnectivity.TabIndex = 1;
            this.btnConvertConnectivity.Text = "Convert Connectivity Log";
            this.btnConvertConnectivity.UseVisualStyleBackColor = true;
            this.btnConvertConnectivity.Click += new System.EventHandler(this.button2_Click);
            // 
            // panel1
            // 
            this.panel1.Controls.Add(this.label4);
            this.panel1.Controls.Add(this.tbxJSONDestFolder);
            this.panel1.Controls.Add(this.btnConvertJSON);
            this.panel1.Controls.Add(this.label3);
            this.panel1.Controls.Add(this.label2);
            this.panel1.Controls.Add(this.label1);
            this.panel1.Controls.Add(this.tbxWebSocketDestFolder);
            this.panel1.Controls.Add(this.tbxConnDestFolder);
            this.panel1.Controls.Add(this.tbxLogTargetFolder);
            this.panel1.Controls.Add(this.btnConvertConnectivity);
            this.panel1.Controls.Add(this.btnConvertWebSocket);
            this.panel1.Dock = System.Windows.Forms.DockStyle.Top;
            this.panel1.Location = new System.Drawing.Point(0, 0);
            this.panel1.Name = "panel1";
            this.panel1.Size = new System.Drawing.Size(722, 179);
            this.panel1.TabIndex = 2;
            // 
            // label3
            // 
            this.label3.AutoSize = true;
            this.label3.Location = new System.Drawing.Point(3, 85);
            this.label3.Name = "label3";
            this.label3.Size = new System.Drawing.Size(143, 12);
            this.label3.TabIndex = 7;
            this.label3.Text = "Web Socket Convert Log";
            // 
            // label2
            // 
            this.label2.AutoSize = true;
            this.label2.Location = new System.Drawing.Point(3, 46);
            this.label2.Name = "label2";
            this.label2.Size = new System.Drawing.Size(146, 12);
            this.label2.TabIndex = 6;
            this.label2.Text = "Connectivity Convert Log";
            // 
            // label1
            // 
            this.label1.AutoSize = true;
            this.label1.Location = new System.Drawing.Point(3, 6);
            this.label1.Name = "label1";
            this.label1.Size = new System.Drawing.Size(105, 12);
            this.label1.TabIndex = 5;
            this.label1.Text = "Log Target Folder";
            // 
            // tbxWebSocketDestFolder
            // 
            this.tbxWebSocketDestFolder.Location = new System.Drawing.Point(3, 100);
            this.tbxWebSocketDestFolder.Name = "tbxWebSocketDestFolder";
            this.tbxWebSocketDestFolder.Size = new System.Drawing.Size(508, 21);
            this.tbxWebSocketDestFolder.TabIndex = 4;
            // 
            // tbxConnDestFolder
            // 
            this.tbxConnDestFolder.Location = new System.Drawing.Point(3, 61);
            this.tbxConnDestFolder.Name = "tbxConnDestFolder";
            this.tbxConnDestFolder.Size = new System.Drawing.Size(508, 21);
            this.tbxConnDestFolder.TabIndex = 3;
            // 
            // tbxLogTargetFolder
            // 
            this.tbxLogTargetFolder.Location = new System.Drawing.Point(3, 21);
            this.tbxLogTargetFolder.Name = "tbxLogTargetFolder";
            this.tbxLogTargetFolder.Size = new System.Drawing.Size(508, 21);
            this.tbxLogTargetFolder.TabIndex = 2;
            // 
            // splitContainer1
            // 
            this.splitContainer1.Dock = System.Windows.Forms.DockStyle.Fill;
            this.splitContainer1.Location = new System.Drawing.Point(0, 179);
            this.splitContainer1.Name = "splitContainer1";
            // 
            // splitContainer1.Panel1
            // 
            this.splitContainer1.Panel1.Controls.Add(this.groupBox1);
            // 
            // splitContainer1.Panel2
            // 
            this.splitContainer1.Panel2.Controls.Add(this.groupBox2);
            this.splitContainer1.Size = new System.Drawing.Size(722, 405);
            this.splitContainer1.SplitterDistance = 361;
            this.splitContainer1.TabIndex = 3;
            // 
            // groupBox1
            // 
            this.groupBox1.Controls.Add(this.rTbxConnStatus);
            this.groupBox1.Dock = System.Windows.Forms.DockStyle.Fill;
            this.groupBox1.Location = new System.Drawing.Point(0, 0);
            this.groupBox1.Name = "groupBox1";
            this.groupBox1.Size = new System.Drawing.Size(361, 405);
            this.groupBox1.TabIndex = 0;
            this.groupBox1.TabStop = false;
            this.groupBox1.Text = "Connectivity Log";
            // 
            // rTbxConnStatus
            // 
            this.rTbxConnStatus.Dock = System.Windows.Forms.DockStyle.Fill;
            this.rTbxConnStatus.Location = new System.Drawing.Point(3, 17);
            this.rTbxConnStatus.Name = "rTbxConnStatus";
            this.rTbxConnStatus.Size = new System.Drawing.Size(355, 385);
            this.rTbxConnStatus.TabIndex = 0;
            this.rTbxConnStatus.Text = "";
            // 
            // groupBox2
            // 
            this.groupBox2.Controls.Add(this.rTbxWebSocketStatus);
            this.groupBox2.Dock = System.Windows.Forms.DockStyle.Fill;
            this.groupBox2.Location = new System.Drawing.Point(0, 0);
            this.groupBox2.Name = "groupBox2";
            this.groupBox2.Size = new System.Drawing.Size(357, 405);
            this.groupBox2.TabIndex = 0;
            this.groupBox2.TabStop = false;
            this.groupBox2.Text = "Web Socket Log";
            // 
            // rTbxWebSocketStatus
            // 
            this.rTbxWebSocketStatus.Dock = System.Windows.Forms.DockStyle.Fill;
            this.rTbxWebSocketStatus.Location = new System.Drawing.Point(3, 17);
            this.rTbxWebSocketStatus.Name = "rTbxWebSocketStatus";
            this.rTbxWebSocketStatus.Size = new System.Drawing.Size(351, 385);
            this.rTbxWebSocketStatus.TabIndex = 1;
            this.rTbxWebSocketStatus.Text = "";
            // 
            // label4
            // 
            this.label4.AutoSize = true;
            this.label4.Location = new System.Drawing.Point(3, 125);
            this.label4.Name = "label4";
            this.label4.Size = new System.Drawing.Size(94, 12);
            this.label4.TabIndex = 10;
            this.label4.Text = "JSON Message";
            // 
            // tbxJSONDestFolder
            // 
            this.tbxJSONDestFolder.Location = new System.Drawing.Point(3, 140);
            this.tbxJSONDestFolder.Name = "tbxJSONDestFolder";
            this.tbxJSONDestFolder.Size = new System.Drawing.Size(508, 21);
            this.tbxJSONDestFolder.TabIndex = 9;
            // 
            // btnConvertJSON
            // 
            this.btnConvertJSON.Location = new System.Drawing.Point(517, 138);
            this.btnConvertJSON.Name = "btnConvertJSON";
            this.btnConvertJSON.Size = new System.Drawing.Size(193, 23);
            this.btnConvertJSON.TabIndex = 8;
            this.btnConvertJSON.Text = "Convert JSON";
            this.btnConvertJSON.UseVisualStyleBackColor = true;
            this.btnConvertJSON.Click += new System.EventHandler(this.btnConvertJSON_Click);
            // 
            // RemoveForm
            // 
            this.AutoScaleDimensions = new System.Drawing.SizeF(7F, 12F);
            this.AutoScaleMode = System.Windows.Forms.AutoScaleMode.Font;
            this.ClientSize = new System.Drawing.Size(722, 584);
            this.Controls.Add(this.splitContainer1);
            this.Controls.Add(this.panel1);
            this.Name = "RemoveForm";
            this.Text = "Form1";
            this.panel1.ResumeLayout(false);
            this.panel1.PerformLayout();
            this.splitContainer1.Panel1.ResumeLayout(false);
            this.splitContainer1.Panel2.ResumeLayout(false);
            this.splitContainer1.ResumeLayout(false);
            this.groupBox1.ResumeLayout(false);
            this.groupBox2.ResumeLayout(false);
            this.ResumeLayout(false);

        }

        #endregion

        private System.Windows.Forms.Button btnConvertWebSocket;
        private System.Windows.Forms.Button btnConvertConnectivity;
        private System.Windows.Forms.Panel panel1;
        private System.Windows.Forms.Label label3;
        private System.Windows.Forms.Label label2;
        private System.Windows.Forms.Label label1;
        private System.Windows.Forms.TextBox tbxWebSocketDestFolder;
        private System.Windows.Forms.TextBox tbxConnDestFolder;
        private System.Windows.Forms.TextBox tbxLogTargetFolder;
        private System.Windows.Forms.SplitContainer splitContainer1;
        private System.Windows.Forms.GroupBox groupBox1;
        private System.Windows.Forms.RichTextBox rTbxConnStatus;
        private System.Windows.Forms.GroupBox groupBox2;
        private System.Windows.Forms.RichTextBox rTbxWebSocketStatus;
        private System.Windows.Forms.Label label4;
        private System.Windows.Forms.TextBox tbxJSONDestFolder;
        private System.Windows.Forms.Button btnConvertJSON;
    }
}

