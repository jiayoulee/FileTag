import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
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
	public Proof(Pairing p, HashMap<BigInteger,Element> challInfo,ReadFile tagFile,File dataFile) throws IOException {
		M=p.getZr().newZeroElement();
		BigInteger[] testNums=new BigInteger[1000];
		Element[] challValue=new Element[1000];
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
		System.out.println(dataFile.getName());
		FileInputStream fs=new FileInputStream(dataFile);
		byte[] bys = new byte[1024*10];
		 while ((len = fs.read(bys)) != -1) {
				for(j=0;j<1000;j++) {
				if(sequence.equals(testNums[j]))
				{
					Element blockHashValue1=p.getZr().newElementFromBytes(bys);
					//System.out.println(testNums[j]+"#####################"+blockHashValue1);
					//System.out.println("ch:"+challValue[j]);
					M=M.add(blockHashValue1.mul(challValue[j]));
					//System.out.println("temp"+M);
					j++; 
				}
				}
				sequence=sequence.add(BigInteger.ONE);
		     }
	
	int index;
	Sig=p.getG1().newZeroElement();
	//System.out.println("Initial pro:"+Sig);
	for(int k=0;k<1000;k++) 
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
