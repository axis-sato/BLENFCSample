package com.example.c8112002.blenfcsample

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.nfc.NfcAdapter
import android.nfc.NdefMessage
import android.nfc.NdefRecord

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        println("MainActivity onCreate")
        intent.action
        if (NfcAdapter.ACTION_NDEF_DISCOVERED == intent.action) {
            val messages = intent.getParcelableArrayExtra(NfcAdapter.EXTRA_NDEF_MESSAGES)
            println(messages)
//            for (message in messages) {
//                val record =
//            }

        }
    }
}
