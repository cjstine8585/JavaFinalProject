import java.util.Date;

public class Ticket {
    private String movieTitle;
    private Date showtime;
    private String ticketType; //Senior, Adult, Child


    public Ticket(String movieTitle, String ticketType, Date showtime){
        this.movieTitle = movieTitle;
        this.ticketType = ticketType;
        this.showtime = showtime;
    }

    public String getMovie() {
        return movieTitle;
    }

    public void setMovie(String movieTitle) {
        this.movieTitle = movieTitle;
    }
    
	public String getTicketType() {
		return ticketType;
	}

	public void setTicketType(String ticketType) {
		this.ticketType = ticketType;
	}

	public Date getShowtime() {
		return showtime;
	}

	public void setShowtime(Date showtime) {
		this.showtime = showtime;
	}

}
