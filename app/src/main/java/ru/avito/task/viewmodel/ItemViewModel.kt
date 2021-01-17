package ru.avito.task.viewmodel

import android.os.Handler
import android.os.Looper
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import ru.avito.task.model.Item
import kotlin.random.Random

class ItemViewModel : ViewModel() {

    private val listItem = MutableLiveData<MutableList<Item>>()
    private val poolItem = mutableListOf<Item>()

    init {
        listItem.value = mutableListOf<Item>().apply {
            for (i in 1..15) add(Item(i))
        }

        ItemGenerator().apply {
            setItemListener {
                addItem()
            }
        }.start()
    }


    fun observe(owner: LifecycleOwner, observer: (MutableList<Item>) -> Unit) {
        listItem.observe(owner, observer)
    }

    fun removeItem(position: Int) {
        val itemList = listItem.value!!
        poolItem.add(itemList[position])
        itemList.removeAt(position)
        listItem.value = itemList
    }


    private fun addItem() {
        val itemList = listItem.value!!
        val randomPos: Int = if (itemList.isEmpty()) 0 else Random.nextInt(0, listItem.value!!.size)
        if (poolItem.isEmpty()) itemList.add(randomPos, Item(itemList.size + 1))
        else {
            itemList.add(randomPos, poolItem[0])
            poolItem.removeAt(0)
        }
        listItem.value = itemList
    }


    class ItemGenerator : Thread("ItemCreator") {

        private val handlerUi: Handler = Handler(Looper.getMainLooper())
        private lateinit var callback: () -> Unit

        override fun run() {
            while (true) {
                sleep(5000)
                handlerUi.post(callback)
            }
        }

        fun setItemListener(itemListener: () -> Unit) {
            callback = itemListener
        }

    }
}