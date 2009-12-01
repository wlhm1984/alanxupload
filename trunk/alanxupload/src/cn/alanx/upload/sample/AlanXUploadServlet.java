package cn.alanx.upload.sample;

import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

public class AlanXUploadServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static final String ALANX_UPLOAD_FOLDER = "AlanX_Upload_Folder";
	public  static final String ALANX_UPLOAD_SERVLET = "AlanXUploadServlet";
	public AlanXUploadServlet() {
		super();
	}

	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		this.doPost(request, response);
	}

	@SuppressWarnings("unchecked")
	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException {

		try {
			request.setCharacterEncoding("UTF-8");
		} catch (UnsupportedEncodingException e1) {
			e1.printStackTrace();
		}
		//磁盘文件条目工厂
		DiskFileItemFactory factory = new DiskFileItemFactory();

		// 文件上传如果文件小,上传组件可以将文件存放在内存中,如果过大时会放在临时目录里面,之后再通过IO流操作
		//获取目录真实路径   / 代表 WebRoot目录下面

		String path = request.getSession().getServletContext().getRealPath(
				"/"+ALANX_UPLOAD_FOLDER);
		System.out.println(path);
		File pathFile = new File(path);
		if (!pathFile.exists()) {
			pathFile.mkdir();
		}
		//设置临时目录
		factory.setRepository(new File(path));
		//设置上传文件大小

		factory.setSizeThreshold(1024 * 1024);
		//创建一个ServletFileUpload 实例 

		ServletFileUpload sfu = new ServletFileUpload(factory);
		try {
			//解析请求,取得FileItem 列表
			List<FileItem> lis = sfu.parseRequest(request);
			//循环遍历
			for (FileItem item : lis) {
				//判断是否是简单的表单字段
				if (item.isFormField()) {
					String name = item.getFieldName();
					String value = item.getString("UTF-8");
					request.setAttribute(name, value);
				} else {
					//取得字段名
					String name = item.getFieldName();
					//       取得文件路径名
					String value = item.getName();
					System.out.println(value);
					//为了屏蔽各个浏览器提供的路径不同异常
					int start = value.lastIndexOf("\\");
					//取得文件名
					String fileName = value.substring(start + 1);
					request.setAttribute(name, fileName);
					item.write(new File(path, fileName)); //这句代码与下面高亮显示的代表功能相同
				}
			}
		} catch (FileUploadException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		try {
			response.getWriter().print("success");
			response.getWriter().close();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

}
