package space.shefer.receipt.fnssdk.dto

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import java.time.LocalDateTime

@JsonIgnoreProperties(ignoreUnknown = true)
abstract class FnsReceiptDto {
    /**
     * The dateTime parameter had to be made abstract due to differences
     * in date formats in the FNS api and in the tax service mobile application.
     */
    abstract var dateTime: LocalDateTime?
    var cashTotalSum: Int = 0 // 0
    var discount: Double? = null // null
    var discountSum: Double? = null // null
    var ecashTotalSum: Int = 0 // 66430

    /**
     * Фискальный Документ. ФД.
     */
    var fiscalDocumentNumber: Int = 0 // 28649

    /**
     * Фискальный Номер. ФН.
     */
    var fiscalDriveNumber: String = "" // 9280440300037581

    /**
     * Фискальный признак. ФП.
     */
    var fiscalSign: Long = 0 // 2869721424
    var items: List<FnsItemDto> = emptyList() // Список товаров
    var kktNumber: String? = null // null
    var kktRegId: String? = null// 0000270725037066
    var nds0: Int? = null // null
    var nds10: Int? = null // 4706
    var nds18: Int? = null // 2445
    var ndsCalculated10: Int? = null // null
    var ndsCalculated18: Int? = null // null
    var ndsNo: Int? = null // null
    var operationType: Int = 1 // 1 is for incoming (for seller)
    var `operator`: String? = null // Пикина 97283.
    var requestNumber: Int = 0 // 172
    var retailPlaceAddress: String? = null // 197341, Санкт-Петербург, б-р. Серебристый, д. 19, к. 1
    var shiftNumber: Int = 0 // 57
    var taxationType: Int = 0 // 1

    /**
     * Сумма покупки в копейках
     */
    var totalSum: Int = 0 // 66430
    var user: String? = null // ООО "ТД Интерторг"
    var userInn: String? = null // 7842005813
}
