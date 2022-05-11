import it.unisa.dia.gas.jpbc.Element;
import it.unisa.dia.gas.jpbc.ElementPowPreProcessing;
import it.unisa.dia.gas.jpbc.Pairing;
import it.unisa.dia.gas.plaf.jpbc.pairing.PairingFactory;
import java.math.BigInteger;
public class Sign {
	private Pairing pairing=null;
	private ElementPowPreProcessing generator;
	 Element userSecretKey;
	 Element pdaSecretKey;
	public Sign(Pairing pairingSystem, 
			ElementPowPreProcessing generatorSystem, 
			Element userSecretKeySystem,
			Element PDAsecretKeySystem) {
		pairing=pairingSystem;
		generator=generatorSystem;
		userSecretKey=userSecretKeySystem;
		pdaSecretKey=PDAsecretKeySystem;
	}
public Element signPDA(byte[] bys, BigInteger sequece) {
	 Element blockHashValue1=pairing.getZr().newElementFromBytes(bys);
	 Element g_Pow_Mi1=generator.powZn(blockHashValue1);
	 Element H=pairing.getG1().newElementFromBytes(("TransNo"+sequece).getBytes());
	 Element H_Mul_g_Pow_Mi1=H.mul(g_Pow_Mi1);
	 Element sig_PDA=H_Mul_g_Pow_Mi1.powZn(pdaSecretKey);
	 return sig_PDA;
	 }

public Element signPDAV(byte[] bys, BigInteger sequece) {
	 Element blockHashValue1=pairing.getZr().newElementFromBytes(bys);
	 Element g_Pow_Mi1=generator.powZn(blockHashValue1);
	 Element hi_Mul_g_Pow_Mi1=g_Pow_Mi1.pow(sequece);
	// Element sig_PDA=hi_Mul_g_Pow_Mi1.powZn(pdaSecretKey);
	 return hi_Mul_g_Pow_Mi1;
	 }
public Element signUser(byte[] bys, BigInteger sequece) {
	 Element blockHashValue2=pairing.getZr().newElementFromBytes(bys);
	 Element g_Pow_Mi2=generator.powZn(blockHashValue2);
	 Element H=pairing.getG1().newElementFromBytes(("TransNo"+sequece).getBytes());
	 Element H_Mul_g_Pow_Mi2=H.mul(g_Pow_Mi2);
	 Element sig_User=H_Mul_g_Pow_Mi2.powZn(userSecretKey);
	 return sig_User;
	 }
public Element sigAggregate(Element User_tag, Element PDA_tag) {
	Element tagAggre=PDA_tag.getImmutable().mul(User_tag.getImmutable());
	 Element h=pairing.getZr().newElementFromBytes("PK1||PK2".getBytes());
	 Element tagAggre_h=tagAggre.powZn(h);
	return tagAggre_h;
}
}
