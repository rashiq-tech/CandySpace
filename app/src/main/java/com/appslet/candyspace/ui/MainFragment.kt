package com.appslet.candyspace.ui

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import android.text.InputType
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.appslet.candyspace.databinding.MainFragmentBinding
import com.appslet.candyspace.utils.MainRepository
import com.appslet.candyspace.utils.RetrofitService
import com.appslet.candyspace.utils.ViewModelFactory
import com.appslet.candyspace.viewmodel.MainViewModel

class MainFragment : Fragment() {

    companion object {
        fun newInstance() = MainFragment()
    }

    private lateinit var viewModel: MainViewModel
    private var _binding: MainFragmentBinding? = null
    private val binding get() = _binding!!

    lateinit var retrofitService: RetrofitService
    lateinit var adapter: HomeRvAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        retrofitService = RetrofitService.getInstance()
        viewModel = ViewModelProvider(
            this,
            ViewModelFactory(MainRepository(retrofitService))
        )[MainViewModel::class.java]

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = MainFragmentBinding.inflate(inflater, container, false)
        adapter = context?.let { HomeRvAdapter(it) }!!

        binding.rvHome.adapter = adapter
        binding.rvHome.addItemDecoration(
            DividerItemDecoration(
                context,
                LinearLayoutManager.VERTICAL
            )
        )

        viewModel.usersList.observe(viewLifecycleOwner, Observer {
            adapter.setHomePostList(it)
        })
        viewModel.errorMessage.observe(viewLifecycleOwner, Observer {
            Toast.makeText(context, it, Toast.LENGTH_SHORT).show()
        })

        //inputType made to TYPE_NULL inorder to hide keypad on clear focus
        binding.etSearch.inputType = InputType.TYPE_NULL;
        binding.btSearch.setOnClickListener {
            viewModel.getUsersList(binding.etSearch.text.toString())
            binding.etSearch.clearFocus()
        }

        return binding.root
    }

    override fun onResume() {
        super.onResume()
        viewModel.getUsersList(binding.etSearch.text.toString())
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}