/**
 * This is a simple notepad written in Java
 */
package org.liang.entity;

import java.awt.*;
import java.awt.event.*;
import java.io.*;
import javax.swing.*;

/**
 * 
 * @author shiro-liang-yi
 * @time 2018��6��2�� ����9:47:10
 */
public class FileEditor extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JTextField selectField; // �ļ��ľ���·���ı���
	private JTextArea editArea; // �༭��
	private JButton saveBtn; // �����桱��ť
	private JButton openFileBtn; // ���������ť

	private int level = 0; // ��¼Ŀ¼�����

	public FileEditor() {
		this.init();
	}

	private void init() {
		this.setTitle("�򵥼��±�");
		this.setBounds(300, 50, 600, 650);

		/*
		 * ���ı��ߣ���·�������������ť
		 */
		selectField = new JTextField(40);
		openFileBtn = new JButton("���");
		openFileBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ae) {
				FileEditor.this.level = 0;
				String path = selectField.getText();
				// ���Ŀ¼�����ļ�
				openDirOrFile(path.replaceAll("/", "\\"));
			}
		});
		JPanel upPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
		upPanel.setBackground(Color.CYAN);
		upPanel.add(selectField);
		upPanel.add(openFileBtn);
		this.add(upPanel, BorderLayout.NORTH);

		/*
		 * �ı��༭��
		 */
		editArea = new JTextArea();
		ScrollPane scollPanel = new ScrollPane(ScrollPane.SCROLLBARS_AS_NEEDED);
		scollPanel.add(editArea);
		this.add(scollPanel, BorderLayout.CENTER);

		/*
		 * ���水ť
		 */
		saveBtn = new JButton("����");
		saveBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ae) {
				// ����
				saveFile();
			}
		});
		JPanel southPanel = new JPanel();
		southPanel.setBackground(Color.green);
		southPanel.add(saveBtn);
		this.add(southPanel, BorderLayout.SOUTH);

		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setVisible(true);
	}

	/**
	 * �����ļ�
	 */
	private void saveFile() {
		FileDialog fd = new FileDialog(this, "�����ļ�");
		fd.setFile("*.java");
		// ����Ϊ�����桱ģʽ
		fd.setMode(FileDialog.SAVE);
		fd.setVisible(true);
		// ��ȡ�ļ���
		String fileName = fd.getFile();
		// ��ȡ�Ի���ĵ�ǰĿ¼
		String dir = fd.getDirectory();
		// ����Ŀ¼�����ļ�������һ���ļ�����Ҫ�����Ŀ���ļ�
		File newFile = new File(dir + File.separator + fileName);
		PrintWriter pw = null;
		try {
			pw = new PrintWriter(new OutputStreamWriter(new FileOutputStream(newFile)));

			String str = editArea.getText();
			pw.println(str);
			pw.flush();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			pw.close();
		}
	}

	/**
	 * ��Ŀ¼���ļ�
	 * 
	 * @param absolutePath
	 *            : ָ��Ŀ¼���ļ��ľ���·����
	 */
	private void openDirOrFile(String absolutePath) {
		File file = new File(absolutePath);

		if (!(file.exists())) {
			editArea.setText("�ļ�������!");
		} else if (file.isDirectory()) {
			editArea.setText(null);
			showDir(file);
		} else if (file.isFile()) {
			try {
				FileInputStream fis = new FileInputStream(file);
				BufferedReader br = new BufferedReader(new InputStreamReader(fis));
				String str = null;
				editArea.setText(null);
				while ((str = br.readLine()) != null) {
					editArea.append(str + "\r\n");
				}
				br.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

	}

	/**
	 * ���Ŀ¼,��������ͼ
	 * 
	 * @param directory
	 *            ����Ҫ�򿪵�Ŀ¼
	 */
	private void showDir(File directory) {
		File[] files = directory.listFiles();
		int len = files.length;
		for (int i = 0; i < len; i++) {
			if (files[i].isDirectory()) {
				for (int j = 0; j < this.level; j++) {
					editArea.append("  ");
				}
				editArea.append(this.level + 1 + "  +" + files[i].getName() + " �ļ���\r\n");
				this.level++;
				showDir(files[i]);
			} else if (files[i].isFile()) {
				for (int j = 0; j < this.level + 2; j++) {
					editArea.append(" ");
				}
				editArea.append(this.level + " �� ��" + files[i].getAbsolutePath() + "\r\n");
			}
		}
	}

	/**
	 * ����
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		new FileEditor();
	}

}
