package com.example.audio

import android.media.AudioAttributes
import android.media.AudioFormat
import android.media.AudioTrack
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.isActive
import kotlinx.coroutines.launch
import kotlin.math.PI
import kotlin.math.sin

object AmbientAudioPlayer {

    private val _isPlaying = MutableStateFlow(false)
    val isPlaying: StateFlow<Boolean> = _isPlaying

    private var audioTrack: AudioTrack? = null
    private var playbackJob: Job? = null
    private val scope = CoroutineScope(Dispatchers.Default)

    // Pentatonic calm frequencies (E4, G#4, B4, C#5, E5, F#5, G#5)
    private val chordNotes = listOf(
        doubleArrayOf(329.63, 415.30, 493.88),  // E maj
        doubleArrayOf(277.18, 329.63, 415.30),  // C# min
        doubleArrayOf(220.00, 329.63, 440.00),  // A maj
        doubleArrayOf(246.94, 369.99, 493.88)   // B maj
    )

    fun toggle() {
        if (_isPlaying.value) {
            stop()
        } else {
            start()
        }
    }

    fun start() {
        if (_isPlaying.value) return
        _isPlaying.value = true

        playbackJob = scope.launch {
            val sampleRate = 44100
            val bufferSize = AudioTrack.getMinBufferSize(
                sampleRate,
                AudioFormat.CHANNEL_OUT_MONO,
                AudioFormat.ENCODING_PCM_16BIT
            ).coerceAtLeast(4096)

            audioTrack = AudioTrack.Builder()
                .setAudioAttributes(
                    AudioAttributes.Builder()
                        .setUsage(AudioAttributes.USAGE_MEDIA)
                        .setContentType(AudioAttributes.CONTENT_TYPE_MUSIC)
                        .build()
                )
                .setAudioFormat(
                    AudioFormat.Builder()
                        .setEncoding(AudioFormat.ENCODING_PCM_16BIT)
                        .setSampleRate(sampleRate)
                        .setChannelMask(AudioFormat.CHANNEL_OUT_MONO)
                        .build()
                )
                .setBufferSizeInBytes(bufferSize)
                .setTransferMode(AudioTrack.MODE_STREAM)
                .build()

            audioTrack?.play()

            var chordIndex = 0
            val pcmBuffer = ShortArray(1024)

            while (isActive && _isPlaying.value) {
                val freqs = chordNotes[chordIndex % chordNotes.size]
                chordIndex++

                val noteDurationSec = 3.5
                val totalSamples = (sampleRate * noteDurationSec).toInt()

                for (sampleIdx in 0 until totalSamples) {
                    if (!isActive || !_isPlaying.value) break

                    val t = sampleIdx.toDouble() / sampleRate
                    
                    // Smooth envelope fade in and out
                    val attack = (t / 0.8).coerceAtMost(1.0)
                    val release = ((noteDurationSec - t) / 1.2).coerceIn(0.0, 1.0)
                    val envelope = attack * release

                    var sampleVal = 0.0
                    for (i in freqs.indices) {
                        val f = freqs[i]
                        // Fundamental + soft overtone for warm bell/piano sound
                        sampleVal += sin(2.0 * PI * f * t) * 0.5
                        sampleVal += sin(2.0 * PI * (f * 2.0) * t) * 0.15
                        sampleVal += sin(2.0 * PI * (f * 3.0) * t) * 0.05
                    }

                    // Normalize & apply master volume
                    sampleVal = (sampleVal / freqs.size) * envelope * 0.25
                    val pcmSample = (sampleVal * 32767.0).toInt().coerceIn(-32768, 32767).toShort()

                    val bufferPos = sampleIdx % pcmBuffer.size
                    pcmBuffer[bufferPos] = pcmSample

                    if (bufferPos == pcmBuffer.size - 1 || sampleIdx == totalSamples - 1) {
                        val countToWrite = if (bufferPos == pcmBuffer.size - 1) pcmBuffer.size else bufferPos + 1
                        audioTrack?.write(pcmBuffer, 0, countToWrite)
                    }
                }
                delay(200)
            }
            stopTrack()
        }
    }

    fun stop() {
        _isPlaying.value = false
        playbackJob?.cancel()
        stopTrack()
    }

    private fun stopTrack() {
        try {
            audioTrack?.apply {
                if (playState == AudioTrack.PLAYSTATE_PLAYING) {
                    stop()
                }
                release()
            }
        } catch (_: Exception) {}
        audioTrack = null
    }
}
