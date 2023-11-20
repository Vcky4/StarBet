package com.starbet.ui.screens.home

import android.content.ContentValues
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.database.ChildEventListener
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.Exclude
import com.google.firebase.database.IgnoreExtraProperties
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.database.ktx.getValue
import com.google.firebase.ktx.Firebase
import com.starbet.data.StaticData
import com.starbet.data.db.models.TipModel
import com.starbet.datastore.Settings
import com.starbet.datastore.SettingsConstants
import kotlinx.coroutines.launch
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class HomeViewModel : ViewModel(), KoinComponent {
    private val settings: Settings by inject()
    private val database: DatabaseReference = Firebase.database.reference
    private val _whatsapp = MutableLiveData("")
    private val _telegram = MutableLiveData("")
    private val _email = MutableLiveData("")
    private val _isFirstTime = MutableLiveData(false)
    private val _tips = MutableLiveData<List<TipModel>>()
    val tips: LiveData<List<TipModel>> = _tips
    val whatsApp = _whatsapp
    val telegram = _telegram
    val email = _email
    val freeItems = StaticData.freeItems
    val vipItems = StaticData.vipItems
    val liveItems = StaticData.liveItems
    val contactItems = StaticData.contactItems
    val isFirstTime = _isFirstTime


    //get all tips
    fun getTips(tag: String) {
        val childEventListener = object : ChildEventListener {
            override fun onChildAdded(snapshot: DataSnapshot, previousChildName: String?) {
                val tipListener = object : ValueEventListener {
                    private val tipList = mutableListOf<TipModel>()
                    override fun onDataChange(dataSnapshot: DataSnapshot) {
                        for (dataValues in dataSnapshot.children) {
                            val tip = dataValues.getValue(TipModel::class.java)
                            if (tip != null) {
                                tipList.add(tip)
                            }
                        }
                        _tips.value = tipList
                        Log.d(ContentValues.TAG, "list value is: $tipList")
                    }

                    override fun onCancelled(databaseError: DatabaseError) {
                        // handle error
//                        Toast.makeText(context, "unable to update nuggets", Toast.LENGTH_SHORT).show()

                    }
                }
                database.child("tips").child(tag).ref.addListenerForSingleValueEvent(tipListener)
            }

            override fun onChildChanged(snapshot: DataSnapshot, previousChildName: String?) {}

            override fun onChildRemoved(snapshot: DataSnapshot) {}

            override fun onChildMoved(snapshot: DataSnapshot, previousChildName: String?) {}

            override fun onCancelled(error: DatabaseError) {
//                binding.loadingPost.visibility = GONE
//                Toast.makeText(context, "unable to update nuggets", Toast.LENGTH_SHORT).show()

            }

        }
        database.child("tips").child(tag).ref.addChildEventListener(childEventListener)
    }

    //set first time to false
    fun setFirstTime() {
        viewModelScope.launch {
            settings.putPreference(SettingsConstants.IS_FIRST_RUN, false)
        }
    }

    //get data on init
    init {
        viewModelScope.launch {
            settings.getPreference(SettingsConstants.IS_FIRST_RUN, false).collect {
                if (it) {
                    _isFirstTime.value = true
                }
            }
        }
        val contactListener = object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                val value = dataSnapshot.getValue<ContactModel>()
                Log.d(ContentValues.TAG, "Value is: $value")
                _whatsapp.value = value?.whatsapp ?: ""
                _telegram.value = value?.telegram ?: ""
                _email.value = value?.email ?: ""
            }

            override fun onCancelled(databaseError: DatabaseError) {
                Log.w(ContentValues.TAG, "loadPost:onCancelled", databaseError.toException())
            }
        }
        database.child("contacts").addValueEventListener(contactListener)
    }
}

//contact model
@IgnoreExtraProperties
data class ContactModel(
    var whatsapp: String?,
    var telegram: String?,
    var email: String?
) {
    constructor() : this("", "", "")

    @Exclude
    fun toMap(): Map<String, Any?> {
        return mapOf(
            "whatsApp" to whatsapp,
            "telegram" to telegram,
            "email" to email
        )
    }
}