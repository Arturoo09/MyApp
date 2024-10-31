package edu.ucam.myapp.ViewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import edu.ucam.myapp.Model.CategoryModel


class MainViewModel : ViewModel() {
    private val firebaseDatabase = FirebaseDatabase.getInstance()

    private val _categoryList = MutableLiveData<MutableList<CategoryModel>>()

    val category: LiveData<MutableList<CategoryModel>> = _categoryList

    fun loadCategory() {
        val ref = firebaseDatabase.getReference("Category")
        ref.addValueEventListener(object :ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                val lists = mutableListOf<CategoryModel>()

                for (snap in snapshot.children){
                    val category = snap.getValue(CategoryModel::class.java)
                    if (category != null){
                        lists.add(category)
                    }
                }

                _categoryList.value = lists
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }

        })
    }
}