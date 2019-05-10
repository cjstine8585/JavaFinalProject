
/*
import static org.junit.jupiter.api.Assertions.*;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import org.junit.jupiter.api.Test;

class MovieTest {

	@Test
	void test() {
		SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");
		try {
			Date today = sdf.parse("04/25/2019");
			ArrayList<Date> showtimes = new ArrayList<>();
			showtimes.add(today);
			Movie m = new Movie("title", "desc", "runtime", showtimes, 0, "PG-13", 0);
			assert(m.isThisWeek());
			assert(m.isToday()); //These will fail depending on what day this is tested on
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

}
*/