package pl.sepka.mvvmrecipeapp.util

import android.annotation.SuppressLint
import java.text.SimpleDateFormat
import java.util.*

object DateUtil {
    private const val dashPattern = "dd-MM-yyyy"
    private const val slashPattern = "dd/MM/yyyy"

    @SuppressLint("SimpleDataFormat")
    fun dateToSlashText(date: Date): String = SimpleDateFormat(slashPattern).format(date)

    @SuppressLint
    fun dateToDashText(date: Date): String = SimpleDateFormat(dashPattern).format(date)
}
