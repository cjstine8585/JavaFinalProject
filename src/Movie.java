import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class Movie {

	private String title, description, runtime, rating;
	private ArrayList<Date> showtimes;
	private double price;
	private int uniqueID;

	public Movie(String title, String description, String runtime, ArrayList<Date> showtimes, double price,
			String rating, int uniqueID) {
		this.title = title;
		this.description = description;
		this.runtime = runtime;
		this.showtimes = showtimes;
		this.price = price;
		this.rating = rating;
		this.uniqueID = uniqueID;
	}

	public int getUniqueID() {
		return uniqueID;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getRuntime() {
		return runtime;
	}

	public void setRuntime(String runtime) {
		this.runtime = runtime;
	}

	public ArrayList<Date> getShowtimes() {
		return showtimes;
	}

	public void addShowtime(Date showtime) {
		this.showtimes.add(showtime);
	}

	public void setShowtimes(ArrayList<Date> showtimes) {
		this.showtimes = showtimes;
	}

	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
	}

	public String getRating() {
		return rating;
	}

	public void setRating(String rating) {
		this.rating = rating;
	}
	
	public boolean isToday() {
		Calendar calToday = Calendar.getInstance();
		boolean isPlayingToday = false; // This would be instantiated false, but our ShowTimes aren't populated
		for (Date showtime : this.getShowtimes()) {
			Calendar calShowtime = Calendar.getInstance();
			calShowtime.setTime(showtime);
			if (calToday.get(Calendar.ERA) == calShowtime.get(Calendar.ERA)
					&& calToday.get(Calendar.YEAR) == calShowtime.get(Calendar.YEAR)
					&& calToday.get(Calendar.DAY_OF_YEAR) == calShowtime.get(Calendar.DAY_OF_YEAR)) {
				return true;
			}
		}
		return false;
	}
	
	public boolean isThisWeek() {
		Calendar calToday = Calendar.getInstance();
		boolean isPlayingThisWeek = false; // This would be instantiated false, but our ShowTimes aren't populated
		for (Date showtime : this.getShowtimes()) {
			Calendar calShowtime = Calendar.getInstance();
			calShowtime.setTime(showtime);
			if (calToday.get(Calendar.ERA) == calShowtime.get(Calendar.ERA)
					&& calToday.get(Calendar.YEAR) == calShowtime.get(Calendar.YEAR)
					&& calToday.get(Calendar.DAY_OF_YEAR) - calShowtime.get(Calendar.DAY_OF_YEAR) > -7
					&& calToday.get(Calendar.DAY_OF_YEAR) - calShowtime.get(Calendar.DAY_OF_YEAR) <= 0) {
				return true;
			}
		}
		return false;
	}

	public void displayMovieInfo() {
		System.out.println(getTitle());
		System.out.println(getDescription());
		System.out.println(getRuntime());
		System.out.println("$" + getPrice());
		System.out.println(getRating());

	}
}
