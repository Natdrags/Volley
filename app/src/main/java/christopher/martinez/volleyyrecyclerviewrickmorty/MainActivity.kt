package christopher.martinez.volleyyrecyclerviewrickmorty

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import christopher.martinez.volleyyrecyclerviewrickmorty.Adaptador.PersonajeAdapter
import christopher.martinez.volleyyrecyclerviewrickmorty.Modelo.Personaje
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley

class MainActivity : AppCompatActivity() {
    lateinit var miRecycler: RecyclerView
    lateinit var lista:ArrayList<Personaje>
    lateinit var adaptador:PersonajeAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        lista= ArrayList<Personaje>()
        miRecycler= findViewById(R.id.RecyclerPersonajes)
        adaptador = PersonajeAdapter(lista)
        miRecycler.adapter= adaptador
        getPersonajes()
        miRecycler.layoutManager= LinearLayoutManager(this, LinearLayoutManager.VERTICAL,false)

    }

    fun getPersonajes(){
        val queue = Volley.newRequestQueue(this)
        val url = "https://rickandmortyapi.com/api/character"
        val objectRequest = JsonObjectRequest(
            Request.Method.GET,url,null,
            Response.Listener { respuesta ->
                val personajesJson = respuesta.getJSONArray("results")

                for (indice in 0..personajesJson.length()-1){
                    val personajeIndJson = personajesJson.getJSONObject(indice)
                    val personaje = Personaje(personajeIndJson.getString("name"),personajeIndJson.getString("image"))
                    lista.add(personaje)
                }

                adaptador.notifyDataSetChanged()
            },
            Response.ErrorListener {
                Log.e("PersonajesApi", "Error")
            })
        queue.add(objectRequest)
    }
}