import java.io.File;
import java.io.FileOutputStream;

import it.unisa.dia.gas.jpbc.Element;
import it.unisa.dia.gas.jpbc.ElementPowPreProcessing;
import it.unisa.dia.gas.jpbc.Pairing;
import it.unisa.dia.gas.plaf.jpbc.pairing.PairingFactory;
import java.math.BigInteger;
import java.io.FileWriter;
import java.io.IOException;
import java.io.OutputStream;
import java.math.BigInteger;

import it.unisa.dia.gas.jpbc.Element;
import it.unisa.dia.gas.jpbc.ElementPowPreProcessing;
public class WriteFile {
	String fileName;
	public WriteFile(String filePath) {
		fileName=filePath;
	}
	public void OBUInfo(Element OBUsecret, Element OBUpublic) {
		try{
	        String content = "OBU的私钥是："+OBUsecret+"\n"+"OBU的公钥是："+OBUpublic;
	        File file =new File("OBU.Key");
	        if(!file.exists()){
	        	file.createNewFile();
	        }
	        //使用true，即进行append file 
	        FileWriter fileWritter = new FileWriter(file.getName(),false);
	        fileWritter.write(content);
	        fileWritter.close();
	        System.out.println("OBU_finish");
	    }catch(IOException e){
	        e.printStackTrace();
	    }
	}
	public void UserInfo(Element Usersecret, Element Userpublic) {
		try{
	        String content = "User的私钥是："+Usersecret+"\n"+"User的公钥是："+Userpublic;
	        File file =new File("User.Key");
	        if(!file.exists()){
	        	file.createNewFile();
	        }
	        //使用true，即进行append file 
	        FileWriter fileWritter = new FileWriter(file.getName(),false);
	        fileWritter.write(content);
	        fileWritter.close();
	        System.out.println("User_finish");
	    }catch(IOException e){
	        e.printStackTrace();
	    }
	}
	public void SystemInfo(Element g) {
		try{
	        String content = "生成元是："+g;
	        File file =new File("PublicParameter.data");
	        if(!file.exists()){
	        	file.createNewFile();
	        }
	        //使用true，即进行append file 
	        FileWriter fileWritter = new FileWriter(file.getName(),false);
	        fileWritter.write(content);
	        fileWritter.close();
	        System.out.println("PublicParameter_finish");
	    }catch(IOException e){
	        e.printStackTrace();
	    }
	}
	public void TagInfo(Pairing p, BigInteger index, Element Utag,Element ObuTag, Element AggreTag, byte[] bys) {
		try{
			ByteToString convertor=new ByteToString();
			Element blockHashValue1=p.getZr().newElementFromBytes(bys);
	        String content = "\nblock"+index+":"+
	        "\n        utag：\n"+Utag+
	        "\n        utag(HEX)：\n"+convertor.byte2Hex(Utag.toBytes())+
	        "\n        Otag：\n"+ObuTag+
	        "\n        Otag(HEX)：\n"+convertor.byte2Hex(ObuTag.toBytes())+
	        "\n        atag：\n"+AggreTag+
	        "\n        atag(HEX)：\n"+convertor.byte2Hex(AggreTag.toBytes())+
	        "\n	    blockhash:\n"+blockHashValue1+
	        "\n--------------------------------------------------------------------------------------------";
	        File file =new File("_"+fileName+"_FileTag.Sig");
	        if(!file.exists()){
	        	file.createNewFile();
	        }
	        //使用true，即进行append file 
	        FileWriter fileWritter = new FileWriter(file.getName(),true);
	        fileWritter.write(content);
	        fileWritter.close();

	    }catch(IOException e){
	        e.printStackTrace();
	    }
	}
	public void writeEnd(BigInteger index,long time) {
		try{
	        String content = "\n##totally "+(index)+" blocks##"+
		    "10 KB size each block##"+
	        		time+"ms";
	        File file =new File("_"+fileName+"_FileTag.Sig");
	        if(!file.exists()){
	        	file.createNewFile();
	        }
	        //使用true，即进行append file 
	        FileWriter fileWritter = new FileWriter(file.getName(),true);
	        fileWritter.write(content);
	        fileWritter.close();
	        System.out.println("FileTag_writen");
	    }catch(IOException e){
	        e.printStackTrace();
	}
}
		public void writeBegin(String fileName) {
			try{
		        String content = "##"+fileName+"##";
		        File file =new File("_"+fileName+"_FileTag.Sig");
		        if(!file.exists()){
		        	file.createNewFile();
		        }
		        //使用true，即进行append file 
		        FileWriter fileWritter = new FileWriter(file.getName(),true);
		        fileWritter.write(content);
		        fileWritter.close();
		        //System.out.println("FileTag_writen");
		    }catch(IOException e){
		        e.printStackTrace();
		}
	}
}
