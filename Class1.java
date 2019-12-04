import java.util.Vector;
import java.util.Scanner;

public class Class1 {
	//this Class processes the Flags for the text file one line at a time - execpt for 2columns because it is special
	//for example: right and block must be called every time it is used for each line
	// this class assumes that LengthProcessing returns a String array of words that can fit into a given character limit
	//ie. LengthProcessing(line,80) returns an array of words that add up to less than 80 characters- with spaces
	
	public String[] twoColumns(String line, Scanner sc) //pass in the original line and the texfile scanner
	{
		Vector<String[]> chunks= new Vector<String[]>(); // one chunk is an array of words that fit into 35 characters 
		//Vector<String[]> columns= new Vector<String[]>();
		String bigLine="";
		int linecounter=0;
		while(line.charAt(0)!='-') {
			String[] chunkline= LengthProcessing(line, 35);
			chunks.add(chunkline);
			line=sc.nextLine();
			if(line.charAt(0)=='-'&&line.charAt(1)=='e')
			{
				String[] emptyLine=new String[35];
				for(int i=0;i<35;i++)
				{
					emptyLine[i]=" ";
				}
				chunks.add(emptyLine);
				line=sc.nextLine();
			}
			linecounter++;
		}
		String[] output=new String[linecounter];
		int halfway=chunks.size()/2; //find middle of the components of the chunks vector
		String halfLine="";
		String secondHalfLine="";
		//for(int i=0;i<chunks.elementAt(h);i++)
		//{
		//	halfLine+=chunks.elementAt(h)[i];
		//}
		//String[] fullLine= new String[3];
		int o=0;
		for(int h=0;h<halfway;h++)
		{
			for(int i=0;i<chunks.elementAt(h).length;i++)
			{
				halfLine+=chunks.elementAt(h)[i];
			}
			for(int i=0;i<chunks.elementAt(halfway+h).length;i++)
			{
				secondHalfLine+=chunks.elementAt(halfway+h)[i];
			}
			//fullLine[0]=halfLine;
			//fullLine[1]="          ";//10 spaces
			//fullLine[2]=secondHalfLine;
			bigLine+=halfLine;
			bigLine+="          ";//10 spaces
			bigLine+=secondHalfLine;
			halfLine="";
			secondHalfLine="";
			//columns.add(fullLine);
			output[o]=bigLine;
			o++;
			output[o]="\n";
			o++;
		}
		
		return output;
	}
	public String[] indent(String line)
	{
		String[] output= new String[80];
		output[0]="     ";//start with 5 spaces as the first "word"
		String[] words= LengthProcessing(line,75);
		int b=0;
		int wordsLength=0;
		while (b < words.length) { //count the elements in words
			if(words[b]!=null)
			{
				wordsLength++;
			}
			b++;
		}
		for(int i=0;i<wordsLength;i++)
		{
			output[i+1]=words[i];
		}
		return output;
	}
	public String[] block(String line) {
		String[] output= new String[80];
		output[0]="          ";// start with 10 spaces as the first "word"
		String[] words=LengthProcessing(line,70);
		int wordsLength=0;
		int b=0;
		while (b < words.length) { //count the elements in words
			if(words[b]!=null)
			{
				wordsLength++;
			}
			b++;
		}
		for(int i=0;i<wordsLength;i++)
		{
			output[i+1]=words[i];
		}
		return output;
	}
	public String[] right(String line) {
		String[] output= new String[80];
		String[] words=LengthProcessing(line,80);
		int i = 0;
		int b=0;
		int size = 0;
		int wordsLength=0;
		while (b < words.length) { //count the elements in words
			if(words[b]!=null)
			{
				wordsLength++;
			}
			b++;
		}
		while (i < words.length) {	//count the size of the words
			if(words[i]!=null)
			{
				size += words[i].length();
				if(words[i]==" ")
				{
					size++;
				}
			}
			i++;
		}
		int leftOverSpace=80-size; //find leftOverSpace
		int j=0;
		while(j<=leftOverSpace) //add that amount of space to the front of the line
		{
			output[j]=" ";
			j++;
		}
		int k=0;
		while(k<wordsLength) //add all the other words into the array
		{
			output[j]=words[k];
			k++;
			j++;
		}
		
		return output;
	}
	public String[] left(String line) {
		String[] output= new String[80];
		String[] words=LengthProcessing(line,80);
		int i = 0;
		int b=0;
		int size = -1;
		int wordsLength=0;
		while (b < words.length) { //count the elements in words
			if(words[b]!=null)
			{
				wordsLength++;
			}
			b++;
		}
		while (i < wordsLength) {	//count the size of the words
			if(words[i]!=null)
			{
				size += words[i].length();
			    if(words[i]==" ")
				{
					size++;
				}
			}
			i++;
		}
		int leftOverSpace=80-size; //find leftOverSpace
		
		int j=0;
		int k=0;
		while(k<wordsLength) //add all the words into the array
		{
			output[j]=words[k];
			k++;
			j++;
		}
		while(j<leftOverSpace) //add leftOverSpace spaces to the end of the line
		{
			output[j]=" ";
			j++;
		}
		return output;
	}
	public String[] title(String line)
	{
		String[] output= new String[80];
		String[] words= new String[80];
		 words= LengthProcessing(line,80);
		int i = 0;
		int b=0;
		int size = 0;
		int wordsLength=0;
		while (b < words.length) { //count the elements in words
			if(words[b]!=null)
			{
				wordsLength++;
			}
			b++;
		}
		while (i < wordsLength) { //count the size of the words
			if(words[i]!=null)
			{
				size += words[i].length();
			}
			i++;
		}
		int leftOverSpace=80-size; //find leftOverSpace
		leftOverSpace=leftOverSpace/2; // divide it by two to put the title in the middle
		int j=0;
		while(j<leftOverSpace) //put the spaces in front in the output
		{
			output[j]=" ";
			j++;
		}
		int k=0;
		while(k<wordsLength) //put the words into the output
		{
			output[j]=words[k];
			k++;
			j++;
		} 
		while(j<leftOverSpace)// put spaces at the end of the output 
		{
			output[j]=" ";
			j++;
		}
		
		return output;
	}
	public String[] center(String line)
	{
		String[] output=new String[80];
		String[] words= LengthProcessing(line,80);
		int i = 0;
		int b=0;
		int size = 0;
		int wordsLength=0;
		while (b < words.length) { //count the elements in words
			if(words[b]!=null)
			{
				wordsLength++;
			}
			b++;
		}
		while (i < words.length) { //count the length of the words
			if(words[i]!=null)
			{
				size += words[i].length();
				if(words[i]==" ")
				{
					size++;
				}
			}
			i++;
		}
		int leftOverSpace=80-size;
		int addedFiller=leftOverSpace/(wordsLength-1); //added filler is leftOverSpaces divided by how many words
														//ie. how many spaces per actual space do I need to add
		int k=0;
		i=0;
		while(k<wordsLength-1) //for all words until the last word
		{
			output[i]=words[k]; //place the word into the output
			i++;
			for(int j=0;j<addedFiller;j++) //add how many addedFiller spaces needed
			{
				output[i]=" ";
				i++;
			}
			k++;
		}
		output[i]=words[k]; //add the final word at the end
		
		
		return output;
		
	}
	
public String[] LengthProcessing(String line, int maxChars)
{
	String[] words = new String[maxChars];
	Scanner s2 = new Scanner(line);
	int i=0;
	int charSize=0;
    while (s2.hasNext() && charSize<maxChars) {
    	
        String s = s2.next(); //gets the next word 
       // System.out.println(s);
        if(charSize+s.length()<maxChars) //if the next word can fit into the current line
        {	
        		words[i]=s;
        		i++;
        		charSize=charSize+s.length();
        		if(charSize<maxChars)
        		{
        			words[i]=" ";//add a space after the word
        			i++;
        			charSize++;
        		}
        }else{
        	charSize=maxChars;// get out of the while loop for this line;
        }
    }
	
	return words;
}
	
	
}
