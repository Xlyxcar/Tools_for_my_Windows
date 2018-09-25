import java.io.File;
import java.io.FileWriter;
import java.io.RandomAccessFile;
import java.io.UnsupportedEncodingException;
import java.net.URI;

public class PrintDir {
    
    private static final String TXT_NAME = "printDir_dir.txt";
    
	public static void main(String[] args) throws Exception {
        
		// 获取根目录
        String jarWholePath = PrintDir.class.getProtectionDomain().getCodeSource().getLocation().getFile();
        //System.out.println(jarWholePath);
        try {
            jarWholePath = java.net.URLDecoder.decode(jarWholePath, "UTF-8");
        } catch (UnsupportedEncodingException e) { System.out.println(e.toString()); }
        String root = new File(jarWholePath).getParentFile().getAbsolutePath();
        
    	// 创建txt文件
        File file = new File(root);
        File word = new File(root + "\\" + TXT_NAME);
        //System.out.println("path:"+word.getPath());
        if(!word.exists()){
            word.createNewFile();
        }
    	FileWriter fw = new FileWriter(word);
    	fw.write("");
    	
    	RandomAccessFile raf = new RandomAccessFile(word, "rw");
    	
		// 读取下一级目录
    	printFile(file, raf, 0);
    	
    	// 关闭raf
    	raf.close();
    	fw.close();
    }
	
	public static void printFile(File file, RandomAccessFile raf, int grade) throws Exception{
        if("PrintDir.jar".equals(file.getName()) || TXT_NAME.equals(file.getName())) return;
		//System.out.println(file.getName());
		for(int i=0; i<grade; i++){
			raf.write("    ".getBytes("utf-8"));
		}
		raf.write(("├"+file.getName().toString()).getBytes("utf-8"));
		raf.write('\n');
		if(file.isDirectory()){
			File[] files = file.listFiles();
            cSort(files);
			for(File f : files){
				printFile(f, raf, grade+1);
			}
		}
	}
    
    // 将所有文件放在所有文件夹下面
    public static void cSort(File[] list){
        for(int i=0; i<list.length-1; i++){
            for(int j=i+1; j>0; j--){
                if(!list[j].isFile() && !list[j-1].isDirectory()){
                    File t = list[j];
                    list[j] = list[j -1];
                    list[j - 1] = t;
                }else{
                    break;
                }
            }
        }
    }
}