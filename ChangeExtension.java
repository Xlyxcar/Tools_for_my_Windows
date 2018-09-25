import java.io.File;
import java.io.FilenameFilter;
import java.io.UnsupportedEncodingException;
import java.util.Scanner;

public class ChangeExtension {
    
	public static void main(String[] args) throws Exception {
        
		// 获取根目录
        String jarWholePath = ChangeExtension.class.getProtectionDomain().getCodeSource().getLocation().getFile();
        //System.out.println(jarWholePath);
        try {
            jarWholePath = java.net.URLDecoder.decode(jarWholePath, "UTF-8");
        } catch (UnsupportedEncodingException e) { System.out.println(e.toString()); }
        String root = new File(jarWholePath).getParentFile().getAbsolutePath();
        
    	// 创建txt文件
        File file = new File(root);
        //System.out.println("path:"+word.getPath());
    	
    	Scanner scan = new Scanner(System.in);
    	System.out.println("==模式选择==");
    	System.out.println("1 -> 为无后缀文件添加后缀\n"
    					+ "2 -> 修改文件后缀\n");
    	String oldExt;
    	if("1".equals(scan.nextLine())){
    		oldExt = "no_extension";
    	}else{
        	System.out.print("进入修改后缀名模式...\n输入需要修改的文件后缀: ");
        	oldExt = scan.nextLine();
    	}
    	System.out.print("你想要修改成什么: ");
    	String newExt = scan.nextLine();
    	
    	// 修改所有文件后缀
    	ChangeExtension(file, oldExt, newExt);
    	
    	// 关闭raf
    	scan.close();
    }
	
	public static void ChangeExtension(File root, String oldExt, String newExt){
		File[] list = root.listFiles(new FilenameFilterImpl(oldExt));
		String fileName = "";
		for(File file : list){
			fileName = file.getName();
			if("no_extension".equals(oldExt)){
				if(file.isDirectory()) continue;
				file.renameTo(new File(fileName + newExt));
			}else{
				file.renameTo(new File(fileName.substring(0, fileName.lastIndexOf('.')) + newExt));
			}
		}
	}
    
}

class FilenameFilterImpl implements FilenameFilter{
	public String ex = "";
	
	FilenameFilterImpl(String ex){
		this.ex = ex;
	}
	
	public boolean accept(File dir, String name) {
		if("no_extension".equals(ex)){
			return name.indexOf('.') == -1;
		}
		return name.endsWith(ex);
	}
}