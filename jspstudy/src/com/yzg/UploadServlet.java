package com.yzg;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import javax.security.auth.message.callback.PrivateKeyCallback.Request;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

@WebServlet("/uploadfile")
public class UploadServlet extends HttpServlet {
	
	private static final String UPLOAD_DIRECTORY="upload";
	private static final int MEMARY_THRESHOLD = 1024 * 1024 * 3;
	private static final int MAX_FILE_SIZE = 1024 * 1024 * 40;
	private static final int MAX_REQUEST_SIZE = 1024 * 1024 * 50;
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// TODO Auto-generated method stub
		if (! ServletFileUpload.isMultipartContent(req)) {
			PrintWriter writer = resp.getWriter();
			writer.println("ERROR : must have enctype=multipart/form-data");
			writer.flush();
			return;
		}
		
		DiskFileItemFactory factory = new DiskFileItemFactory();
		factory.setSizeThreshold(MEMARY_THRESHOLD);
		factory.setRepository(new File(System.getProperty("java.io.tmpdir")));
		
		ServletFileUpload upload = new ServletFileUpload(factory);
		upload.setFileSizeMax(MAX_FILE_SIZE);
		upload.setSizeMax(MAX_REQUEST_SIZE);
		upload.setHeaderEncoding("UTF-8");
		
		String uploadPath = getServletContext().getRealPath("/") + File.separator + UPLOAD_DIRECTORY;
		File uploadDir = new File(uploadPath);
		if (!uploadDir.exists()) {
			uploadDir.mkdir();
		}
		
		try {
			List<FileItem> formitems = upload.parseRequest(req);
			if (formitems != null && formitems.size() > 0) {
				for (FileItem item: formitems) {
					if (!item.isFormField()) {
						String filename = new File(item.getName()).getName();
						String filepath = uploadPath+ File.separator + filename;
						File newfile = new File(filepath);
						System.out.println(filepath);
						item.write(newfile);
						req.setAttribute("message", "文件上传成功");
					}
				}
			}
		} catch (Exception e) {
			// TODO: handle exception
			req.setAttribute("message", "错误信息:" + e.getMessage());
			
		}
		getServletContext().getRequestDispatcher("/cainiao/message.jsp").forward(req, resp);
		
		
	}
	
	
}
