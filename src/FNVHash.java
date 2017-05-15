
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public final class FNVHash {

    private static final long FNV_64_INIT = 0xcbf29ce484222325L;
    private static final long FNV_64_PRIME = 0x100000001b3L;

    private static final int FNV_32_INIT = 0x811c9dc5;
    private static final int FNV_32_PRIME = 0x01000193;
    private static final BigInteger FNV_32_PRIME_BI = new BigInteger("109951168211");
    public static String getHashMD5(String string) {
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            BigInteger bi = new BigInteger(1, md.digest(string.getBytes()));
            return bi.toString(16);
        } catch (NoSuchAlgorithmException ex) {
            

            return "";
        }
    }
    private static final BigInteger FNV_32_MOD = new BigInteger("2").pow(64);
    public FNVHash() {}
    
    public static int H_function(int k,String S){
    	String S1=getHashMD5(S);
    	int Sum=0;
    	for(int i=0;i<S1.length();i++)
    		Sum+=(i+1)*S1.charAt(i);
    	return Math.abs((int)(hash64(S)+k*Sum));
    }
    public static int hash32(final byte[] k) {
        int rv = FNV_32_INIT;
        final int len = k.length;
        for(int i = 0; i < len; i++) {
            rv ^= k[i];
            rv *= FNV_32_PRIME;
        }
        return rv;
    }

    public static long hash64(final byte[] k) {
        long rv = FNV_64_INIT;
        final int len = k.length;
        for(int i = 0; i < len; i++) {
            rv ^= k[i];
            rv *= FNV_64_PRIME;
        }
        return rv;
    }

    public static int hash32(final String k) {
        int rv = FNV_32_INIT;
        final int len = k.length();
        for(int i = 0; i < len; i++) {
            rv ^= k.charAt(i);
            rv *= FNV_32_PRIME;
        }
        return rv;
    }

    public static long hash64(final String k) {
        long rv = FNV_64_INIT;
        final int len = k.length();
        for(int i = 0; i < len; i++) {
            rv ^= k.charAt(i);
            rv *= FNV_64_PRIME;
        }
        return rv;
    }

}