package com.example.examenfinal3

import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GravityCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.fragment.app.Fragment
import androidx.preference.PreferenceManager
import com.example.examenfinal3.databinding.ActivityMainBinding
import com.google.android.material.navigation.NavigationView

class MainActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {

    private lateinit var binding: ActivityMainBinding
    private lateinit var drawerLayout: DrawerLayout
    private lateinit var toggle: ActionBarDrawerToggle


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Configuración del NavigationView

        drawerLayout = findViewById<DrawerLayout>(R.id.main)

        val navigationView = findViewById<NavigationView>(R.id.nav_view)
        navigationView.setNavigationItemSelectedListener(this)


        val toolBar: androidx.appcompat.widget.Toolbar =
            findViewById<androidx.appcompat.widget.Toolbar>(R.id.appbar)
        setSupportActionBar(toolBar)

        // Configurar el botón de hamburguesa para abrir/cerrar el menú lateral
        toggle = ActionBarDrawerToggle(this, drawerLayout, toolBar, R.string.open_drawer, R.string.close_drawer)
        drawerLayout.addDrawerListener(toggle)
        toggle.syncState()

        //Inicializamos con el HomeFragment
        supportFragmentManager.beginTransaction()
            .replace(R.id.fragment_container, HomeFragment())
            .commit()
        binding.bottomNavigation.setOnNavigationItemSelectedListener {
            it.isChecked = true
            when (it.itemId) {
                R.id.navigation_home -> {
// Acción al seleccionar Home
                    replaceFragment(HomeFragment())
                }
                R.id.navigation_dashboard -> {
// Acción al seleccionar Dashboard
                    replaceFragment(DashboardFragment())
                }
                R.id.navigation_notifications -> {
// Acción al seleccionar Notifications
                    replaceFragment(NotificationsFragment())
                }
            }
            false
        }

        binding.btnPreferencias.setOnClickListener {
            val intent = Intent(this, SettingsActivity::class.java)
            startActivity(intent)
        }

        binding.btnMostrarPreferencias.setOnClickListener {
            val pref: SharedPreferences =
                PreferenceManager.getDefaultSharedPreferences(this@MainActivity)
            Log.i("Tema8App2", "Reproducir música: "
                    + pref.getBoolean("reproducirMusica", false));
            Log.i("Tema8App2", "Nick: " + pref.getString("nick", ""));
            Log.i("Tema8App2", "Pais: " + pref.getString("pais", ""));
        }





        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when(item.itemId){
            R.id.action_inicio ->{
                showToast("Opción settings seleccionada")
                true
            }
            R.id.action_api ->{
                showToast("Opción search seleccionada")
                true
            }
            R.id.action_room ->{
                showToast("Opción add seleccionada")
                true
            }R.id.action_room2 ->{
                showToast("Opción add seleccionada")
                true
            }else -> super.onOptionsItemSelected(item)
        }
    }
    private fun showToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }

    private fun replaceFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction()
            .replace(R.id.fragment_container, fragment)
            .commit()
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.nav_api -> supportFragmentManager.beginTransaction()
                .replace(R.id.fragment_container, HomeFragment()).commit()
            R.id.nav_room -> supportFragmentManager.beginTransaction()
                .replace(R.id.fragment_container, DashboardFragment()).commit()
            R.id.nav_room2 -> supportFragmentManager.beginTransaction()
                .replace(R.id.fragment_container, HomeFragment()).commit()
            R.id.nav_about -> supportFragmentManager.beginTransaction()
                .replace(R.id.fragment_container, DashboardFragment()).commit()
            R.id.nav_logout -> Toast.makeText(this, "Logout!", Toast.LENGTH_SHORT).show()
        }
        drawerLayout.closeDrawer(GravityCompat.START)
        return true
    }
}