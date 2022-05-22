package com.tutorial.crud.shared;

import lombok.Getter;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.util.Date;

@Getter
@MappedSuperclass
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@EntityListeners(AuditingEntityListener.class)
public abstract class Auditable<U> {

  @CreatedBy
  @Column(name = "ID_CREATED_BY")
  protected String createdBy;

  @CreatedDate
  @Column(name = "DATE_CREATED")
  protected Date creationDate;

  @LastModifiedBy
  @Column(name = "ID_LAST_MODIFIED_BY")
  protected String lastModifiedBy;

  @LastModifiedDate
  @Column(name = "DATE_LAST_MODIFIED")
  protected Date lastModifiedDate;
}
