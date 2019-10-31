import java.util.Scanner;

public class PlayfairCipher
{
	public static char[] alphabet = {'a','b','c','d','e','f','g','h','i','j','k','l',
			'm','n','o','p','q','r','s','t','u','v','w','x','y','z'};
	
	public static String Encrypt(String plainText, String key){
		String decString = new String();
		
		for(int i = 0; i < (plainText.length() - 1); i = i + 2){
			if((plainText.charAt(i) == plainText.charAt(i+1))){
				plainText = plainText.substring(0, i+1) + 'x' + plainText.substring(i+1, plainText.length());
			}
		}
		if((plainText.length() % 2) != 0){
			plainText = plainText + 'x';
		}
		
		for(int i = 0; i < plainText.length(); i = i + 2){
			char place1 = plainText.charAt(i);
			char place2 = plainText.charAt(i+1);
			int loc1 = 0;
			int loc2 = 0;
			int diff = 0;
			
			for(int j = 0; j < key.length(); j++){
				if(place1 == key.charAt(j)){
					loc1 = j;
				}
				if(place2 == key.charAt(j)){
					loc2 = j;
				}
			}
			
			if((loc1 % 5) == (loc2 % 5)){
				loc1 = (loc1 + 5) % 25;
				loc2 = (loc2 + 5) % 25;
			}
			else if((loc1 / 5) == (loc2 / 5)){
				if((loc1 / 5) != ((loc1 + 1) / 5)){
					loc1 = (loc1 - 5);
					loc1 = (loc1 + 1);
				}
				else{
					loc1 = (loc1 + 1);
				}
				if((loc2 / 5) != ((loc2 + 1) / 5)){
					loc2 = (loc2 - 5);
					loc2 = (loc2 + 1);
				}
				else{
					loc2 = (loc2 + 1);
				}
			}
			else{
				diff = (loc1 % 5) - (loc2 % 5);
				if(diff < 0){
					loc1 = loc1 - diff;
					loc2 = loc2 + diff;
				}
				else{
					loc1 = loc1 - diff;
					loc2 = loc2 + diff;
				}
			}

			decString = decString + key.charAt(loc1) + key.charAt(loc2);
		}
		
		return decString;
	}
	
	public static String Decrypt(String cipherText, String key){
		String encString = new String();
		
		for(int i = 0; i < cipherText.length(); i = i + 2){
			char place1 = cipherText.charAt(i);
			char place2 = cipherText.charAt(i+1);
			int loc1 = 0;
			int loc2 = 0;
			int diff = 0;
			
			for(int j = 0; j < key.length(); j++){
				if(place1 == key.charAt(j)){
					loc1 = j;
				}
				if(place2 == key.charAt(j)){
					loc2 = j;
				}
			}
			
			if((loc1 % 5) == (loc2 % 5)){
				loc1 = (loc1 + 20) % 25;
				loc2 = (loc2 + 20) % 25;
			}
			else if((loc1 / 5) == (loc2 / 5)){
				if(((loc1 + 25) / 5) != (((loc1 + 25) - 1) / 5)){
					loc1 = (loc1 + 5);
					loc1 = (loc1 - 1);
				}
				else{
					loc1 = (loc1 - 1);
				}
				if(((loc2 + 25) / 5) != (((loc2 + 25) - 1) / 5)){
					loc2 = (loc2 + 5);
					loc2 = (loc2 - 1);
				}
				else{
					loc2 = (loc2 - 1);
				}
			}
			else{
				diff = (loc1 % 5) - (loc2 % 5);
				if(diff < 0){
					loc1 = loc1 - diff;
					loc2 = loc2 + diff;
				}
				else{
					loc1 = loc1 - diff;
					loc2 = loc2 + diff;
				}
			}
			
			encString = encString + key.charAt(loc1) + key.charAt(loc2);
		}
		return encString;
	}
	
	public static String MakeKey(String cipherKeyword){
		String tempKey = new String();
		boolean bool = false;
		
		tempKey = tempKey + cipherKeyword.charAt(0);
		for(int i = 1; i < cipherKeyword.length(); i++){
			for(int j = 0; j < tempKey.length(); j++){
				if(cipherKeyword.charAt(i) == tempKey.charAt(j)){
					bool = true;
				}
			}
			if(bool == false){
				tempKey = tempKey + cipherKeyword.charAt(i);
			}
			bool = false;
		}
		
		for(int i = 0; i < alphabet.length; i++){
			for(int j = 0; j < tempKey.length(); j++){
				if(alphabet[i] == tempKey.charAt(j) | alphabet[i] == 'j'){
					bool = true;
				}
			}
			if(bool == false){
				tempKey = tempKey + alphabet[i];
			}
			bool = false;
		}
		
		return tempKey;
	}
	
	public static void main(String[] args) {
		Scanner scanner = new Scanner(System.in);
        String cipherKeyword;
	    String key;
	    String cipheredText = "";
		
		System.out.println("Input keyword:");
		cipherKeyword = scanner.nextLine();
		System.out.println("Keyword is: " + cipherKeyword);
		key = MakeKey(cipherKeyword);
		System.out.println("Key is: " + key);
		
		System.out.println("Would you like to encrypt or decrypt?");
		String input = scanner.next();
		System.out.println("What is the plain text or cipher text? (no spaces)");
		String textInput = scanner.next();
		if(input.equals("encrypt")){
			cipheredText = Encrypt(textInput, key);
		}
		else if(input.equals("decrypt")){
			cipheredText = Decrypt(textInput, key);
		}
		System.out.println("The corresponding text is: ");
		System.out.println(cipheredText);
		scanner.close();
	}
}
