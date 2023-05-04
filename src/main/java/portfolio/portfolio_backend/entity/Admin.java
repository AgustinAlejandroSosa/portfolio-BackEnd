package portfolio.portfolio_backend.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import lombok.Data;

@Data
@Entity
public class Admin{
  
  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private long id;

  private String username;
  
  private String password;

  public Admin(){}

  public Admin(String username, String password){
    this.username = username;
    this.password = password;
  }
  
}
