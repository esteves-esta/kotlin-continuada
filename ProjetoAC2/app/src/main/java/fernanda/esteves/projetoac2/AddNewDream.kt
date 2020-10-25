package fernanda.esteves.projetoac2

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_add_new_dream.*

class AddNewDream : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_new_dream)

        cardPesadelo.setOnClickListener {
            cardPesadelo.isChecked = true
            cardNormal.isChecked = false
            cardDali.isChecked = false
        }

        cardNormal.setOnClickListener {
            cardNormal.isChecked = true
            cardPesadelo.isChecked = false
            cardDali.isChecked = false
        }

        cardDali.setOnClickListener {
            cardDali.isChecked = true
            cardPesadelo.isChecked = false
            cardNormal.isChecked = false
        }
    }

    fun goBack (view: View){
        this.finish();
    }

    fun addDreamToHiveMind (view: View){
        Toast.makeText(this, "Checkado ${cardDali.isChecked}", Toast.LENGTH_SHORT).show()
        val adicionar = Intent(this@AddNewDream, MainActivity::class.java)
        startActivity(adicionar)
    }

}