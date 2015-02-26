import java.util.List;

import utils.*;

public class Knapsack {

	static int process(List<StoryEvent> filteredEvents, int index,int height, List<StoryEvent> selectedEvents) {
		if(index >= filteredEvents.size())
			return 0;
		
		StoryEvent storyEvent = filteredEvents.get(index);
		
		if(storyEvent.heightInPixel > height){
			return process(filteredEvents, index+1, height, selectedEvents);
		}
		
		//selected
		int newHeight = height - storyEvent.heightInPixel;
		int maxScoreSelected = storyEvent.score + process(filteredEvents, index+1, newHeight, selectedEvents);

		int maxScore_not_Selected = process(filteredEvents, index+1, height, selectedEvents);
		
		if(maxScoreSelected > maxScore_not_Selected){
			if(!selectedEvents.contains(storyEvent)){
			    storyEvent.indexNumber = index;
				selectedEvents.add(storyEvent);
			}
			return maxScoreSelected;
		}
		
		return maxScore_not_Selected;
	}
}
