package io.coin.ccbc.support;

import java.nio.charset.StandardCharsets;
import org.bouncycastle.jcajce.provider.digest.Keccak;

public class Hash {

  public static byte[] sha3(byte[] input) {
    return sha3(input, 0, input.length);
  }

  public static byte[] sha3(byte[] input, int offset, int length) {
    Keccak.DigestKeccak kecc = new Keccak.Digest256();
    kecc.update(input, offset, length);
    return kecc.digest();
  }

  public static String sha3String(String utf8String) {
    return Converter.byteArrayToHexString(sha3(utf8String.getBytes(StandardCharsets.UTF_8)));
  }
}
