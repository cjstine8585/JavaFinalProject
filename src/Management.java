import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;
import java.util.Scanner;

public class Management {

	//Handles editing Theater
	public static void run(MovieTheater m) {
		if(!verifyPassword()) {
			return;	
		}

    	System.out.println("\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n");
		while(true) {
			System.out.println("1. Add Movie");
			System.out.println("2. Edit Movie");
			System.out.println("3. Return to menu");
			Scanner input = m.getScanner();
	    	int option = 0; //This block ensures we don't needlessly re-print the menu info
	    	//While still reserving the ability to reprint it after returning from another function
	    	while(option < 1 || option > 3) {
	    		String in = input.nextLine();
	    		if(!in.matches("^[+-]?\\d+$")) {
	    			System.out.println("Please input an integer between 1-3");
	    		}else {
	    			option = Integer.parseInt(in);
	    			if(option < 1 || option > 3) {
	    				System.out.println("Please input an integer between 1-3");        				
	    			}
	    		}
	    	}

        	System.out.println("\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n");
	    	if(option == 1) {
	    		addMovie(m, input);
	    	}else if(option == 2) {
	    		editMovie(m, input);
	    	}
	    	else if(option == 3) {
	    		break;
	    	}
		}
		
	}
	
	private static boolean verifyPassword() {
		//Arbitrary password
		Scanner in = new Scanner(System.in);
		System.out.println("Input Management Password");
		String input = in.next();
		return (input.equals("Group18"));
	}
	
	private static void addMovie(MovieTheater m, Scanner input) {
		System.out.println("Movie Title:");
		int lowestID = 0;
		for(Movie movie : m.getMovies()) {
			if(lowestID <= movie.getUniqueID())
				lowestID = movie.getUniqueID() + 1;
		}
		Movie movieToAdd = new Movie("","","",new ArrayList<Date>(),0,"",lowestID);
		movieToAdd.setTitle(input.nextLine());
		System.out.println("Movie Description:");
		movieToAdd.setDescription(input.nextLine());
		System.out.println("Movie Runtime: (1:45, etc.)");
		movieToAdd.setRuntime(input.nextLine());
		System.out.println("Showtimes:");
		System.out.println("Format: 04/28/2019 [12:00, 14:00, 16:00, 18:00], 01/25/2019 [11:30, 13:30, 15:30]");
		String showtimes = input.nextLine();
		
		String[] showDays = showtimes.split("(?=[0-9]{2}(/)[0-9]{2}(/)[0-9]{4})");
    	
    	for(String currentDay : showDays) {
    		if(currentDay.length() < 2) continue;
    		String[] showTimes = currentDay.split(" ");
    		if(showTimes.length > 0) {
    			SimpleDateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy HH:mm", Locale.ENGLISH);
    			dateFormat.setLenient(false);
    			try {
            		for(int c = 1; c < showTimes.length; c++) {
            			String currentTime = showTimes[c];
            			currentTime = currentTime.replaceAll("[\\[.,\\]]", "").trim();
            			Date currShowtime = dateFormat.parse(showTimes[0].trim() + " " + currentTime);
            			movieToAdd.addShowtime(currShowtime);
            		}
    			}catch(Exception e) {
    				e.printStackTrace();
    				System.out.println("Error adding showtimes, returning to menu");
    				return;
    			}
    		}
    	}
    	
    	System.out.println("Price:");
    	movieToAdd.setPrice(Double.parseDouble(input.nextLine()));
    	System.out.println("Rating: (PG-13, etc.):");
    	movieToAdd.setRating(input.nextLine());
    	System.out.println("Movie Information:");
    	movieToAdd.displayMovieInfo();
    	System.out.println("Showtimes:");
    	for(Date d : movieToAdd.getShowtimes()) {
    		System.out.println(d.toString());
    	}
    	System.out.println("1. Confirm");
    	System.out.println("2. Reject and Return to menu");
    	int option = 0; //This block ensures we don't needlessly re-print the menu info
    	//While still reserving the ability to reprint it after returning from another function
    	while(option < 1 || option > 3) {
    		String in = input.nextLine();
    		if(!in.matches("^[+-]?\\d+$")) {
    			System.out.println("Please input an integer between 1-3");
    		}else {
    			option = Integer.parseInt(in);
    			if(option < 1 || option > 3) {
    				System.out.println("Please input an integer between 1-3");        				
    			}
    		}
    	}
    	if(option == 1) {
    		m.getMovies().add(movieToAdd);
    		System.out.println("\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n");
    		System.out.println("Movie added successfully");
    		System.out.println("Input an integer to return to manager menu");
    		input.nextLine();
    		
    	}

    	System.out.println("\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n");
	}
	
	private static void editMovie(MovieTheater m, Scanner input) {
		for(Movie loopMov : m.getMovies()) {
			System.out.println(loopMov.getUniqueID() + " " + loopMov.getTitle());
		}
		System.out.println("Input Movie ID to edit");
		
		int id = Integer.parseInt(input.nextLine());
		Movie mov = null;
		for(Movie loopMov : m.getMovies()) {
			if(loopMov.getUniqueID() == id)
				mov = loopMov;
		}
		if(mov == null) {
			System.out.println("Movie not found");
    		System.out.println("Input an integer to return to manager menu");
    		input.nextLine();
    		System.out.println("\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n");
        	return;
		}
		
		System.out.println("Editing Movie");
		System.out.println("Leave an line blank to not change");
		
		Movie updatedMovie = new Movie("","","",new ArrayList<Date>(),0,"",id);
		System.out.println("Previous Title:");
		System.out.println(mov.getTitle());
		System.out.println("New Title:");
		String updatedTitle = input.nextLine();
		if(updatedTitle.length() > 0)
			updatedMovie.setTitle(updatedTitle);
		else
			updatedMovie.setTitle(mov.getTitle());
		
		System.out.println("Previous Description:");
		System.out.println(mov.getDescription());
		System.out.println("New Description:");
		String updatedDesc = input.nextLine();
		if(updatedDesc.length() > 0)
			updatedMovie.setDescription(updatedDesc);
		else
			updatedMovie.setDescription(mov.getDescription());
		
		System.out.println("Preious Runtime:");
		System.out.println(mov.getRuntime());
		System.out.println("New Runtime: (1:45, etc.)");
		String updatedRuntime = input.nextLine();
		if(updatedRuntime.length() > 0)
			updatedMovie.setRuntime(updatedRuntime);
		else
			updatedMovie.setRuntime(mov.getRuntime());
		
		System.out.println("Previous Showtimes:");
		for(Date d : mov.getShowtimes()) {
			System.out.println(d.toString());
		}
		System.out.println("New Showtimes:");
		System.out.println("Format: 04/28/2019 [12:00, 14:00, 16:00, 18:00], 01/25/2019 [11:30, 13:30, 15:30]");
		String showtimes = input.nextLine();
		if(showtimes.length() < 1) {
			updatedMovie.setShowtimes(mov.getShowtimes());
		}else {
			String[] showDays = showtimes.split("(?=[0-9]{2}(/)[0-9]{2}(/)[0-9]{4})");
	    	
	    	for(String currentDay : showDays) {
	    		if(currentDay.length() < 2) continue;
	    		String[] showTimes = currentDay.split(" ");
	    		if(showTimes.length > 0) {
	    			SimpleDateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy HH:mm", Locale.ENGLISH);
	    			dateFormat.setLenient(false);
	    			try {
	            		for(int c = 1; c < showTimes.length; c++) {
	            			String currentTime = showTimes[c];
	            			currentTime = currentTime.replaceAll("[\\[.,\\]]", "").trim();
	            			Date currShowtime = dateFormat.parse(showTimes[0].trim() + " " + currentTime);
	            			updatedMovie.addShowtime(currShowtime);
	            		}
	    			}catch(Exception e) {
	    				e.printStackTrace();
	    				System.out.println("Error adding showtimes, returning to menu");
	    				return;
	    			}
	    		}
	    	}
		}
    	
    	System.out.println("Old Price:");
    	System.out.println(mov.getPrice());
    	System.out.println("New Price:");
    	String updatedPrice = input.nextLine();
    	if(updatedPrice.length() > 0)
    		updatedMovie.setPrice(Double.parseDouble(updatedPrice));
    	else
    		updatedMovie.setPrice(mov.getPrice());
    	
    	System.out.println("Old Rating:");
    	System.out.println(mov.getRating());
    	System.out.println("New Rating: (PG-13, etc.):");
    	String updatedRating = input.nextLine();
    	if(updatedRating.length() > 0)
    		updatedMovie.setRating(updatedRating);
    	else
    		updatedMovie.setRating(mov.getRating());
    	
    	System.out.println("New Movie Information:");
    	updatedMovie.displayMovieInfo();
    	System.out.println("Showtimes:");
    	for(Date d : updatedMovie.getShowtimes()) {
    		System.out.println(d.toString());
    	}
    	System.out.println("1. Confirm");
    	System.out.println("2. Reject and Return to menu");
    	int option = 0; //This block ensures we don't needlessly re-print the menu info
    	//While still reserving the ability to reprint it after returning from another function
    	while(option < 1 || option > 2) {
    		String in = input.nextLine();
    		if(!in.matches("^[+-]?\\d+$")) {
    			System.out.println("Please input an integer between 1-2");
    		}else {
    			option = Integer.parseInt(in);
    			if(option < 1 || option > 2) {
    				System.out.println("Please input an integer between 1-2");        				
    			}
    		}
    	}
    	if(option == 1) {
    		m.getMovies().remove(mov);
    		m.getMovies().add(updatedMovie);
    		System.out.println("\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n");
    		System.out.println("Movie updated successfully");
    		System.out.println("Input an integer to return to manager menu");
    		input.nextLine();
    		
    	}

    	System.out.println("\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n");
		
	}
	
	
}
