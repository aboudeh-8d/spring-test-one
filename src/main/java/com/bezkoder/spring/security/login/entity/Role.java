package com.bezkoder.spring.security.login.entity;

import com.bezkoder.spring.security.login.entity.base.BaseEntity;
import com.bezkoder.spring.security.login.enums.ERole;

import javax.persistence.*;

@Entity
@Table(name = "roles")
public class Role extends BaseEntity {

  @Enumerated(EnumType.STRING)
  @Column(length = 20)
  private ERole name;

  public Role() {

  }

  public Role(ERole name) {
    this.name = name;
  }

  public ERole getName() {
    return name;
  }

  public void setName(ERole name) {
    this.name = name;
  }
}