package fernanda.esteves.projetoac2

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View

class FullDream : AppCompatActivity() {
    var id : Int? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_full_dream)

        id = intent.extras?.getInt("id")
    }

    fun goBack (view: View){
        this.finish();
    }

}