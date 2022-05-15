import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import org.apache.commons.codec.digest.DigestUtils;
import it.unisa.dia.gas.jpbc.Element;
import it.unisa.dia.gas.jpbc.ElementPowPreProcessing;
import it.unisa.dia.gas.jpbc.Pairing;
import it.unisa.dia.gas.plaf.jpbc.pairing.PairingFactory;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.math.BigInteger;

public class Proof {
	public Element M;
	public Element Sig;
	public Proof(Pairing p, HashMap<BigInteger,Element> challInfo,ReadFile tagFile,String dataFile) throws IOException {
		M=p.getZr().newZeroElement();
		BigInteger[] testNums=new BigInteger[Parameter.CHLLAN];
		Element[] challValue=new Element[Parameter.CHLLAN];
		BigInteger sequence=new BigInteger("0");
		int i=0;
		int len;
		Iterator iter = challInfo.entrySet().iterator();
			while (iter.hasNext()) {
				Map.Entry entry = (Map.Entry) iter.next();
				testNums[i]= (BigInteger)entry.getKey();
				challValue[i] =(Element)entry.getValue();
				i++;
			}
		int j=0;
		System.out.println(dataFile);
		FileInputStream fs=new FileInputStream(dataFile);
		byte[] bys = new byte[Parameter.blockSize];
		 while ((len = fs.read(bys)) != -1) {
				for(j=0;j<Parameter.CHLLAN;j++) {
				if(sequence.equals(testNums[j]))
				{
					
			    Element blockHashValue=p.getZr().newElementFromBytes(DigestUtils.md5(bys));
				//	if((sequence.equals(new BigInteger("1045")) || (sequence.equals(new BigInteger("0"))))){
				//	System.out.println(testNums[j]+"#####################"+blockHashValue1);
				//	System.out.println("ch:"+challValue[j]);
				//	}
					M=M.add(blockHashValue.mul(challValue[j]));
				//	System.out.println("temp"+M);
					j++; 
				}
				}
				sequence=sequence.add(BigInteger.ONE);
		     }
	
	int index;
	Sig=p.getG1().newZeroElement();
	//System.out.println("Initial pro:"+Sig);
	for(int k=0;k<Parameter.CHLLAN;k++) 
	{
		index=testNums[k].intValue();
		String Hex=tagFile.get_aTag(index);
		//System.out.println("index"+index);
		//System.out.println("Hex"+Hex);
		Element e=p.getG1().newElementFromBytes(ByteToString.hex2Byte(Hex));
		//System.out.println("e"+e);
		Sig=Sig.add(e.powZn(challValue[k]));
		//System.out.println("now sum is"+Sig);
	}
	}
}
