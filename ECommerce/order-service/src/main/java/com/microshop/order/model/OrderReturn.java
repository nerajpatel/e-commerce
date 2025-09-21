// entity/OrderReturn.java
package com.microshop.order.model;

import jakarta.persistence.*;
import lombok.*;

import java.time.OffsetDateTime;

@Entity @Table(name="order_returns")
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class OrderReturn {
  @Id @GeneratedValue(strategy=GenerationType.IDENTITY)
  private Long id;

  @ManyToOne(fetch=FetchType.LAZY) @JoinColumn(name="order_id", nullable=false)
  private Order order;

  @Enumerated(EnumType.STRING)
  @Column(nullable=false, length=20)
  private ReturnStatus status;

  @Column(nullable=false)
  private String reason;

  private String resolutionNote;

  private OffsetDateTime createdAt;
  private OffsetDateTime updatedAt;
}
