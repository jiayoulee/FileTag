
public class ByteToString {
	public static String byte2Hex(byte[] bytes) {
	    if (bytes == null) {
	        return null;
	    }
	    StringBuilder builder = new StringBuilder();
	    // ����byte[]���飬��ÿ��byte����ת����16�����ַ�����ƴ���������ַ���
	    for (int i = 0; i < bytes.length; i++) {
	        // ÿ��byteת����16�����ַ�ʱ��bytes[i] & 0xff�����λ��0���������ȥ��������+0x100(�ڸ���λ��1)���ٽ�ȡ����λ�ַ�
	        builder.append(Integer.toString((bytes[i] & 0xff) + 0x100, 16).substring(1));
	    }
	    return builder.toString();
	}
	public static byte[] hex2Byte(String string) {
	    if (string == null || string.length() < 1) {
	        return null;
	    }
	    // ��Ϊһ��byte���������ַ������ȶ�Ӧ1:2������byte[]���鳤�����ַ�������һ��
	    byte[] bytes = new byte[string.length() / 2];
	    // ����byte[]���飬�����������ַ�������һ��
	    for (int i = 0; i < string.length() / 2; i++) {
	        // ��ȡû�����ַ���ǰһ��������תΪint��ֵ
	        int high = Integer.parseInt(string.substring(i * 2, i * 2 + 1), 16);
	        // ��ȡû�����ַ��ĺ�һ��������תΪint��ֵ
	        int low = Integer.parseInt(string.substring(i * 2 + 1, i * 2 + 2), 16);
	        // ��λ�ַ���Ӧ��intֵ*16+��λ��intֵ��ǿת��byte��ֵ����
	        // ��dd����λ13*16+��λ13=221(ǿת��byte������11011101����Ӧʮ����-35)
	        bytes[i] = (byte) (high * 16 + low);
	    }
	    return bytes;
	}
}
