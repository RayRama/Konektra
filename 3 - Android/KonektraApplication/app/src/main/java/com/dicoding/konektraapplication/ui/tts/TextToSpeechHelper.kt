//package com.dicoding.konektraapplication.ui.tts
//
//import android.content.Context
//import android.media.MediaPlayer
//import android.speech.tts.TextToSpeech
//import android.widget.Toast
//import com.dicoding.konektraapplication.R
//import com.google.api.gax.core.FixedCredentialsProvider
//import com.google.auth.oauth2.GoogleCredentials
//import com.google.cloud.texttospeech.v1.*
//import java.io.ByteArrayInputStream
//import java.io.File
//import java.io.IOException
//import java.io.InputStream
//import java.util.Locale
//
//class TextToSpeechHelper(private val context: Context) : TextToSpeech.OnInitListener {
//
//    private lateinit var textToSpeech: TextToSpeech
//    private lateinit var credentials: GoogleCredentials
//
//    init {
//        // Inisialisasi kredensial saat objek TextToSpeechHelper dibuat
//        initializeCredentials()
//    }
//
//    private fun initializeCredentials() {
//        try {
//            val credentialsStream: InputStream = context.resources.openRawResource(R.raw.task)
//            credentials = GoogleCredentials.fromStream(credentialsStream)
//                .createScoped(listOf("https://www.googleapis.com/auth/cloud-platform"))
//        } catch (e: IOException) {
//            e.printStackTrace()
//        }
//    }
//
//    fun speakText(text: String) {
//        if (!::textToSpeech.isInitialized) {
//            textToSpeech = TextToSpeech(context, this)
//        } else {
//            speak(text)
//        }
//    }
//
//    private fun speak(text: String) {
//        val ttsClient = TextToSpeechClient.create(
//            TextToSpeechSettings.newBuilder()
//                .setCredentialsProvider(FixedCredentialsProvider.create(credentials))
//                .build()
//        )
//
//
//
//        val input = SynthesisInput.newBuilder().setText(text).build()
//        val voice = VoiceSelectionParams.newBuilder()
//            .setLanguageCode("en-US") // Sesuaikan dengan kode bahasa yang diinginkan
//            .setName("en-US-Standard-C") // Sesuaikan dengan suara yang diinginkan
//            .build()
//        val audioConfig = AudioConfig.newBuilder().setAudioEncoding(AudioEncoding.LINEAR16).build()
//
//        val response = ttsClient.synthesizeSpeech(input, voice, audioConfig)
//
//        // Dapatkan audioData dari response
//        val audioData = response.audioContent.toByteArray()
//
//        // Memainkan suara menggunakan MediaPlayer
//        val mediaPlayer = MediaPlayer()
//        val file = File(context.cacheDir, "audio.mp3")
//        file.writeBytes(audioData)
//
//// Setel dataSource menggunakan path file lokal
//        mediaPlayer.setDataSource(file.absolutePath)
//        mediaPlayer.prepare()
//        mediaPlayer.start()
//
//        ttsClient.close()
//    }
//
//    override fun onInit(status: Int) {
//        if (status == TextToSpeech.SUCCESS) {
//            // Inisialisasi sukses, atur bahasa suara ke bahasa Inggris (US)
//            val result = textToSpeech.setLanguage(Locale.US)
//
//            if (result == TextToSpeech.LANG_MISSING_DATA ||
//                result == TextToSpeech.LANG_NOT_SUPPORTED
//            ) {
//                // Bahasa tidak didukung, berikan umpan balik kepada pengguna
//                showToast("Bahasa tidak didukung.")
//            } else {
//                // Inisialisasi sukses dan bahasa didukung, mulai pembacaan teks
//                speak("Hello, this is a test.")
//            }
//        } else {
//            showToast("Gagal menginisialisasi Text-to-Speech.")
//        }
//    }
//
//    private fun showToast(message: String) {
//        Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
//    }
//
//    fun onDestroy() {
//        if (::textToSpeech.isInitialized) {
//            textToSpeech.stop()
//            textToSpeech.shutdown()
//        }
//    }
//}
