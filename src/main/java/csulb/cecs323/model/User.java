package csulb.cecs323.model;
import javax.persistence.*;
import java.io.Serializable;
import java.util.*;
import java.time.LocalDateTime;

/**
 * The User class represents an individual user of the site.  It has the following relationships:
 * A many-to-many recursive relationship with itself, representing a User being able to follow other users
 * A one-to-one subclass relationship (incomplete, disjoint) with both FoodCritic and Chef.  A User could be one, both,
 * or neither.
 */
@Entity
@Table(
        name = "user_account",
        uniqueConstraints = {
                @UniqueConstraint(columnNames = "email"),
                @UniqueConstraint(columnNames = "username")
        })
@Inheritance(strategy=InheritanceType.JOINED)
@DiscriminatorColumn(name="user_type")
@DiscriminatorValue("User")
public class User implements Serializable {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(nullable = false)
    private String firstName;
    
    @Column(nullable = false)
    private String lastName;

    @Column(nullable = false)
    private String userName;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false)
    private String email;

    @Column(nullable = false)
    private LocalDateTime dateRegistered;

    //Recursive relationship
    @ManyToMany
    @JoinTable(
            name = "user_account_junction",
            joinColumns = @JoinColumn(name="user_account", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name="user_following", referencedColumnName = "id"))
    private Set<User> usersFollowing;

    /**
     * Default constructor, initializes usersFollowing to an empty set
     */
    public User() { usersFollowing = new HashSet<User>(); }

    /**
     * Returns id an unique identifier made by users
     * @return id an unique identifier made by users
     */
    public Long getId() { return id; }
    
    /**
     * Set id an unique identifier made by users
     * @param id an unique identifier made by users
     */
    public void setId(Long id) { this.id = id; }

    /**
     * Returns firstName of the user
     * @return firstName of the user
     */
    public String getFirstName() { return firstName; }
    
    /**
     * Set firstName of the user
     * @param firstName of the user
     */
    public void setFirstName(String firstName) { this.firstName = firstName; }

    /**
     * Returns lastName of the user
     * @return lastName of the user
     */
    public String getLastName() { return lastName; }
    
    /**
     * Set lastName of the user
     * @param lastName of the user
     */
    public void setLastName(String lastName) { this.lastName = lastName; }

    /**
     * Returns userName a combination of numbers and letters representing specific user
     * @return userName a combination of numbers and letters representing specific user
     */
    public String getUserName() { return userName; }
    
    /**
     * Set userName a combination of numbers and letters representing specific user
     * @param userName a combination of numbers and letters representing specific user
     */
    public void setUserName(String userName) { this.userName = userName; }

    /**
     * Returns password a secret word or phrase that users set for their accounts
     * @return password a secret word or phrase that users set for their accounts
     */
    public String getPassword() { return password; }
    
    /**
     * Set password a secret word or phrase that users set for their accounts
     * @param password a secret word or phrase that users set for their accounts
     */
    public void setPassword(String password) { this.password = password; }

    /**
     * Returns email the email address users use to register
     * @return email the email address users use to register
     */
    public String getEmail() { return email; }
    
    /**
     * Set email the email address users use to register
     * @param email the email address users use to register
     */
    public void setEmail(String email) { this.email = email; }

    /**
     * Returns dateRegistered the date users register their accounts
     * @return dateRegistered the date users register their accounts
     */
    public LocalDateTime getDateRegistered() { return dateRegistered; }
    
    /**
     * Set dateRegistered the date users register their accounts
     * @param dateRegistered the date users register their accounts
     */
    public void setDateRegistered(LocalDateTime dateRegistered) { this.dateRegistered = dateRegistered; }

    /**
     * Returns users_following name of users who followed another user
     * @return users_following name of users who followed another user
     */
    public Set<User> getUsersFollowing() { return usersFollowing; }

    /**
     * Adds a user to the list of users this user is followed by
     * @param user user to be following
     */
    public void followUser(User user) { this.usersFollowing.add(user); }
    
    /**
     * Set users_following name of users who following another user
     * @param usersFollowed name of users who following another user
     */
    public void setUsersFollowing(Set<User> usersFollowed) { this.usersFollowing = usersFollowed; }
}
