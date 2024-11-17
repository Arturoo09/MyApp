package edu.ucam.myapp.ViewModel

import android.content.ClipData.Item
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import edu.ucam.myapp.Model.ItemsModel


class MainViewModel : ViewModel() {
    private val firebaseDatabase = FirebaseDatabase.getInstance()

    private val _popularList = MutableLiveData<MutableList<ItemsModel>>()
    private val _offerList = MutableLiveData<MutableList<ItemsModel>>()

    val popular: LiveData<MutableList<ItemsModel>> = _popularList
    val offer: LiveData<MutableList<ItemsModel>> = _offerList

    fun loadPopular() {
        val ref = firebaseDatabase.getReference("Items")
        ref.addValueEventListener(object :ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                val lists = mutableListOf<ItemsModel>()

                for (childSnap in snapshot.children){
                    val item = childSnap.getValue(ItemsModel::class.java)
                    if (item != null){
                        lists.add(item)
                    }
                }

                _popularList.value = lists
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }
        })
    }

    fun loadOffer() {
        val ref = firebaseDatabase.getReference("Offers")
        ref.addValueEventListener(object :ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                val lists = mutableListOf<ItemsModel>()

                for (childSnap in snapshot.children){
                    val item = childSnap.getValue(ItemsModel::class.java)
                    if (item != null){
                        lists.add(item)
                    }
                }

                _offerList.value = lists
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }
        })
    }
}