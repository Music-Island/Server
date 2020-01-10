import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.util.List;

public class getFile extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setCharacterEncoding("UTF-8");
        request.setCharacterEncoding("UTF-8");
        response.setContentType("text/html");
        PrintWriter out = response.getWriter();
        //输出到客户端浏览器
        DiskFileItemFactory factory = new DiskFileItemFactory();
        ServletFileUpload sup = new ServletFileUpload(factory);//这里要将factory传入，否则会报NullPointerException: No FileItemFactory has been set.
        try{
            List<FileItem> list = sup.parseRequest(request);
            for(FileItem fileItem:list){
                System.out.println(fileItem.getFieldName()+"--"+fileItem.getName());
                if(!fileItem.isFormField()){
                    if("file".equals(fileItem.getFieldName())){
                        //获取远程文件名
                        String remoteFilename = new String(fileItem.getName().getBytes(),"UTF-8");
                        File remoteFile = new File(remoteFilename);

                        //设置服务器端存放文件的位置
                        File locate = new File("/usr/tomcat/mapDownload",remoteFile.getName());

//                      locate.getParentFile().mkdirs();//用于确保文件目录存在,如果为单级目录可以去掉
                        locate.createNewFile(); //创建新文件

                        InputStream ins = fileItem.getInputStream();   //FileItem的内容
                        OutputStream ous = new FileOutputStream(locate); //输出
                        try{
                            byte[] buffer = new byte[1024]; //缓冲字节
                            int len = 0;
                            while((len = ins.read(buffer))>-1)
                                ous.write(buffer, 0, len);
                        }finally{
                            ous.close();
                            ins.close();
                        }
                    }
                }
            }
        }catch (FileUploadException e){}

        out.print("everything is ok");
        out.flush();
        out.close();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doPost(req, resp);
    }
}
