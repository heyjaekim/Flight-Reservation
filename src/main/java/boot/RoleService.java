package boot;

import java.security.Principal;
import java.util.ArrayList;
import org.slf4j.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

@Service
public class RoleService {
  @Autowired
  private JdbcTemplate jdbc;

	private static final Logger log = LoggerFactory.getLogger(RoleService.class);

  public Role save(Role role) {
    jdbc.update("INSERT INTO role (username, role) VALUES (?, ?)", 
      role.getUsername(), role.getRole() // arguments
    );
    return role;
  }
    
  public void delete(String username, String role) {
    jdbc.update("DELETE FROM role WHERE username=? AND role=?", username, role);
  }

  public void delete(String username) {
    jdbc.update("DELETE FROM role WHERE username=?", username);
  }
  

  public Iterable<Role> getRoles(String name) {
    if (name != null) {
      return jdbc.query("SELECT username, role FROM role where username=?", 
        new Object[] { name }, // arguments as array
        (rs, rowNum) -> new Role(rs.getString("username"), rs.getString("role"))); // row mapper
    } else {
      return new ArrayList<Role>();
    }
  }

  public boolean isCustomerRep(Principal p) {
    String username = p.getName();
    Iterable<Role> roles = getRoles(username);
    boolean isCustomerRep = false;
    for(Role r:roles) {
      if (r.getRole().equals("customer_representative")) {
        isCustomerRep = true;
      }
    }
    return isCustomerRep;
  }
}