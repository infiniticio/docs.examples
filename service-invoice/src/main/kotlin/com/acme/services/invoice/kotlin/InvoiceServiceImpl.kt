package com.acme.services.invoice.kotlin

import com.acme.contracts.services.invoice.Invoice
import com.acme.contracts.services.invoice.InvoiceService
import com.acme.common.AbstractService
import com.acme.contracts.workflows.invoicing.User
import java.time.LocalDateTime

class InvoiceServiceImpl: AbstractService(), InvoiceService {
    override fun create(user: User, amount: Double, start: LocalDateTime?, end: LocalDateTime?): Invoice {
        log("Creating new Invoice for user (${user.id}), amount ($amount), start ($start), end ($end))")
        return Invoice(user.id, amount, start, end)
    }
}