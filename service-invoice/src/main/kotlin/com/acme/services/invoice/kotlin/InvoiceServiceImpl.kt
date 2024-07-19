package com.acme.services.invoice.kotlin

import com.acme.services.invoice.Invoice
import com.acme.services.invoice.InvoiceService
import com.acme.utils.AbstractService
import com.acme.workflows.invoicing.User
import java.time.LocalDateTime

class InvoiceServiceImpl: AbstractService(), InvoiceService {
    override fun create(user: User, amount: Double, start: LocalDateTime?, end: LocalDateTime?): Invoice {
        log("Creating new Invoice for user (${user.id}), amount ($amount), start ($start), end ($end))")
        return Invoice(user.id, amount, start, end)
    }
}