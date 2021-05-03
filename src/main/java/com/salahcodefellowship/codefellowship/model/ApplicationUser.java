package com.salahcodefellowship.codefellowship.model;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.util.Collection;
import java.util.List;
import java.util.Set;

@Entity
public class ApplicationUser implements UserDetails {

    public ApplicationUser(String password, String username, String firstName, String lastName, String dateOfBirth, String bio) {
        this.password = password;
        this.username = username;
        this.firstName = firstName;
        this.lastName = lastName;
        this.dateOfBirth = dateOfBirth;
        this.bio = bio;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String password;
    @Column(unique = true)
    private String username;
    private String firstName;
    private String lastName;
    private String dateOfBirth;
    private String bio;
    private boolean isAdmin;

    @OneToMany(mappedBy = "writer")
    List<Post> posts;

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(String dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public String getBio() {
        return bio;
    }

    public void setBio(String bio) {
        this.bio = bio;
    }

    public boolean isAdmin() {
        return isAdmin;
    }

    public void setAdmin(boolean admin) {
        isAdmin = admin;
    }

    public List<Post> getPosts() {
        return posts;
    }

    public void setPosts(List<Post> posts) {
        this.posts = posts;
    }

    public Set<ApplicationUser> getFollowing() {
        return following;
    }

    public void setFollowing(Set<ApplicationUser> following) {
        this.following = following;
    }

    public Set<ApplicationUser> getFollower() {
        return follower;
    }

    public void setFollower(Set<ApplicationUser> follower) {
        this.follower = follower;
    }

    @ManyToMany
    @JoinTable(
            name = "following_followers",
            joinColumns = {@JoinColumn(name = "follower")},
            inverseJoinColumns = {@JoinColumn(name = "following")}
    )
    Set<ApplicationUser> following;


    @ManyToMany(mappedBy = "following")
    Set<ApplicationUser> follower;

    public ApplicationUser() {

    }



    public ApplicationUser(String password, String username, String firstName, String lastName, String dateOfBirth, String bio, boolean isAdmin, List<Post> posts, Set<ApplicationUser> following, Set<ApplicationUser> follower) {
        this.password = password;
        this.username = username;
        this.firstName = firstName;
        this.lastName = lastName;
        this.dateOfBirth = dateOfBirth;
        this.bio = bio;
        this.isAdmin = isAdmin;
        this.posts = posts;
        this.following = following;
        this.follower = follower;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return null;
    }

    public Integer getId() {
        return id;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    @Override
    public String getPassword() {
        return this.password;
    }

    @Override
    public String getUsername() {
        return this.username;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    @Override
    public String toString() {
        return "this is user " + this.username;
    }
}
