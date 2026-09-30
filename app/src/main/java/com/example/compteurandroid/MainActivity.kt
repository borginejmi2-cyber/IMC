package com.example.compteurandroid

import android.graphics.Color
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Récupération des composants
        val editTextPoids = findViewById<EditText>(R.id.editTextPoids)
        val editTextTaille = findViewById<EditText>(R.id.editTextTaille)
        val buttonCalculer = findViewById<Button>(R.id.buttonCalculer)
        val buttonEffacer = findViewById<Button>(R.id.buttonEffacer)
        val textViewImc = findViewById<TextView>(R.id.textViewImc)
        val textViewCategorie = findViewById<TextView>(R.id.textViewCategorie)

        // Clic sur Calculer
        buttonCalculer.setOnClickListener {
            val texteP = editTextPoids.text.toString().trim()
            val texteT = editTextTaille.text.toString().trim()

            // 1. Vérifier que les champs sont renseignés
            if (texteP.isEmpty() || texteT.isEmpty()) {
                Toast.makeText(this, R.string.erreur_champ_vide, Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            // 2. Convertir en décimaux (accepte aussi la virgule)
            val poids = texteP.replace(',', '.').toDoubleOrNull()
            val taille = texteT.replace(',', '.').toDoubleOrNull()

            if (poids == null || taille == null) {
                Toast.makeText(this, R.string.erreur_format, Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            // 3. Vérifier que les valeurs sont strictement positives
            if (poids <= 0 || taille <= 0) {
                Toast.makeText(this, R.string.erreur_valeur_invalide, Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            // 4. Calcul et arrondi à 2 décimales
            val imc = Math.round(poids / (taille * taille) * 100) / 100.0

            // 5. Catégorie et couleur
            val categorie: Int
            val couleur: Int
            if (imc < 18.5) {
                categorie = R.string.cat_insuffisance
                couleur = Color.parseColor("#FF9800")   // orange
            } else if (imc < 25) {
                categorie = R.string.cat_normale
                couleur = Color.parseColor("#4CAF50")   // vert
            } else if (imc < 30) {
                categorie = R.string.cat_surpoids
                couleur = Color.parseColor("#FF9800")   // orange
            } else if (imc < 35) {
                categorie = R.string.cat_obesite_moderee
                couleur = Color.parseColor("#F44336")   // rouge
            } else if (imc < 40) {
                categorie = R.string.cat_obesite_severe
                couleur = Color.parseColor("#F44336")   // rouge
            } else {
                categorie = R.string.cat_obesite_morbide
                couleur = Color.parseColor("#B71C1C")   // rouge foncé
            }

            // 6. Affichage
            textViewImc.text = getString(R.string.imc_resultat, imc)
            textViewCategorie.setText(categorie)
            textViewImc.setTextColor(couleur)
            textViewCategorie.setTextColor(couleur)
        }

        // Clic sur Effacer
        buttonEffacer.setOnClickListener {
            editTextPoids.text.clear()
            editTextTaille.text.clear()
            textViewImc.text = ""
            textViewCategorie.text = ""
            editTextPoids.requestFocus()
        }
    }
}