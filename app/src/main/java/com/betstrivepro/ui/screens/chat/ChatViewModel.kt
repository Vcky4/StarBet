package com.betstrivepro.ui.screens.chat

import android.app.Application
import android.content.ContentValues
import android.net.Uri
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.android.volley.RequestQueue
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.google.android.gms.tasks.Task
import com.google.firebase.database.ChildEventListener
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.StorageTask
import com.google.firebase.storage.UploadTask
import com.google.firebase.storage.ktx.storage
import com.betstrivepro.data.db.models.ChatModel
import com.betstrivepro.data.db.models.ConversationModel
import com.betstrivepro.datastore.Settings
import com.betstrivepro.datastore.SettingsConstants
import kotlinx.coroutines.launch
import org.json.JSONException
import org.json.JSONObject
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class ChatViewModel(private val context: Application) : ViewModel(), KoinComponent {
    private val database: DatabaseReference = Firebase.database.reference
    private val _token = MutableLiveData<String>()
    // Create a storage reference from our app
    var storageRef = Firebase.storage.reference
    private val settings: Settings by inject()
    private val requestQueue: RequestQueue by lazy {
        Volley.newRequestQueue(context)
    }
    private val fcmApi = "https://fcm.googleapis.com/fcm/send"
    private val serverKey =
        "key=" + "AAAABN5sNKs:APA91bEV7NARYe_lqKyshzK4JtwaHvvSVJ9us0AjapbK7L_WvyqUxBDTv46srY1Ra4dFi2cm6KCnjuZJtBgTvSiaonlU5mflGW1OwmmU78biMGOw5qENky2O045BzQ_QKNxk849tNUlR"
    private val contentType = "application/json"
    private val _chatId = MutableLiveData("")
    private val _userName = MutableLiveData("")
    private val _chats = MutableLiveData<List<ChatModel>>()
    private val _conversations = MutableLiveData<List<ConversationModel>>()

    val userName = _userName
    val chatId = _chatId
    val chats = _chats
    val conversations = _conversations
    val token: LiveData<String> = _token

    val getPassword = database.child("password").get()
    fun login(token: String) {
        _token.value = token
    }

    private fun sendNotification(title: String, body: String, to: String) {
        Log.e("TAG", "sendNotification")
        val topic = "/topics/$to" //topic has to match what the receiver subscribed to

        val notification = JSONObject()
        val notifcationBody = JSONObject()

        try {
            notifcationBody.put("title", title)
            notifcationBody.put("message", body)   //Enter your notification message
            notification.put("to", topic)
            notification.put("data", notifcationBody)
        } catch (e: JSONException) {
            Log.e("TAG", "notification: " + e.message)
        }
        val jsonObjectRequest = object : JsonObjectRequest(fcmApi, notification,
            Response.Listener { response ->
                Log.i("TAG", "onResponse: $response")
            },
            Response.ErrorListener {
                Toast.makeText(context, "Request error", Toast.LENGTH_LONG).show()
                Log.i("TAG", "onErrorResponse: Didn't work $it")
            }) {

            override fun getHeaders(): Map<String, String> {
                val params = HashMap<String, String>()
                params["Authorization"] = serverKey
                params["Content-Type"] = contentType
                return params
            }
        }
        requestQueue.add(jsonObjectRequest)
    }

    fun deleteChat(parent: String, key: String): Task<Void> {
        return database.child("chats").child(parent).child(key).removeValue()
    }

    fun deleteConversation(key: String): Task<Void> {
        database.child("chats").child(key).removeValue()
        return database.child("conversations").child(key).removeValue()
    }

    fun setUserName(name: String) {
        viewModelScope.launch {
            settings.putPreference(SettingsConstants.USER_NAME, name)
            _userName.value = name
        }
    }

    fun sendChat(
        message: String = "",
        name: String = "",
        time: Long = System.currentTimeMillis(),
        isAdmin: Boolean,
        parent: String = ""
    ): Task<Void> {
        val key = database.child("chats").push().key
        val pKey = parent.ifEmpty { database.child("conversations").push().key }
        val conversation = ConversationModel(
            pKey ?: "",
            message,
            name,
            time,
        )
        return database.child("conversations").child(pKey ?: "").setValue(conversation)
            .addOnSuccessListener {
                if (!isAdmin) {
                    viewModelScope.launch {
                        settings.putPreference(SettingsConstants.CHAT_ID, pKey ?: "")
                        _chatId.value = pKey ?: ""
                    }
                }
                val chat = ChatModel(
                    key ?: "",
                    message,
                    name,
                    time,
                    isAdmin,
                    pKey ?: ""
                )
                database.child("chats").child(pKey ?: "").child(key ?: "").setValue(chat)
                    .addOnSuccessListener {
                        sendNotification(
                            "New message $name",
                            message,
                            if (isAdmin) pKey ?: "" else "adminChat"
                        )
                    }
            }
    }

    fun uploadImage(
        message: String = "",
        name: String = "",
        time: Long = System.currentTimeMillis(),
        isAdmin: Boolean,
        parent: String = "",
        uri: String
    ): StorageTask<UploadTask.TaskSnapshot> {
        val key = database.child("chats").push().key
        val pKey = parent.ifEmpty { database.child("conversations").push().key }
        val conversation = ConversationModel(
            pKey ?: "",
            message,
            name,
            time,
        )
        val chats = _chats.value ?: listOf()
        _chats.value = chats + ChatModel(
            key ?: "",
            message,
            name,
            time,
            isAdmin,
            parent,
            uri
        )
        val imageRef = storageRef.child("images/${key}.jpg")
        return imageRef.putFile(Uri.parse(uri))
            .addOnSuccessListener {
                //get download url
                imageRef.downloadUrl.addOnSuccessListener { downloadUrl ->
                    Log.d(ContentValues.TAG, "download url is: $downloadUrl")
                    if (it != null) {
                        database.child("conversations").child(pKey ?: "").setValue(conversation)
                            .addOnSuccessListener {
                                if (!isAdmin) {
                                    viewModelScope.launch {
                                        settings.putPreference(
                                            SettingsConstants.CHAT_ID,
                                            pKey ?: ""
                                        )
                                        _chatId.value = pKey ?: ""
                                    }
                                }
                                val chat = ChatModel(
                                    key ?: "",
                                    message,
                                    name,
                                    time,
                                    isAdmin,
                                    pKey ?: "",
                                    downloadUrl.toString()
                                )
                                database.child("chats").child(pKey ?: "").child(key ?: "")
                                    .setValue(chat)
                                    .addOnSuccessListener {
                                        sendNotification(
                                            "New message $name",
                                            message,
                                            if (isAdmin) pKey ?: "" else "adminChat"
                                        )
                                    }
                            }
                    }
                }
            }
    }

    fun getChats(parent: String = "") {
        val childEventListener = object : ChildEventListener {
            override fun onChildAdded(snapshot: DataSnapshot, previousChildName: String?) {
                val tipListener = object : ValueEventListener {
                    private val chatList = mutableListOf<ChatModel>()
                    override fun onDataChange(dataSnapshot: DataSnapshot) {
                        for (dataValues in dataSnapshot.children) {
                            val chat = dataValues.getValue(ChatModel::class.java)
                            if (chat != null) {
                                chatList.add(chat)
                            }
                        }
                        _chats.value = chatList
                        Log.d(ContentValues.TAG, "list value is: $chatList")
                    }

                    override fun onCancelled(databaseError: DatabaseError) {
                        // handle error
//                        Toast.makeText(context, "unable to update nuggets", Toast.LENGTH_SHORT).show()

                    }
                }
                database.child("chats")
                    .child(parent).ref.addListenerForSingleValueEvent(tipListener)
            }

            override fun onChildChanged(snapshot: DataSnapshot, previousChildName: String?) {}

            override fun onChildRemoved(snapshot: DataSnapshot) {}

            override fun onChildMoved(snapshot: DataSnapshot, previousChildName: String?) {}

            override fun onCancelled(error: DatabaseError) {
//                binding.loadingPost.visibility = GONE
//                Toast.makeText(context, "unable to update nuggets", Toast.LENGTH_SHORT).show()

            }

        }
        database.child("chats").child(parent).ref.addChildEventListener(childEventListener)
    }

    fun getConversations() {
        val childEventListener = object : ChildEventListener {
            override fun onChildAdded(snapshot: DataSnapshot, previousChildName: String?) {
                val tipListener = object : ValueEventListener {
                    private val conversationList = mutableListOf<ConversationModel>()
                    override fun onDataChange(dataSnapshot: DataSnapshot) {
                        for (dataValues in dataSnapshot.children) {
                            Log.d(ContentValues.TAG, "dataValues is: $dataValues")
                            val conversation = dataValues.getValue(ConversationModel::class.java)
                            if (conversation != null) {
                                conversationList.add(conversation)
                            }
                        }
                        _conversations.value = conversationList
                        Log.d(ContentValues.TAG, "list value is: $conversationList")
                    }

                    override fun onCancelled(databaseError: DatabaseError) {
                        // handle error
//                        Toast.makeText(context, "unable to update nuggets", Toast.LENGTH_SHORT).show()

                    }
                }
                database.child("conversations")
                    .ref.addListenerForSingleValueEvent(tipListener)
            }

            override fun onChildChanged(snapshot: DataSnapshot, previousChildName: String?) {}

            override fun onChildRemoved(snapshot: DataSnapshot) {}

            override fun onChildMoved(snapshot: DataSnapshot, previousChildName: String?) {}

            override fun onCancelled(error: DatabaseError) {
//                binding.loadingPost.visibility = GONE
//                Toast.makeText(context, "unable to update nuggets", Toast.LENGTH_SHORT).show()

            }

        }
        database.child("conversations").ref.addChildEventListener(childEventListener)
    }

    init {
        viewModelScope.launch {
            settings.getPreference(SettingsConstants.USER_NAME, "").collect {
                if (it.isNotEmpty()) {
                    _userName.value = it
                }
            }
        }
        viewModelScope.launch {
            settings.getPreference(SettingsConstants.CHAT_ID, "").collect {
                if (it.isNotEmpty()) {
                    _chatId.value = it
                }
            }
        }
    }

}