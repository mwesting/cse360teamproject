import java.util.Vector;
import java.util.Scanner;
import java.util.ArrayList;

public class Class1 {
	//this Class processes the Flags for the text file one line at a time - execpt for 2columns because it is special
	//for example: right and block must be called every time it is used for each line
	// this class assumes that LengthProcessing returns a String array of words that can fit into a given character limit
	//ie. LengthProcessing(line,80) returns an array of words that add up to less than 80 characters- with spaces
	
	
public String[] ListLengthProcessing(ArrayList<String> line, int maxChars)
{
	String[] words = new String[maxChars];
	
	int index2=0;
	int index3=0;
	int charSize=0;
	
	while(index2<line.size()&&charSize<maxChars)
	{
		if(charSize+line.get(index2).length()<maxChars)
		{
			words[index3]=line.get(index2);
			//System.out.println(index2);
			//System.out.println(line.get(index2)+" "+line.get(index2).length());
			charSize=charSize+words[index3].length();
			index2++;
			index3++;
			
			if(charSize<maxChars)
			{
				words[index3]=" "; //add a space after each word
				charSize++;
				index3++;
			}
			//System.out.println("charSize= "+charSize);
		}else {
			charSize=maxChars;//get out of while loop;
		}
		
	}
	
	
	
	return words;
}
public String[] leftw(String[] line) {
	String[] output= new String[80];
	String[] words=line;
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
	while (i < wordsLength) {	//count the size of the words
		if(words[i]!=null)
		{
			size += words[i].length();
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
public String[] centerw(String[] line)
{
	String[] output=new String[80];
	String[] words=line;
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
	while (i < wordsLength) { //count the length of the words
		if(words[i]!=null)
		{
			size += words[i].length();
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
	
} //end of centerw method
public String[] rightw(String[] line) {
	String[] output= new String[80];
	String[] words=line;
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
}//end of rightw method
public String[] titlew(String[] line)
{
	String[] output= new String[80];
	String[] words= line;
	 //words= LengthProcessing(line,80);
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
}//end of titlew method

public String[] indentw(String[] line)
{
	String[] output= new String[80];
	output[0]="     ";//start with 5 spaces as the first "word"
	String[] words= line;
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
public String[] blockw(String[] line) {
	String[] output= new String[80];
	output[0]="          ";// start with 10 spaces as the first "word"
	String[] words=line;
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
}//end of blockw method
public String[] twoColumnsw(ArrayList<String[]> lineList) //pass in the arrayList of chunks
{
	
	int halfwaypoint=lineList.size()/2;
	String[] output=new String[halfwaypoint+1];
	String bigstring="";
	String firsthalf="";
	String secondhalf="";
	String tenspaces="          ";//ten spaces
	for(int i=0;i<halfwaypoint;i++)
	{
		firsthalf="";
		secondhalf="";
		for(int h=0;h<lineList.get(i).length;h++)
		{
			if(lineList.get(i)[h]!=null)
			{
				firsthalf+=lineList.get(i)[h];
			}
			
		}
		for(int j=0;j<lineList.get(halfwaypoint+i).length;j++)
		{
			if(lineList.get(halfwaypoint+i)[j]!=null)
			{
				secondhalf+=lineList.get(halfwaypoint+i)[j];
			}
			
		}
		bigstring=firsthalf+tenspaces+secondhalf+"\n";
		output[i]=bigstring;
		
	}
	
	
	return output;
}//end of twocolumnsw method
}//END OF CLASS
