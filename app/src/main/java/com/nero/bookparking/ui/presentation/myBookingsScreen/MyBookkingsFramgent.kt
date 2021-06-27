package com.nero.bookparking.ui.presentation.myBookingsScreen

import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.nero.bookparking.helper.KEY_USER_GOOGLE_ID
import com.nero.bookparking.helper.PreferenceHelper
import com.nero.bookparking.repository.listenerAndDatabaseModel.ListenerAndDatabaseReference
import com.nero.bookparking.ui.theme.TicketShapeTopRightBottomRight
import dagger.hilt.android.AndroidEntryPoint
import java.time.Instant
import java.time.LocalDateTime
import java.time.ZoneId
import kotlin.math.abs

@AndroidEntryPoint
class MyBookkingsFramgent : Fragment() {

    val viewModel by viewModels<MyBookingViewModel>()
    lateinit var databaseReference: ListenerAndDatabaseReference
    lateinit var u_id: String

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        activity?.let { PreferenceHelper.getSharedPreferences(it) }

        u_id = PreferenceHelper.getStringFromPreference(KEY_USER_GOOGLE_ID) ?: "u5"
        // Inflate the layout for this fragment
        return ComposeView(requireContext()).apply {


            setContent {


                val listOfBooking = viewModel.allBooksings.value

                LazyColumn {

                    item {
                        Spacer(modifier = Modifier.size(25.dp))
                    }

                    items(listOfBooking.size) { index ->
                        val data = listOfBooking[index]

                        val fromTime: LocalDateTime = Instant.ofEpochMilli(data.time ?: 556456)
                            .atZone(ZoneId.systemDefault()).toLocalDateTime()

                        val toTime: LocalDateTime = Instant.ofEpochMilli(data.toTime ?: 556456)
                            .atZone(ZoneId.systemDefault()).toLocalDateTime()
                        val time =
                            "${fromTime.hour}:" + "${fromTime.minute}" + " - " + "${toTime.hour}:" + "${toTime.minute}"

                        var showCheckOut by remember {
                            mutableStateOf(false)
                        }
                        Box(
                            modifier = Modifier.fillMaxWidth(),
                            contentAlignment = Alignment.Center
                        ) {
                            Ticket(
                                time = time,
                                spot = data.boxId ?: "nan",
                                timeLeft = "${abs(fromTime.hour - toTime.hour)}:${abs(fromTime.minute - toTime.minute)}",
                                day = "${fromTime.dayOfMonth}",
                                month = "${fromTime.month}",
                                year = "${fromTime.year}",
                                showCheckOut = showCheckOut,
                                onClick = {
                                    showCheckOut = !showCheckOut
                                },
                                index = index,
                                checkOutOnClick = {
                                    if (id != null) {
                                        viewModel.checkOut(it, u_id)
                                    }
                                }, isCheckOut = data.checkedOut ?: true
                            )
                        }
                        Spacer(modifier = Modifier.size(15.dp))
                    }
                }
            }
        }
    }

    override fun onResume() {
        super.onResume()
        databaseReference = viewModel.getAllBooking(u_id)
    }

    override fun onPause() {
        super.onPause()
        databaseReference.database.removeEventListener(databaseReference.listener)
    }

}


@Composable
fun Ticket(
    time: String,
    spot: String,
    timeLeft: String,
    day: String,
    month: String,
    year: String,
    showCheckOut: Boolean,
    onClick: (Int) -> Unit,
    index: Int,
    isCheckOut: Boolean,
    checkOutOnClick: (Int) -> Unit
) {

    Column {


        Column(modifier = Modifier.shadow(5.dp)) {
            Row(modifier = Modifier.clickable { onClick(index) }) {
                Box(
                    modifier = Modifier
                        .height(157.dp)
                        .width(273.dp)
                        .background(if (isCheckOut) Color(0xFFB4B4B4) else Color(0xFF306FFE))
                        .clip(
                            shape = RoundedCornerShape(topStart = 12.dp, bottomStart = 12.dp)
                        )
                )
                {
                    Row {
                        Column(
                            modifier = Modifier
                                .fillMaxHeight()
                                .width(272.dp),

                            ) {

                            Column(
                                modifier = Modifier
                                    .fillMaxSize()
                                    .padding(20.dp), verticalArrangement = Arrangement.SpaceBetween
                            ) {
                                Text(text = "Mall 1", fontSize = 12.sp, color = Color.White)
                                HeadingAndContent("Time", time)
                                Row() {
                                    HeadingAndContent(heading = "Spot", content = spot)
                                    Spacer(modifier = Modifier.size(25.dp))
                                    HeadingAndContent(heading = "Time left", content = timeLeft)
                                }

                            }

                        }
                        Column {
                            (0..27).forEach() {

                                Box(
                                    modifier = Modifier
                                        .width(2.dp)
                                        .height(3.dp)
                                        .background(Color(0xFF306FFE))
                                )
                                Box(
                                    modifier = Modifier
                                        .width(2.dp)
                                        .height(3.dp)
                                        .background(Color.White)
                                )

                            }
                        }
                    }
                }
                Box(
                    modifier = Modifier
                        .height(157.dp)
                        .width(90.dp)
                        .background(Color.White)
                        .clip(shape = TicketShapeTopRightBottomRight(12f)),
                    contentAlignment = Alignment.Center
                )
                {
                    DateSHow(
                        day = day,
                        month = month,
                        year = year
                    )
                }
            }
        }
        if (showCheckOut) {
            Row(
                modifier = Modifier
                    .width(353.dp)
                    .padding(top = 10.dp)
            ) {
                Box(modifier = Modifier.fillMaxWidth(), contentAlignment = Alignment.CenterEnd) {
                    Card(
                        modifier = Modifier
                            .width(100.dp)
                            .height(36.dp)
                            .clickable {
                                checkOutOnClick(index)
                            }
                    ) {
                        Box(
                            modifier = Modifier
                                .fillMaxSize()
                                .background(Color.Red)
                                .clip(RoundedCornerShape(12.dp)),
                            contentAlignment = Alignment.Center,

                            ) {
                            Text(text = "Check Out", color = Color.White)
                        }
                    }
                }

            }
        }

    }
}

@Composable
fun HeadingAndContent(heading: String, content: String) {
    Column {
        Text(text = heading, fontSize = 12.sp, color = Color.White, modifier = Modifier.alpha(0.7f))
        Spacer(modifier = Modifier.height(5.dp))
        Text(text = content, fontSize = 18.sp, color = Color.White)
    }
}

@Composable
fun DateSHow(
    day: String,
    month: String,
    year: String
) {

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(text = month, fontSize = 24.sp, color = Color(0xFF114548))
        //Spacer(modifier = Modifier.size(10.dp))
        Text(
            text = day,
            fontSize = 30.sp,
            color = Color(0xFF2D6FFA),
            fontWeight = FontWeight.Bold
        )
        //Spacer(modifier = Modifier.size(10.dp))
        Text(text = year, fontSize = 18.sp, color = Color(0xFF114548))
    }

}


//@Composable
//@Preview
//fun Test() {
//    Ticket(
//        time = "sfd",
//        spot = "sfd",
//        timeLeft = "sfd",
//        day = "sfd",
//        month = "sfd",
//        year = "sfd",
//        showCheckOut = false,
//
//        {},
//        1,
//        {}, {}
//    )
//
//}