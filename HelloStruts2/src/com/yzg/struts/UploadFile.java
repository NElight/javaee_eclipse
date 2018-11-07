package com.yzg.struts;

import java.io.File;
import org.apache.commons.io.FileUtils;

import com.opensymphony.xwork2.ActionSupport;

import java.io.IOException;

public class UploadFile extends ActionSupport {
	private File myFile;
	private String myFileContentType;
	private String myFileFileName;
	private String destPath;
	
	public String execute() {
		/* Copy file to a safe location */
	      destPath = "D:\\";

	      try{
	     	 System.out.println("Src File name: " + myFile);
	     	 System.out.println("Dst File name: " + myFileFileName);
	     	    	 
	     	 File destFile  = new File(destPath, myFileFileName);
	    	 FileUtils.copyFile(myFile, destFile);
	  
	      }catch(IOException e){
	         e.printStackTrace();
	         return ERROR;
	      }

	      return SUCCESS;
	}
	
	public File getMyFile() {
	      return myFile;
	   }
	
	   public void setMyFile(File myFile) {
	      this.myFile = myFile;
	   }
	   public String getMyFileContentType() {
	      return myFileContentType;
	   }
	   public void setMyFileContentType(String myFileContentType) {
	      this.myFileContentType = myFileContentType;
	   }
	   public String getMyFileFileName() {
	      return myFileFileName;
	   }
	   public void setMyFileFileName(String myFileFileName) {
	      this.myFileFileName = myFileFileName;
	   }
	
	
}
