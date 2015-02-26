

import java.io.BufferedReader;

import myTest.*;

import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;


public class Test {
	
	static int timeWindow = 0;
	static int heightOfBrowser = 0;

	static List<StoryEvent> storyEvents = new ArrayList<StoryEvent>();
	
	public static void main(String[] args) throws IOException { 
    	readInput();
    }
    

	private static void processAndPrint(int currentTime) {
		
		int minTime = currentTime - timeWindow; //TODO: in.timeWindow
		
		// go over the storyEvents , get list of events whose time is >= minTime
		// =currentSet
		
		List<StoryEvent> filteredEvents = filterEventsOnTime(storyEvents, minTime);
		
		List<StoryEvent> selectedEvents = new ArrayList<StoryEvent>();
		
		
		int totalScore = knapSack(filteredEvents, 0, heightOfBrowser, selectedEvents);
		// Now from currentSet find the outputSet such that
		// summation of widths of outputSet <= BrowserWidth
		// summation of score of outputSet is maximied

		// call knapsack() on this set
		
		// ptint the output set
		
		
		printOutput(totalScore, selectedEvents);
		
		
	}
	
	

	private static void printOutput(int score, List<StoryEvent> selectedEvents) {

		System.out.println("###################"+score +" " + selectedEvents.size());
	}


	private static int knapSack(List<StoryEvent> filteredEvents, int index,int height, List<StoryEvent> selectedEvents) {
		
		if(index >= filteredEvents.size()){
			return 0;
		}
		
		StoryEvent storyEvent = filteredEvents.get(index);
		
		if(storyEvent.heightInPixel > height){
			return knapSack(filteredEvents, index+1, height, selectedEvents);
		}
		
		//selected
		int newHeight = height - storyEvent.heightInPixel;
		
		
		int maxScoreSelected = storyEvent.score + knapSack(filteredEvents, index+1, newHeight, selectedEvents);
		int maxScore_not_Selected = knapSack(filteredEvents, index+1, height, selectedEvents);
		
		if(maxScoreSelected > maxScore_not_Selected){
			if(!selectedEvents.contains(storyEvent)){
				selectedEvents.add(storyEvent);
			}
			return maxScoreSelected;
		}
		
		return maxScore_not_Selected;
		
	}


	private static List<StoryEvent> filterEventsOnTime(
			List<StoryEvent> storyEvents2, int minTime) {
		
		List<StoryEvent> filteredList = new ArrayList<StoryEvent>();
		
		for(StoryEvent e : storyEvents2){
			if(e.timeOfPublication >= minTime){
				filteredList.add(e);
			}
		}
		
		return filteredList;
	}

	
	
	public static void readInput() throws IOException{
		
		boolean readFromFile = true;
		
		BufferedReader br = null;
	  
		if(readFromFile){
			String file = "/Users/chinmay.parekh/Workspaces/MyEclipse 2015 CI/FeedOptimizer/src/input.txt";
			br = new BufferedReader(new FileReader(file));
		}
		else{
			br = new BufferedReader(new InputStreamReader(System.in));
		}
		
		readInput(br);
		
			
	}
	


	private static void readInput(BufferedReader br) throws IOException{


        //System.out.println("Enter LeadLine: eg: \"2 10 20\"");
        String s = br.readLine();
        
        InputLeadline in =parseLeadLine(s);
        heightOfBrowser = in.heightOfBrowser;
        timeWindow = in.timeWindow ;

        int i = in.numberOfElements;
        //System.out.println("i=" + i);
        //System.out.println("Enter " + i + " strings: eg. \"S 1 2 3\" ");
        
        String[] inputStrs = new String[i];
        
        for(int a = 0; a < i; a++){
        	 inputStrs[a] = br.readLine();
        }
        
        for(int a = 0; a < inputStrs.length; a++){
        	String s1 = inputStrs[a];
	       	 //System.out.println(s1);
	       	 if(s1.startsWith("S"))
	       		 parseStoryLine(s1);
	       	 else 
	       		 parseReloadLine(s1);
       }

    }

	private static void parseStoryLine(String s) {
		// TODO Auto-generated method stub
		//read the line and update the datastructure
		
		String[] strArray = s.split(" ");
		
		StoryEvent storyEvent = new StoryEvent();
		storyEvent.timeOfPublication = Integer.parseInt(strArray[1]);
		storyEvent.score  = Integer.parseInt(strArray[2]);
		storyEvent.heightInPixel = Integer.parseInt(strArray[3]);
		
		storyEvents.add(storyEvent);
	}

	private static void parseReloadLine(String s) {
		// TODO Auto-generated method stub
		//access the data structure processed/generated so far
		// print the output
		
		String[] strArray = s.split(" ");
		int currentTime = Integer.parseInt(strArray[1]);
		
    	processAndPrint(currentTime);
	}
	
	

	private static InputLeadline parseLeadLine(String s) {
		// TODO Auto-generated method stub
		
		//tokenize the string
		// get first int and return it
		// store W and BW in a variable.
		
		String[] strArray = s.split(" ");
		
		InputLeadline in = new InputLeadline();
		in.numberOfElements =		Integer.parseInt(strArray[0]);
		in.timeWindow = Integer.parseInt(strArray[1]);
		in.heightOfBrowser = Integer.parseInt(strArray[2]);
		
		return in;
	}
}

