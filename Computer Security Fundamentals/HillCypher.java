import java.util.Scanner;

public class HillCypher
{
	public static char[] alphabet = {'a','b','c','d','e','f','g','h','i','j','k','l',
			'm','n','o','p','q','r','s','t','u','v','w','x','y','z'};
	
	private static String Encrypt2(String textInput, int[] keyMatrix)
	{
		String decText = "";
		
		for(int i = 0; i < (textInput.length() - 1); i = i + 2){
			if((textInput.charAt(i) == textInput.charAt(i+1))){
				textInput = textInput.substring(0, i+1) + 'x' + textInput.substring(i+1, textInput.length());
			}
		}
		if((textInput.length() % 2) != 0){
			textInput = textInput + 'x';
		}
		System.out.println(textInput);
		
		for(int i = 0; i < textInput.length(); i = i + 2){
			char place1 = textInput.charAt(i);
			char place2 = textInput.charAt(i+1);
			int loc1 = 0;
			int loc2 = 0;
			int ans1 = 0;
			int ans2 = 0;
			
			for(int j = 0; j < alphabet.length; j++){
				if(place1 == alphabet[j]){
					loc1 = j;
				}
				if(place2 == alphabet[j]){
					loc2 = j;
				}
			}
			
			ans1 = ((keyMatrix[0]*loc1)+(keyMatrix[1]*loc2)) % 26;
			ans2 = ((keyMatrix[2]*loc1)+(keyMatrix[3]*loc2)) % 26;
			decText = decText + alphabet[ans1] + alphabet[ans2];
		}
		
		return decText;
	}
	
	private static String Decrypt2(String textInput, int[] keyMatrix)
	{
		String encText = "";
		int[] invMatrix = new int[4];
		int det;
		
		det = (((keyMatrix[0]*keyMatrix[3])-(keyMatrix[1]*keyMatrix[2]) + 26) % 26);
		if(det == 0 || det % 2 == 0 || det % 13 == 0){
			System.out.println("Matrix is not invertable.");
			return "";
		}
		for(int j = 1; j < 26; j++){
			if(((det * j) % 26) == 1){
				det = j;
				break;
			}
		}
		invMatrix[0] = (keyMatrix[3] * det) % 26;
		invMatrix[1] = ((((-keyMatrix[1]) + 26) % 26) * det) % 26;
		invMatrix[2] = ((((-keyMatrix[2]) + 26) % 26) * det) % 26;
		invMatrix[3] = (keyMatrix[0] * det) % 26;
		
		for(int i = 0; i < textInput.length(); i = i + 2){
			char place1 = textInput.charAt(i);
			char place2 = textInput.charAt(i+1);
			int loc1 = 0;
			int loc2 = 0;
			int ans1 = 0;
			int ans2 = 0;
			
			for(int j = 0; j < alphabet.length; j++){
				if(place1 == alphabet[j]){
					loc1 = j;
				}
				if(place2 == alphabet[j]){
					loc2 = j;
				}
			}
			
			ans1 = ((invMatrix[0]*loc1)+(invMatrix[1]*loc2)) % 26;
			ans2 = ((invMatrix[2]*loc1)+(invMatrix[3]*loc2)) % 26;
			encText = encText + alphabet[ans1] + alphabet[ans2];
		}
		
		return encText;
	}
	
	private static String Encrypt3(String textInput, int[] keyMatrix)
	{
		String decText = "";
		
		for(int i = 0; i < (textInput.length() - 1); i = i + 3){
			if((textInput.charAt(i) == textInput.charAt(i+1))){
				textInput = textInput.substring(0, i+1) + 'x' + textInput.substring(i+1, textInput.length());
			}
			if((textInput.charAt(i+1) == textInput.charAt(i+2))){
				textInput = textInput.substring(0, i+2) + 'x' + textInput.substring(i+2, textInput.length());
			}
		}
		for(int j = 0; j < 2; j++){
			if((textInput.length() % 3) != 0){
				textInput = textInput + 'x';
			}
		}
		
		for(int i = 0; i < textInput.length(); i = i + 3){
			char place1 = textInput.charAt(i);
			char place2 = textInput.charAt(i+1);
			char place3 = textInput.charAt(i+2);
			int loc1 = 0;
			int loc2 = 0;
			int loc3 = 0;
			int ans1 = 0;
			int ans2 = 0;
			int ans3 = 0;
			
			for(int j = 0; j < alphabet.length; j++){
				if(place1 == alphabet[j]){
					loc1 = j;
				}
				if(place2 == alphabet[j]){
					loc2 = j;
				}
				if(place3 == alphabet[j]){
					loc3 = j;
				}
			}
			
			ans1 = ((keyMatrix[0]*loc1)+(keyMatrix[1]*loc2)+(keyMatrix[2]*loc3)) % 26;
			ans2 = ((keyMatrix[3]*loc1)+(keyMatrix[4]*loc2)+(keyMatrix[5]*loc3)) % 26;
			ans3 = ((keyMatrix[6]*loc1)+(keyMatrix[7]*loc2)+(keyMatrix[8]*loc3)) % 26;
			decText = decText + alphabet[ans1] + alphabet[ans2] + alphabet[ans3];
		}
		
		return decText;
	}
	
	private static String Decrypt3(String textInput, int[] keyMatrix)
	{
		String encText = "";
		int[] invMatrix = new int[9];
		int det;
		
		//A = (ei-fh) B = (di-fg) C = (dh-eg)
		invMatrix[0] = ((keyMatrix[4]*keyMatrix[8])-(keyMatrix[5]*keyMatrix[7]) + 676) % 26;//A
		invMatrix[1] = (-((keyMatrix[1]*keyMatrix[8])-(keyMatrix[2]*keyMatrix[7])) + 676) % 26;//D
		invMatrix[2] = ((keyMatrix[1]*keyMatrix[5])-(keyMatrix[2]*keyMatrix[4]) + 676) % 26;//G
		invMatrix[3] = (-((keyMatrix[3]*keyMatrix[8])-(keyMatrix[5]*keyMatrix[6])) + 676) % 26;//B
		invMatrix[4] = ((keyMatrix[0]*keyMatrix[8])-(keyMatrix[2]*keyMatrix[6]) + 676) % 26;//E
		invMatrix[5] = (-((keyMatrix[0]*keyMatrix[5])-(keyMatrix[2]*keyMatrix[3])) + 676) % 26;//H
		invMatrix[6] = ((keyMatrix[3]*keyMatrix[7])-(keyMatrix[4]*keyMatrix[6]) + 676) % 26;//C
		invMatrix[7] = (-((keyMatrix[0]*keyMatrix[7])-(keyMatrix[1]*keyMatrix[6])) + 676) % 26;//F
		invMatrix[8] = ((keyMatrix[0]*keyMatrix[4])-(keyMatrix[1]*keyMatrix[3]) + 676) % 26;//I
		//det = aA*bB*cC
		det = ((keyMatrix[0]*invMatrix[0])+(keyMatrix[1]*invMatrix[3])+(keyMatrix[2]*invMatrix[6]) + 676) % 26;
		if(det == 0 || det % 2 == 0 || det % 13 == 0){
			System.out.println("Matrix is not invertable.");
			return "";
		}
		for(int j = 1; j < 26; j++){
			if(((det * j) % 26) == 1){
				det = j;
				break;
			}
		}
		//System.out.println(det);
		for(int k = 0; k < invMatrix.length; k++){
			invMatrix[k] = (invMatrix[k] * det) % 26;
		}
		
		for(int i = 0; i < textInput.length(); i = i + 3){
			char place1 = textInput.charAt(i);
			char place2 = textInput.charAt(i+1);
			char place3 = textInput.charAt(i+2);
			int loc1 = 0;
			int loc2 = 0;
			int loc3 = 0;
			int ans1 = 0;
			int ans2 = 0;
			int ans3 = 0;
			
			for(int j = 0; j < alphabet.length; j++){
				if(place1 == alphabet[j]){
					loc1 = j;
				}
				if(place2 == alphabet[j]){
					loc2 = j;
				}
				if(place3 == alphabet[j]){
					loc3 = j;
				}
			}
			
			ans1 = ((invMatrix[0]*loc1)+(invMatrix[1]*loc2)+(invMatrix[2]*loc3)) % 26;
			ans2 = ((invMatrix[3]*loc1)+(invMatrix[4]*loc2)+(invMatrix[5]*loc3)) % 26;
			ans3 = ((invMatrix[6]*loc1)+(invMatrix[7]*loc2)+(invMatrix[8]*loc3)) % 26;
			encText = encText + alphabet[ans1] + alphabet[ans2] + alphabet[ans3];
		}
		
		return encText;
	}
	
	public static void main(String[] args) {
		Scanner scanner = new Scanner(System.in);
		int matrix = 0;
		String cipheredText = "";
		
		System.out.println("Is the key a 2x2 or 3x3 (2/3)?");
		matrix = scanner.nextInt();
		if(matrix != 2 && matrix != 3){
			System.out.println("Wrong number entered");
			scanner.close();
			return;
		}
		int[] keyMatrix = new int[matrix * matrix];
		System.out.println("Enter key matrix as each integer. left to right, top to bottom. hit enter after each integer.");
		for(int i = 0; i < keyMatrix.length; i++){
			keyMatrix[i] = scanner.nextInt();
		}
		System.out.println("Would you like to encrypt or decrypt?");
		String input = scanner.next();
		System.out.println("What is the plain text or cipher text? (no spaces)");
		String textInput = scanner.next();
		if(matrix == 2){
			if(input.equals("encrypt")){
				cipheredText = Encrypt2(textInput, keyMatrix);
			}
			else if(input.equals("decrypt")){
				cipheredText = Decrypt2(textInput, keyMatrix);
			}
		}
		else if(matrix == 3){
			if(input.equals("encrypt")){
				cipheredText = Encrypt3(textInput, keyMatrix);
			}
			else if(input.equals("decrypt")){
				cipheredText = Decrypt3(textInput, keyMatrix);
			}
		}
		
		System.out.println("The corresponding text is: ");
		System.out.println(cipheredText);

		scanner.close();
	}
}
