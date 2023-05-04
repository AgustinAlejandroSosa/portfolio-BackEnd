package portfolio.portfolio_backend.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import portfolio.portfolio_backend.entity.Admin;
import portfolio.portfolio_backend.repository.AdminRepository;

@Service
public class AdminService implements UserDetailsService {

  @Autowired
  PasswordEncoder passwordEncoder;

  @Autowired
  private AdminRepository adminRepository;

  @Transactional
  public void saveAdmin(Admin admin) throws Exception {
    try {
      adminRepository.save(admin);
    } catch (Exception e) {
      System.err.println("Error --" + e.getMessage());
    }
  }

  @Transactional
  public void deleteAdmin() {
    try {
      adminRepository.deleteAll();
    } catch (Exception e) {
      System.err.println("Error --" + e.getMessage());
    }
  }

  @Transactional
  public Admin getAdmin() {
    try {

      List<Admin> admin = adminRepository.findAll();

      if (admin.size() > 1)
        throw new Exception("There is more than one admin");

      return admin.get(0);

    } catch (Exception e) {
      System.err.println("Error --" + e.getMessage());
      return null;
    }
  }

  @Transactional
  public Admin modifyAdmin(Admin admin, Long id) {
    try {

      Optional<Admin> result = adminRepository.findById(id);
      if (result.isPresent()) {
        admin.setId(result.get().getId());
        adminRepository.save(admin);
        return admin = result.get();
      } else {
        throw new Exception("There is no entity with id:" + id);
      }
    } catch (Exception e) {
      System.err.println("Error --" + e.getMessage());
      return null;
    }
  }


  @Transactional
  @Override
  public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

    Admin admin = getAdmin();

    if (admin != null) {

      List<GrantedAuthority> authorities = new ArrayList<>();

      GrantedAuthority authority = new SimpleGrantedAuthority("ROLE_ADMIN");

      authorities.add(authority);

      User user = new User(admin.getUsername(), admin.getPassword(), authorities);

      return user;

    } else {

      return null;

    }
  }

}
