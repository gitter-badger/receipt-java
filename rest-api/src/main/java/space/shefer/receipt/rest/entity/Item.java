package space.shefer.receipt.rest.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Table(name = "item")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Item {

  @Id
  @SequenceGenerator(name = "item_id_seq", sequenceName = "item_id_seq", allocationSize = 1)
  @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "item_id_seq")
  @Column(name = "id", nullable = false)
  private Long id;

  @Column(name = "text", nullable = false)
  private String text;

  @Column(name = "price", nullable = false)
  private Double price;

  @Column(name = "amount", nullable = false)
  private Double amount;

  @ManyToOne(targetEntity = Receipt.class)
  @JoinColumn(name = "receipt_id")
  private Receipt receipt;

}
