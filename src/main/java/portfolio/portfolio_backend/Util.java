/*package portfolio.portfolio_backend;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import portfolio.portfolio_backend.entity.Admin;
import portfolio.portfolio_backend.service.AdminService;

@Component
public class Util implements CommandLineRunner {

  @Autowired
  AdminService adminService;

  @Autowired
  PasswordEncoder passwordEncoder;

  @Override
  public void run(String... args) throws Exception {

    String passwordEncode = passwordEncoder.encode("okaysoftware");

    Admin admin = new Admin("admin", passwordEncode);

    adminService.saveAdmin(admin);
  }

}*/
