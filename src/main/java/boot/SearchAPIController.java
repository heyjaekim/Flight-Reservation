package boot;

import org.springframework.web.bind.annotation.*;
import org.springframework.beans.factory.annotation.*;

// needed for cross domain Ajax
@CrossOrigin(origins={"http://codepen.io", "https://codepen.io", "https://cdpn.io"})
@RestController
public class SearchAPIController {

  // private static final Logger log = LoggerFactory.getLogger(SearchAPIController.class);

  @Autowired
  SearchService ss;

	@RequestMapping(value="/search", method=RequestMethod.GET)
  public Iterable<String> search(
    @RequestParam(name = "da") String da,
    @RequestParam(name = "aa") String aa,
    @RequestParam(name = "dd") String dd,
    @RequestParam(name = "nl") Integer nl,
    @RequestParam(name = "xl") Integer xl
  ) {
    // String da = "EWR"; // da - departure airport
    // String aa = "BCN"; // aa - arrival airport
    // String dd = "2019-11-14"; // dd - departure weekday
    // Integer nl = new Integer(60); // nl - min layover minutes
    // Integer xl = new Integer(90); // xl - max layover minutes
    
    return ss.search(da, aa, dd, nl, xl);
  }
}
