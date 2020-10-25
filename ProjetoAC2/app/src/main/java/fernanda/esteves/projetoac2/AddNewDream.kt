package fernanda.esteves.projetoac2

import android.content.Intent
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.TypedValue
import android.view.View
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import fernanda.esteves.projetoac2.models.Dream
import fernanda.esteves.projetoac2.services.Api
import fernanda.esteves.projetoac2.services.Rotas
import kotlinx.android.synthetic.main.activity_add_new_dream.*
import kotlinx.android.synthetic.main.activity_add_new_dream.container_conteudo
import kotlinx.android.synthetic.main.activity_main.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

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

        val titulo = campoTitulo.text.toString().trim()
        val descricao = campoDescricao.text.toString().trim()
        val dali = cardDali.isChecked
        val normal = cardNormal.isChecked
        val pesadelo = cardPesadelo.isChecked

        var campoEmpty = "";
        if(titulo.isEmpty()){
            campoEmpty =  getString(R.string.label_title)
        } else if(descricao.isEmpty()){
            campoEmpty = getString(R.string.label_description)
        } else if (!dali && !normal && !pesadelo) {
            campoEmpty = getString(R.string.label_dreamtype)
        }

        if(!campoEmpty.trim().isBlank()){
            Toast.makeText(this, getString(R.string.empty_field).format(campoEmpty), Toast.LENGTH_LONG).show()
        } else {
            val tipo = getTipo(normal, dali, pesadelo)

            val dream: Dream = Dream(null, titulo, tipo, descricao)

            cadastar(dream);
        }
    }

    private fun getTipo(normal: Boolean, dali: Boolean, pesadelo: Boolean): String{
        return if(normal){
            getString(R.string.dream_type_1)
        } else if(dali){
            getString(R.string.dream_type_3)
        } else {
            getString(R.string.dream_type_2)
        }
    }


    private fun cadastar(dream: Dream) {
        val service: Rotas = Api().getApiClient()!!.create(Rotas::class.java)
        val lista: Call<Void> = service.setOne(dream)

        lista.enqueue(object : Callback<Void> {
            override fun onFailure(call: Call<Void>, t: Throwable) {
                val textoDinamico = TextView(baseContext)
                textoDinamico.text = getString(R.string.save_error)
                textoDinamico.setTextColor(Color.WHITE)
                textoDinamico.setTextSize(TypedValue.COMPLEX_UNIT_SP, 20f)
                container_conteudo.addView(textoDinamico)
            }

            override fun onResponse(call: Call<Void>, response: Response<Void>) {

                val adicionar = Intent(this@AddNewDream, MainActivity::class.java)
                startActivity(adicionar)

                val textoDinamico = TextView(baseContext)
                textoDinamico.text = getString(R.string.carregando)
                textoDinamico.setTextColor(Color.WHITE)
                textoDinamico.setTextSize(TypedValue.COMPLEX_UNIT_SP, 20f)
                container_conteudo.addView(textoDinamico)
            }
        })
    }



}