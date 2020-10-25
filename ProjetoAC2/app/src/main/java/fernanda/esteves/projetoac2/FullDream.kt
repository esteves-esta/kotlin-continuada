package fernanda.esteves.projetoac2

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import fernanda.esteves.projetoac2.models.Dream
import fernanda.esteves.projetoac2.services.Api
import fernanda.esteves.projetoac2.services.Rotas
import kotlinx.android.synthetic.main.activity_full_dream.*
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.activity_main.container_conteudo
import kotlinx.android.synthetic.main.activity_main.list_error
import kotlinx.android.synthetic.main.activity_main.loading_view
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class FullDream : AppCompatActivity() {

    var id : Int? = null
    val loadError = MutableLiveData<Boolean>()
    val loading = MutableLiveData<Boolean>()
    private var swipe: SwipeRefreshLayout? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_full_dream)

        id = intent.extras?.getInt("id")
        if(id !== null) consumirApi(id!!)

        swipe = this.findViewById(R.id.swipeDream) as SwipeRefreshLayout
        swipe!!.setOnRefreshListener {
            swipeDream.isRefreshing = false
            if(id !== null) consumirApi(id!!)
        }
    }

    fun goBack (view: View){
        this.finish();
    }

    private fun consumirApi(id: Int) {
        loading.value = true;

        val service: Rotas = Api().getApiClient()!!.create(Rotas::class.java)
        val lista: Call<Dream> = service.getOne(id)

        lista.enqueue(object : Callback<Dream> {
            override fun onFailure(call: Call<Dream>, t: Throwable) {
                loadError.value = true;
                loading.value = false;
            }

            override fun onResponse(call: Call<Dream>, response: Response<Dream>) {
                val dream = response.body();
                full_dream_title.text = dream?.titulo
                full_dream_type.text = dream?.tipo
                full_dream_description.text = dream?.descricao

                loadError.value = false;
                loading.value = false;
            }
        })

        refresh();
    }

    private fun refresh() {

        loadError.observe(this, Observer { isError ->
            isError?.let { list_error.visibility = if (it) View.VISIBLE else View.GONE }

        })

        loading.observe(this, Observer { isLoading ->
            isLoading?.let {
                loading_view.visibility = if (it) View.VISIBLE else View.GONE
                if (it) {
                    list_error.visibility = View.GONE
                    container_conteudo?.visibility = View.GONE
                }else {
                    container_conteudo?.visibility = View.VISIBLE
                }
            }
        })
    }

}