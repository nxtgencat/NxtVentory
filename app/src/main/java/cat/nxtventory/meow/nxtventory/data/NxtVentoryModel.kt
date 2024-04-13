package cat.nxtventory.meow.nxtventory.data

import io.github.jan.supabase.createSupabaseClient
import io.github.jan.supabase.postgrest.Postgrest
import kotlinx.serialization.Serializable
import java.util.UUID

val supabase = createSupabaseClient(
    supabaseUrl = "https://khfuazacgobrmgyehgex.supabase.co",
    supabaseKey = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpc3MiOiJzdXBhYmFzZSIsInJlZiI6ImtoZnVhemFjZ29icm1neWVoZ2V4Iiwicm9sZSI6ImFub24iLCJpYXQiOjE3MTAyNjU3NTcsImV4cCI6MjAyNTg0MTc1N30.E0lSMvCv-Huk9JiUdD3gymHv0vDVBoWc4EXaETf5wC4"
) {
    install(Postgrest)
}

@Serializable
data class Country(
    val id: Int,
    val name: String,
)

@Serializable
data class NxtVentoryUser(
    val id: Int,
    val username: String,
    val name: String,
    val email: String,
    val phone: String,
    val role: String,
)

val newId = UUID.randomUUID()
