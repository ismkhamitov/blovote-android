package com.blovote.surveys.ui.passing

import android.Manifest
import android.arch.persistence.room.util.StringUtil
import android.content.pm.PackageManager
import android.os.Bundle
import android.os.Environment
import android.support.v4.app.ActivityCompat
import android.support.v4.app.Fragment
import android.support.v4.content.ContextCompat
import android.support.v7.app.AlertDialog
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import com.blovote.R
import com.blovote.app.App
import com.blovote.surveys.data.entities.Respond
import com.blovote.surveys.data.entities.Survey
import com.blovote.surveys.domain.SurveysInteractor
import com.blovote.surveys.ui.monitoring.AnswersActivity
import com.blovote.surveys.ui.toReadableString
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.functions.BiFunction
import io.reactivex.schedulers.Schedulers
import org.jetbrains.anko.sdk25.coroutines.onClick
import java.io.BufferedOutputStream
import java.io.BufferedWriter
import java.io.File
import java.io.OutputStreamWriter
import java.math.BigInteger
import java.text.SimpleDateFormat
import java.util.*
import javax.inject.Inject

class SurveyDetailsFragment : Fragment() {

    private lateinit var titleView : TextView
    private lateinit var rewardTextView : TextView
    private lateinit var progressBarQuestions : ProgressBar
    private lateinit var progressBarState : ProgressBar
    private lateinit var stateTextView : TextView
    private lateinit var questionsCountTextView: TextView
    private lateinit var progressBarCurrResp : ProgressBar
    private lateinit var textViewCurrResp : TextView
    private lateinit var buttonViewResults : Button
    private lateinit var buttonExportResults : Button
    private lateinit var buttonStart : Button

    private lateinit var address : String
    private var index : Int = -1
    private lateinit var survey: Survey
    private var monitorMode : Boolean = false


    @Inject
    lateinit var surveysInteractor : SurveysInteractor

    var disposable : CompositeDisposable = CompositeDisposable()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.layout_survey_details, container, false)
        App.appComponent.inject(this)
        setupUI(view)
        return view
    }

    override fun onStart() {
        super.onStart()
        setupUX()
    }

    override fun onStop() {
        super.onStop()
        disposable.dispose()
        disposable = CompositeDisposable()
    }

    private fun setupUI(view: View) {
        address = arguments!!.getString(KEY_ADDRESS)
        index = arguments!!.getInt(KEY_INDEX)
        monitorMode = arguments!!.getBoolean(KEY_MONITOR_MODE)

        titleView = view.findViewById(R.id.title)
        rewardTextView = view.findViewById(R.id.text_view_reward_unit)
        progressBarQuestions = view.findViewById(R.id.progress_bar_questions_count)
        progressBarState = view.findViewById(R.id.progress_bar_survey_state)
        stateTextView = view.findViewById(R.id.text_view_survey_state)
        questionsCountTextView = view.findViewById(R.id.text_view_questions_count)
        progressBarCurrResp = view.findViewById(R.id.progress_bar_curr_resp_count)
        textViewCurrResp = view.findViewById(R.id.text_view_curr_resp_count)
        buttonViewResults = view.findViewById(R.id.button_view_results)
        buttonExportResults = view.findViewById(R.id.button_export_results)
        buttonStart = view.findViewById(R.id.button_create)

        val delimiterCurrRespCount = view.findViewById<View>(R.id.delimiter_curr_resp_count)
        val layoutCurrRespCount = view.findViewById<LinearLayout>(R.id.layout_curr_resp_count)
        if (!monitorMode) {
            delimiterCurrRespCount.visibility = View.GONE
            layoutCurrRespCount.visibility = View.GONE
            buttonViewResults.visibility = View.GONE
            buttonExportResults.visibility = View.GONE
        } else {
            buttonStart.visibility = View.GONE
        }

    }

    private fun setupUX() {
        disposable.add(surveysInteractor.getSurvey(address)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe { loadedSurvey, _ ->
                    survey = loadedSurvey

                    titleView.text = survey.title
                    rewardTextView.text = BigInteger(survey.rewardSize).toReadableString()

                    if (survey.fresh) {
                        onSurveyLoaded(survey)
                    } else {
                        surveysInteractor.updateSurveyInfo(survey)
                                .subscribeOn(Schedulers.computation())
                                .observeOn(AndroidSchedulers.mainThread())
                                .subscribe({ newSurvey ->
                                    onSurveyLoaded(newSurvey)
                                })
                    }
                })
    }

    private fun onSurveyLoaded(survey: Survey?) {
        if (survey == null || context == null) {
            return
        }

        progressBarState.visibility = View.GONE
        progressBarQuestions.visibility = View.GONE
        progressBarCurrResp.visibility = View.GONE

        stateTextView.text = getString(survey.state.nameResId)
        stateTextView.visibility = View.VISIBLE

        questionsCountTextView.text = (survey.filterQuestionsCount + survey.questionsCount).toString()
        questionsCountTextView.visibility = View.VISIBLE

        textViewCurrResp.text = survey.currentRespCount.toString()
        textViewCurrResp.visibility = View.VISIBLE

        if (survey.currentRespCount > 0) {
            buttonViewResults.isEnabled = true
            buttonExportResults.isEnabled = true

            buttonViewResults.onClick {
                val context = this@SurveyDetailsFragment.context
                if (context != null) {
                    startActivity(AnswersActivity.getStartIntent(context, address))
                }
            }

            buttonExportResults.onClick {
                prepareExportSurveys()
            }
        }
        buttonStart.isEnabled = true
        buttonStart.onClick {
            (activity as? QuestionPassListener)?.onPassNext(survey)
        }
    }


    private fun prepareExportSurveys() {
        val context = context ?: return
        if (ContextCompat.checkSelfPermission(context, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {
            val activity = this.activity
            if (activity != null) {
                ActivityCompat.requestPermissions(activity,
                        arrayOf(Manifest.permission.WRITE_EXTERNAL_STORAGE), PERMISSIONS_CODE)
            }
        } else {
            exportSurveyAnswers()
        }
    }

    private fun exportSurveyAnswers() {
        val context = context ?: return
        if (Environment.getExternalStorageState() != Environment.MEDIA_MOUNTED) {
            AlertDialog.Builder(context)
                    .setMessage("External storage is unavailable")
                    .show()
            return
        }

        val docsDir = getExportDir()
        if (!docsDir.exists() && !docsDir.mkdirs()) {
            AlertDialog.Builder(context)
                    .setMessage("Unable to create directory for exporting")
                    .show()
            return
        }

        val dialog = AlertDialog.Builder(context)
                .setView(R.layout.layout_progress_dialog)
                .show()
        dialog.findViewById<TextView>(R.id.loading_msg)?.text = getString(R.string.loading_responds)

        disposable.add(surveysInteractor.loadAllRespondsInfo(address)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    surveysInteractor.getAllResponds(this, address)
                            .observeOn(Schedulers.io())
                            .subscribe( {
                                dialog.dismiss()
                                exportLoadedRespondsToFile()
                            }, {
                                dialog.dismiss()
                                AlertDialog.Builder(context)
                                        .setMessage(it.message)
                                        .show()
                            })


                }, {
                    dialog.dismiss()
                    AlertDialog.Builder(context)
                            .setMessage("Unable to load responds")
                            .show()
                }))
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        when (requestCode) {
            PERMISSIONS_CODE -> {
                if (!grantResults.isEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    exportSurveyAnswers()
                }
            }
        }
    }

    private fun exportLoadedRespondsToFile() {
        disposable.add(Observable.combineLatest(surveysInteractor.getSurvey(address).toObservable(),
                surveysInteractor.getAllResponds(this, address),
                BiFunction<Survey, List<Respond>, Pair<Survey, List<Respond>>> { t1, t2 -> Pair(t1, t2) })
                .take(1)
                .subscribe { pair ->

                    val timeStamp = SimpleDateFormat("yyyyMMdd_HHmmss", Locale.getDefault()).format(Date())
                    val filename = "blovote_$timeStamp.csv"
                    val file = File(getExportDir(), filename)
                    file.createNewFile()

                    val writer = BufferedWriter(OutputStreamWriter(file.outputStream()))
                    writer.use {
                        writeSurveyRow(pair.first, it)
                        writeResponds(pair.second, it)
                    }

                    Toast.makeText(this.context, "Surveys exported to: ${file.canonicalPath}",
                            Toast.LENGTH_LONG).show()
                })

    }

    private fun getExportDir() : File {
        return File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOCUMENTS), "blovotes")
    }

    private fun writeSurveyRow(survey: Survey, writer: BufferedWriter) {
        writer.write("Respondent;")
        for (question in survey.questions) {
            writer.write(question.title + ";")
        }
        writer.newLine()
    }

    private fun writeResponds(responds: List<Respond>, writer: BufferedWriter) {
        for (i in 0 until responds.size) {
            val num = i + 1
            val respond = responds[i]
            writer.write(num.toString() + ";")
            respond.data.forEach { answers ->
                writer.write( answers.data.joinToString(",") + ";")
            }
            writer.newLine()
        }
    }

    companion object {

        private val PERMISSIONS_CODE = 42

        private val KEY_ADDRESS = "address"
        private val KEY_INDEX = "index"
        private val KEY_MONITOR_MODE = "monitor"

        fun newInstance(surveyAddress: String, index: Int, monitorState: Boolean) : Fragment {
            val fragment = SurveyDetailsFragment()

            val bundle = Bundle()
            bundle.putString(KEY_ADDRESS, surveyAddress)
            bundle.putInt(KEY_INDEX, index)
            bundle.putBoolean(KEY_MONITOR_MODE, monitorState)

            fragment.arguments = bundle
            return fragment
        }

    }

}