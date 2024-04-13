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
    val todaySaleAmount: Double = 5249.00
    val todayBills: Int = 154
    val todayEarnigs: Double = 8950.00
    val instockItems: Int = 2985
}

