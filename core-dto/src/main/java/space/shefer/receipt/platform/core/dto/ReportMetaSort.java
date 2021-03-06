package space.shefer.receipt.platform.core.dto;

import lombok.Getter;

public enum ReportMetaSort {
  DATE("date");

  ReportMetaSort(String receiptFieldName){
    this.receiptFieldName = receiptFieldName;
  }

  @Getter
  private final String receiptFieldName;

}
