import javax.swing.plaf.basic.BasicInternalFrameTitlePane;
import java.util.ArrayList;
import java.util.Date;
import java.util.Scanner;

public class PointOfSale {
	private static ArrayList<Ticket> temp;
	public static void purchaseTickets(MovieTheater mt, Customer c) {
		System.out.println("Are you going to buy a movie for: \n1. Today\n2. This week\n"+
				"Enter -1 to return to the movie selection");
		Scanner scan = new Scanner(System.in);
		int userInput = scan.nextInt();
		Movie boughtMovie = null;

		if(userInput == 1){
			for(Movie m: mt.getTodaysMovies()){
				System.out.println(m.getUniqueID()+" "+m.getTitle());
			}
			System.out.println("Please enter the number for the movie you want to purchase.");

			userInput = scan.nextInt();

			for(Movie m: mt.getTodaysMovies()){
				if(userInput==m.getUniqueID()){
					boughtMovie = m;
				}
			}
			if(boughtMovie==null){
				System.out.println("That movie is not on the list.");
				purchaseTickets(mt, c);
			}
		}
		else if(userInput==2){

			for(Movie m: mt.getThisWeeksMovies()){
				System.out.println(m.getUniqueID()+" " +m.getTitle());
			}
			System.out.println("Please enter the number for the movie you want to purchase.");
			userInput = scan.nextInt();
			for(Movie m: mt.getThisWeeksMovies()){
				if(userInput==m.getUniqueID()){
					boughtMovie = new Movie(m.getTitle(),m.getDescription(),m.getRuntime(),m.getShowtimes(),m.getPrice(),m.getRating(),m.getUniqueID());

				}
			}
			if(boughtMovie==null){
				System.out.println("That movie is not on the list.");
				purchaseTickets(mt, c);
			}

		}
		else if(userInput == -1){
			purchaseTickets(mt, c);
		}
		else{
			System.out.println("That was not an option.");
			purchaseTickets(mt, c);
		}

		purchaseTicketForMovie(boughtMovie, c);

	}
	
	private static void purchaseTicketForMovie(Movie m, Customer c) {
		//Handle multiple showtimes being available, ticketPayment(t, c);

		Scanner scan = new Scanner(System.in);

		int i = 1;
		for(Date d: m.getShowtimes()){
			System.out.println(i+" "+d);
			i++;
		}
		System.out.println("Please enter the number for the showtime you would like to purchase.");
		int userInput = scan.nextInt()-1;
		if(userInput>m.getShowtimes().size()||userInput<0){
			System.out.println("That was not one of the available showtimes.");
			purchaseTicketForMovie(m, c);
		}
		else{
			Date showtime = m.getShowtimes().get(userInput);
			ticketPayment(c, m, showtime);
		}
	}
	
	private static void ticketPayment(Customer c, Movie m, Date showtime) {
		System.out.println(m.getTitle()+ " " + m.getPrice()+" "+showtime);
		//Handle payment method, add Tickets to Customer's ticket list
		//Can purchase several tickets for the same showtime at once, 
		//e.g. 1 Senior ticket, 2 Adult tickets
		//printReceipt(t, true);
		int numAdult=0, numSenior=0, numChild=0;
		Scanner scan = new Scanner(System.in);
		System.out.println("Select a ticket to add to your cart:\n 1. Adult - "+ numAdult +
				"\n2. Senior - "+numSenior+"\n3. Child - "+numChild);
		boolean shopping = true;
		System.out.println("Enter -1 to stop shopping.");
		int userInput;

		while(shopping){
			userInput = scan.nextInt();

			if(userInput==1){
				numAdult=numAdult+1;
			}
			else if(userInput == 2){
				numSenior=numSenior+1;
			}
			else if(userInput==3){
				numChild = numChild+1;
			}
			else if(userInput==-1){
				shopping=false;
			}
			else{
				System.out.println("That was not an option: ");
			}
			System.out.println("Adult - "+numAdult+" Senior - "+numSenior+" Child - "+numChild);
		}
		calculatePrice(c, m, numAdult,numSenior,numChild,showtime);

	}

	public static void calculatePrice(Customer c, Movie m, int adults, int seniors, int children, Date showtime){
		double total = 0;
		double adjustedPrice = m.getPrice();

		if(isMatinee(showtime)){
			adjustedPrice = adjustedPrice*.5;

		}
		if(adults>=2){
			for(int i = 0; i < children;i++ ){
				total += (.75)*adjustedPrice*(.5);

			}
		}
		else{
			if(isSaturday(showtime)) {
				for (int i = 0; i < children; i++) {
					if(i%2==0) {
						total += (.75) * adjustedPrice*(.5);
					}
				}
			}
			else{
				for(int i = 0; i<children;i++){
					total+=adjustedPrice *(.75);
				}
			}
		}
		for(int i = 0; i<adults;i++){
			total+=adjustedPrice;
		}
		if(!isWednesday(showtime)){
			for(int i = 0; i<seniors;i++){
				total+=adjustedPrice*(.75);
			}
		}

		pay(adults,seniors,children,total,c,m,showtime);
	}

	public static boolean isMatinee(Date d){
		return true;
	}
	public static void isFamily(){

	}
	public static boolean isSaturday(Date d){
		return true;
	}
	public static boolean isWednesday(Date d){
		return true;
	}

	private static void pay(int adults, int seniors, int children, double total, Customer c, Movie m, Date showtime){
		System.out.println("Are you paying cash or card?\nEnter 1 for cash and 2 for card.");
		Scanner scan = new Scanner(System.in);
		int userInput = scan.nextInt();

		if(userInput==1){
			System.out.println("Thank you for shopping with us!\nWe will now print out your receipt.");
			generateTickets(adults, seniors,children,c, m,showtime);
			printReceipt(temp,true,total);
		}
		else if(userInput==2){
			String cardInfo = "";
			System.out.println("Please enter the name on the card: ");
			cardInfo = scan.nextLine();
			System.out.println("Please enter the card number: ");
			cardInfo = scan.nextLine();
			System.out.println("Please enter the expiration date: ");
			cardInfo = scan.nextLine();
			System.out.println("Please enter the CCV: ");
			cardInfo = scan.nextLine();
			System.out.println("Thank you for shopping with us!\nWe will now print your receipt.");
			generateTickets(adults, seniors,children, c, m,showtime);
			printReceipt(temp,false,total);
		}

		else{
			System.out.println("That was not a valid option.");
			pay(adults,seniors,children,total,c, m, showtime);
		}
	}

	public static void generateTickets(int adults, int seniors, int children, Customer c, Movie m,Date showtime){
		temp = new ArrayList<>();
		//adding the tickets to the customers full array of tickets
		for(int i = 0; i < adults; i++){
			c.getTickets().add(new Ticket(m.getTitle(),"Adult",showtime));
		}
		for(int i = 0; i < seniors; i++){
			c.getTickets().add(new Ticket(m.getTitle(),"Senior",showtime));
		}
		for(int i = 0; i < children; i++){
			c.getTickets().add(new Ticket(m.getTitle(),"Child",showtime));
		}
		//adding tickets to the temporary reciept arraylist of tickets
		for(int i = 0; i < adults; i++){
			temp.add(new Ticket(m.getTitle(),"Adult",showtime));
		}
		for(int i = 0; i < seniors; i++){
			temp.add(new Ticket(m.getTitle(),"Senior",showtime));
		}
		for(int i = 0; i < children; i++){
			temp.add(new Ticket(m.getTitle(),"Child",showtime));
		}


	}



	private static void printReceipt(ArrayList<Ticket> t, boolean usedCash, double total) {
		//Prints information about what was purchased, etc.
		for(Ticket ticket: t){
			System.out.println(ticket.getMovie()+" "+ticket.getTicketType()+" "+ticket.getShowtime());
		}
		if(usedCash) {
			System.out.printf("You payed: $%.2f with cash", total);
		}
		else{
			System.out.printf("You payed: $%.2f witih card.", total);
		}
	}
	
	public static void viewPurchasedTickets(Customer c) {
		for(Ticket ticket: c.getTickets()){
			System.out.println(ticket.getMovie()+" "+ticket.getTicketType()+" "+ticket.getShowtime());
		}
	}
}
