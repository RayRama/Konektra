package com.dicoding.konektraapplication

import android.content.res.AssetManager
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.SystemClock
import androidx.core.net.toUri
import androidx.test.core.app.ApplicationProvider
import androidx.test.platform.app.InstrumentationRegistry
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.dicoding.konektraapplication.ui.sst.GestureRecognizerHelper
import com.google.mediapipe.framework.image.BitmapImageBuilder
import com.google.mediapipe.tasks.components.containers.Category
import com.google.mediapipe.tasks.vision.core.RunningMode

import org.junit.Test
import org.junit.runner.RunWith

import org.junit.Assert.*
import org.junit.Before
import java.io.File
import java.io.FileOutputStream
import java.io.InputStream
import java.util.concurrent.locks.Condition
import java.util.concurrent.locks.ReentrantLock
import kotlin.concurrent.withLock
import kotlin.math.min

@RunWith(AndroidJUnit4::class)
class ExampleInstrumentedTest {

    private companion object {
        private const val TEST_IMAGE = "hand_thumb_up.jpg"
        private const val TEST_VIDEO = "test_video.mp4"
    }

    private val expectedCategoriesForImageAndLiveStreamMode = listOf(
        Category.create(0.8105f, 0, "P", ""),
    )

    private val expectedCategoryForVideoMode = listOf(
        Category.create(0.8193482f, 0, "P", ""),
    )
    private lateinit var lock: ReentrantLock
    private lateinit var condition: Condition

    @Before
    fun setup() {
        lock = ReentrantLock()
        condition = lock.newCondition()
    }

    /**
     * Verify that the result returned from the Gesture Recognizer Helper with
     * LIVE_STREAM mode is within the acceptable range to the expected result.
     */
    @Test
    @Throws(Exception::class)
    fun recognizerLiveStreamModeResultFallsWithinAcceptedRange() {
        var recognizerResult: GestureRecognizerHelper.ResultBundle? = null
        val gestureRecognizerHelper =
            GestureRecognizerHelper(context = ApplicationProvider.getApplicationContext(),
                runningMode = RunningMode.LIVE_STREAM,
                gestureRecognizerListener = object :
                    GestureRecognizerHelper.GestureRecognizerListener {
                    override fun onError(error: String, errorCode: Int) {

                        println(error)


                        lock.withLock {
                            condition.signal()
                        }
                    }

                    override fun onResults(resultBundle: GestureRecognizerHelper.ResultBundle) {
                        recognizerResult = resultBundle


                        lock.withLock {
                            condition.signal()
                        }
                    }
                })


        val testImage = loadImage(TEST_IMAGE)
        val mpImage = BitmapImageBuilder(testImage).build()


        gestureRecognizerHelper.recognizeAsync(
            mpImage, SystemClock.uptimeMillis()
        )


        lock.withLock {
            condition.await()
        }


        assertNotNull(recognizerResult)


        val categories = recognizerResult!!.results.first().gestures().first()


        assert(categories.isNotEmpty())


        assertEquals(
            expectedCategoriesForImageAndLiveStreamMode.first().score(),
            categories.first().score(),
            0.01f
        )


        assertEquals(
            expectedCategoriesForImageAndLiveStreamMode.first().categoryName(),
            categories.first().categoryName()
        )
    }

    /**
     * Verify that the result returned from the Gesture Recognizer Helper with
     * VIDEO mode is within the acceptable range to the expected result.
     */
    @Test
    fun recognizerVideoModeResultFallsWithinAcceptedRange() {
        val gestureRecognizerHelper = GestureRecognizerHelper(
            context = ApplicationProvider.getApplicationContext(),
            runningMode = RunningMode.VIDEO,
        )

        val videoUri = getVideoUri(TEST_VIDEO)


        val recognizerResult = gestureRecognizerHelper.recognizeVideoFile(
            videoUri,
            300
        )


        assertNotNull(recognizerResult)

        // Average scores of all frames.
        val hashMap = HashMap<String, Pair<Float, Int>>()
        recognizerResult!!.results.forEach { frameResult ->
            if (frameResult.gestures().isNotEmpty()) {
                frameResult.gestures().first().forEach {
                    if (hashMap.containsKey(it.categoryName())) {
                        hashMap[it.categoryName()] = Pair(
                            hashMap[it.categoryName()]!!.first + it.score(),
                            hashMap[it.categoryName()]!!.second + 1
                        )
                    } else {
                        hashMap[it.categoryName()] = Pair(it.score(), 1)
                    }
                }
            }
        }
        val actualAverageCategories = hashMap.map {
            val averageScore = it.value.first / it.value.second
            Category.create(averageScore, 0, it.key, "")
        }.toList().sortedByDescending { it.score() }

        val minSize =
            min(
                actualAverageCategories.size, expectedCategoryForVideoMode.size
            )

        for (i in 0 until minSize) {
            // Verify that the categories are correct.
            assertEquals(
                expectedCategoryForVideoMode[i].categoryName(),
                actualAverageCategories[i].categoryName()
            )

            // Verify that the scores are correct.
            assertEquals(
                expectedCategoryForVideoMode[i].score(),
                actualAverageCategories[i].score(), 0.05f
            )
        }
    }

    /**
     * Verify that the result returned from the Gesture Recognizer Helper with
     * IMAGE mode is within the acceptable range to the expected result.
     */
    @Test
    fun recognizerImageModeResultFallsWithinAcceptedRange() {
        val gestureRecognizerHelper = GestureRecognizerHelper(
            context = ApplicationProvider.getApplicationContext(),
            runningMode = RunningMode.IMAGE,
        )

        val bitmap = loadImage(TEST_IMAGE)


        val recognizerResult =
            gestureRecognizerHelper.recognizeImage(bitmap!!)?.results?.first()


        assertNotNull(recognizerResult)


        val actualCategories =
            recognizerResult!!.gestures().first()

        assert(actualCategories.isNotEmpty())


        assertEquals(
            expectedCategoriesForImageAndLiveStreamMode.first().categoryName(),
            actualCategories.first().categoryName()
        )


        assertEquals(
            expectedCategoriesForImageAndLiveStreamMode.first().score(),
            actualCategories.first().score(), 0.01f
        )
    }

    @Throws(Exception::class)
    private fun loadImage(fileName: String): Bitmap? {
        val assetManager: AssetManager =
            InstrumentationRegistry.getInstrumentation().context.assets
        val inputStream: InputStream = assetManager.open(fileName)
        return BitmapFactory.decodeStream(inputStream)
    }

    @Throws(Exception::class)
    private fun getVideoUri(videoName: String): Uri {
        val assetManager: AssetManager =
            InstrumentationRegistry.getInstrumentation().context.assets
        val file = File.createTempFile("test_video", ".mp4")
        val output = FileOutputStream(file)
        val inputStream: InputStream = assetManager.open(videoName)
        inputStream.copyTo(output)
        return file.toUri()
    }
}