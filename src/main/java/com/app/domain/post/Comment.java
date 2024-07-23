package com.app.domain.post;

import com.app.domain.user.ForoUser;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.ForeignKey;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "comment")
public class Comment {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @ManyToOne
  @JoinColumn(name = "id_user", referencedColumnName = "id", foreignKey = @ForeignKey(name = "FK_user_comment"), nullable = false)
  private ForoUser user;

  @ManyToOne
  @JoinColumn(name = "id_entry_commented", referencedColumnName = "id", foreignKey = @ForeignKey(name = "FK_entry_comment"), nullable = false)
  private Entry entry;

  @Column(nullable = false)
  private String content;

  // Getters
  public Long getId() {
    return id;
  }

  public ForoUser getUser() {
    return user;
  }

  public Entry getEntry() {
    return entry;
  }

  public String getContent() {
    return content;
  }

  // Setters
  public void setUser(ForoUser user) {
    this.user = user;
  }

  public void setEntry(Entry entry) {
    this.entry = entry;
  }

  public void setContent(String content) {
    this.content = content;
  }
}
