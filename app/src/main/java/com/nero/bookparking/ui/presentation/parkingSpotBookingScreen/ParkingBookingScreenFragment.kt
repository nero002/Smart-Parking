package com.nero.bookparking.ui.presentation.parkingSpotBookingScreen

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Divider
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.consumeAllChanges
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.findNavController
import com.nero.bookparking.MainActivity
import com.nero.bookparking.R
import com.nero.bookparking.dto.parkingDTO.ParkingBoxDto
import com.nero.bookparking.dto.parkingDTO.ParkingPillarDto
import com.nero.bookparking.repository.listenerAndDatabaseModel.ListenerAndDatabaseReference
import com.nero.bookparking.ui.parcalable.ArgsParkingToPayment
import com.nero.bookparking.ui.theme.Ebony
import com.nero.bookparking.ui.theme.EbonyClay
import com.nero.bookparking.ui.theme.PaleSky
import com.nero.bookparking.ui.theme.SelectedRed
import dagger.hilt.android.AndroidEntryPoint
import kotlin.math.roundToInt

@AndroidEntryPoint
class ParkingBookingScreenFragment : Fragment() {

    private val viewModel by viewModels<BookingScreenViewModel>()
    private lateinit var databaseReference: ListenerAndDatabaseReference

    @ExperimentalFoundationApi
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        return ComposeView(requireContext()).apply {
            setContent {

                val listOfPillars =
                    viewModel.listOfData.value?.parkingFloorDto?.get(viewModel.currentFloor.value)?.parkingPillarDto
                val listOfFloors = viewModel.listOfData.value?.parkingFloorDto
                Box(modifier = Modifier.background(Ebony)) {

                    Column {

                        Row {

                            LazyColumn {

                                item {
                                    Spacer(modifier = Modifier.size(60.dp))
                                    Row(
                                        modifier = Modifier.fillMaxWidth(),
                                        horizontalArrangement = Arrangement.Center
                                    ) {
                                        Spacer(modifier = Modifier.size(50.dp))
                                        Image(
                                            painter = painterResource(id = R.drawable.dots),
                                            contentDescription = "back",
                                            Modifier.height(63.dp)
                                        )
                                        Spacer(modifier = Modifier.size(15.dp))
                                        Box(
                                            modifier = Modifier.height(63.dp),
                                            contentAlignment = Alignment.Center
                                        ) {

                                            Text(
                                                text = "Parking",
                                                fontSize = 22.sp,
                                                color = Color.White,
                                                fontWeight = FontWeight.Bold
                                            )

                                        }
                                    }

                                    Spacer(modifier = Modifier.size(10.dp))
                                }

                                item {

                                    LazyRow(

                                    ) {
                                        item {
                                            Spacer(modifier = Modifier.size(50.dp))
                                        }
                                        if (listOfFloors != null) {
                                            items(listOfFloors.size) { index ->
                                                SelectableChips(
                                                    isSelected = viewModel.currentFloor.value == index,
                                                    index = index,
                                                    name = listOfFloors[index].id,
                                                    onClick = {
                                                        viewModel.currentFloor.value = it
                                                    }
                                                )
                                            }
                                        }
                                    }
                                }

                                item {
                                    Column {
                                        Box(
                                            modifier = Modifier.width(157.dp),
                                            contentAlignment = Alignment.Center
                                        ) {
                                            Text(
                                                text = "ENTRY",
                                                fontSize = 15.sp,
                                                color = Color.White,
                                                modifier = Modifier.padding(20.dp)
                                            )
                                        }
                                    }
                                }

                                if (listOfPillars != null) {

                                    items(listOfPillars.size) { index ->
                                        Row {
                                            Box(
                                                modifier = Modifier
                                                    .width(157.dp)
                                                    .height(271.dp),
                                                contentAlignment = Alignment.TopCenter
                                            ) {
                                                Column() {
                                                    (0..4).forEach {
                                                        RoadBlock()
                                                    }
                                                }

                                            }
                                            Column {
                                                Column {
                                                    ParkingPillar(
                                                        pillarName = listOfPillars[index].id,
                                                        listOfParkingBoxes = listOfPillars[index].listOfParkingBoxes,
                                                        currentSelectedId = viewModel.currentSelectede.value,
                                                        currentUserUid = "u5",
                                                        onClick = { slectedId, pillarId ->
                                                            viewModel.currentSelectede.value =
                                                                slectedId
                                                            viewModel.currentSelectedPillar.value =
                                                                pillarId
                                                        }
                                                    )
                                                }
                                                Spacer(modifier = Modifier.size(50.dp))
                                            }
                                        }
                                    }
                                }

                                item {
                                    Column {
                                        Box(
                                            modifier = Modifier
                                                .width(157.dp)
                                                .height(70.dp),
                                            contentAlignment = Alignment.TopCenter
                                        ) {
                                            Row(verticalAlignment = Alignment.CenterVertically) {
                                                Text(
                                                    text = "EXIT",
                                                    fontSize = 15.sp,
                                                    color = Color.White
                                                )
                                                Spacer(modifier = Modifier.width(10.dp))
                                                Image(
                                                    painter = painterResource(id = R.drawable.ic_baseline_arrow_forward_24),
                                                    contentDescription = ""
                                                )
                                            }

                                        }
                                        Spacer(modifier = Modifier.height(10.dp))
                                    }

                                }


                            }
                        }
                    }
                    Box(
                        modifier = Modifier
                            .padding(bottom = 33.dp, end = 27.dp)
                            .fillMaxSize(),
                        contentAlignment = Alignment.BottomEnd
                    ) {
                        Box(
                            modifier = Modifier
                                .size(68.dp)
                                .clip(RoundedCornerShape(17.dp))
                                .background(Color.White)
                                .clickable {
                                    val action =
                                        ParkingBookingScreenFragmentDirections
                                            .actionParkingBookingScreenFragmentToSlotBookingFragment(
                                                ArgsParkingToPayment(
                                                    pillor = viewModel.currentSelectedPillar.value,
                                                    parkingBox = viewModel.currentSelectede.value,
                                                    building = "m_1",
                                                    floor = listOfFloors?.get(viewModel.currentFloor.value)?.id
                                                        ?: "ff55"
                                                )
                                            )
                                    findNavController().navigate(action)
                                },
                            contentAlignment = Alignment.Center
                        ) {
                            Image(
                                painter = painterResource(id = R.drawable.boxback),
                                contentDescription = ""
                            )
                        }
                    }

                }
            }
        }
    }


    override fun onResume() {
        super.onResume()
        databaseReference = viewModel.getParkingData("m_1")
        val activity = activity as MainActivity
        activity.hideToolBar()
    }

    override fun onPause() {
        super.onPause()
        databaseReference.database.removeEventListener(databaseReference.listener)
        val activity = activity as MainActivity
        activity.showToolBar()
    }

}

@Composable
fun RoadBlock() {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Box(
            modifier = Modifier
                .width(1.dp)
                .height(17.dp)
                .background(Color(0xFF525969))
        )
        Spacer(modifier = Modifier.height(34.dp))
    }
}


@Composable
fun SelectableChips(
    isSelected: Boolean,
    index: Int,
    name: String,
    onClick: (Int) -> Unit
) {
    Box(
        modifier = Modifier
            .size(43.dp)
            .clip(RoundedCornerShape(12.dp))
            .background(if (isSelected) SelectedRed else EbonyClay)
            .clickable {
                onClick(index)
            },
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = name,
            fontSize = 13.sp,
            color = if (isSelected) Color.White else Color(0xFF6B6F7D)
        )
    }
}


@ExperimentalFoundationApi
@Composable
fun GridOfPillars(
    listOfPillar: List<ParkingPillarDto>,
    currentSelectedId: String,
    currentUserUid: String,
    onClick: (String, String) -> Unit
) {
    var offsetX by remember { mutableStateOf(0f) }
    var offsetY by remember { mutableStateOf(0f) }

    Box(modifier = Modifier
        .offset { IntOffset(offsetX.roundToInt(), offsetY.roundToInt()) }
        .pointerInput(Unit) {
            detectDragGestures { change, dragAmount ->
                change.consumeAllChanges()
                offsetX += dragAmount.x
            }
        }) {
        Column {
            Row {
                CapablePiller(
                    listOfPillar = listOfPillar,
                    currentUserUid = currentUserUid,
                    currentSelectedId = currentSelectedId,
                    onClick = onClick,
                    index = 0
                )
                CapablePiller(
                    listOfPillar = listOfPillar,
                    currentUserUid = currentUserUid,
                    currentSelectedId = currentSelectedId,
                    onClick = onClick,
                    index = 1
                )
            }
            Row {
                CapablePiller(
                    listOfPillar = listOfPillar,
                    currentUserUid = currentUserUid,
                    currentSelectedId = currentSelectedId,
                    onClick = onClick,
                    index = 2
                )
                CapablePiller(
                    listOfPillar = listOfPillar,
                    currentUserUid = currentUserUid,
                    currentSelectedId = currentSelectedId,
                    onClick = onClick,
                    index = 3
                )
            }
        }
    }
}

@ExperimentalFoundationApi
@Composable
fun CapablePiller(
    listOfPillar: List<ParkingPillarDto>, index: Int,
    currentSelectedId: String,
    currentUserUid: String,
    onClick: (String, String) -> Unit
) {
    val data = listOfPillar[index]

    ParkingPillar(
        pillarName = data.id,
        data.listOfParkingBoxes,
        currentSelectedId = currentSelectedId,
        currentUserUid = currentUserUid,
        onClick = onClick
    )
}


@ExperimentalFoundationApi
@Composable
fun ParkingPillar(
    pillarName: String,
    listOfParkingBoxes: List<ParkingBoxDto>,
    currentSelectedId: String,
    currentUserUid: String,
    onClick: (String, String) -> Unit,

    ) {
    Column {
        Box(
            modifier = Modifier
                .size(43.dp)
                .clip(RoundedCornerShape(12.dp))
                .background(EbonyClay),
            contentAlignment = Alignment.Center
        ) {
            Text(text = pillarName, fontSize = 13.sp, color = Color.White)
        }
        Spacer(modifier = Modifier.size(13.dp))
        Column {
            Row() {
                LeftCapableBox(
                    listOfParkingBoxes = listOfParkingBoxes,
                    index = 0,
                    currentSelectedId = currentSelectedId,
                    currentUserUid = currentUserUid,
                    onClick = onClick,
                    pillarID = pillarName
                )
                RightCapableBox(
                    listOfParkingBoxes = listOfParkingBoxes,
                    index = 1,
                    currentSelectedId = currentSelectedId,
                    currentUserUid = currentUserUid,
                    onClick = onClick,

                    pillarID = pillarName
                )
            }
            Row() {
                LeftCapableBox(
                    listOfParkingBoxes = listOfParkingBoxes,
                    index = 2,
                    currentSelectedId = currentSelectedId,
                    currentUserUid = currentUserUid,
                    onClick = onClick,

                    pillarID = pillarName
                )
                RightCapableBox(
                    listOfParkingBoxes = listOfParkingBoxes,
                    index = 3,
                    currentSelectedId = currentSelectedId,
                    currentUserUid = currentUserUid,
                    onClick = onClick,
                    pillarID = pillarName
                )
            }
            Row() {
                LeftCapableBox(
                    listOfParkingBoxes = listOfParkingBoxes,
                    index = 4,
                    currentSelectedId = currentSelectedId,
                    currentUserUid = currentUserUid,
                    onClick = onClick,

                    pillarID = pillarName
                )
                RightCapableBox(
                    listOfParkingBoxes = listOfParkingBoxes,
                    index = 5,
                    currentSelectedId = currentSelectedId,
                    currentUserUid = currentUserUid,
                    onClick = onClick,
                    pillarID = pillarName
                )
            }
        }
    }


}


@Composable
fun LeftCapableBox(
    listOfParkingBoxes: List<ParkingBoxDto>,
    index: Int,
    currentSelectedId: String,
    currentUserUid: String,
    onClick: (String, String) -> Unit,
    pillarID: String,
) {

    val data = listOfParkingBoxes[index]
    val showCar = currentSelectedId == data.id || data.available == false

    ParkingBoxLeft(
        showCar = showCar,
        parkingBoxId = data.id ?: "nan",
        boxBackgroundColor = if (currentUserUid == data.user_id) Color.Green else if (currentSelectedId == data.id && data.available == true) SelectedRed else Ebony,
        onClick = onClick,
        pillarID = pillarID
    )


}

@Composable
fun RightCapableBox(
    listOfParkingBoxes: List<ParkingBoxDto>,
    index: Int,
    currentSelectedId: String,
    currentUserUid: String,
    onClick: (String, String) -> Unit,

    pillarID: String
) {

    val data = listOfParkingBoxes[index]
    val showCar = currentSelectedId == data.id || data.available == false

    ParkingBoxRight(
        showCar = showCar,
        parkingBoxId = data.id ?: "nan",
        boxBackgroundColor = if (currentUserUid == data.user_id) Color.Green else if (currentSelectedId == data.id && data.available == true) SelectedRed else Ebony,
        onClick = onClick,
        pillarID = pillarID
    )


}


@Composable
fun ParkingBoxRight(
    showCar: Boolean,
    parkingBoxId: String,
    boxBackgroundColor: androidx.compose.ui.graphics.Color,
    onClick: (String, String) -> Unit,
    pillarID: String
) {
    Row {
        Box(
            modifier = Modifier
                .background(PaleSky)
                .height(59.dp)
                .width(1.dp)
        )
        Column() {
            Divider(modifier = Modifier.width(90.dp), thickness = 0.5.dp, color = PaleSky)
            Box(
                modifier = Modifier
                    .height(59.dp)
                    .width(90.dp)
                    .clickable {
                        onClick(parkingBoxId, pillarID)
                    }
            ) {
                Row(modifier = Modifier.fillMaxSize()) {
                    Box(
                        modifier = Modifier
                            .height(59.dp)
                            .width(.05.dp)
                            .background(PaleSky)
                    )
                    Box(
                        modifier = Modifier
                            .fillMaxHeight()
                            .width(89.5.dp)
                            .background(color = boxBackgroundColor),
                        contentAlignment = if (showCar) Alignment.Center else Alignment.BottomEnd
                    ) {

                        if (showCar) {
                            Car()
                        } else {
                            Text(
                                text = parkingBoxId,
                                color = PaleSky,
                                modifier = Modifier.padding(start = 1.dp, bottom = 6.dp),
                                fontSize = 12.sp
                            )
                        }
                    }

                }
            }

            Divider(modifier = Modifier.width(90.dp), thickness = 0.5.dp, color = PaleSky)
        }
    }


}


@Composable
fun ParkingBoxLeft(
    showCar: Boolean,
    parkingBoxId: String,
    boxBackgroundColor: androidx.compose.ui.graphics.Color,
    onClick: (String, String) -> Unit,
    pillarID: String
) {

    Column() {

        Divider(modifier = Modifier.width(90.dp), thickness = 0.5.dp, color = PaleSky)

        Box(
            modifier = Modifier
                .height(59.dp)
                .width(90.dp)
                .clickable {
                    onClick(parkingBoxId, pillarID)
                }
        ) {

            Row(modifier = Modifier.fillMaxSize()) {
                Box(
                    modifier = Modifier
                        .fillMaxHeight()
                        .width(89.5.dp)
                        .background(color = boxBackgroundColor),
                    contentAlignment = if (showCar) Alignment.Center else Alignment.BottomStart
                ) {
                    if (showCar) {
                        Car()
                    } else {
                        Text(
                            text = parkingBoxId,
                            color = PaleSky,
                            modifier = Modifier.padding(start = 1.dp, bottom = 6.dp),
                            fontSize = 12.sp
                        )
                    }
                }
                Box(
                    modifier = Modifier
                        .height(59.dp)
                        .width(1.dp)
                        .background(PaleSky)
                )
            }
        }

        Divider(modifier = Modifier.width(90.dp), thickness = 0.5.dp, color = PaleSky)
    }
}


@Composable
fun Car() {
    Image(
        painter = painterResource(id = R.drawable.car), contentDescription = "Car image",
        modifier = Modifier.width(61.dp)
    )
}