package com.example.cryptoapp.presentation

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.cryptoapp.R
import com.example.cryptoapp.databinding.ActivityCoinPriceListBinding
import com.example.cryptoapp.presentation.adapters.CoinInfoAdapter

class CoinPriceListActivity : AppCompatActivity() {


    private val binding by lazy {
        ActivityCoinPriceListBinding.inflate(layoutInflater)
    }

    private lateinit var viewModel: CoinViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        val adapter = CoinInfoAdapter(this)
        binding.rvCoinPriceList.adapter = adapter
        binding.rvCoinPriceList.itemAnimator = null
        viewModel = ViewModelProvider(this)[CoinViewModel::class.java]
        viewModel.coinInfoListUseCase.observe(this, Observer {
            adapter.submitList(it)
        })
        adapter.onCoinClickListener = {
            if (isOnePaneMode()) {
                val intent = CoinDetailActivity.newIntent(
                    this@CoinPriceListActivity,
                    it.fromSymbol
                )
                startActivity(intent)
            } else {
                val fragment = CoinDetailFragment
                    .newInstanceCoinDetail(it.fromSymbol)
                startFragment(fragment)
            }

        }
    }

    private fun isOnePaneMode(): Boolean = binding.containerDetailCoinHorizontal == null

    private fun startFragment(fragment: Fragment) {
        supportFragmentManager.popBackStack()
        supportFragmentManager.beginTransaction()
            .addToBackStack(null)
            .replace(R.id.containerDetailCoinHorizontal, fragment).commit()
    }

}
