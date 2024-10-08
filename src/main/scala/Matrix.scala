package Ex0

import chisel3._

// This import statement makes the scala vector invisible, reducing confusion
import scala.collection.immutable.{Vector => _}

class Matrix(val rowsDim: Int, val colsDim: Int) extends Module {

  val io = IO(
    new Bundle {
      val colIdx = Input(UInt(32.W))
      val rowIdx = Input(UInt(32.W))
      val dataIn = Input(UInt(32.W))
      val writeEnable = Input(Bool())

      val dataOut = Output(UInt(32.W))
    }
  )
  val rows = Vec.fill(rowsDim)(Module(new Vector(colsDim)).io)

  for (ii <- 0 until rowsDim) {
    rows(ii).dataIn := 0.U
    rows(ii).writeEnable := false.B
    rows(ii).idx := io.colIdx

    when(io.writeEnable && io.rowIdx === ii.U) {
      rows(ii).dataIn := io.dataIn
      rows(ii).writeEnable := true.B
    }
  }
  io.dataOut := rows(io.rowIdx).dataOut

}
