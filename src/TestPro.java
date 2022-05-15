import it.unisa.dia.gas.jpbc.Element;
import it.unisa.dia.gas.jpbc.ElementPowPreProcessing;
import it.unisa.dia.gas.jpbc.Pairing;
import it.unisa.dia.gas.plaf.jpbc.pairing.PairingFactory;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.math.BigInteger;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;


public class TestPro {
public static void main(String[] args) throws IOException
{   
	Initial iniSystem=new Initial();
	String filePath="test.data";
	String corruption="corruption.data";
	BigInteger sequence=new BigInteger("0");
	FileInputStream fis;
	fis = new FileInputStream(filePath);
	Element OBUsecretKey= iniSystem.getPairing().getZr().newRandomElement().getImmutable();
	//System.out.println("OBU��˽Կ�ǣ�"+OBUsecretKey);
	Element OBUPublicKey=iniSystem.getPubOBU(OBUsecretKey).getImmutable();
	//System.out.println("OBU�Ĺ�Կ�ǣ�"+OBUPublicKey);
	Element UserSecretKey= iniSystem.getPairing().getZr().newRandomElement().getImmutable();
	//System.out.println("User��˽Կ�ǣ�"+UserSecretKey);
	Element UserPublicKey=iniSystem.getPubUser(UserSecretKey).getImmutable();
	//System.out.println("User�Ĺ�Կ�ǣ�"+UserPublicKey);
	
	ElementPowPreProcessing g=iniSystem.getGenerator();
	WriteFile wf=new WriteFile(filePath);
	wf.SystemInfo(iniSystem.getGeneratorPre());
	wf.OBUInfo(OBUsecretKey,OBUPublicKey);
	wf.UserInfo(UserSecretKey,UserPublicKey);
	Sign signtest=new Sign(iniSystem.getPairing(), 
			iniSystem.getGenerator(), 
			UserSecretKey.getImmutable(),
			OBUsecretKey.getImmutable());
	 byte[] bys = new byte[Parameter.blockSize];//1024����������
     int len;
     long beginTime=System.currentTimeMillis();
     wf.writeBegin(filePath);
     while ((len = fis.read(bys)) != -1) {
   // 	System.out.println(len);
     	Element tagUser=signtest.signUser(bys, sequence);
		Element tagOBU=signtest.signPDA(bys, sequence);
		Element tagAggre=signtest.sigAggregate(tagUser, tagOBU);
		//Element T=signtest.signPDAV(bys, sequence);
		//Element L=iniSystem.getPairing().pairing(tagAggre, iniSystem.getGeneratorPre().getImmutable());
		//Element R=iniSystem.getPairing().pairing(T,iniSystem.getGeneratorPre().powZn(UserSecretKey.add(OBUsecretKey)));
		//Element R=iniSystem.getPairing().pairing(T,UserPublicKey.mul(OBUPublicKey));
		//System.out.println("L"+L);
		//System.out.println("R"+R);
		//System.out.println("�����ǩ"+sequence+"...");
		wf.TagInfo(iniSystem.getPairing(),sequence, tagUser, tagOBU, tagAggre,bys);
		sequence=sequence.add(BigInteger.ONE);
     }
     long endTime=System.currentTimeMillis();
     wf.writeEnd(sequence,endTime-beginTime);
    fis.close();
    ReadFile tagFile=new ReadFile("_FileTag.Sig");
    //System.out.println(tagFile.get_aTag(0));
    //System.out.println(tagFile.get_aTag(1000));
    Challenge chall=new Challenge(tagFile.getBlocksNumber(),Parameter.CHLLAN,iniSystem.getPairing());
    HashMap<BigInteger,Element> chaInfo=chall.selectChallengeNumberBlocks();
    Proof pro1=new Proof(iniSystem.getPairing(),chaInfo,tagFile,filePath);
    Proof pro2=new Proof(iniSystem.getPairing(),chaInfo,tagFile,corruption);
    System.out.println("the Proof1 is: ("+pro1.M+"\n"+pro1.Sig+")");
    Verify verifier1=new Verify(iniSystem,chaInfo,pro1);
    boolean result1=verifier1.check(OBUPublicKey, UserPublicKey);
    System.out.println("The check result is:"+result1);
    System.out.println("the Proof2 is: ("+pro2.M+"\n"+pro2.Sig+")");
    Verify verifier2=new Verify(iniSystem,chaInfo,pro2);
    boolean result2=verifier2.check(OBUPublicKey, UserPublicKey);
    System.out.println("The check result is:"+result2);
		}
	

	}

