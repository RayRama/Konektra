// TextToSpeechViewModel.kt
import androidx.lifecycle.ViewModel
import com.dicoding.konektraapplication.data.HistoryItem
import java.util.*

class TextToSpeechViewModel : ViewModel() {

    private val _historyList = mutableListOf<HistoryItem>()
    val historyList: List<HistoryItem> get() = _historyList.toList()

    fun addToHistory(text: String) {
        _historyList.add(HistoryItem(text, Date()))
    }

    fun clearHistory() {
        _historyList.clear()
    }
}
