package fernanda.esteves.projetoac2

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import fernanda.esteves.projetoac2.adapters.DreamAdapter
import fernanda.esteves.projetoac2.models.Dream
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private val temp = arrayListOf<Dream>(
        Dream(1, "Teste", "dali", "oi"),
        Dream(2, "Teste", "dali", "oi")
    )

    private var listaRecycler: RecyclerView? = null
    private var swipe: SwipeRefreshLayout? = null

    val listaSonhosDados = MutableLiveData<List<Dream>>()
    val loadError = MutableLiveData<Boolean>()
    val loading = MutableLiveData<Boolean>()

    // adapter do recycleview
    private val listaAdapter = DreamAdapter(arrayListOf()) { dream: Dream -> onItemClick(dream) }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        listaRecycler = rv_listaSonhos as RecyclerView
        listaRecycler!!.apply {
            layoutManager = LinearLayoutManager(this@MainActivity)
            adapter = listaAdapter
        }

        refresh()

        // aplica função de refresh ao componente swipe refresh
        swipe = this.findViewById(R.id.swipeRefreshLayout) as SwipeRefreshLayout
        swipe!!.setOnRefreshListener {
            swipeRefreshLayout.isRefreshing = false
            refresh()
        }
    }

    fun goToAdd(view: View) {
        val adicionar = Intent(this@MainActivity, AddNewDream::class.java)
        startActivity(adicionar)
    }

    private fun onItemClick(dream: Dream) {
        val intent = Intent(this, FullDream::class.java)
        intent.putExtra("id", dream.id)
        startActivity(intent)
    }


    private fun refresh() {
        consumirApi()

        listaSonhosDados.observe(this, Observer { linhas ->
            linhas?.let {
                rv_listaSonhos?.visibility = View.VISIBLE
                listaAdapter.update(it)
            }
        })

        loadError.observe(this, Observer { isError ->
            isError?.let { list_error.visibility = if (it) View.VISIBLE else View.GONE }

        })

        loading.observe(this, Observer { isLoading ->
            isLoading?.let {
                loading_view.visibility = if (it) View.VISIBLE else View.GONE
                if (it) {
                    list_error.visibility = View.GONE
                    rv_listaSonhos?.visibility = View.GONE
                }
            }
        })
    }


    private fun consumirApi() {
        loading.value = true;

        //val service: Api = HttpHelper().getApiClient()!!.create(Api::class.java)
//        val lista: Call<List<Dream>> = service.getDreams()
//
//        lista.enqueue(object : Callback<List<Dream>> {
//            override fun onFailure(call: Call<List<Dream>>, t: Throwable) {
//                loadError.value = true;
//                loading.value = false;
//
//                println("deu ruim = ${t.message}")
//            }
//
//            override fun onResponse(call: Call<List<Dream>>, response: Response<List<Dream>>) {
//                listaSonhosDados.value = response.body()?.toList()
                  listaSonhosDados.value = temp;
//                loadError.value = false;
//                loading.value = false;
//
//                println("status code = ${response.code()}")
//            }
//        })
        loading.value = false;
    }
}