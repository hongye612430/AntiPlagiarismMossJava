package com.twins.util;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * @author twins make for the file operation
 */

public class FileAboutExtension {
	public FileAboutExtension() {
	}
	private ArrayList<String> dirNameList = new ArrayList<String>();
	/**
	 * get file from a directory by twins
	 * reference of the language
	 * @param dir, language
	 */
	public List<File> getFileFromDir(final String dir, final String language) {
		if (!dir.equals("")) {
			File fileDir = new File(dir);
			List<File> filelist = new ArrayList<File>();
			File[] files = fileDir.listFiles();
			for (int i = 0; i < files.length; i++) {
				if(files[i].getName().endsWith(language) && files[i].isFile()) {
					filelist.add(files[i]);
				}
			}
			return filelist;
		}
		return null;
	}

	/**
	 * ͨ���ݹ���һ���ļ�Ŀ¼�µ������ļ����Լ������������������
	 * @param strPath
	 */
	public ArrayList<String> getDirectoryFiles(final String strPath) {
		ArrayList<String> fileNameList = new ArrayList<String>();
		try {
			File f = new File(strPath);
			if (f.isDirectory()) 
			{
				File[] fList = f.listFiles();
				for (int j = 0; j < fList.length; j++) {
					if (fList[j].isDirectory()) {
						System.out.println(fList[j].getPath());
						getDirectory(fList[j].getPath()); // ��getDir���������ֵ�����getDir��������
					}
				}
				for (int j = 0; j < fList.length; j++) {
					if (fList[j].isFile()) {
						fileNameList.add(fList[j].getPath());
					}
				}
			}
			return fileNameList;
		} catch (Exception e) {
			System.out.println("Error�� " + e);
		}
		fileNameList.add(strPath);
		return fileNameList;
	}
	
	/**
	 * ������һ����Ŀ¼�µ�������Ŀ¼����ˣ�����ͨ����ص�Ŀ¼������һЩ������
	 * �ݹ����Ŀ¼ע���һ���������Ŀ¼����Ҫ���
	 * @param strPath
	 * @return
	 */
	public ArrayList<String> getDirectory(final String strPath) {
		
		try {
			File f = new File(strPath);
			if (f.isDirectory()) 
			{
				File[] fList = f.listFiles();
				for (int j = 0; j < fList.length; j++) {
					if (fList[j].isDirectory()) {
						getDirectory(fList[j].getPath()); // ��getDir���������ֵ�����getDir��������
					}
				}
				dirNameList.add(strPath);
			}
			return dirNameList;
		} catch (Exception e) {
			System.out.println("Error�� " + e);
		}
		return null;
	}
	
	/**
	 * �ݹ�ɾ��һ��Ŀ¼�µ������ļ���
	 * @param strPath
	 * @return
	 */
	public boolean deleteDirectory(final String strPath) {
		try {
			File f = new File(strPath);
			if (f.isDirectory()) 
			{
				File[] fList = f.listFiles();
				for (int j = 0; j < fList.length; j++) {
					if (fList[j].isDirectory()) {
						deleteDirectory(fList[j].getPath()); // ��getDir���������ֵ�����getDir��������
					}
				}
				for (int j = 0; j < fList.length; j++) {

					if (fList[j].isFile()) {
						fList[j].delete();
					}
				}
			}
			return true;
		} catch (Exception e) {
			System.out.println("Error�� " + e);
		}
		return false;
	}
	
	public static void main(final String[] args) {
		FileAboutExtension fs = new FileAboutExtension();
		ArrayList<String> name = new ArrayList<String>();
		name = fs.getDirectory("F:\\uploadfile");
	    for(String n:name) {
	    	System.out.println(n);
	    }
		//fs.getDirectory("F:\\java\\");
	}
}
