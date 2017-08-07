package com.example.c8112002.blenfcsample

import android.app.Application
import android.app.PendingIntent
import android.content.Intent
import android.content.IntentFilter
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.nfc.NfcAdapter
import android.nfc.NdefMessage
import android.nfc.NdefRecord
import android.os.PatternMatcher

class MainActivity : AppCompatActivity() {
    private val pendingIntent: PendingIntent by lazy {
        PendingIntent.getActivity(
                this,
                0,
                Intent(this, javaClass).addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP),
                0
        )
    }
    private val mAdapter: NfcAdapter by lazy {
        NfcAdapter.getDefaultAdapter(applicationContext)
    }
    private val intentFiltersArray: Array<IntentFilter> by lazy {
        val ndef = IntentFilter(NfcAdapter.ACTION_NDEF_DISCOVERED);
        ndef.addDataScheme("vnd.android.nfc")
        ndef.addDataAuthority("ext", null);
        ndef.addDataPath("/android.com:pkg", PatternMatcher.PATTERN_PREFIX);
        arrayOf(ndef)
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        println("MainActivity onCreate")
        println("Intent: ${intent}")

        if (intent != null && intent.action == NfcAdapter.ACTION_NDEF_DISCOVERED) {
            val rawMessages = intent.getParcelableArrayExtra(NfcAdapter.EXTRA_NDEF_MESSAGES)
            rawMessages?.map { it as NdefMessage }?.forEach { println("message: $it") }
        }
    }


    override fun onResume() {
        super.onResume()
        mAdapter.enableForegroundDispatch(this, pendingIntent, intentFiltersArray, null)
    }

    override fun onPause() {
        super.onPause()
        mAdapter.disableForegroundDispatch(this)
    }

    override fun onNewIntent(intent: Intent) {
        println("onNewIntent")
        println("Intent: $intent")

        if (intent.action == NfcAdapter.ACTION_NDEF_DISCOVERED) {
            val rawMessages = intent.getParcelableArrayExtra(NfcAdapter.EXTRA_NDEF_MESSAGES)
            rawMessages?.map { it as NdefMessage }?.forEach { println("message: $it") }
        }
    }
}
