// TextToSpeechViewModel.kt
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class TextToSpeechViewModel : ViewModel() {

    private val _textToSpeech = MutableLiveData<String>()
    val textToSpeech: LiveData<String>
        get() = _textToSpeech

    fun setTextToSpeech(text: String) {
        _textToSpeech.value = text
    }

    fun clearTextToSpeech() {
        _textToSpeech.value = ""
    }
}
