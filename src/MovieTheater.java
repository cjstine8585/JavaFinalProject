import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Scanner;
import java.util.Date;
import java.util.Locale;

//using this as the 'driver' class
public class MovieTheater {
	private ArrayList<Movie> movies = new ArrayList<>();
	private Customer customer;
	// thinking about using text files to add a bunch of movies to the arraylist
	// It should make the code look a lot cleaner
	private Scanner input;

	public MovieTheater() {
		customer = new Customer(loadTickets());
		loadMovies();
	}

	public void run() {
		input = new Scanner(System.in);

		// Display menu to user
		// we can use a csv file of movie names to pull them and add them to the
		// ArrayList<>
		// this way we do not have to add a ton of movies manually

		while (true) {

			System.out.println(
					"Welcome to Group 18's movie theater!\nPlease choose an option from the menu by typing an integer from 1 to 6.");
			System.out.println(
					"1. Today's movies\n2. Movies this week\n3. Purchase ticket\n4. View purchased tickets\n5. Management mode\n6. Exit");

			int option = 0; // This block ensures we don't needlessly re-print the menu info
			// While still reserving the ability to reprint it after returning from another
			// function
			while (option < 1 || option > 6) {
				if (!input.hasNextInt()) {
					System.out.println("Please input an integer between 1-6");
				} else {
					option = input.nextInt();
					if (option < 1 || option > 6) {
						System.out.println("Please input an integer between 1-6");
					}
				}
			}
			System.out.println("\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n");
			if (option == 1) {
				displayTodaysMovies();
			} else if (option == 2) {
				displaysThisWeeksMovies();
			} else if (option == 3) {
				PointOfSale.purchaseTickets(this, customer);
			} else if (option == 4) {
				PointOfSale.viewPurchasedTickets(customer);
			} else if (option == 5) {
				Management.run(this);
			} else if (option == 6) {
				saveMovies();
				saveTickets();
				System.out.println("Goodbye!.");
				System.exit(0);
			} else {
				System.out.println("That was not a menu item.");
			}

			System.out.println("\n\n");
		}
	}

	public ArrayList<Movie> getMovies() {
		return movies;
	}

	public void displayTodaysMovies() {
		for (Movie m : getTodaysMovies()) {
			System.out.println(m.getTitle());
		}
		Scanner in = new Scanner(System.in);
		System.out.println("Input any integer to return to menu");
		while (!in.hasNextInt()) {
			System.out.println("Input any integer to return to menu");
			in.next();
		}
		in.nextInt();
	}

	public void displaysThisWeeksMovies() {
		for (Movie m : getThisWeeksMovies()) {
			System.out.println(m.getTitle());
		}
		Scanner in = new Scanner(System.in);
		System.out.println("Input any integer to return to menu");
		while (!in.hasNextInt()) {
			System.out.println("Input any integer to return to menu");
			in.next();
		}
		in.nextInt();
	}

	public ArrayList<Movie> getTodaysMovies() {
		ArrayList<Movie> todaysMovies = new ArrayList<Movie>();
		for (Movie m : movies) {
			if (m.isToday()) {
				todaysMovies.add(m);
			}
		}
		return todaysMovies;
	}

	public ArrayList<Movie> getThisWeeksMovies() {
		ArrayList<Movie> thisWeeksMovies = new ArrayList<Movie>();
		for (Movie m : movies) {
			if (m.isThisWeek()) {
				thisWeeksMovies.add(m);
			}
		}
		return thisWeeksMovies;
	}

	public Scanner getScanner() {
		return input;
	}

	@SuppressWarnings("deprecation")
	public void loadMovies() {
		Scanner titleScan = null;
		File titles = new File("titles.txt");

		try {
			titleScan = new Scanner(titles);
			while (titleScan.hasNextLine()) {
				String movieTitle = titleScan.nextLine();
				String movieID = movieTitle.split(" ")[0].trim();
				if (movieID.length() < 1) {
					continue; // Ignore whitespace lines
				}
				movieTitle = movieTitle.substring(movieID.length()).stripLeading();
				Movie currentMovie = new Movie(movieTitle, "", "", new ArrayList<Date>(), 0, "G",
						Integer.parseInt(movieID));

				// Populate our movie data
				// File format is: Title.txt
				// Line 1: Description
				// Line 2: Runtime
				// Line 3: Showtimes
				// Line 4: Price
				// Line 5: Rating
				// Line 6: Title for redundancy, not processed by program
				// Specific formatting examples are included in project
				File currentMovieFile = new File("movies/movie " + movieID + ".txt");
				Scanner movieScan = new Scanner(currentMovieFile);
				ArrayList<String> movieData = new ArrayList<>();
				while (movieScan.hasNextLine()) {
					movieData.add(movieScan.nextLine());
				}
				if (movieData.size() > 0) {
					currentMovie.setDescription(movieData.get(0));
				}
				if (movieData.size() > 1) {
					currentMovie.setRuntime(movieData.get(1));
				}
				if (movieData.size() > 2) {
					String[] showDays = movieData.get(2).split("(?=[0-9]{2}(/)[0-9]{2}(/)[0-9]{4})");

					for (String currentDay : showDays) {
						if (currentDay.length() < 2)
							continue;
						String[] showTimes = currentDay.split(" ");
						if (showTimes.length > 0) {
							SimpleDateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy HH:mm", Locale.ENGLISH);
							dateFormat.setLenient(false);
							try {
								for (int c = 1; c < showTimes.length; c++) {
									String currentTime = showTimes[c];
									currentTime = currentTime.replaceAll("[\\[.,\\]]", "").trim();
									Date currShowtime = dateFormat.parse(showTimes[0].trim() + " " + currentTime);
									currentMovie.addShowtime(currShowtime);
								}
							} catch (Exception e) {
								e.printStackTrace();
							}
						}
					}
				}
				if (movieData.size() > 3) {
					currentMovie.setPrice(Double.parseDouble(movieData.get(3)));
				}
				if (movieData.size() > 4) {
					currentMovie.setRating(movieData.get(4));
				}
				movies.add(currentMovie);
				movieScan.close();

			}
		} catch (FileNotFoundException e) {
			System.out.println("Could not locate file.");
		}
		titleScan.close();

	}

	private void saveMovies() {
		// Saves files on shutdown (We can leave everything cached while running, and
		// only read/write to files on close and open
		File titles = new File("titles.txt");
		try {
			PrintWriter out = new PrintWriter(titles);
			for (Movie m : getMovies()) {
				out.println(m.getUniqueID() + " " + m.getTitle());
			}
			out.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}

		for (Movie m : getMovies()) {
			File movieFile = new File("movies/movie " + m.getUniqueID() + ".txt");
			try {
				PrintWriter out = new PrintWriter(movieFile);
				out.println(m.getDescription());
				out.println(m.getRuntime());
				// Format dates
				String dates = "";
				for (Date d : m.getShowtimes()) {
					SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy [HH:mm] ");
					dates += sdf.format(d);
				}
				out.println(dates);
				out.println(m.getPrice());
				out.println(m.getRating());
				out.println(m.getTitle());
				out.close();
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

	}

	public ArrayList<Ticket> loadTickets() {
		ArrayList<Ticket> tickets = new ArrayList<Ticket>();
		Scanner ticketScan = null;
		File ticketFile = new File("tickets.txt");
		try {
			ticketScan = new Scanner(ticketFile);
			while (ticketScan.hasNextLine()) {
				String title = ticketScan.nextLine();
				if (title.length() < 1 || !ticketScan.hasNextLine())
					break;
				SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy [HH:mm]");
				String dateString = ticketScan.nextLine();
				if (dateString.length() < 1 || !ticketScan.hasNextLine())
					break;
				Date date = sdf.parse(dateString);
				String type = ticketScan.nextLine();
				if (type.length() < 1)
					break;
				Ticket currTicket = new Ticket(title, type, date);
				tickets.add(currTicket);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return tickets;
	}

	public void saveTickets() {
		File ticketFile = new File("tickets.txt");
		try {
			PrintWriter out = new PrintWriter(ticketFile);
			for (Ticket t : customer.getTickets()) {
				out.println(t.getMovie());
				SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy [HH:mm]");
				out.println(sdf.format(t.getShowtime()));
				out.println(t.getTicketType());
			}
			out.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {
		MovieTheater demo = new MovieTheater();
		demo.run();
	}

}
