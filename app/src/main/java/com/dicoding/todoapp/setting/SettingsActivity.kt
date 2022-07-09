package com.dicoding.todoapp.setting

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.preference.PreferenceFragmentCompat
import androidx.preference.SwitchPreference
import androidx.work.Data
import androidx.work.ExistingPeriodicWorkPolicy
import androidx.work.PeriodicWorkRequest
import androidx.work.WorkManager
import com.dicoding.todoapp.R
import com.dicoding.todoapp.notification.NotificationWorker
import com.dicoding.todoapp.utils.NOTIFICATION_CHANNEL_ID
import java.util.concurrent.TimeUnit

class SettingsActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.settings_activity)
        if (savedInstanceState == null) {
            supportFragmentManager
                .beginTransaction()
                .replace(R.id.settings, SettingsFragment())
                .commit()
        }
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }

    class SettingsFragment : PreferenceFragmentCompat() {
        private lateinit var workManager: WorkManager

        override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
            setPreferencesFromResource(R.xml.root_preferences, rootKey)
            workManager = WorkManager.getInstance(requireContext())

            val prefNotification = findPreference<SwitchPreference>(getString(R.string.pref_key_notify))
            prefNotification?.setOnPreferenceChangeListener { preference, newValue ->
                val channelName = getString(R.string.notify_channel_name)
                Log.d("SettingActivity", "new Value is $newValue and preference is $preference")
                if (newValue == true){
                    activateNotificationWorker(channelName)
                } else {
                    cancelWork(channelName)
                }
                true
            }
        }

        fun cancelWork(notificationName: String) {
            workManager.cancelUniqueWork(notificationName)
        }

        fun activateNotificationWorker(notificationName: String){
            val workerData = Data.Builder().putString(NOTIFICATION_CHANNEL_ID, notificationName).build()
            val notificationRequest = PeriodicWorkRequest.Builder(NotificationWorker::class.java, 1, TimeUnit.SECONDS)
                .setInputData(workerData)
                .setInitialDelay(1, TimeUnit.SECONDS)
                .build()

            workManager.enqueueUniquePeriodicWork(notificationName, ExistingPeriodicWorkPolicy.KEEP, notificationRequest)
        }


        private fun updateTheme(mode: Int): Boolean {
            AppCompatDelegate.setDefaultNightMode(mode)
            requireActivity().recreate()
            return true
        }
    }
}