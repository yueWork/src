package com.org;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileItem;  
import org.apache.commons.fileupload.FileUploadException;  
import org.apache.commons.fileupload.disk.DiskFileItemFactory;  
import org.apache.commons.fileupload.servlet.ServletFileUpload;

import com.alibaba.fastjson.JSONObject; 


public class ImgUpload extends HttpServlet {
	 protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {  
	        //需要返回的fileName  
	        String fileName = null;  
//	        response.setContentType("application/json");
	        PrintWriter out = response.getWriter();
	        
	        boolean isMultipart = ServletFileUpload.isMultipartContent(request);  
	          
	        // Create a factory for disk-based file items  
	        DiskFileItemFactory factory = new DiskFileItemFactory();  
	  
	        // Configure a repository (to ensure a secure temp location is used)  
	        ServletContext servletContext = this.getServletConfig().getServletContext();  
	        File repository = (File) servletContext.getAttribute("javax.servlet.context.tempdir");  
	        factory.setRepository(repository);  
	  
	        // Create a new file upload handler  
	        ServletFileUpload upload = new ServletFileUpload(factory);  
	  
	        // Parse the request  
	        try {  
	            List<FileItem> items = upload.parseRequest(request);  
	            for(FileItem item : items) {  
	                //其他参数  
	                String type = item.getContentType();  
	                if(type == null) {  
//	                  System.out.println(item.getString(item.getFieldName()));  
	                    continue;  
	                }  
	                  
	                //文件参数  
//	                fileName = "userimg.jpg";  
	                fileName=item.getName();
	                fileName=fileName.replace("&", "");
	                System.out.println("filename"+fileName);  
	                //设置保存文件路径  
	                String realPath1 = request.getServletContext().getRealPath("/userImg"); 
	                System.out.println(realPath1);
	                File test=new File(realPath1);
	                if(!test.exists()&&!test.isDirectory()){
	                	test.mkdir();
	                }
	                
	                File f = new File(test, fileName);  	                  
	                if(f.exists()) {  
	                    f.delete();  
	                }  
	                f.createNewFile();  	                  
	                //保存  
	                item.write(f);  
	                  
	            }  
	        } catch (FileUploadException e) {  
	            e.printStackTrace();  
	        } catch (Exception e) {  
	            e.printStackTrace();  
	        }  
	        //返回结果  
//	        JSONObject obj = new JSONObject();  
//	        obj.put("fileName", fileName);  
//	        response.getWriter().print(obj.toJSONString());
	        out.print("{\"filename\":"+fileName+"}");  
	    }  
}
