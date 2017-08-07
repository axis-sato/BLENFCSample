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

class MainActivity : AppCompatActivity() {
    private lateinit var pendingIntent: PendingIntent
    private lateinit var mAdapter: NfcAdapter
    private lateinit var intentFiltersArray: Array<IntentFilter>


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        println("MainActivity onCreate")

        pendingIntent = PendingIntent.getActivity(
                this, 0, Intent(this, javaClass).addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP), 0)
        mAdapter = NfcAdapter.getDefaultAdapter(applicationContext)

        val ndef = IntentFilter(NfcAdapter.ACTION_NDEF_DISCOVERED);
        ndef.addDataType("*/*")
        intentFiltersArray = arrayOf(ndef)
//        techListsArray = new String[][] { new String[] { NfcF.class.getName() } };



        println("Intent: ${intent}")

        if (intent != null && intent.action == NfcAdapter.ACTION_NDEF_DISCOVERED) {
            val rawMessages = intent.getParcelableArrayExtra(NfcAdapter.EXTRA_NDEF_MESSAGES)
            if (rawMessages != null) {
                rawMessages
                        .map { it as NdefMessage }
                        .forEach { println("message: $it") }
            }
        }
    }

    override fun onPause() {
        super.onPause();
        mAdapter.disableForegroundDispatch(this);
    }

    override fun onResume() {
        super.onResume();
        mAdapter.enableForegroundDispatch(this, pendingIntent, intentFiltersArray, null);
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
