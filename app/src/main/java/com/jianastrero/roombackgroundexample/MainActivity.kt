package com.jianastrero.roombackgroundexample

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.jianastrero.roombackgroundexample.adapters.MessagesAdapter
import com.jianastrero.roombackgroundexample.databinding.ActivityMainBinding
import com.jianastrero.roombackgroundexample.viewmodels.MainViewModel
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private lateinit var mBinding: ActivityMainBinding
    private lateinit var mViewModel: MainViewModel
    private lateinit var mAdapter: MessagesAdapter
    private var hasScrolledSteep = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        mViewModel = ViewModelProviders.of(this).get(MainViewModel::class.java)
        mAdapter = MessagesAdapter(emptyList())

        mBinding.viewModel = mViewModel
        mBinding.rvMessages.apply {
            adapter = mAdapter
            layoutManager = LinearLayoutManager(this@MainActivity)
        }

        mViewModel.messages.observe(this, Observer { messages ->
            mAdapter.messages = messages
            mAdapter.notifyDataSetChanged()
            if (!hasScrolledSteep)
                rvMessages.scrollToPosition(mAdapter.messages.size - 1)
            else
                rvMessages.smoothScrollToPosition(mAdapter.messages.size - 1)
            hasScrolledSteep = true
        })
    }
}
