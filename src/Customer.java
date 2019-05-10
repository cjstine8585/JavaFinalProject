import java.util.ArrayList;

public class Customer
{

    private ArrayList<Ticket> tickets = new ArrayList<>();

    public Customer(ArrayList<Ticket> tickets){
        super();
        this.tickets = tickets;
    }

    public ArrayList<Ticket> getTickets() {
        return tickets;
    }

    public void setTickets(ArrayList<Ticket> tickets) {
        this.tickets = tickets;
    }
}
