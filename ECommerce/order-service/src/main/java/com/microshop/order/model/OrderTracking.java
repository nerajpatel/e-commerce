// entity/OrderTracking.java
package com.microshop.order.model;

import jakarta.persistence.*;
import lombok.*;
import java.time.OffsetDateTime;

@Entity @Table(name="order_tracking")
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class OrderTracking {
  @Id @GeneratedValue(strategy=GenerationType.IDENTITY)
  private Long id;

  @ManyToOne(fetch=FetchType.LAZY) @JoinColumn(name="order_id", nullable=false)
  private Order order;

  @Column(nullable=false)
  private String courier;

  @Column(nullable=false)
  private String trackingNumber;

  @Column(nullable=false)
  private String status; // e.g., IN_TRANSIT, OUT_FOR_DELIVERY, DELIVERED

  private String note;

  private OffsetDateTime createdAt;
}
