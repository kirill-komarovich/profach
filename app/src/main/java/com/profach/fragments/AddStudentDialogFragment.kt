package com.profach.fragments

import android.app.Dialog
import android.os.Bundle
import android.view.WindowManager.LayoutParams
import androidx.appcompat.app.AppCompatDialogFragment
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.android.material.textfield.TextInputLayout
import com.profach.R

class AddStudentDialogFragment(private val onPositive: (email: String, group: String) -> Unit) : AppCompatDialogFragment() {
    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val inflater = requireActivity().layoutInflater
        val view = inflater.inflate(R.layout.add_student_dialog_fragment, null)

        val dialog = MaterialAlertDialogBuilder(requireActivity())
            .setView(view)
            .setTitle(R.string.add_student_form_title)
            .setNeutralButton(resources.getString(R.string.cancel)) { _, _ -> }
            .setPositiveButton(resources.getString(R.string.add)) { _, _ ->
                val emailInput: TextInputLayout = view.findViewById(R.id.add_student_email_input)
                val groupInput: TextInputLayout = view.findViewById(R.id.add_student_group_input)
                val email = emailInput.editText!!.text.toString()
                val group = groupInput.editText!!.text.toString()
                onPositive(email, group)
            }
            .create()

        dialog.window?.setSoftInputMode(LayoutParams.SOFT_INPUT_STATE_VISIBLE)

        return dialog
    }
}
