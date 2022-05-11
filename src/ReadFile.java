import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.LineNumberReader;

	public class ReadFile {
	String tagFileName;
	public ReadFile(String tagFile) {
		this.tagFileName=tagFile;
	}
	public String getInfo() {
		File fl=new File(tagFileName);
		String info=readLastLineV0(fl);
		System.out.println(info);
		return info;
	}
	public int getBlocksNumber() {
		String[] tokens=this.getInfo().split("##");
		String[] num=tokens[1].split(" ");
		Integer value=new Integer(num[1]);
		return value;
	}
	public String get_uTag(int index) throws IOException {
		int row=4+index*16;
	    String aTag=this.ReadwithLine(row+1);
		return aTag;
	}
	public String get_OTag(int index) throws IOException {
		 int row=8+index*16;
		    String aTag=this.ReadwithLine(row+1);
			return aTag;
	}
	public String get_aTag(int index) throws IOException {
	    int row=12+index*16;
	    String aTag=this.ReadwithLine(row+1);
		return aTag;
	}
	private String readLastLineV0(File file) {
		  String lastLine = "";
		  try (BufferedReader bufferedReader = new BufferedReader(new FileReader(file))) {
		    String currentLine = "";
		    while ((currentLine = bufferedReader.readLine()) != null) {
		      lastLine = currentLine;
		    }
		  } catch (Exception e) {
		    System.out.println("file read error, msg:{}"+ e.getMessage());
		  }

		  return lastLine;
		}
	public String ReadwithLine(int lineNum) throws IOException {
		File file = new File(tagFileName);//
		FileReader fileReader = new FileReader(file);
		LineNumberReader reader = new LineNumberReader(fileReader); 
		String content=null;
		int lines = 0; 
		while (reader.readLine()!=null) { 
			lines++; 
			if (lines == lineNum) { 
				content=reader.readLine(); 
				}
		}
		reader.close(); 
		fileReader.close();
		return content;
	}
}
