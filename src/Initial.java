import it.unisa.dia.gas.jpbc.Element;
import it.unisa.dia.gas.jpbc.ElementPowPreProcessing;
import it.unisa.dia.gas.jpbc.Pairing;
import it.unisa.dia.gas.plaf.jpbc.pairing.PairingFactory;
import java.math.BigInteger;

public class Initial {
	private Pairing pairing=PairingFactory.getPairing("a.properties");
	private Element g=pairing.getG1().newRandomElement().getImmutable();
	private ElementPowPreProcessing ppp =g.getElementPowPreProcessing();
	public Pairing getPairing() {
		return pairing;
	}
	public Element getGeneratorPre() {
		return g;		
	}
	public ElementPowPreProcessing getGenerator() {
		return ppp;		
	}
	
	public Element getPubOBU(Element PrivateOBU) {
		return ppp.powZn(PrivateOBU);
	} 
	
    public Element getPubUser(Element PrivateUser) {
		return ppp.powZn(PrivateUser);
	}
}


