package se.magictechnology.pia11weather

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import se.magictechnology.pia11weather.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        supportFragmentManager.beginTransaction().replace(R.id.mainFragCon, PlacedetailFragment()).commit()

        binding.goPlacelistButton.setOnClickListener {
            supportFragmentManager.beginTransaction().replace(R.id.mainFragCon, PlacelistFragment()).commit()
        }
        binding.goSettingsButton.setOnClickListener {
            supportFragmentManager.beginTransaction().replace(R.id.mainFragCon, SettingsFragment()).commit()
        }

        // TODO: Kolla om senaste stad finns
        supportFragmentManager.beginTransaction().replace(R.id.mainFragCon, PlacelistFragment()).commit()


    }
}




/*

F: Detaljvy för en plats/stad
F: Lista med platser
F: Inställningar
Kanske egen fin meny

Lagra på telefon





 */