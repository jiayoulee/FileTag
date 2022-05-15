import java.math.BigInteger;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Random;

import it.unisa.dia.gas.jpbc.Element;
import it.unisa.dia.gas.jpbc.Pairing;

public class Challenge{
	int fileBlocksNumber;
	int challengeNumber;
	Pairing sys;
	public Challenge(int fileBlocks, int challenge,Pairing pairingSystem) {
		this.fileBlocksNumber=fileBlocks;
		this.challengeNumber=challenge;
		this.sys=pairingSystem;
	}
	HashMap<BigInteger,Element> selectChallengeNumberBlocks(){
		Random r = new Random();
		HashMap<BigInteger,Element> challengeInfo=new HashMap<BigInteger,Element>();
		while(challengeInfo.size()<this.challengeNumber) {
			BigInteger blockNum=new BigInteger(""+r.nextInt(this.fileBlocksNumber));
			Element v_j=sys.getZr().newRandomElement().getImmutable();
			challengeInfo.put(blockNum,v_j);
		}
		return challengeInfo;
	}

}