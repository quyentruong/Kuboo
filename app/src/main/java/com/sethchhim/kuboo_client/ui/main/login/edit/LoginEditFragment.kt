package com.sethchhim.kuboo_client.ui.main.login.edit

import android.os.Bundle
import android.support.design.widget.TextInputEditText
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import butterknife.BindView
import butterknife.ButterKnife
import com.sethchhim.kuboo_client.Constants
import com.sethchhim.kuboo_client.Constants.ARG_LOGIN
import com.sethchhim.kuboo_client.Extensions.gone
import com.sethchhim.kuboo_client.Extensions.visible
import com.sethchhim.kuboo_client.R
import com.sethchhim.kuboo_client.data.ViewModel
import com.sethchhim.kuboo_client.ui.main.MainActivity
import com.sethchhim.kuboo_remote.model.Login
import dagger.android.support.DaggerFragment
import org.jetbrains.anko.sdk25.coroutines.onClick
import timber.log.Timber
import javax.inject.Inject

open class LoginEditFragment : DaggerFragment() {

    @Inject lateinit var mainActivity: MainActivity
    @Inject lateinit var viewModel: ViewModel

    @BindView(R.id.login_layout_edit_textInputEditText1) lateinit var editTextNickName: TextInputEditText
    @BindView(R.id.login_layout_edit_textInputEditText2) lateinit var editTextServerAddress: TextInputEditText
    @BindView(R.id.login_layout_edit_textInputEditText3) lateinit var editTextUsername: TextInputEditText
    @BindView(R.id.login_layout_edit_textInputEditText4) lateinit var editTextPassword: TextInputEditText
    @BindView(R.id.login_layout_edit_button2) lateinit var addServerButton: Button
    @BindView(R.id.login_layout_edit_button1) lateinit var deleteServerButton: Button

    private var login: Login? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.login_layout_edit, container, false)
        ButterKnife.bind(this, view)
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        addServerButton.onClick { onAddServerButtonClicked() }
        deleteServerButton.onClick { onDeleteServerButtonClicked() }
    }

    override fun onStart() {
        super.onStart()
        arguments?.apply {
            if (containsKey(Constants.ARG_LOGIN)) {
                login = getParcelable(Constants.ARG_LOGIN) as Login?
                when (login == null) {
                    true -> onLoginNull()
                    false -> onLoginValid()
                }
            }
        }
    }

    private fun onLoginValid() {
        Timber.i("onLoginValid")
        deleteServerButton.visible()
        login?.apply {
            editTextNickName.setText(nickname, TextView.BufferType.EDITABLE)
            editTextServerAddress.setText(server, TextView.BufferType.EDITABLE)
            editTextUsername.setText(username, TextView.BufferType.EDITABLE)
            editTextPassword.setText(password, TextView.BufferType.EDITABLE)
        }
    }

    private fun onLoginNull() {
        Timber.i("onLoginNull")
        deleteServerButton.gone()
        editTextNickName.setText("", TextView.BufferType.EDITABLE)
        editTextServerAddress.setText("", TextView.BufferType.EDITABLE)
        editTextUsername.setText("", TextView.BufferType.EDITABLE)
        editTextPassword.setText("", TextView.BufferType.EDITABLE)
    }

    private fun onAddServerButtonClicked() {
        login?.let { viewModel.removeLogin(it) }
        viewModel.addLogin(getLoginFromEditTexts())
        mainActivity.showFragmentLoginBrowser()
    }

    private fun onDeleteServerButtonClicked() {
        login?.let {
            viewModel.removeLogin(it)
            when (viewModel.isActiveLogin(it) || viewModel.isLoginListEmpty()) {
                true -> viewModel.setActiveLogin(null)
                false -> mainActivity.showFragmentLoginBrowser()
            }
        } ?: mainActivity.showToastError()
    }

    private fun getLoginFromEditTexts() = Login().apply {
        nickname = editTextNickName.text.toString()
        server = editTextServerAddress.text.toString()
        username = editTextUsername.text.toString()
        password = editTextPassword.text.toString()
    }

    companion object {
        fun newInstance(login: Login?) = LoginEditFragment().apply {
            arguments = Bundle().apply { putParcelable(ARG_LOGIN, login) }
        }
    }

}