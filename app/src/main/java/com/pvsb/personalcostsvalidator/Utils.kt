package com.pvsb.personalcostsvalidator

import java.text.SimpleDateFormat
import java.util.Date

fun Date.formatToStringDate(): String {
    return SimpleDateFormat.getInstance().format(this)
}