package fr.thibaud.sparcoon.fragments

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import android.os.AsyncTask
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.core.content.ContextCompat.startActivity
import androidx.fragment.app.Fragment
import fr.thibaud.sparcoon.MainActivity
import fr.thibaud.sparcoon.R
import java.io.*
import java.net.HttpURLConnection
import java.net.MalformedURLException
import java.net.URL
import java.net.URLEncoder


//override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
//        val view = inflater.inflate(R.layout.fragment_connexion, container, false)

class ConnexionFragment (
    private val context: MainActivity
) : Fragment() {

    var _txtEmail: EditText? = null
    var _txtPassword:EditText? = null
    var _btnConnexion: Button? = null


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        super.onCreate(savedInstanceState)
        val view = inflater.inflate(R.layout.fragment_connexion, container, false)
        _txtEmail = view.findViewById(R.id.txtEmail) as EditText
        _txtPassword = view.findViewById(R.id.txtPassword) as EditText
        _btnConnexion = view.findViewById(R.id.btnConnexion) as Button
        _btnConnexion?.setOnClickListener {
            val email: String = _txtEmail!!.text.toString()
            val passwd: String = _txtPassword!!.text.toString()
            val background = bg(context)
            background.execute(email, passwd)
            Toast.makeText(context, "Connection en cours ...", Toast.LENGTH_SHORT).show()
        }
        return view
    }

    private class bg(context: MainActivity):
        AsyncTask<String, Void, String>() {
        var dialog: AlertDialog? = null
        @SuppressLint("StaticFieldLeak")
        var c: Context = context

        @Deprecated("Deprecated in Java")
        override fun onPreExecute() {
            dialog = AlertDialog.Builder(c).create()
            with(dialog) { this?.setTitle("Etat de connexion") }
        }

        @Deprecated("Deprecated in Java")
        override fun doInBackground(vararg strings: String): String {
            var result = ""
            val email = strings[0]
            val passwd = strings[1]
            //pour savoir votre adresse ip: lancer la commande "ipconfig" avec le programme cmd
            val connstr = "http://127.0.0.1/Sparcoon/login.php"
            try {
                val url = URL(connstr)
                val http: HttpURLConnection = url.openConnection() as HttpURLConnection
                http.requestMethod = "POST"
                http.doInput = true
                http.doOutput = true
                val ops: OutputStream = http.outputStream
                val writer = BufferedWriter(OutputStreamWriter(ops, "UTF-8"))
                val data: String =
                    URLEncoder.encode("email", "UTF-8").toString() + "=" + URLEncoder.encode(email, "UTF-8") +
                            "&&" + URLEncoder.encode("passwd", "UTF-8") + "=" + URLEncoder.encode(passwd, "UTF-8")
                writer.write(data)
                writer.flush()
                writer.close()
                val ips: InputStream = http.inputStream
                val reader = BufferedReader(InputStreamReader(ips, "ISO-8859-1"))
                var ligne = ""
                while ((reader.readLine().also { ligne = it }) != null) {
                    result = result + ligne
                    // ou bien result += ligne;
                }
                reader.close()
                ips.close()
                http.disconnect()
                return result
            } catch (e: MalformedURLException) {
                e.printStackTrace()
            } catch (e: IOException) {
                e.printStackTrace()
                Log.e("error", e.message.toString())
            }
            return result
        }

        fun onPostExecute(context: MainActivity, s: String, savedInstanceState: Bundle?) {
            dialog!!.setMessage(s)
            try {
                dialog!!.show()
            } catch (e: Exception) {
                Log.e("errorpost", e.message.toString())
            }
            if (s.contains("succes")) {
                val i = Intent()
                i.setClass(c.applicationContext, HomeFragment::class.java)
                startActivity(context,i,savedInstanceState)
            }
        }
    }
}