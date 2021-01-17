package ru.avito.task.view

import android.content.Context
import android.content.res.Configuration
import android.util.AttributeSet
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import ru.avito.task.MainActivity
import ru.avito.task.adapter.ItemAdapter
import ru.avito.task.viewmodel.ItemViewModel

class ItemRecyclerView(ctx: Context, attr: AttributeSet) : RecyclerView(ctx, attr) {

    private val activity = ctx as MainActivity
    private val viewModel: ItemViewModel =
        ViewModelProvider(activity)[ItemViewModel::class.java]

    private val adapterItem = ItemAdapter().apply {
        setOnItemClickListener {
            viewModel.removeItem(it)
        }
    }

    init {
        checkOrientation()
        adapter = adapterItem
        viewModel.observe(activity, {
            adapterItem.submitList(it.toMutableList())
        })
    }


    private fun checkOrientation() {
        layoutManager =
            if (resources.configuration.orientation == Configuration.ORIENTATION_PORTRAIT)
                GridLayoutManager(activity, 2) else {
                GridLayoutManager(activity, 4)
            }
    }
}