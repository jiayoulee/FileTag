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

import it.unisa.dia.gas.jpbc.Element;

public class Verify {
	Initial iniSystem;
	HashMap<BigInteger,Element> chall;
	Proof pro;
	BigInteger[] testNums=new BigInteger[Parameter.CHLLAN];
	Element[] challValue=new Element[Parameter.CHLLAN];
	public Verify(Initial System,HashMap<BigInteger,Element> cha, Proof p) {
		iniSystem=System;
		chall=cha;
		pro=p;
		int i=0;
		Iterator iter = chall.entrySet().iterator();
		while (iter.hasNext()) {
			Map.Entry entry = (Map.Entry) iter.next();
			testNums[i]= (BigInteger)entry.getKey();
			challValue[i] =(Element)entry.getValue();
			i++;
		}
	}
	public boolean check(Element PK1, Element PK2) {
		Element H=iniSystem.getPairing().getG1().newZeroElement();
		for(int i=0;i<Parameter.CHLLAN;i++) {
			H=H.add(iniSystem.getPairing().getG1().newElementFromBytes(("TransNo"+testNums[i]).getBytes()).powZn(challValue[i]));
		}
		Element H_g_M=H.mul(iniSystem.getGeneratorPre().powZn(pro.M));
		Element P_P=PK1.mul(PK2);
		Element h=iniSystem.getPairing().getZr().newElementFromBytes("PK1||PK2".getBytes());
		Element P_P_h=P_P.powZn(h);
		Element Right=iniSystem.getPairing().pairing(H_g_M, P_P_h);
		Element Left=iniSystem.getPairing().pairing(pro.Sig, iniSystem.getGeneratorPre());
		return Right.equals(Left);
	}
	
}
