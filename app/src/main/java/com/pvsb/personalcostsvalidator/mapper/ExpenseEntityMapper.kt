package com.pvsb.personalcostsvalidator.mapper

import com.pvsb.personalcostsvalidator.ExpenseEntity
import com.pvsb.personalcostsvalidator.entity.Expense
import java.text.SimpleDateFormat
import java.util.Date

object ExpenseEntityMapper {

    fun Expense.toEntity(): ExpenseEntity {
        return ExpenseEntity(
            id = id.toLong(),
            title = title,
            createdAt = formatDateToString(createdAt),
            value_ = value,
        )
    }

    fun ExpenseEntity.toModel(): Expense {
        return Expense(
            id = id.toInt(),
            title = title,
            createdAt = getDateFromString(createdAt),
            value = value_
        )
    }

    private fun getDateFromString(dateString: String): Date? {
        return try {
            SimpleDateFormat.getInstance().parse(dateString)
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }
    }

    private fun formatDateToString(date: Date?): String {
        return try {
            date?.let {
                SimpleDateFormat.getInstance().format(date)
            } ?: throw NullPointerException("${super.toString()} date is null")
        } catch (e: Exception) {
            e.printStackTrace()
            ""
        }
    }
}