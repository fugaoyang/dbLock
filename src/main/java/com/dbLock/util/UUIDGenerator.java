package com.dbLock.util;

import java.util.UUID;

public class UUIDGenerator {

	public static String getUuid() {
		UUID uId = UUID.randomUUID();
		String uuid = uId.toString();
		uuid = uuid.replaceAll("-", "").toUpperCase();
		uuid = "0" + uuid;
		uuid = hexToBinary(uuid);
		return binaryTo64(uuid);
	}

	private static String hexToBinary(String hex) {
		StringBuffer sb = new StringBuffer();
		if (hex == null || "".equals(hex))
			return "";
		for (int i = 0; i < hex.length(); i++) {
			sb.append(hexToBinary(hex.charAt(i)));
		}
		return sb.toString();
	}

	private static String hexToBinary(char hex) {
		String binary = null;
		switch (hex) {
		case '0':
			binary = "0000";
			break;
		case '1':
			binary = "0001";
			break;
		case '2':
			binary = "0010";
			break;
		case '3':
			binary = "0011";
			break;
		case '4':
			binary = "0100";
			break;
		case '5':
			binary = "0101";
			break;
		case '6':
			binary = "0110";
			break;
		case '7':
			binary = "0111";
			break;
		case '8':
			binary = "1000";
			break;
		case '9':
			binary = "1001";
			break;
		case 'A':
			binary = "1010";
			break;
		case 'B':
			binary = "1011";
			break;
		case 'C':
			binary = "1100";
			break;
		case 'D':
			binary = "1101";
			break;
		case 'E':
			binary = "1110";
			break;
		case 'F':
			binary = "1111";
			break;
		default:
			binary = "";
		}
		return binary;
	}

	private static String binaryTo64(String binary) {
		StringBuffer sb = new StringBuffer();
		if (binary == null || "".equals(binary))
			return "";
		if ((binary.length() % 6) != 0)
			return "";
		for (int i = 0; i < binary.length(); i += 6) {
			sb.append(binaryTo64a(binary.substring(i, i + 6)));
		}
		return sb.toString();
	}

	private static String binaryTo64a(String binary) {
		String alphabet = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789-_";
		return String.valueOf(alphabet.charAt(Integer.valueOf(binary, 2)));
	}
}
