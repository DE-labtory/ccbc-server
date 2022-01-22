package io.coin.ccbc.support;

public class Converter {

  public static String makeChecksumAddress(String address) {
    String lowercaseAddress = remove0x(address).toLowerCase();
    String addressHash = remove0x(Hash.sha3String(lowercaseAddress));
    StringBuilder result = new StringBuilder(lowercaseAddress.length() + 2);
    result.append("0x");
    for (int i = 0; i < lowercaseAddress.length(); i++) {
      if (Integer.parseInt(String.valueOf(addressHash.charAt(i)), 16) >= 8) {
        result.append(String.valueOf(lowercaseAddress.charAt(i)).toUpperCase());
      } else {
        result.append(lowercaseAddress.charAt(i));
      }
    }
    return result.toString();
  }

  public static String add0x(String hexString) {
    if (hexString.length() >= 2 && hexString.startsWith("0x")) {
      return hexString;
    }
    return "0x" + hexString;
  }

  public static String remove0x(String hexString) {
    if (hexString.length() > 2 && hexString.startsWith("0x")) {
      return hexString.substring(2);
    }
    return hexString;
  }

  public static String byteArrayToHexString(byte[] byteArray) {
    StringBuilder sb = new StringBuilder();
    for (byte b : byteArray) {
      sb.append(String.format("%02x", b));
    }
    return "0x" + sb.toString();
  }

  public static byte[] hexStringToByteArray(String hexString) {
    String target = hexString;
    if (target == null) {
      return null;
    }
    target = remove0x(target);
    if (target.length() == 0) {
      return null;
    }

    byte[] ba = new byte[target.length() / 2];
    for (int i = 0; i < ba.length; i++) {
      ba[i] = (byte) Integer.parseInt(target.substring(2 * i, 2 * i + 2), 16);
    }
    return ba;
  }
}
