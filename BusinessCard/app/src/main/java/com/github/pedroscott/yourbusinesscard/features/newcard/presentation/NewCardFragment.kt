package com.github.pedroscott.yourbusinesscard.features.newcard.presentation

import android.Manifest
import android.app.Activity.RESULT_OK
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.provider.MediaStore
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import androidx.activity.result.ActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContextCompat
import androidx.core.net.toUri
import codes.side.andcolorpicker.converter.setFromColorInt
import codes.side.andcolorpicker.converter.toColorInt
import codes.side.andcolorpicker.group.PickerGroup
import codes.side.andcolorpicker.model.IntegerHSLColor
import com.github.pedroscott.yourbusinesscard.R
import com.github.pedroscott.yourbusinesscard.databinding.FragmentNewCardBinding
import com.github.pedroscott.yourbusinesscard.extension.*
import com.github.pedroscott.yourbusinesscard.features.main.presentation.MainActivity
import com.github.pedroscott.yourbusinesscard.model.Card
import com.github.pedroscott.yourbusinesscard.model.CardViewParams
import com.github.pedroscott.yourbusinesscard.util.Mask
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.google.android.material.snackbar.Snackbar
import org.koin.androidx.viewmodel.ext.android.viewModel

class NewCardFragment : BottomSheetDialogFragment() {

    private var binding: FragmentNewCardBinding? = null
    private val viewModel: NewCardViewModel by viewModel()

    private lateinit var pickerGroupBackground:  PickerGroup<IntegerHSLColor>
    private lateinit var pickerGroupTextColor:  PickerGroup<IntegerHSLColor>

    private var isNewCard: Boolean = true
    private lateinit var dataCard: Card
    private var uriLogo: String = ""

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentNewCardBinding.inflate(inflater, container, false)

        binding?.run {
            pickerGroupBackground = PickerGroup<IntegerHSLColor>().setupPickerGroup(colorPickerBackground, lightnessPickerBackground)
            pickerGroupTextColor = PickerGroup<IntegerHSLColor>().setupPickerGroup(lightnessPickerTextColor)
        }

        if (!isNewCard) {
            setupFields()
        }

        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupClicks()
        setupColorPickers()
        setupValidationConditions()
    }

    override fun onDestroyView() {
        super.onDestroyView()

        binding = null
    }

    private fun setupFields() {
        binding?.run {
            dataCard.run {
                tietNewCardName.text = name.toEditable()
                tietNewCardCompany.text = companyName.toEditable()
                tietNewCardPhone.text = phone.toEditable()
                tietNewCardEmail.text = email.toEditable()
                pickerGroupBackground.setColor(
                    IntegerHSLColor().also {
                        it.setFromColorInt(colorBackground)
                    }
                )
                pickerGroupTextColor.setColor(
                    IntegerHSLColor().also {
                        it.setFromColorInt(colorText)
                    }
                )
                if (logo == "") {
                    ivNewCardLogo.setImageResource(R.drawable.no_image)
                } else {
                    ivNewCardLogo.setImageURI(logo.toUri())
                }

                btNewCardSave.isEnabled = true
            }
        }
    }

    private fun setupClicks() {
        binding?.run {
            btNewCardSave.setOnClickListener {
                binding?.let { bindingNonNull ->
                    onClickButtonSave(bindingNonNull)
                }
            }

            fabNewCardAddLogo.setOnClickListener {
                context?.let { contextNonNull ->
                    resquestPermissionAndPickImage(contextNonNull)
                }
            }
        }
    }

    private fun onClickButtonSave(bindingNonNull: FragmentNewCardBinding) {
        with(bindingNonNull) {
            if (isNewCard) {
                viewModel.createCard(
                    CardViewParams(
                        name = tietNewCardName.text.toString(),
                        companyName = tietNewCardCompany.text.toString(),
                        phone = tietNewCardPhone.text.toString(),
                        email = tietNewCardEmail.text.toString(),
                        colorBackground = colorPickerBackground.pickedColor.toColorInt(),
                        colorText = lightnessPickerTextColor.pickedColor.toColorInt(),
                        logo = uriLogo
                    )
                )
            } else {
                dataCard.let { cardEdit ->
                    with(cardEdit) {
                        name = tietNewCardName.text.toString()
                        companyName = tietNewCardCompany.text.toString()
                        phone = tietNewCardPhone.text.toString()
                        email = tietNewCardEmail.text.toString()
                        colorBackground = colorPickerBackground.pickedColor.toColorInt()
                        colorText = lightnessPickerTextColor.pickedColor.toColorInt()
                        logo = uriLogo
                    }

                    viewModel.updateCard(cardEdit)
                }
            }

            context?.let { startActivity(Intent(it, MainActivity::class.java)) }
            activity?.finish()
        }
    }

    private fun setupColorPickers() {
        binding?.run {
            pickerGroupBackground.setupListener(
                onColorPicking = { _, color, _, _ ->
                    colorExampleBackground.visibility = View.VISIBLE
                    colorExampleBackground.setBackgroundColor(color.toColorInt())
                },
                onColorPicked = { _, _, _, _ ->
                    colorExampleBackground.visibility = View.GONE
                    if (lightnessPickerBackground.progress == 0) {
                        lightnessPickerBackground.progress = 1
                    } else if (lightnessPickerBackground.progress == 100) {
                        lightnessPickerBackground.progress = 99
                    }
                }
            )

            if (isNewCard) lightnessPickerTextColor.progress = 0
            lightnessPickerTextColor.setupListener(
                onColorPicking = { _, color, _, _ ->
                    colorExampleTextColor.visibility = View.VISIBLE
                    colorExampleTextColor.setBackgroundColor(color.toColorInt())
                },
                onColorPicked = { _, _, _, _ ->
                    colorExampleTextColor.visibility = View.GONE
                }
            )
        }
    }

    private val permissionLauncher =
        registerForActivityResult(ActivityResultContracts.RequestPermission()) { isGranted: Boolean ->
            if (isGranted) {
                pickImageFromGallery()
            } else {
                Snackbar.make(
                    View(context),
                    "Não foi possível acessar a galeria!",
                    Snackbar.LENGTH_SHORT
                ).show()
            }
        }

    private fun resquestPermissionAndPickImage(context: Context) =
        ContextCompat.checkSelfPermission(
            context,
            Manifest.permission.READ_EXTERNAL_STORAGE
        ).let { checkPermission ->
            when (checkPermission) {
                PackageManager.PERMISSION_GRANTED -> {
                    pickImageFromGallery()
                }
                else -> {
                    permissionLauncher.launch(Manifest.permission.READ_EXTERNAL_STORAGE)
                }
            }
        }

    private val galleryLauncher =
        registerForActivityResult(
            ActivityResultContracts.StartActivityForResult()
        ) { result: ActivityResult ->
            if (result.resultCode == RESULT_OK){
                binding?.run {
                    result.data?.data?.let { uriNonNull ->
                        context?.contentResolver?.takePersistableUriPermission(
                            uriNonNull,
                            Intent.FLAG_GRANT_READ_URI_PERMISSION
                        )

                        uriLogo = uriNonNull.toString()

                        ivNewCardLogo.setImageURI(uriNonNull)
                    }
                }
            }
        }

    private fun pickImageFromGallery() =
        Intent(
            Intent.ACTION_OPEN_DOCUMENT,
            MediaStore.Images.Media.INTERNAL_CONTENT_URI
        ).let { intent ->
            galleryLauncher.launch(intent)
        }

    private fun setupValidationConditions() {
        binding?.run {
            tietNewCardPhone.addTextChangedListener(Mask.mask(Mask.PHONE, tietNewCardPhone))

            val validationConditions = mapOf<EditText, () -> Boolean>(
                Pair(tietNewCardName) { tietNewCardName.text.toString().isNotEmpty() },
                Pair(tietNewCardCompany) { tietNewCardCompany.text.toString().isNotEmpty() },
                Pair(tietNewCardPhone) { tietNewCardPhone.text.toString().length == 16 },
                Pair(tietNewCardEmail) { tietNewCardEmail.text.toString().isNotEmpty() }
            )

            tilNewCardName.errorListener(getString(R.string.new_card_error_name), validationConditions[tietNewCardName])
            tilNewCardCompany.errorListener(getString(R.string.new_card_error_name), validationConditions[tietNewCardCompany])
            tilNewCardPhone.errorListener(getString(R.string.new_card_error_name), validationConditions[tietNewCardPhone])
            tilNewCardEmail.errorListener(getString(R.string.new_card_error_name), validationConditions[tietNewCardEmail])

            btNewCardSave.enableByFieldsValidation(validationConditions)
        }
    }

    fun setupWithThisCard(card: Card) {
        isNewCard = false
        dataCard = card
        uriLogo = card.logo
    }

}