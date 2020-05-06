package space.shefer.receipt.rest.service;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import space.shefer.receipt.rest.dto.ReceiptCreateDto;
import space.shefer.receipt.rest.dto.ReceiptCreateNewFormatDto;
import space.shefer.receipt.rest.dto.ReceiptMetaDto;
import space.shefer.receipt.rest.dto.ReceiptStatus;
import space.shefer.receipt.rest.dto.ReportMetaFilter;
import space.shefer.receipt.rest.entity.Receipt;
import space.shefer.receipt.rest.repository.ReceiptRepository;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ReceiptService {

  @Value(value = "${receipt.place.default}")
  private String defaultPlace;

  private final ReceiptRepository receiptRepository;

  @Autowired
  public ReceiptService(ReceiptRepository receiptRepository) {
    this.receiptRepository = receiptRepository;
  }

  public List<ReceiptMetaDto> getReceipts(ReportMetaFilter metaFilter) {
    List<Receipt> receipts = receiptRepository.getReceipts(metaFilter);
    return receipts.stream().map(Receipt::toDto)
      .peek(this::setDefaultPlaceIfNull)
      .collect(Collectors.toList());
  }

  public Receipt create(ReceiptCreateDto receipt) {
    List<Receipt> matchingReceipts = receiptRepository.getReceipts(
      ReportMetaFilter.builder()
        .fn(receipt.getFn())
        .fd(receipt.getFd())
        .fp(receipt.getFp())
        .dateFrom(receipt.getDate())
        .dateTo(receipt.getDate())
        .sumMax(receipt.getSum())
        .sumMin(receipt.getSum())
        .build()
    );

    if (!matchingReceipts.isEmpty()) {
      return matchingReceipts.get(0);
    }

    Receipt entity = new Receipt();
    entity.setFrom(receipt);
    entity.setStatus(ReceiptStatus.IDLE);
    Receipt savedReceipt = receiptRepository.save(entity);
    return savedReceipt;
  }

  public Receipt setStatus(Receipt receipt, ReceiptStatus status) {
    receipt.setStatus(status);
    return receiptRepository.save(receipt);
  }

  public List<Receipt> getAllIdle() {
    return receiptRepository.findAllIdle();
  }

  private void setDefaultPlaceIfNull(ReceiptMetaDto i) {
    if (i.getPlace() == null && !StringUtils.isBlank(defaultPlace)) {
      i.setPlace(defaultPlace);
    }
  }

}
