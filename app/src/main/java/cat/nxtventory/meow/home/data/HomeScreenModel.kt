package cat.nxtventory.meow.home.data

import android.os.Build
import androidx.annotation.RequiresApi
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.Calendar
import java.util.Locale

fun GetTodaysDayAndDateLegacy(): String {
    val calendar = Calendar.getInstance()
    val formatter = SimpleDateFormat("EEE, d MMMM yyyy", Locale.getDefault())
    return formatter.format(calendar.time)
}

@RequiresApi(Build.VERSION_CODES.O)
fun GetFormattedDate(): String {
    val currentDate = LocalDate.now()
    val formatter = DateTimeFormatter.ofPattern("EEE, dd'th' MMMM yyyy")
    return currentDate.format(formatter)
}

object HomeScreenSampleData {
    val user: String = "Sushanth"
    val SaleAmount: Double = 25249.22
    val numberOfBills: Int = 154
    val earnings: Double = 3950.86
    val availableInventoryItems: Int = 2985
    val customers: Int = 196
}

