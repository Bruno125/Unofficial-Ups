package com.brunoaybar.unofficialupc.modules.reserve.options

import android.os.Bundle
import android.support.v7.app.AlertDialog
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.Toolbar
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ProgressBar
import android.widget.Toast
import butterknife.BindView
import butterknife.ButterKnife
import butterknife.OnClick
import com.brunoaybar.unofficialupc.R
import com.brunoaybar.unofficialupc.modules.base.BaseFragment
import com.brunoaybar.unofficialupc.modules.reserve.DisplayableReserveOption
import com.brunoaybar.unofficialupc.modules.reserve.ReserveViewModel
import rx.android.schedulers.AndroidSchedulers

class ReserveOptionsFragment : BaseFragment(), ReserveOptionsAdapter.Callback{

    companion object {
        @JvmStatic val dataParam = "param_data"
        fun newInstance() = ReserveOptionsFragment()
    }

    private val viewModel = ReserveViewModel()
    private var optionsDataAsString = ""
    lateinit var adapter: ReserveOptionsAdapter

    @BindView(R.id.toolbar) lateinit var toolbar: Toolbar
    @BindView(R.id.rviReserveOptions) lateinit var recyclerView: RecyclerView
    @BindView(R.id.btnReserve) lateinit var btnReserve: Button
    @BindView(R.id.progressBar) lateinit var progressBar: ProgressBar

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater?.inflate(R.layout.fragment_reserve_options,container,false)
        if (view == null) {
            return super.onCreateView(inflater, container, savedInstanceState)
        }
        ButterKnife.bind(this,view)
        optionsDataAsString = getOptionsData(arguments)
        setupToolbar()
        setupRecyclerView()
        return view
    }

    fun setupRecyclerView(){
        recyclerView.layoutManager = LinearLayoutManager(context)
        adapter = ReserveOptionsAdapter(context)
        recyclerView.adapter = adapter
    }

    override fun bind() {
        super.bind()

        progressBar.visibility = View.VISIBLE
        mSubscription.add(viewModel.getReserveOptions()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ displayOptions(it) },{ displayError(it) }))

        mSubscription.add(viewModel.getReserveEnabledStream()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe( { btnReserve.isSelected = it }, { displayError(it)}))

        adapter.selectionListener = this
        viewModel.loadOptions(optionsDataAsString)
    }

    fun getOptionsData(bundle: Bundle): String{
        return bundle.getString(dataParam)
    }

    fun displayOptions(options: List<DisplayableReserveOption>){
        adapter.update(options)
        progressBar.visibility = View.GONE
    }

    override fun selected(position: Int) {
        viewModel.selectedReserveOption(adapter.options,position)
    }

    private var isReserving = false
    @OnClick(R.id.btnReserve)
    fun onReserveClicked(){
        if(isReserving)
            return

        when(btnReserve.isSelected){
            true -> {
                viewModel.searchSelected(adapter.options)
                        .map { it.name }
                        .subscribe( { askIfReallyWantsToReserve(it) }, { displayError(it)} )
            }
            false -> {
                displayMessage(R.string.text_reserve_pick_option)
                isReserving  = false
            }
        }
    }

    fun askIfReallyWantsToReserve(resourceName: String){
        val hint = String.format(getString(R.string.text_hint_reserve_confirmation),resourceName)

        AlertDialog.Builder(activity)
                .setTitle(activity.getString(R.string.title_reserve_confirmation))
                .setMessage(hint)
                .setPositiveButton(R.string.text_reserve_action) { _, _ -> reserve() }
                .setNegativeButton(R.string.text_cancel) { _, _ -> }
                .show()
    }

    fun reserve(){
        isReserving = true
        viewModel.requestedReserve(adapter.options)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ showMessageAndFinish(it) }, { displayError(it);  })
    }

    fun showMessageAndFinish(message: String){
        displayMessage(message, Toast.LENGTH_LONG)
        progressBar.visibility = View.GONE
        activity.finish()
    }

    override fun displayError(error: Throwable?) {
        super.displayError(error)
        progressBar.visibility = View.GONE
    }

    fun setupToolbar(){
        if(activity is AppCompatActivity){
            val appCompatActivity = activity as AppCompatActivity
            appCompatActivity.setSupportActionBar(toolbar)
            appCompatActivity.supportActionBar?.setDisplayHomeAsUpEnabled(true)
        }
    }

}